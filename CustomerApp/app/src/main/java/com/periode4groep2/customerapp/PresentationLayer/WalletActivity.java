package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.periode4groep2.customerapp.R;

import java.util.ArrayList;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener{
    //Logging tag
    private final String TAG = this.getClass().getSimpleName();
    //controller elements
    private ListView transactionListView;
    private Button addBalanceButton;
    private Button refundBalanceButton;

    //Domain objects
    //private Balance balance;
    //private Order order;
    //private Transaction transaction;
    //private ArrayList<Transaction> transactionsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        transactionListView = (ListView)findViewById(R.id.transactionListView);

        addBalanceButton = (Button)findViewById(R.id.addSaldoBtn);
        refundBalanceButton = (Button)findViewById(R.id.refundSaldoBtn);

        addBalanceButton.setOnClickListener(this);
        refundBalanceButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(v.equals(addBalanceButton)){
            Intent intent = new Intent(this, AddBalanceActivity.class);
            startActivity(intent);
        }
        else if(v.equals(refundBalanceButton)){
            Intent intent = new Intent(this, RefundActivity.class);
            startActivity(intent);
            Toast.makeText(this, "REFUND TIME", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void onTransactionAvailable(Transaction transaction) {
//        transactionsArray.add(transaction);
//        Log.i(TAG, "Transaction added to list: " + transaction.toString() + ")");
//        myTransactionAdapter.notifyDataSetChanged();
//    }
}
