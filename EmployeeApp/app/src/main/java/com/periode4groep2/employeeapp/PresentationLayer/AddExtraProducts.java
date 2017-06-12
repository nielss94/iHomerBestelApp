package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.periode4groep2.employeeapp.DomainModel.Order;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;

public class AddExtraProducts extends AppCompatActivity implements ProductSetAvailable, View.OnClickListener {
    private ArrayList<ProductButton> productButtons = new ArrayList<>();
    private LinearLayout layoutSoda, layoutFastFood, layoutWater, layoutBread, layoutAlchohol, layoutSnacks;
    private DAOFactory factory;
    private ProductDAO productDAO;
    private ArrayList<Product> products = new ArrayList<>();
    private Order order;
    private Button saveOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products_activity);
        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);
        order = (Order)getIntent().getSerializableExtra("order");
        saveOrderButton = (Button)findViewById(R.id.backButton);
        saveOrderButton.setOnClickListener(this);

        layoutSoda = (LinearLayout) findViewById(R.id.layout_buttonspawnerSoda);
        layoutAlchohol = (LinearLayout)findViewById(R.id.layout_buttonspawnerAlchohol);
        layoutSnacks = (LinearLayout)findViewById(R.id.layout_buttonspawnerSnacks);
        layoutFastFood = (LinearLayout)findViewById(R.id.layout_buttonspawnerFastFood);
        layoutBread = (LinearLayout)findViewById(R.id.layout_buttonspawnerBread);
        layoutWater = (LinearLayout)findViewById(R.id.layout_buttonspawnerWater);

        layoutSoda.setOrientation(LinearLayout.VERTICAL);
        layoutAlchohol.setOrientation(LinearLayout.VERTICAL);
        layoutSnacks.setOrientation(LinearLayout.VERTICAL);
        layoutFastFood.setOrientation(LinearLayout.VERTICAL);
        layoutBread.setOrientation(LinearLayout.VERTICAL);
        layoutWater.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < products.size(); i++) {
            ProductButton button = new ProductButton(this);
            button.setText(products.get(i).getName());

        }
    }

    @Override
    public void productSetAvailable(ArrayList<Product> prod) {
        products = prod;
        for (int i = 0; i < products.size() ; i++) {
            ProductButton button = new ProductButton(this);
            button.setText(products.get(i).getName());
            button.setProductID(i);
            productButtons.add(button);

                if (products.get(i).getCategory().equals("Frisdrank")) {
                    if(products.get(i).isInStock() == false){
                        button.setClickable(false);
                        button.setInstock(false);
                        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    layoutSoda.addView(button);
                } else if (products.get(i).getCategory().equals("Fast food")) {
                    if(products.get(i).isInStock() == false){
                        button.setClickable(false);
                        button.setInstock(false);
                        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    layoutFastFood.addView(button);
                } else if (products.get(i).getCategory().equals("Water")) {
                    if(products.get(i).isInStock() == false){
                        button.setClickable(false);
                        button.setInstock(false);
                        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    layoutWater.addView(button);
                } else if (products.get(i).getCategory().equals("Brood")) {
                    if(products.get(i).isInStock() == false){
                        button.setClickable(false);
                        button.setInstock(false);
                        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    layoutBread.addView(button);
                } else if (products.get(i).getCategory().equals("Alcohol")) {
                    if(products.get(i).isInStock() == false){
                        button.setClickable(false);
                        button.setInstock(false);
                        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    layoutAlchohol.addView(button);
                } else if (products.get(i).getCategory().equals("Snacks")) {
                    if(products.get(i).isInStock() == false){
                        button.setClickable(false);
                        button.setInstock(false);
                        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    layoutSnacks.addView(button);
            }
        }
        for (int i = 0; i < productButtons.size(); i++) {
            final ProductButton pb = productButtons.get(i);
            final String orderString = "U heeft een " + pb.getText() + " besteld";

            productButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < products.size(); j++) {
                    if (products.get(j).isInStock() == false) {
                        Toast.makeText(AddExtraProducts.this, "Dit product is momenteel niet beschikbaar", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddExtraProducts.this, orderString, Toast.LENGTH_SHORT).show();
                    }
                }
            }
                }
            );
        }
    }

    @Override
    public void onClick(View v) {
        Intent backToOrder = new Intent(this, HandleOrderActivity.class);
        backToOrder.putExtra("order", order);
        startActivity(backToOrder);
    }
}
