package com.example.paperoutclone4.ProfileFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
import com.example.paperoutclone4.R;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileFragment extends Fragment {

    TextInputEditText editpro_name,editpro_email,editpro_mobile;
    Button edit_profile;
    ProgressBar progressBar;
    String url = "",s_id = "",username,useremail,usermobile;
    String updatedname,updatedemail,updatedmobile;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
        s_id = sharedPreferences.getString("sid","");
        username = sharedPreferences.getString("s_name","");
        useremail = sharedPreferences.getString("s_email","");
        usermobile = sharedPreferences.getString("s_mobile","");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
       editpro_name = v.findViewById(R.id.editpro_name);
        editpro_email = v.findViewById(R.id.editpro_email);
        editpro_mobile = v.findViewById(R.id.editpro_mobile);
        edit_profile = v.findViewById(R.id.edit_profile);
        progressBar = v.findViewById(R.id.progressBar);

        editpro_name.setText(username);
        editpro_mobile.setText(usermobile);
        editpro_email.setText(useremail);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedname = editpro_name.getText().toString();
                updatedemail = editpro_email.getText().toString();
                updatedmobile = editpro_mobile.getText().toString();
                updatedetaisl(s_id,updatedname,updatedemail,updatedmobile);
            }
        });

        return v;
    }

    private void updatedetaisl(String s_id, String updatedname, String updatedemail, String updatedmobile) {
       progressBar.setVisibility(View.VISIBLE);
        BaseUrl b = new BaseUrl();
        url = b.url;
        url = url.concat("eknumber/api/Register/edit_profile");
        RequestQueue volleyRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        BaseUrl b = new BaseUrl();
                        url = b.url;
                        if(response != null) {
                            JSONObject json = null;

                            try {
                                json = new JSONObject(String.valueOf(response));
                                Boolean status = json.getBoolean("status");
                                String stat = status.toString();
                                if(stat.equals("true"))
                                {
                                    String msg = json.getString("message");
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userpref", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("s_name",updatedname);
                                    editor.putString("s_email",updatedemail);
                                    editor.putString("s_mobile",updatedmobile);
                                    editor.apply();
                                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();

                                    getActivity().getSupportFragmentManager().popBackStack();
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
                if(error instanceof ClientError)
                {
                    try{
                        String responsebody = new String(error.networkResponse.data,"utf-8");
                        JSONObject data = new JSONObject(responsebody);
                        Boolean status = data.getBoolean("status");
                        String stat = status.toString();
                        if(stat.equals("false"))
                        {
                            String msg = data.getString("message");
                            Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(error instanceof NoConnectionError)
                {
                    Toast.makeText(getContext(),"Please make sure you have an active Internet connection.",Toast.LENGTH_SHORT).show();
                }
                else if(error instanceof TimeoutError)
                {
                    Toast.makeText(getContext(),"Server is taking more than usual time to respond,please try after sometime.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("sid", s_id);
                params.put("s_name",updatedname);
                params.put("s_email",updatedemail);
                params.put("s_mobile",updatedmobile);
                return params;
            }
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = "HRCET:.r*fm5D%d-Re45-)";
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                headers.put("x-api-key","HRCETCRACKER@123");
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
}