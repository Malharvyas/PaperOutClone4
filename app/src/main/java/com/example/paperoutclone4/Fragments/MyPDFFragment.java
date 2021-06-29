package com.example.paperoutclone4.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.paperoutclone4.R;


public class MyPDFFragment extends Fragment {

    private boolean isViewShown = false;

    public MyPDFFragment() {
        // Required empty public constructor
    }

    public static MyPDFFragment newInstance(String param1, String param2) {
        MyPDFFragment fragment = new MyPDFFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mypdf, container, false);

        return v;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getView() != null && isVisibleToUser) {
            isViewShown = true; // fetchdata() contains logic to show data when page is selected mostly asynctask to fill the data
            fetchData();
        } else {
            isViewShown = false;
        }
    }

    private void fetchData() {

    }
}