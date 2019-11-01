package run.app.service.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.UserDetail;
import run.app.entity.model.*;
import run.app.entity.VO.UserParams;
import run.app.mapper.BloggerAccountMapper;
import run.app.mapper.BloggerProfileMapper;
import run.app.security.token.TokenService;
import run.app.service.RoleService;
import run.app.service.UserService;
import run.app.util.UploadUtil;

import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/6/26 21:34
 * Description: ://用户服务层
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BloggerAccountMapper bloggerAccountMapper;

    @Autowired
    BloggerProfileMapper bloggerProfileMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    TokenService tokenService;


    @Override
    public @NonNull BloggerProfile findUserDetailByBloggerId(@NonNull Long bloggerId) {
        return bloggerProfileMapper.selectByPrimaryKey(bloggerId);
    }

    @Override
    public @NonNull UserDetail updateProfileById(@NonNull UserParams userParams, @NonNull String token) {


        Long userId = tokenService.getUserIdWithToken(token);
        BloggerProfile bloggerProfile = new BloggerProfile();

//    这个才是昵称
        bloggerProfile.setBloggerId(userId);
        bloggerProfile.setNickname(userParams.getUsername());
        bloggerProfile.setAboutMe(userParams.getAboutMe());
//        bloggerProfileMapper.updateByExampleSelective(bloggerProfileWithBLOBs,bloggerProfileExample);


        bloggerProfileMapper.updateByPrimaryKeySelective(bloggerProfile);

        UserDetail userDetail = new UserDetail();

        userDetail.setAboutMe(bloggerProfile.getAboutMe());
        userDetail.setUsername(bloggerProfile.getNickname());

        return userDetail;
    }

    @Override
    public BaseResponse getUserDetailByToken(@NonNull String token) {
        Long id = tokenService.getUserIdWithToken(token);

        @NonNull
        BloggerProfile bloggerProfile = findUserDetailByBloggerId(id);


        UserDetail userDetail = new UserDetail();
        userDetail.setAvatarId(bloggerProfile.getAvatarId());

        BloggerAccount bloggerAccount = bloggerAccountMapper.selectByPrimaryKey(id);

        BeanUtils.copyProperties(bloggerAccount,userDetail);




        //找到用户权限
        userDetail.setRoles(roleService.getRolesByUserId(id).stream().map(n->n.getAuthority()).collect(Collectors.toList()));

        return new BaseResponse(HttpStatus.OK.value(),"",userDetail);

//        return tokenService.findUserDetailsByToken(token);

    }

    @Transactional
    @Override
    public void uploadAvatarId(@NonNull String avatar, @NonNull String token) {
        Long id = tokenService.getUserIdWithToken(token);

        BloggerProfile bloggerProfile = bloggerProfileMapper.selectByPrimaryKey(id);

        if (!StringUtils.isBlank(bloggerProfile.getAvatarId())) {
            UploadUtil instance = UploadUtil.getInstance();
            instance.delFile(bloggerProfile.getAvatarId());
        }

        BloggerProfile profile = new BloggerProfile();
        profile.setBloggerId(id);
        profile.setAvatarId(avatar);
        bloggerProfileMapper.updateByPrimaryKeySelective(profile);

    }

    @Override
    public boolean logout(String token) {
        return tokenService.logout(token);
    }


}
