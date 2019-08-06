package run.app.entity.model;

import java.util.Date;

public class BlogWithBLOBs extends Blog {
    private String content;

    private String contentMd;


    public BlogWithBLOBs() {
    }


    public BlogWithBLOBs(Integer id, String status, String title, String summary, Date releaseDate, Date nearestModifyDate, String tagTitle, String content, String contentMd) {
        super(id, status, title, summary, releaseDate, nearestModifyDate, tagTitle);
        this.content = content;
        this.contentMd = contentMd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentMd() {
        return contentMd;
    }

    public void setContentMd(String contentMd) {
        this.contentMd = contentMd == null ? null : contentMd.trim();
    }
}