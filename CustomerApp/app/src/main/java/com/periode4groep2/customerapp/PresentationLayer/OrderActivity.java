package com.periode4groep2.customerapp.PresentationLayer;
//http://stackoverflow.com/questions/9824074/android-expandablelistview-looking-for-a-tutorial

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.periode4groep2.customerapp.R;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        basket = (ImageView)findViewById(R.id.order_basket);
        basket.setOnClickListener(this);
    }

    public void onClick(View v){
        Intent intent = new Intent(this, OrderDetailActivity.class);
        startActivity(intent);
    }
}
