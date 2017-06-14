package com.periode4groep2.customerapp.PersistancyLayer;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.DomainModel.OrderItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public class MySQLOrderDAO implements OrderDAO, MySQLOrderAPIConnector.OrderAvailable {
    private final String TAG = getClass().getSimpleName();

    private ArrayList<Order> orders = new ArrayList<>();
    private OrderSetAvailable context;

    @Override
    public void selectData(OrderSetAvailable c) {
        context = c;
        String[] urls = {
                "http://ihomerapi.herokuapp.com/api/getOrders"
        };

        new MySQLOrderAPIConnector(this).execute(urls);
    }

    @Override
    public void insertData(Account account, Order order) {
        new OrderInsertConnector(account,order).execute();
    }

    @Override
    public void handleOrder(Account account, Order order) {
        new OrderHandleConnector(account, order).execute();
    }

    @Override
    public void deleteOrder(Account account, Order order) {
        new OrderDeleteConnector(account, order).execute();
    }

    @Override
    public void addToOrder(Account account, Order order, ArrayList<OrderItem> orderItems) {
        new OrderAddItemConnector(account,order,orderItems).execute();
    }

    @Override
    public void orderAvailable(Order order, boolean done) {
        orders.add(order);
        if(done) {
            context.orderSetAvailable(orders);
        }
    }
}
