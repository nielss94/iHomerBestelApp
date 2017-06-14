package com.periode4groep2.customerapp.PersistancyLayer;

import android.os.AsyncTask;
import android.util.Log;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.DomainModel.OrderItem;
import com.periode4groep2.customerapp.DomainModel.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Niels on 5/16/2017.
 */

public class OrderAddItemConnector extends AsyncTask<Void, Void, Void>{

    private static final String TAG = "OrderAddItemConnector";
    private Account account;
    private Order order;
    private ArrayList<OrderItem> orderItems;

    public OrderAddItemConnector(Account account, Order order, ArrayList<OrderItem> orderItems) {
        this.account = account;
        this.order = order;
        this.orderItems = orderItems;
    }

    @Override
    protected Void doInBackground(Void... params) {
        StringBuilder sb = new StringBuilder();
        String http = "https://ihomerapi.herokuapp.com/API/addToOrder";
        HttpURLConnection urlConnection=null;
        try {
            URL url = new URL(http);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.connect();
            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("email",account.getEmail());
            jsonParam.put("password", account.getPassword());
            jsonParam.put("orderid", order.getOrderID());
            JSONArray orderItemArray = new JSONArray();
            for (int i = 0; i < orderItems.size(); i++) {
                JSONObject orderItem = new JSONObject();
                orderItem.put("ProductID",orderItems.get(i).getProductID());
                orderItem.put("Quantity",orderItems.get(i).getQuantity());
                orderItemArray.put(orderItem);
            }
            jsonParam.put("products", orderItemArray);
            Log.i(TAG,jsonParam.toString());
            OutputStreamWriter out = new   OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonParam.toString());
            out.close();

            int HttpResult =urlConnection.getResponseCode();
            if(HttpResult ==HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                System.out.println(""+sb.toString());

            }else{
                System.out.println(urlConnection.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }finally{
            if(urlConnection!=null)
                urlConnection.disconnect();
        }
        return null;
    }
}
