package com.reactive.livebus.Fragments;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.livebus.Adapter.BookingAdapter;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.databinding.FragmentBookingBinding;
import com.reactive.livebus.model.BookingClass;

import java.util.ArrayList;
import java.util.List;


public class BookingFragment extends Fragment {


    final String TAG = BookingFragment.class.getSimpleName();
    FragmentBookingBinding binding;
    BookingAdapter adapter;
    List<BookingClass> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_booking,container,false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recycler.hasFixedSize();
        binding.recycler.setLayoutManager(linearLayoutManager);
        adapter = new BookingAdapter(getContext(),list);
        binding.recycler.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    void getData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.BOOKING);
        databaseReference.orderByChild("userId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                BookingClass bookingClass = snapshot1.getValue(BookingClass.class);
                                list.add(bookingClass);
                            }
                            adapter.notifyDataSetChanged();
                        }else {
                            Log.i(TAG,"data not exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i(TAG,error.getMessage());
                    }
                });
    }
}