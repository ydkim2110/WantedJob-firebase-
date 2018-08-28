package com.example.anti2110.wantedjob.notice;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.notice.adapter.MenuRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomNoticeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MenuRecyclerAdapter adapter;

    private AppBarLayout collapse_appbar;
    private TextView notice_toolbar_title;

    public BottomNoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_notice, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.notice_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MenuRecyclerAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notice_toolbar_title = view.findViewById(R.id.notice_toolbar_title);
        notice_toolbar_title.setVisibility(View.INVISIBLE);

        collapse_appbar = view.findViewById(R.id.collapse_appbar);
        collapse_appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    //  Collapsed
                    notice_toolbar_title.setVisibility(View.VISIBLE);
                } else if(verticalOffset == 0) {
                    //Expanded
                    notice_toolbar_title.setVisibility(View.INVISIBLE);
                } else {
                    // Somewhere in between
                }

            }
        });
    }
}
