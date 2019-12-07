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
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.BlogDetail;
import run.app.entity.DTO.DataGrid;
import run.app.entity.enums.ArticleStatus;
import run.app.entity.enums.CiteNumEnum;
import run.app.entity.model.*;
import run.app.entity.VO.ArticleParams;
import run.app.entity.VO.PostQueryParams;
import run.app.exception.BadRequestException;
import run.app.exception.NotFoundException;
import run.app.mapper.*;
import run.app.service.TokenService;
import run.app.service.ArticleService;
import run.app.service.AttachmentService;
import run.app.service.TagService;
import run.app.util.AppUtil;

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
    TagService tagService;

    @Autowired
    BlogContentMapper blogContentMapper;


    @Autowired
    BloggerPictureMapper bloggerPictureMapper;


    @Override
    @Transactional
    public @NonNull boolean submitArticle(@NonNull ArticleParams articleParams,@NonNull String token) {

        Blog blog = new Blog();

        Long bloggerId;
//        if((bloggerId =userService.getUserIdByToken(token)) == -1){
        if((bloggerId =tokenService.getUserIdWithToken(token)) == -1){
            throw  new BadRequestException("用户信息错误！");
        }
        blog.setBloggerId(bloggerId);
        blog.setReleaseDate(new Date());
        blog.setNearestModifyDate(new Date());
        BeanUtils.copyProperties(articleParams,blog);

        /*生成文章id 10-9 - 19 WHOAMI*/
        Long blog_id = AppUtil.nextId();
        blog.setId(blog_id);

        /*增加代码结束*/

        /**
         * 功能描述: 增加文章缩略图
         * @Author: WHOAMI
         * @Date: 2019/9/4 20:17
         */

        if(!StringUtils.isBlank(articleParams.getPicture())){
            blog.setPictureId(attachmentService.getIdByTitle(articleParams.getPicture()));
//            相对应的应该让改图片引用人数+1
            attachmentService.changePictureStatus(blog.getPictureId(), CiteNumEnum.ADD);
        }

        /*增加代码结束*/

//        blog.setTagTitle(articleParams.getTag());

        blog.setStatus(ArticleStatus.PUBLISHED.getName());


        /**
         * 功能描述: 增加文章标签功能
         * @Author: WHOAMI
         */

        String tag = tagService.submitArticleWithTagString(articleParams.getTagList(), blog_id);

        blog.setTagTitle(tag);

        blogMapper.insertSelective(blog);

        /*增加代码结束*/

        BlogContent blogContent = new BlogContent();

        BeanUtils.copyProperties(articleParams,blogContent);
        blogContent.setId(blog.getId());
        blogContentMapper.insert(blogContent);

        return true;
    }

    @Override
    @Transactional
    public boolean updateArticle(@NonNull ArticleParams articleParams, @NonNull Long blogId, @NonNull String token) {

        Blog blog1 = blogMapper.selectByPrimaryKey(blogId);

        tokenService.authentication(blog1.getBloggerId(),token);


        /**
        * 功能描述: 增加文章标签功能
        * @Author: WHOAMI
         */
        String nowTagsString = tagService.updateTagsWithId(blogId,articleParams.getTagList());

        /*增加代码结束*/

        /**
        * 功能描述: 增加文章缩略图
        * @Author: WHOAMI
        * @Date: 2019/9/4 20:17
         */

        Long picture_id = -1L;

        if(null != articleParams.getPicture()){
            picture_id = attachmentService.getIdByTitle(articleParams.getPicture());
            /**
            * 功能描述: 相应当前所引用的博客图片人数进行更新
            * @Author: WHOAMI
            * @Date: 2019/11/10 13:22
             */
            if(!StringUtils.isBlank(blog1.getPictureId().toString()) && picture_id != blog1.getPictureId()){
                attachmentService.changePictureStatus(picture_id, CiteNumEnum.ADD);
                attachmentService.changePictureStatus(blog1.getPictureId(), CiteNumEnum.REDUCE);
            }
        }

        /*增加代码结束*/

        Blog blog = new Blog();
        blog.setId(blogId);
        blog.setNearestModifyDate(new Date());

        BeanUtils.copyProperties(articleParams,blog);
        //todo tag问题
        blog.setTagTitle(nowTagsString);
//        blog.setTagTitle(articleParams.getTag());

        if(picture_id != -1){
            blog.setPictureId(picture_id);
        }
        blogMapper.updateByPrimaryKeySelective(blog);

        BlogContent blogContent = new BlogContent();

        blogContent.setId(blogId);

        BeanUtils.copyProperties(articleParams,blogContent);

        blogContentMapper.updateByPrimaryKey(blogContent);
        return true;
    }

    @Override
    public boolean updateArticleStatus(@NonNull Long blogId, @NonNull String status,String token) {

        Blog blog1 = blogMapper.selectByPrimaryKey(blogId);

        tokenService.authentication(blog1.getBloggerId(),token);

        Blog blog = new Blog();
        blog.setStatus(status);
        blog.setId(blogId);
        blogMapper.updateByPrimaryKeySelective(blog);
        return true;
    }

    @Override
    public BaseResponse getArticleDetail(@NonNull Long blogId,String token) {

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());

        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        if(null == blog){ //查询没有相关博客
            throw new NotFoundException("没有相关博客信息");
        }
        tokenService.authentication(blog.getBloggerId(),token);

        BlogContent blogContent = blogContentMapper.selectByPrimaryKey(blogId);

        BlogDetail blogDetail = new BlogDetail();

        BeanUtils.copyProperties(blog,blogDetail);
        BeanUtils.copyProperties(blogContent,blogDetail);

