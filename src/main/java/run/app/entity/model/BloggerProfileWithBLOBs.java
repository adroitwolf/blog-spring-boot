package run.app.entity.model;

public class BloggerProfileWithBLOBs extends BloggerProfile {
    private String aboutMe;

    private String intro;

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe == null ? null : aboutMe.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }
}