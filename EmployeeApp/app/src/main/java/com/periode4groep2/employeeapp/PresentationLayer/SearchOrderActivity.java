package com.periode4groep2.employeeapp.PresentationLayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.periode4groep2.employeeapp.DomainModel.Account;
import com.periode4groep2.employeeapp.PersistancyLayer.OrderSetAvailable;
import com.periode4groep2.employeeapp.R;

public class SearchOrderActivity extends AppCompatActivity {
    //implements View.OnClickListener, OrderSetAvailable
    private final String TAG = getClass().getSimpleName();

    private ListView orderListView;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_order);
    }
}
