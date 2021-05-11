package com.reactive.livebus.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.reactive.livebus.Adapter.StudentBusAdapter;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.databinding.FragmentHomeBinding;
import com.reactive.livebus.model.BusClass;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    final String TAG = HomeFragment.class.getSimpleName();
    FragmentHomeBinding binding;
    StudentBusAdapter adapter;
    List<BusClass> list = new ArrayList<>();
    String temp = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recycler.hasFixedSize();
        binding.recycler.setLayoutManager(linearLayoutManager);
        adapter = new StudentBusAdapter(getContext(),list);
        binding.recycler.setAdapter(adapter);

        binding.destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getData(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return binding.getRoot();
    }

    void getData(String data){
        if (!temp.equals(data)){
            temp = data;
            Query query = FirebaseDatabase.getInstance().getReference(Constants.BUS).orderByChild(Constants.DESTINATION)
                    .startAt(data)
                    .endAt(data+"\uf8ff");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    if (dataSnapshot.exists()){
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            BusClass busClass = snapshot.getValue(BusClass.class);
                            busClass.setId(snapshot.getKey());
                            Log.i(TAG,busClass.toString());
                            list.add(busClass);
                        }
                        adapter.notifyDataSetChanged();
                    }else {
                        Log.i(TAG,"Dont exist");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.i(TAG,databaseError.getMessage());
                }
            });
        }

    }
}