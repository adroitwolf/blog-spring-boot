package run.app.service.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerAccountExample;
import run.app.entity.params.LoginParams;
import run.app.exception.BadRequestException;
import run.app.exception.ServiceException;
import run.app.mapper.BloggerAccountMapper;
import run.app.security.token.TokenService;
import run.app.service.AccountService;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/16 9:01
 * Description: ://TODO ${END}
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    BloggerAccountMapper bloggerAccountMapper;

    @Autowired
    TokenService tokenService;

    @Override
    public @NonNull Optional<String> loginService(@NonNull LoginParams loginParams) {

//        if(tokenService.islogined(loginParams.getUsername())){
////            throw new BadRequestException("用户已经在别处登陆！");
////        }

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

//                  token = Optional.ofNullable(Optional.ofNullable(tokenService.generateToken(loginParams.getUsername())).orElseThrow(() -> new ServiceException("系统服务错误")));
//                 tokenService.storage(token.get(),loginParams.getUsername());
                token  =Optional.ofNullable(Optional.ofNullable( tokenService.getToken(bloggerAccount))).orElseThrow(() -> new ServiceException("服务异常"));
            }else{
                throw new BadRequestException("密码不正确");
            }
        }

//        return tokenService.creatAuthToken(token.get());

        return token;
    }


    @Override
    public boolean updatePassword(@NonNull String oldPassword, @NonNull String newPassword, String token) {
        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();

        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();
        criteria.andIdEqualTo(tokenService.getUserIdWithToken(token));
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
    public String getUsernameByToken(@NonNull String token) {

        Integer id = tokenService.getUserIdWithToken(token);


        BloggerAccount bloggerAccount = bloggerAccountMapper.selectByPrimaryKey(id);

        return bloggerAccount.getUsername();
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


}
