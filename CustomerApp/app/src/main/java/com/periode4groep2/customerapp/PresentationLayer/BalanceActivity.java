package com.periode4groep2.customerapp.PresentationLayer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import static android.R.id.input;

public class BalanceActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mutateBalance;
    private Button addBalance;
    private Button refundBalance;
    private TextView currentBalanceTextView;
    private Account account;
    private DAOFactory factory;
    private AccountDAO accountDAO;
    private final double maxBalance = 150;
    private double currentBalanceValue;


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

        currentBalanceValue = Double.parseDouble(currentBalanceTextView.getText().toString().replaceAll("€", "0"));

    }

    @Override
    public void onClick(View v) {
        if ( v.equals(addBalance) ) {

            String input = mutateBalance.getText().toString().trim().replaceAll("€", "0");
            double currentEntryValue = 0;

            if(!input.isEmpty()){
                currentEntryValue = Double.parseDouble(input);
                if (currentBalanceValue == 150){
                    Toast.makeText(this, R.string.max_amount_toast, Toast.LENGTH_SHORT).show();
                }
                else if ( currentBalanceValue + currentEntryValue > 150.00 || currentEntryValue >= 150.00 ) {
                    createMaxDialog();
                }
                else {
                    createAcceptationDialog();
                }
                mutateBalance.setText("");
            }
            else if ( input.isEmpty() || currentEntryValue == 0) {
                Toast.makeText(this, R.string.no_amount_entered_toast, Toast.LENGTH_SHORT).show();
            }


        } else if ( v.equals(refundBalance) ) {

            String input = mutateBalance.getText().toString().trim().replaceAll("€", "0");
            double currentEntryValue = 0;

            if (!input.isEmpty()) {
                currentEntryValue = Double.parseDouble(input);
                if (currentEntryValue == 0) {
                    Toast.makeText(this, R.string.not_possible_to_add_nothing_toast, Toast.LENGTH_SHORT).show();
                }
                if (currentEntryValue > currentBalanceValue) {
                    createRefundAllDialog();
                } else {
                    createRefundDialog();
                }
                mutateBalance.setText("");
            }
            else {
                createRefundAllDialog();
            }
        }
    }

    public void createMaxDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getResources().getString(R.string.max_balance_dialog_message));
        builder.setPositiveButton(getResources().getString(R.string.balance_dialog_positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        double amountToMax = maxBalance - currentBalanceValue;
                        currentBalanceValue += amountToMax;
                        account.setBalance(currentBalanceValue);
                        accountDAO.updateBalance(account, (amountToMax * 100));
                        currentBalanceTextView.setText("€" + String.format("%.2f", currentBalanceValue));
                        dialogInterface.dismiss();
                    }
                });

        builder.setNegativeButton(getResources().getString(R.string.balance_dialog_negative_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createRefundAllDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getResources().getString(R.string.refund_balance_dialog_message));
        builder.setPositiveButton(getResources().getString(R.string.balance_dialog_positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                currentBalanceValue = 0;
                account.setBalance(currentBalanceValue);
                accountDAO.updateBalance(account, -(currentBalanceValue * 100));
                currentBalanceTextView.setText("€" + String.format("%.2f", currentBalanceValue));
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.balance_dialog_negative_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createAcceptationDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        final String getBalance = mutateBalance.getText().toString();

        builder.setMessage("Weet u zeker dat u " + getBalance.trim() + " euro wil toevoegen?");
        builder.setPositiveButton(getResources().getString(R.string.balance_dialog_positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                double currentEntryValue = Double.parseDouble(getBalance.trim().replaceAll("€", "0"));
                currentBalanceValue = currentBalanceValue + currentEntryValue;

                currentBalanceTextView.setText("€" + String.format("%.2f", currentBalanceValue));
                account.setBalance(currentBalanceValue);
                accountDAO.updateBalance(account,currentEntryValue * 100);
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.balance_dialog_negative_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createRefundDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        final String getBalance = mutateBalance.getText().toString();
        builder.setMessage("Weet u zeker dat u " + getBalance + " euro van uw account wil terugstorten?");
        builder.setPositiveButton(getResources().getString(R.string.balance_dialog_positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                double currentEntryValue = Double.parseDouble(getBalance.trim().replaceAll("€", "0"));
                currentBalanceValue = currentBalanceValue - currentEntryValue;

                currentBalanceTextView.setText("€"+String.format("%.2f", currentBalanceValue));
                account.setBalance(currentBalanceValue);
                accountDAO.updateBalance(account,-(currentEntryValue* 100));
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.balance_dialog_negative_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }


}


