package com.periode4groep2.employeeapp.PresentationLayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
import android.support.v7.widget.Toolbar;
import android.util.Log;
=======
>>>>>>> origin/master
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
    //implements View.OnClickListener, OrderSetAvailable
    private final String TAG = getClass().getSimpleName();

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private DAOFactory factory;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;
    private Account account;

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

        orderDAO.selectData(this);
        productDAO.selectData(this);

        account = (Account)getIntent().getSerializableExtra("account");

        searchOrderEditText = (EditText)findViewById(R.id.searchOrderEditText);
        //searchOrderListView = (ListView)findViewById(R.id.searchedOrdersListView);
        searchOrderButton = (Button)findViewById(R.id.searchOrderButton);

        searchOrderButton.setOnClickListener(this);
        searchOrderListView.setOnItemClickListener(this);

    }
    @Override
    public void onClick(View v){
        if(v.equals(searchOrderButton)){
            String entry_str = searchOrderEditText.getText().toString();
            //Do something to send string via API.
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Order o = (Order) searchOrderListView.getItemAtPosition(position);

    }

    @Override
    public void orderSetAvailable(ArrayList<Order> orders) {
        for (int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getEmail().equalsIgnoreCase(account.getEmail())) {
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
