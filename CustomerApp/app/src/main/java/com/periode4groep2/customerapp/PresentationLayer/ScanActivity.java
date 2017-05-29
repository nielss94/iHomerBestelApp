package com.periode4groep2.customerapp.PresentationLayer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.periode4groep2.customerapp.CardEmulation.AccountStorage;
import com.periode4groep2.customerapp.CardEmulation.CardService;
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
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        myToolbar.setTitle(R.string.Scan_toolbar);
        setSupportActionBar(myToolbar);

        balanceButton = (Button)findViewById(R.id.buttonBalance);
        menuButton = (Button)findViewById(R.id.scan_button);
        balanceButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);

        order = (Order)getIntent().getSerializableExtra("order");
        account = (Account)getIntent().getSerializableExtra("account");
        AccountStorage.SetAccount(this, order.getOrderID() + "");

        checkicon = (ImageView)findViewById(R.id.scan_icon);

        balanceButton.setText("€" + String.format("%.2f", account.getBalance()/100) + "");
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
