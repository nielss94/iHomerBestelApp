package com.periode4groep2.employeeapp.PresentationLayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;


import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.OrderItem;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.OrderDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class StockActivity extends AppCompatActivity implements ProductSetAvailable{

    private final String TAG = getClass().getSimpleName();
    private Account account;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<String> listDataHeader = new ArrayList<>();
    private HashMap<String, ArrayList<Product>> listHash = new HashMap<>();
    private DAOFactory factory;
    private ProductDAO productDAO;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_layout);

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);

        account = (Account) getIntent().getSerializableExtra("account");
        listView = (ExpandableListView) findViewById(R.id.expandableListId);
    }

    public void productSetAvailable(final ArrayList<Product> prod) {
        products = prod;
        for (int i = 0; i < products.size(); i++) {
            Log.i(TAG, products.get(i).toString());
        }
        initData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Product product = (Product) listView.getExpandableListAdapter().getChild(groupPosition, childPosition);

                Log.i(TAG, "Click on item " + product.getName());
                Log.i(TAG,product.isInStock()+"");
                if(product.isInStock()){
                    v.setBackgroundColor(getResources().getColor(R.color.colorGrayWhite));
                    productDAO.changeStock(account,product);
                    product.setInStock(false);
                }else{
                    v.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                    productDAO.changeStock(account,product);
                    product.setInStock(true);
                }
                return true;
            }
        });
    }


    private void initData() {

        Product product;
        for (int i = 0; i < products.size(); i++) {
            product = products.get(i);

            if (!listDataHeader.contains(product.getCategory())) {
                listDataHeader.add(product.getCategory());
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

