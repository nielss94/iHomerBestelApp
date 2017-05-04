package com.periode4groep2.customerapp.PresentationLayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.periode4groep2.customerapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button)findViewById(R.id.inlogKnopId);
        loginButton.setOnClickListener(this);
    }

    //Deze button moet nog veranderd worden wanneer de gegevens kloppen etc
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}