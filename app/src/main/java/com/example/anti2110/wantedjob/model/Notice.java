package com.example.anti2110.wantedjob.model;

public class Notice {
    private String title;
    private String content;

    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Notice() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
