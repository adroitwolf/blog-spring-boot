package run.app.entity.model;

import java.util.Date;

public class BlogLabel {
    private Long id;

    private String title;

    private Date createDate;

    private Integer citeNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCiteNum() {
        return citeNum;
    }

    public void setCiteNum(Integer citeNum) {
        this.citeNum = citeNum;
    }
}