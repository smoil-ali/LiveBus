package com.reactive.livebus.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.livebus.Activities.BusActivity;
import com.reactive.livebus.Activities.DriverActivity;
import com.reactive.livebus.Adapter.DriverAdapter;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.databinding.FragmentDriverBinding;
import com.reactive.livebus.model.BusClass;
import com.reactive.livebus.model.DriverClass;

import java.util.ArrayList;
import java.util.List;


public class DriverFragment extends Fragment {


    final String TAG = DriverFragment.class.getSimpleName();
    FragmentDriverBinding binding;
    DriverAdapter adapter;
    List<DriverClass> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_driver,container,false);
        binding.setVisibility(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recycler.setLayoutManager(linearLayoutManager);
        binding.recycler.hasFixedSize();
        adapter = new DriverAdapter(getContext(),list);
        binding.recycler.setAdapter(adapter);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.add.setOnClickListener(v -> {
            openScreen();
        });
        getData();
    }

    void openScreen(){
        Intent intent = new Intent(getContext(), DriverActivity.class);
        startActivity(intent);
    }

    void getData(){
        showProgressBar();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.DRIVER);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                hideProgressBar();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    DriverClass driverClass = snapshot1.getValue(DriverClass.class);
                    driverClass.setId(snapshot1.getKey());
                    list.add(driverClass);
                }
                if (list.size() > 0){
                    adapter.notifyDataSetChanged();
                    binding.setVisibility(true);
                }else {
                    binding.setVisibility(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideProgressBar();
                binding.setVisibility(false);
                Log.i(TAG,error.getMessage());
            }
        });
    }

    public void showProgressBar(){
        binding.progress.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        binding.progress.setVisibility(View.GONE);
    }
}