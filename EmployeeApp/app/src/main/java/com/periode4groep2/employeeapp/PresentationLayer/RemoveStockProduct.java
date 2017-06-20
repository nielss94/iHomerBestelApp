package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;

public class RemoveStockProduct extends AppCompatActivity implements DeleteProductAdapter.OnDeleteProduct{

    private Toolbar toolbar;
    private Account account;
    private ListView deleteListView;
    private DAOFactory factory;
    private ProductDAO productDAO;
    private DeleteProductAdapter deleteProductAdapter;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_stock_product);

        account = (Account) getIntent().getSerializableExtra("account");

        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();

        products = (ArrayList<Product>)getIntent().getSerializableExtra("products");

        deleteListView = (ListView)findViewById(R.id.listViewDelete);
        deleteProductAdapter = new DeleteProductAdapter(getApplicationContext(), products, this);
        deleteListView.setAdapter(deleteProductAdapter);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_homebutton);
        toolbar.setNavigationIcon(homeButton);

        toolbar.setTitle(R.string.employee_remove_stock_product_activity);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDeleteProduct(Product product) {
        productDAO.deleteProductFromStock(account,product);
        products.remove(product);
        deleteProductAdapter.notifyDataSetChanged();
    }
}