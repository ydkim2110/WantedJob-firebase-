package com.example.anti2110.wantedjob.notice.public_instt;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.notice.NoticeDetailActivity;
import com.example.anti2110.wantedjob.notice.model.NoticeVO;
import com.example.anti2110.wantedjob.utils.ItemClickListener;
import com.example.anti2110.wantedjob.utils.TimeChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PublicRecyclerAdapter extends RecyclerView.Adapter<PublicRecyclerAdapter.MainViewHolder>{

    private Context context;
    private List<NoticeVO> noticeList;

    private FirebaseFirestore mFirestore;

    public PublicRecyclerAdapter(List<NoticeVO> noticeList, Context context) {
        this.noticeList = noticeList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notice, parent, false);

        mFirestore = FirebaseFirestore.getInstance();

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, final int position) {

        final String documentId= noticeList.get(position).NoticeVoId;

        CardView cardView = holder.cardview;

        holder.company_name.setText(noticeList.get(position).getCompany_name());
        holder.title.setText(noticeList.get(position).getTitle());
        holder.sub_title.setText(noticeList.get(position).getSub_title());
        holder.target.setText(noticeList.get(position).getTarget());
        holder.edu_background.setText(noticeList.get(position).getEdu_background());
        holder.end_date.setText(noticeList.get(position).getEnd_date());

        /**
         * 이미지 등록
         */
        String image_url = noticeList.get(position).getImage_url();
        Picasso.get().load(image_url).placeholder(R.drawable.laptop).into(holder.imageView);

        /**
         * 등록일
         */
        String str = noticeList.get(position).getTimestamp();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.notice_date.setText(TimeChange.formatTimeString(date));

        /**
         * 댓글 수
         */
        mFirestore.collection("notice")
                .document("finance")
                .collection("bank")
                .document(documentId)
                .collection("messages")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if(!documentSnapshots.isEmpty()) {
                            int count = documentSnapshots.size();
                            holder.notice_reply_count.setText(String.valueOf(count));
                        }
                    }
                });

        /**
         * 조회 수
         */
        if(Integer.parseInt(noticeList.get(position).getView_count()) > 0) {
            holder.notice_view_count.setText(noticeList.get(position).getView_count());
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoticeDetailActivity.class);
                intent.putExtra(NoticeDetailActivity.EXTRA_DOCUMENT_ID, noticeList.get(position).NoticeVoId);
                intent.putExtra(NoticeDetailActivity.EXTRA_COMPANY_NAME, noticeList.get(position).getCompany_name());
                intent.putExtra(NoticeDetailActivity.EXTRA_TITLE, noticeList.get(position).getTitle());
                intent.putExtra(NoticeDetailActivity.EXTRA_SUB_TITLE, noticeList.get(position).getSub_title());
                intent.putExtra(NoticeDetailActivity.EXTRA_CONTENT, noticeList.get(position).getContent());
                intent.putExtra(NoticeDetailActivity.EXTRA_END_DATE, noticeList.get(position).getEnd_date());
                intent.putExtra(NoticeDetailActivity.EXTRA_ID, noticeList.get(position).getId());
                intent.putExtra(NoticeDetailActivity.EXTRA_IMAGE_URL, noticeList.get(position).getImage_url());
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

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardview;
        private LinearLayout linLayout2_likes;

        private TextView company_name;
        private TextView title;
        private TextView sub_title;
        private TextView target;
        private TextView edu_background;
        private TextView end_date;
        private TextView notice_date;
        private TextView notice_reply_count;
        private TextView notice_view_count;
        private ImageView imageView;

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MainViewHolder(View itemView) {
            super(itemView);

            cardview = itemView.findViewById(R.id.cardview);
            linLayout2_likes = itemView.findViewById(R.id.linLayout2_likes);

            company_name = itemView.findViewById(R.id.home_notice_category);
            title = itemView.findViewById(R.id.home_notice_text_title);
            sub_title = itemView.findViewById(R.id.home_notice_text_sub_title);
            target = itemView.findViewById(R.id.home_notice_target);
            edu_background = itemView.findViewById(R.id.home_notice_edu_background);
            end_date = itemView.findViewById(R.id.notice_end_date_text);
            notice_date = itemView.findViewById(R.id.notice_date);
            notice_reply_count = itemView.findViewById(R.id.notice_reply_count);
            notice_view_count = itemView.findViewById(R.id.notice_view_count);
            imageView = itemView.findViewById(R.id.notice_list_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }

}
