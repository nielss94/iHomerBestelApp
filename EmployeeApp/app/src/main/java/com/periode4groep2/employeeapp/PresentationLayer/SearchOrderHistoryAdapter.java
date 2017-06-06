package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.periode4groep2.employeeapp.DomainModel.Order;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;

/**
 * Created by jesse on 02/06/17.
 */

public class SearchOrderHistoryAdapter extends ArrayAdapter<Order> {

    public SearchOrderHistoryAdapter(Context context, ArrayList<Order> orderList) {
        super(context, R.layout.activity_custom_list_item_order_history_search, orderList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater productInflater = LayoutInflater.from(getContext());
        View customView = productInflater.inflate(R.layout.activity_custom_list_item_order_history_search, parent, false);

        TextView orderDate = (TextView) customView.findViewById(R.id.orderHistoryDateId);
        TextView orderPrice = (TextView) customView.findViewById(R.id.orderHistoryPriceId);
        TextView orderHandled = (TextView) customView.findViewById(R.id.handledId);

        Order singleOrder = getItem(position);

        String date = singleOrder.getDate();
        String fullDate = date.substring(0,10);

        orderDate.setText(fullDate);

        Double price = singleOrder.getTotalPrice();
        String goodPrice = String.format("€%10.2f", price);

        orderPrice.setText(goodPrice);

        boolean handled = singleOrder.isHandled();
        if (handled == true) {
            orderHandled.setText("✓");
        } else {
            orderHandled.setText("×");
        }

        return customView;
    }
}
