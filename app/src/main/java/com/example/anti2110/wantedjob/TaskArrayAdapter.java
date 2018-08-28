package com.example.anti2110.wantedjob;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.anti2110.wantedjob.todo.Task;

import java.util.List;

public class TaskArrayAdapter extends ArrayAdapter<Task> {

    public TaskArrayAdapter(@NonNull Context context, @NonNull List<Task> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Task task = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_todo, parent, false);
        }

        TextView taskName = (TextView) convertView.findViewById(R.id.task_title);

        taskName.setText(task.getTask_name());

        return convertView;
    }
}
