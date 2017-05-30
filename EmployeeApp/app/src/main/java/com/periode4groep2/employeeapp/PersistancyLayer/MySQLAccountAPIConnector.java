package com.periode4groep2.employeeapp.PersistancyLayer;

/**
 * Created by Niels on 5/8/2017.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.periode4groep2.employeeapp.DomainModel.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MySQLAccountAPIConnector extends AsyncTask<String, Void, String> {


    private final String TAG = getClass().getSimpleName();
    private AccountAvailable listener;

    public MySQLAccountAPIConnector(AccountAvailable listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        int responseCode = -1;

        //URL we get from .execute()
        String accountUrl = params[0];

        String result = "";

        try {
            //Make URL object
            URL url = new URL(accountUrl);
            //Open connection on URL
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                return null;
            }

            // Initiate a HTTP connection
            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");

            // Do a request on the HTTP connection
            httpConnection.connect();

            // Check if connection is made using a response code
            responseCode = httpConnection.getResponseCode();
            Log.i(TAG,responseCode + "");
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                result = getStringFromInputStream(inputStream);
            } else if(responseCode == 404 || responseCode == 403){
                result = "Account not found";
            }
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return result;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {


        if(result.equalsIgnoreCase("Account not found"))
        {
            Log.i(TAG,"ACCOUNT NOT FOUND!");
            listener.accountNotAvailable();
        }
        else {
            try {
                //Top level object
                JSONObject jsonObject = new JSONObject(result);


                Account acc = new Account(
                        jsonObject.optString("Email"),
                        jsonObject.optString("IBAN"),
                        jsonObject.optDouble("Balance"),
                        jsonObject.optString("FirstName"),
                        jsonObject.optString("LastName"),
                        jsonObject.optString("Password"),
                        jsonObject.optString("DateOfBirth"),
                        (jsonObject.optInt("isemployee") == 1) ? true : false
                );
                Log.i(TAG, ""+acc.isEmployee());
                listener.accountAvailable(acc);

            } catch (JSONException ex) {
                Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());

            }
        }
    }
    //Convert InputStream to String
    private String getStringFromInputStream(InputStream inputStream) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    //Callback interface
    public interface AccountAvailable {

        void accountAvailable(Account account);
        void accountNotAvailable();
    }
}