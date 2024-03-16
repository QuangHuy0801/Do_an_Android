package com.example.doan_nhom_6.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doan_nhom_6.Fragment.AllOrderFragment;
import com.example.doan_nhom_6.Fragment.PayOnDeliveryFragment;

public class OrderFragmentAdapter extends FragmentStateAdapter {


    public OrderFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0)
            return new AllOrderFragment();
        else {
            return new PayOnDeliveryFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
