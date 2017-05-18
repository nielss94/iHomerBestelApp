/**
 * Created by Kayvon Rahimi on 8-5-2017.
 */

package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.periode4groep2.customerapp.DomainModel.OrderItem;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;


public class OrderItemAdapter extends ArrayAdapter<OrderItem> {

    public OrderItemAdapter(Context context, ArrayList<OrderItem> orderItemList){
        super(context, R.layout.activity_custom_order_list_item, orderItemList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater productInflater = LayoutInflater.from(getContext());
        View customView = productInflater.inflate(R.layout.activity_custom_order_list_item, parent, false);

        OrderItem orderItem = getItem(position);
        //Order order = getItem();
        //Product product = getItem(position);

        TextView productName = (TextView)customView.findViewById(R.id.productNameTextView);
        TextView productQuantity = (TextView)customView.findViewById(R.id.productQuantityTextView);
        TextView productTotalPrice = (TextView)customView.findViewById(R.id.productTotalPriceTextView);

        productName.setText(orderItem.getProductID());
        productQuantity.setText(orderItem.getQuantity());
        // productTotalPrice.setText(product.getTotalPrice());

        return customView;
    }

}