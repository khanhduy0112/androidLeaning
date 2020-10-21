package com.nkd.app.tasklistsqlite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.nkd.app.tasklistsqlite.R;
import com.nkd.app.tasklistsqlite.database.DatabaseHelper;
import com.nkd.app.tasklistsqlite.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<Task> tasks;
    private Context context;

    //animation containts
    int lastPosition = -1;

    private DatabaseHelper db;

    public TaskAdapter(List<Task> tasks, Context context, DatabaseHelper db) {
        this.tasks = tasks;
        this.context = context;
        this.db = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        if (holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row);
            holder.cb_item.startAnimation(animation);

            if (position % 2 == 0) {
                holder.cb_item.setBackgroundResource(R.color.appItemBg1);
            } else {
                holder.cb_item.setBackgroundResource(R.color.appItemBg2);
            }
            holder.cb_item.setText(tasks.get(position).getContent());
            holder.cb_item.setChecked(tasks.get(position).getStatus() == 1 ? true : false);
            holder.cb_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean status = holder.cb_item.isChecked();
                    int id = tasks.get(position).getId();
                    db.updateStatus(id, status ? 1 : 0);
                }
            });
            lastPosition = holder.getAdapterPosition();

        }


    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox cb_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_item = itemView.findViewById(R.id.cb_taskItem);
        }
    }

}
