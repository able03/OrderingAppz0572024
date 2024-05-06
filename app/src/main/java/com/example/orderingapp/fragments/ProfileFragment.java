package com.example.orderingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.orderingapp.R;
import com.example.orderingapp.models.AccountStaticModel;


public class ProfileFragment extends Fragment {


    private TextView tv_name, tv_address, tv_contact;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initValues();

       if(AccountStaticModel.getId() > 0)
       {
           tv_name.setText(AccountStaticModel.getName());
           tv_address.setText(AccountStaticModel.getAddress());
           tv_contact.setText(AccountStaticModel.getPhone());
       }
       else
       {
           tv_name.setText("");
           tv_address.setText("");
           tv_contact.setText("");
       }
    }

    private void initValues()
    {
        view = getView();
        tv_name = view.findViewById(R.id.tvProfileName);
        tv_address = view.findViewById(R.id.tvProfileAddress);
        tv_contact = view.findViewById(R.id.tvProfileContactNo);
    }
}