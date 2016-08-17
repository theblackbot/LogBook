package com.ashutosh.mishra.logbook.customer;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ashutosh.mishra.logbook.R;
import com.ashutosh.mishra.logbook.models.Customer;
import com.ashutosh.mishra.logbook.models.JobDate;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerCalendarFragment extends Fragment {

    GridView gridView;
    CalendarGridAdapter calendarGridAdapter;
    List<JobDate> jobDates;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    TextView dateTextView;
    String dateString;
    Customer customer;
    int maxDate;


    public CustomerCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_calendar, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null){
            customer = new Gson().fromJson(getArguments().getString("cust_data"), Customer.class);
        }


        jobDates = new ArrayList<>();

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateString = dateFormat.format(Calendar.getInstance().getTime());


        maxDate = Integer.valueOf(Utils.getLastDay(dateString));
        for (int i = 1; i <= maxDate; i++){
            JobDate jobDate = new JobDate();
            jobDate.setDate(String.valueOf(i));
            jobDates.add(jobDate);
        }

        gridView = (GridView) getView().findViewById(R.id.gridview_customer_calendar_fragment);

        setDatePicker();
        dateTextView = (TextView) getView().findViewById(R.id.textview_date_customer_calendar_fragment);
        dateTextView.setText(dateString);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        setAdapter();
    }

    private void setAdapter(){
        calendarGridAdapter = new CalendarGridAdapter(jobDates);
        gridView.setAdapter(calendarGridAdapter);
    }

    private class CalendarGridAdapter extends BaseAdapter {

        List<JobDate> jobDateList;

        public CalendarGridAdapter(List<JobDate> jobs) {
            this.jobDateList = jobs;
        }

        @Override
        public int getCount() {
            return jobDateList.size();
        }

        @Override
        public Object getItem(int position) {
            return jobDateList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;
            ViewHolder viewHolder = null;
            if (item == null) {
                item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_calendar, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.dateText = (TextView) item.findViewById(R.id.textview_date_item_calendar);

                JobDate jobDate = jobDateList.get(position);

                viewHolder.dateText.setText(jobDate.getDate());

            } else {
                viewHolder = (ViewHolder) item.getTag();
            }

            return item;
        }

        class ViewHolder {
            TextView dateText;
        }
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

                getDateList();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }

    private void getDateList(){

        jobDates.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.softgators.com/appapi/index.php/v1/customer-calender", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("volleyResGetDates", response);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        maxDate = Integer.valueOf(Utils.getLastDay(dateString));
                        for (int i = 1; i <= maxDate; i++){
                            JobDate jobDate = new JobDate();
                            jobDate.setDate(String.valueOf(i));
                            jobDates.add(jobDate);
                        }
                        calendarGridAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("volleyErrGetDates", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer_id", customer.getId().toString());
                params.put("date", dateString);
                Log.v("calendarParams", params.toString());
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }



}
