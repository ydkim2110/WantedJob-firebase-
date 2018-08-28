package com.example.anti2110.wantedjob.more;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.anti2110.wantedjob.R;

public class EmailActivity extends AppCompatActivity {

    private ImageView email_toolbar_send;
    private ImageView email_toolbar_back_arrow;

    private EditText email_receiver_edit_text;
    private EditText email_title_edit_text;
    private EditText email_content_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        email_receiver_edit_text = (EditText) findViewById(R.id.email_receiver_edit_text);
        email_title_edit_text = (EditText) findViewById(R.id.email_title_edit_text);
        email_content_edit_text = (EditText) findViewById(R.id.email_content_edit_text);

        email_toolbar_send = (ImageView) findViewById(R.id.email_toolbar_send);
        email_toolbar_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        email_toolbar_back_arrow = (ImageView) findViewById(R.id.email_toolbar_back_arrow);
        email_toolbar_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void sendMail() {
        String recipientList = email_receiver_edit_text.getText().toString();
        String[] recipients = recipientList.split(",");

        String subject = email_title_edit_text.getText().toString();
        String message = email_content_edit_text.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(intent.createChooser(intent, "이메일 선택"));

        email_title_edit_text.setText("");
        email_content_edit_text.setText("");

    }

}
