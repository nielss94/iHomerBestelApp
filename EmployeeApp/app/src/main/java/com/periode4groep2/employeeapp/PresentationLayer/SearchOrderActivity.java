package com.periode4groep2.employeeapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Order;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;

public class SearchOrderActivity extends AppCompatActivity implements View.OnClickListener{
    //implements View.OnClickListener, OrderSetAvailable
    private final String TAG = getClass().getSimpleName();

    private ArrayList<Order> orderArrayList;
    private Account account;
    private Order order;

    private EditText searchOrderEditText;
    private Button searchOrderButton;
    private ListView searchOrderListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_order);

        account = (Account)getIntent().getSerializableExtra("account");

        searchOrderEditText = (EditText)findViewById(R.id.searchOrderEditText);
        searchOrderListView = (ListView)findViewById(R.id.searchedOrdersListView);
        searchOrderButton = (Button)findViewById(R.id.searchOrderButton);

        searchOrderButton.setOnClickListener(this);
        searchOrderListView.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        if(v.equals(searchOrderButton)){
            String entry_str = searchOrderEditText.getText().toString();
            //Do something to send string via API.

        } else if (v.equals(searchOrderListView)){

        }
    }
}
