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

    /**
    * 功能描述: 设置当前用户的角色
    * @Param: [role, userId]
    * @Return: void
    * @Author: WHOAMI
    * @Date: 2020/1/30 19:53
     */
    void setRolesWithUserId(List<RoleEnum> role, Long userId);


    /**
    * 功能描述: 此方法应该严格保护，因为涉及到写入数据库,并且牵扯到user - role 数据库
    * @Date: 2019/10/30 22:48
     */
    void addRole(BloggerRole bloggerRole);


    /**
    * 功能描述: 根据角色类型获取到角色的id
    * @Param: [role]
    * @Return: java.lang.Long
    * @Author: WHOAMI
    * @Date: 2020/1/30 19:54
     */
    Long getRoleIdByType(RoleEnum role);


    /**
    * 功能描述: 根据用户id获取到当前用户的角色s
    * @Param: [userId]
    * @Return: java.util.List<run.app.entity.enums.RoleEnum>
    * @Author: WHOAMI
    * @Date: 2020/1/30 19:54
     */
    List<RoleEnum> getRolesByUserId(Long userId);

    /**
    * 功能描述: 通过id获取到当前的角色
    * @Param: [id]
     @Return: run.app.entity.enums.RoleEnum
    * @Author: WHOAMI
    * @Date: 2020/1/30 19:55
            */
    RoleEnum getRoleById(Long id);


    /**
    * 功能描述: 根据id删除用户和角色的关系
    * @Param: [id]
    * @Return: void
    * @Author: WHOAMI
    * @Date: 2020/1/30 19:55
     */
    void deleteUserById(Long id);
}
