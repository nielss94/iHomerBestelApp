package com.periode4groep2.employeeapp.PersistancyLayer;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.DomainModel.Product;

import java.util.ArrayList;

/**
 * Created by Niels on 5/5/2017.
 */

public class MySQLProductDAO implements ProductDAO, MySQLProductAPIConnector.ProductAvailable {

    private ProductSetAvailable context;
    private MySQLProductAPIConnector mySQLProductAPIConnector = new MySQLProductAPIConnector(this);
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    public void selectData(ProductSetAvailable c) {
        context = c;
        String[] urls = {
            "https://ihomerapi.herokuapp.com/API/getProducts"
        };

        mySQLProductAPIConnector.execute(urls);

    }

    @Override
    public void changeStock(Account account, Product product) {
        new ProductStockConnector(account,product).execute();
    }

    @Override
    public void productAvailable(Product product, boolean done) {
        products.add(product);
        if(done)
        {
            context.productSetAvailable(products);
        }
    }
}
