package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyAccountAvailable {
    private Button loginButton;
    private Toolbar toolbar;
    private DAOFactory factory;
    private AccountDAO accountDAO;
    private EditText email, password;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        factory = new MySQLDAOFactory();
        accountDAO = factory.createAccountDAO();

        loginButton = (Button) findViewById(R.id.inlogKnopId);
        loginButton.setOnClickListener(this);

        email = (EditText)findViewById(R.id.emailInputId);
        password = (EditText)findViewById(R.id.passwordInputId);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        setSupportActionBar(toolbar);
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
    }  .

    public void logIn(){

        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.putExtra("account", account);
        startActivity(intent);
        Toast.makeText(this, R.string.successful_log_in_toast, Toast.LENGTH_SHORT).show();

    }
}