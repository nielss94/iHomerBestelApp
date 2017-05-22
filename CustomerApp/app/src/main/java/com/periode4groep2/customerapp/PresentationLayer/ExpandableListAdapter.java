package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.periode4groep2.customerapp.DomainModel.OrderItem;
import com.periode4groep2.customerapp.DomainModel.Product;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
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
        ImageView plusButton = (ImageView)convertView.findViewById(R.id.list_plusser);
        final TextView orderedAmount = (TextView)convertView.findViewById(R.id.ordered_amount);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = Integer.parseInt(orderedAmount.getText().toString());
                i++;
                OrderItem oi = new OrderItem(childProduct.getProductID(),i);
                listener.onOrderChanged(childProduct.getPrice(),oi);
                orderedAmount.setText(i+"");
                Log.i("List", "Plus bij " + txtListChild.getText().toString());
            }
        });
        ImageView minButton = (ImageView)convertView.findViewById(R.id.list_minner);
        minButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(orderedAmount.getText().toString());
                if(i > 0){
                    i--;
                    orderedAmount.setText(i+"");
                    OrderItem oi = new OrderItem(childProduct.getProductID(),1);
                    listener.onOrderChanged(-childProduct.getPrice(), oi);
                    Log.i("List", "Min bij " + txtListChild.getText().toString());

                }
            }
        });
        txtListChild.setText(childProduct.getName());
        txtListPrice.setText("€" + childProduct.getPrice());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface OnOrderChanged{
        void onOrderChanged(double newPrice, OrderItem orderItem);
    }

}
