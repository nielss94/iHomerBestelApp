package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.customerapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;

public class UnhandledOrderHistoryDetailActivity extends AppCompatActivity implements ProductSetAvailable {

    private final String TAG = getClass().getSimpleName();
    private Button orderButton;
    private Order order;
    private Account account;
    private Toolbar toolbar;
    private DAOFactory factory;
    private ProductDAO productDAO;
    private ArrayList<Product> productList = new ArrayList<>();
    private ListView orderListView;
    private UnhandledOrderItemAdapter unhandledOrderItemAdapter;
    private Button balanceButton;
    private static final String ACCOUNT = "account";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unhandled_order_history_detail);


        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);
        balanceButton = (Button) findViewById(R.id.buttonBalance);


        order = (Order)getIntent().getSerializableExtra("order");
        account = (Account)getIntent().getSerializableExtra(ACCOUNT);
        balanceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent addBalanceIntent = new Intent(getApplicationContext(), BalanceActivity.class);
                addBalanceIntent.putExtra(ACCOUNT, account);
                startActivity(addBalanceIntent);
            }
        });
        orderButton = (Button)findViewById(R.id.payButton);

        account = (Account)getIntent().getSerializableExtra(ACCOUNT);
        balanceButton.setText("€" + String.format("%.2f", account.getBalance()/100) + "");

        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);

        Double price = order.getTotalPrice();
        String goodPrice = String.format("€%10.2f", price);

        totalPrice.setText(goodPrice);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanIntent = new Intent(getApplicationContext(), ScanActivity.class);
                scanIntent.putExtra(ACCOUNT, account);
                scanIntent.putExtra("order", order);
                startActivity(scanIntent);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.Balance_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnhandledOrderHistoryDetailActivity.this, HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });
    }

    @Override
    public void productSetAvailable(ArrayList<Product> products) {
        productList = products;
        orderListView = (ListView) findViewById(R.id.orderItemListView);
        unhandledOrderItemAdapter = new UnhandledOrderItemAdapter(this, order.getOrderItems(), productList);
        orderListView.setAdapter(unhandledOrderItemAdapter);
    }
}
