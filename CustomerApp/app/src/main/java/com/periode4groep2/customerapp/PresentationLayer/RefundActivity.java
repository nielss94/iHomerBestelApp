package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.periode4groep2.customerapp.R;

public class RefundActivity extends AppCompatActivity implements View.OnClickListener{
    //
    //UI elements
    private TextView currentBalanceHeader;
    private TextView currentBalance;
    private EditText refundBalanceEntry;
    private Button refundBalanceButton;
    double totalBalance = 0;

    //Domain objects
    //private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);

        currentBalanceHeader = (TextView)findViewById(R.id.currentBalanceHeaderTextView);
        currentBalance = (TextView)findViewById(R.id.currentBalanceTextView);
        refundBalanceEntry = (EditText)findViewById(R.id.refundBalanceEntryEditText);

        Button refundBalanceButton = (Button)findViewById(R.id.refundBalanceButton);
            refundBalanceButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(v.equals(refundBalanceButton)) {

            Toast.makeText(this, "Refund balance clicked", Toast.LENGTH_SHORT).show();


            double inputValue = Double.parseDouble(refundBalanceEntry.getText().toString());
            String input = refundBalanceEntry.getText().toString().trim();

            if (input.isEmpty()) {
                Toast.makeText(this, "U heeft geen bedrag ingevoerd", Toast.LENGTH_SHORT).show();
            } else {
                double currentValue = Double.parseDouble(currentBalance.getText().toString());

                if (currentValue <= 0.01) {
                    Toast.makeText(this, "U kunt niet €0.00 terugstorten op uw rekening.", Toast.LENGTH_SHORT).show();
                } else if (currentValue < 5.00) {
                    Toast.makeText(this, "U kunt niet minder dan 5 euro terugstorten.", Toast.LENGTH_SHORT).show();
                } else if (currentValue < inputValue) {
                    Toast.makeText(this, "U heeft een bedrag ingevuld dat hoger is dan wat er op uw account staat", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "U heeft " + input + " euro van uw account teruggestort.", Toast.LENGTH_SHORT).show();
                    currentBalance.setText(0);
                }
            }
        }
    }
}
