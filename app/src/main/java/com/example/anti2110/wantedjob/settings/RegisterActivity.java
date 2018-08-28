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

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends Activity {

    private EditText register_email_edit_text;
    private EditText register_password_edit_text;
    private EditText register_password_confirm_edit_text;
    private Button register_btn;

    private ProgressBar register_progressbar;
    private TextView register_progressbar_text;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private CollectionReference userListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        userListRef = mFirestore.collection("user");

        register_progressbar = (ProgressBar) findViewById(R.id.register_progressbar);
        register_progressbar_text = (TextView) findViewById(R.id.register_progressbar_text);
        register_progressbar.setVisibility(View.INVISIBLE);
        register_progressbar_text.setVisibility(View.INVISIBLE);

        register_email_edit_text = (EditText) findViewById(R.id.register_email_edit_text);
        register_password_edit_text = (EditText) findViewById(R.id.register_password_edit_text);
        register_password_confirm_edit_text = (EditText) findViewById(R.id.register_password_confirm_edit_text);

        register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_progressbar.setVisibility(View.VISIBLE);
                register_progressbar_text.setVisibility(View.VISIBLE);

                String email = register_email_edit_text.getText().toString();
                String password = register_password_edit_text.getText().toString();
                String confirmPassword = register_password_confirm_edit_text.getText().toString();

                if(password.length() > 7) {
                    if(password.equals(confirmPassword)) {
                        registerUser(email, password);
                    } else {
                        Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        register_progressbar.setVisibility(View.INVISIBLE);
                        register_progressbar_text.setVisibility(View.INVISIBLE);
                    }
                } else  {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 8자리 이상 입력하세요.", Toast.LENGTH_SHORT).show();
                    register_progressbar.setVisibility(View.INVISIBLE);
                    register_progressbar_text.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            register_progressbar.setVisibility(View.INVISIBLE);
                            register_progressbar_text.setVisibility(View.INVISIBLE);

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String user_id  = currentUser.getUid();

                            sendVerificationEmail();
                            saveUser(user_id);

                        } else {
                            register_progressbar.setVisibility(View.INVISIBLE);
                            register_progressbar_text.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUser(String user_id) {
        String email = register_email_edit_text.getText().toString();
        String password = register_password_edit_text.getText().toString();

        User user = new User(email, password);

        userListRef.document(user_id).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "회원가입에 성공했습니다. 이메일 인증을 해주세요.", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendVerificationEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null) {
            currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {

                    } else {

                    }
                }
            });
        }
    }
}
