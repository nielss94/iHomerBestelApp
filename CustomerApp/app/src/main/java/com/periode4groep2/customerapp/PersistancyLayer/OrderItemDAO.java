package com.periode4groep2.customerapp.PersistancyLayer;

import com.periode4groep2.customerapp.DomainModel.OrderItem;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public interface OrderItemDAO {

    ArrayList<OrderItem> selectData();
    void updateData(OrderItem orderItem);
}
