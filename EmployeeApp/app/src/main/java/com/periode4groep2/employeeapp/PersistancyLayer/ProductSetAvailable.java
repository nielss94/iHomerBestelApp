package com.periode4groep2.employeeapp.PersistancyLayer;

import com.periode4groep2.employeeapp.DomainModel.Product;

import java.util.ArrayList;

/**
 * Created by Niels on 5/9/2017.
 */

public interface ProductSetAvailable {

    void productSetAvailable(ArrayList<Product> products);
}
