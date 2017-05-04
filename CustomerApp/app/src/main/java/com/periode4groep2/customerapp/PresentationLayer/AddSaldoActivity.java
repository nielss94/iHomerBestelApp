package com.periode4groep2.customerapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.periode4groep2.customerapp.R;


public class AddSaldoActivity extends AppCompatActivity implements View.OnClickListener {
    EditText saldoToevoegen;
    Button stortKnop, balance;
    CashCounter cashcounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saldo);
        saldoToevoegen = (EditText) findViewById(R.id.addsaldoId);

        stortKnop = (Button) findViewById(R.id.stortButton);
        stortKnop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        balance = (Button)findViewById(R.id.buttonBalance);
        TextView saldo = (TextView)findViewById(R.id.huidigSaldoTextMoney);
        //Hier laat ik een toast weergeven als de gebruiker op de button heeft geklikt
        //Hij pakt binnen de onCLick de gettext en veranderd deze naar een string zodat deze in de toast kan worden gedisplayed
        String input = saldoToevoegen.getText().toString();
        int getinput = Integer.parseInt(input);
        cashcounter = new CashCounter();
        cashcounter.Count(getinput);
        int total = cashcounter.getSubtotal();
        saldo.setText(String.format("%.2f", total));


        Toast.makeText(this, "U heeft " + input + " euro aan uw account toegevoegd", Toast.LENGTH_SHORT).show();

        //Hier komt de code voor de wallet die je in de title bar kan zien, deze wordt gelijk veranderd zodra je op de knop saldo
        //toevoegen klikt, De limieten van 150 euro en minstens 0 euro komen later.
    }
}

    class CashCounter {
    private int subtotal;
        public void Count(int price){
            subtotal += price;
        }

        public int getSubtotal() {
            return subtotal;
        }

}



