package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.example.instagramclone.Fragments.DetailsFragment;

public class DetailsActivity extends AppCompatActivity {

    static String details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        details = getIntent().getStringExtra("details");
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("email", details);
        detailsFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.details_fragment_container, detailsFragment).commit();
    }
}