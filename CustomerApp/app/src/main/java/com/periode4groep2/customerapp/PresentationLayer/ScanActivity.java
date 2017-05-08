package com.periode4groep2.customerapp.PresentationLayer;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.periode4groep2.customerapp.R;


/**
 * Created by Lars on 8-5-2017.
 */

public class ScanActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button;
    private ImageView checkicon;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        button = (Button)findViewById(R.id.scan_button);
        button.setOnClickListener(this);
        button.setClickable(false);

        checkicon = (ImageView)findViewById(R.id.scan_icon);
        checkicon.setVisibility(View.INVISIBLE);

        text = (TextView)findViewById(R.id.scan_textview);
        text.setText(R.string.scan_device);

        waitExample();
    }

    public void waitExample() {

        // Dit is een tijdelijke oplossing om het scannen te simuleren. De text wordt niet vervangen maar
        // dat komt door deze methode.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkicon.setVisibility(View.VISIBLE);
                button.setClickable(true);
                text.setText(R.string.scan_device_done);
            }
        }, 4000);
    }

    public void onClick(View v){
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
}
