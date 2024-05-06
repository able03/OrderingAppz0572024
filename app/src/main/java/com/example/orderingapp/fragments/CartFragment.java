package com.example.orderingapp.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderingapp.CartClickedListener;
import com.example.orderingapp.DBHelper;
import com.example.orderingapp.OnCheckedChangeListener;
import com.example.orderingapp.R;
import com.example.orderingapp.adapters.CartAdapter;
import com.example.orderingapp.models.AccountStaticModel;
import com.example.orderingapp.models.CartModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;


public class CartFragment extends Fragment {



    CartAdapter adapter;
    RecyclerView rv;
    List<CartModel> cartModelList;
    DBHelper dbHelper;
    ImageView iv_empty;
    TextView tv_empty;
    MaterialButton btn_checkout;
    private DatePickerDialog dialog;

    Stack<CartModel> cartModelStack;

    AlertDialog.Builder builder_checkout;
    AlertDialog alert_checkout;

    TextInputLayout lo_delivery_date;
    TextInputEditText et_delivery_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragments_cart_temp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initValues();

        Toast.makeText(getContext(), "User ID: " + AccountStaticModel.getId(), Toast.LENGTH_SHORT).show();


        int id = AccountStaticModel.getId();
        Cursor cursor = dbHelper.getCart(id);

        while (cursor.moveToNext()) {
            int cart_id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            int customer_id = cursor.getInt(cursor.getColumnIndexOrThrow("account_id"));
            int product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            int imgRID = cursor.getInt(cursor.getColumnIndexOrThrow("imgRID"));

            Toast.makeText(getContext(), "Cart ID: " + cart_id +
                    " Customer ID: " + customer_id +
                    "Product ID: " + product_id + "Name: " + name + " Price: " + price + " ImageRID: " + imgRID, Toast.LENGTH_SHORT).show();

            cartModelList.add(new CartModel(cart_id, customer_id, product_id, name, price, 0, imgRID));
        }


        adapter.setCartList(cartModelList);

        rv.setAdapter(adapter);

        if (cartModelList.isEmpty())
        {
            iv_empty.setVisibility(View.VISIBLE);
            tv_empty.setVisibility(View.VISIBLE);
        }
        else
        {
            iv_empty.setVisibility(View.INVISIBLE);
            tv_empty.setVisibility(View.INVISIBLE);
        }

        adapter.setListener(new CartClickedListener() {
            @Override
            public void itemClicked(CartModel cartModel) {
               if(cartModelList.isEmpty())
               {
                   iv_empty.setVisibility(View.VISIBLE);
                   tv_empty.setVisibility(View.VISIBLE);
               }
               if(!cartModelList.isEmpty())
               {
                   iv_empty.setVisibility(View.INVISIBLE);
                   tv_empty.setVisibility(View.INVISIBLE);
               }
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCheckoutDialog();
            }
        });


//        cartModelStack = new Stack<>();
        adapter.setChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void checkChanged(CartModel cartModel)
            {
                if(cartModel.isChecked())
                {
                    Log.d("Debugging", cartModel.getProduct_name() + ": is " + cartModel.isChecked());
                }
                else
                {
                    Log.d("Debugging", cartModel.getProduct_name() + ": is " + cartModel.isChecked());
                }

            }
        });



        dbHelper.close();
    }

    private void initValues()
    {
        cartModelList = new ArrayList<>();
        dbHelper = new DBHelper(getContext());
        adapter = new CartAdapter(getContext());
        rv = getView().findViewById(R.id.rvCart);

        iv_empty = getView().findViewById(R.id.ivCartEmpty);
        tv_empty = getView().findViewById(R.id.tvCartEmpty);

        btn_checkout = getView().findViewById(R.id.btnCheckout);
    }

    private void showDatePickerDialog()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                et_delivery_date.setText(String.format("%04d", year) + "/" + String.format("%02d", month+1) + "/" + String.format("%02d", dayOfMonth));
            }
        }, year, month, day);

        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis()+2000);
        Log.d("Debugging", "Time in millis: " + calendar.getTimeInMillis());
        dialog.show();
    }

    private void showCheckoutDialog()
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.checkout_dialog_layout, null);


        lo_delivery_date = view.findViewById(R.id.loDeliveryDate);
        et_delivery_date = view.findViewById(R.id.etDeliveryDate);

        lo_delivery_date.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        builder_checkout = new AlertDialog.Builder(getContext());
        builder_checkout.setView(view);
        alert_checkout = builder_checkout.create();

        alert_checkout.show();
    }





}