package com.ashutosh.mishra.logbook.customer;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ashutosh.mishra.logbook.R;
import com.ashutosh.mishra.logbook.models.Customer;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddJobsFragment extends Fragment {

    EditText jobName, jobTime, jobComments;
    String jobRoutine;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    TextView dateTextView;
    String dateString;
    RadioGroup radioGroup;
    Button addBtn;
    Customer customer;

    public AddJobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_jobs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null){
            customer = new Gson().fromJson(getArguments().getString("cust_data"), Customer.class);
        }

        Log.v("cust_iud", customer.getId().toString());

        jobName = (EditText) getView().findViewById(R.id.edittext_job_name_add_job_fragment);
        jobTime = (EditText) getView().findViewById(R.id.edittext_job_est_time_add_job_fragment);
        jobComments = (EditText) getView().findViewById(R.id.edittext_job_comment_add_job_fragment);
        radioGroup = (RadioGroup) getView().findViewById(R.id.radio_group_customer_add_job_fragment);




        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateString = dateFormat.format(Calendar.getInstance().getTime());

        setDatePicker();
        dateTextView = (TextView) getView().findViewById(R.id.date_header_textview_customer_add_jobs_fragment);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        addBtn = (Button) getView().findViewById(R.id.add_btn_add_jobs_fragment);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int routineId = 0;
                int radioBtnId = radioGroup.getCheckedRadioButtonId();
                if (radioBtnId != -1){
                    View radioBtn = radioGroup.findViewById(radioBtnId);
                    routineId = radioGroup.indexOfChild(radioBtn);
                    routineId++;

                    if (jobName.getText().toString().length() > 0 && jobTime.getText().toString().length() > 0 && jobComments.getText().toString().length() > 0){
                        addJobToServer(jobName.getText().toString(), jobTime.getText().toString(), jobComments.getText().toString(), String.valueOf(routineId));
                    } else {
                        Toast.makeText(getContext(), "fill valid details....", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "select routine..", Toast.LENGTH_LONG).show();
                }

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

    private void addJobToServer(final String jobName, final String jobTime, final String jobComment, final String routine){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.softgators.com/appapi/index.php/v1/employer-create-job", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("volleyResAddJobs", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equalsIgnoreCase("Job has been created")){
                        Toast.makeText(getContext(), "Job Created..", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "something went wrong..", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("volleyErrAddJobs", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer_id", customer.getId().toString());
                params.put("created_date", dateString);
                params.put("routine_id", routine);
                params.put("name", jobName);
                params.put("estimated_time", jobTime);
                params.put("comment", jobComment);
                Log.v("addjobParam", params.toString());
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }
}
