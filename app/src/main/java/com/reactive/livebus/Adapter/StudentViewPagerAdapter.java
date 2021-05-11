package com.reactive.livebus.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.reactive.livebus.Fragments.BookingFragment;
import com.reactive.livebus.Fragments.BusFragment;
import com.reactive.livebus.Fragments.DriverFragment;
import com.reactive.livebus.Fragments.HomeFragment;

public class StudentViewPagerAdapter extends FragmentStateAdapter {
    private final int MAX_NUMBER = 2;
    public StudentViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new BookingFragment();
            default:
                return null;
        }
    }


    @Override
    public int getItemCount() {
        return MAX_NUMBER;
    }
}
