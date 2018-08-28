package com.example.anti2110.wantedjob.notice.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.notice.model.NoticeVO;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminNoticeActivity extends AppCompatActivity {

    private ImageView admin_notice_toolbar_back_arrow;
    private ImageView admin_notice_toolbar_save;

    private Spinner admin_notice_category_spinner;
    private Spinner admin_notice_sub_category_spinner;

    private EditText admin_notice_company_name_et;
    private EditText admin_notice_start_date_et;
    private EditText admin_notice_end_date_et;
    private EditText admin_notice_title_et;
    private EditText admin_notice_sub_title_et;
    private EditText admin_notice_content_et;
    private EditText admin_notice_image_url_et;
    private EditText admin_notice_link_et;
    private EditText admin_notice_target_et;
    private EditText admin_notice_edu_background_et;

    private String category;
    private String sub_category;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notice);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        admin_notice_company_name_et = (EditText) findViewById(R.id.admin_notice_company_name_et);
        admin_notice_start_date_et = (EditText) findViewById(R.id.admin_notice_start_date_et);
        admin_notice_end_date_et = (EditText) findViewById(R.id.admin_notice_end_date_et);
        admin_notice_title_et = (EditText) findViewById(R.id.admin_notice_title_et);
        admin_notice_sub_title_et = (EditText) findViewById(R.id.admin_notice_sub_title_et);
        admin_notice_content_et = (EditText) findViewById(R.id.admin_notice_content_et);
        admin_notice_image_url_et = (EditText) findViewById(R.id.admin_notice_image_url_et);
        admin_notice_link_et = (EditText) findViewById(R.id.admin_notice_link_et);
        admin_notice_target_et = (EditText) findViewById(R.id.admin_notice_target_et);
        admin_notice_edu_background_et = (EditText) findViewById(R.id.admin_notice_edu_background_et);

        admin_notice_toolbar_back_arrow = (ImageView) findViewById(R.id.admin_notice_toolbar_back_arrow);
        admin_notice_toolbar_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        admin_notice_toolbar_save = (ImageView) findViewById(R.id.admin_notice_toolbar_save);
        admin_notice_toolbar_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AdminNoticeActivity.this)
                        .setTitle("채용공고 등록")
                        .setMessage("신규 채용공고를 등록하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                saveData();
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
            }
        });

        admin_notice_category_spinner = (Spinner) findViewById(R.id.admin_notice_category_spinner);
        admin_notice_sub_category_spinner = (Spinner) findViewById(R.id.admin_notice_sub_category_spinner);

        category = (String) admin_notice_category_spinner.getSelectedItem();
        sub_category = (String) admin_notice_sub_category_spinner.getSelectedItem();

        admin_notice_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (String) admin_notice_category_spinner.getSelectedItem();
                Toast.makeText(AdminNoticeActivity.this, ""+category, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        admin_notice_sub_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sub_category = (String) admin_notice_sub_category_spinner.getSelectedItem();
                Toast.makeText(AdminNoticeActivity.this, ""+sub_category, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void saveData() {

        String id = currentUser.getEmail();
        String company_name = admin_notice_company_name_et.getText().toString();
        String title = admin_notice_title_et.getText().toString();
        String sub_title = admin_notice_sub_title_et.getText().toString();
        String content = admin_notice_content_et.getText().toString();
        String start_date = admin_notice_start_date_et.getText().toString();
        String end_date = admin_notice_end_date_et.getText().toString();
        String image_url = admin_notice_image_url_et.getText().toString();
        String link  = admin_notice_link_et.getText().toString();
        String view_count  = "0";
        String timestamp =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String target = admin_notice_target_et.getText().toString();
        String edu_background = admin_notice_edu_background_et.getText().toString();
        String reply_count = "0";

        NoticeVO noticeVO = new NoticeVO(id, company_name, title, sub_title, content, start_date, end_date,
                image_url, link, view_count, timestamp, target, edu_background, reply_count);

        mFirestore.collection("notice")
                .document(category)
                .collection(sub_category)
                .add(noticeVO)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AdminNoticeActivity.this, "새 채용공고가 등록되었습니다. \n"+documentReference.getId(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
