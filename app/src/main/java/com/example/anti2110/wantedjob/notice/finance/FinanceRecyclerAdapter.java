package com.example.anti2110.wantedjob.notice.finance;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anti2110.wantedjob.notice.NoticeDetailActivity;
import com.example.anti2110.wantedjob.utils.ItemClickListener;
import com.example.anti2110.wantedjob.model.Notice;
import com.example.anti2110.wantedjob.R;

import java.util.List;

public class FinanceRecyclerAdapter extends RecyclerView.Adapter<FinanceRecyclerAdapter.MainViewHolder> {

    private static final String TAG = "FinanceRecyclerAdapter";

    public List<Notice> noticeList;
    private Context context;

    public FinanceRecyclerAdapter(List<Notice> noticeList, Context context) {
        this.noticeList = noticeList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notice, parent, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {
        CardView cardView = holder.cardview;
        String title = noticeList.get(position).getTitle();
        String content = noticeList.get(position).getContent();

        if(content.length() > 70) {
            content = content.substring(0, 70)+"...+더보기";
        }

        holder.title.setText(title);
        holder.content.setText(content);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoticeDetailActivity.class);
                intent.putExtra(NoticeDetailActivity.EXTRA_TITLE, noticeList.get(position).getTitle());
                intent.putExtra(NoticeDetailActivity.EXTRA_CONTENT, noticeList.get(position).getContent());
                context.startActivity(intent);
            }
        });
        holder.linLayout2_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, noticeList.get(position).getTitle()+" likes click!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private CardView cardview;
        private TextView title;
        private TextView content;
        private LinearLayout linLayout2_likes;

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MainViewHolder(View itemView) {
            super(itemView);

            cardview = (CardView) itemView.findViewById(R.id.cardview);
            title = (TextView) itemView.findViewById(R.id.home_notice_text_title);
            content = (TextView) itemView.findViewById(R.id.home_notice_text_sub_title);
            linLayout2_likes = (LinearLayout) itemView.findViewById(R.id.linLayout2_likes);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }

}
