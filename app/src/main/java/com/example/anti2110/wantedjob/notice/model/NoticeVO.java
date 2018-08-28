package com.example.anti2110.wantedjob.notice.model;

public class NoticeVO extends NoticeVoId {

    private String id;
    private String company_name;
    private String title;
    private String sub_title;
    private String content;
    private String start_date;
    private String end_date;
    private String image_url;
    private String link ;
    private String view_count;
    private String timestamp;
    private String target;
    private String edu_background;
    private String reply_count;

    public NoticeVO() {
    }

    public NoticeVO(String id, String company_name, String title, String sub_title, String content, String start_date, String end_date,
                    String image_url, String link, String view_count, String timestamp, String target, String edu_background, String reply_count) {
        this.id = id;
        this.company_name = company_name;
        this.title = title;
        this.content = content;
        this.start_date = start_date;
        this.end_date = end_date;
        this.image_url = image_url;
        this.link = link;
        this.view_count = view_count;
        this.timestamp = timestamp;
        this.target = target;
        this.edu_background = edu_background;
        this.reply_count = reply_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getEdu_background() {
        return edu_background;
    }

    public void setEdu_background(String edu_background) {
        this.edu_background = edu_background;
    }

    public String getReply_count() {
        return reply_count;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }
}
