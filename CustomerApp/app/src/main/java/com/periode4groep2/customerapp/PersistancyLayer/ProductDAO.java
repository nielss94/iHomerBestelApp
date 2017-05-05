package com.periode4groep2.customerapp.PersistancyLayer;

import com.periode4groep2.customerapp.DomainModel.Product;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public interface ProductDAO {

    ArrayList<Product> selectData();
    void updateData(Product product);
}
