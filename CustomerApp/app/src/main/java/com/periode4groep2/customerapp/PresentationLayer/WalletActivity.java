package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.periode4groep2.customerapp.R;

public class WalletActivity extends AppCompatActivity {

    private ListView transactionListView;
    private Button addSaldoBtn;
    private Button refundSaldoBtn;

    private Saldo saldo;
    private Transaction transaction;
    private Order order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        transactionListView = (ListView)findViewById(R.id.transactionListView);

        addSaldoBtn = (Button)findViewById(R.id.addSaldoBtn);
        refundSaldoBtn = (Button)findViewById(R.id.refundSaldoBtn);


    }
}
