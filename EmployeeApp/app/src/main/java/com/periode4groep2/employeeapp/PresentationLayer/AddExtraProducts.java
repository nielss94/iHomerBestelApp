package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Order;
import com.periode4groep2.employeeapp.DomainModel.OrderItem;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.OrderDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductSetAvailable;
import com.periode4groep2.employeeapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddExtraProducts extends AppCompatActivity implements ProductSetAvailable, View.OnClickListener {
    private ArrayList<ProductButton> productButtons = new ArrayList<>();
    private LinearLayout layoutSoda, layoutFastFood, layoutWater, layoutBread, layoutAlchohol, layoutSnacks;
    private DAOFactory factory;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private ArrayList<Product> products = new ArrayList<>();
    private Order order;
    private Account account;
    private Button saveOrderButton;
    private ArrayList<OrderItem> orderItemsToAdd = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products_activity);
        factory = new MySQLDAOFactory();
        orderDAO = factory.createOrderDAO();
        productDAO = factory.createProductDAO();
        productDAO.selectData(this);

        order = (Order)getIntent().getSerializableExtra("order");
        account = (Account)getIntent().getSerializableExtra("account");

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
            button.setProduct(products.get(i));
            button.setText(button.getProduct().getName());
            productButtons.add(button);

                if (products.get(i).getCategory().equals("Frisdrank")) {
                    if(!button.getProduct().isInStock()){
                        button.setClickable(false);
                        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    layoutSoda.addView(button);
                } else if (products.get(i).getCategory().equals("Fast food")) {
                    if(!button.getProduct().isInStock()){
                        button.setClickable(false);
                        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    layoutFastFood.addView(button);
                } else if (products.get(i).getCategory().equals("Water")) {
                    if(!button.getProduct().isInStock()){
                        button.setClickable(false);
                        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    layoutWater.addView(button);
                } else if (products.get(i).getCategory().equals("Brood")) {
                    if(!button.getProduct().isInStock()){
                        button.setClickable(false);
                        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    layoutBread.addView(button);
                } else if (products.get(i).getCategory().equals("Alcohol")) {
                    if(!button.getProduct().isInStock()){
                        button.setClickable(false);
                        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    layoutAlchohol.addView(button);
                } else if (products.get(i).getCategory().equals("Snacks")) {
                    if(!button.getProduct().isInStock()){
                        button.setClickable(false);
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
                    if (!pb.getProduct().isInStock()) {
                        Toast.makeText(AddExtraProducts.this, "Dit product is momenteel niet beschikbaar", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddExtraProducts.this, orderString, Toast.LENGTH_SHORT).show();

                        OrderItem oi = new OrderItem(pb.getProduct().getProductID(), 1);
                        addOrderitem(pb.getProduct().getPrice(), oi);

                        Log.i("poep", order.getOrderItems() + "");
                        }
                    }
                }
            );
        }
    }

    public void addOrderitem(double newPrice, OrderItem orderItem) {
        order.setTotalPrice(order.getTotalPrice() + newPrice);
        ArrayList<Integer> productIDsInOrderItems = new ArrayList<>();

        for (int i = 0; i < order.getOrderItems().size(); i++) {
            productIDsInOrderItems.add(order.getOrderItems().get(i).getProductID());
        }

        if(!productIDsInOrderItems.contains(orderItem.getProductID())){
            order.getOrderItems().add(orderItem);
        }
        else{
            for (int i = 0; i < order.getOrderItems().size(); i++) {
                if(order.getOrderItems().get(i).getProductID() == orderItem.getProductID()){
                    order.getOrderItems().get(i).setQuantity(order.getOrderItems().get(i).getQuantity() + orderItem.getQuantity());
                    break;
                }
            }
        }

        ArrayList<Integer> productIDsInOrderItemsToAdd = new ArrayList<>();

        for (int i = 0; i < orderItemsToAdd.size(); i++) {
            productIDsInOrderItemsToAdd.add(orderItemsToAdd.get(i).getProductID());
        }

        if(!productIDsInOrderItemsToAdd.contains(orderItem.getProductID())){
            orderItemsToAdd.add(orderItem);
        }
        else{
            for (int i = 0; i < orderItemsToAdd.size(); i++) {
                if(orderItemsToAdd.get(i).getProductID() == orderItem.getProductID()){
                    orderItemsToAdd.get(i).setQuantity(orderItemsToAdd.get(i).getQuantity() + orderItem.getQuantity());
                    break;
                }
            }
        }


        //Log.i(TAG,newOrder.getOrderItems().toString());
    }

    @Override
    public void onClick(View v) {
        Intent backToOrder = new Intent(AddExtraProducts.this, HandleOrderActivity.class);
        backToOrder.putExtra("order", order);
        backToOrder.putExtra("account", account);
        orderDAO.addToOrder(account,order,orderItemsToAdd);
        Toast.makeText(this, "Producten succesvol toegevoegd aan de order", Toast.LENGTH_SHORT).show();
        startActivity(backToOrder);
    }
}
