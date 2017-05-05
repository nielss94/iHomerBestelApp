package com.periode4groep2.customerapp.DomainModel;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public class Order {

    private int orderID;
    private String email;
    private boolean handled;
    private Double totalPrice;
    private String date;
    private ArrayList<OrderItem> orderItems;

    public Order(int orderID, String email, boolean handled, Double totalPrice, String date) {
        this.orderID = orderID;
        this.email = email;
        this.handled = handled;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", email='" + email + '\'' +
                ", handled=" + handled +
                ", totalPrice=" + totalPrice +
                ", date='" + date + '\'' +
                '}';
    }
}
