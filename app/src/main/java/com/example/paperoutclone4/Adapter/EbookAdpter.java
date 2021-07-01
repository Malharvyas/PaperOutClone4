package com.example.paperoutclone4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paperoutclone4.Model.EbookCourseModel;
import com.example.paperoutclone4.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EbookAdpter extends RecyclerView.Adapter<EbookAdpter.ViewHolder> {

    private Context context;
    private List<EbookCourseModel> list;
    private onClickListener monclicklistener;

    public EbookAdpter(Context context, List<EbookCourseModel> list, onClickListener onclicklistener) {
        this.context = context;
        this.list = list;
        this.monclicklistener = onclicklistener;
    }

    @NonNull
    @Override
    public EbookAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(v, monclicklistener);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookAdpter.ViewHolder holder, int position) {

        EbookCourseModel ebookCourseModel = list.get(position);
        if(position == 1)
        {
            holder.course_name.setText("This is course name of two lines");
        }
        else{
            holder.course_name.setText(ebookCourseModel.getCourseName());
        }

        holder.selling_price.setText("\u20B9" + ebookCourseModel.getPrice());

        if (ebookCourseModel.getDiscountedPrice().equals("null") || ebookCourseModel.getDiscountedPrice().equals("0.00")) {
            holder.actual_price.setVisibility(View.INVISIBLE);
            holder.view.setVisibility(View.INVISIBLE);
        } else {
            holder.actual_price.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.VISIBLE);
            holder.actual_price.setText("\u20B9" + ebookCourseModel.getDiscountedPrice());
        }

        Picasso.get().load(ebookCourseModel.getCourseIamge()).fit().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView course_name, selling_price, actual_price, pdfnumber;
        public ImageView imageView;
        public View view;
        onClickListener onClickListener;

        public ViewHolder(View itemView, onClickListener onClickListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            course_name = itemView.findViewById(R.id.course_name);
            selling_price = itemView.findViewById(R.id.selling_price);
            actual_price = itemView.findViewById(R.id.actual_price);
            view = itemView.findViewById(R.id.view);

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
