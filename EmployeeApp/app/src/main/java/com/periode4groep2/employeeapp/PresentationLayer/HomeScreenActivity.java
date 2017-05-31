package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.R;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private Account account;
    private LinearLayout scanOrderView;
    private LinearLayout searchOrdersView;
    private LinearLayout stockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        account = (Account)getIntent().getSerializableExtra("account");

        scanOrderView = (LinearLayout)findViewById(R.id.scanOrderView);
        searchOrdersView = (LinearLayout)findViewById(R.id.searchOrderView);
        stockView = (LinearLayout)findViewById(R.id.stockView);


        scanOrderView.setOnClickListener(this);
        searchOrdersView.setOnClickListener(this);
        stockView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(scanOrderView)){
            Intent i = new Intent(this, HandleOrderActivity.class);
            i.putExtra("account", account);
            startActivity(i);
        }else if(v.equals(searchOrdersView)){
//            Intent i = new Intent(this, SearchOrderActivity.class);
//            i.putExtra("account", account);
//            startActivity(i);
        }else if(v.equals(stockView)){
//            Intent i = new Intent(this, StockActivity.class);
//            i.putExtra("account", account);
        }
    }
}
