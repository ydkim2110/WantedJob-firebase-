package com.example.anti2110.wantedjob.more;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.settings.SignoutActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomMoreFragment extends Fragment {

    private CardView profile_card_view;
    private CardView logout_card_view;
    private CardView email_card_view;

    public BottomMoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_more, container, false);

        profile_card_view = (CardView) view.findViewById(R.id.profile_card_view);
        profile_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        logout_card_view = (CardView) view.findViewById(R.id.logout_card_view);
        logout_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignoutActivity.class);
                startActivity(intent);
            }
        });

        email_card_view = (CardView) view.findViewById(R.id.email_card_view);
        email_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmailActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

}
