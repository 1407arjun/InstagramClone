package com.example.instagramclone.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.instagramclone.R;
import com.google.android.material.textfield.TextInputLayout;

public class DetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        Button syncButton = view.findViewById(R.id.syncButton);
        TextView noSyncText = view.findViewById(R.id.noSyncTextView);
        EditText nameEditText = view.findViewById(R.id.nameEditText);
        EditText passwordEditText = view.findViewById(R.id.passwordEditText);
        TextInputLayout passwordLayout = view.findViewById(R.id.passwordLayout);

        nameEditText.setEnabled(true);
        passwordEditText.setEnabled(true);
        syncButton.setEnabled(false);
        noSyncText.setEnabled(false);
        passwordLayout.setError(null);

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordLayout.setError(null);
                if (!passwordEditText.getText().toString().isEmpty() && passwordEditText.getText().toString() != null &&
                        passwordEditText.getText().toString().length() < 6){
                    syncButton.setEnabled(false);
                    noSyncText.setEnabled(false);
                    passwordLayout.setError(getString(R.string.password_error));
                }
                else if (s.toString().isEmpty() || s.toString() == null ||
                        passwordEditText.getText().toString().isEmpty() || passwordEditText.getText().toString() == null){
                    syncButton.setEnabled(false);
                    noSyncText.setEnabled(false);
                }else{
                    syncButton.setEnabled(true);
                    noSyncText.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordLayout.setError(null);
                if (!s.toString().isEmpty() && s.toString() != null &&
                        s.toString().length() < 6){
                    syncButton.setEnabled(false);
                    noSyncText.setEnabled(false);
                    passwordLayout.setError(getString(R.string.password_error));
                }
                else if (s.toString().isEmpty() || s.toString() == null ||
                        nameEditText.getText().toString().isEmpty() || nameEditText.getText().toString() == null){
                    syncButton.setEnabled(false);
                    noSyncText.setEnabled(false);
                }else{
                    syncButton.setEnabled(true);
                    noSyncText.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        syncButton.setOnClickListener(v -> {
            nameEditText.setEnabled(false);
            passwordEditText.setEnabled(false);
            addUser();
            getFragmentManager().beginTransaction().replace(R.id.details_fragment_container, new UsernameFragment()).commit();
        });

        noSyncText.setOnClickListener(v -> {
            nameEditText.setEnabled(false);
            passwordEditText.setEnabled(false);
            addUser();
            getFragmentManager().beginTransaction().replace(R.id.details_fragment_container, new UsernameFragment()).commit();
        });
        return view;
    }

    public void addUser(){

    }
}