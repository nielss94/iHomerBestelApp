package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.R;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout orderLayoutButton, settingsLayoutButton,
                  balanceLayoutButton, myOrdersLayoutButton;
    Account account;
    private Button balanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        myToolbar.setTitle("Home");
        setSupportActionBar(myToolbar);

        orderLayoutButton = (LinearLayout)findViewById(R.id.OrderID);
        settingsLayoutButton = (LinearLayout)findViewById(R.id.AccountID);
        balanceLayoutButton = (LinearLayout)findViewById(R.id.BalanceID);
        myOrdersLayoutButton = (LinearLayout)findViewById(R.id.MyOrdersID);
        balanceButton = (Button)findViewById(R.id.buttonBalance);

        orderLayoutButton.setOnClickListener(this);
        settingsLayoutButton.setOnClickListener(this);
        balanceLayoutButton.setOnClickListener(this);
        myOrdersLayoutButton.setOnClickListener(this);
        balanceButton.setOnClickListener(this);

        account = (Account)getIntent().getSerializableExtra("account");
        balanceButton.setText("€" + String.format("%.2f", account.getBalance()/100) + "");
    }

    @Override
    public void onClick(View v) {
        if(v.equals(orderLayoutButton)){
            Intent orderIntent = new Intent(this, OrderActivity.class);
            orderIntent.putExtra("account", account);
            startActivity(orderIntent);
        }else if(v.equals(settingsLayoutButton)){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            settingsIntent.putExtra("account", account);
            startActivity(settingsIntent);
        } else if(v.equals(balanceLayoutButton)){
            Intent addBalanceIntent = new Intent(this, BalanceActivity.class);
            addBalanceIntent.putExtra("account", account);
            startActivity(addBalanceIntent);
        } else if (v.equals(myOrdersLayoutButton)){
            Intent myOrdersIntent = new Intent(this, OrderHistoryActivity.class);
            myOrdersIntent.putExtra("account", account);
            startActivity(myOrdersIntent);
        } else if (v.equals(balanceButton)){
            Intent addBalanceIntent = new Intent(this, BalanceActivity.class);
            addBalanceIntent.putExtra("account", account);
            startActivity(addBalanceIntent);
        }
    }
}
