package com.example.project162.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.project162.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    public String TAG="uilover";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }
}