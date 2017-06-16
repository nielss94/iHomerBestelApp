package com.periode4groep2.employeeapp.PresentationLayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.PersistancyLayer.AccountDAO;
import com.periode4groep2.employeeapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.employeeapp.PersistancyLayer.MyAccountAvailable;
import com.periode4groep2.employeeapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.employeeapp.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyAccountAvailable {
    private final String TAG = getClass().getSimpleName();
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
        Drawable homeButton = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_homebutton);
        toolbar.setNavigationIcon(homeButton);
        toolbar.setTitle(R.string.employee_main_title);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(loginButton)){
            accountDAO.selectData(this,email.getText().toString(),password.getText().toString());
        }
    }

    @Override
    public void myAccountAvailable(Account acc) {
        account = acc;
        if(account.isEmployee()) {
            logIn();
        }else{
            Toast.makeText(this, R.string.failed_log_in_no_employee_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void myAccountNotAvailable() {
        Toast.makeText(this, R.string.account_not_found_toast, Toast.LENGTH_SHORT).show();
    }

    public void logIn(){
        Intent intent = new Intent(this, HomeScreenActivity.class);
        intent.putExtra("account", account);
        startActivity(intent);
        Toast.makeText(this, R.string.successful_log_in_toast, Toast.LENGTH_SHORT).show();
    }
}