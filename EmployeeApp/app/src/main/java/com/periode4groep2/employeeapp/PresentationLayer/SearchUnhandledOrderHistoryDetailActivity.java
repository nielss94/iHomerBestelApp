package com.periode4groep2.employeeapp.PresentationLayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Order;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;

public class SearchUnhandledOrderHistoryDetailActivity extends AppCompatActivity implements ProductSetAvailable {

    private final String TAG = getClass().getSimpleName();
    private Order order;
    private Account account;
    private DAOFactory factory;
    private ProductDAO productDAO;
    private ArrayList<Product> productList = new ArrayList<>();
    private ListView orderListView;
    private SearchUnhandledOrderItemAdapter unhandledOrderItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_unhandled_order_history_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        myToolbar.setTitle(R.string.Unhandled_Order_toolbar);
        setSupportActionBar(myToolbar);

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);

        order = (Order)getIntent().getSerializableExtra("order");
        account = (Account)getIntent().getSerializableExtra("account");
        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);

        Double price = order.getTotalPrice();
        String goodPrice = String.format("€%10.2f", price);

        totalPrice.setText(goodPrice);
    }

    @Override
    public void productSetAvailable(ArrayList<Product> products) {
        productList = products;
        orderListView = (ListView) findViewById(R.id.orderItemListView);
        unhandledOrderItemAdapter = new SearchUnhandledOrderItemAdapter(this, order.getOrderItems(), productList);
        orderListView.setAdapter(unhandledOrderItemAdapter);
    }
}
