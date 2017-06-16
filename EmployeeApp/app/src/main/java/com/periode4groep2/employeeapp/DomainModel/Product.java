package com.periode4groep2.employeeapp.DomainModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Niels on 5/5/2017.
 */

public class Product implements Parcelable {

    private int productID;
    private String category;
    private String name;
    private boolean inStock;
    private Double price;
    private String nameEng;
    private String categoryEng;

    public Product(int productID, String category, String name, boolean inStock, Double price) {
        this.productID = productID;
        this.category = category;
        this.name = name;
        this.inStock = inStock;
        this.price = price;
    }

    protected Product(Parcel in) {
        productID = in.readInt();
        category = in.readString();
        name = in.readString();
        price = in.readDouble();
        inStock = in.readByte() != 0;
        nameEng = in.readString();
        categoryEng = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getCategoryEng() {
        return categoryEng;
    }

    public void setCategoryEng(String categoryEng) {
        this.categoryEng = categoryEng;
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
                ", nameEng='" + nameEng + '\'' +
                ", categoryEng='" + categoryEng + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productID);
        dest.writeString(category);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeByte((byte) (inStock ? 1 : 0));
        dest.writeString(nameEng);
        dest.writeString(categoryEng);
    }
}
