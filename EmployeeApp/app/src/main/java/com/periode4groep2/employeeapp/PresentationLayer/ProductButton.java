package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.BackgroundColorSpan;
import android.widget.Button;
import android.widget.LinearLayout;

import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.R;

/**
 * Created by ricky on 8-6-2017.
 */

public class ProductButton extends android.support.v7.widget.AppCompatButton {
    private Product product;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    public ProductButton(Context context) {
        super(context);
        params.setMargins(0, 0 , 0, 25);
        setLayoutParams(params);
        setBackground(getResources().getDrawable(R.drawable.button_border));
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
