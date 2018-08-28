package com.example.anti2110.wantedjob.settings;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.anti2110.wantedjob.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignoutActivity extends AppCompatActivity {

    private ProgressBar sign_out_progressBar;
    private TextView sign_out_progressBar_text;
    private Button sign_out_confirm_btn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser == null) {
//                    Intent intent = new Intent(SignoutActivity.this, LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
                }
            }
        };

        sign_out_progressBar = (ProgressBar) findViewById(R.id.sign_out_progressBar);
        sign_out_progressBar_text = (TextView) findViewById(R.id.sign_out_progressBar_text);
        sign_out_progressBar.setVisibility(View.INVISIBLE);
        sign_out_progressBar_text.setVisibility(View.INVISIBLE);

        sign_out_confirm_btn = (Button) findViewById(R.id.sign_out_confirm_btn);
        sign_out_confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_out_progressBar.setVisibility(View.VISIBLE);
                sign_out_progressBar_text.setVisibility(View.VISIBLE);
                mAuth.signOut();
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}
