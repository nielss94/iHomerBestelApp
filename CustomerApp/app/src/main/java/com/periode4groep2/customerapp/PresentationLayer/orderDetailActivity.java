package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.periode4groep2.customerapp.R;

public class orderDetailActivity extends AppCompatActivity {

    private ListView orderItemListView;
    private Button cancelOrderButton;
    private Button scanOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ListView orderItemListView = (ListView)findViewById(R.id.orderItemListView);


    }
}
