package run.app.service.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.app.entity.DTO.UserDetail;
import run.app.entity.model.*;
import run.app.entity.VO.UserParams;
import run.app.mapper.BloggerProfileMapper;
import run.app.security.token.TokenService;
import run.app.service.UserService;
import run.app.util.UploadUtil;

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
    BloggerProfileMapper bloggerProfileMapper;

    @Autowired
    TokenService tokenService;


    @Override
    public @NonNull BloggerProfile findUserDetailByBloggerId(@NonNull Long bloggerId) {

//        BloggerProfileExample bloggerProfileExample = new BloggerProfileExample();
//        BloggerProfileExample.Criteria criteria = bloggerProfileExample.createCriteria();
//
//        criteria.andBloggerIdEqualTo(bloggerId);
//
//        List<BloggerProfileWithBLOBs> bloggerProfileWithBLOBs = bloggerProfileMapper.selectByExampleWithBLOBs(bloggerProfileExample);


//        for (BloggerProfileWithBLOBs bloggerProfile: bloggerProfileWithBLOBs) {
//            return bloggerProfile;
//        }

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
    public UserDetail getUserDetailByToken(@NonNull String token) {
        Long id = tokenService.getUserIdWithToken(token);

        @NonNull BloggerProfile bloggerProfile = findUserDetailByBloggerId(id);


        UserDetail userDetail = new UserDetail();
        userDetail.setAvatarId(bloggerProfile.getAvatarId());
        userDetail.setUsername(bloggerProfile.getNickname());
        userDetail.setAboutMe(bloggerProfile.getAboutMe());

        return userDetail;

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
