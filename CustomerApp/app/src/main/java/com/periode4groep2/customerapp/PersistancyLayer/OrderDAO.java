package com.periode4groep2.customerapp.PersistancyLayer;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.DomainModel.OrderItem;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public interface OrderDAO {

    void selectData(OrderSetAvailable c);

    void insertData(Account account, Order order);
    void handleOrder(Account account, Order order);
    void deleteOrder(Account account, Order order);
    void addToOrder(Account account, Order order, ArrayList<OrderItem> orderItems);
}
