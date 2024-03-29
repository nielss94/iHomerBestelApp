package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.DomainModel.OrderItem;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.OrderDAO;
import com.periode4groep2.customerapp.PersistancyLayer.OrderSetAvailable;
import com.periode4groep2.customerapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.customerapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, ProductSetAvailable, OrderSetAvailable, ExpandableListAdapter.OnOrderChanged{

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
    private Toolbar toolbar;
    private ImageButton popUpButton;
    private TextView orderItemCount;
    private ListPopupWindow menu;
    private static final String ACCOUNT = "account";
    private Boolean menuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.Order_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);
        orderDAO = factory.createOrderDAO();

        orderDAO.selectData(this);

        totalOrderPrice = (TextView)findViewById(R.id.totalOrderPrice);
        orderItemCount = (TextView)findViewById(R.id.itemCount);
        orderItemCount.setText("0");
        basket = (TextView) findViewById(R.id.orderButton);
        basket.setOnClickListener(this);

        balanceButton = (Button) findViewById(R.id.buttonBalance);
        balanceButton.setOnClickListener(this);
        account = (Account) getIntent().getSerializableExtra(ACCOUNT);
        balanceButton.setText("€" + String.format("%.2f", account.getBalance() / 100) + "");

        popUpButton = (ImageButton) findViewById(R.id.popUpButton);
        popUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuOpen){
                    menu.dismiss();
                    menuOpen = false;
                }else{
                    menu.show();
                    menuOpen = true;
                }
            }
        });
        listView = (ExpandableListView) findViewById(R.id.expandableListId);

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash, this);
        listView.setAdapter(listAdapter);
    }

    public void productSetAvailable(ArrayList<Product> prod) {
        products = prod;
        for (int i = 0; i < products.size(); i++) {
            Log.i(TAG, products.get(i).toString());
        }
        initData();
        newOrder = new Order(1, account.getEmail(), false, 0.00, "2017-05-17"); menu = new ListPopupWindow(OrderActivity.this);
        menu.setAdapter(new PopupMenuAdapter(OrderActivity.this, newOrder.getOrderItems(), products));
        menu.setAnchorView(popUpButton);
        menu.setWidth(900);
        menu.setHeight(1000);
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
            addBalanceIntent.putExtra(ACCOUNT, account);
            startActivity(addBalanceIntent);
        } else if (v.equals(basket)) {
            Boolean canCreateOrder = true;
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).isHandled() == false && orders.get(i).getEmail().equals(account.getEmail())) {
                    Toast.makeText(this, "U heeft al een openstaande bestelling.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            if(newOrder.getOrderItems().size() < 1){
                Toast.makeText(this, "U moet tenminste 1 product in uw mandje zitten.", Toast.LENGTH_LONG).show();
                return;
            }
            Log.i(TAG, String.valueOf(newOrder.getTotalPrice()) + " " + account.getBalance());
            if(newOrder.getTotalPrice() > account.getBalance() / 100){
                Toast.makeText(this, "U heeft niet genoeg saldo.", Toast.LENGTH_LONG).show();
                return;
            }
            if (canCreateOrder) {
                orderDAO.insertData(account, newOrder);
                Intent intent = new Intent(this, OrderDetailActivity.class);
                intent.putExtra(ACCOUNT, account);
                Bundle b = new Bundle();
                b.putParcelableArrayList("products", products);
                intent.putExtras(b);
                startActivity(intent);
            }
        }
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


        for (int i = 0; i < newOrder.getOrderItems().size(); i++) {
            if(newOrder.getOrderItems().get(i).getProductID() == orderItem.getProductID()){
                newOrder.getOrderItems().get(i).setQuantity(newOrder.getOrderItems().get(i).getQuantity() + orderItem.getQuantity());
                if(newOrder.getOrderItems().get(i).getQuantity() == 0){
                    newOrder.getOrderItems().remove(i);
                }
                break;
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
