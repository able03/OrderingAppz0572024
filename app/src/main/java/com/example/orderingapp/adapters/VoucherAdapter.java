package com.example.orderingapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderingapp.R;
import com.example.orderingapp.models.VoucherModel;

import java.util.ArrayList;

public class VoucherAdapter extends BaseAdapter {
    ArrayList<VoucherModel> voucherList;
    Context context;

    public VoucherAdapter(Context context, ArrayList<VoucherModel> voucherList) {
        this.context = context;
        this.voucherList = voucherList;
    }

    @Override
    public int getCount() {
        return voucherList.size();
    }

    @Override
    public Object getItem(int i) {
        return voucherList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.voucher_layout, viewGroup, false);
        }

        ImageView iv = view.findViewById(R.id.ivVoucherImage);
        iv.setImageResource(voucherList.get(i).getImgRID());

        Button btn_claim = view.findViewById(R.id.btnClaimVoucher);

        btn_claim.setOnClickListener(claim -> {
            Log.d("Debugging", "Click");
        });
        return view;


    }
}
