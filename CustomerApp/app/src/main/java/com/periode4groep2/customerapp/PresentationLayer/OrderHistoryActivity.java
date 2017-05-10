package com.periode4groep2.customerapp.PresentationLayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.OrderDAO;
import com.periode4groep2.customerapp.PersistancyLayer.OrderSetAvailable;
import com.periode4groep2.customerapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.customerapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity implements ProductSetAvailable, OrderSetAvailable{

    private final String TAG = getClass().getSimpleName();
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private DAOFactory factory;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        orderDAO = factory.createOrderDAO();

        orderDAO.selectData(this);
        productDAO.selectData(this);
    }

    @Override
    public void productSetAvailable(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public void orderSetAvailable(ArrayList<Order> orders) {
        this.orders = orders;
        for (int i = 0; i < this.orders.size(); i++) {
            Log.i(TAG, this.orders.get(i).toString());
            for (int j = 0; j < this.orders.get(i).getOrderItems().size() ; j++) {
                Log.i(TAG, this.orders.get(i).getOrderItems().get(j).toString());
            }
        }
    }
}
