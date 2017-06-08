package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.os.Bundle;
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
import com.periode4groep2.customerapp.PersistancyLayer.OrderDAO;
import com.periode4groep2.customerapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.customerapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;

public class HandledOrderHistoryDetailActivity extends AppCompatActivity implements ProductSetAvailable {

    private Button orderButton, balanceButton;
    private Order order;
    private Account account;
    private DAOFactory factory;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;
    private ArrayList<Product> productList = new ArrayList<>();
    private ListView orderListView;
    private HandledOrderItemAdapter handledOrderItemAdapter;
    private static final String ACCOUNT = "account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handled_order_history_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        myToolbar.setTitle("Handled Bestelling");
        setSupportActionBar(myToolbar);

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);
        orderDAO = factory.createOrderDAO();
        order = (Order) getIntent().getSerializableExtra("order");
        account = (Account) getIntent().getSerializableExtra(ACCOUNT);

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
                orderDAO.insertData(account,order);
                Intent scanIntent = new Intent(getApplicationContext(), ScanActivity.class);
                scanIntent.putExtra(ACCOUNT, account);
                scanIntent.putExtra("order", order);
                startActivity(scanIntent);
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
}
