package com.periode4groep2.employeeapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;

public class AddExtraProducts extends AppCompatActivity {
    private ArrayList<ProductButton> productButtons = new ArrayList<>();
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products_activity);

        //mic-check-1-2
    }
}
