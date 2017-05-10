package com.periode4groep2.customerapp.PersistancyLayer;

import android.os.AsyncTask;
import android.util.Log;

import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.DomainModel.OrderItem;

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
 * Created by jesse on 10/05/17.
 */

public class MySQLOrderAPIConnector extends AsyncTask<String, Void, String> {

    private final String TAG = getClass().getSimpleName();
    private OrderAvailable listener;

    public MySQLOrderAPIConnector(OrderAvailable listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        int responseCode = -1;

        //URL we get from .execute()
        String ordersUrl = params[0];

        String result = "";

        try {
            //Make URL object
            URL url = new URL(ordersUrl);
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
        try {
            //Top level object
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Order o = new Order (
                    jsonObject.optInt("OrderID"),
                    jsonObject.optString("Email"),
                    jsonObject.optBoolean("Handled"),
                    (jsonObject.optDouble("TotalPrice") / 100),
                    jsonObject.optString("Date")
                );

                JSONArray orderItems = jsonObject.getJSONArray("OrderItems");

                for (int j = 0; j < orderItems.length() ; j++) {
                    JSONObject orderItem = orderItems.getJSONObject(j);
                    OrderItem oi = new OrderItem(
                        orderItem.optInt("ProductID"),
                        orderItem.optInt("Quantity")
                    );
                    o.addOrderItem(oi);
                }

                if(i == jsonArray.length() - 1){
                    listener.orderAvailable(o,true);
                }
                else{
                    listener.orderAvailable(o, false);
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
    public interface OrderAvailable {

        void orderAvailable(Order order, boolean done);
    }
}
