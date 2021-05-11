package com.reactive.livebus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reactive.livebus.Adapter.AdminViewPagerAdapter;
import com.reactive.livebus.Adapter.StudentViewPagerAdapter;
import com.reactive.livebus.R;
import com.reactive.livebus.databinding.ActivityStudentBinding;

public class StudentActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    final String TAG = StudentActivity.class.getSimpleName();
    ActivityStudentBinding binding;
    StudentViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_student);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null)
            binding.bottomNavigation.setSelectedItemId(R.id.bus);

        viewPagerAdapter = new StudentViewPagerAdapter(this);
        binding.pager.setAdapter(viewPagerAdapter);
        binding.pager.setUserInputEnabled(false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bus:
                binding.pager.setCurrentItem(0);
                return true;
            case R.id.booking:
                binding.pager.setCurrentItem(1);
                return true;
        }
        return true;
    }
}