package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.PersistancyLayer.AccountDAO;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.R;

public class BalanceActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mutateBalance;
    private Button addBalance;
    private Button refundBalance;
    private TextView currentBalanceTextView;
    private Account account;
    private DAOFactory factory;
    private AccountDAO accountDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);

        factory = new MySQLDAOFactory();
        accountDAO = factory.createAccountDAO();

        mutateBalance = (EditText) findViewById(R.id.giveBalanceID);
        currentBalanceTextView = (TextView) findViewById(R.id.currentBalance);

        account = (Account) getIntent().getSerializableExtra("account");
        currentBalanceTextView.setText("€" + String.format("%.2f", account.getBalance() / 100) + "");

        addBalance = (Button) findViewById(R.id.addBalanceID);
        addBalance.setOnClickListener(this);

        refundBalance = (Button) findViewById(R.id.refundBalanceButton);
        refundBalance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ( v.equals(addBalance) ) {

            double currentBalanceValue = Double.parseDouble(currentBalanceTextView.getText().toString().replaceAll("€", "0"));
            String input = mutateBalance.getText().toString().trim().replaceAll("€", "0");
            double currentEntryValue = Double.parseDouble(input);

            if ( input.isEmpty() ) {
                Toast.makeText(this, R.string.no_amount_entered_toast, Toast.LENGTH_SHORT).show();
            } else {
                if ( currentEntryValue <= 0.01 ) {
                    Toast.makeText(this, R.string.no_amount_entered_toast, Toast.LENGTH_SHORT).show();
                } else if ( currentEntryValue >= 150.00 ) {
                    Toast.makeText(this, R.string.too_much_money_toast, Toast.LENGTH_SHORT).show();
                } else if ( currentBalanceValue + currentEntryValue > 150.00 ) {
                    Toast.makeText(this, R.string.too_much_money_toast, Toast.LENGTH_SHORT).show();
                } else {
                    String youHave = getString(R.string.you_have_toast);
                    String amountAdded = getString(R.string.amount_added_toast);
                    String toastText = youHave + " " + input + " " + amountAdded;

                    Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
                    double newBalanceValue = currentBalanceValue + currentEntryValue;
                    currentBalanceTextView.setText("€" + String.format("%.2f", newBalanceValue));
                    account.setBalance(newBalanceValue);
                    accountDAO.updateBalance(account,currentEntryValue * 100);
                }
                mutateBalance.setText("");
            }

        } else if ( v.equals(refundBalance) ) {

            double currentBalanceValue = Double.parseDouble(currentBalanceTextView.getText().toString().replaceAll("€", "0"));
            String input = mutateBalance.getText().toString().trim().replaceAll("€", "0");
            double currentEntryValue = Double.parseDouble(input);

            if ( input.isEmpty() ) {
                Toast.makeText(this, "U heeft geen bedrag ingevoerd.", Toast.LENGTH_SHORT).show();
            } else {
                if ( currentEntryValue <= 0.01 ) {
                    Toast.makeText(this, "U kunt niet €0.00 terugstorten op uw rekening.", Toast.LENGTH_SHORT).show();
                }
                else if ( currentEntryValue > currentBalanceValue ) {
                    Toast.makeText(this, "U heeft een bedrag ingevuld dat hoger is dan wat er op uw account staat.", Toast.LENGTH_SHORT).show();
                } else {
                    double newBalanceValue = currentBalanceValue - currentEntryValue;

                    Toast.makeText(this, "U heeft " + input + " euro van uw account teruggestort.", Toast.LENGTH_SHORT).show();
                    currentBalanceTextView.setText("€"+String.format("%.2f", newBalanceValue));
                    account.setBalance(newBalanceValue);
                    accountDAO.updateBalance(account,-(currentEntryValue* 100));
                    mutateBalance.setText("");
                }
            }
        }
    }
}
