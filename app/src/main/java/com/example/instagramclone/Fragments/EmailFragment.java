package com.example.instagramclone.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.instagramclone.DetailsActivity;
import com.example.instagramclone.R;

public class EmailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_email, container, false);
        Button nextButton = view.findViewById(R.id.nextButton);
        EditText emailEditText = view.findViewById(R.id.emailEditText);

        nextButton.setEnabled(false);
        emailEditText.setEnabled(true);

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(!s.toString().isEmpty() && s.toString() != null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        nextButton.setOnClickListener(v -> {
            emailEditText.setEnabled(false);
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("details", emailEditText.getText().toString());
        });

        return view;
    }

    
}