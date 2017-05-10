package com.periode4groep2.customerapp.PersistancyLayer;

import com.periode4groep2.customerapp.DomainModel.Order;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public class MySQLOrderDAO implements OrderDAO, MySQLOrderAPIConnector.OrderAvailable {
    private final String TAG = getClass().getSimpleName();

    private ArrayList<Order> orders = new ArrayList<>();
    private MySQLOrderAPIConnector mySQLOrderAPIConnector = new MySQLOrderAPIConnector(this);
    private OrderSetAvailable context;

    @Override
    public void selectData(OrderSetAvailable c) {
        context = c;
        String[] urls = {
                "http://ihomerapi.herokuapp.com/api/getOrders"
        };

        mySQLOrderAPIConnector.execute(urls);
    }

    @Override
    public void updateData(Order order) {

    }

    @Override
    public void orderAvailable(Order order, boolean done) {
        orders.add(order);
        if(done) {
            context.orderSetAvailable(orders);
        }
    }
}
