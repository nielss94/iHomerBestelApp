package com.periode4groep2.employeeapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.periode4groep2.employeeapp.R;

public class LoginTest extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_no_button);
        setSupportActionBar(toolbar);

    }
}
