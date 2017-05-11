package com.periode4groep2.customerapp.PresentationLayer;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.R;

public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener{
    Account account;
    private Button saveSettings, balanceButton;
    private TextView textviewEmail, textviewFirstName, textviewLastName, textviewBirthDate;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

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

        calculateAge();
    }

    @Override
    public void onClick(View v) {
        if(v.equals(saveSettings)) {
            this.finish();
            Toast.makeText(this, R.string.save_settings_toast, Toast.LENGTH_SHORT).show();
        } else if(v.equals(balanceButton)){
            Intent addBalanceIntent = new Intent(this, AddBalanceActivity.class);
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
}
