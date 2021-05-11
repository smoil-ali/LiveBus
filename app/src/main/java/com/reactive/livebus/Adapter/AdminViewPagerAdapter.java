package com.reactive.livebus.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.reactive.livebus.Fragments.BusFragment;
import com.reactive.livebus.Fragments.DriverFragment;

public class AdminViewPagerAdapter extends FragmentStateAdapter {
    private final int MAX_NUMBER = 2;
    public AdminViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new BusFragment();
            case 1:
                return new DriverFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return MAX_NUMBER;
    }
}
