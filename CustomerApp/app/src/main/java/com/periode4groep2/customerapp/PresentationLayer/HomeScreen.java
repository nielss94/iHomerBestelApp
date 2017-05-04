package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.periode4groep2.customerapp.R;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {
    LinearLayout orderLayoutButton, myAcountLayoutButton,
                  balanceLayoutButton, myOrdersLayoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        orderLayoutButton = (LinearLayout)findViewById(R.id.OrderID);
        myAcountLayoutButton = (LinearLayout)findViewById(R.id.AccountID);
        balanceLayoutButton = (LinearLayout)findViewById(R.id.BalanceID);
        myOrdersLayoutButton = (LinearLayout)findViewById(R.id.MyOrdersID);
        orderLayoutButton.setOnClickListener(this);
        myAcountLayoutButton.setOnClickListener(this);
        balanceLayoutButton.setOnClickListener(this);
        myOrdersLayoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(orderLayoutButton)){
            Toast.makeText(this, "SLETJE ORDERS", Toast.LENGTH_SHORT).show();
        }else if(v.equals(myAcountLayoutButton)){
            Toast.makeText(this, "SLETJE ACCOUNT", Toast.LENGTH_SHORT).show();
        } else if(v.equals(balanceLayoutButton)){
            Toast.makeText(this, "SLETJE BALANCE", Toast.LENGTH_SHORT).show();
        } else if (v.equals(myOrdersLayoutButton)){
            Toast.makeText(this, "SLETJE MY ORDERS", Toast.LENGTH_SHORT).show();
        }
    }
}
