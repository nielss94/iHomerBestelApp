package com.periode4groep2.customerapp.PersistancyLayer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.periode4groep2.customerapp.DomainModel.Account;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public class MySQLAccountDAO implements AccountDAO, MySQLAccountAPIConnector.AccountAvailable {

    private final String TAG = getClass().getSimpleName();

    private ArrayList<Account> accounts = new ArrayList<>();
    private MySQLAccountAPIConnector mySQLAccountAPIConnector = new MySQLAccountAPIConnector(this);
    private AccountSetAvailable context;

    @Override
    public ArrayList<Account> selectData(AccountSetAvailable c) {
        context = c;
        String[] urls = {
                "http://10.0.2.2:8080/api/accounts"
        };

        mySQLAccountAPIConnector.execute(urls);

        return accounts;
    }

    @Override
    public void updateData(Account account) {

    }

    @Override
    public void accountAvailable(Account account, boolean done) {
        accounts.add(account);
        if(done)
        {
            context.accountSetAvailable(accounts);
        }
    }
}
