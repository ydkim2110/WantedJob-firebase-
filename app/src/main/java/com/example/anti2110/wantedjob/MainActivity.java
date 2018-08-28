package com.example.anti2110.wantedjob;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.anti2110.wantedjob.notice.BottomNoticeFragment;
import com.example.anti2110.wantedjob.settings.LoginActivity;
import com.example.anti2110.wantedjob.more.BottomMoreFragment;
import com.example.anti2110.wantedjob.post.BottomPostFragment;
import com.example.anti2110.wantedjob.search.BottomSearchFragment;
import com.example.anti2110.wantedjob.todo.BottomTodoFragment;
import com.example.anti2110.wantedjob.utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private BottomNoticeFragment noticeFragment;
    private BottomSearchFragment searchFragment;
    private BottomPostFragment postFragment;
    private BottomTodoFragment todoFragment;
    private BottomMoreFragment moreFragment;

    private long lastTimeBackPressed;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        frameLayout = (FrameLayout) findViewById(R.id.container);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        setFragment(new BottomNoticeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        getSupportFragmentManager().popBackStack();
                        fragment = new BottomNoticeFragment();
                        break;
                    case R.id.bottom_search:
                        fragment = new BottomSearchFragment();
                        break;
                    case R.id.bottom_post:
                        fragment = new BottomPostFragment();
                        break;
                    case R.id.bottom_todo:
                        fragment = new BottomTodoFragment();
                        break;
                    case R.id.bottom_more:
                        fragment = new BottomMoreFragment();
                        break;
                    default:
                        return  false;
                }

                if(fragment != null) {
                    setFragment(fragment);
                }

                return true;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (System.currentTimeMillis() - lastTimeBackPressed < 1000) {
                finish();
                return;
            }
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            lastTimeBackPressed = System.currentTimeMillis();
        } else {
            getSupportFragmentManager().popBackStack();
        }


    }
}
