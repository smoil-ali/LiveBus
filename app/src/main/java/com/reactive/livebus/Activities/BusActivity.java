package com.reactive.livebus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.livebus.Adapter.StopsAdapter;
import com.reactive.livebus.R;
import com.reactive.livebus.Interfaces.StopListener;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.databinding.ActivityBusBinding;
import com.reactive.livebus.model.BusClass;
import com.reactive.livebus.model.StopClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BusActivity extends AppCompatActivity implements StopListener {

    final String TAG = BusActivity.class.getSimpleName();
    ActivityBusBinding binding;
    BusClass busClass;
    StopsAdapter adapter;
    List<StopClass> list = new ArrayList<>();
    List<StopClass> finalList = new ArrayList<>();
    ProgressDialog progressDialog;
    boolean isEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bus);
        busClass = new BusClass();
        if (getIntent().getExtras() != null){
            busClass = (BusClass)getIntent().getExtras().getSerializable(Constants.PARAMS);
            isEdit = true;
            binding.delete.setVisibility(View.VISIBLE);
            finalList.addAll(busClass.getList());
        }
        binding.setData(busClass);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(linearLayoutManager);
        binding.recycler.hasFixedSize();
        adapter = new StopsAdapter(this,list);
        binding.recycler.setAdapter(adapter);
        adapter.setListener(this);
        progressDialog = new ProgressDialog(this);



        binding.delete.setOnClickListener(v -> {
            progressDialog.setMessage("Wait, while data is removing...");
            progressDialog.setTitle("Delete Bus");
            delete();
        });

        binding.stopButton.setOnClickListener(v -> {
            openScreen();
        });

        binding.starttime.setOnClickListener(v -> {
            setTime("Select Start Time",true);
        });

        binding.arrivaltime.setOnClickListener(v -> {
            setTime("Select Arrival Time",false);
        });

        binding.done.setOnClickListener(v -> {
            if (isValid()){
                busClass.setList(finalList);
                busClass.setActualPrice(busClass.getFare());
                if (!isEdit){
                    Log.i(TAG,busClass.toString());
                    progressDialog.setMessage("Wait, while data is uploading...");
                    progressDialog.setTitle("Add Bus");
                    setData();
                }else {
                    progressDialog.setMessage("Wait, while data is uploading...");
                    progressDialog.setTitle("Update Bus");
                    update();
                }
            }
        });

        getData();


    }

    void setTime(String title,boolean check){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new
                TimePickerDialog(BusActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (check){
                    binding.starttime.setText(hourOfDay+":"+minute);
                }else {
                    binding.arrivaltime.setText(hourOfDay+":"+minute);
                }
            }
        }, hour,minutes,false);
        timePickerDialog.setTitle(title);
        timePickerDialog.show();
    }

    boolean isValid(){
        busClass.setDisplayError(true);
        if (!busClass.getBusNumberError().isEmpty()){
            return false;
        }
        if (!busClass.getStartPointError().isEmpty()){
            return false;
        }
        if (!busClass.getStartPointLatError().isEmpty()){
            return false;
        }
        if (!busClass.getStartPointLngError().isEmpty()){
            return false;
        }
        if (!busClass.getDestinationError().isEmpty()){
            return false;
        }
        if (!busClass.getDestinationLatError().isEmpty()){
            return false;
        }
        if (!busClass.getDestinationLngError().isEmpty()){
            return false;
        }
        if (!busClass.getStartTimeError().isEmpty()){
            return false;
        }
        if (!busClass.getArrivalTimeError().isEmpty()){
            return false;
        }
        if (!busClass.getTotalSeatsError().isEmpty()){
            return false;
        }
        if (!busClass.getFareError().isEmpty()){
            return false;
        }
        busClass.setDisplayError(false);
        return true;
    }

    void openScreen(){
        Intent intent = new Intent(this,StopsActivity.class);
        startActivity(intent);
    }

    void getData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.STOPS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    StopClass stopClass = snapshot1.getValue(StopClass.class);
                    stopClass.setId(snapshot1.getKey());
                    list.add(stopClass);
                }
                adapter.notifyDataSetChanged();
                if (isEdit){
                    setList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG,error.getMessage());
            }
        });
    }


    void setList(){
        for (StopClass model:busClass.getList()) {
            for (int i=0;i<list.size();i++) {
                if (list.get(i).getId().equals(model.getId())){
                    list.get(i).setSelected(true);
                }
            }
        }
    }

    void update(){
        progressDialog.show();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.BUS);
        databaseReference.child(busClass.getId())
                .setValue(busClass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        onBackPressed();
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
        progressDialog.show();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.BUS);
        databaseReference.push()
                .setValue(busClass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
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

    void delete(){
        progressDialog.show();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.BUS);
        databaseReference.child(busClass.getId())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        onBackPressed();
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

    @Override
    public void onStopAddClick(StopClass model) {
        finalList.add(model);
    }

    @Override
    public void onStopRemoveClick(StopClass model) {
        for (int i = 0;i<finalList.size();i++){
            if (finalList.get(i).getId().equals(model.getId())){
                finalList.remove(i);
                break;
            }
        }
    }
}