package run.app.entity.model;

public class BlogTagMapKey {
    private Long tagId;

    private Long blogId;

    public BlogTagMapKey() {
    }

    public BlogTagMapKey(Long tagId, Long blogId) {
        this.tagId = tagId;
        this.blogId = blogId;
    }

    public BlogTagMapKey(Long tagId) {
        this.tagId = tagId;
    }


    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }
}