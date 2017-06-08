package com.periode4groep2.employeeapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;

public class AddExtraProducts extends AppCompatActivity implements ProductSetAvailable {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<ProductButton> productButtons = new ArrayList<>();
    private LinearLayout layout;
    private DAOFactory factory;
    private ProductDAO productDAO;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products_activity);
        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);

        layout = (LinearLayout) findViewById(R.id.layout_buttonspawner);
        layout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < products.size(); i++) {
            Button button = new Button(this);
            button.setText(products.get(i).getName());

            layout.addView(button);
        }

        for (int i = 0; i < productButtons.size(); i++) {
            final ProductButton pb = productButtons.get(i);

            productButtons.get(i).setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(AddExtraProducts.this, "nummer " + (1 + pb.getProductID()), Toast.LENGTH_SHORT).show();
                    }
                }
            );
        }
    }

    @Override
    public void productSetAvailable(ArrayList<Product> prod) {
        products = prod;
        for (int i = 0; i < products.size() ; i++) {
            Button button = new Button(this);
            button.setText(products.get(i).getName());

            if(products.get(i).getCategory().equals("Fast food")){
                layout.addView(button);
            }
        }
    }
}
