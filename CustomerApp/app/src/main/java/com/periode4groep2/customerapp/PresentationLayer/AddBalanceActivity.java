package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.periode4groep2.customerapp.R;


public class AddBalanceActivity extends AppCompatActivity implements View.OnClickListener {
    EditText putInBalance;
    Button addBalance;
    TextView currentBalance;
    double subtotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);
        putInBalance = (EditText) findViewById(R.id.giveBalanceID);

        addBalance = (Button) findViewById(R.id.addBalanceID);
        addBalance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        currentBalance = (TextView) findViewById(R.id.currentBalance);

        String input = putInBalance.getText().toString();
        Toast.makeText(this, "U heeft " + input + " euro aan uw account toegevoegd", Toast.LENGTH_SHORT).show();

        double currBalance = Double.parseDouble(input);
        subtotal += currBalance;
        currentBalance.setText(String.format("%.2f", subtotal));

        //EditText leegmaken na gebruik
        putInBalance.setText("");

        //Hier komt de code voor de wallet die je in de title bar kan zien, deze wordt gelijk veranderd zodra je op de knop saldo
        //toevoegen klikt, De limieten van 150 euro en minstens 0 euro komen later.
        }
    }
