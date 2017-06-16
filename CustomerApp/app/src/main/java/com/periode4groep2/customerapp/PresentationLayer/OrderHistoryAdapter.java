package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.periode4groep2.customerapp.DomainModel.Order;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;

/**
 * Created by jesse on 08/05/17.
 */

public class OrderHistoryAdapter extends ArrayAdapter<Order> {

    public OrderHistoryAdapter(Context context, ArrayList<Order> orderList) {
        super(context, R.layout.activity_custom_list_item_order_history, orderList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater productInflater = LayoutInflater.from(getContext());
        View customView = productInflater.inflate(R.layout.activity_custom_list_item_order_history, parent, false);

        Order singleOrder = getItem(position);

        TextView orderDate = (TextView) customView.findViewById(R.id.orderHistoryDateId);
        TextView orderPrice = (TextView) customView.findViewById(R.id.orderHistoryPriceId);
        TextView isHandled = (TextView) customView.findViewById(R.id.handledId);

        String date = singleOrder.getDate();
        String fullDate = date.substring(0,10);
        String fullTime = date.substring(11,16);
        String finalDate = fullDate + " " + fullTime;

        orderDate.setText(finalDate);

        Double price = singleOrder.getTotalPrice();
        String goodPrice = String.format("€%10.2f", price);

        orderPrice.setText(goodPrice);

        boolean handled = singleOrder.isHandled();
        if (handled == true) {
            isHandled.setText("✓");
        } else {
            isHandled.setText("×");
        }

        return customView;
    }
}
