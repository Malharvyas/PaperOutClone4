package com.example.paperoutclone4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.paperoutclone4.Class.BaseUrl;
import com.example.paperoutclone4.Model.EbookCourseModel;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CourseActivity extends AppCompatActivity implements PaymentResultListener {

    Button buy;
    String username, useremail, usermobile, price, s_id, url = "";
    String course_id;
    TextView selling_price, course_name, actual_price;
    ProgressBar progressBar;
    private ImageView imageView, btnBackSpace;
    EbookCourseModel ebookCourseModel;
    Toolbar toolbar;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getOverflowIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);

        SharedPreferences sharedPreferences = getSharedPreferences("userpref", Context.MODE_PRIVATE);
        s_id = sharedPreferences.getString("sid", "");

        buy = findViewById(R.id.buy_course);
        selling_price = findViewById(R.id.selling_price);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);
        course_name = findViewById(R.id.course_name);
        actual_price = findViewById(R.id.actual_price);
        btnBackSpace = findViewById(R.id.btnBackSpace);
        view = findViewById(R.id.view);

        Intent i = getIntent();
        ebookCourseModel = (EbookCourseModel) i.getSerializableExtra("ebook");

        Picasso.get().load(ebookCourseModel.getCourseIamge()).fit().into(imageView);
        course_name.setText(ebookCourseModel.getCourseName());
        selling_price.setText(ebookCourseModel.getPrice());

        if (ebookCourseModel.getDiscountedPrice().equals("null") || ebookCourseModel.getDiscountedPrice().equals("0.00")) {
            actual_price.setVisibility(View.INVISIBLE);
            view.setVisibility(View.INVISIBLE);
        } else {
            actual_price.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            actual_price.setText("\u20B9" + " " + ebookCourseModel.getDiscountedPrice());
        }

        btnBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buy.getText().toString().equals("Buy Now")) {
                    price = selling_price.getText().toString();
                    startrazorpay(price);
                }
            }
        });
    }

    private void startrazorpay(String price) {
        SharedPreferences userpref = getSharedPreferences("userpref", Context.MODE_PRIVATE);
        username = userpref.getString("s_name", "NA");
        useremail = userpref.getString("s_email", "NA");
        usermobile = userpref.getString("s_mobile", "NA");

        int amount = Integer.parseInt(price);
        amount = amount * 100;

        Checkout.preload(this);
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_HU0xL5fylvOKxY");


        try {
            JSONObject options = new JSONObject();

            options.put("name", username);
            options.put("currency", "INR");
            options.put("amount", amount);//pass amount in currency subunits
            options.put("prefill.email", useremail);
            options.put("prefill.contact", "+91" + usermobile);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open((Activity) CourseActivity.this, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("payment_details", Context.MODE_PRIVATE);
        String payment_status = sharedPreferences.getString("course_payment", "0");

        if (payment_status.equals("1")) {
            activateplan(s_id);
        }
    }

    private void activateplan(String s_id) {
        progressBar.setVisibility(View.VISIBLE);
        BaseUrl b = new BaseUrl();
        url = b.url;
        url = url.concat("eknumber/api/Course/enrol");
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(CourseActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        BaseUrl b = new BaseUrl();
                        url = b.url;
                        if (response != null) {
                            JSONObject json = null;
                            try {
                                json = new JSONObject(String.valueOf(response));
                                Boolean status = json.getBoolean("status");
                                if (status == true) {

                                    String msg = json.getString("message");
                                    Toast.makeText(CourseActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences = getSharedPreferences("payment_details", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear();
                                    editor.apply();
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
                            Toast.makeText(CourseActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(CourseActivity.this, "Please make sure you have an active Internet connection.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(CourseActivity.this, "Server is taking more than usual time to respond,please try after sometime.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CourseActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sid", s_id);
                params.put("course_id", ebookCourseModel.getCourseId());
                params.put("price", ebookCourseModel.getPrice());
                params.put("days", ebookCourseModel.getDays());
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
    public void onPaymentSuccess(String s) {
        Checkout.clearUserData(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("payment_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("course_payment", "1");
        editor.apply();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Checkout.clearUserData(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("payment_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("course_payment", "0");
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