package com.example.anti2110.wantedjob.todo;

public class Task {
    private String task_name;
    private String task_memo;
    private String task_end_date;

    public Task() {
    }

    public Task(String task_name, String task_memo, String task_end_date) {
        this.task_name = task_name;
        this.task_memo = task_memo;
        this.task_end_date = task_end_date;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_memo() {
        return task_memo;
    }

    public void setTask_memo(String task_memo) {
        this.task_memo = task_memo;
    }

    public String getTask_end_date() {
        return task_end_date;
    }

    public void setTask_end_date(String task_end_date) {
        this.task_end_date = task_end_date;
    }
}
