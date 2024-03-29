package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.PersistancyLayer.AccountBalanceAvailable;
import com.periode4groep2.customerapp.PersistancyLayer.AccountDAO;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.OrderDAO;
import com.periode4groep2.customerapp.PersistancyLayer.OrderSetAvailable;
import com.periode4groep2.customerapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.customerapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener, ProductSetAvailable, OrderSetAvailable, AccountBalanceAvailable {
    private LinearLayout orderLayoutButton, settingsLayoutButton,
                  balanceLayoutButton, myOrdersLayoutButton;

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private DAOFactory factory;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;
    private Toolbar toolbar;
    private AccountDAO accountDAO;
    private Account account;
    private Order unhandledOrder;
    private Button balanceButton;
    private ImageButton unhandledOrderButton;
    private AnimationDrawable unhandledOrderAnimation;
    private static final String ACCOUNT = "account";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.Home_Screen_toolbar);
        setSupportActionBar(toolbar);

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        orderDAO = factory.createOrderDAO();
        accountDAO = factory.createAccountDAO();

        productDAO.selectData(this);

        orderLayoutButton = (LinearLayout) findViewById(R.id.OrderID);
        settingsLayoutButton = (LinearLayout) findViewById(R.id.AccountID);
        balanceLayoutButton = (LinearLayout) findViewById(R.id.BalanceID);
        myOrdersLayoutButton = (LinearLayout) findViewById(R.id.MyOrdersID);
        balanceButton = (Button) findViewById(R.id.buttonBalance);
        unhandledOrderButton = (ImageButton) findViewById(R.id.unhandledOrderButton);

        orderLayoutButton.setOnClickListener(this);
        settingsLayoutButton.setOnClickListener(this);
        balanceLayoutButton.setOnClickListener(this);
        myOrdersLayoutButton.setOnClickListener(this);
        balanceButton.setOnClickListener(this);

        account = (Account)getIntent().getSerializableExtra(ACCOUNT);
        balanceButton.setText("€" + String.format("%.2f", account.getBalance()/100) + "");
        Log.i("balance: ", account.getBalance() + "");
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(!getIntent().getClass().getSimpleName().equalsIgnoreCase("MainActivity")){
            accountDAO.getAccountBalance(this,account);
        }
        orderDAO.selectData(this);
    }
    @Override
    public void onClick(View v) {
        if(v.equals(orderLayoutButton)){
            Intent orderIntent = new Intent(this, OrderActivity.class);
            orderIntent.putExtra(ACCOUNT, account);
            startActivity(orderIntent);

        } else if(v.equals(settingsLayoutButton)){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            settingsIntent.putExtra(ACCOUNT, account);
            startActivity(settingsIntent);
            finish();
        } else if(v.equals(balanceLayoutButton)){
            Intent addBalanceIntent = new Intent(this, BalanceActivity.class);
            addBalanceIntent.putExtra(ACCOUNT, account);
            startActivity(addBalanceIntent);
            finish();
        } else if (v.equals(myOrdersLayoutButton)){
            Intent myOrdersIntent = new Intent(this, OrderHistoryActivity.class);
            myOrdersIntent.putExtra(ACCOUNT, account);
            startActivity(myOrdersIntent);

        } else if (v.equals(balanceButton)){
            Intent addBalanceIntent = new Intent(this, BalanceActivity.class);
            addBalanceIntent.putExtra(ACCOUNT, account);
            startActivity(addBalanceIntent);
            finish();
        }
    }


    @Override
    public void orderSetAvailable(ArrayList<Order> orders) {
        this.orders.clear();
        for (int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getEmail().equalsIgnoreCase(account.getEmail())) {
                this.orders.add(orders.get(i));
            }
        }
        checkUnhandledOrder();
    }

    public void checkUnhandledOrder() {
        for (int i = 0; i < orders.size() ; i++) {
            if(orders.get(i).isHandled() == false) {
                unhandledOrder = orders.get(i);
            }
        }

        if(unhandledOrder != null){
            unhandledOrderButton.setVisibility(View.VISIBLE);
            unhandledOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HomeScreenActivity.this, UnhandledOrderHistoryDetailActivity.class);
                    i.putExtra(ACCOUNT, account);
                    i.putExtra("order", unhandledOrder);
                    startActivity(i);
                }
            });
            unhandledOrderButton.setBackgroundResource(R.drawable.popup_unhandled_order);
            unhandledOrderAnimation = (AnimationDrawable) unhandledOrderButton.getBackground();
            int color = Color.parseColor("#FF9600");
            unhandledOrderAnimation.setColorFilter( color, PorterDuff.Mode.MULTIPLY );
            unhandledOrderAnimation.start();
        } else {
            unhandledOrderButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void productSetAvailable(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public void accountBalanceAvailable(Double balance) {
        account.setBalance(balance);
        balanceButton.setText("€" + String.format("%.2f", account.getBalance() / 100) + "");
    }
}

