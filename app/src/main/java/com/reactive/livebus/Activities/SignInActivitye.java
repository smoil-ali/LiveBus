package com.reactive.livebus.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.Utils.Helper;
import com.reactive.livebus.databinding.ActivitySignInActivityeBinding;
import com.reactive.livebus.model.SignInModel;


public class SignInActivitye extends AppCompatActivity {

    final String TAG = SignInActivitye.class.getSimpleName();
    ActivitySignInActivityeBinding binding;
    SignInModel model;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = firebaseDatabase.getReference(Constants.USERS);
    private Uri filePath;
    private String downloadUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in_activitye);
        model = new SignInModel();
        binding.setData(model);
        binding.setVisibility(true);

        mAuth = FirebaseAuth.getInstance();


        binding.done.setOnClickListener(v -> {
            if (isValid()){
                binding.setVisibility(false);
                signIn();
            }
        });

        binding.signUp.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivitye.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        });
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

    void signIn(){
        mAuth.signInWithEmailAndPassword(binding.email.getText().toString(),
                binding.password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        openScreen();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG,e.getMessage());
                        binding.setVisibility(true);
                        Toast.makeText(SignInActivitye.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void openScreen(){
        Helper.setLogin(this,true);
        Intent intent = new Intent(this,StudentActivity.class);
        startActivity(intent);
        finish();
    }


}