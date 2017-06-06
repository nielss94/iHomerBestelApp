package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;

/**
 * Created by Kayvon Rahimi on 2-6-2017.
 */

public class StockItemAdapter extends ArrayAdapter<Product> {
    private ArrayList<Product> productList;

    public StockItemAdapter(Context context, ArrayList<Product> productList){
        super(context, R.layout.custom_list_stock_item, productList);
        this.productList = productList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater productInflater = LayoutInflater.from(getContext());

        View customView = productInflater.inflate(R.layout.custom_list_stock_item, parent, false);

        Product product = getItem(position);
        TextView productName = (TextView)customView.findViewById(R.id.productNameTextView);
        TextView productQuantity = (TextView)customView.findViewById(R.id.productQuantityTextView);
        TextView productPrice = (TextView)customView.findViewById(R.id.productTotalPriceTextView);

        for (int i = 0; i < productList.size(); i++) {
            if(productList.get(i).getProductID() == product.getProductID())
            {
                productName.setText(productList.get(i).getName());
                //Double price = productList.get(i).getPrice() * product.getQuantity();
                //String goodPrice = String.format("€%10.2f", price);
                //productPrice.setText(goodPrice);
                break;
            }
        }

        //productQuantity.setText(singleOrderItem.getQuantity()+"");

        return customView;
    }

}
