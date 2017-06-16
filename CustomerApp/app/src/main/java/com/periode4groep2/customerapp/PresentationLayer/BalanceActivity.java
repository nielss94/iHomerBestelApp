package com.periode4groep2.customerapp.PresentationLayer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
    private Toolbar toolbar;
    private DAOFactory factory;
    private AccountDAO accountDAO;
    private final double maxBalance = 150;
    private double currentBalanceValue;
    private double epsilon = 0.00001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.Balance_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BalanceActivity.this, HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

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

        currentBalanceValue = account.getBalance() / 100;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.putExtra("account", account);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if ( v.equals(addBalance) ) {

            String input = mutateBalance.getText().toString().trim().replaceAll("€", "0");
            double currentEntryValue = 0;

            if(!input.isEmpty()){
                currentEntryValue = Double.parseDouble(input);
                // Controleer of het verschil tussen de twee waarden kleiner is dan x
                // equality tests mogen niet met floating points
                if (Math.abs(currentBalanceValue - 150) < epsilon){
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
            else if ( input.isEmpty() || (Math.abs(currentEntryValue - 0) < epsilon)) {
                Toast.makeText(this, R.string.no_amount_entered_toast, Toast.LENGTH_SHORT).show();
            }


        } else if ( v.equals(refundBalance) ) {

            String input = mutateBalance.getText().toString().trim().replaceAll("€", "0");
            double currentEntryValue = 0;

            if (!input.isEmpty()) {
                currentEntryValue = Double.parseDouble(input);
                if (Math.abs(currentEntryValue - 0) < epsilon) {
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

                double refundAll = 0;
                account.setBalance(refundAll);
                accountDAO.updateBalance(account, -(currentBalanceValue * 100));
                currentBalanceTextView.setText("€" + String.format("%.2f", refundAll));
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

        builder.setMessage(getResources().getString(R.string.are_you_sure_dialog_message1) + getBalance.trim() + getResources().getString(R.string.add_balance_message));
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
        builder.setMessage(getResources().getString(R.string.are_you_sure_dialog_message2) + getBalance + getResources().getString(R.string.refund_from_account_message));
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


