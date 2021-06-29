package com.example.paperoutclone4.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.paperoutclone4.R;


public class MembershipFragment extends Fragment {



    public MembershipFragment() {
        // Required empty public constructor
    }


    public static MembershipFragment newInstance(String param1, String param2) {
        MembershipFragment fragment = new MembershipFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_membership, container, false);
        return v;
    }
}