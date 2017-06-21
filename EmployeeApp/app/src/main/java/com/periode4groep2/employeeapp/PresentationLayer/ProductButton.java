package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Context;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.R;

/**
 * Created by ricky on 8-6-2017.
 */

public class ProductButton extends android.support.v7.widget.AppCompatButton {
    private Product product;

    public ProductButton(Context context) {
        super(context);
        //setBackgroundResource();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
