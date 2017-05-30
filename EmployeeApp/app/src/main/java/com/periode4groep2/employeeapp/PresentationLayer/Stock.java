package com.periode4groep2.employeeapp.PresentationLayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;



import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Order;
import com.periode4groep2.employeeapp.DomainModel.OrderItem;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.OrderDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.OrderSetAvailable;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Stock extends AppCompatActivity implements View.OnClickListener, ProductSetAvailable, OrderSetAvailable {

    private final String TAG = getClass().getSimpleName();
    private Account account;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<String> listDataHeader = new ArrayList<>();
    private HashMap<String, ArrayList<Product>> listHash = new HashMap<>();
    private DAOFactory factory;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;
    private ArrayList<Product> products = new ArrayList<>();
    private Product product;
    private Button balanceButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_layout);



        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);
        orderDAO = factory.createOrderDAO();

        orderDAO.selectData(this);



        balanceButton = (Button) findViewById(R.id.buttonBalance);
        balanceButton.setOnClickListener(this);
        account = (Account) getIntent().getSerializableExtra("account");
        balanceButton.setText("€" + String.format("%.2f", account.getBalance() / 100) + "");


        listView = (ExpandableListView) findViewById(R.id.expandableListId);



        listView.setAdapter(listAdapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Product product = (Product) listView.getExpandableListAdapter().getChild(groupPosition, childPosition);

                Log.i(TAG, "Click on item " + product.getName() + " " + product.getPrice());
                OrderItem oi = new OrderItem(product.getProductID(), 1);



                return true;
            }
        });

    }

    public void productSetAvailable(ArrayList<Product> prod) {
        products = prod;
        for (int i = 0; i < products.size(); i++) {
            Log.i(TAG, products.get(i).toString());
        }
        initData();
    }


    private void initData() {
        for (int i = 0; i < products.size(); i++) {
            product = products.get(i);

            if (!listDataHeader.contains(product.getCategory())) {
                listDataHeader.add(product.getCategory());
                Log.i(TAG, product.getCategory() + " added to categories");
            }
        }
        for (int j = 0; j < listDataHeader.size(); j++) {
            ArrayList<Product> list = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                if (listDataHeader.get(j).equalsIgnoreCase(products.get(i).getCategory())) {
                    list.add(products.get(i));
                    listHash.put(listDataHeader.get(j), list);
                }
            }
        }

    }
}

