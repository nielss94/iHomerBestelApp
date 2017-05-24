package com.periode4groep2.customerapp.PresentationLayer;

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
import com.periode4groep2.customerapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.customerapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;

public class HandledOrderHistoryDetailActivity extends AppCompatActivity implements ProductSetAvailable {

    private Button orderButton;
    private Order order;
    private Account account;
    private DAOFactory factory;
    private ProductDAO productDAO;
    private ArrayList<Product> productList = new ArrayList<>();
    private ListView orderListView;
    private HandledOrderItemAdapter handledOrderItemAdapter;

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


        order = (Order) getIntent().getSerializableExtra("order");
        account = (Account) getIntent().getSerializableExtra("account");

        orderButton = (Button) findViewById(R.id.orderAgainButton);

        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
        totalPrice.setText("€" + order.getTotalPrice());

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
