package com.example.paperoutclone4.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.paperoutclone4.Adapter.PDFAdapter;
import com.example.paperoutclone4.Class.BaseUrl;
import com.example.paperoutclone4.Model.MyPDF;
import com.example.paperoutclone4.PDFView;
import com.example.paperoutclone4.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LessionFragment extends Fragment implements PDFAdapter.onClickListener {

    private boolean isViewShown = false;
    String url = "",s_id = "";
    RecyclerView answers_recycler;
    ProgressBar progressBar;
    RecyclerView.Adapter adapter;
    List<MyPDF> pdflist = new ArrayList<>();

    public LessionFragment() {
        // Required empty public constructor
    }


    public static LessionFragment newInstance(String param1, String param2) {
        LessionFragment fragment = new LessionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        s_id = sharedPreferences.getString("sid", "");

        adapter = new PDFAdapter(getContext(), pdflist, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lession, container, false);
        answers_recycler = v.findViewById(R.id.answers_recycler);
        progressBar = v.findViewById(R.id.progressBar);

        answers_recycler.setHasFixedSize(true);
        answers_recycler.setItemAnimator(new DefaultItemAnimator());
        answers_recycler.setAdapter(adapter);

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
        getPDF(s_id);
    }

    private void getPDF(String s_id) {
        progressBar.setVisibility(View.VISIBLE);
        BaseUrl b = new BaseUrl();
        url = b.url;
        url = url.concat("eknumber/api/Course/mypdf");
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
                            pdflist.clear();
                            JSONObject json = null;

                            try {
                                json = new JSONObject(String.valueOf(response));
                                Boolean status = json.getBoolean("status");
                                if (status) {
                                    JSONArray datarr = json.getJSONArray("data");
                                    JSONObject course = datarr.getJSONObject(0);
                                    JSONArray lessions = course.getJSONArray("lession");
                                    for(int i = 0; i < lessions.length(); i++)
                                    {
                                        MyPDF model = new MyPDF();
                                        JSONObject lsobj = lessions.getJSONObject(i);
                                        String lesion_id = lsobj.getString("lesion_id");
                                        String course_id = lsobj.getString("course_id");
                                        String total_question = lsobj.getString("total_question");
                                        String name = lsobj.getString("name");
                                        String pdf_url = lsobj.getString("pdf_url");

                                        model.setLesion_id(lesion_id);
                                        model.setCourse_id(course_id);
                                        model.setTotal_question(total_question);
                                        model.setName(name);
                                        model.setPdf_url(pdf_url);

                                        pdflist.add(model);
                                    }
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
    public void onClicked(int position) {
        MyPDF p = pdflist.get(position);
        String pdf = String.valueOf(p.getPdf_url());
        String pname = String.valueOf(p.getName());
        Intent i = new Intent(getContext(), PDFView.class);
        i.putExtra("pdf_url", pdf);
        i.putExtra("pdf_name", pname);
        i.putExtra("id", "answer");
        startActivity(i);
    }
}