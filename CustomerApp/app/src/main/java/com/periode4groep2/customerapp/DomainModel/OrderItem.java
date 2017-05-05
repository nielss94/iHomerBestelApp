package com.periode4groep2.customerapp.DomainModel;

/**
 * Created by Niels on 5/5/2017.
 */

public class OrderItem {

    private int orderItemID;
    private int orderID;
    private Product product;
    private int quantity;

    public OrderItem(int orderItemID, int orderID, Product product, int quantity) {
        this.orderItemID = orderItemID;
        this.orderID = orderID;
        this.product = product;
        this.quantity = quantity;
    }

    public int getOrderItemID() {
        return orderItemID;
    }

    public void setOrderItemID(int orderItemID) {
        this.orderItemID = orderItemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemID=" + orderItemID +
                ", orderID=" + orderID +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
