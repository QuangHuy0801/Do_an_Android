package com.example.doan_nhom_6.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_nhom_6.Adapter.CheckOutAdapter;
import com.example.doan_nhom_6.Model.Address;
import com.example.doan_nhom_6.Model.Cart;
import com.example.doan_nhom_6.Model.Order;
import com.example.doan_nhom_6.Model.User;
import com.example.doan_nhom_6.R;
import com.example.doan_nhom_6.Retrofit.CartAPI;
import com.example.doan_nhom_6.Retrofit.OrderAPI;
import com.example.doan_nhom_6.Somethings.ObjectSharedPreferences;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvUserName, tvAddress, tvTotalPrice, tvPhoneNumber, tvChangeAddress, tvAddAddress, tvPayWithZaloPay, tvPayOnDelivery;
    private Button btnPlaceOrder;
    private ConstraintLayout constraintLayoutAddress, constraintLayoutNotAddress, placeOrder, placeOrderSuccess;
    private RadioButton rbPayOnDelivery, rbPayWithZaloPay;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        setControl();
        setEvent();

    }
    private void setEvent() {
        ivBackClick();
        constraintLayoutNotAddressClick();
        tvChangeAddressClick();
        btnPlaceOrderClick();
        radioButtonClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadAddress();
        LoadProductItem();
    }

    private void radioButtonClick() {
        tvPayOnDelivery.setOnClickListener(v -> {
            rbPayOnDelivery.setChecked(true);
        });
        tvPayWithZaloPay.setOnClickListener(v -> {
            rbPayWithZaloPay.setChecked(true);
        });
    }

    private void btnPlaceOrderClick() {
        btnPlaceOrder.setOnClickListener(v -> {
            if (rbPayOnDelivery.isChecked()) {
                newOrder("Pay on Delivery");
            }
            else {
                Toast.makeText(getApplicationContext(), "Please choose a payment method", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void newOrder(String method) {
        Address address = ObjectSharedPreferences.getSavedObjectFromPreference(this, "address", "MODE_PRIVATE", Address.class);
        if (address == null) {
            Toast.makeText(this, "Please add your address before place order", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = ObjectSharedPreferences.getSavedObjectFromPreference(this, "User", "MODE_PRIVATE", User.class);
        if (user == null) {
            Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show();
            return;
        }

        OrderAPI.orderAPI.placeOrder(user.getId(), tvUserName.getText().toString(), tvPhoneNumber.getText().toString().replace("(", "").replace(")", ""), tvAddress.getText().toString(), method)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        Order order = response.body();
                        if (order != null) {
                            placeOrder.setVisibility(View.GONE);
                            placeOrderSuccess.setVisibility(View.VISIBLE);
                            Button btnContinueShopping = findViewById(R.id.btnContinueShopping);
                            btnContinueShopping.setOnClickListener(v -> {
                                placeOrder.setVisibility(View.VISIBLE);
                                placeOrderSuccess.setVisibility(View.GONE);
                                startActivity(new Intent(CheckOutActivity.this, MainActivity.class));
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Log.e("OrderAPI", "Failed to place order: " + t.getMessage());
                        Toast.makeText(getApplicationContext(), "Failed to place order", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void tvChangeAddressClick() {
        tvChangeAddress.setOnClickListener(v -> {
            startActivity(new Intent(this, AddressActivity.class));
            finish();
        });
    }

    private void constraintLayoutNotAddressClick() {
        constraintLayoutNotAddress.setOnClickListener(v -> {
            startActivity(new Intent(this, AddressActivity.class));
            finish();
        });
    }

    private void ivBackClick() {
        ivBack.setOnClickListener(v -> onBackPressed());
    }

    private void LoadProductItem() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        User user = ObjectSharedPreferences.getSavedObjectFromPreference(this, "User", "MODE_PRIVATE", User.class);
        if (user == null) {
            Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show();
            return;
        }

        CartAPI.cartAPI.cartOfUser(user.getId()).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                List<Cart> listCart = response.body();
                if (listCart != null) {
                    CheckOutAdapter checkOutAdapter = new CheckOutAdapter(listCart, CheckOutActivity.this);
                    recyclerView.setAdapter(checkOutAdapter);

                    int total = 0;
                    for (Cart cart : listCart) {
                        total += cart.getCount() * cart.getProduct().getPrice();
                    }
                    Locale localeEN = new Locale("en", "EN");
                    NumberFormat en = NumberFormat.getInstance(localeEN);
                    tvTotalPrice.setText(en.format(total));
                } else {
                    Log.e("LoadProductItem", "Failed to load cart items");
                    Toast.makeText(getApplicationContext(), "Failed to load cart items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Log.e("LoadProductItem", "Failed to fetch cart items: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LoadAddress() {
        Address address = ObjectSharedPreferences.getSavedObjectFromPreference(this, "address", "MODE_PRIVATE", Address.class);
        if (address != null) {
            tvPhoneNumber.setText("(" + address.getPhoneNumber() + ")");
            tvUserName.setText(address.getFullName());
            tvAddress.setText(address.getAddress());
        } else {
            constraintLayoutAddress.setVisibility(View.GONE);
            constraintLayoutNotAddress.setVisibility(View.VISIBLE);
        }
    }

    private void setControl() {
        ivBack = findViewById(R.id.ivBack);
        tvUserName = findViewById(R.id.tvUserName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvAddress = findViewById(R.id.tvAddress);
        tvChangeAddress = findViewById(R.id.tvChangeAddress);
        tvAddAddress = findViewById(R.id.tvAddAddress);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        constraintLayoutAddress = findViewById(R.id.constraintLayoutAddress);
        constraintLayoutNotAddress = findViewById(R.id.constraintLayoutNotAddress);
        placeOrderSuccess = findViewById(R.id.placeOrderSuccess);
        placeOrder = findViewById(R.id.placeOrder);
        tvPayWithZaloPay = findViewById(R.id.tvPayWithZaloPay);
        tvPayOnDelivery = findViewById(R.id.tvPayOnDelivery);
        rbPayWithZaloPay = findViewById(R.id.rbPayWithZaloPay);
        rbPayOnDelivery = findViewById(R.id.rbPayOnDelivery);
        recyclerView = findViewById(R.id.view);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        ZaloPaySDK.getInstance().onResult(intent);
    }
}