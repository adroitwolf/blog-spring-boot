package run.app.entity.model;

import lombok.ToString;

import java.util.Date;

@ToString
public class Blog {
    private Long id;

    private Integer bloggerId;

    private String status;

    private String title;

    private Integer pictureId;

    private String summary;

    private Date releaseDate;

    private Date nearestModifyDate;

    private String tagTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBloggerId() {
        return bloggerId;
    }

    public void setBloggerId(Integer bloggerId) {
        this.bloggerId = bloggerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getNearestModifyDate() {
        return nearestModifyDate;
    }

    public void setNearestModifyDate(Date nearestModifyDate) {
        this.nearestModifyDate = nearestModifyDate;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle == null ? null : tagTitle.trim();
    }
}