package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.R;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private Account account;
    private LinearLayout scanOrderView;
    private LinearLayout searchOrdersView;
    private LinearLayout stockView;
    private Toolbar toolbar;

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

        toolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_homebutton);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.employee_home_title);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(scanOrderView)){
            Intent i = new Intent(this, HandleOrderActivity.class);
            i.putExtra("account", account);
            startActivity(i);
        }else if(v.equals(searchOrdersView)){
            Intent i = new Intent(this, SearchOrderActivity.class);
            i.putExtra("account", account);
            startActivity(i);
        }else if(v.equals(stockView)){
            Intent i = new Intent(this, StockActivity.class);
            i.putExtra("account", account);
            startActivity(i);
        }
    }
}
