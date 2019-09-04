package run.app.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.app.entity.DTO.UserDetail;
import run.app.entity.model.*;
import run.app.entity.params.LoginParams;
import run.app.entity.params.UserParams;
import run.app.exception.BadRequestException;
import run.app.exception.ServiceException;
import run.app.mapper.BloggerAccountMapper;
import run.app.mapper.BloggerProfileMapper;
import run.app.security.token.AuthToken;
import run.app.security.token.TokenService;
import run.app.service.UserService;
import run.app.util.RedisUtil;
import run.app.util.UploadUtil;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
    public @NonNull BloggerProfileWithBLOBs findUserDetailByBloggerId(@NonNull Integer bloggerId) {

        BloggerProfileExample bloggerProfileExample = new BloggerProfileExample();
        BloggerProfileExample.Criteria criteria = bloggerProfileExample.createCriteria();

        criteria.andBloggerIdEqualTo(bloggerId);

        List<BloggerProfileWithBLOBs> bloggerProfileWithBLOBs = bloggerProfileMapper.selectByExampleWithBLOBs(bloggerProfileExample);

        for (BloggerProfileWithBLOBs bloggerProfile: bloggerProfileWithBLOBs) {
            return bloggerProfile;
        }

        return null;
    }

    @Override
    public @NonNull UserDetail updateProfileById(@NonNull UserParams userParams, @NonNull String token) {


//        Integer userId = getUserIdByToken(token);
        Integer userId = tokenService.getUserIdWithToken(token);
        BloggerProfileWithBLOBs bloggerProfileWithBLOBs = new BloggerProfileWithBLOBs();

        BloggerProfileExample bloggerProfileExample = new BloggerProfileExample();

        BloggerProfileExample.Criteria criteria = bloggerProfileExample.createCriteria();

        criteria.andBloggerIdEqualTo(userId);

//    这个才是昵称
        bloggerProfileWithBLOBs.setIntro(userParams.getUsername());

        bloggerProfileWithBLOBs.setAboutMe(userParams.getAboutMe());

        bloggerProfileWithBLOBs.setPhone(userParams.getPhone());
        bloggerProfileWithBLOBs.setEmail(userParams.getEmail());


        bloggerProfileMapper.updateByExampleSelective(bloggerProfileWithBLOBs,bloggerProfileExample);


        UserDetail userDetail = new UserDetail();

        userDetail.setAboutMe(bloggerProfileWithBLOBs.getAboutMe());
        userDetail.setUsername(bloggerProfileWithBLOBs.getIntro());

        userDetail.setPhone(bloggerProfileWithBLOBs.getPhone());
        userDetail.setEmail(bloggerProfileWithBLOBs.getEmail());

        return userDetail;
    }

    @Override
    public UserDetail getUserDetailByToken(@NonNull String token) {
        int id = tokenService.getUserIdWithToken(token);

        @NonNull BloggerProfileWithBLOBs bloggerProfile = findUserDetailByBloggerId(id);


        UserDetail userDetail = new UserDetail();
        userDetail.setAvatarId(bloggerProfile.getAvatarId());
        userDetail.setUsername(bloggerProfile.getIntro());
        userDetail.setEmail(bloggerProfile.getEmail());
        userDetail.setPhone(bloggerProfile.getPhone());
        userDetail.setAboutMe(bloggerProfile.getAboutMe());

        return userDetail;

//        return tokenService.findUserDetailsByToken(token);

    }

    @Transactional
    @Override
    public void uploadAvatarId(@NonNull String avatar, @NonNull String token) {
        int id = tokenService.getUserIdWithToken(token);


        BloggerProfileExample bloggerProfileExample = new BloggerProfileExample();
        BloggerProfileExample.Criteria criteria = bloggerProfileExample.createCriteria();
        criteria.andBloggerIdEqualTo(id);
        List<BloggerProfileWithBLOBs> withBLOBs = bloggerProfileMapper.selectByExampleWithBLOBs(bloggerProfileExample);

//        如果该账户目前有头像，要先删除当前头像
        withBLOBs.stream().filter(Objects::nonNull).findFirst().ifPresent(a->{
            if(!StringUtils.isBlank(a.getAvatarId())){
                UploadUtil instance = UploadUtil.getInstance();
                instance.delFile(a.getAvatarId());
            }
        });



        criteria.andBloggerIdEqualTo(id);

        BloggerProfileWithBLOBs bloggerProfileWithBLOBs = new BloggerProfileWithBLOBs();
        bloggerProfileWithBLOBs.setAvatarId(avatar);
        bloggerProfileMapper.updateByExampleSelective(bloggerProfileWithBLOBs,bloggerProfileExample);
    }




//    @Override
//    public Integer getUserIdByToken(@NonNull String token) {
//
//        String username = tokenService.getUsernameByToken(token);
//
//
//        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();
//        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();
//        criteria.andUsernameEqualTo(username);
//        List<BloggerAccount> bloggerAccounts = bloggerAccountMapper.selectByExample(bloggerAccountExample);
//
//        if(bloggerAccounts.isEmpty()){
//            throw new BadRequestException("用户信息错误，请重试！");
//        }
//
//        for (BloggerAccount bloggerAccount:bloggerAccounts) {
//            return bloggerAccount.getId();
//        }
//
//        return -1;
//    }

    @Override
    public boolean logout(String token) {
        return tokenService.logout(token);
    }


}
