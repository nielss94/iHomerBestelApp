package com.periode4groep2.customerapp.PersistancyLayer;

import com.periode4groep2.customerapp.DomainModel.Account;

import java.util.ArrayList;

/**
 * Created by Niels on 5/8/2017.
 */

public interface MyAccountAvailable
{
    void myAccountAvailable(Account acc);
    void myAccountNotAvailable();
}
