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

        String input = putInBalance.getText().toString().trim();
        if(input.isEmpty()){
            Toast.makeText(this, "U heeft geen bedrag ingevoerd", Toast.LENGTH_SHORT).show();
        } else {
            double currBalance = Double.parseDouble(input);
            //Een if else statement om ervoor te zorgen dat er geen bedrag boven de 150 euro komt
            if (currBalance <= 0.01) {
                Toast.makeText(this, "U kunt niet €0.00 teoevoegen", Toast.LENGTH_SHORT).show();
            } else if (subtotal >= 150.00) {
                Toast.makeText(this, "U kunt niet meer dan €150,00 op uw account hebben", Toast.LENGTH_SHORT).show();
            } else if (subtotal + currBalance > 150.00) {
                Toast.makeText(this, "U kunt niet meer dan €150,00 op uw account hebben", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "U heeft " + input + " euro aan uw account toegevoegd", Toast.LENGTH_SHORT).show();
                subtotal += currBalance;
                currentBalance.setText(String.format("%.2f", subtotal));
            }
        }
        
        //EditText leegmaken na gebruik
        putInBalance.setText("");

        //Hier komt de code voor de wallet die je in de title bar kan zien, deze wordt gelijk veranderd zodra je op de knop saldo
        //toevoegen klikt, De limieten van 150 euro en minstens 0 euro komen later.
        }
    }
