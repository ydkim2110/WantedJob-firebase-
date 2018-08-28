package com.example.anti2110.wantedjob.todo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.TodoViewHolder> {

    private Context context;
    private List<Task> taskList;

    private ItemClickListener itemClickListener;

    public TodoRecyclerAdapter() {
    }

    public TodoRecyclerAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_todo, parent, false);

        ((Activity) context).registerForContextMenu(view);

        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.task_title.setText(taskList.get(position).getTask_name());
        holder.task_content.setText(taskList.get(position).getTask_memo());
        holder.task_end_date.setText(taskList.get(position).getTask_end_date());
        holder.relLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Layout click!!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.task_priority_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Image Click!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        private TextView task_title;
        private TextView task_content;
        private TextView task_end_date;
        private RelativeLayout relLayout1;
        private ImageView task_priority_image;

        public TodoViewHolder(View itemView) {
            super(itemView);

            task_title = (TextView) itemView.findViewById(R.id.task_title);
            task_content = (TextView) itemView.findViewById(R.id.task_content);
            task_end_date = (TextView) itemView.findViewById(R.id.task_end_date);

            relLayout1 = (RelativeLayout) itemView.findViewById(R.id.relLayout1);
            task_priority_image = (ImageView) itemView.findViewById(R.id.task_priority_image);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle("Select the action");
            menu.add(0, 0, getAdapterPosition(), "삭제");
            menu.add(0, 1, getAdapterPosition(), "수정");

        }
    }

}
