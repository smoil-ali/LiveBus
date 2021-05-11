package com.reactive.livebus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.livebus.Adapter.StopBookingAdapter;
import com.reactive.livebus.Adapter.StudentBusAdapter;
import com.reactive.livebus.Interfaces.StudentStopListener;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.databinding.ActivityStudentBusBinding;
import com.reactive.livebus.model.BookingClass;
import com.reactive.livebus.model.BusClass;
import com.reactive.livebus.model.CounterClass;
import com.reactive.livebus.model.StopClass;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;

public class StudentBusActivity extends AppCompatActivity implements StudentStopListener {

    final String TAG = StudentBusActivity.class.getSimpleName();
    ActivityStudentBusBinding binding;
    BusClass busClass;
    BookingClass bookingClass;
    CounterClass counterClass;
    ProgressDialog progressDialog;
    StopBookingAdapter adapter;
    StopClass stopClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_student_bus);
        busClass = new BusClass();
        bookingClass = new BookingClass();
        progressDialog = new ProgressDialog(this);
        if (getIntent().getExtras() != null){
            busClass = (BusClass)getIntent().getExtras().getSerializable(Constants.PARAMS);
            StopClass stopClass = new StopClass();
            stopClass.setStopName(busClass.getDestination());
            stopClass.setLat(busClass.getDestinationLat());
            stopClass.setLng(busClass.getDestinationLng());
            busClass.getList().add(stopClass);
        }
        binding.setData(busClass);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recycler.hasFixedSize();
        binding.recycler.setLayoutManager(linearLayoutManager);
        adapter = new StopBookingAdapter(this,busClass.getList());
        adapter.setListener(this);
        binding.recycler.setAdapter(adapter);

        binding.numberPicker.setMin(1);
        binding.numberPicker.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                int price = Integer.parseInt(busClass.getActualPrice());
                price = price * value;
                busClass.setFare(price+"");
                bookingClass.setSeats(value+"");
            }
        });

        binding.map.setOnClickListener(v -> {
            openScreen(busClass);
        });

        binding.done.setOnClickListener(v -> {
            if (isValid()){
                int counter;
                progressDialog.setTitle("Booking");
                progressDialog.setMessage("Wait, while your booking is being done...");
                progressDialog.show();
                bookingClass.setFare(busClass.getFare());
                bookingClass.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                bookingClass.setBusClass(busClass);
                bookingClass.setDestination(stopClass.getStopName());
                if (!counterClass.getCounter().isEmpty()){
                    counter = Integer.parseInt(counterClass.getCounter());
                }else {
                    counter = 0;
                }
                if (bookingClass.getSeats().isEmpty()){
                    bookingClass.setSeats("1");
                }
                counter = counter + Integer.parseInt(bookingClass.getSeats());
                counterClass.setCounter(counter+"");
                counterClass.setBusId(busClass.getId());
                setData();
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getCounterVal();
    }

    void openScreen(BusClass busClass){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(Constants.PARAMS,busClass);
        startActivity(intent);
    }

    void getCounterVal(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.COUNTER);
        databaseReference.child(busClass.getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressDialog.dismiss();
                        if (snapshot.exists()){
                            counterClass = snapshot.getValue(CounterClass.class);
                            int remain = Integer.parseInt(busClass.getTotalSeats()) - Integer.parseInt(counterClass.getCounter());
                            if (remain < 0){
                                binding.seats.setText(0+"");
                            }else {
                                binding.seats.setText(remain+"");
                            }
                            Log.i(TAG,counterClass.toString());
                        }else {
                            counterClass = new CounterClass();
                            binding.seats.setText(busClass.getTotalSeats());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i(TAG,error.getMessage());
                        progressDialog.dismiss();
                    }
                });
    }

    void setCounterVal(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.COUNTER);
        databaseReference.child(busClass.getId())
                .setValue(counterClass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Log.i(TAG,"Counter added");
                        Log.i(TAG,counterClass.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG,e.getMessage());
                        progressDialog.dismiss();
                    }
                });
    }

    void setData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.BOOKING);
        databaseReference.push().setValue(bookingClass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG,"Data added");
                        Log.i(TAG,bookingClass.toString());
                        setCounterVal();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG,e.getMessage());
                    }
                });

    }

    @Override
    public void onStopClick(StopClass stopClass) {
        if (stopClass.isSelected2()){
            this.stopClass = stopClass;
        }else {
            this.stopClass = null;
        }
    }

    boolean isValid(){
        if (stopClass == null){
            Toast.makeText(this, "select stop!!!", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
}