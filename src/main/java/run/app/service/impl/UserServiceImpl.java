package run.app.service.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/6/26 21:34
 * Description: ://用户服务层
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    BloggerAccountMapper bloggerAccountMapper;


    @Autowired
    BloggerProfileMapper bloggerProfileMapper;

    @Autowired
    TokenService tokenService;

    @Override
    public @NonNull AuthToken loginService(@NonNull LoginParams loginParams) {

        if(tokenService.islogined(loginParams.getUsername())){
            throw new BadRequestException("用户已经在别处登陆！");
        }

        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();

        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();

        criteria.andUsernameEqualTo(loginParams.getUsername());

        List<BloggerAccount> bloggerAccounts = bloggerAccountMapper.selectByExample(bloggerAccountExample);

        if(bloggerAccounts.isEmpty()){
            throw  new BadRequestException("用户名不正确");
        }

        Optional<String> token = Optional.ofNullable(null);


        for (BloggerAccount bloggerAccount:bloggerAccounts) {
            if(bloggerAccount.getPassword().equals(loginParams.getPassword())){

                  token = Optional.ofNullable(Optional.ofNullable(tokenService.generateToken(loginParams.getUsername())).orElseThrow(() -> new ServiceException("系统服务错误")));
                 tokenService.storage(token.get(),loginParams.getUsername());
            }else{
                throw new BadRequestException("密码不正确");
            }
        }


        return tokenService.creatAuthToken(token.get());

    }

    @Override
    public String getUsernameByToken(@NonNull String token) {

        return tokenService.getUsernameByToken(token);
    }

    @Override
    public @NonNull Integer findBloggerIdByUsername(@NonNull String username) {

        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();
        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<BloggerAccount> bloggerAccounts = bloggerAccountMapper.selectByExample(bloggerAccountExample);

        for (BloggerAccount bloggerAccount: bloggerAccounts) {
            return bloggerAccount.getId();
        }
        return new Integer(-1);
    }

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


        Integer userId = getUserIdByToken(token);
        BloggerProfileWithBLOBs bloggerProfileWithBLOBs = new BloggerProfileWithBLOBs();

        BloggerProfileExample bloggerProfileExample = new BloggerProfileExample();

        BloggerProfileExample.Criteria criteria = bloggerProfileExample.createCriteria();

        criteria.andBloggerIdEqualTo(userId);


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
        return tokenService.findUserDetailsByToken(token);

    }

    @Override
    public boolean updatePassword(@NonNull String oldPassword, @NonNull String newPassword, String username) {
        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();

        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(oldPassword);

        List<BloggerAccount> bloggerAccounts = bloggerAccountMapper.selectByExample(bloggerAccountExample);

        if(bloggerAccounts.isEmpty()){
            throw new BadRequestException("密码错误！");
        }


        for (BloggerAccount bloggerAccount:bloggerAccounts) {
            bloggerAccount.setPassword(newPassword);
            bloggerAccountMapper.updateByPrimaryKey(bloggerAccount);
            return true;
        }

        return false;
    }

    @Override
    public Integer getUserIdByToken(@NonNull String token) {

        String username = tokenService.getUsernameByToken(token);

        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();
        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<BloggerAccount> bloggerAccounts = bloggerAccountMapper.selectByExample(bloggerAccountExample);

        if(bloggerAccounts.isEmpty()){
            throw new BadRequestException("用户信息错误，请重试！");
        }

        for (BloggerAccount bloggerAccount:bloggerAccounts) {
            return bloggerAccount.getId();
        }

        return -1;
    }

    @Override
    public boolean logout(String token) {
        return tokenService.logout(token);
    }


}
