package com.example.doan_nhom_6.Activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doan_nhom_6.Adapter.AdminOrderAdapter;
import com.example.doan_nhom_6.Model.Order;
import com.example.doan_nhom_6.R;
import com.example.doan_nhom_6.Retrofit.OrderAPI;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class RQOrderFragment extends Fragment {
    RecyclerView rvAllOrder;
    AdminOrderAdapter adminOrderAdapter;
    List<Order> orderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_r_q_order, container, false);
        anhXa(view);
        loadData();
        return view;
    }

    private void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvAllOrder.setLayoutManager(linearLayoutManager);
        OrderAPI.orderAPI.getAllOrder().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orderList = response.body();
                adminOrderAdapter = new AdminOrderAdapter(orderList, getContext());
                rvAllOrder.setAdapter(adminOrderAdapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("====", "Call API Get Order fail");
            }
        });
    }


    private void anhXa(View view) {
        rvAllOrder = view.findViewById(R.id.rcvOrder);
    }
}