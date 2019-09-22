package run.app.service.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerAccountExample;
import run.app.entity.model.BloggerProfileWithBLOBs;
import run.app.entity.VO.LoginParams;
import run.app.entity.VO.RegisterParams;
import run.app.exception.BadRequestException;
import run.app.exception.ServiceException;
import run.app.mapper.BloggerAccountMapper;
import run.app.mapper.BloggerProfileMapper;
import run.app.security.token.TokenService;
import run.app.service.AccountService;
import run.app.util.AppUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/16 9:01
 * Description: :账号服务
 */
@Service
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    public AccountServiceImpl() {
        this.appUtil = AppUtil.getInstance();

    }

    AppUtil appUtil;

    @Autowired
    BloggerAccountMapper bloggerAccountMapper;

    @Autowired
    BloggerProfileMapper bloggerProfileMapper;

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

        Long id = tokenService.getUserIdWithToken(token);


        BloggerAccount bloggerAccount = bloggerAccountMapper.selectByPrimaryKey(id);

        return bloggerAccount.getUsername();
    }

    @Override
    public @NonNull Long findBloggerIdByUsername(@NonNull String username) {

        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();
        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<BloggerAccount> bloggerAccounts = bloggerAccountMapper.selectByExample(bloggerAccountExample);

        for (BloggerAccount bloggerAccount: bloggerAccounts) {
            return bloggerAccount.getId();
        }
        return new Long(-1L);
    }

    @Override
    public BaseResponse registerUser(@NonNull RegisterParams registerParams) {
        BaseResponse baseResponse = new BaseResponse();

//        先查询是否账号是否可用

        if(findBloggerIdByUsername(registerParams.getAccount()) != -1){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("此账号已被人抢先注册了！");
            return baseResponse;
        }

        BloggerAccount bloggerAccount = new BloggerAccount();
        bloggerAccount.setId(appUtil.nextId());

        bloggerAccount.setUsername(registerParams.getAccount());
        bloggerAccount.setPassword(registerParams.getPassword());
        bloggerAccount.setRegisterDate(new Date());



        if(bloggerAccountMapper.insertSelective(bloggerAccount) == 0){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setData("服务异常，请稍后重试");
            return baseResponse;
        }

        BloggerProfileWithBLOBs bloggerProfileWithBLOBs = new BloggerProfileWithBLOBs();

        bloggerProfileWithBLOBs.setIntro(registerParams.getUsername());
        bloggerProfileWithBLOBs.setBloggerId(bloggerAccount.getId());

        bloggerProfileMapper.insertSelective(bloggerProfileWithBLOBs);


        baseResponse.setStatus(HttpStatus.OK.value());

        return baseResponse;
    }


}
