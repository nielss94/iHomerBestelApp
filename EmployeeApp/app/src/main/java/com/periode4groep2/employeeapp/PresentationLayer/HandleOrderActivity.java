package com.periode4groep2.employeeapp.PresentationLayer;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.periode4groep2.employeeapp.CardReader.LoyaltyCardReader;
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

public class HandleOrderActivity extends AppCompatActivity implements LoyaltyCardReader.AccountCallback, OrderSetAvailable, ProductSetAvailable {
    private Toolbar toolbar;
    public static final String TAG = "CardReaderFragment";
    // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
    // activity is interested in NFC-A devices (including other Android devices), and that the
    // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
    public LoyaltyCardReader mLoyaltyCardReader;
    private TextView test, test2;
    private Button acceptOrderButton;
    private DAOFactory factory;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private Order order;
    private ArrayList<Order> orders = new ArrayList<>();
    private Account account;

    //orders ophalen voor jordanus
    private ListView orderListView;
    private ArrayList<Product> productlist = new ArrayList<>();
    private ReceivedOrderAdapter receivedOrderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_order);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        setSupportActionBar(toolbar);

        test = (TextView) findViewById(R.id.totalTagTextView);
        test2 = (TextView) findViewById(R.id.totalPriceTagTextView);
        mLoyaltyCardReader = new LoyaltyCardReader(this);

        // Disable Android Beam and register our card reader callback
        //enableReaderMode();

        account = (Account)getIntent().getSerializableExtra("account");
        acceptOrderButton = (Button)findViewById(R.id.acceptOrderButton);
        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);
        orderDAO = factory.createOrderDAO();
        orderDAO.selectData(this);
        acceptOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order != null){
                    orderDAO.handleOrder(account, order);
                    order = null;
                }
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        disableReaderMode();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableReaderMode();
    }

    private void enableReaderMode() {
        Log.i(TAG, "Enabling reader mode");
        Activity activity = this;
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.enableReaderMode(activity, mLoyaltyCardReader, READER_FLAGS, null);
        }
    }

    private void disableReaderMode() {
        Log.i(TAG, "Disabling reader mode");
        Activity activity = this;
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.disableReaderMode(activity);
        }
    }

    @Override
    public void onAccountReceived(final String account) {
        // This callback is run on a background thread, but updates to UI elements must be performed
        // on the UI thread.
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                test.setText(account);
            }
        });

        try{
            int orderID = Integer.parseInt(account);
            for (int i = 0; i < orders.size(); i++) {
                if(orders.get(i).getOrderID() == orderID){
                    order = orders.get(i);
                }
            }
        } catch (NumberFormatException e){
            Log.i(TAG, e.getMessage());
        }
    }

    @Override
    public void orderSetAvailable(ArrayList<Order> orders) {
        this.orders = orders;

    }

    @Override
    public void productSetAvailable(ArrayList<Product> products) {
        //productlist.clear();
        productlist = products;
        orderListView = (ListView) findViewById(R.id.listview_show_orders);
        receivedOrderAdapter = new ReceivedOrderAdapter(this, order.getOrderItems(), productlist);
        orderListView.setAdapter(receivedOrderAdapter);
        //receivedOrderAdapter.notifyDataSetChanged();
    }
}
