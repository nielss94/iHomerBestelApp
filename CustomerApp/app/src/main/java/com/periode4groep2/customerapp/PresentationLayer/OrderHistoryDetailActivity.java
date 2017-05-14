package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.periode4groep2.customerapp.R;

public class OrderHistoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_detail);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tool_bar);
        myToolbar.setTitle("Bestelling");
        setSupportActionBar(myToolbar);
    }
}
