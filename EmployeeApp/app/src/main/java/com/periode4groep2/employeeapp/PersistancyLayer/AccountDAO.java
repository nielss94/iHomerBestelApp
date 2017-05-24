package com.periode4groep2.employeeapp.PersistancyLayer;

import com.periode4groep2.employeeapp.DomainModel.Account;

/**
 * Created by Niels on 5/5/2017.
 */

public interface AccountDAO {

    void selectData(MyAccountAvailable context, String email, String password);
    void updateBalance(Account account, double amount);
}
