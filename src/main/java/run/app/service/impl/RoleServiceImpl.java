package run.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.app.entity.enums.RoleEnum;
import run.app.entity.model.BloggerRole;
import run.app.entity.model.BloggerRoleExample;
import run.app.entity.model.BloggerRoleMapExample;
import run.app.entity.model.BloggerRoleMapKey;
import run.app.exception.ServiceException;
import run.app.mapper.BloggerRoleMapMapper;
import run.app.mapper.BloggerRoleMapper;
import run.app.service.RoleService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 22:43
 * Description: 角色服务实现类
 */
@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    private BloggerRoleMapper bloggerRoleMapper;

    @Autowired
    private BloggerRoleMapMapper bloggerRoleMapMapper;


    @Override
    public void setRolesWithUserId(List<RoleEnum> roles, Long userId) {
        roles.stream().forEach(roleEnum -> {
            Long roleId = getRoleIdByType(roleEnum);
            bloggerRoleMapMapper.insert(new BloggerRoleMapKey(userId, roleId));
        });

    }


    @Override
    public void addRole(BloggerRole bloggerRole) {
        bloggerRoleMapper.insert(bloggerRole);
    }

    @Override
    public Long getRoleIdByType(RoleEnum role) {
        BloggerRoleExample bloggerRoleExample = new BloggerRoleExample();
        BloggerRoleExample.Criteria criteria = bloggerRoleExample.createCriteria();
        criteria.andRoleNameEqualTo(role.getAuthority());
        return bloggerRoleMapper.selectByExample(bloggerRoleExample).stream()
                .findFirst().orElseThrow(() -> new ServiceException("服务异常")).getId();
    }

    @Override
    public List<RoleEnum> getRolesByUserId(Long userId) {
        BloggerRoleMapExample bloggerRoleMapExample = new BloggerRoleMapExample();
        BloggerRoleMapExample.Criteria criteria = bloggerRoleMapExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return bloggerRoleMapMapper.selectByExample(bloggerRoleMapExample).stream()
                .filter(Objects::nonNull)
                .map(n -> getRoleById(n.getRoleId()))
                .collect(Collectors.toList());
    }

    @Override
    public RoleEnum getRoleById(Long id) {
        return RoleEnum.valueOf(bloggerRoleMapper.selectByPrimaryKey(id).getRoleName());
    }

    @Override
    public void deleteUserById(Long id) {
        BloggerRoleMapExample example = new BloggerRoleMapExample();
        BloggerRoleMapExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(id);
        bloggerRoleMapMapper.deleteByExample(example);
    }
}
