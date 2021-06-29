package com.example.paperoutclone4;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paperoutclone4.Class.PdfDownloader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.io.IOException;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;
import es.voghdev.pdfviewpager.library.util.VerticalViewPager;

public class PDFView extends AppCompatActivity implements DownloadFile.Listener {

    WebView pdfview;
    String pdf,url,pname,id;
    WebSettings setiings;
    ProgressBar progressBar;
//    com.github.barteksc.pdfviewer.PDFView viewer;
    Uri myUri;
    FloatingActionButton download;
    private final static int REQUEST_CODE = 42;
    private RemotePDFViewPager remotePDFViewPager;

    private PDFPagerAdapter pdfPagerAdapter;
    private VerticalViewPager pdfLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_p_d_f_view);

        download = findViewById(R.id.download_pdf);

        Intent i = getIntent();
        pdf = i.getExtras().getString("pdf_url");
        pname = i.getExtras().getString("pdf_name");
        id = i.getExtras().getString("id");
        url = Uri.encode(pdf);
        myUri = Uri.parse(url);

        if(id.equals("question")){
            download.setVisibility(View.VISIBLE);
        }
        else {
            download.setVisibility(View.GONE);
        }

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        pdfLayout = findViewById(R.id.pdfview);

        remotePDFViewPager = new RemotePDFViewPager(this, pdf, this);


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PDFView.DownloadFile(pname).execute(pdf);
            }
        });

    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        pdfPagerAdapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        pdfLayout.setAdapter(pdfPagerAdapter);
//        updateLayout();
        progressBar.setVisibility(View.GONE);
    }

    private void updateLayout() {

        pdfLayout.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {
        String fname;
        public DownloadFile(String s) {
            this.fname = s;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];
            String fileName = fname+".pdf";
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory,"Download");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            PdfDownloader.DownloadFile(fileUrl,pdfFile);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Download PDf successfully", Toast.LENGTH_SHORT).show();
        }
    }

}