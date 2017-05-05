package com.periode4groep2.customerapp.PersistancyLayer;

import com.periode4groep2.customerapp.DomainModel.Order;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public interface OrderDAO {

    ArrayList<Order> selectData();
    void updateData(Order order);
}
