package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Order;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.OrderDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;


public class SearchHandledOrderHistoryDetailActivity extends AppCompatActivity implements ProductSetAvailable {

    private Order order;
    private Account account;
    private DAOFactory factory;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;
    private Toolbar myToolbar;
    private ArrayList<Product> productList = new ArrayList<>();
    private ListView orderListView;
    private SearchHandledOrderItemAdapter handledOrderItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_handled_order_history_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        myToolbar.setTitle("Handled Bestelling");
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchHandledOrderHistoryDetailActivity.this, HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);
        orderDAO = factory.createOrderDAO();
        order = (Order) getIntent().getSerializableExtra("order");
        account = (Account) getIntent().getSerializableExtra("account");

        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);

        Double price = order.getTotalPrice();
        String goodPrice = String.format("€%10.2f", price);

        totalPrice.setText(goodPrice);
    }

    @Override
    public void productSetAvailable(ArrayList<Product> products) {
        productList = products;
        orderListView = (ListView) findViewById(R.id.orderItemListView);
        handledOrderItemAdapter = new SearchHandledOrderItemAdapter(this, order.getOrderItems(), productList);
        orderListView.setAdapter(handledOrderItemAdapter);
    }
}
