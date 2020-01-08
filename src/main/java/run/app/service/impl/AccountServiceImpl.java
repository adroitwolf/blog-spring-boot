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
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.DataGrid;
import run.app.entity.DTO.User;
import run.app.entity.DTO.UserInfo;
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
import run.app.mapper.BloggerAccountMapper;
import run.app.mapper.BloggerProfileMapper;
import run.app.service.AttachmentService;
import run.app.service.TokenService;
import run.app.service.AccountService;
import run.app.service.RoleService;
import run.app.util.AppUtil;

import java.util.*;
import java.util.stream.Collector;
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
        Optional<String> token = Optional.ofNullable(null);

        try {
            user = Validator.isEmail(loginParams.getP()) ? loginWithEmail(loginParams.getP()) : loginWithUsername(loginParams.getP());
        }catch (NotFoundException e){
                throw new BadRequestException(PASSERROR);
        }

        if(user.getPassword().equals(loginParams.getPassword())){

//            这里应该判断账户是否被封禁
            log.info(user.toString());
            if(UserStatusEnum.YES.getName().equals(user.getisEnabled())){ //说明被封禁
                throw new BadRequestException(BLOCKED);
            }
            BeanUtils.copyProperties(user,userRs);
            userRs.setRoles(roleService.getRolesByUserId(userRs.getId())
                    .stream().map(n->n.getAuthority()).collect(Collectors.toList()));
                token  = Optional.ofNullable(Optional.ofNullable( tokenService.getToken(userRs))).orElseThrow(() -> new ServiceException("服务异常"));
        }else{
                throw new BadRequestException(PASSERROR);
        }
        BaseResponse baseResponse = new BaseResponse();
        HashMap<String, Object> map = new HashMap<>();
        map.put("token",token.get());
        map.put("user",userRs);
        baseResponse.setData(map);
        baseResponse.setMessage(LOGINSUCCESS);
        baseResponse.setStatus(HttpStatus.OK.value());
        return baseResponse;
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

        roleService.setRoleWithUserId(RoleEnum.USER,bloggerAccount.getId());

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

    /**
    * 功能描述: 封禁账户
    * @Param: [bloggerId, token]
    * @Return: run.app.entity.DTO.BaseResponse
    * @Author: WHOAMI
    * @Date: 2019/12/15 14:04
     */
    @Override
    public BaseResponse updateUserStatus(Long bloggerId,String status){

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
    public BaseResponse deleteUser(Long bloggerId) {
        if(bloggerAccountMapper.deleteByPrimaryKey(bloggerId) ==0){ //说明没有此账号
            throw new BadRequestException(NOTFOUND);
        }
        bloggerProfileMapper.deleteByPrimaryKey(bloggerId);

        return new BaseResponse(HttpStatus.OK.value(),"账删除成功",null);
    }

    @Override
    public BaseResponse selectUserByExample(int pageNum, int pageSize, QueryParams queryParams) {
        if(!StringUtils.isEmpty(queryParams.getStatus())){
            UserStatusEnum.valueOf(queryParams.getStatus());
        }

        PageHelper.startPage(pageNum,pageSize);
        List<UserInfo> accounts = bloggerAccountMapper.selectByQueryParams(queryParams);

        PageInfo<UserInfo> pageInfo = new PageInfo<>(accounts);

        DataGrid dataGrid = new DataGrid();

        List<UserInfo> lists = pageInfo.getList().stream().map(item->{

            UserInfo userInfo = new UserInfo();

            BeanUtils.copyProperties(item,userInfo);

            if(null != item.getAvatarId()){ //查询是否头像为空
                userInfo.setAvatar(attachmentService.getPathById(item.getAvatarId()));
            }

            return userInfo;
        }).collect(Collectors.toList());


        dataGrid.setRows(lists);

        dataGrid.setTotal(pageInfo.getTotal());



        return new BaseResponse(HttpStatus.OK.value(),null,dataGrid);
    }


}
