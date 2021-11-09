package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        SystemClock.sleep(3000);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser=firebaseAuth.getCurrentUser();
        if(currentuser==null){
            Intent loginIntent = new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(loginIntent);
            finish();

        }else{
            Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(mainIntent);
            finish();

        }
    }
}