package com.example.anti2110.wantedjob.notice.public_instt;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.model.Notice;
import com.example.anti2110.wantedjob.notice.model.NoticeVO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicNoticeFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView textView;

    private List<NoticeVO> noticeList;

    private RecyclerView recyclerView;
    private PublicRecyclerAdapter adapter;

    private FirebaseFirestore mFirestore;
    private CollectionReference mReference;

    public PublicNoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_public_notice, container, false);

        noticeList = new ArrayList<>();

        mFirestore = FirebaseFirestore.getInstance();
        mReference = mFirestore.collection("notice");

        recyclerView = view.findViewById(R.id.public_notice_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new PublicRecyclerAdapter(noticeList, getActivity());
        recyclerView.setAdapter(adapter);

        loadNoticeList();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.finance_toolbar_back_arrow_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        progressBar = view.findViewById(R.id.public_notice_progressbar);
        textView = view.findViewById(R.id.public_notice_progressbar_text);

    }

    private void loadNoticeList() {
        Query query = mReference.document("finance").collection("bank");

        query.orderBy("timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    for (DocumentSnapshot ds : task.getResult()) {
                        String id = ds.getId();
                        NoticeVO noticeVO = ds.toObject(NoticeVO.class).withId(id);
                        noticeList.add(noticeVO);
                    }
                }
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        });

    }


}
