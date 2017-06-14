package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.PersistancyLayer.AccountDAO;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MyAccountAvailable;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.R;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyAccountAvailable {
    private Button loginButton;
    private Toolbar toolbar;
    private DAOFactory factory;
    private AccountDAO accountDAO;
    private EditText email, password;
    private Account account;
    private String currentLanguage;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        factory = new MySQLDAOFactory();
        accountDAO = factory.createAccountDAO();

        loginButton = (Button) findViewById(R.id.inlogKnopId);
        loginButton.setOnClickListener(this);

        email = (EditText)findViewById(R.id.emailInputId);
        password = (EditText)findViewById(R.id.passwordInputId);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.Home_Screen_toolbar);
        setSupportActionBar(toolbar);

        editor.remove("Email");

        currentLanguage = sharedPreferences.getString("LANGUAGE", null);

        if (currentLanguage == null){
            currentLanguage = "nl";
            languageChanged();
        } else if (currentLanguage != null){
            languageChanged();
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        //Check if email/password is empty
        accountDAO.selectData(this,email.getText().toString(),password.getText().toString());
    }

    @Override
    public void myAccountAvailable(Account acc) {
        account = acc;
        logIn();
    }

    @Override
    public void myAccountNotAvailable() {
        Toast.makeText(this, R.string.account_not_found_toast, Toast.LENGTH_SHORT).show();
    }

    public void logIn(){

        editor.putString("Email", account.getEmail());
        editor.commit();
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.putExtra("account", account);
        startActivity(intent);
        Toast.makeText(this, R.string.successful_log_in_toast, Toast.LENGTH_SHORT).show();
    }

    public void languageChanged(){
        Configuration mainConfig = new Configuration(getResources().getConfiguration());
        Locale locale = new Locale(currentLanguage);
        Locale.setDefault(locale);
        mainConfig.setLocale(locale);
        getResources().updateConfiguration(mainConfig, null);
    }
}