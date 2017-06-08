package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.customerapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {
    //Logging tag
    private final String TAG = this.getClass().getSimpleName();
    //controller elements
    private ListView orderItemListView;
    private UnhandledOrderItemAdapter unhandledOrderItemAdapter;
    private TextView totalTagTextView;
    private TextView totalPriceTextView;
    private Button cancelOrderButton;
    private Button scanOrderButton;
    private Button balanceButton;
    private Account account;
    private Order order;
    private ArrayList<Product> products = new ArrayList<>();
    private static final String ACCOUNT = "account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        myToolbar.setTitle(R.string.Order_Detail_toolbar);
        setSupportActionBar(myToolbar);

        Bundle b = this.getIntent().getExtras();
        products = b.getParcelableArrayList("products");
        Log.i(TAG,products.get(0).getName() +"");
        account = (Account)getIntent().getSerializableExtra(ACCOUNT);
        order = (Order)getIntent().getSerializableExtra("order");

        orderItemListView = (ListView)findViewById(R.id.orderItemListView);
        unhandledOrderItemAdapter = new UnhandledOrderItemAdapter(this, order.getOrderItems(), products);
        orderItemListView.setAdapter(unhandledOrderItemAdapter);
        totalTagTextView = (TextView)findViewById(R.id.totalTagTextView);
        totalPriceTextView = (TextView)findViewById(R.id.totalPriceTagTextView);
        totalPriceTextView.setText("€"+String.format("%.2f" ,order.getTotalPrice()));

        cancelOrderButton = (Button)findViewById(R.id.cancelOrderButton);
        scanOrderButton = (Button)findViewById(R.id.scanOrderButton);
        balanceButton = (Button)findViewById(R.id.buttonBalance);

        cancelOrderButton.setOnClickListener(this);
        scanOrderButton.setOnClickListener(this);
        balanceButton.setOnClickListener(this);
        balanceButton.setText("€" + String.format("%.2f" ,account.getBalance()/100));
    }

    @Override
    public void onClick(View v){
        if(v.equals(cancelOrderButton)){
            Intent intent = new Intent(this, HomeScreenActivity.class);
            startActivity(intent);
        } else if(v.equals(scanOrderButton)){
            Intent intent = new Intent(this, ScanActivity.class);
            intent.putExtra(ACCOUNT, account);
            intent.putExtra("order", order);
            startActivity(intent);
        } else if (v.equals(balanceButton)){
            Intent addBalanceIntent = new Intent(this, BalanceActivity.class);
            addBalanceIntent.putExtra(ACCOUNT, account);
            startActivity(addBalanceIntent);
        }
    }
}
