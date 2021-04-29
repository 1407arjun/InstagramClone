package com.example.instagramclone.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.instagramclone.DetailsActivity;
import com.example.instagramclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DetailsFragment extends Fragment {

    Button syncButton;
    TextView noSyncText;
    EditText nameEditText, passwordEditText;
    TextInputLayout passwordLayout;
    LottieAnimationView animationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle args = getArguments();
        String email = args.getString("email");

        syncButton = view.findViewById(R.id.syncButton);
        noSyncText = view.findViewById(R.id.noSyncTextView);
        nameEditText = view.findViewById(R.id.nameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        passwordLayout = view.findViewById(R.id.passwordLayout);
        animationView = view.findViewById(R.id.animationView);

        nameEditText.setEnabled(true);
        passwordEditText.setEnabled(true);
        syncButton.setEnabled(false);
        noSyncText.setEnabled(false);
        passwordLayout.setError(null);
        animationView.setVisibility(View.GONE);


        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordLayout.setError(null);
                if (!passwordEditText.getText().toString().isEmpty() && passwordEditText.getText().toString().length() < 6){
                    syncButton.setEnabled(false);
                    noSyncText.setEnabled(false);
                    passwordLayout.setError(getString(R.string.password_error));
                }
                else if (s.toString().isEmpty() || passwordEditText.getText().toString().isEmpty()){
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
                if (!s.toString().isEmpty() && s.toString().length() < 6){
                    syncButton.setEnabled(false);
                    noSyncText.setEnabled(false);
                    passwordLayout.setError(getString(R.string.password_error));
                }
                else if (s.toString().isEmpty() || nameEditText.getText().toString().isEmpty()){
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
            animationView.playAnimation();
            animationView.setVisibility(View.VISIBLE);
            syncButton.setEnabled(false);
            noSyncText.setEnabled(false);
            String name = nameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            nameEditText.setEnabled(false);
            passwordEditText.setEnabled(false);
            addUser(name, email, password);
        });

        noSyncText.setOnClickListener(v -> {
            animationView.playAnimation();
            syncButton.setEnabled(false);
            noSyncText.setEnabled(false);
            animationView.setVisibility(View.VISIBLE);
            String name = nameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            nameEditText.setEnabled(false);
            passwordEditText.setEnabled(false);
            addUser(name, email, password);
        });
        return view;
    }

    public void addUser(String name, String email, String password){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("name", name);
            entry.put("email", email);
            entry.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());

            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(entry).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            animationView.setVisibility(View.GONE);
                            animationView.cancelAnimation();
                            getFragmentManager().beginTransaction().replace(R.id.details_fragment_container, new UsernameFragment()).commit();
                        }
                    });
        }).addOnFailureListener(e -> {
            animationView.setVisibility(View.GONE);
            animationView.cancelAnimation();
            nameEditText.setEnabled(true);
            passwordEditText.setEnabled(true);
            syncButton.setEnabled(true);
            noSyncText.setEnabled(true);
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
}