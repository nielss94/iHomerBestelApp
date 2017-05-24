package com.periode4groep2.customerapp.PresentationLayer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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

    private Button button;
    private ImageView checkicon;
    private Order order;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        button = (Button)findViewById(R.id.scan_button);
        button.setOnClickListener(this);
        button.setClickable(false);

        order = (Order)getIntent().getSerializableExtra("order");
        account = (Account)getIntent().getSerializableExtra("account");
        AccountStorage.SetAccount(this, order.getOrderID() + "");

        checkicon = (ImageView)findViewById(R.id.scan_icon);
        checkicon.setVisibility(View.INVISIBLE);
    }

    public void onClick(View v){
        Intent intent = new Intent(this, CardService.class);
        intent.putExtra("account", account.getFirstName());
        startService(intent);
    }

    public void onResume(){
        super.onResume();
    }
}
