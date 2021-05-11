package com.reactive.livebus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.databinding.ActivityStopsBinding;
import com.reactive.livebus.model.StopClass;

public class StopsActivity extends AppCompatActivity {

    final String TAG = StopsActivity.class.getSimpleName();
    ActivityStopsBinding binding;
    StopClass stopClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_stops);
        binding.setVisibility(true);
        stopClass = new StopClass();
        binding.setData(stopClass);

        binding.done.setOnClickListener(v -> {
            if (isValid()){
                binding.setVisibility(false);
                setData();
            }
        });


    }

    boolean isValid(){
        stopClass.setDisplayError(true);
        if (!stopClass.getStopNameError().isEmpty()){
            return false;
        }
        if (!stopClass.getLatError().isEmpty()){
            return false;
        }
        if (!stopClass.getLngError().isEmpty()){
            return false;
        }
        stopClass.setDisplayError(false);
        return true;
    }

    void setData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.STOPS);
        databaseReference.push().setValue(stopClass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        binding.setVisibility(true);
                        Toast.makeText(StopsActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG,e.getMessage());
                        Toast.makeText(StopsActivity.this, "try again", Toast.LENGTH_SHORT).show();
                        binding.setVisibility(true);
                    }
                });
    }
}