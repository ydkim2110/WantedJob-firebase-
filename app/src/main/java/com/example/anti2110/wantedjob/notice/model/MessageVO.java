package com.example.anti2110.wantedjob.notice.model;

public class MessageVO {

    private String id;
    private String message;
    private String timestamp;
    private String document_id;

    public MessageVO() {
    }

    public MessageVO(String id, String message, String timestamp, String document_id) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.document_id = document_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }
}
