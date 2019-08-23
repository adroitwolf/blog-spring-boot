package run.app.entity.model;

public class BlogTagMapKey {
    private Integer tagId;

    private Integer blogId;

    public BlogTagMapKey() {
    }

    public BlogTagMapKey(Integer tagId, Integer blogId) {
        this.tagId = tagId;
        this.blogId = blogId;
    }

    public BlogTagMapKey(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }
}