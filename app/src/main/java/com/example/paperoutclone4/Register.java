package com.example.paperoutclone4;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.paperoutclone4.Class.BaseUrl;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    String url = "";
    TextInputEditText user_name,user_mobile,user_email,user_pass1,user_pass2;
    Button register;
    TextView nav_login;
    int check = 0;
    int check2 = 0;
    int check3 = 0;
    String email,uname,uemail,umobile,upass1,upass2,device_id,device_name,device_type,device_os;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_register);

        user_name = findViewById(R.id.user_name);
        user_mobile = findViewById(R.id.user_mobile);
        user_email = findViewById(R.id.user_email);
        user_pass1 = findViewById(R.id.user_pass1);
        user_pass2 = findViewById(R.id.user_pass2);
        register = findViewById(R.id.register);
        nav_login = findViewById(R.id.nav_login);
        progressBar = findViewById(R.id.progressBar);

        user_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                email = user_email.getText().toString();
                if (email.matches(emailPattern)) {
                    check2 = 1;
                } else {
                    check2 = 0;
                    user_email.setError("Please enter a valid email");
                }
            }
        });

        user_pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String p1 = user_pass1.getText().toString();
                String p2 = user_pass2.getText().toString();

                if (p1.equals(p2)) {
                    check3 = 1;
                } else {
                    check3 = 0;
                    if (p2.length() >= p1.length() - 1) {
                        user_pass2.setError("Password Does Not Matches");
                    }

                }
            }
        });

        nav_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkdata();
                reigsterprocess();
            }
        });
    }

    private void reigsterprocess() {
        if (check == 1) {
            if (check3 == 1) {
                if (check2 == 1) {
                    uname = user_name.getText().toString();
                    umobile = user_mobile.getText().toString();
                    uemail = user_email.getText().toString();
                    upass1 = user_pass1.getText().toString();
                    upass2 = user_pass2.getText().toString();
                    device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    device_name = Build.MODEL;
                    device_type = "Android";
                    device_os = String.valueOf(Build.VERSION.SDK_INT);
                    senddatatoapi(uname, uemail, umobile, upass1, upass2, device_id, device_name, device_os, device_type);
                } else {
                    Toast.makeText(getApplicationContext(), "Please correct the errors", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please correct the errors", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please correct the errors", Toast.LENGTH_SHORT).show();
        }

    }

    private void senddatatoapi(String uname, String uemail, String umobile, String upass1, String upass2, String device_id, String device_name, String device_os, String device_type) {
//        Toast.makeText(getApplicationContext(),""+upass1,Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        BaseUrl b = new BaseUrl();
        url = b.url;
        url = url.concat("eknumber/api/Register/register");
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("Response : ", response);
                        BaseUrl b = new BaseUrl();
                        url = b.url;
                        if (response != null) {
                            JSONObject json = null;

                            try {
                                json = new JSONObject(String.valueOf(response));
                                Boolean status = json.getBoolean("status");
                                String stat = status.toString();
                                if (stat.equals("true")) {
                                    String msg = json.getString("message");
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), Login.class);
                                    startActivity(i);
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
                else {
                    Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("s_name", uname);
                params.put("s_mobile", umobile);
                params.put("s_email", uemail);
                params.put("s_password", upass1);
                Log.e("Pass:",upass1);
                params.put("passconf", upass2);
                params.put("device_uid", device_id);
                params.put("device_name", device_name);
                params.put("device_type", device_type);
                params.put("os_version", device_os);
                return params;
            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                String credentials = "HRCET:.r*fm5D%d-Re45-)";
//                String auth = "Basic "
//                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                headers.put("Authorization", auth);
//                headers.put("x-api-key", "HRCETCRACKER@123");
////                headers.put("Content-Type", "application/form-data");
//                return headers;
//            }

        };
        volleyRequestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
    }

    private void checkdata() {
        if (isEmpty(user_name)) {
            user_name.setError("Please Enter the username");
        } else if (isEmpty(user_mobile)) {
            user_mobile.setError("Please Enter the mobile no.");
        } else if (isEmpty(user_email)) {
            user_email.setError("Please Enter the email address");
        } else if (isEmpty1(user_pass1)) {
            user_pass1.setError("Password cannot be empty");
        } else if (isEmpty1(user_pass2)) {
            user_pass2.setError("Password cannot be empty");
        } else if (user_mobile.length() != 10) {
            user_mobile.setError("Please enter a valid 10 digit mobile no.");
        } else if (!user_pass1.getText().toString().equals(user_pass2.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Password doesnot matches", Toast.LENGTH_SHORT).show();
        } else {
            check = 1;
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isEmpty1(TextInputEditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}