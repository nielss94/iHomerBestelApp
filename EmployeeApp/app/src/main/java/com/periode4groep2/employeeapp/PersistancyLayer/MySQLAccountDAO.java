package com.periode4groep2.employeeapp.PersistancyLayer;

import com.periode4groep2.employeeapp.DomainModel.Account;

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
    public void updateBalance(Account account, double amount) {
        new BalanceUpdateConnector(account,amount).execute();
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
