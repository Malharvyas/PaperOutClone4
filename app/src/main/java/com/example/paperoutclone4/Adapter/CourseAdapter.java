package com.example.paperoutclone4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paperoutclone4.Model.MyCourse;
import com.example.paperoutclone4.Model.MyPDF;
import com.example.paperoutclone4.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{

    private Context context;
    private List<MyCourse> list;
    private onClickListener monclicklistener;

    public CourseAdapter(Context context, List<MyCourse> list, onClickListener onclicklistener) {
        this.context = context;
        this.list = list;
        this.monclicklistener = onclicklistener;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_layout2, parent, false);
        return new ViewHolder(v, monclicklistener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {

        MyCourse model = list.get(position);

        holder.course_name.setText(model.getCourse_name());
        holder.selling_price.setText("\u20B9" + model.getPrice());

        if (model.getDiscounted_price().equals("null") || model.getDiscounted_price().equals("0.00")) {
            holder.actual_price.setVisibility(View.INVISIBLE);
            holder.view.setVisibility(View.INVISIBLE);
        } else {
            holder.actual_price.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.VISIBLE);
            holder.actual_price.setText("\u20B9" + model.getDiscounted_price());
        }

        Picasso.get().load(model.getCourse_iamge()).fit().into(holder.imageView);

        holder.description.setText(model.getShort_description());

        holder.tvValidity.setText(model.getDays() + " Days");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView course_name,selling_price,actual_price,description,tvValidity;
        onClickListener onClickListener;
        Button buy_now;
        public View view;
        ImageView imageView;

        public ViewHolder(View itemView,onClickListener onClickListener)
        {
            super(itemView);

            course_name = itemView.findViewById(R.id.course_name);
            selling_price = itemView.findViewById(R.id.selling_price);
            actual_price = itemView.findViewById(R.id.actual_price);
            description = itemView.findViewById(R.id.description);
            tvValidity = itemView.findViewById(R.id.tvValidity);
            buy_now = itemView.findViewById(R.id.buy_course);
            view = itemView.findViewById(R.id.view);
            imageView = itemView.findViewById(R.id.imageView);

            this.onClickListener = onClickListener;

            buy_now.setOnClickListener(new View.OnClickListener() {
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
