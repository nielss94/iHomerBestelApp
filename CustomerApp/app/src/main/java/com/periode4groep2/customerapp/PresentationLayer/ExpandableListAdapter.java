package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.periode4groep2.customerapp.DomainModel.OrderItem;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by J.h2os on 9-5-2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> ListDataHeader;
    private HashMap<String,ArrayList<Product>> listHashMap;
    private OnOrderChanged listener;
    private ArrayList<OrderItem> orderItems = new ArrayList<>();

    public ExpandableListAdapter(Context context, ArrayList<String> listDataHeader, HashMap<String, ArrayList<Product>> listHashMap, OnOrderChanged listener) {
        this.context = context;
        ListDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
        this.listener = listener;
    }

    @Override
    public int getGroupCount() {
        return ListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(ListDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(ListDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String)getGroup(groupPosition);
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group,null);
        }
        TextView listGroupId = (TextView)convertView.findViewById(R.id.listGroupId);
        listGroupId.setTypeface(null, Typeface.BOLD);
        listGroupId.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Product childProduct = (Product)getChild(groupPosition,childPosition);
        Log.i("ExpandableListAdapter", groupPosition + "");
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,null);
        }

        final TextView txtListChild = (TextView)convertView.findViewById(R.id.listItemId);
        final TextView txtListPrice = (TextView)convertView.findViewById(R.id.listItemPrice);

        Button imgDecrease = (Button)convertView.findViewById(R.id.listItemDecrease);
        final TextView txtListQuantity = (TextView)convertView.findViewById(R.id.listItemQuantity);

        LinearLayout parentItem = (LinearLayout)txtListChild.getParent();
        if(childProduct.isInStock() == false){
            parentItem.setBackgroundColor(parentItem.getResources().getColor(R.color.colorDarkRed));
        }else{
            parentItem.setBackgroundColor(parentItem.getResources().getColor(R.color.colorBackground));
        }

        txtListChild.setText(childProduct.getName());
        txtListPrice.setText("€" + childProduct.getPrice());
        Boolean notInOrderItems = true;
        for (int i = 0; i < orderItems.size(); i++) {
            if(childProduct.getProductID() == orderItems.get(i).getProductID()){
                Log.i(TAG, String.valueOf(orderItems.get(i).getQuantity()));
                txtListQuantity.setText(String.valueOf(orderItems.get(i).getQuantity()));
                notInOrderItems = false;
                break;
            }
        }

        if(notInOrderItems){
            txtListQuantity.setText("0");
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!childProduct.isInStock()){
                    Toast.makeText(context, "Dit product is op het moment niet beschikbaar.", Toast.LENGTH_SHORT).show();
                }else{
                    int amount = Integer.parseInt(txtListQuantity.getText().toString());
                    txtListQuantity.setText((amount + 1) + "");
                    OrderItem oi = new OrderItem(childProduct.getProductID(), 1);
                    addOrderitem(oi);
                    listener.onOrderChanged(childProduct, 1);
                }

            }
        });

        imgDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!childProduct.isInStock()){
                    Toast.makeText(context, "Dit product is op het moment niet beschikbaar.", Toast.LENGTH_SHORT).show();
                }else {
                    int amount = Integer.parseInt(txtListQuantity.getText().toString());
                    if (amount > 0) {
                        txtListQuantity.setText((amount - 1) + "");
                        OrderItem oi = new OrderItem(childProduct.getProductID(), -1);
                        addOrderitem(oi);
                        listener.onOrderChanged(childProduct, -1);
                    }
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface OnOrderChanged{
        void onOrderChanged(Product product, int quantity);
    }

    public void addOrderitem(OrderItem orderItem) {
        ArrayList<Integer> productIDsInOrderItems = new ArrayList<>();

        for (int i = 0; i < orderItems.size(); i++) {
            productIDsInOrderItems.add(orderItems.get(i).getProductID());
        }

        if(!productIDsInOrderItems.contains(orderItem.getProductID())){
            orderItems.add(orderItem);
        }
        else{
            for (int i = 0; i < orderItems.size(); i++) {
                if(orderItems.get(i).getProductID() == orderItem.getProductID()){
                    orderItems.get(i).setQuantity(orderItems.get(i).getQuantity() + orderItem.getQuantity());
                    break;
                }
            }
        }

    }
}
