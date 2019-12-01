package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.BlogDetailWithAuthor;
import run.app.entity.DTO.DataGrid;
import run.app.entity.DTO.PopularBlog;
import run.app.entity.enums.ArticleStatus;
import run.app.entity.model.*;
import run.app.entity.VO.PostQueryParams;
import run.app.mapper.*;
import run.app.service.AttachmentService;
import run.app.service.BlogStatusService;
import run.app.service.PostService;
import run.app.service.RedisService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/14 19:51
 * Description: 前台服务
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {


    /**
     * 功能描述: 新增两个标签服务的mapper层
     * @Author: WHOAMI
     * @Date: 2019/8/23 17:37
     */

    @Autowired
    TagServiceImpl tagService;
    /*代码修改结束*/


    @Autowired
    RedisService redisService;

    @Autowired
    BlogStatusService blogStatusService;

    /*
    * 功能描述: 新增博客图片功能
    * @Author: WHOAMI
    * @Date: 2019/9/3 18:08
     */
    @Autowired
    AttachmentService attachmentService;

    /*代码修改结束*/

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    BlogContentMapper blogContentMapper;


    @Autowired
    BloggerProfileMapper bloggerProfileMapper;

    @Override
    public BaseResponse getList(int pageNum, int pageSize) {


        BlogExample blogExample = new BlogExample();

        blogExample.setOrderByClause("release_date desc");
        BlogExample.Criteria criteria = blogExample.createCriteria();
        criteria.andStatusEqualTo("PUBLISHED");


        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.selectByExample(blogExample);

        PageInfo<Blog> list = new PageInfo<>(blogs);

        List<run.app.entity.DTO.Blog> resultX= transDtoFrmModel(list);
        DataGrid dataGrid = new DataGrid();

        dataGrid.setRows(resultX);
        dataGrid.setTotal(list.getTotal());




        return  new BaseResponse(HttpStatus.OK.value(),"",dataGrid);
    }

    @Override
    public BaseResponse getListByExample(int pageNum, int pageSize, String keyword) {

        PostQueryParams postQueryParams = new PostQueryParams();

        postQueryParams.setKeyword(keyword);
        postQueryParams.setStatus("PUBLISHED");
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogList = blogMapper.selectByUserExample(postQueryParams, null);

        PageInfo<Blog> list = new PageInfo<>(blogList);

        DataGrid dataGrid = new DataGrid();

        List<run.app.entity.DTO.Blog> blogs = transDtoFrmModel(list);


        dataGrid.setRows(blogs);

        dataGrid.setTotal(list.getTotal());

        return new BaseResponse(HttpStatus.OK.value(),"",dataGrid);
    }

    @Override
    public BlogDetailWithAuthor getDetail(Long blogId) {

        Blog blog = blogMapper.selectByPrimaryKey(blogId);


        BlogContent blogContent = blogContentMapper.selectByPrimaryKey(blogId);

        BloggerProfile bloggerProfile = bloggerProfileMapper.selectByPrimaryKey(blog.getBloggerId());


        //todo: tags
        List<String> nowTags = new ArrayList<>();
        if(!StringUtils.isBlank(blog.getTagTitle())){

          nowTags = tagService.selectTagTitleByIdString(blog.getTagTitle());
        }

        String pic = "";

        if(null != blog.getPictureId()){
            pic = attachmentService.selectPicById(blog.getPictureId());
        }

        //todo 属性太长 需要重构
        BlogDetailWithAuthor blogDetailWithAuthor = new BlogDetailWithAuthor(blogId,blog.getTitle(),blog.getSummary(),blog.getReleaseDate(),nowTags,blogContent.getContent(),pic,bloggerProfile.getNickname(),attachmentService.getPathById(bloggerProfile.getAvatarId()));
        return blogDetailWithAuthor;
    }

    @Override
    public BaseResponse getListByTag(int pageNum, int pageSize, String tag) {

        Long id = tagService.selectIdWithName(tag);


        DataGrid dataGrid = new DataGrid();


        if(null != id) {
            List<Long> list = tagService.selectBlogIdByTagId(pageSize,pageNum,id);
            log.debug("tag集合"+list.size());
            PageInfo<Long> longPageInfo = new PageInfo<>(list);
//          采取分页的方式 10-9 -19
//            List<Long> list1 = list.subList((pageNum - 1) * pageSize, list.size()>pageNum * pageSize?pageNum*pageSize:list.size());

            List<run.app.entity.DTO.Blog> blogs = new ArrayList<>();

            list.stream().forEach(x -> {
                run.app.entity.DTO.Blog blogx = new run.app.entity.DTO.Blog();
                Blog blog = blogMapper.selectByPrimaryKey(x);
                BeanUtils.copyProperties(blog,blogx);

                if (!StringUtils.isBlank(blog.getTagTitle())) {
                    blogx.setTagsTitle(tagService.selectTagTitleByIdString(blog.getTagTitle()));
                }

                if(null != blog.getPictureId()){
                    blogx.setPicture(attachmentService.selectPicById(blog.getPictureId()));
                }

                blogs.add(blogx);

            });


            dataGrid.setRows(blogs);
            dataGrid.setTotal(longPageInfo.getTotal());
            return new BaseResponse(HttpStatus.OK.value(),"",dataGrid);
        }

        dataGrid.setTotal(0);

        return new BaseResponse(HttpStatus.OK.value(),"",dataGrid);
    }

    @Override
    public BaseResponse getTopPosts() {
        Set<PopularBlog> popularBlogs = redisService.listTop5FrmRedis();
        if (null == popularBlogs || popularBlogs.size()<5){ //说明redis不准确,需要查询数据库
            popularBlogs = (Set<PopularBlog>) blogStatusService.listTop5Posts();
        }

        return new BaseResponse(HttpStatus.OK.value(),null,popularBlogs);
    }



    private List<run.app.entity.DTO.Blog> transDtoFrmModel(PageInfo<Blog> list){
        return list.getList().stream().map(item->{
            List<String> tagsTitle = new ArrayList<>();
            if(!StringUtils.isBlank(item.getTagTitle())){

                tagsTitle = tagService.selectTagTitleByIdString(item.getTagTitle());
            }

            String pic = "";

            if(null != item.getPictureId()){
                pic = attachmentService.selectPicById(item.getPictureId());
            }

            run.app.entity.DTO.Blog blog = new run.app.entity.DTO.Blog();

            BeanUtils.copyProperties(item,blog);
            blog.setTagsTitle(tagsTitle);
            blog.setPicture(pic);
            return blog;
        }).collect(Collectors.toList());
    }
}
