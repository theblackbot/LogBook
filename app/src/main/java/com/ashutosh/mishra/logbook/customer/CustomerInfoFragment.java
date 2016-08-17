package com.ashutosh.mishra.logbook.customer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashutosh.mishra.logbook.R;
import com.ashutosh.mishra.logbook.models.Customer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerInfoFragment extends Fragment {

    Customer customer;
    TextView name, address;
    ImageView imageView;


    public CustomerInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null){
            customer = new Gson().fromJson(getArguments().getString("cust_data"), Customer.class);
        }

        name = (TextView) getView().findViewById(R.id.textview_name_customer_info_fragment);
        name.setText(customer.getFullName());
        address = (TextView) getView().findViewById(R.id.textview_address_customer_info_fragment);
        address.setText(customer.getAddress());

        imageView = (ImageView) getView().findViewById(R.id.imageview_image_customer_info_fragment);
        Picasso.with(getContext()).load(customer.getImage()).into(imageView);
    }
}
