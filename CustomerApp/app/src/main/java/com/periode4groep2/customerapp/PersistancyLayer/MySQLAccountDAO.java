package com.periode4groep2.customerapp.PersistancyLayer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.PresentationLayer.MainActivity;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public class MySQLAccountDAO implements AccountDAO, MySQLAccountAPIConnector.AccountAvailable {

    private final String TAG = getClass().getSimpleName();

    private MyAccountAvailable context;

    @Override
    public void selectData(MyAccountAvailable c, String email, String password) {
        context = c;
        String[] urls = {
                "https://ihomerapi.herokuapp.com/API/getAccount?email="+email+"&password="+password
        };
        new MySQLAccountAPIConnector(this).execute(urls);

    }

    @Override
    public void updateData(Account account) {

    }

    @Override
    public void accountAvailable(Account account) {

        context.myAccountAvailable(account);
    }

    @Override
    public void accountNotAvailable() {
        context.myAccountNotAvailable();
    }
}
