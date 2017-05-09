package com.periode4groep2.customerapp.PresentationLayer;
//http://stackoverflow.com/questions/9824074/android-expandablelistview-looking-for-a-tutorial

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.periode4groep2.customerapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView basket;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        basket = (ImageView)findViewById(R.id.order_basket);
        basket.setOnClickListener(this);

        listView = (ExpandableListView)findViewById(R.id.expandableListId);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);



    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Frisdranken");
        listDataHeader.add("Alchoholisch");
        listDataHeader.add("Bier");
        listDataHeader.add("Wijn");

        List<String> Frisdranken = new ArrayList<>();
        Frisdranken.add("Hier komt shit uit de DB");

        List<String> Alchoholisch = new ArrayList<>();
        Alchoholisch.add("Hier komt shit uit de DB");
        Alchoholisch.add("Hier komt shit uit de DB");

        List<String> Bier = new ArrayList<>();
        Bier.add("Hier komt shit uit de DB");

        List<String> Wijn = new ArrayList<>();
        Wijn.add("Hier komt shit uit de DB");
        Wijn.add("Hier komt shit uit de DB");
        Wijn.add("Hier komt shit uit de DB");

        listHash.put(listDataHeader.get(0),Frisdranken);
        listHash.put(listDataHeader.get(1),Alchoholisch);
        listHash.put(listDataHeader.get(2),Bier);
        listHash.put(listDataHeader.get(3),Wijn);
    }

    public void onClick(View v){
        Intent intent = new Intent(this, OrderDetailActivity.class);
        startActivity(intent);
    }
}
