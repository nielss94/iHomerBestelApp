package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.periode4groep2.customerapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginButton;
    private Toolbar toolbar;
    private Button balanceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button)findViewById(R.id.inlogKnopId);
        loginButton.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        balanceButton = (Button) findViewById(R.id.buttonBalance);
        balanceButton.setOnClickListener(this);
    }

    //Deze button moet nog veranderd worden wanneer de gegevens kloppen etc
    @Override
    public void onClick(View v) {
        if(v.equals(loginButton)) {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        } else if (v.equals(balanceButton)){
            Intent intent = new Intent(this, add_saldo.class);
            startActivity(intent);
        }

    }

}