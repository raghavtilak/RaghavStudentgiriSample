package com.raghav.raghavstudentgiri.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raghav.raghavstudentgiri.R;
import com.raghav.raghavstudentgiri.model.Todo;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private ArrayList<Todo> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnEditClickListener onEditClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    public TodoAdapter(Context context, ArrayList<Todo> list,
                       OnItemClickListener onItemClickListener,
                       OnEditClickListener onEditClickListener,
                       OnDeleteClickListener onDeleteClickListener) {
        this.list = list;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.onEditClickListener = onEditClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.todo_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo item = list.get(position);
        holder.titleTV.setText(item.getTitle());
        if (item.isCompleted()) {
            holder.statusTV.setTextColor(context.getResources().getColor(R.color.teal_800));
            holder.statusTV.setText("Completed");
        } else {
            holder.statusTV.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
            holder.statusTV.setText("Pending");
        }
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditClickListener.onEditClick(position, item);
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteClickListener.onDeleteClick(position, item);
            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position, item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTV, statusTV;
        public RelativeLayout relativeLayout;
        public ImageButton updateBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = (TextView) itemView.findViewById(R.id.title);
            statusTV = (TextView) itemView.findViewById(R.id.status);
            updateBtn = (ImageButton) itemView.findViewById(R.id.updateBtn);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.deleteBtn);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Todo item);
    }

    public interface OnEditClickListener {
        void onEditClick(int position, Todo item);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position, Todo item);
    }
}