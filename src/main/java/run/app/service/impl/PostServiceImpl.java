package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.app.entity.DTO.BlogDetailWithAuthor;
import run.app.entity.DTO.DataGrid;
import run.app.entity.model.Blog;
import run.app.entity.model.BlogContent;
import run.app.entity.model.BlogExample;
import run.app.entity.model.BloggerProfileWithBLOBs;
import run.app.mapper.*;
import run.app.service.PostService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

            return new run.app.entity.DTO.Blog(item.getId(),
                    item.getTitle(),
                    item.getSummary(),
                    item.getReleaseDate(),
                    tagsTitle);}).collect(Collectors.toList());
        DataGrid dataGrid = new DataGrid();

        dataGrid.setRows(resultX);
        dataGrid.setTotal(list.getTotal());


        return dataGrid;
    }

    @Override
    public BlogDetailWithAuthor getDetail(Integer blogId) {

        Blog blog = blogMapper.selectByPrimaryKey(blogId);

        BlogContent blogContent = blogContentMapper.selectByPrimaryKey(blogId);

        BloggerProfileWithBLOBs bloggerProfileWithBLOBs = bloggerProfileMapper.selectByPrimaryKey(blog.getBloggerId());


        //todo: tags
        List<String> nowTagsId = new ArrayList<>();
        if(!StringUtils.isBlank(blog.getTagTitle())){

          nowTagsId = tagService.selectTagTitleByIdString(blog.getTagTitle());
        }


        BlogDetailWithAuthor blogDetailWithAuthor = new BlogDetailWithAuthor(blogId,blog.getTitle(),blog.getSummary(),blog.getReleaseDate(),nowTagsId,blogContent.getContent(),bloggerProfileWithBLOBs.getIntro(),bloggerProfileWithBLOBs.getAvatarId());


        return blogDetailWithAuthor;
    }
}
