package com.periode4groep2.employeeapp.PersistancyLayer;

import android.os.AsyncTask;
import android.util.Log;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Order;
import com.periode4groep2.employeeapp.DomainModel.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Niels on 5/17/2017.
 */

public class ProductStockConnector extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "ProductStockConnector";
    private Account account;
    private Product product;

    public ProductStockConnector(Account account, Product product) {
        this.account = account;
        this.product = product;
    }

    @Override
    protected Void doInBackground(Void... params) {
        StringBuilder sb = new StringBuilder();
        String http = "https://ihomerapi.herokuapp.com/API/setProductInStock";
        HttpURLConnection urlConnection=null;
        try {
            URL url = new URL(http);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.connect();
            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("email",account.getEmail());
            jsonParam.put("password", account.getPassword());
            jsonParam.put("productid", product.getProductID());
            if(product.isInStock()){
                jsonParam.put("set", false);
            }else{
                jsonParam.put("set", true);
            }

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