//        todo tag标签问题

        if(!StringUtils.isBlank(blog.getTagTitle())) {
            blogDetail.setTagsTitle(tagService.selectTagTitleByIdString(blog.getTagTitle()));
        }

//        博客缩略图问题
        if(null != blog.getPictureId()){
            blogDetail.setPicture(attachmentService.selectPicById(blog.getPictureId()));
        }

        baseResponse.setData(blogDetail);
        baseResponse.setStatus(HttpStatus.OK.value());
        return baseResponse;


    }



    @Override
    public BaseResponse getArticleListByExample(@NonNull int pageNum, @NonNull int pageSize, PostQueryParams postQueryParams, @NonNull String token) {

        log.info("查询目标" + postQueryParams.toString());

        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogList = blogMapper.selectByUserExample(postQueryParams, tokenService.getUserIdWithToken(token));

        PageInfo<Blog> list = new PageInfo<>(blogList);

        DataGrid dataGrid = new DataGrid();

        List<run.app.entity.DTO.Blog> blogs = list.getList().stream().map(item->{


            List<String> tagsTitle = new ArrayList<>();
            if(!StringUtils.isBlank(item.getTagTitle())){

                tagsTitle = tagService.selectTagTitleByIdString(item.getTagTitle());
            }
            String pic = "";
//            获取博客图片名称
            if(null != item.getPictureId()){
                pic = attachmentService.selectPicById(item.getPictureId());
            }
            run.app.entity.DTO.Blog blog = new run.app.entity.DTO.Blog();
            BeanUtils.copyProperties(item,blog);
            blog.setPicture(pic);
            blog.setTagsTitle(tagsTitle);
            return  blog;
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
    public void deleteBlog(@NonNull Long blogId,String token) {

        Blog blog1 = blogMapper.selectByPrimaryKey(blogId);

        tokenService.authentication(blog1.getBloggerId(),token);

        /**
        * 功能描述:这里应该先查询此文章的所有标签，然后删除，顺序不可以变
        * @Author: WHOAMI
        * @Date: 2019/8/28 7:35
         */

//        todo:这里应该也减少tag标签
        Blog blog = blogMapper.selectByPrimaryKey(blogId);

        if(!StringUtils.isBlank(blog.getTagTitle())){
            tagService.deleteTagsKeyByBlogId(blogId);
            tagService.dealWithNumByIdString(blog.getTagTitle(),false);
        }
        blogMapper.deleteByPrimaryKey(blogId);
        blogContentMapper.deleteByPrimaryKey(blogId);

    }

    @Override
    public long getArticleCount(@NonNull String token) {
//        Integer bloggerId = userService.getUserIdByToken(token);

        Long bloggerId = tokenService.getUserIdWithToken(token);
        BlogExample blogExample = new BlogExample();

        BlogExample.Criteria criteria = blogExample.createCriteria();
        criteria.andBloggerIdEqualTo(bloggerId);
       return blogMapper.countByExample(blogExample);

    }

    @Override
    @Transactional
    public void deleteQuotePic(Long picId) {
        blogMapper.deletePicByPicId(picId);

    }


}
