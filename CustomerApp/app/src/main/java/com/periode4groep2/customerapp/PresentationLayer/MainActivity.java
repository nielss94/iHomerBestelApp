package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.periode4groep2.customerapp.DomainModel.Account;
import com.periode4groep2.customerapp.PersistancyLayer.AccountDAO;
import com.periode4groep2.customerapp.PersistancyLayer.AccountSetAvailable;
import com.periode4groep2.customerapp.PersistancyLayer.DAOFactory;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLAccountDAO;
import com.periode4groep2.customerapp.PersistancyLayer.MySQLDAOFactory;
import com.periode4groep2.customerapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AccountSetAvailable {

    private final String TAG = getClass().getSimpleName();
    private Button loginButton;
    private Toolbar toolbar;
    private Button balanceButton;
    private DAOFactory factory;
    private AccountDAO accountDAO;
    private ArrayList<Account> accounts = new ArrayList<>();
    private EditText email, password;
    private Account account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        factory = new MySQLDAOFactory();
        accountDAO = factory.createAccountDAO();
        accountDAO.selectData(this);

        loginButton = (Button) findViewById(R.id.inlogKnopId);
        loginButton.setOnClickListener(this);

        email = (EditText)findViewById(R.id.emailInputId);
        password = (EditText)findViewById(R.id.passwordInputId);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        balanceButton = (Button) findViewById(R.id.buttonBalance);
        balanceButton.setOnClickListener(this);
    }


    //Deze button moet nog veranderd worden wanneer de gegevens kloppen etc
    @Override
    public void onClick(View v) {
        //Toast.makeText(this, "Succesvol ingelogd met:" + userName, Toast.LENGTH_SHORT).show();
        if(v.equals(loginButton)) {

            for (int i = 0; i < accounts.size(); i++){
                if(email.getText().toString().equals(accounts.get(i).getEmail())){
                    if (password.getText().toString().equals(accounts.get(i).getPassword())){
                        account = accounts.get(i);
                        Intent intent = new Intent(this, HomeScreenActivity.class);
                        intent.putExtra("account",account);
                        startActivity(intent);
                        Toast.makeText(this, "Succesvol ingelogd", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Fout tijdens het inloggen", Toast.LENGTH_SHORT).show();
                    }
                }else if(!email.getText().toString().equals(accounts.get(i).getEmail())){
                    Toast.makeText(this, "Onjuist email adres", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (v.equals(balanceButton)){
            Intent intent = new Intent(this, AddBalanceActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void accountSetAvailable(ArrayList<Account> accs) {

        accounts = accs;
        for (int i = 0; i < accounts.size(); i++) {

            Log.i(TAG,accounts.get(i).toString());
        }
    }
}