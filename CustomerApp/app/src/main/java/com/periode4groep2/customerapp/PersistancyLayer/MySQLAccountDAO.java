package com.periode4groep2.customerapp.PersistancyLayer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.PresentationLayer.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public class MySQLAccountDAO implements AccountDAO, MySQLAccountAPIConnector.AccountAvailable, BalanceGetConnector.BalanceAvailable {

    private final String TAG = getClass().getSimpleName();

    private MyAccountAvailable context;
    private AccountBalanceAvailable balanceContext;
    @Override
    public void selectData(MyAccountAvailable c, String email, String password) {
        context = c;
        String[] urls = {
                "https://ihomerapi.herokuapp.com/API/getAccount?email="+email+"&password="+password
        };
        new MySQLAccountAPIConnector(this).execute(urls);

    }



    @Override
    public void updateBalance(Account account, double amount) {
        new BalanceUpdateConnector(account,amount).execute();
    }

    @Override
    public void getAccountBalance(AccountBalanceAvailable context, Account account) {
        String[] urls = {
                "https://ihomerapi.herokuapp.com/API/getAccount?email="+account.getEmail()+"&password="+account.getPassword()
        };
        new BalanceGetConnector(this).execute(urls);
        balanceContext = context;

    }


    @Override
    public void accountAvailable(Account account) {

        context.myAccountAvailable(account);
    }

    @Override
    public void accountNotAvailable() {
        context.myAccountNotAvailable();
    }

    @Override
    public void balanceAvailable(Double balance) {
        balanceContext.accountBalanceAvailable(balance);
    }
}
