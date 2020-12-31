package run.app.service.impl;

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
import run.app.entity.enums.ArticleStatusEnum;
import run.app.entity.enums.CiteNumEnum;
import run.app.entity.enums.RoleEnum;
import run.app.entity.model.*;
import run.app.entity.VO.ArticleParams;
import run.app.entity.model.Blog;
import run.app.exception.BadRequestException;
import run.app.exception.NotFoundException;
import run.app.exception.UnAccessException;
import run.app.mapper.*;
import run.app.service.*;
import run.app.util.AppUtil;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/26 7:24
 * Description: :博客相关服务层实现类
 */
@Slf4j
@Service
public class ArtcileServiceImpl implements ArticleService {


    /**
     * 功能描述: 新增两个标签服务的mapper层
     *
     * @Author: WHOAMI
     * @Date: 2019/8/23 17:37
     */
    @Autowired
    BlogTagMapMapper blogTagMapMapper;

    @Autowired
    BlogLabelMapper blogLabelMapper;
    /*代码修改结束*/

    /*
     * 功能描述: 添加博客图片功能
     * @Author: WHOAMI
     * @Date: 2019/9/3 18:05
     */

    @Autowired
    AttachmentService attachmentService;

    /*代码修改结束*/
    @Autowired
    BlogMapper blogMapper;

    //    @Autowired
//    UserService userService;
    @Autowired
    TokenService tokenService;

    @Autowired
    RoleService roleService;

    @Autowired
    TagService tagService;

    @Autowired
    BlogContentMapper blogContentMapper;

    @Autowired
    AccountService accountService;


    @Autowired
    UserService userService;

    @Autowired
    BloggerPictureMapper bloggerPictureMapper;


    @Override
    @Transactional
    public @NonNull
    BaseResponse submitArticle(@NonNull ArticleParams articleParams, @NonNull String token) {

        Blog blog = new Blog();

        Long bloggerId;
//        if((bloggerId =userService.getUserIdByToken(token)) == -1){
        if ((bloggerId = tokenService.getUserIdWithToken(token)) == -1) {
            throw new BadRequestException("用户信息错误！");
        }
        blog.setBloggerId(bloggerId);
        blog.setReleaseDate(new Date());
        blog.setNearestModifyDate(new Date());
        BeanUtils.copyProperties(articleParams, blog);

        /*生成文章id 10-9 - 2019 WHOAMI*/
        Long blog_id = AppUtil.nextId();
        blog.setId(blog_id);

        /*增加代码结束*/

        /**
         * 功能描述: 增加文章缩略图
         * @Author: WHOAMI
         * @Date: 2019/9/4 20:17
         */

        if (null != articleParams.getPictureId()) {
            blog.setPictureId(articleParams.getPictureId());
//            相对应的应该让改图片引用人数+1
            attachmentService.changePictureStatus(blog.getPictureId(), CiteNumEnum.ADD);
        }

        /*增加代码结束*/

//        blog.setTagTitle(articleParams.getTag());
        /**
         * 功能描述: 这里增加文章逻辑，需要管理员审核通过后文章才会使用户可见，但是管理员的文章会直接发布
         * @Author: WHOAMI
         * @Date: 2020/1/10 20:29
         */
        List<RoleEnum> roles = roleService.getRolesByUserId(bloggerId);
        String status = roles.contains(RoleEnum.ADMIN) ? ArticleStatusEnum.PUBLISHED.getName() : ArticleStatusEnum.CHECK.getName();

        blog.setStatus(status);

        /**
         * 功能描述: 增加文章标签功能
         * @Author: WHOAMI
         */

        String tag = tagService.submitArticleWithTagString(articleParams.getTagList(), blog_id);

        blog.setTagTitle(tag);
        try {
            blogMapper.insertSelective(blog);
        } catch (Exception e) {
            log.info(e.getMessage());
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new BadRequestException("您已经发布过类似文章，请仔细查询");
            }
        }
        /*增加代码结束*/

