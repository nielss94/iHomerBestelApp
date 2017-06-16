package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLProductDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.R;

public class AddStockProduct extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private Account account;
    private EditText category, name, price, nameEng, categoryEng;
    private Product product;
    private Button button;
    private DAOFactory factory;
    private ProductDAO productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_product);
        factory = new MySQLDAOFactory();
        productDAO = factory.createProductDAO();
        account = (Account) getIntent().getSerializableExtra("account");

        category = (EditText) findViewById(R.id.stock_add_product_category);
        name = (EditText) findViewById(R.id.stock_add_product_product);
        price = (EditText) findViewById(R.id.stock_add_product_price);
        nameEng = (EditText) findViewById(R.id.stock_add_product_nameEng);
        categoryEng = (EditText) findViewById(R.id.stock_add_product_categoryEng);

        button = (Button) findViewById(R.id.stock_add_product_button);
        button.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_homebutton);
        toolbar.setNavigationIcon(homeButton);

        toolbar.setTitle(R.string.employee_add_stock_product_activity);
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

    public void onClick(View v){
        product = new Product(0, category.getText().toString(), name.getText().toString(), true,
                Double.parseDouble(price.getText().toString()));
        product.setNameEng(nameEng.getText().toString());
        product.setCategoryEng(categoryEng.getText().toString());

        productDAO.addProductToStock(account, product);

        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
        intent.putExtra("account", account);
        startActivity(intent);
    }


}
