package com.example.paperoutclone4.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hssc.app.ProfileFragment.EditProfileFragment;
import com.hssc.app.R;


public class ProfileFragment extends Fragment {

    TextView uname,uemail,umobile;
    String username,useremail,usermobile,s_id;
    Button main_page,edit_profile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("s_name","");
        useremail = sharedPreferences.getString("s_email","");
        usermobile = sharedPreferences.getString("s_mobile","");
        s_id = sharedPreferences.getString("sid","");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        uname = v.findViewById(R.id.profile_uname);
        uemail = v.findViewById(R.id.profile_uemail);
        umobile = v.findViewById(R.id.profile_umobile);
        main_page = v.findViewById(R.id.nav_mainpage3);
        edit_profile = v.findViewById(R.id.edit_details);

        uname.setText(username);
        uemail.setText(useremail);
        umobile.setText(usermobile);

        main_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileFragment edf = new EditProfileFragment();
                FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("EditProfile");
                ft1.replace(R.id.fragment_container, edf);
                ft1.commit();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("s_name","");
        useremail = sharedPreferences.getString("s_email","");
        usermobile = sharedPreferences.getString("s_mobile","");
        uname.setText(username);
        uemail.setText(useremail);
        umobile.setText(usermobile);
    }
}