package com.example.anti2110.wantedjob.todo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.database.DbHelper;

import java.util.Calendar;

public class TodoActivity extends AppCompatActivity {

    private ImageView todo_add_toolbar_back_arrow;
    private ImageView todo_add_toolbar_save;

    private EditText todo_add_title_et;
    private Button todo_add_end_date_et;
    private Button todo_add_end_time_et;
    private EditText todo_add_content_et;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        dbHelper = new DbHelper(this);

        todo_add_title_et = findViewById(R.id.todo_add_title_et);
        todo_add_end_date_et = findViewById(R.id.todo_add_end_date_et);
        todo_add_end_time_et = findViewById(R.id.todo_add_end_time_et);
        todo_add_content_et = findViewById(R.id.todo_add_content_et);

        todo_add_toolbar_back_arrow = (ImageView) findViewById(R.id.todo_add_toolbar_back_arrow);
        todo_add_toolbar_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        todo_add_toolbar_save = (ImageView) findViewById(R.id.todo_add_toolbar_save);
        todo_add_toolbar_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });

        todo_add_end_date_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TodoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        todo_add_end_date_et.setText(year+"/"+month+"/"+dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        todo_add_end_time_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(TodoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String AM_PM ;
                        if(hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        todo_add_end_time_et.setText(hourOfDay+":"+minute+" "+AM_PM);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

    }

    private void showConfirmDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TodoActivity.this);
        alertDialog.setTitle("저장");
        alertDialog.setMessage("내 할일을 저장할까요?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                insertTask();
            }
        });

        alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void insertTask() {
        String title = todo_add_title_et.getText().toString();
        String date = todo_add_end_date_et.getText().toString();
        String memo = todo_add_content_et.getText().toString();

        boolean isInserted = dbHelper.insertNewTask(title, date, memo);
        if(isInserted) {
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(TodoActivity.this, "등록 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

}
