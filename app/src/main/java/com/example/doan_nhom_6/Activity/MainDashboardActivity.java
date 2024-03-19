package com.example.doan_nhom_6.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doan_nhom_6.Adapter.StatisticAdapter;
import com.example.doan_nhom_6.Model.Statistic;
import com.example.doan_nhom_6.Model.User;
import com.example.doan_nhom_6.R;
import com.example.doan_nhom_6.Somethings.ObjectSharedPreferences;

import java.util.ArrayList;

public class MainDashboardActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    TextView tvHiNameAdmin;
    ImageView ivAvatarAdmin;
    User Admin;
    ListView listView;
    ImageView  ivHomeAdmin, ivIFAdmin, ivInventory, ivRQOrder;
    ArrayList<Statistic> data = new ArrayList<>();
    StatisticAdapter statisticAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        setControl();
        setEvent();
    }

    private void setEvent() {
        appBarClick();
    }
    private void appBarClick() {
        ivHomeAdmin.setOnClickListener(v -> {
            startActivity(new Intent(MainDashboardActivity.this, MainDashboardActivity.class));
            finish();
        });
        ivIFAdmin.setOnClickListener(v -> {
            startActivity(new Intent(MainDashboardActivity.this, AdminActivity.class));
            finish();
        });
        ivInventory.setOnClickListener(v -> {
            startActivity(new Intent(MainDashboardActivity.this, InventoryActivity.class));
            finish();
        });

        ivRQOrder.setOnClickListener(v -> {
            startActivity(new Intent(MainDashboardActivity.this, RQOrderActivity.class));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadUserInfor();
    }

    private void LoadUserInfor() {
        statisticAdapter = new StatisticAdapter(this, R.layout.activity_statistic_row, data);
        listView.setAdapter(statisticAdapter);
        Admin = ObjectSharedPreferences.getSavedObjectFromPreference(MainDashboardActivity.this, "Admin", "MODE_PRIVATE", User.class);
        tvHiNameAdmin.setText("Hi "+ Admin.getUser_Name());
        try {
            Glide.with(getApplicationContext()).load(Admin.getAvatar()).into(ivAvatarAdmin);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setControl() {
        tvHiNameAdmin = findViewById(R.id.tvHiNameAdmin);
        ivAvatarAdmin = findViewById(R.id.ivAvatarAdmin);
        listView = findViewById(R.id.lvListStatistic);
        data.add(new Statistic(1, "Doanh thu", "Xem thông tin về doanh thu của cửa hàng"));
        data.add(new Statistic(2, "Sản phẩm", "Xem thống kê về tổng số lượng sản phẩm và phân loại của chúng"));
        data.add(new Statistic(3, "Order", "Xem thống kê về các đơn hàng đã được đặt trong cửa hàng"));
        ivIFAdmin=findViewById(R.id.ivIFAdmin);
        ivRQOrder=findViewById(R.id.ivRQOrder);
        ivInventory=findViewById(R.id.ivInventory);
        ivHomeAdmin=findViewById(R.id.ivHomeAdmin);
    }
}