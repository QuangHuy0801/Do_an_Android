package com.example.doan_nhom_6.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.doan_nhom_6.R;

public class AdminActivity extends AppCompatActivity {
    ImageView ivHomeAdmin, ivIFAdmin, ivInventory, ivRQOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setControl();
        setEvent();
    }

    private void setControl() {
        ivIFAdmin=findViewById(R.id.ivIFAdmin);
        ivRQOrder=findViewById(R.id.ivRQOrder);
        ivInventory=findViewById(R.id.ivInventory);
        ivHomeAdmin=findViewById(R.id.ivHomeAdmin);
    }


    private void setEvent() {
        appBarClick();
    }

    private void appBarClick() {
        ivHomeAdmin.setOnClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, MainDashboardActivity.class));
            finish();
        });
        ivIFAdmin.setOnClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, AdminActivity.class));
            finish();
        });
        ivInventory.setOnClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, InventoryActivity.class));
            finish();
        });

        ivRQOrder.setOnClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, RQOrderActivity.class));
            finish();
        });
    }
}