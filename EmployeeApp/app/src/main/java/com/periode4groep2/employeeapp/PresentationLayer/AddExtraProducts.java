package com.periode4groep2.employeeapp.PresentationLayer;

/**
 * Created by J.h2os on 31-5-2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class AddExtraProducts extends AppCompatActivity implements ProductSetAvailable, OrderSetAvailable, ExpandableListAdapter.OnOrderChanged{

    private final String TAG = getClass().getSimpleName();
    private TextView basket;
    private Account account;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<String> listDataHeader = new ArrayList<>();
    private HashMap<String, ArrayList<Product>> listHash = new HashMap<>();
    private DAOFactory factory;
    private ProductDAO productDAO;
    private ArrayList<Order> orders = new ArrayList<>();
    private OrderDAO orderDAO;
    private ArrayList<Product> products = new ArrayList<>();
    private Product product;
    private Button balanceButton;
    private TextView totalOrderPrice;
    private Order newOrder;
    private ImageButton popUpButton;
    private TextView orderItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_extra_product);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        myToolbar.setTitle(R.string.order_toolbar);
        setSupportActionBar(myToolbar);

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);
        orderDAO = factory.createOrderDAO();

        orderDAO.selectData(this);

        totalOrderPrice = (TextView)findViewById(R.id.totalOrderPrice);
        orderItemCount = (TextView)findViewById(R.id.itemCount);
        orderItemCount.setText("0");


        popUpButton = (ImageButton) findViewById(R.id.popUpButton);
        popUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(AddExtraProducts.this, v);

                for (int i = 0; i < products.size(); i++) {
                    for (int j = 0; j < newOrder.getOrderItems().size(); j++) {
                        if (products.get(i).getProductID() == newOrder.getOrderItems().get(j).getProductID()) {

                            Log.i(TAG, "New menu item pls");
                            menu.getMenu().add(products.get(i).getName() + " " + newOrder.getOrderItems().get(j).getQuantity());
                        }
                    }
                }
                menu.show();
            }
        });
        listView = (ExpandableListView) findViewById(R.id.expandableListId);

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash, this);
        listView.setAdapter(listAdapter);


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


    public void addOrderitem(double newPrice, OrderItem orderItem) {
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

        Log.i(TAG,newOrder.getOrderItems().toString());
    }
    public void removeOrderitem(double newPrice, OrderItem orderItem) {
        newOrder.setTotalPrice(newOrder.getTotalPrice() - newPrice);
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

        Log.i(TAG,newOrder.getOrderItems().toString());
    }
    @Override
    public void orderSetAvailable(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void onOrderChanged(Product product, int quantity) {
        if(quantity > 0){
            addOrderitem(product.getPrice(), new OrderItem(product.getProductID(), quantity));
            int i = 0;
            for (int j = 0; j < newOrder.getOrderItems().size(); j++) {
                i += newOrder.getOrderItems().get(j).getQuantity();
            }
            orderItemCount.setText(i + "");
        }else if(quantity < 0){
            removeOrderitem(product.getPrice(), new OrderItem(product.getProductID(), quantity));
            int i = 0;
            for (int j = 0; j < newOrder.getOrderItems().size(); j++) {
                i += newOrder.getOrderItems().get(j).getQuantity();
            }
            orderItemCount.setText(i + "");
        }else {
            return;
        }
    }
}