package com.example.anti2110.wantedjob.notice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.notice.finance.FinanceNoticeFragment;
import com.example.anti2110.wantedjob.notice.public_instt.PublicNoticeFragment;
import com.example.anti2110.wantedjob.utils.ItemClickListener;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.MenuViewHolder> {

    private String[] menus = {"금융권", "공기업", "대기업", "중견중소", "스타트업", "이공계", "외국계", "추가1", "추가2", "추가3", "추가4"};
    private Context context;

    public MenuRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notice_menu, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, final int position) {
        holder.menu_title.setText(menus[position]);
        holder.notice_menu_relLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToFragment(menus[position]);
            }
        });
    }

    private void sendToFragment(String menu) {
        Fragment fragment = null;
        switch (menu) {
            case "금융권":
                fragment = new FinanceNoticeFragment();
                break;
            case "공기업":
                fragment = new PublicNoticeFragment();
                break;
            default:
                return;
        }

        if(fragment != null) {
            FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.addToBackStack("root_fragment");
            fragmentTransaction.commit();
        }

    }

    @Override
    public int getItemCount() {
        return menus.length;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView menu_title;
        private RelativeLayout notice_menu_relLayout;

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MenuViewHolder(View itemView) {
            super(itemView);

            menu_title = (TextView) itemView.findViewById(R.id.notice_menu_text);
            notice_menu_relLayout = (RelativeLayout) itemView.findViewById(R.id.notice_menu_relLayout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }

}
