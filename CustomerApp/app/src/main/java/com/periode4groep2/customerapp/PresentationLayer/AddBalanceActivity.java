package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.PersistancyLayer.AccountDAO;
import com.periode4groep2.customerapp.PersistancyLayer.AccountSetAvailable;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.R;

import java.util.ArrayList;


public class AddBalanceActivity extends AppCompatActivity implements View.OnClickListener, AccountSetAvailable {
    private EditText putInBalance;
    private Button addBalance;
    private TextView currentBalance;
    double subtotal = 0;
    private DAOFactory factory;
    private ArrayList<Account> accounts = new ArrayList<>();
    private final String TAG = getClass().getSimpleName();
    private AccountDAO accountDAO;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);
        putInBalance = (EditText) findViewById(R.id.giveBalanceID);
        currentBalance = (TextView) findViewById(R.id.currentBalance);
        currentBalance.setText("");

        factory = new MySQLDAOFactory();
        accountDAO = factory.createAccountDAO();
        accountDAO.selectData(this);

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

            //Een if else statement om ervoor te zorgen dat er geen bedrag boven de 150 euro komt
            if (currBalance <= 0.01) {
                Toast.makeText(this, R.string.no_amount_entered_toast, Toast.LENGTH_SHORT).show();
                } else if (subtotal >= 150.00) {
                    Toast.makeText(this, R.string.too_much_money_toast, Toast.LENGTH_SHORT).show();
                } else if (subtotal + currBalance > 150.00) {
                    Toast.makeText(this, R.string.too_much_money_toast, Toast.LENGTH_SHORT).show();
                } else {
                    //Strings gemaakt zodat er maar 1 string in de toast hoeft te staan
                    String youHave = getString(R.string.you_have_toast);
                    String amountAdded = getString(R.string.amount_added_toast);
                    String toastText = youHave + " " + input + " " + amountAdded;

                    Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
                    subtotal += currBalance;
                    currentBalance.setText(String.format("%.2f", subtotal));
                }
            putInBalance.setText("");
        }
    }

    @Override
    public void accountSetAvailable(ArrayList<Account> accs) {
        accounts = accs;
        for (int i = 0; i < accounts.size(); i++) {
            Log.i(TAG,accounts.get(i).toString());
        }
        setBalanceText();
    }

    public void setBalanceText(){
        for (int i = 0; i < accounts.size(); i++){
//            if (){
//
//            }
        }
    }
}
