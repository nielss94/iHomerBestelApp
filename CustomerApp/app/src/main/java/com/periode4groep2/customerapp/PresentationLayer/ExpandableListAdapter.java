package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.periode4groep2.customerapp.DomainModel.OrderItem;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by J.h2os on 9-5-2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> ListDataHeader;
    private HashMap<String,ArrayList<Product>> listHashMap;
    private OnOrderChanged listener;

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
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,null);
        }

        final TextView txtListChild = (TextView)convertView.findViewById(R.id.listItemId);
        final TextView txtListPrice = (TextView)convertView.findViewById(R.id.listItemPrice);

        Button imgDecrease = (Button)convertView.findViewById(R.id.listItemDecrease);
        final TextView txtListQuantity = (TextView)convertView.findViewById(R.id.listItemQuantity);



        txtListChild.setText(childProduct.getName());
        txtListPrice.setText("€" + childProduct.getPrice());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(txtListQuantity.getText().toString());
                txtListQuantity.setText((amount + 1) + "");
                OrderItem oi = new OrderItem(childProduct.getProductID(), 1);
                listener.onOrderChanged(oi);
            }
        });

        imgDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(txtListQuantity.getText().toString());
                txtListQuantity.setText((amount - 1) + "");
                OrderItem oi = new OrderItem(childProduct.getProductID(), -1);
                listener.onOrderChanged(oi);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface OnOrderChanged{
        void onOrderChanged(OrderItem oi);
    }

}
