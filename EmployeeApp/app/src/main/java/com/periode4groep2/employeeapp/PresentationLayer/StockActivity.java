package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class StockActivity extends AppCompatActivity implements View.OnClickListener, ProductSetAvailable{

    private final String TAG = getClass().getSimpleName();
    private Account account;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<String> listDataHeader = new ArrayList<>();
    private HashMap<String, ArrayList<Product>> listHash = new HashMap<>();
    private DAOFactory factory;
    private Toolbar toolbar;
    private Button addProduct, removeProduct;
    private ProductDAO productDAO;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_layout);

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_homebutton);
        toolbar.setNavigationIcon(homeButton);

        toolbar.setTitle(R.string.employee_stock_activity);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockActivity.this, HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });
        account = (Account) getIntent().getSerializableExtra("account");
        listView = (ExpandableListView) findViewById(R.id.expandableListId);

        addProduct = (Button) findViewById(R.id.stock_add_product);
        removeProduct = (Button) findViewById(R.id.stock_delete_product);
        addProduct.setOnClickListener(this);
        removeProduct.setOnClickListener(this);
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
                    v.setBackgroundColor(getResources().getColor(R.color.colorDarkRed));
                    listAdapter.notifyDataSetChanged();
                    productDAO.changeStock(account,product);
                    product.setInStock(false);
                }else{
                    v.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                    listAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {

        if (v.equals(addProduct)){
            Intent intent = new Intent(this, AddStockProduct.class);
            intent.putExtra("account", account);
            startActivity(intent);
        } else if (v.equals(removeProduct)){
            Intent intent = new Intent(this, RemoveStockProduct.class);
            intent.putExtra("account", account);
            startActivity(intent);
        }
    }
}

