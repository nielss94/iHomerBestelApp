package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.periode4groep2.customerapp.R;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {
    LinearLayout orderLayoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        orderLayoutButton = (LinearLayout)findViewById(R.id.OrderID);
        orderLayoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "SLETJE", Toast.LENGTH_SHORT).show();
    }
}
