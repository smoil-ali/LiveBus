package com.reactive.livebus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.databinding.ActivityDriverBinding;
import com.reactive.livebus.model.DriverClass;

public class DriverActivity extends AppCompatActivity {

    final String TAG = DriverActivity.class.getSimpleName();
    ActivityDriverBinding binding;
    DriverClass driverClass;
    ProgressDialog progressDialog;
    boolean isEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_driver);
        driverClass = new DriverClass();
        if (getIntent().getExtras() != null){
            driverClass = (DriverClass)getIntent().getExtras().getSerializable(Constants.PARAMS);
            isEdit = true;
            binding.delete.setVisibility(View.VISIBLE);
        }
        binding.setData(driverClass);
        progressDialog = new ProgressDialog(this);
        binding.delete.setOnClickListener(v -> {
            progressDialog.setMessage("Wait, while data is removing...");
            progressDialog.setTitle("Delete Bus");
            delete();
        });

        binding.done.setOnClickListener(v -> {
            if (isValid()){
                if (!isEdit){
                    Log.i(TAG,driverClass.toString());
                    progressDialog.setMessage("Wait, while data is uploading...");
                    progressDialog.setTitle("Add Driver");
                    setData();
                }else {
                    progressDialog.setMessage("Wait, while data is uploading...");
                    progressDialog.setTitle("Update Driver");
                    update();
                }
            }
        });

    }


    boolean isValid(){
        driverClass.setDisplayError(true);
        if (!driverClass.getNameError().isEmpty()){
            return false;
        }
        if (!driverClass.getCnicError().isEmpty()){
            return false;
        }
        if (!driverClass.getPasswordError().isEmpty()){
            return false;
        }
        if (!driverClass.getAssignedBusError().isEmpty()){
            return false;
        }
        driverClass.setDisplayError(false);
        return true;
    }


    void setData(){
        progressDialog.show();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.DRIVER);
        databaseReference.push().setValue(driverClass)
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

    private void update() {
        progressDialog.show();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.DRIVER);
        databaseReference.child(driverClass.getId())
                .setValue(driverClass)
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

    void delete(){
        progressDialog.show();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.DRIVER);
        databaseReference.child(driverClass.getId())
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
}