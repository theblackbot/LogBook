package com.ashutosh.mishra.logbook.customer;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ashutosh.mishra.logbook.R;
import com.ashutosh.mishra.logbook.models.Customer;
import com.ashutosh.mishra.logbook.models.Job;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class CustomerJobsFragment extends Fragment {

    RecyclerView recyclerView;
    Customer customer;
    String dateString;
    RecyclerView.LayoutManager layoutManager;
    List<Job> jobsData;
    JobsRecyclerAdapter jobsRecyclerAdapter;
    ImageView addJobBtn;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    TextView dateTextView;

    public CustomerJobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_jobs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null){
            customer = new Gson().fromJson(getArguments().getString("cust_data"), Customer.class);
        }

        jobsData = new ArrayList<>();


        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateString = dateFormat.format(Calendar.getInstance().getTime());


        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview_customer_jobs_fragment);

        addJobBtn = (ImageView) getView().findViewById(R.id.imageview_add_customer_jobs_fragment);
        addJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("cust_data", new Gson().toJson(customer));
                Fragment fragment = new AddJobsFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container_customer_activity, fragment).commit();
            }
        });



        setDatePicker();
        dateTextView = (TextView) getView().findViewById(R.id.date_header_textview_customer_jobs_fragment);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });




//        weekCalendar = (WeekCalendar) getView().findViewById(R.id.weekcalendar_customer_jobs_fragment);
//        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
//            @Override
//            public void onDateClick(DateTime dateTime) {
//                Log.v("dateSelected", dateTime.toString().substring(0, 10));
//                dateString = dateTime.toString().substring(0, 10);
//                getJobsList();
//            }
//        });

        getJobsList();






    }

    class JobsRecyclerAdapter extends RecyclerView.Adapter<JobsRecyclerAdapter.ViewHolder>{

        List<Job> jobList;

        public JobsRecyclerAdapter(List<Job> jobs) {
            this.jobList = jobs;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_jobs, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Job job = jobList.get(position);
            holder.jobName.setText(job.getJobName());
            holder.jobMoreInfo.setText(job.getComment());
        }

        @Override
        public int getItemCount() {
            return jobList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView jobName, jobMoreInfo;

            public ViewHolder(View itemView) {
                super(itemView);

                jobName = (TextView) itemView.findViewById(R.id.textview_job_item_customer_job);
                jobMoreInfo = (TextView) itemView.findViewById(R.id.textview_info_item_customer_job);
            }
        }
    }

    private void setRecyclerAdapter(){
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        jobsRecyclerAdapter = new JobsRecyclerAdapter(jobsData);
        recyclerView.setAdapter(jobsRecyclerAdapter);
    }


    private void getJobsList(){

        jobsData.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.softgators.com/appapi/index.php/v1/customer-job-list-for-employer", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("volleyResGetJobs", response);
                try {
                    JSONObject mainObject = new JSONObject(response);
                    if (mainObject.getString("status").contains("true")){
                        JSONArray jsonArray = mainObject.getJSONArray("jobs");
                        for (int i = 0; i< jsonArray.length(); i++){
                            Job job = new Job();
                            JSONObject object = jsonArray.getJSONObject(i);
                            job.setJobName(object.getString("name"));
                            job.setComment(object.getString("comment"));
                            jobsData.add(job);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (recyclerView.getAdapter() == null){
                                    setRecyclerAdapter();
                                } else {
                                    jobsRecyclerAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    } else {
                        Snackbar.make(getView(), "something went wrong..", Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("volleyErrGetJobs", error.toString());

                jobsData.clear();
                if (recyclerView.getAdapter() == null){
                    setRecyclerAdapter();
                } else {
                    jobsRecyclerAdapter.notifyDataSetChanged();
                }

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer_id", String.valueOf(customer.getId()));
                params.put("date", dateString);
                Log.v("getJobsParams", params.toString());
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        Volley.newRequestQueue(getContext()).add(stringRequest);
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

                getJobsList();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }
}
