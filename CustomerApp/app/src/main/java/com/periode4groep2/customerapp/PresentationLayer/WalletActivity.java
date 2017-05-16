package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.R;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener{
    //Logging tag
    private final String TAG = this.getClass().getSimpleName();
    //controller elements
    private ListView transactionListView;
    private Button toBalanceButton;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        transactionListView = (ListView)findViewById(R.id.transactionListView);

        toBalanceButton = (Button)findViewById(R.id.addSaldoBtn);

        toBalanceButton.setOnClickListener(this);

        account = (Account)getIntent().getSerializableExtra("account");
    }

    @Override
    public void onClick(View v){
            Intent intent = new Intent(this, BalanceActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
    }

//    @Override
//    public void onTransactionAvailable(Transaction transaction) {
//        transactionsArray.add(transaction);
//        Log.i(TAG, "Transaction added to list: " + transaction.toString() + ")");
//        myTransactionAdapter.notifyDataSetChanged();
//    }
}
