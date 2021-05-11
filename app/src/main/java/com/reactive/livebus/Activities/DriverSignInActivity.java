package com.reactive.livebus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.databinding.ActivityDriverBinding;
import com.reactive.livebus.databinding.ActivityDriverSignInBinding;
import com.reactive.livebus.model.DriverClass;
import com.reactive.livebus.model.SignInModel;

public class DriverSignInActivity extends AppCompatActivity {

    final String TAG = DriverSignInActivity.class.getSimpleName();
    ActivityDriverSignInBinding binding;
    SignInModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_driver_sign_in);

        model = new SignInModel();
        binding.setData(model);
        binding.setVisibility(true);

        binding.done.setOnClickListener(v -> {
            if (isValid()){
                binding.setVisibility(false);
                signIn();
            }
        });
    }

    private void signIn() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.DRIVER);
        databaseReference.orderByChild("cnic").equalTo(model.getEmail())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        DriverClass driverClass = snapshot1.getValue(DriverClass.class);
                        driverClass.setId(snapshot1.getKey());
                        if (driverClass.getPassword().equals(model.getPassword())){
                            binding.setVisibility(true);
                            openScreen(driverClass);
                        }else {
                            binding.setVisibility(true);
                            Toast.makeText(DriverSignInActivity.this,
                                    "Password not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    binding.setVisibility(true);
                    Toast.makeText(DriverSignInActivity.this,
                            "Driver not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG,error.getMessage());
                binding.setVisibility(true);
                Toast.makeText(DriverSignInActivity.this, "try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openScreen(DriverClass driverClass) {
        Intent intent = new Intent(this,DriverDashboard.class);
        intent.putExtra(Constants.PARAMS,driverClass);
        startActivity(intent);
    }

    boolean isValid(){
        model.setDisplayError(true);
        if (!model.getEmailError().isEmpty()){
            return false;
        }
        if (!model.getPasswordError().isEmpty()){
            return false;
        }
        model.setDisplayError(false);
        return true;
    }
}