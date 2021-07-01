package com.example.paperoutclone4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.paperoutclone4.Adapter.PDFAdapter;
import com.example.paperoutclone4.Model.MyPDF;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class LessionActivity extends AppCompatActivity implements PDFAdapter.onClickListener{

    String url = "",s_id = "";
    RecyclerView answers_recycler;
    ProgressBar progressBar;
    RecyclerView.Adapter adapter;
    List<MyPDF> pdflist = new ArrayList<>();
    String list,course;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_lession);

        Intent i = getIntent();
        list = i.getExtras().getString("lessions");
        course = i.getExtras().getString("course");

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.getTabAt(0).setText(course);

        pdflist = new Gson().fromJson(list,new TypeToken<List<MyPDF>>(){}.getType());

        SharedPreferences sharedPreferences = getSharedPreferences("userpref", Context.MODE_PRIVATE);
        s_id = sharedPreferences.getString("sid", "");

        adapter = new PDFAdapter(getApplicationContext(), pdflist, this);

        answers_recycler = findViewById(R.id.answers_recycler);
        progressBar = findViewById(R.id.progressBar);

        answers_recycler.setHasFixedSize(true);
        answers_recycler.setItemAnimator(new DefaultItemAnimator());
        answers_recycler.setAdapter(adapter);
    }

    @Override
    public void onClicked(int position) {
        MyPDF p = pdflist.get(position);
        String pdf = String.valueOf(p.getPdf_url());
        String pname = String.valueOf(p.getName());
        Intent i = new Intent(getApplicationContext(), PDFView.class);
        i.putExtra("pdf_url", pdf);
        i.putExtra("pdf_name", pname);
        i.putExtra("id", "answer");
        startActivity(i);
    }
}