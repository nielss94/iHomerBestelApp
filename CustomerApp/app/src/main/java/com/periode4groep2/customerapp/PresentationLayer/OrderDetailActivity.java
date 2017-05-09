package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.periode4groep2.customerapp.R;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener  {
    //Logging tag
    private final String TAG = this.getClass().getSimpleName();
    //controller elements
    private ListView orderItemListView;
    private TextView totalTagTextView;
    private TextView totalPriceTextView;
    private Button cancelOrderButton;
    private Button scanOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ListView orderItemListView = (ListView)findViewById(R.id.orderItemListView);
        //totalTagPriceTextView = (TextView)findViewById(R.id.totalTagTextView);
        //totalPricePriceTextView = (TextView)findViewById(R.id.totalPriceTagTextView);
        cancelOrderButton = (Button)findViewById(R.id.cancelOrderButton);
        scanOrderButton = (Button)findViewById(R.id.scanOrderButton);


        cancelOrderButton.setOnClickListener(this);
        scanOrderButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.equals(cancelOrderButton)){
            Intent intent = new Intent(this, HomeScreenActivity.class);
            startActivity(intent);
        }
        else if(v.equals(scanOrderButton)){
            Intent intent = new Intent(this, ScanActivity.class);
            startActivity(intent);
        }
    }

//    @Override
//    public void onOrderItemAvailable(OrderItem orderItem) {
//        orderItemArray.add(orderItem);
//        Log.i(TAG, "Order items added to list: " + orderItem.toString() + ")");
//        myOrderItemAdapter.notifyDataSetChanged();
//    }
}
