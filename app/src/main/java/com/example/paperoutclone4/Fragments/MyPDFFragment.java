package com.example.paperoutclone4.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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
import com.example.paperoutclone4.Adapter.CourseAdapter;
import com.example.paperoutclone4.Adapter.EbookAdpter;
import com.example.paperoutclone4.Adapter.PDFAdapter;
import com.example.paperoutclone4.Class.BaseUrl;
import com.example.paperoutclone4.Class.GridSpacing;
import com.example.paperoutclone4.LessionActivity;
import com.example.paperoutclone4.Model.EbookCourseModel;
import com.example.paperoutclone4.Model.MyCourse;
import com.example.paperoutclone4.Model.MyPDF;
import com.example.paperoutclone4.PDFView;
import com.example.paperoutclone4.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyPDFFragment extends Fragment implements CourseAdapter.onClickListener{

    private boolean isViewShown = false;
    String url = "",s_id = "";
    RecyclerView answers_recycler;
    ProgressBar progressBar;
    RecyclerView.Adapter adapter;
    List<MyCourse> courselist ;
    List<MyPDF> lessoionlist ;

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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        s_id = sharedPreferences.getString("sid", "");

        courselist = new ArrayList<>();
        lessoionlist = new ArrayList<>();
        adapter = new CourseAdapter(getContext(), courselist, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mypdf, container, false);
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
                            courselist.clear();
                            lessoionlist.clear();
                            JSONObject json = null;

                            try {
                                json = new JSONObject(String.valueOf(response));
                                Boolean status = json.getBoolean("status");
                                if (status) {
                                    JSONArray datarr = json.getJSONArray("data");
                                    for(int j = 0; j < datarr.length(); j++)
                                    {
                                        lessoionlist.clear();
                                        MyCourse model = new MyCourse();
                                        JSONObject course = datarr.getJSONObject(j);
                                        String enrole_id = course.getString("enrole_id");
                                        String course_id = course.getString("course_id");
                                        String price = course.getString("price");
                                        String start_date = course.getString("start_date");
                                        String end_date = course.getString("end_date");
                                        String days = course.getString("days");
                                        String sid = course.getString("sid");
                                        String course_iamge = course.getString("course_iamge");
                                        String course_name = course.getString("course_name");
                                        String discounted_price = course.getString("discounted_price");
                                        String short_description = course.getString("short_description");

                                        JSONArray lessions = course.getJSONArray("lession");

                                        for(int i = 0; i < lessions.length(); i++)
                                        {
                                            MyPDF model2 = new MyPDF();
                                            JSONObject lsobj = lessions.getJSONObject(i);
                                            String lesion_id = lsobj.getString("lesion_id");
                                            String course_id1 = lsobj.getString("course_id");
                                            String total_question = lsobj.getString("total_question");
                                            String name = lsobj.getString("name");
                                            String pdf_url = lsobj.getString("pdf_url");
                                            String created_date = lsobj.getString("created_date");

                                            model2.setLesion_id(lesion_id);
                                            model2.setCourse_id(course_id1);
                                            model2.setTotal_question(total_question);
                                            model2.setName(name);
                                            model2.setPdf_url(pdf_url);
                                            model2.setCreated_date(created_date);

                                            lessoionlist.add(model2);
                                        }

                                        model.setEnrole_id(enrole_id);
                                        model.setCourse_id(course_id);
                                        model.setPrice(price);
                                        model.setStart_date(start_date);
                                        model.setEnd_date(end_date);
                                        model.setDays(days);
                                        model.setSid(sid);
                                        model.setCourse_name(course_name);
                                        model.setCourse_iamge(course_iamge);
                                        model.setLessions(lessoionlist);
                                        model.setShort_description(short_description);
                                        model.setDiscounted_price(discounted_price);

                                        courselist.add(model);

                                    }

//                                    for(int k = 0; k < courselist.size(); k++)
//                                    {
//                                        MyCourse c = courselist.get(k);
//                                        List<MyPDF> p = c.getLessions();
//                                        Toast.makeText(getContext(),""+c.getLessions().size(),Toast.LENGTH_SHORT).show();
//                                    }
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
        MyCourse p = courselist.get(position);
        Intent i = new Intent(getContext(), LessionActivity.class);
        List<MyPDF> lession = p.getLessions();
//        String c = p.getEnrole_id();
//        Toast.makeText(getContext(),""+lession.size(),Toast.LENGTH_SHORT).show();
        String stlist = new Gson().toJson(lession);
        i.putExtra("lessions", stlist);
        i.putExtra("course",p.getCourse_name());
        startActivity(i);
    }
}