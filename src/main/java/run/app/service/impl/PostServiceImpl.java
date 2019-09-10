package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.app.entity.DTO.BlogDetailWithAuthor;
import run.app.entity.DTO.DataGrid;
import run.app.entity.model.*;
import run.app.entity.params.PostQueryParams;
import run.app.mapper.*;
import run.app.service.AttachmentService;
import run.app.service.PostService;

import java.util.ArrayList;
import java.util.List;
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
    public DataGrid getList(int pageNum, int pageSize) {


        BlogExample blogExample = new BlogExample();

        blogExample.setOrderByClause("release_date desc");
        BlogExample.Criteria criteria = blogExample.createCriteria();
        criteria.andStatusEqualTo("PUBLISHED");


        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.selectByExample(blogExample);

        PageInfo<Blog> list = new PageInfo<>(blogs);

        List<Blog> result = list.getList();

        List<run.app.entity.DTO.Blog> resultX= result.stream().map(item->{

            List<String> tagsTitle = new ArrayList<>();
            if(!StringUtils.isBlank(item.getTagTitle())){

                tagsTitle = tagService.selectTagTitleByIdString(item.getTagTitle());
            }

            String pic = "";

            if(null != item.getPictureId()){
                pic = attachmentService.selectPicById(item.getBloggerId());
            }

            return new run.app.entity.DTO.Blog(item.getId(),
                    item.getTitle(),
                    item.getSummary(),
                    item.getReleaseDate(),
                    tagsTitle,
                    pic);}).collect(Collectors.toList());
        DataGrid dataGrid = new DataGrid();

        dataGrid.setRows(resultX);
        dataGrid.setTotal(list.getTotal());


        return dataGrid;
    }

    @Override
    public DataGrid getListByExample(int pageNum, int pageSize, String keyword) {

        PostQueryParams postQueryParams = new PostQueryParams();

        postQueryParams.setKeyword(keyword);
        postQueryParams.setStatus("PUBLISHED");
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogList = blogMapper.selectByUserExample(postQueryParams, null);

        PageInfo<Blog> list = new PageInfo<>(blogList);

        DataGrid dataGrid = new DataGrid();

        List<run.app.entity.DTO.Blog> blogs = list.getList().stream().map(item->{
            List<String> tagsTitle = new ArrayList<>();
            if(!StringUtils.isBlank(item.getTagTitle())){

                tagsTitle = tagService.selectTagTitleByIdString(item.getTagTitle());
            }

            String pic = "";

            if(null != item.getPictureId()){
                pic = attachmentService.selectPicById(item.getBloggerId());
            }

            return new run.app.entity.DTO.Blog(item.getId(),
                    item.getTitle(),
                    item.getSummary(),
                    item.getReleaseDate(),
                    tagsTitle,
                    pic);
        }).collect(Collectors.toList());


        dataGrid.setRows(blogs);

        dataGrid.setTotal(list.getTotal());

        return dataGrid;
    }

    @Override
    public BlogDetailWithAuthor getDetail(Long blogId) {

        Blog blog = blogMapper.selectByPrimaryKey(blogId);


        BlogContent blogContent = blogContentMapper.selectByPrimaryKey(blogId);

        BloggerProfileWithBLOBs bloggerProfileWithBLOBs = bloggerProfileMapper.selectByPrimaryKey(blog.getBloggerId());


        //todo: tags
        List<String> nowTagsId = new ArrayList<>();
        if(!StringUtils.isBlank(blog.getTagTitle())){

          nowTagsId = tagService.selectTagTitleByIdString(blog.getTagTitle());
        }

        String pic = "";

        if(null != blog.getPictureId()){
            pic = attachmentService.selectPicById(blog.getBloggerId());
        }


        BlogDetailWithAuthor blogDetailWithAuthor = new BlogDetailWithAuthor(blogId,blog.getTitle(),blog.getSummary(),blog.getReleaseDate(),nowTagsId,blogContent.getContent(),pic,bloggerProfileWithBLOBs.getIntro(),bloggerProfileWithBLOBs.getAvatarId());


        return blogDetailWithAuthor;
    }

    @Override
    public DataGrid getListByTag(int pageNum, int pageSize, String tag) {

        Long id = tagService.selectIdWithName(tag);

        log.debug("id:"+ id);

        DataGrid dataGrid = new DataGrid();




        if(!StringUtils.isBlank(id.toString())) {
            PageHelper.startPage(pageNum,pageSize);
            List<Long> list = tagService.selectBlogIdByTagId(id);
            PageInfo<Long> pageInfo = new PageInfo<>(list);
//          采取分页的方式 10-9 -19
//            List<Long> list1 = list.subList((pageNum - 1) * pageSize, list.size()>pageNum * pageSize?pageNum*pageSize:list.size());

            List<run.app.entity.DTO.Blog> blogs = new ArrayList<>();

            pageInfo.getList().stream().forEach(x -> {
                run.app.entity.DTO.Blog blogx = new run.app.entity.DTO.Blog();
                Blog blog = blogMapper.selectByPrimaryKey(x);
                blogx.setId(x);

                blogx.setSummary(blog.getSummary());
                blogx.setTitle(blog.getTitle());
                blogx.setReleaseDate(blog.getReleaseDate());

                if (!StringUtils.isBlank(blog.getTagTitle())) {
                    blogx.setTagsTitle(tagService.selectTagTitleByIdString(blog.getTagTitle()));
                }
                blogs.add(blogx);

            });


            dataGrid.setRows(blogs);
            dataGrid.setTotal(list.size());

            return dataGrid;
        }

        dataGrid.setTotal(0);

        return dataGrid;
    }
}
