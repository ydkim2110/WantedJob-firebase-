package com.example.anti2110.wantedjob.notice.model;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class NoticeVoId {
    @Exclude
    public String NoticeVoId;

    public <T extends NoticeVoId> T withId(@NonNull final String id) {
        this.NoticeVoId = id;
        return (T) this;
    }

}
