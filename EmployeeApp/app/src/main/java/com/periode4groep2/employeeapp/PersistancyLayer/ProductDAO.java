package com.periode4groep2.employeeapp.PersistancyLayer;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Product;

/**
 * Created by Niels on 5/5/2017.
 */

public interface ProductDAO {

    void selectData(ProductSetAvailable c);
    void changeStock(Account account, Product product);
}
