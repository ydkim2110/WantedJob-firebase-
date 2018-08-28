package com.example.anti2110.wantedjob.post;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anti2110.wantedjob.R;

public class PostActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "post_title";

    private EditText et_post_category;
    private EditText et_post_title;
    private EditText et_post_content;

    private ImageView back_image;
    private ImageView save_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initWidgets();

        String title = getIntent().getStringExtra(EXTRA_TITLE);

        if(title != null) {
            Toast.makeText(this, ""+title, Toast.LENGTH_SHORT).show();
            et_post_category.setText(title);
        }

        toolbarAction();


    }

    /**
     * 툴바 이미지 클릭
     */
    private void toolbarAction() {
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PostActivity.this);
                alertDialog.setTitle("게시글 저장")
                        .setMessage("새 게시글을 저장할까요?")
                        .setCancelable(false)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Toast.makeText(PostActivity.this, "새글을 저장했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("아니오", null)
                        .show();
            }
        });
    }

    /**
     * 위젯 초기화
     */
    private void initWidgets() {
        et_post_category = findViewById(R.id.et_post_category);
        et_post_title = findViewById(R.id.et_post_title);
        et_post_content = findViewById(R.id.et_post_content);

        back_image = findViewById(R.id.post_toolbar_back_arrow_image);
        save_image = findViewById(R.id.post_toolbar_save_image);
    }

}