        BlogContent blogContent = new BlogContent();

        BeanUtils.copyProperties(articleParams, blogContent);
        blogContent.setId(blog.getId());

        blogContentMapper.insert(blogContent);


        return new BaseResponse(HttpStatus.OK.value(), "上传成功", null);
    }

    @Override
    @Transactional
    public BaseResponse updateArticle(@NonNull ArticleParams articleParams, @NonNull Long blogId, @NonNull String token) {

        Blog blog1 = blogMapper.selectByPrimaryKey(blogId);

        tokenService.authentication(blog1.getBloggerId(), token);


        /**
         * 功能描述: 增加文章标签功能
         * @Author: WHOAMI
         */
        String nowTagsString = tagService.updateTagsWithId(blogId, articleParams.getTagList());

        /*增加代码结束*/

        /**
         * 功能描述: 增加文章缩略图
         * @Author: WHOAMI
         * @Date: 2019/9/4 20:17
         */

        Long picture_id = -1L;

        if (null != articleParams.getPictureId()) {
//            picture_id = attachmentService.getIdByTitle(articleParams.getPictureId());
            picture_id = articleParams.getPictureId();
            /**
             * 功能描述: 相应当前所引用的博客图片人数进行更新
             * @Author: WHOAMI
             * @Date: 2019/11/10 13:22
             */
            if (null == blog1.getPictureId()) {
                attachmentService.changePictureStatus(picture_id, CiteNumEnum.ADD);
            } else if (picture_id != blog1.getPictureId()) {
                attachmentService.changePictureStatus(picture_id, CiteNumEnum.ADD);
                attachmentService.changePictureStatus(blog1.getPictureId(), CiteNumEnum.REDUCE);
            }


        }

        /*增加代码结束*/

        Blog blog = new Blog();
        blog.setId(blogId);
        blog.setNearestModifyDate(new Date());
        BeanUtils.copyProperties(articleParams, blog);
        String status = tokenService.getRoles(token).contains(RoleEnum.ADMIN) ? ArticleStatusEnum.PUBLISHED.getName() : ArticleStatusEnum.CHECK.getName();
        blog.setStatus(status);
        //todo tag问题
        blog.setTagTitle(nowTagsString);
//        blog.setTagTitle(articleParams.getTag());

        if (picture_id != -1) {
            blog.setPictureId(picture_id);
        }
        try {
            blogMapper.updateByPrimaryKeySelective(blog);

        } catch (Exception e) {
            log.info(e.getMessage());
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new BadRequestException("您已经发布过类似文章，请仔细查询");
            }
        }

        BlogContent blogContent = new BlogContent();

        blogContent.setId(blogId);

        BeanUtils.copyProperties(articleParams, blogContent);

        blogContentMapper.updateByPrimaryKey(blogContent);

        return new BaseResponse(HttpStatus.OK.value(), "文章更新成功", null);
    }

    @Override
    public BaseResponse updateArticleStatus(@NonNull Long blogId, @NonNull String status, String token) {


        Blog blog1 = blogMapper.selectByPrimaryKey(blogId); //这里应该会有空指针异常 应该制止

        if (ArticleStatusEnum.NO.getName().equals(blog1.getStatus())) { //审核失败的文章只允许删除操作
            throw new UnAccessException("请不要尝试非法操作");
        }

        tokenService.authentication(blog1.getBloggerId(), token);
        Blog blog = new Blog();
        //检测是否有非法字符注入
//        String articleStatus =ArticleStatusEnum.valueOf(status).equals(ArticleStatusEnum.PUBLISHED) ?
//                (tokenService.getRoles(token).contains(RoleEnum.ADMIN)?ArticleStatusEnum.PUBLISHED.getName():ArticleStatusEnum.CHECK.getName()):ArticleStatusEnum.RECYCLE.getName();
        StringBuilder articleStatus = new StringBuilder();
        if (tokenService.getRoles(token).contains(RoleEnum.ADMIN)) {
            articleStatus.append(ArticleStatusEnum.valueOf(status));
        }

        blog.setStatus(articleStatus.toString());
        blog.setId(blogId);

        blogMapper.updateByPrimaryKeySelective(blog);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());
        Map<String, String> updateStatus = new HashMap<>();
        updateStatus.put("status", articleStatus.toString());
        baseResponse.setData(updateStatus);
        return baseResponse;
    }

    @Override
    public BaseResponse updateArticleStatusByAdmin(@NonNull Long blogId, @NonNull String status, String token) {
//        if(ArticleStatusEnum.PUBLISHED.getName().equals(status)){ //说明审核通过
//
//        }else if(ArticleStatusEnum.NO.getName().equals(status)){
//
//        }
        //这里有一个问题就是审核失败的文章怎么办？ 删还是不删
        // 2020-1-30补充
        return updateArticleStatus(blogId, status, token);
    }

    @Override
    public BaseResponse getArticleDetail(@NonNull Long blogId, String token) {

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());

        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        if (null == blog) { //查询没有相关博客
            throw new NotFoundException("没有相关博客信息");
        }
        tokenService.authentication(blog.getBloggerId(), token);

        BlogContent blogContent = blogContentMapper.selectByPrimaryKey(blogId);

        BlogDetail blogDetail = new BlogDetail();

        BeanUtils.copyProperties(blog, blogDetail);

        BeanUtils.copyProperties(blogContent, blogDetail);

