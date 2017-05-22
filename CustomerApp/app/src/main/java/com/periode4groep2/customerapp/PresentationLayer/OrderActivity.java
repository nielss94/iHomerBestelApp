package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.DomainModel.OrderItem;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.customerapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, ProductSetAvailable, ExpandableListAdapter.OnOrderChanged {

    private final String TAG = getClass().getSimpleName();
    private ImageView basket;
    private Account account;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<String> listDataHeader = new ArrayList<>();
    private HashMap<String, ArrayList<Product>> listHash = new HashMap<>();
    private DAOFactory factory;
    private ProductDAO productDAO;
    private ArrayList<Product> products = new ArrayList<>();
    private Product product;
    private Button balanceButton;
    private TextView totalOrderPrice;
    private Order newOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);

        totalOrderPrice = (TextView)findViewById(R.id.totalOrderPrice);
        basket = (ImageView) findViewById(R.id.order_basket);
        basket.setOnClickListener(this);

        balanceButton = (Button) findViewById(R.id.buttonBalance);
        balanceButton.setOnClickListener(this);
        account = (Account) getIntent().getSerializableExtra("account");
        balanceButton.setText("€" + String.format("%.2f", account.getBalance() / 100) + "");

        listView = (ExpandableListView) findViewById(R.id.expandableListId);

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash, this);

        listView.setAdapter(listAdapter);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Doing nothing
                return true;
            }
        });
        newOrder = new Order(1, account.getEmail(), false, 0.00, "2017-05-17");
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
        listAdapter.notifyDataSetChanged();
    }

    public void onClick(View v) {
        if (v.equals(balanceButton)) {
            Intent addBalanceIntent = new Intent(this, BalanceActivity.class);
            addBalanceIntent.putExtra("account", account);
            startActivity(addBalanceIntent);
        } else if (v.equals(basket)) {
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra("account", account);
            intent.putExtra("order",newOrder);
            startActivity(intent);
        }
    }

    @Override
    public void onOrderChanged(double newPrice, OrderItem orderItem) {
        newOrder.setTotalPrice(newOrder.getTotalPrice() + newPrice);
        totalOrderPrice.setText("€" + String.format("%.2f", newOrder.getTotalPrice()));
        ArrayList<Integer> productIDsInOrderItems = new ArrayList<>();

        for (int i = 0; i < newOrder.getOrderItems().size(); i++) {
            productIDsInOrderItems.add(newOrder.getOrderItems().get(i).getProductID());
        }

        if(!productIDsInOrderItems.contains(orderItem.getProductID())){
            newOrder.getOrderItems().add(orderItem);
        }
        else{
            for (int i = 0; i < newOrder.getOrderItems().size(); i++) {
                if(newOrder.getOrderItems().get(i).getProductID() == orderItem.getProductID()){
                    newOrder.getOrderItems().get(i).setQuantity(newOrder.getOrderItems().get(i).getQuantity() + orderItem.getQuantity());
                    break;
                }
            }
        }

        Log.i(TAG,newOrder.toString());
    }
}
