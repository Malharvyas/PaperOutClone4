package com.example.paperoutclone4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.paperoutclone4.Fragments.AboutUsFragment;
import com.example.paperoutclone4.Fragments.ContactUsFragment;
import com.example.paperoutclone4.Fragments.ProfileFragment;
import com.google.android.material.tabs.TabLayout;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

public class OptionsMenu extends AppCompatActivity implements PaymentResultListener {

    TabLayout tabLayout;
    String fragment = "", title = "", sub_id;
    Fragment target_fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_options_menu);

        tabLayout = findViewById(R.id.tab_layout2);
        Intent i = getIntent();
        fragment = i.getExtras().getString("fragment");
        title = i.getExtras().getString("Title");
        sub_id = i.getExtras().getString("id");

        switch (fragment) {
            case "profile": {
                tabLayout.getTabAt(0).setText("PROFILE");
                target_fragment = new ProfileFragment();
                String frag_tag = "Profile";
                showfragment(target_fragment, frag_tag);
            }
            break;
            case "about_us": {
                tabLayout.getTabAt(0).setText("ABOUT US");
                target_fragment = new AboutUsFragment();
                String frag_tag = "about_us";
                showfragment(target_fragment, frag_tag);
            }
            break;
            case "contact_us": {
                tabLayout.getTabAt(0).setText("CONTACT US");
                target_fragment = new ContactUsFragment();
                String frag_tag = "contact_us";
                showfragment(target_fragment, frag_tag);
            }
            break;
        }
    }

    private void showfragment(Fragment target_fragment, String frag_tag) {
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction().addToBackStack(frag_tag);
        ft1.replace(R.id.fragment_container, target_fragment);
        ft1.commit();
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            SharedPreferences s = getSharedPreferences("back_pressed",Context.MODE_PRIVATE);
            SharedPreferences.Editor e = s.edit();
            e.putInt("is_press",1);
            e.apply();
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Checkout.clearUserData(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("payment_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("payment", "1");
        editor.apply();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Checkout.clearUserData(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("payment_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("payment", "0");
        editor.apply();
        switch (i) {
            case Checkout.NETWORK_ERROR: {
                Toast.makeText(getApplicationContext(), "There was an network error..please try after sometime", Toast.LENGTH_SHORT).show();
            }
            break;
            case Checkout.INVALID_OPTIONS: {
                Toast.makeText(getApplicationContext(), "User data is incorrect", Toast.LENGTH_SHORT).show();
            }
            break;
            case Checkout.PAYMENT_CANCELED: {
                Toast.makeText(getApplicationContext(), "Canceled!!", Toast.LENGTH_SHORT).show();
            }
            break;
            case Checkout.TLS_ERROR: {
                Toast.makeText(getApplicationContext(), "This device is not supported for online payment", Toast.LENGTH_SHORT).show();
            }
            break;
            default:
                Toast.makeText(getApplicationContext(), "Error : " + s, Toast.LENGTH_SHORT).show();
        }
    }
}