//        todo tag标签问题

        if (!StringUtils.isBlank(blog.getTagTitle())) {
            blogDetail.setTagsTitle(tagService.selectTagTitleByIdString(blog.getTagTitle()));
        }

//        博客缩略图问题
        if (null != blog.getPictureId()) {
            blogDetail.setPicture(attachmentService.getPathById(blog.getPictureId()));
        }

        baseResponse.setData(blogDetail);
        baseResponse.setStatus(HttpStatus.OK.value());
        return baseResponse;


    }


    @Override
    public BaseResponse getArticleListByExample(run.app.entity.VO.PageInfo pageInfo, QueryParams postQueryParams, @NonNull String token) {

        if (!StringUtils.isEmpty(postQueryParams.getStatus())) {

            ArticleStatusEnum.valueOf(postQueryParams.getStatus());
        }
        log.info("查询目标" + postQueryParams.toString());

        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        List<Blog> blogList = blogMapper.selectByUserExample(postQueryParams, tokenService.getUserIdWithToken(token));

        PageInfo<Blog> list = new PageInfo<>(blogList);

        DataGrid dataGrid = new DataGrid();

        List<run.app.entity.DTO.Blog> blogs = list.getList().stream().map(item -> {


            List<String> tagsTitle = new ArrayList<>();
            if (!StringUtils.isBlank(item.getTagTitle())) {

                tagsTitle = tagService.selectTagTitleByIdString(item.getTagTitle());
            }
            String pic = "";
//            获取博客图片逻辑路径
            if (null != item.getPictureId()) {
                pic = attachmentService.getPathById(item.getPictureId());
            }
            run.app.entity.DTO.Blog blog = new run.app.entity.DTO.Blog();
            BeanUtils.copyProperties(item, blog);
            blog.setPicture(pic);
            blog.setTagsTitle(tagsTitle);


            return blog;
        }).collect(Collectors.toList());


        dataGrid.setRows(blogs);

        dataGrid.setTotal(list.getTotal());

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setData(dataGrid);


        return baseResponse;
    }

    @Override
    /**
     * 功能描述: 面向管理员的文章查询功能，其中默认是查询审核中的
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/1/11 10:58
     */
    public BaseResponse getArticleListToAdminByExample(run.app.entity.VO.PageInfo pageInfo, QueryParams postQueryParams, @NonNull String token) {
        if (!StringUtils.isEmpty(postQueryParams.getStatus())) {

            ArticleStatusEnum.valueOf(postQueryParams.getStatus());

        } else {
            postQueryParams.setStatus(ArticleStatusEnum.CHECK.getName());
        }

        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());

        List<Blog> blogList = blogMapper.selectByUserExample(postQueryParams, null);

        PageInfo<Blog> list = new PageInfo<>(blogList);

        DataGrid dataGrid = new DataGrid();

        List<run.app.entity.DTO.BlogDetailWithAuthor> blogs = list.getList().stream().map(item -> {


            List<String> tagsTitle = new ArrayList<>();

            if (!StringUtils.isBlank(item.getTagTitle())) {

                tagsTitle = tagService.selectTagTitleByIdString(item.getTagTitle());
            }

            String pic = "";
//            获取博客图片逻辑路径
            if (null != item.getPictureId()) {
                pic = attachmentService.getPathById(item.getPictureId());
            }

            run.app.entity.DTO.BlogDetailWithAuthor blog = new run.app.entity.DTO.BlogDetailWithAuthor();

            BeanUtils.copyProperties(item, blog);

            blog.setPicture(pic);

            blog.setTagsTitle(tagsTitle);

//            获取博客作者相关信息
            UserDTO author = userService.getUserDTOById(item.getBloggerId());

            blog.setAuthor(author);

            return blog;
        }).collect(Collectors.toList());


        dataGrid.setRows(blogs);

        dataGrid.setTotal(list.getTotal());

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setData(dataGrid);


        return baseResponse;
    }

    @Override
    public String getArticleNameByBlogId(Long blogId) {

        return blogMapper.selectBlogNameByPrimaryKey(blogId);
    }


    @Override
    @Transactional
    public BaseResponse deleteBlog(@NonNull Long blogId, String token) {

        Blog blog1 = blogMapper.selectByPrimaryKey(blogId);

        tokenService.authentication(blog1.getBloggerId(), token);

        /**
         * 功能描述:这里应该先查询此文章的所有标签，然后删除，顺序不可以变
         * @Author: WHOAMI
         * @Date: 2019/8/28 7:35
         */

//        todo:这里应该也减少tag标签
        Blog blog = blogMapper.selectByPrimaryKey(blogId);

        if (!StringUtils.isBlank(blog.getTagTitle())) {
            tagService.deleteTagsKeyByBlogId(blogId);
            tagService.dealWithNumByIdString(blog.getTagTitle(), false);
        }
        blogMapper.deleteByPrimaryKey(blogId);
        blogContentMapper.deleteByPrimaryKey(blogId);

        return new BaseResponse(HttpStatus.OK.value(), "删除成功", null);
    }

    @Override
    public BaseResponse getArticleCount(@NonNull String token) {
//        Integer bloggerId = userService.getUserIdByToken(token);
        BaseResponse baseResponse = new BaseResponse();

        Long bloggerId = tokenService.getUserIdWithToken(token);
        BlogExample blogExample = new BlogExample();

        BlogExample.Criteria criteria = blogExample.createCriteria();
        criteria.andBloggerIdEqualTo(bloggerId);
        long count = blogMapper.countByExample(blogExample);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setData(count);
        return baseResponse;

    }

    @Override
    @Transactional
    public BaseResponse deletePostsByUserId(Long userId) { //逻辑太多 需要放到一个单独的服务里面运行

        return new BaseResponse();

    }

    @Override
    @Transactional
    public void deleteQuotePic(Long picId) {
        blogMapper.deletePicByPicId(picId);
    }

    @Override
    public Blog getBlogByBlogId(Long id) {
        return blogMapper.selectByPrimaryKey(id);
    }

    @Override
    public BaseBlog getBaseBlogById(Long id) {
        Blog blog = blogMapper.selectByPrimaryKey(id);
        BaseBlog baseBlog = new BaseBlog();

        BeanUtils.copyProperties(blog, baseBlog);

        if (null != blog.getPictureId()) {

            baseBlog.setPicture(attachmentService.getPathById(blog.getPictureId()));
        }

        return baseBlog;
    }


}
