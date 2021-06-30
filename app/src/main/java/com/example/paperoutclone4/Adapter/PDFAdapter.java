package com.example.paperoutclone4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paperoutclone4.Model.EbookCourseModel;
import com.example.paperoutclone4.Model.MyPDF;
import com.example.paperoutclone4.R;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.ViewHolder>{

    private Context context;
    private List<MyPDF> list;
    private onClickListener monclicklistener;

    public PDFAdapter(Context context, List<MyPDF> list, onClickListener onclicklistener) {
        this.context = context;
        this.list = list;
        this.monclicklistener = onclicklistener;
    }

    @NonNull
    @Override
    public PDFAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.pdflayout, parent, false);
        return new ViewHolder(v, monclicklistener);
    }

    @Override
    public void onBindViewHolder(@NonNull PDFAdapter.ViewHolder holder, int position) {

        MyPDF model = list.get(position);

        holder.pdf_name.setText(model.getName());
        holder.pdf_date.setText("15 May");
        holder.total_ques.setText(model.getTotal_question());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView pdf_name,pdf_date,total_ques;
        onClickListener onClickListener;

        public ViewHolder(View itemView,onClickListener onClickListener)
        {
            super(itemView);

            pdf_name = itemView.findViewById(R.id.pdf_name);
            pdf_date = itemView.findViewById(R.id.pdf_date);
            total_ques = itemView.findViewById(R.id.pdf_questions);

            this.onClickListener = onClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface onClickListener {
        void onClicked(int position);
    }
}
