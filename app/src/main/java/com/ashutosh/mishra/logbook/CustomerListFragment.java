package com.ashutosh.mishra.logbook;


import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ashutosh.mishra.logbook.customer.CustomerActivity;
import com.ashutosh.mishra.logbook.models.Customer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerListFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Customer> customerData;


    public CustomerListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customerData = new ArrayList<>();

        getCustomerList();

    }

    private void setRecyclerAdapter(){
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview_customer_list_fragment);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new CustomerListRecyclerAdapter(customerData));
    }

    public class CustomerListRecyclerAdapter extends RecyclerView.Adapter<CustomerListRecyclerAdapter.ViewHolder>{

        List<Customer> customerList;


        public CustomerListRecyclerAdapter(List<Customer> list) {
            this.customerList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_card, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Customer customer = customerList.get(position);
            Picasso.with(getContext()).load(customer.getImage()).into(holder.imageView);
            holder.textView.setText(customer.getFullName());
            holder.rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    String custData = gson.toJson(customer);
                    startActivity(new Intent(getContext(), CustomerActivity.class).putExtra("cust_data", custData));
                }
            });
        }

        @Override
        public int getItemCount() {
            return customerList.size();
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

    private void getCustomerList(){

        customerData.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.softgators.com/appapi/index.php/v1/customers/list/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("volleyResGetCustList", response);

                Gson gson = new Gson();

                try {
                    JSONObject mainObject = new JSONObject(response);
                    if (mainObject.getString("status").contains("true")){
                        JSONArray custArray = mainObject.getJSONArray("customers");
                        for (int i = 0; i < custArray.length(); i++){
                            Customer customer = gson.fromJson(custArray.getJSONObject(i).toString(), Customer.class);
                            customerData.add(customer);
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
                Log.v("volleyErrGetCustList", error.toString());
            }
        });

        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}
