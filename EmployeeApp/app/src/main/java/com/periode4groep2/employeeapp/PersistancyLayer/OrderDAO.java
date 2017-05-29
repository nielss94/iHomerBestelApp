package com.periode4groep2.employeeapp.PersistancyLayer;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Order;
import com.periode4groep2.employeeapp.DomainModel.OrderItem;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public interface OrderDAO {

    void selectData(OrderSetAvailable c);
    void addToOrder(Account account, Order order, ArrayList<OrderItem> orderItems);
    void removeFromOrder(Account account,Order order, ArrayList<OrderItem> orderItems);
    void handleOrder(Account account, Order order);
}
