package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.OrderDAO;
import com.periode4groep2.customerapp.PersistancyLayer.OrderSetAvailable;
import com.periode4groep2.customerapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.customerapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity implements ProductSetAvailable, OrderSetAvailable, AdapterView.OnItemClickListener, View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private DAOFactory factory;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;
    private Toolbar toolbar;
    private Account account;
    private ListView orderHistoryListView;
    private OrderHistoryAdapter historyAdapter;
    private Button balanceButton;
    private static final String ACCOUNT = "account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.Balance_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderHistoryActivity.this, HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        orderDAO = factory.createOrderDAO();

        balanceButton = (Button)findViewById(R.id.buttonBalance);
        balanceButton.setOnClickListener(this);
        account = (Account) getIntent().getSerializableExtra(ACCOUNT);
        balanceButton.setText("€" + String.format("%.2f", account.getBalance()/100) + "");

        orderDAO.selectData(this);
        productDAO.selectData(this);
    }

    @Override
    public void productSetAvailable(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public void orderSetAvailable(ArrayList<Order> orders) {

        for (int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getEmail().equalsIgnoreCase(account.getEmail())) {
                this.orders.add(orders.get(i));
            }
        }
        orderHistoryListView = (ListView) findViewById(R.id.orderHistoryListViewId);
        historyAdapter = new OrderHistoryAdapter(this, this.orders);
        orderHistoryListView.setAdapter(historyAdapter);
        orderHistoryListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == balanceButton){
            Intent addBalanceIntent = new Intent(this, BalanceActivity.class);
            addBalanceIntent.putExtra(ACCOUNT, account);
            startActivity(addBalanceIntent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Order o = (Order) orderHistoryListView.getItemAtPosition(position);
        if (o.isHandled()) {
            Intent orderHistoryDetail = new Intent(this, HandledOrderHistoryDetailActivity.class);
            orderHistoryDetail.putExtra("order", (Order) orderHistoryListView.getItemAtPosition(position));
            orderHistoryDetail.putExtra(ACCOUNT, account);

            startActivity(orderHistoryDetail);
        } else {
            Intent orderHistoryDetail = new Intent(this, UnhandledOrderHistoryDetailActivity.class);
            orderHistoryDetail.putExtra("order", (Order) orderHistoryListView.getItemAtPosition(position));
            orderHistoryDetail.putExtra(ACCOUNT, account);

            startActivity(orderHistoryDetail);
        }
    }
}
