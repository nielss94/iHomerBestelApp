package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.periode4groep2.employeeapp.DomainModel.Product;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.ProductDAO;
import com.periode4groep2.employeeapp.R;

import java.util.ArrayList;

/**
 * Created by Kayvon Rahimi on 2-6-2017.
 */

public class DeleteProductAdapter extends ArrayAdapter<Product> {

    private OnDeleteProduct listener;

    public DeleteProductAdapter(Context context, ArrayList<Product> productList, OnDeleteProduct listener){
        super(context, 0, productList);
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater productInflater = LayoutInflater.from(getContext());

        View customView = productInflater.inflate(R.layout.list_item_remove_product, parent, false);

        final Product product = getItem(position);
        TextView productName = (TextView)customView.findViewById(R.id.productNameTextView);

        productName.setText(product.getName());
        Button deleteButton = (Button)customView.findViewById(R.id.listItemDelete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteProduct(product);
            }
        });
        return customView;
    }

    public interface OnDeleteProduct{
        void onDeleteProduct(Product product);
    }
}
