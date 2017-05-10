package com.periode4groep2.customerapp.PersistancyLayer;

import com.periode4groep2.customerapp.DomainModel.Order;

/**
 * Created by Niels on 5/5/2017.
 */

public interface OrderDAO {

    void selectData(OrderSetAvailable c);

    void updateData(Order order);
}
