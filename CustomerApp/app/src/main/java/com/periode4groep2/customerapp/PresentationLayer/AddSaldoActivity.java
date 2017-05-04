package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView

import com.periode4groep2.customerapp.R;

import static android.R.attr.value;

public class AddSaldoActivity extends AppCompatActivity implements View.OnClickListener {
    EditText saldoToevoegen;
    Button stortKnop;
    EditText firstNumber;
    EditText secondNumber;
    TextView addResult;


    double num1,num2,sum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saldo);
        saldoToevoegen = (EditText) findViewById(R.id.addsaldoId);
        String value = "" + saldoToevoegen.getText();
        stortKnop = (Button) findViewById(R.id.stortButton);
        stortKnop.setOnClickListener(this);
        firstNumber = (EditText)findViewById(R.id.buttonBalance);
        secondNumber = (EditText)findViewById(R.id.addsaldoId);
        addResult = (TextView)findViewById(R.id.addsaldoId);
        stortKnop = (Button)findViewById(R.id.stortButton);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Saldo opgewaardeerd met:" +  value, Toast.LENGTH_SHORT).show();
    }
    public static int addone(int a)
    {
        int b = a+1;
        return b;
    }
}



