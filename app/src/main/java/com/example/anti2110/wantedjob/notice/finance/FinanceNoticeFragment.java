package com.example.anti2110.wantedjob.notice.finance;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.model.User;
import com.example.anti2110.wantedjob.notice.admin.AdminNoticeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FinanceNoticeFragment extends Fragment {

    private FloatingActionButton fab;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private DocumentReference docRef;
    private CollectionReference collRef;
    private List<User> userList;

    private Boolean currentUserisStaff = false;

    public FinanceNoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_finance_notice, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        fab = (FloatingActionButton) view.findViewById(R.id.notice_fab);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            final String currentUserEmail = currentUser.getEmail();

            collRef = mFirestore.collection("user");
            collRef.whereEqualTo("isStaff", "true").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        userList = new ArrayList<>();

                        for(DocumentSnapshot ds : task.getResult()) {
                            User user = ds.toObject(User.class);
                            String email = user.getEmail();
                            userList.add(user);
                        }

                        for(int i=0; i < userList.size(); i++) {
                            if(currentUserEmail.equals(userList.get(i).getEmail())) {
                                activateFab();
                            }
                        }

                    }
                }
            });
        } else {

        }

        setupViewPager(view);

        return view;
    }

    private void activateFab() {
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminNoticeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupViewPager(View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.notice_view_pager);
        FinanceViewPageAdapter pagerAdapter = new FinanceViewPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout =(TabLayout) view.findViewById(R.id.notice_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

}
