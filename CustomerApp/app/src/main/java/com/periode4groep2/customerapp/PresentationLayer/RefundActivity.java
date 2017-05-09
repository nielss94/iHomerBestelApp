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

    //Domain objects
    //private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);

        TextView currentBalanceHeader = (TextView)findViewById(R.id.currentBalanceHeaderTextView);
        TextView currentBalance = (TextView)findViewById(R.id.currentBalanceTextView);
        EditText refundBalanceEntry = (EditText)findViewById(R.id.refundBalanceEntryEditText);
        Button refundBalanceButton = (Button)findViewById(R.id.refundBalanceButton);
            refundBalanceButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(v.equals(refundBalanceButton)){
            Toast.makeText(this, "Refund balance clicked", Toast.LENGTH_SHORT).show();

            double currentValue = Double.parseDouble(currentBalance.getText().toString());
            double entryValue = Double.parseDouble(refundBalanceEntry.getText().toString());
            double newValue =  currentValue - entryValue;

            currentBalance.setText(Double.toString(newValue));
            //Refund logic !<=0, refund value != 0, Refund cap.

        }
        else if(v.equals(refundBalanceButton)){

            Toast.makeText(this, "Refund balance clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
