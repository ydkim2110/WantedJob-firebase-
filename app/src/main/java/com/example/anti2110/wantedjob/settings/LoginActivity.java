package com.example.anti2110.wantedjob.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anti2110.wantedjob.MainActivity;
import com.example.anti2110.wantedjob.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {

    private EditText login_email_edit_text;
    private EditText login_password_edit_text;
    private Button login_btn;
    private Button login_register_btn;

    private ProgressBar login_progressbar;
    private TextView login_progressbar_text;

    private long lastTimeBackPressed;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        login_progressbar = (ProgressBar) findViewById(R.id.login_progressbar);
        login_progressbar_text = (TextView) findViewById(R.id.login_progressbar_text);
        login_progressbar.setVisibility(View.INVISIBLE);
        login_progressbar_text.setVisibility(View.INVISIBLE);

        login_email_edit_text = (EditText) findViewById(R.id.login_email_edit_text);
        login_password_edit_text = (EditText) findViewById(R.id.login_password_edit_text);

        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        login_register_btn = (Button) findViewById(R.id.login_register_btn);
        login_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser() {
        login_progressbar.setVisibility(View.VISIBLE);
        login_progressbar_text.setVisibility(View.VISIBLE);

        String email = login_email_edit_text.getText().toString();
        String password = login_password_edit_text.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            login_progressbar.setVisibility(View.INVISIBLE);
                            login_progressbar_text.setVisibility(View.INVISIBLE);

                            Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            login_progressbar.setVisibility(View.INVISIBLE);
                            login_progressbar_text.setVisibility(View.INVISIBLE);

                            Toast.makeText(LoginActivity.this, "로그인 실패!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1000) {
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
}
