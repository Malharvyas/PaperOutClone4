package com.example.paperoutclone4.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.paperoutclone4.Adapter.EbookAdpter;
import com.example.paperoutclone4.Adapter.EmockskAdpter;
import com.example.paperoutclone4.Class.BaseUrl;
import com.example.paperoutclone4.Class.GridSpacing;
import com.example.paperoutclone4.CourseActivity;
import com.example.paperoutclone4.Model.EbookCourseModel;
import com.example.paperoutclone4.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmocksFragment extends Fragment implements EmockskAdpter.onClickListener {

    List<EbookCourseModel> ebookCourseModelList = new ArrayList<>();
    String url = "", type = "2";
    RecyclerView questions_recycler;
    ProgressBar progressBar;
    RecyclerView.Adapter adapter;
    private boolean isViewShown = false;

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
        adapter = new EmockskAdpter(getContext(), ebookCourseModelList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_emocks, container, false);
        questions_recycler = v.findViewById(R.id.questions_recycler);
        progressBar = v.findViewById(R.id.progressBar);

        questions_recycler.setHasFixedSize(true);
        questions_recycler.setItemAnimator(new DefaultItemAnimator());
        questions_recycler.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        questions_recycler.setAdapter(adapter);
        questions_recycler.addItemDecoration(new GridSpacing(5));

        fetchData();

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
        getCourse(type);
    }

    private void getCourse(String type) {
        progressBar.setVisibility(View.VISIBLE);
        BaseUrl b = new BaseUrl();
        url = b.url;
        url = url.concat("eknumber/api/Course/course");
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("PrintLog", "----course_Response----" + response);
                        BaseUrl b = new BaseUrl();
                        url = b.url;
                        if (response != null) {
                            ebookCourseModelList.clear();
                            JSONObject json = null;

                            try {
                                json = new JSONObject(String.valueOf(response));
                                Boolean status = json.getBoolean("status");
                                if (status) {
                                    JSONArray data = json.getJSONArray("data");
                                    String dataStr = data.toString();
                                    Log.e("PrintLog", "----dataStr----" + dataStr);
                                    Gson gson = new Gson();

                                    Type courseListType = new TypeToken<List<EbookCourseModel>>() {
                                    }.getType();

                                    ebookCourseModelList.addAll(gson.fromJson(dataStr, courseListType));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter.notifyDataSetChanged();

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
                            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", type);
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
    public void onClicked(int position) {
        EbookCourseModel eb = ebookCourseModelList.get(position);
        Intent i = new Intent(getContext(), CourseActivity.class);
        i.putExtra("ebook", eb);
        startActivity(i);
    }
}