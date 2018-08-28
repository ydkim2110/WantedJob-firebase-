package com.example.anti2110.wantedjob.notice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.notice.model.MessageVO;
import com.example.anti2110.wantedjob.utils.TimeChange;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoticeReplyRecyclerAdapter extends RecyclerView.Adapter<NoticeReplyRecyclerAdapter.MainViewHolder> {

    private List<MessageVO> messageVOList;
    private Context context;
    private String passDocumentId;

    private FirebaseFirestore mFirestore;

    public NoticeReplyRecyclerAdapter(List<MessageVO> messageVOList, Context context, String passDocumentId) {
        this.messageVOList = messageVOList;
        this.context = context;
        this.passDocumentId = passDocumentId;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notice_reply, parent, false);

        mFirestore = FirebaseFirestore.getInstance();

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String user_email = currentUser.getEmail();

        /**
         * 날짜 형식으로 변환
         */
        String str = messageVOList.get(position).getTimestamp();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.id.setText(messageVOList.get(position).getId());
        holder.timestamp.setText(TimeChange.formatTimeString(date));
        holder.message.setText(messageVOList.get(position).getMessage());

        if(user_email.equals(messageVOList.get(position).getId())) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }

        /**
         * 삭제
         */
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mFirestore.collection("notice")
                .document("finance")
                .collection("bank")
                .document(passDocumentId)
                .collection("messages")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show();
                    }
                });

//            .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(context, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return messageVOList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        private TextView id;
        private TextView timestamp;
        private TextView message;
        private ImageView imageView;

        public MainViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.reply_id);
            timestamp = itemView.findViewById(R.id.reply_date);
            message = itemView.findViewById(R.id.reply_message);
            imageView = itemView.findViewById(R.id.reply_delete_image);

        }
    }

}
