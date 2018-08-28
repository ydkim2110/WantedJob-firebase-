package com.example.anti2110.wantedjob.notice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anti2110.wantedjob.R;
import com.example.anti2110.wantedjob.notice.model.MessageVO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeDetailActivity extends AppCompatActivity {

    private static final String TAG = "NoticeDetailActivity";

    public static final String EXTRA_DOCUMENT_ID = "document_id";
    public static final String EXTRA_COMPANY_NAME = "company_name";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_SUB_TITLE = "sub_title";
    public static final String EXTRA_CONTENT = "content";
    public static final String EXTRA_END_DATE = "end_date";
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_IMAGE_URL = "image_url";

    private TextView company_name;
    private TextView title;
    private TextView sub_title;
    private TextView end_date;
    private TextView id;
    private TextView notice_detail_reply;
    private ImageView notice_image;
    private HtmlTextView content;
    private RelativeLayout relLayout4_reply;
    private LinearLayout linLayout1_likes;
    private LinearLayout linLayout1_share;

    private EditText notice_reply_et;
    private ImageView notice_reply_send_image;

    private ImageView notice_detail_toolbar_back_arrow;
    private ImageView notice_detail_toolbar_bookmark;

    private RecyclerView recyclerView;
    private NoticeReplyRecyclerAdapter adapter;

    private FirebaseFirestore mFirestore;
    private CollectionReference mReference;
    private String user_id;
    private String user_email;

    private List<MessageVO> messageVOList;

    private boolean isBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        final String passDocumentId = getIntent().getStringExtra(EXTRA_DOCUMENT_ID);
        final String passCompanyName = getIntent().getStringExtra(EXTRA_COMPANY_NAME);
        final String passTitle = getIntent().getStringExtra(EXTRA_TITLE);
        final String passSubTitle = getIntent().getStringExtra(EXTRA_SUB_TITLE);
        final String passContent = getIntent().getStringExtra(EXTRA_CONTENT);
        final String passEndDate = getIntent().getStringExtra(EXTRA_END_DATE);
        final String passId = getIntent().getStringExtra(EXTRA_ID);
        final String passImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);

        initWidgets(passDocumentId);
        widgetsAction();
        loadReplyMessage(passDocumentId);

        company_name.setText(passCompanyName);
        title.setText(passTitle);
        sub_title.setText(passSubTitle);
        id.setText(passId);
        end_date.setText(passEndDate);
        content.setHtml(passContent, new HtmlHttpImageGetter(content));

        /**
         * 이미지 로드 및 클릭이벤트
         */
        Picasso.get().load(passImageUrl).placeholder(R.drawable.laptop).into(notice_image);
        notice_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeDetailActivity.this, NoticeFullScreenActivity.class);
                intent.setData(Uri.parse(passImageUrl));
                startActivity(intent);
            }
        });

        /**
         * 댓글수 로드
         */
        loadReplyCount(passDocumentId);

        /**
         * 댓글 저장
         */
        notice_reply_send_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = notice_reply_et.getText().toString();
                saveMessage(message, passDocumentId);
            }
        });

        /**
         * 북마크
         */
        // 북마크 체크 여부 확인
        checkBookmark(passDocumentId);
        // 북마크 버튼 클릭 이벤트 처리
        notice_detail_toolbar_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id != null) {
                    if(isBookmark) {
                        deleteBookmark(passDocumentId, user_id);
                    } else {
                        addBookmark(passDocumentId, user_id);
                    }
                }
            }
        });

    }


    /**
     * 위젯 초기화
     */
    private void initWidgets(String passDocumentId) {
        mFirestore = FirebaseFirestore.getInstance();
        mReference = mFirestore.collection("notice");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        user_id = currentUser.getUid();
        user_email = currentUser.getEmail();

        messageVOList = new ArrayList<>();
        relLayout4_reply = findViewById(R.id.relLayout4_reply);
        relLayout4_reply.setVisibility(View.INVISIBLE);

        recyclerView = findViewById(R.id.notice_reply_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoticeDetailActivity.this));
        adapter = new NoticeReplyRecyclerAdapter(messageVOList, NoticeDetailActivity.this, passDocumentId);
        recyclerView.setAdapter(adapter);

        title = findViewById(R.id.notice_detail_title);
        sub_title = findViewById(R.id.notice_detail_sub_title);
        content =  findViewById(R.id.notice_detail_content);
        company_name = findViewById(R.id.notice_detail_category);
        id = findViewById(R.id.notice_detail_id);
        notice_detail_reply = findViewById(R.id.notice_detail_reply);
        end_date = findViewById(R.id.notice_detail_date);
        notice_image = findViewById(R.id.notice_image);
        linLayout1_likes = findViewById(R.id.linLayout1_likes);
        linLayout1_share = findViewById(R.id.linLayout1_share);

        notice_reply_et = findViewById(R.id.notice_reply_et);

        notice_reply_send_image = findViewById(R.id.notice_reply_send_image);
        notice_reply_send_image.setVisibility(View.INVISIBLE);

        notice_detail_toolbar_back_arrow = findViewById(R.id.notice_detail_toolbar_back_arrow);
        notice_detail_toolbar_bookmark = findViewById(R.id.notice_detail_toolbar_bookmark);
    }

    /**
     * 위젯 클릭 이벤트
     */
    private void widgetsAction() {
        // 좋아요 클릭
        linLayout1_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoticeDetailActivity.this, "좋아요 클릭!", Toast.LENGTH_SHORT).show();
            }
        });
        // 공유하기 클릭
        linLayout1_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoticeDetailActivity.this, "공유하기 클릭!", Toast.LENGTH_SHORT).show();
            }
        });

        // 댓글 작성 이벤트
        notice_reply_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0) {
                    notice_reply_send_image.setVisibility(View.VISIBLE);
                } else {
                    notice_reply_send_image.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 뒤로가기
        notice_detail_toolbar_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 댓글 수
     */
    private void loadReplyCount(String passDocumentId) {
        mFirestore.collection("notice")
                .document("finance")
                .collection("bank")
                .document(passDocumentId)
                .collection("messages")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if(!documentSnapshots.isEmpty()) {
                            int count = documentSnapshots.size();
                            notice_detail_reply.setText(String.valueOf(count));
                        }
                    }
                });
    }

    /**
     * 북마크 저장
     */
    private void addBookmark(final String passDocumentId, final String user_id) {
        Map<String, String> boomarkMap = new HashMap<>();
        boomarkMap.put("uid", user_id);

        mFirestore.collection("notice")
                .document("finance")
                .collection("bank")
                .document(passDocumentId)
                .collection("bookmarks")
                .document(user_id)
                .set(boomarkMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NoticeDetailActivity.this, "북마크에 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        notice_detail_toolbar_bookmark.setImageResource(R.drawable.ic_bookmark_red);
                        isBookmark = true;

// 북마크 USER 에 추가하기(추후)
//                        mFirestore.collection("user")
//                                .document(passDocumentId)
//                                .update("bookmark", Arrays.asList(user_id));

                    }
                });
    }

    /**
     * 북마크 삭제
     */
    private void deleteBookmark(String passDocumentId, String user_id) {

        mFirestore.collection("notice")
                .document("finance")
                .collection("bank")
                .document(passDocumentId)
                .collection("bookmarks")
                .document(user_id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NoticeDetailActivity.this, "북마크에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        notice_detail_toolbar_bookmark.setImageResource(R.drawable.ic_bookmark_black);
                        isBookmark = false;
                    }
                });
    }

    /**
     * 북마크 확인
     */
    private void checkBookmark(String passDocumentId) {
        mReference.document("finance")
                .collection("bank")
                .document(passDocumentId)
                .collection("bookmarks")
                .document(user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()) {
                            DocumentSnapshot ds = task.getResult();
                            if(ds.exists()) {
                                Toast.makeText(NoticeDetailActivity.this, "있음", Toast.LENGTH_SHORT).show();
                                notice_detail_toolbar_bookmark.setImageResource(R.drawable.ic_bookmark_red);
                                isBookmark = true;
                            } else {
                                Toast.makeText(NoticeDetailActivity.this, "없음", Toast.LENGTH_SHORT).show();
                                isBookmark = false;
                            }

                        }
                    }
                });

    }

    /**
     * 댓글 불러오기
     */
    public void loadReplyMessage(String passDocumentId) {
        Query query = mReference.document("finance")
                .collection("bank").document(passDocumentId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(!documentSnapshots.isEmpty()) {
                    relLayout4_reply.setVisibility(View.VISIBLE);
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        MessageVO messageVO = doc.getDocument().toObject(MessageVO.class);
                        messageVOList.add(messageVO);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * 댓글 저장
     */
    private void saveMessage(String message, final String passDocumentId) {
        String timestamp =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        DocumentReference ref = mFirestore.collection("notice")
                .document("finance")
                .collection("bank")
                .document(passDocumentId)
                .collection("messages").document();

        MessageVO messageVO = new MessageVO(user_email, message, timestamp, ref.getId());

        ref.set(messageVO).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(NoticeDetailActivity.this, "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        dismissKeyboard();
        notice_reply_et.setText("");
    }

    /**
     * 키보드 닫기
     */
    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), 0);
        }
    }


}
