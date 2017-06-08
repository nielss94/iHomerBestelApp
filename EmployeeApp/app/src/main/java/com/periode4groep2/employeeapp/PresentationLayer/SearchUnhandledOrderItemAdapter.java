/**
 * Created by Kayvon Rahimi on 8-5-2017.
 */

package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.periode4groep2.employeeapp.DomainModel.OrderItem;
import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;


public class SearchUnhandledOrderItemAdapter extends ArrayAdapter<OrderItem> {

    private ArrayList<Product> productList;

    public SearchUnhandledOrderItemAdapter(Context context, ArrayList<OrderItem> orderItemList, ArrayList<Product> productList){
        super(context, R.layout.activity_custom_list_item_search_unhandled_order_history_detail, orderItemList);
        this.productList = productList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater productInflater = LayoutInflater.from(getContext());

        View customView = productInflater.inflate(R.layout.activity_custom_list_item_search_unhandled_order_history_detail, parent, false);

        OrderItem singleOrderItem = getItem(position);
        TextView productName = (TextView)customView.findViewById(R.id.productNameTextView);
        TextView productQuantity = (TextView)customView.findViewById(R.id.productQuantityTextView);
        TextView productPrice = (TextView)customView.findViewById(R.id.productPriceTextView);

        for (int i = 0; i < productList.size(); i++) {
            if(productList.get(i).getProductID() == singleOrderItem.getProductID())
            {
                productName.setText(productList.get(i).getName());
                Double price = productList.get(i).getPrice() * singleOrderItem.getQuantity();
                String goodPrice = String.format("€%10.2f", price);
                productPrice.setText(goodPrice);
                break;
            }
        }

        productQuantity.setText(singleOrderItem.getQuantity()+"");

        return customView;
    }
}