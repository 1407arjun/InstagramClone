package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.example.instagramclone.Fragments.DetailsFragment;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String details = getIntent().getStringExtra("details");

        getSupportFragmentManager().beginTransaction().replace(R.id.details_fragment_container, new DetailsFragment()).commit();
    }
}