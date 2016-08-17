package com.ashutosh.mishra.logbook.customer;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ashutosh.mishra.logbook.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllotTwoFragment extends Fragment {


    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    TextView dateTextView;
    String dateString;


    public AllotTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_time, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateString = dateFormat.format(Calendar.getInstance().getTime());

        setDatePicker();
        dateTextView = (TextView) getView().findViewById(R.id.date_header_textview_allot_emp_two_fragment);
        dateTextView.setText(dateString);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

    }

    private void setDatePicker(){

        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(year, monthOfYear, dayOfMonth);
                dateString = dateFormat.format(calendar1.getTime());
                dateTextView.setText(dateString);

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }
}
