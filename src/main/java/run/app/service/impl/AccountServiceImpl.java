package run.app.service.impl;

import cn.hutool.core.lang.Validator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.app.entity.DTO.*;
import run.app.entity.VO.QueryParams;
import run.app.entity.enums.RoleEnum;
import run.app.entity.enums.UserStatusEnum;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerAccountExample;
import run.app.entity.VO.LoginParams;
import run.app.entity.VO.RegisterParams;
import run.app.entity.model.BloggerProfile;
import run.app.exception.BadRequestException;
import run.app.exception.NotFoundException;
import run.app.exception.ServiceException;
import run.app.exception.UnAccessException;
import run.app.mapper.BloggerAccountMapper;
import run.app.mapper.BloggerProfileMapper;
import run.app.service.AttachmentService;
import run.app.service.TokenService;
import run.app.service.AccountService;
import run.app.service.RoleService;
import run.app.util.AppUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/16 9:01
 * Description: :账号服务
 */
@Service
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService{


    @Autowired
    BloggerAccountMapper bloggerAccountMapper;

    @Autowired
    BloggerProfileMapper bloggerProfileMapper;


    @Autowired
    AttachmentService attachmentService;

    @Autowired
    RoleService roleService;

    @Autowired
    TokenService tokenService;


    private final static  String PASSERROR = "用户名或密码不正确";

    private final static  String LOGINSUCCESS ="用户登陆成功";

    private final static String BLOCKED = "账号被封禁";

    private final static String NOTFOUND= "账号未找到";

    @Override
    public @NonNull BaseResponse loginService(@NonNull LoginParams loginParams) {

//        if(tokenService.islogined(loginParams.getUsername())){
////            throw new BadRequestException("用户已经在别处登陆！");
////        }
        //判断用户是用邮箱还是账号登陆的
        final BloggerAccount user;

        User userRs = new User();

//        Optional<String> token = Optional.ofNullable(null);

        try {
            user = Validator.isEmail(loginParams.getP()) ? loginWithEmail(loginParams.getP()) : loginWithUsername(loginParams.getP());
        }catch (NotFoundException e){
                throw new BadRequestException(PASSERROR);
        }

        if(user.getPassword().equals(loginParams.getPassword())){

//            这里应该判断账户是否被封禁
            log.info(user.toString());
            if(!UserStatusEnum.YES.getName().equals(user.getisEnabled())){ //说明被封禁
                throw new BadRequestException(BLOCKED);
            }
            userRs = convertBloggerAccount2User(user);


        }else{
                throw new BadRequestException(PASSERROR);
        }

        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setData(tokenService.buildAutoToken(userRs));

//        HashMap<String, Object> map = new HashMap<>();
//        map.put("token",token.get());
//        map.put("user",userRs);
//        baseResponse.setData(map);
//        baseResponse.setMessage(LOGINSUCCESS);
        baseResponse.setStatus(HttpStatus.OK.value());
        return baseResponse;
    }

    @Override
    public BaseResponse refresh(String refresh) {
        Long userId = tokenService.getUserIdByRefreshToken(refresh);

        BloggerAccount bloggerAccount = bloggerAccountMapper.selectByPrimaryKey(userId);

        User user = convertBloggerAccount2User(bloggerAccount);

        AutoToken autoToken = tokenService.buildAutoToken(user);

        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setData(autoToken);

        baseResponse.setStatus(HttpStatus.OK.value());

        return baseResponse;
    }


    @Override
    public BaseResponse updatePassword(@NonNull String oldPassword, @NonNull String newPassword, String token) {
        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();

        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();

        BaseResponse baseResponse = new BaseResponse();

        criteria.andIdEqualTo(tokenService.getUserIdWithToken(token));

        criteria.andPasswordEqualTo(oldPassword);

        List<BloggerAccount> bloggerAccounts = bloggerAccountMapper.selectByExample(bloggerAccountExample);

        if(bloggerAccounts.isEmpty()){
            throw new BadRequestException("密码错误！");
        }


        for (BloggerAccount bloggerAccount:bloggerAccounts) {
            bloggerAccount.setPassword(newPassword);
            bloggerAccountMapper.updateByPrimaryKey(bloggerAccount);
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return baseResponse;
        }

        baseResponse.setStatus(HttpStatus.OK.value());
        return baseResponse;
    }


    @Override
    public String getUsernameByToken(@NonNull String token) {

        Long id = tokenService.getUserIdWithToken(token);

        return getUsernameById(id);
    }

    @Override
    public String getUsernameById(Long userId) {
        BloggerAccount bloggerAccount = bloggerAccountMapper.selectByPrimaryKey(userId);
        /**
         * 问题描述: 开发环境与生产环境的jwt生成算法必须不一致
         * @Author: WHOAMI
         * @Date: 2019/12/2 23:11
         */
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

        BeanUtils.copyProperties(registerParams,bloggerAccount);

        bloggerAccount.setId(AppUtil.nextId());
        bloggerAccount.setUsername(registerParams.getAccount());
        //todo 设置发送邮件
        bloggerAccount.setRegisterDate(new Date());

        bloggerAccount.setisEnabled(UserStatusEnum.YES.getName());

        log.debug(bloggerAccount.toString());



        if( bloggerAccountMapper.insertSelective(bloggerAccount) == 0){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setData("服务异常，请稍后重试");
            return baseResponse;
        }

        BloggerProfile bloggerProfile = new BloggerProfile();

        //初始用户名就是你的账号名
        bloggerProfile.setNickname(registerParams.getAccount());
        bloggerProfile.setBloggerId(bloggerAccount.getId());

        bloggerProfileMapper.insertSelective(bloggerProfile);
        baseResponse.setStatus(HttpStatus.OK.value());


        //设置用户角色
        //默认都是User用户

        List<RoleEnum> roles = new ArrayList<>();
        roles.add(RoleEnum.USER);

        roleService.setRolesWithUserId(roles,bloggerAccount.getId());

        return baseResponse;
    }

    public BloggerAccount loginWithEmail(String email) {

        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();

        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<BloggerAccount> bloggerAccounts = bloggerAccountMapper.selectByExample(bloggerAccountExample);

        Optional<BloggerAccount> user = bloggerAccounts.stream().filter(Objects::nonNull).findFirst();

        return user.orElseThrow(() -> new NotFoundException("用户邮箱不存在"));
    }

    public BloggerAccount loginWithUsername(String username) {

        BloggerAccountExample bloggerAccountExample = new BloggerAccountExample();
        BloggerAccountExample.Criteria criteria = bloggerAccountExample.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<BloggerAccount> bloggerAccounts = bloggerAccountMapper.selectByExample(bloggerAccountExample);

        Optional<BloggerAccount> user = bloggerAccounts.stream().filter(Objects::nonNull).findFirst();

        return user.orElseThrow(() -> new NotFoundException("用户账号不存在"));
    }


    @Override
    public BaseResponse updateUserStatus(Long bloggerId,String status,String token){
//      不允许封禁自己的账户
        if (bloggerId.equals(tokenService.getUserIdWithToken(token))){
            throw new UnAccessException("不允许对自身账号进行任何操作");
        }

//        应该考虑用户不存在的情况
        BloggerAccount bloggerAccount = new BloggerAccount();
        bloggerAccount.setId(bloggerId);
        bloggerAccount.setisEnabled(UserStatusEnum.valueOf(status).getName()); //这里防止了非法字符注入
        if(bloggerAccountMapper.updateByPrimaryKeySelective(bloggerAccount) ==0){
            throw new BadRequestException(NOTFOUND);
        }
        return new BaseResponse(HttpStatus.OK.value(),"账号状态更改成功",null);
    }

    @Override
    public BaseResponse deleteUser(Long bloggerId,String token) { //删除用户需要权限
        //todo 需要在用户验证 验证码之后的操作
//       1.不能删除自己 2.同水平之间不能删除

        log.info(String.valueOf(bloggerId));

        log.info("经过token解释过的id:"+tokenService.getUserIdWithToken(token));

        if (bloggerId.equals(tokenService.getUserIdWithToken(token))){

            throw new UnAccessException("不允许删除自己");
        }
//
//        if(bloggerAccountMapper.deleteByPrimaryKey(bloggerId) ==0){ //说明没有此账号
//            throw new BadRequestException(NOTFOUND);
//        }
//        bloggerProfileMapper.deleteByPrimaryKey(bloggerId); //删除个人配置文件
//
//
//        roleService.deleteUserById(bloggerId);


        //todo:一些繁琐文件应该放到另一个不浪费时间的时候运行


        //这里并没有删除该用户发布的所有文章
        return new BaseResponse(HttpStatus.OK.value(),"账删除成功",null);
    }

    @Override
    public BaseResponse selectUserByExample(run.app.entity.VO.PageInfo pageInfo, QueryParams queryParams) {
        if(!StringUtils.isEmpty(queryParams.getStatus())){
            UserStatusEnum.valueOf(queryParams.getStatus());
        }

        PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
        List<UserInfo> accounts = bloggerAccountMapper.selectByQueryParams(queryParams);

        PageInfo<UserInfo> pageInfoObject = new PageInfo<>(accounts);

        DataGrid dataGrid = new DataGrid();

        List<UserInfo> lists = pageInfoObject.getList().stream().map(item->{

            UserInfo userInfo = new UserInfo();

            BeanUtils.copyProperties(item,userInfo);

            if(null != item.getAvatarId()){ //查询是否头像为空

                userInfo.setAvatar(attachmentService.getPathById(item.getAvatarId()));
            }

            //查询当前用户角色
            userInfo.setRoles(roleService.getRolesByUserId(userInfo.getId()).stream().map(n->n.getAuthority()).collect(Collectors.toList()));

            return userInfo;
        }).collect(Collectors.toList());


        dataGrid.setRows(lists);

        dataGrid.setTotal(pageInfoObject.getTotal());



        return new BaseResponse(HttpStatus.OK.value(),null,dataGrid);
    }

    @Override
    public BaseResponse logout(String token) {
        return new BaseResponse();
    }

    @Override
    public User convertBloggerAccount2User(BloggerAccount user) {
        User userRs = new User();

        BeanUtils.copyProperties(user,userRs);



        userRs.setRoles(roleService.getRolesByUserId(userRs.getId())
                .stream().map(n->n.getAuthority()).collect(Collectors.toList()));


        return userRs;
    }


}
