package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.R;

public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener{
    Account account;
    private Button balanceInfoButton, orderHistoryButton;
    private TextView textviewEmail, textviewFirstName, textviewLastName, textviewBirthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        balanceInfoButton = (Button) findViewById(R.id.myAccountBalanceInfoId);
        orderHistoryButton = (Button) findViewById(R.id.myAccountHistoryId);

        orderHistoryButton.setOnClickListener(this);
        balanceInfoButton.setOnClickListener(this);

        account = (Account)getIntent().getSerializableExtra("account");
        textviewEmail = (TextView)findViewById(R.id.my_account_email);
        textviewEmail.setText(account.getEmail());
        textviewFirstName = (TextView)findViewById(R.id.my_account_firstname);
        textviewFirstName.setText(account.getFirstName());
        textviewLastName = (TextView)findViewById(R.id.my_account_lastname);
        textviewLastName.setText(account.getLastName());
        textviewBirthDate = (TextView)findViewById(R.id.my_account_age);
        textviewBirthDate.setText(account.getDateOfBirth());
    }

    @Override
    public void onClick(View v) {
        if (v.equals(orderHistoryButton)){
            Intent i = new Intent(getApplicationContext(), OrderHistoryActivity.class);
            startActivity(i);
        } else if (v.equals(balanceInfoButton)){
            Intent i = new Intent(getApplicationContext(), AddBalanceActivity.class);
            startActivity(i);
        }
    }
}
