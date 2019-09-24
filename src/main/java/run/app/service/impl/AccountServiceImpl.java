package run.app.service.impl;

import cn.hutool.core.lang.Validator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerAccountExample;
import run.app.entity.VO.LoginParams;
import run.app.entity.VO.RegisterParams;
import run.app.entity.model.BloggerProfile;
import run.app.exception.BadRequestException;
import run.app.exception.NotFoundException;
import run.app.exception.ServiceException;
import run.app.mapper.BloggerAccountMapper;
import run.app.mapper.BloggerProfileMapper;
import run.app.security.token.TokenService;
import run.app.service.AccountService;
import run.app.util.AppUtil;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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


    private final static  String NOTFOUND = "用户名或密码不正确";

    @Override
    public @NonNull Optional<String> loginService(@NonNull LoginParams loginParams) {

//        if(tokenService.islogined(loginParams.getUsername())){
////            throw new BadRequestException("用户已经在别处登陆！");
////        }
        //判断用户是用用户还是账号登陆的
        final BloggerAccount user;

        Optional<String> token = Optional.ofNullable(null);

        try {
            user = Validator.isEmail(loginParams.getP()) ? loginWithEmail(loginParams.getP()) : loginWithUsername(loginParams.getP());
        }catch (NotFoundException e){
                throw new BadRequestException(NOTFOUND);
        }

        if(user.getPassword().equals(loginParams.getPassword())){
//                  token = Optional.ofNullable(Optional.ofNullable(tokenService.generateToken(loginParams.getUsername())).orElseThrow(() -> new ServiceException("系统服务错误")));
//                 tokenService.storage(token.get(),loginParams.getUsername());
                token  = Optional.ofNullable(Optional.ofNullable( tokenService.getToken(user))).orElseThrow(() -> new ServiceException("服务异常"));
        }else{
                throw new BadRequestException("密码不正确");
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

        //todo 设置发送邮件
        bloggerAccount.setEmail(registerParams.getEmail());
        bloggerAccount.setPhone(registerParams.getPhone());
        bloggerAccount.setRegisterDate(new Date());

        log.debug(bloggerAccount.toString());

        if(bloggerAccountMapper.insertSelective(bloggerAccount) == 0){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setData("服务异常，请稍后重试");
            return baseResponse;
        }

        BloggerProfile bloggerProfile = new BloggerProfile();

        bloggerProfile.setNickname(registerParams.getUsername());
        bloggerProfile.setBloggerId(bloggerAccount.getId());

        bloggerProfileMapper.insertSelective(bloggerProfile);


        baseResponse.setStatus(HttpStatus.OK.value());

        return baseResponse;
    }

    @Override
    public BloggerAccount loginWithEmail(String email) {

        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();

        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<BloggerAccount> bloggerAccounts = bloggerAccountMapper.selectByExample(bloggerAccountExample);

        Optional<BloggerAccount> user = bloggerAccounts.stream().filter(Objects::nonNull).findFirst();

        return user.orElseThrow(() -> new NotFoundException("用户邮箱不存在"));
    }

    @Override
    public BloggerAccount loginWithUsername(String username) {

        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();
        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<BloggerAccount> bloggerAccounts = bloggerAccountMapper.selectByExample(bloggerAccountExample);

        Optional<BloggerAccount> user = bloggerAccounts.stream().filter(Objects::nonNull).findFirst();

        return user.orElseThrow(() -> new NotFoundException("用户账号不存在"));
    }


}
