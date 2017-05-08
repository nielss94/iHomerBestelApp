package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.periode4groep2.customerapp.R;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {
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
            Intent orderIntent = new Intent(this, OrderActivity.class);
            startActivity(orderIntent);
        }else if(v.equals(myAcountLayoutButton)){
            Intent myAccountIntent = new Intent(this, MyAccountActivity.class);
            startActivity(myAccountIntent);
        } else if(v.equals(balanceLayoutButton)){
            Intent walletIntent = new Intent(this, WalletActivity.class);
            startActivity(walletIntent);
        } else if (v.equals(myOrdersLayoutButton)){
            Intent myOrdersIntent = new Intent(this, OrderHistoryActivity.class);
            startActivity(myOrdersIntent);
        }
    }
}
