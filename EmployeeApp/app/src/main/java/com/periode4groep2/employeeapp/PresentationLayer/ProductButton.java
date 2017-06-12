package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Context;
import android.widget.Button;

/**
 * Created by ricky on 8-6-2017.
 */

public class ProductButton extends android.support.v7.widget.AppCompatButton {

    private int productID;
    private boolean instock;

    public boolean isInstock() {
        return instock;
    }

    public void setInstock(boolean instock) {
        this.instock = instock;
    }

    public ProductButton(Context context) {
        super(context);
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
