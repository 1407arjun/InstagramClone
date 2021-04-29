package com.example.instagramclone.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.instagramclone.MainActivity;
import com.example.instagramclone.R;

public class UsernameFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_username, container, false);
        Button nextButton = view.findViewById(R.id.nextButton);
        TextView changeUsernameTextView = view.findViewById(R.id.changeUsernameTextView);

        nextButton.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            getActivity().finish();
        });

        return view;
    }
}