package com.periode4groep2.employeeapp.PersistancyLayer;

import com.periode4groep2.employeeapp.DomainModel.Account;

/**
 * Created by Niels on 5/8/2017.
 */

public interface MyAccountAvailable
{
    void myAccountAvailable(Account acc);
    void myAccountNotAvailable();
}
