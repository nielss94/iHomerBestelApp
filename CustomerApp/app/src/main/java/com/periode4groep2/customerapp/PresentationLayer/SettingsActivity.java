package com.periode4groep2.customerapp.PresentationLayer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.R;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{
    Account account;
    private Button saveSettings, balanceButton;
    private Toolbar toolbar;
    private TextView textviewEmail, textviewFirstName, textviewLastName, textviewBirthDate, textviewIBAN;
    private Spinner spinner;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.Balance_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HomeScreenActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        saveSettings = (Button) findViewById(R.id.saveSettingsID);
        balanceButton = (Button) findViewById(R.id.buttonBalance);

        saveSettings.setOnClickListener(this);
        balanceButton.setOnClickListener(this);

        account = (Account)getIntent().getSerializableExtra("account");
        balanceButton.setText("€" + String.format("%.2f", account.getBalance()/100) + "");

        textviewEmail = (TextView)findViewById(R.id.my_account_email);
        textviewEmail.setText(account.getEmail());
        textviewFirstName = (TextView)findViewById(R.id.my_account_firstname);
        textviewFirstName.setText(account.getFirstName());
        textviewLastName = (TextView)findViewById(R.id.my_account_lastname);
        textviewLastName.setText(account.getLastName());
        textviewBirthDate = (TextView)findViewById(R.id.my_account_age);
        textviewIBAN = (TextView)findViewById(R.id.my_account_iban);
        textviewIBAN.setText(account.getIBAN());

        calculateAge();

        spinner = (Spinner) findViewById(R.id.language_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (pos == 0){

                } else if (pos == 1){
                    editor.putString("LANGUAGE", "nl");
                    editor.commit();
                    logIn();
                } else if (pos == 2){
                    editor.putString("LANGUAGE", "en");
                    editor.commit();
                    logIn();
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v.equals(saveSettings)) {
            Intent intent = new Intent (this, HomeScreenActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        } else if(v.equals(balanceButton)){
            Intent addBalanceIntent = new Intent(this, BalanceActivity.class);
            addBalanceIntent.putExtra("account", account);
            startActivity(addBalanceIntent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void calculateAge(){
        String fullBirthDate = account.getDateOfBirth();
        String yearsOfBirthDate = fullBirthDate.substring(0, 4);
        String monthOfBirthDate = fullBirthDate.substring(5,7);
        String dayOfBirthDate = fullBirthDate.substring(8, 10);
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        int birthYear = Integer.parseInt(yearsOfBirthDate);
        int birthMonth = Integer.parseInt(monthOfBirthDate);
        int birthDay = Integer.parseInt(dayOfBirthDate);

        int age = currentYear - birthYear;

        //logica om te kijken of je binnen de maand zit
        if (currentMonth == birthMonth && currentDay < birthDay){
            textviewBirthDate.setText((age - 1) + "");
        } else {
            textviewBirthDate.setText(age + "");
        }
    }

    public void logIn(){

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);

            builder.setMessage(R.string.dialog_message);
            builder.setPositiveButton(getResources().getString(R.string.dialog_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    dialogInterface.dismiss();
                }
            });

            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

