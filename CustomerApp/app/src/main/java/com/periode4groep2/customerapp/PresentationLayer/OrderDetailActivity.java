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
import com.periode4groep2.customerapp.PersistancyLayer.OrderDAO;
import com.periode4groep2.customerapp.PersistancyLayer.OrderSetAvailable;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener, OrderSetAvailable {
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
    private DAOFactory factory;
    private Toolbar toolbar;
    private OrderDAO orderDAO;
    private Account account;
    private Order order;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.Balance_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this, HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        factory = new MySQLDAOFactory();
        orderDAO = factory.createOrderDAO();

        Bundle b = this.getIntent().getExtras();
        products = b.getParcelableArrayList("products");
        account = (Account)getIntent().getSerializableExtra("account");
        //order = (Order)getIntent().getSerializableExtra("order");

        orderDAO.selectData(this);
        orderItemListView = (ListView)findViewById(R.id.orderItemListView);

        totalTagTextView = (TextView)findViewById(R.id.totalTagTextView);
        totalPriceTextView = (TextView)findViewById(R.id.totalPriceTagTextView);

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
            orderDAO.deleteOrder(account, order);
            Intent intent = new Intent(this, HomeScreenActivity.class);
            startActivity(intent);
        } else if(v.equals(scanOrderButton)){
            Intent intent = new Intent(this, ScanActivity.class);
            intent.putExtra("account", account);
            intent.putExtra("order", order);
            startActivity(intent);
        } else if (v.equals(balanceButton)){
            Intent addBalanceIntent = new Intent(this, BalanceActivity.class);
            addBalanceIntent.putExtra("account", account);
            startActivity(addBalanceIntent);
        }
    }

    @Override
    public void orderSetAvailable(ArrayList<Order> orders) {
        for (int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getEmail().equals(account.getEmail()) && (!orders.get(i).isHandled())){
                order = orders.get(i);
                break;
            }
        }
        unhandledOrderItemAdapter = new UnhandledOrderItemAdapter(this, order.getOrderItems(), products);
        orderItemListView.setAdapter(unhandledOrderItemAdapter);
        unhandledOrderItemAdapter.notifyDataSetChanged();
        totalPriceTextView.setText("€"+String.format("%.2f" ,order.getTotalPrice()));
    }
}
