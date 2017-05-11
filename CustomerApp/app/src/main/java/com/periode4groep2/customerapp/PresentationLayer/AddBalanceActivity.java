package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.R;



public class AddBalanceActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText putInBalance;
    private Button addBalance;
    private TextView currentBalance;
    double subtotal = 0;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);
        putInBalance = (EditText) findViewById(R.id.giveBalanceID);
        currentBalance = (TextView) findViewById(R.id.currentBalance);

        account = (Account)getIntent().getSerializableExtra("account");
        currentBalance.setText("€" + String.format("%.2f", account.getBalance()/100) + "");

        addBalance = (Button) findViewById(R.id.addBalanceID);
        addBalance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            String input = putInBalance.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, R.string.no_amount_entered_toast, Toast.LENGTH_SHORT).show();
            } else {
                double currBalance = Double.parseDouble(input);
                if (currBalance <= 0.01) {
                    Toast.makeText(this, R.string.no_amount_entered_toast, Toast.LENGTH_SHORT).show();
                } else if (subtotal >= 150.00) {
                    Toast.makeText(this, R.string.too_much_money_toast, Toast.LENGTH_SHORT).show();
                } else if (subtotal + currBalance > 150.00) {
                    Toast.makeText(this, R.string.too_much_money_toast, Toast.LENGTH_SHORT).show();
                } else {
                    String youHave = getString(R.string.you_have_toast);
                    String amountAdded = getString(R.string.amount_added_toast);
                    String toastText = youHave + " " + input + " " + amountAdded;

                    Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
                    subtotal += currBalance;
                    currentBalance.setText("€" + String.format("%.2f", subtotal));
                }
            putInBalance.setText("");
        }
    }
}
