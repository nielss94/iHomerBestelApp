package com.periode4groep2.employeeapp.PersistancyLayer;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Order;
import com.periode4groep2.employeeapp.DomainModel.OrderItem;

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
        orders.clear();
        context = c;
        String[] urls = {
                "http://ihomerapi.herokuapp.com/api/getOrders?sorted=true"
        };

        new MySQLOrderAPIConnector(this).execute(urls);
    }

    @Override
    public void addToOrder(Account account, Order order, ArrayList<OrderItem> orderItems) {
        new AddToOrderConnector(account,order,orderItems).execute();
    }

    @Override
    public void removeFromOrder(Account account, Order order, ArrayList<OrderItem> orderItems) {
        new RemoveFromOrderConnector(account,order,orderItems).execute();
    }

    @Override
    public void deleteOrder(Account account, Order order) {
        new OrderDeleteConnector(account,order);
    }

    @Override
    public void handleOrder(Account account, Order order) {
        new OrderHandleConnector(account, order).execute();
    }

    @Override
    public void orderAvailable(Order order, boolean done) {
        orders.add(order);
        if(done) {
            context.orderSetAvailable(orders);
        }
    }
}
