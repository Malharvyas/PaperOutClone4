package com.example.paperoutclone4.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.hssc.app.R;

public class EmocksFragment extends Fragment{


    public EmocksFragment() {
        // Required empty public constructor
    }

    public static EmocksFragment newInstance(String param1, String param2) {
        EmocksFragment fragment = new EmocksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_emocks, container, false);
        return v;
    }

}