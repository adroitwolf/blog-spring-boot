package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.app.entity.DTO.BlogDetail;
import run.app.entity.DTO.DataGrid;
import run.app.entity.model.*;
import run.app.entity.VO.ArticleParams;
import run.app.entity.VO.PostQueryParams;
import run.app.exception.BadRequestException;
import run.app.mapper.BlogContentMapper;
import run.app.mapper.BlogLabelMapper;
import run.app.mapper.BlogMapper;
import run.app.mapper.BlogTagMapMapper;
import run.app.security.token.TokenService;
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
        blog.setSummary(articleParams.getSummary());
        blog.setTitle(articleParams.getTitle());

        /*生成文章id 10-9 - 19 WHOAMI*/

        AppUtil instance = AppUtil.getInstance();

        long blog_id = instance.nextId();

        blog.setId(blog_id);

        /*增加代码结束*/

        /**
         * 功能描述: 增加文章缩略图
         * @Author: WHOAMI
         * @Date: 2019/9/4 20:17
         */


        if(!StringUtils.isBlank(articleParams.getPicture())){
            blog.setPictureId(attachmentService.getIdByTitle(articleParams.getPicture()));
        }

        /*增加代码结束*/

//        blog.setTagTitle(articleParams.getTag());

        blog.setStatus("PUBLISHED");


        /**
         * 功能描述: 增加文章标签功能
         * @Author: WHOAMI
         */

        String tag = tagService.submitArticleWithTagString(articleParams.getTagList(), blog_id);

        blog.setTagTitle(tag);

        blogMapper.insertSelective(blog);

        /*增加代码结束*/

        BlogContent blogContent = new BlogContent();

        blogContent.setId(blog.getId());
        blogContent.setContent(articleParams.getHtmlContent());
        blogContent.setContentMd(articleParams.getContent());

        blogContentMapper.insert(blogContent);

        return true;
    }

    @Override
    @Transactional
    public boolean updateArticle(@NonNull ArticleParams articleParams, @NonNull Long blogId, @NonNull String token) {


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
        }

        /*增加代码结束*/

        Blog blog = new Blog();
//        if((userService.getUserIdByToken(token)) == -1){
        if((tokenService.getUserIdWithToken(token)) == -1){
            throw  new BadRequestException("用户信息错误！");
        }
        blog.setId(blogId);
        blog.setNearestModifyDate(new Date());
        blog.setSummary(articleParams.getSummary());
        blog.setTitle(articleParams.getTitle());
        //todo tag问题
        blog.setTagTitle(nowTagsString);
//        blog.setTagTitle(articleParams.getTag());

        if(picture_id != -1){
            blog.setPictureId(picture_id);
        }

        blogMapper.updateByPrimaryKeySelective(blog);

        BlogContent blogContent = new BlogContent();

        blogContent.setId(blogId);
        blogContent.setContentMd(articleParams.getContent());
        blogContent.setContent(articleParams.getHtmlContent());
        blogContentMapper.updateByPrimaryKeyWithBLOBs(blogContent);
        return true;
    }

    @Override
    public boolean updateArticleStatus(@NonNull Long blogId, @NonNull String status) {

        Blog blog = new Blog();
        blog.setStatus(status);
        blog.setId(blogId);
        blogMapper.updateByPrimaryKeySelective(blog);
        return true;
    }

    @Override
    public BlogDetail getArticleDetail(@NonNull Long blogId) {

        Blog blog = blogMapper.selectByPrimaryKey(blogId);


        BlogContent blogContent = blogContentMapper.selectByPrimaryKey(blogId);

        BlogDetail blogDetail = new BlogDetail();

        blogDetail.setId(blog.getId());
        blogDetail.setContent(blogContent.getContent());
        blogDetail.setContentMd(blogContent.getContentMd());
        blogDetail.setNearestModifyDate(blog.getNearestModifyDate());
        blogDetail.setReleaseDate(blog.getReleaseDate());
        blogDetail.setStatus(blog.getStatus());
        blogDetail.setTitle(blog.getTitle());
//        todo tag标签问题



        if(!StringUtils.isBlank(blog.getTagTitle())) {
            blogDetail.setTagsTitle(tagService.selectTagTitleByIdString(blog.getTagTitle()));
        }

//        博客缩略图问题
        if(null != blog.getPictureId()){
            blogDetail.setPicture(attachmentService.selectPicById(blog.getPictureId()));
        }
        blogDetail.setSummary(blog.getSummary());

        return blogDetail;
    }



    @Override
    public DataGrid getArticleListByExample(@NonNull int pageNum, @NonNull int pageSize, PostQueryParams postQueryParams, @NonNull String token) {
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
            return new run.app.entity.DTO.Blog(item.getId()
                    ,item.getStatus(),
                    item.getTitle(),
                    item.getSummary(),
                    item.getReleaseDate(),
                    item.getNearestModifyDate(),
                    tagsTitle,
                    pic);
        }).collect(Collectors.toList());


        dataGrid.setRows(blogs);

        dataGrid.setTotal(list.getTotal());

        return dataGrid;
    }


    @Override
    @Transactional
    public void deleteBlog(@NonNull Long blogId) {


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
}
