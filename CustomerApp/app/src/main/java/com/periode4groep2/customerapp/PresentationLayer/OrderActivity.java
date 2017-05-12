package com.periode4groep2.customerapp.PresentationLayer;
//http://stackoverflow.com/questions/9824074/android-expandablelistview-looking-for-a-tutorial

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.customerapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, ProductSetAvailable{

    private final String TAG = getClass().getSimpleName();
    private ImageView basket;
    private Account account;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<String> listDataHeader = new ArrayList<>();
    private HashMap<String,ArrayList<String>> listHash = new HashMap<>();;
    private DAOFactory factory;
    private ProductDAO productDAO;
    private ArrayList<Product> products = new ArrayList<>();
    private Product product;
    private Button balanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        basket = (ImageView)findViewById(R.id.order_basket);
        basket.setOnClickListener(this);
        balanceButton = (Button)findViewById(R.id.buttonBalance);
        balanceButton.setOnClickListener(this);
        account = (Account)getIntent().getSerializableExtra("account");
        balanceButton.setText("€" + String.format("%.2f" ,account.getBalance()/100) + "");

        listView = (ExpandableListView)findViewById(R.id.expandableListId);

        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);
    }

    public void productSetAvailable(ArrayList<Product> prod){
        products = prod;
        for (int i = 0; i < products.size(); i++) {
            Log.i(TAG,products.get(i).toString());
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
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                if(listDataHeader.get(j).equalsIgnoreCase(products.get(i).getCategory())){
                    list.add(products.get(i).getName());
                    listHash.put(listDataHeader.get(j), list);
                }
            }
        }
        listAdapter.notifyDataSetChanged();
    }

    public void onClick(View v){
        if(v.equals(balanceButton)){
            Intent addBalanceIntent = new Intent(this, AddBalanceActivity.class);
            addBalanceIntent.putExtra("account", account);
            startActivity(addBalanceIntent);
        } else if (v.equals(basket)) {
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        }
    }
}
