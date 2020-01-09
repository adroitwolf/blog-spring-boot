package run.app.service;

import run.app.entity.enums.RoleEnum;
import run.app.entity.model.BloggerRole;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 22:39
 * Description: 用户服务接口
 */
public interface RoleService {

    void setRoleWithUserId(RoleEnum role, Long userId);


    /**
    * 功能描述: 此方法应该严格保护，因为涉及到写入数据库,并且牵扯到user - role 数据库
    * @Date: 2019/10/30 22:48
     */
    void addRole(BloggerRole bloggerRole);


    Long getRoleIdByType(RoleEnum role);


    List<RoleEnum> getRolesByUserId(Long userId);


    RoleEnum getRoleById(Long id);


    void deleteUserById(Long id);
}
