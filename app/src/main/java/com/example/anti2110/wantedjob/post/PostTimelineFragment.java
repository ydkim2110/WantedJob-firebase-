package com.example.anti2110.wantedjob.post;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anti2110.wantedjob.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostTimelineFragment extends Fragment {


    public PostTimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_timeline, container, false);
    }

}
