package com.periode4groep2.customerapp.PresentationLayer;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.periode4groep2.customerapp.CardEmulation.AccountStorage;
import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.R;


/**
 * Created by Lars on 8-5-2017.
 */

public class ScanActivity extends AppCompatActivity implements View.OnClickListener{

    private Button balanceButton, menuButton;
    private ImageView checkicon;
    private Order order;
    private Toolbar toolbar;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.Scan_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanActivity.this, HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        balanceButton = (Button)findViewById(R.id.buttonBalance);
        menuButton = (Button)findViewById(R.id.scan_button);
        balanceButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);

        order = (Order)getIntent().getSerializableExtra("order");
        account = (Account)getIntent().getSerializableExtra("account");
        AccountStorage.SetAccount(this, order.getOrderID() + "");

        checkicon = (ImageView)findViewById(R.id.scan_icon);

        balanceButton.setText("€" + String.format("%.2f", account.getBalance()/100));
    }

    public void onResume(){
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(v.equals(balanceButton)){
            Intent addBalanceIntent = new Intent(getApplicationContext(), BalanceActivity.class);
            addBalanceIntent.putExtra("account", account);
            startActivity(addBalanceIntent);
        } else if(v.equals(menuButton)){
            Intent menu = new Intent(this, HomeScreenActivity.class);
            startActivity(menu);
        }
    }
}
