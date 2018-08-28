package com.example.anti2110.wantedjob.notice.finance;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.model.Notice;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FinanceNoticeListFragment extends Fragment {

    private int position;
    private TextView textView;

    private RecyclerView recyclerView;
    private FinanceRecyclerAdapter adapter;

    private List<Notice> noticeList;

    public FinanceNoticeListFragment() {

    }

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        FinanceNoticeListFragment fragment = new FinanceNoticeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_finance_notice_list, container, false);

        noticeList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.finance_notice_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        switch (position) {
            case 0:
                for(int i=1; i<101; i++) {
                    Notice notice = new Notice();
                    notice.setTitle("Fragment1-"+i);
                    notice.setContent("Sleep Cycle App Development: Check Out These 3 Solutions Before Creating Sleep Cycle Apps like Sleep Better & Sleep Cycle Alarm Clock-"+i);
                    noticeList.add(notice);
                }
                adapter = new FinanceRecyclerAdapter(noticeList, getActivity());
                recyclerView.setAdapter(adapter);
                break;
            case 1:
                for(int i=1; i<101; i++) {
                    Notice notice = new Notice();
                    notice.setTitle("Fragment2-"+i);
                    notice.setContent("Fragment2-"+i);
                    noticeList.add(notice);
                }
                adapter = new FinanceRecyclerAdapter(noticeList, getActivity());
                recyclerView.setAdapter(adapter);
                break;
            case 2:
                for(int i=1; i<101; i++) {
                    Notice notice = new Notice();
                    notice.setTitle("Fragment3-"+i);
                    notice.setContent("Fragment3-"+i);
                    noticeList.add(notice);
                }
                adapter = new FinanceRecyclerAdapter(noticeList, getActivity());
                recyclerView.setAdapter(adapter);
                break;
            case 3:
                for(int i=1; i<101; i++) {
                    Notice notice = new Notice();
                    notice.setTitle("Fragment4-"+i);
                    notice.setContent("Fragment4-"+i);
                    noticeList.add(notice);
                }
                adapter = new FinanceRecyclerAdapter(noticeList, getActivity());
                recyclerView.setAdapter(adapter);
                break;
            default:
                break;
        }

        return view;
    }

    // onCreateView 에서 리턴한 view 를 가지고 있음
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
