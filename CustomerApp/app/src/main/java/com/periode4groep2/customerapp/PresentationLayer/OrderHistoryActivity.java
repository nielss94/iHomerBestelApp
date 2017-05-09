package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.customerapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity implements ProductSetAvailable{

    private final String TAG = getClass().getSimpleName();
    private ArrayList<Product> products = new ArrayList<>();
    private DAOFactory factory;
    private ProductDAO productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();

        productDAO.selectData(this);
    }

    @Override
    public void productSetAvailable(ArrayList<Product> products) {
        this.products = products;
        for (int i = 0; i < this.products.size(); i++) {
            Log.i(TAG, this.products.get(i).toString());
        }
    }
}
