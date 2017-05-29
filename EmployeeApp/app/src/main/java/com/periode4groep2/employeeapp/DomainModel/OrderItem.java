package com.periode4groep2.employeeapp.DomainModel;

import java.io.Serializable;

/**
 * Created by Niels on 5/5/2017.
 */

public class OrderItem implements Serializable{

    private int productID;
    private int quantity;

    public OrderItem(int productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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
                "productID=" + productID +
                ", quantity=" + quantity +
                '}';
    }
}
