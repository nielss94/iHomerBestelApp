package com.periode4groep2.customerapp.PersistancyLayer;

import android.content.Context;

import com.periode4groep2.customerapp.DomainModel.Account;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public interface AccountDAO {

    void selectData(MyAccountAvailable context, String email, String password);
    void updateBalance(Account account, double amount);
    void getAccountBalance(AccountBalanceAvailable context, Account account);
}
