package com.example.paperoutclone4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.paperoutclone4.Adapter.TabsAdapter;
import com.example.paperoutclone4.Class.BaseUrl;
import com.google.android.material.tabs.TabLayout;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    TabLayout tabLayout;
    Fragment fragment = null;
    String ftag = "";
    Toolbar toolbar;
    String log_status = "";
    ViewPager viewtab;
    String s_id = "", url = "";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getOverflowIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);

        SharedPreferences logincredentials = getSharedPreferences("userlogin", Context.MODE_PRIVATE);
        log_status = logincredentials.getString("logged", "NA");
        if (log_status.equals("true")){
            SharedPreferences sharedPreferences = getSharedPreferences("userpref", Context.MODE_PRIVATE);
            s_id = sharedPreferences.getString("sid","");

            tabLayout = findViewById(R.id.tab_layout);
            viewtab = findViewById(R.id.view_tab);
            progressBar = findViewById(R.id.progressBar);

            TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
            viewtab.setAdapter(adapter);
            viewtab.setOffscreenPageLimit(3);
            tabLayout.setupWithViewPager(viewtab);
//            fragment = new QuestionsFragment();
//            ftag = "Questions";
//            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction().addToBackStack(ftag);
//            ft1.replace(R.id.fragment_container, fragment);
//            ft1.commit();


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewtab.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
        else{
            Intent i = new Intent(getApplicationContext(), Register.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_membership:
            {
                Intent i = new Intent(getApplicationContext(),OptionsMenu.class);
                i.putExtra("fragment","membership");
                startActivity(i);
            }
            break;
            case R.id.nav_profile:
            {
                Intent i = new Intent(getApplicationContext(),OptionsMenu.class);
                i.putExtra("fragment","profile");
                startActivity(i);
            }
            break;
            case R.id.nav_about:
            {
                Intent i = new Intent(getApplicationContext(),OptionsMenu.class);
                i.putExtra("fragment","about_us");
                startActivity(i);
            }
            break;
            case R.id.nav_contact:
            {
                Intent i = new Intent(getApplicationContext(),OptionsMenu.class);
                i.putExtra("fragment","contact_us");
                startActivity(i);
            }
            break;
            case R.id.nav_shareus:
            {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Chhalaang");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "Application Link will be added here"  +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            case R.id.logout:
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.alert_title).setMessage("Are you sure you want to logout from this device?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutprocess(s_id);
                        dialog.dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
            break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void logoutprocess(String s_id) {
        progressBar.setVisibility(View.VISIBLE);
        BaseUrl b = new BaseUrl();
        url = b.url;
        url = url.concat("eknumber/api/Register/logout");
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response : ",response);
                        progressBar.setVisibility(View.GONE);
                        BaseUrl b = new BaseUrl();
                        url = b.url;
                        if (response != null) {
                            JSONObject json = null;

                            try {
                                json = new JSONObject(String.valueOf(response));
                                Boolean status = json.getBoolean("status");
                                String stat = status.toString();
                                if (stat.equals("true")) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("userpref", Context.MODE_PRIVATE);
                                    SharedPreferences logincredentials = getSharedPreferences("userlogin", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor1 = sharedPreferences.edit();
                                    SharedPreferences.Editor editor2 = logincredentials.edit();
                                    editor1.clear();
                                    editor2.clear();
                                    editor1.apply();
                                    editor2.apply();
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                BaseUrl b = new BaseUrl();
                url = b.url;
                if (error instanceof ClientError) {
                    try {
                        String responsebody = new String(error.networkResponse.data, "utf-8");
                        JSONObject data = new JSONObject(responsebody);
                        Boolean status = data.getBoolean("status");
                        String stat = status.toString();
                        if (stat.equals("false")) {
                            String msg = data.getString("message");
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(error instanceof NoConnectionError)
                {
                    Toast.makeText(getApplicationContext(),"Please make sure you have an active Internet connection.",Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof TimeoutError)
                {
                    Toast.makeText(getApplicationContext(),"Server is taking more than usual time to respond,please try after sometime.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error : "+error,Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sid", s_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = "HRCET:.r*fm5D%d-Re45-)";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                headers.put("x-api-key", "HRCETCRACKER@123");
//                headers.put("Content-Type", "application/form-data");
                return headers;
            }
        };
        volleyRequestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences s = getSharedPreferences("back_pressed",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = s.edit();
        int is_press = s.getInt("is_press",0);
        if(is_press == 0){
            e.clear();
            e.apply();
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
        }
        else {
            e.clear();
            e.apply();
        }
    }


}