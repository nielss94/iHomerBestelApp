package com.periode4groep2.customerapp.PersistancyLayer;

import android.content.Context;

import com.periode4groep2.customerapp.DomainModel.Account;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public interface AccountDAO {

    ArrayList<Account> selectData(AccountSetAvailable context);
    void updateData(Account account);
}
