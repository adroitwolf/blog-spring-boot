package run.app.entity.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BloggerRole {
    private Long id;

    private String roleName;

    private String roleZh;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleZh() {
        return roleZh;
    }

    public void setRoleZh(String roleZh) {
        this.roleZh = roleZh == null ? null : roleZh.trim();
    }
}