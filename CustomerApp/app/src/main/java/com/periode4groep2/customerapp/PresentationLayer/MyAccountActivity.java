package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.periode4groep2.customerapp.R;

public class MyAccountActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button balanceInfoButton;
    private Button orderHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        balanceInfoButton = (Button) findViewById(R.id.myAccountBalanceInfoId);
        orderHistoryButton = (Button) findViewById(R.id.myAccountHistoryId);

        orderHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OrderHistoryActivity.class);
                startActivity(i);
            }
        });

        balanceInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddBalanceActivity.class);
                startActivity(i);
            }
        });
    }
}
