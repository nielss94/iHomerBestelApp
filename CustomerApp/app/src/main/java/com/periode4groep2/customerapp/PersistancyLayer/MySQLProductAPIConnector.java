package com.periode4groep2.customerapp.PersistancyLayer;

import android.os.AsyncTask;
import android.util.Log;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Product;

import org.json.JSONArray;
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

/**
 * Created by Niels on 5/9/2017.
 */

public class MySQLProductAPIConnector extends AsyncTask<String, Void, String> {

    private final String TAG = getClass().getSimpleName();
    private ProductAvailable listener;

    public MySQLProductAPIConnector(ProductAvailable listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        int responseCode = -1;

        //URL we get from .execute()
        String productsUrl = params[0];

        String result = "";

        try {
            //Make URL object
            URL url = new URL(productsUrl);
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
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                result = getStringFromInputStream(inputStream);
            } else {
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

        Log.i(TAG,result);

        try {
            //Top level object
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Product p = new Product
                (
                    jsonObject.optInt("ProductID"),
                    jsonObject.optString("Category"),
                    jsonObject.optString("Name"),
                    jsonObject.optBoolean("InStock"),
                    (jsonObject.optDouble("Price") / 100)
                );
                if(i == jsonArray.length() - 1){
                    listener.productAvailable(p,true);
                }
                else{
                    listener.productAvailable(p, false);
                }
            }

        } catch (JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());

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
    public interface ProductAvailable {

        void productAvailable(Product product, boolean done);
    }
}
