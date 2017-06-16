package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.R;

public class AddStockProduct extends AppCompatActivity {

    private Toolbar toolbar;
    private Account account;
    private EditText productID, category, name, price, inStock, nameEng, categoryEng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_product);

        account = (Account) getIntent().getSerializableExtra("account");

        productID = (EditText) findViewById(R.id.stock_add_product_productID);
        category = (EditText) findViewById(R.id.stock_add_product_category);
        name = (EditText) findViewById(R.id.stock_add_product_product);
        price = (EditText) findViewById(R.id.stock_add_product_price);
        inStock = (EditText) findViewById(R.id.stock_add_product_instock);
        nameEng = (EditText) findViewById(R.id.stock_add_product_nameEng);
        categoryEng = (EditText) findViewById(R.id.stock_add_product_categoryEng);

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
}
