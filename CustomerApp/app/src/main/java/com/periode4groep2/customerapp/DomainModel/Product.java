package com.periode4groep2.customerapp.DomainModel;

import java.io.Serializable;

/**
 * Created by Niels on 5/5/2017.
 */

public class Product implements Serializable {

    private int productID;
    private String category;
    private String name;
    private boolean inStock;
    private Double price;

    public Product(int productID, String category, String name, boolean inStock, Double price) {
        this.productID = productID;
        this.category = category;
        this.name = name;
        this.inStock = inStock;
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", inStock=" + inStock +
                ", price=" + price +
                '}';
    }
}
