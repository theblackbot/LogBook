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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ashutosh.mishra.logbook.AppConstants;
import com.ashutosh.mishra.logbook.R;
import com.ashutosh.mishra.logbook.models.Customer;
import com.ashutosh.mishra.logbook.models.Employee;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllotFragment extends Fragment {

    RecyclerView recyclerView;
    List<Employee> employeeData;
    RecyclerView.LayoutManager layoutManager;
    Customer customer;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    TextView dateTextView;
    String dateString;

    public AllotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_allot, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null){
            customer = new Gson().fromJson(getArguments().getString("cust_data"), Customer.class);
        }


        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateString = dateFormat.format(Calendar.getInstance().getTime());


        employeeData = new ArrayList<>();

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview_emp_allot_fragment);

        getEmployeeList();

        setDatePicker();
        dateTextView = (TextView) getView().findViewById(R.id.date_header_textview_allot_emp_fragment);
        dateTextView.setText(dateString);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });


    }

    private void setRecyclerAdapter(){
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new EmployeeListRecyclerAdapter(employeeData));
    }

    public class EmployeeListRecyclerAdapter extends RecyclerView.Adapter<EmployeeListRecyclerAdapter.ViewHolder>{

        List<Employee> employeeList;


        public EmployeeListRecyclerAdapter(List<Employee> list) {
            this.employeeList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_card, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Employee employee = employeeList.get(position);
            Picasso.with(getContext()).load(employee.getImage()).into(holder.imageView);
            holder.textView.setText(employee.getFullName());
            holder.rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    String empData = gson.toJson(employee);
                    Bundle bundle = new Bundle();
                    bundle.putString("emp_data", empData);
                    bundle.putString("cust_data", new Gson().toJson(customer));
                    Fragment fragment = new AllotTwoFragment();
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container_customer_activity, fragment, AppConstants.TAG_CUSTOMER_TIME_TWO_FRAGMENT).commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return employeeList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView textView;
            LinearLayout rootLayout;

            public ViewHolder(View itemView) {
                super(itemView);

                imageView = (ImageView) itemView.findViewById(R.id.imageview_customer_item_card);
                textView = (TextView) itemView.findViewById(R.id.textview_customer_item_card);
                rootLayout = (LinearLayout) itemView.findViewById(R.id.view_subroot_item_customer_card);
            }
        }
    }

    private void getEmployeeList(){

        employeeData.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.softgators.com/appapi/index.php/v1/employee/list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("volleyResGetEmpList", response);

                Gson gson = new Gson();

                try {
                    JSONObject mainObject = new JSONObject(response);
                    if (mainObject.getString("status").contains("true")){
                        JSONArray empArray = mainObject.getJSONArray("employees");
                        for (int i = 0; i < empArray.length(); i++){
                            Employee employee = gson.fromJson(empArray.getJSONObject(i).toString(), Employee.class);
                            employeeData.add(employee);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setRecyclerAdapter();
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
                Log.v("volleyErrGetEmptList", error.toString());
            }
        });

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

                getEmployeeList();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }
}
