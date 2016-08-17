package com.ashutosh.mishra.logbook.customer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashutosh.mishra.logbook.AppConstants;
import com.ashutosh.mishra.logbook.R;
import com.ashutosh.mishra.logbook.models.Customer;
import com.google.gson.Gson;

public class CustomerActivity extends AppCompatActivity {

    Customer customer;
    TextView title;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        if (getIntent().getStringExtra("cust_data") != null){
            customer = new Gson().fromJson(getIntent().getStringExtra("cust_data"), Customer.class);
        } else {
            finish();
        }

        title = (TextView) findViewById(R.id.textview_title_customer_activity);

        title.setText(customer.getFullName());

        backBtn = (ImageView) findViewById(R.id.imageview_back_customer_activity);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide();

        setListenersOnView();

        Fragment fragment = new CustomerInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cust_data", new Gson().toJson(customer));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container_customer_activity, fragment, AppConstants.TAG_CUSTOMER_INFO_FRAGMENT).commit();


    }

    private void setListenersOnView(){
        RelativeLayout infoBtn = (RelativeLayout) findViewById(R.id.view_info_button_customer_activity);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("cust_data", new Gson().toJson(customer));
                Fragment fragment = new CustomerInfoFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_customer_activity, fragment, AppConstants.TAG_CUSTOMER_INFO_FRAGMENT).commit();
            }
        });


        RelativeLayout jobsBtn = (RelativeLayout) findViewById(R.id.view_jobs_button_customer_activity);
        jobsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("cust_data", new Gson().toJson(customer));
                Fragment fragment = new CustomerJobsFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_customer_activity, fragment, AppConstants.TAG_CUSTOMER_JOBS_FRAGMENT).commit();
            }
        });


        RelativeLayout calendarBtn = (RelativeLayout) findViewById(R.id.view_calendar_button_customer_activity);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("cust_data", new Gson().toJson(customer));
                Fragment fragment = new CustomerCalendarFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_customer_activity, fragment, AppConstants.TAG_CUSTOMER_CALENDAR_FRAGMENT).commit();

            }
        });


        RelativeLayout timeBtn = (RelativeLayout) findViewById(R.id.view_time_button_customer_activity);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("cust_data", new Gson().toJson(customer));
                Fragment fragment = new AllotFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_customer_activity, fragment, AppConstants.TAG_CUSTOMER_TIME_ONE_FRAGMENT).commit();

            }
        });
    }

}
