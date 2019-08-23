package run.app.entity.model;

import java.util.Date;

public class BlogLabel {
    private Integer id;

    private String title;

    private Date createDate;

    private Integer citeNum;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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