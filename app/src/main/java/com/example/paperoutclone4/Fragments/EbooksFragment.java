package com.example.paperoutclone4.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.paperoutclone4.R;


public class EbooksFragment extends Fragment {


    public EbooksFragment() {
        // Required empty public constructor
    }

    public static EbooksFragment newInstance(String param1, String param2) {
        EbooksFragment fragment = new EbooksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ebooks, container, false);
        return v;
    }
}