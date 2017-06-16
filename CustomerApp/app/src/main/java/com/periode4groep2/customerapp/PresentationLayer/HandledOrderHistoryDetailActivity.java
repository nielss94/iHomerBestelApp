package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class HandledOrderHistoryDetailActivity extends AppCompatActivity implements ProductSetAvailable, OrderSetAvailable {

    private Button orderButton, balanceButton;
    private Order order;
    private Account account;
    private DAOFactory factory;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;
    private Toolbar toolbar;
    private ArrayList<Product> productList = new ArrayList<>();
    private ListView orderListView;
    private HandledOrderItemAdapter handledOrderItemAdapter;
    private static final String ACCOUNT = "account";
    private ArrayList<Order> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handled_order_history_detail);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.handled_Order_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HandledOrderHistoryDetailActivity.this, HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);
        orderDAO = factory.createOrderDAO();
        orderDAO.selectData(this);
        order = (Order) getIntent().getSerializableExtra("order");
        account = (Account) getIntent().getSerializableExtra(ACCOUNT);

        String date = order.getDate();
        String fullDate = date.substring(0,10);
        String fullTime = date.substring(11,16);

        TextView orderTitle = (TextView) findViewById(R.id.orderTitle);
        TextView orderTime = (TextView) findViewById(R.id.orderTime);

        orderTitle.setText("Bestelling " + fullDate);
        orderTime.setText(fullTime);

        orderButton = (Button) findViewById(R.id.orderAgainButton);
        balanceButton = (Button)findViewById(R.id.buttonBalance);
        orderButton.setText("Bestel opnieuw");

        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);

        Double price = order.getTotalPrice();
        String goodPrice = String.format("€%10.2f", price);

        totalPrice.setText(goodPrice);

        balanceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent addBalanceIntent = new Intent(getApplicationContext(), BalanceActivity.class);
                addBalanceIntent.putExtra(ACCOUNT, account);
                startActivity(addBalanceIntent);
            }
        });

        balanceButton.setText("€" + String.format("%.2f", account.getBalance()/100) + "");

        orderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("hoi", "hoi");
                Boolean canCreateOrder = true;
                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i).isHandled() == false && orders.get(i).getEmail().equals(account.getEmail())) {
                        canCreateOrder = false;
                        break;
                    }
                }
                if (canCreateOrder) {
                    orderDAO.insertData(account,order);
                    Intent scanIntent = new Intent(getApplicationContext(), ScanActivity.class);
                    scanIntent.putExtra(ACCOUNT, account);
                    scanIntent.putExtra("order", order);
                    startActivity(scanIntent);
                } else {
                    Toast.makeText(HandledOrderHistoryDetailActivity.this, "U heeft al een openstaande bestelling.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void productSetAvailable(ArrayList<Product> products) {
        productList = products;
        orderListView = (ListView) findViewById(R.id.orderItemListView);
        handledOrderItemAdapter = new HandledOrderItemAdapter(this, order.getOrderItems(), productList);
        orderListView.setAdapter(handledOrderItemAdapter);
    }

    @Override
    public void orderSetAvailable(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
