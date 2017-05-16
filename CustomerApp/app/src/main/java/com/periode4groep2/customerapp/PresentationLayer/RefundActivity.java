//package com.periode4groep2.customerapp.PresentationLayer;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.periode4groep2.customerapp.R;
//
//import org.w3c.dom.Text;
//
//public class RefundActivity extends AppCompatActivity implements View.OnClickListener{
//    //
//    //UI elements
//    private TextView currentBalanceHeader;
//    private TextView euroSymbolTextView;
//    private TextView currentBalanceTextView;
//    private EditText refundBalanceEntry;
//    private Button refundBalanceButton;
//    double totalBalance = 0;
//
//    //Domain objects
//    //private Account account;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_refund);
//
//        currentBalanceHeader = (TextView)findViewById(R.id.currentBalanceHeaderTextView);
//        euroSymbolTextView = (TextView)findViewById(R.id.euroSymbolTextView);
//        currentBalanceTextView = (TextView)findViewById(R.id.currentBalanceTextView);
//        refundBalanceEntry = (EditText)findViewById(R.id.refundBalanceEntryEditText);
//
//        Button refundBalanceButton = (Button)findViewById(R.id.refundBalanceButton);
//            refundBalanceButton.setOnClickListener(this);
//
//    }
//
//    @Override
//    public void onClick(View v){
//        String input = refundBalanceEntry.getText().toString().trim();
//        double currentBalanceValue = Double.parseDouble(currentBalanceTextView.getText().toString());
//
//                if (input.isEmpty()) {
//                    Toast.makeText(this, "U heeft geen bedrag ingevoerd.", Toast.LENGTH_SHORT).show();
//                } else {
//                    double currentEntryValue = Double.parseDouble(input);
//
//                    if (currentEntryValue <= 0.01) {
//                        Toast.makeText(this, "U kunt niet €0.00 terugstorten op uw rekening.", Toast.LENGTH_SHORT).show();
//                    }
//                    else if (currentEntryValue < 5.00) {
//                        Toast.makeText(this, "U kunt niet minder dan 5 euro terugstorten.", Toast.LENGTH_SHORT).show();
//                    }
//                    else if (currentEntryValue > currentBalanceValue) {
//                        Toast.makeText(this, "U heeft een bedrag ingevuld dat hoger is dan wat er op uw account staat.", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        double newBalanceValue = currentBalanceValue - currentEntryValue;
//
//                        Toast.makeText(this, "U heeft " + input + " euro van uw account teruggestort.", Toast.LENGTH_SHORT).show();
//                        currentBalanceTextView.setText(String.format("%.2f", newBalanceValue));
//
//                        refundBalanceEntry.setText("");
//                    }
//                }
//            }
//        }
