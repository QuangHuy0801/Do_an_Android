package com.example.doan_nhom_6.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_nhom_6.Adapter.AdminOrderAdapter;
import com.example.doan_nhom_6.Interface.UpdateStatusInterface;
import com.example.doan_nhom_6.Model.Order;
import com.example.doan_nhom_6.R;
import com.example.doan_nhom_6.Retrofit.OrderAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedOrderFragment extends Fragment implements UpdateStatusInterface{
    RecyclerView rvCompletedOrder;
    AdminOrderAdapter adminOrderAdapter;
    List<Order> completedOrderList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_order, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();
        LoadData();
    }

    private void LoadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        rvCompletedOrder.setLayoutManager(linearLayoutManager);
        UpdateStatusInterface updateStatusInterface = this;
        OrderAPI.orderAPI.getOrderByStatus("Completed").enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                completedOrderList = response.body();
                adminOrderAdapter = new AdminOrderAdapter(completedOrderList, getContext().getApplicationContext(), updateStatusInterface);
                rvCompletedOrder.setAdapter(adminOrderAdapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }

    private void AnhXa() {
        rvCompletedOrder = getView().findViewById(R.id.rvCompletedOrder);
    }

    public void ReloadDataOnTabLayoutChaged() {
        LoadData();
    }

    @Override
    public void ReloadData() {
        LoadData();
    }
}
