package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Order;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.OrderDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.OrderSetAvailable;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;

public class SearchOrderActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, OrderSetAvailable, ProductSetAvailable{

    private final String TAG = getClass().getSimpleName();

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private DAOFactory factory;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;
    private Account account;
    private Toolbar toolbar;
    private SearchOrderHistoryAdapter searchOrderHistoryAdapter;
    private EditText searchOrderEditText;
    private Button searchOrderButton;
    private ListView searchOrderListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_order);

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        orderDAO = factory.createOrderDAO();

        productDAO.selectData(this);

        account = (Account)getIntent().getSerializableExtra("account");

        searchOrderEditText = (EditText)findViewById(R.id.searchOrderEditText);
        searchOrderListView = (ListView)findViewById(R.id.searchOrdersListView);
        searchOrderButton = (Button)findViewById(R.id.searchOrderButton);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_homebutton);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.search_order_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchOrderActivity.this, HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        searchOrderButton.setOnClickListener(this);
        searchOrderListView.setOnItemClickListener(this);

    }
    @Override
    public void onClick(View v){
        if(v.equals(searchOrderButton)){
            orderDAO.selectData(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Order o = (Order) searchOrderListView.getItemAtPosition(position);
        if(o.isHandled()){
            Intent i = new Intent(this, SearchHandledOrderHistoryDetailActivity.class);
            i.putExtra("order", o);
            startActivity(i);
        }else{
            Intent i = new Intent(this, SearchUnhandledOrderHistoryDetailActivity.class);
            i.putExtra("order", o);
            startActivity(i);
        }
    }

    @Override
    public void orderSetAvailable(ArrayList<Order> orders) {
        String searchQuery = searchOrderEditText.getText().toString();
        this.orders.clear();
        for (int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getEmail().equalsIgnoreCase(searchQuery)) {
                this.orders.add(orders.get(i));
            }
        }
        Log.i(TAG, this.orders.size() +"");
        if (this.orders.size() == 0) {
            Toast.makeText(this, "E-mailadres niet gevonden, probeer het opnieuw.", Toast.LENGTH_SHORT).show();
        }
        searchOrderListView = (ListView) findViewById(R.id.searchOrdersListView);
        searchOrderHistoryAdapter = new SearchOrderHistoryAdapter(this, this.orders);
        searchOrderListView.setAdapter(searchOrderHistoryAdapter);
        searchOrderListView.setOnItemClickListener(this);
    }

    @Override
    public void productSetAvailable(ArrayList<Product> products) {
        this.products = products;
    }
}
