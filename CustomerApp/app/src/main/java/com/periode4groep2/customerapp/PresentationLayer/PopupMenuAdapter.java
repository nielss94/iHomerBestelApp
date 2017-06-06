package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.periode4groep2.customerapp.DomainModel.OrderItem;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;

/**
 * Created by jesse on 08/05/17.
 */

public class PopupMenuAdapter extends ArrayAdapter<OrderItem> {

    private ArrayList<Product> products;

    public PopupMenuAdapter(Context context, ArrayList<OrderItem> orderItemList, ArrayList<Product> products) {
        super(context, R.layout.popup_menu_item, orderItemList);
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater productInflater = LayoutInflater.from(getContext());
        View customView = productInflater.inflate(R.layout.popup_menu_item, parent, false);

        OrderItem singleOrderItem = getItem(position);

        TextView productName = (TextView)customView.findViewById(R.id.popupProductName);
        TextView productQuantity = (TextView)customView.findViewById(R.id.popupProductQuantity);

        for (int i = 0; i < products.size(); i++) {
            if(singleOrderItem.getProductID() == products.get(i).getProductID()){
                productName.setText(products.get(i).getName());
                break;
            }
        }
        productQuantity.setText(singleOrderItem.getQuantity()+"");

        return customView;
    }
}
