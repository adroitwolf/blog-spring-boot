package run.app.entity.model;

import java.util.Date;

public class BlogLog {
    private Integer id;

    private String romoteip;

    private String username;

    private Date romotetime;

    private String operation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRomoteip() {
        return romoteip;
    }

    public void setRomoteip(String romoteip) {
        this.romoteip = romoteip == null ? null : romoteip.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getRomotetime() {
        return romotetime;
    }

    public void setRomotetime(Date romotetime) {
        this.romotetime = romotetime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }
}