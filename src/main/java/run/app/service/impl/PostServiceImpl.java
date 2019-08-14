package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.app.entity.DTO.BlogDetailWithAuthor;
import run.app.entity.DTO.DataGrid;
import run.app.entity.model.Blog;
import run.app.entity.model.BlogContent;
import run.app.entity.model.BlogExample;
import run.app.entity.model.BloggerProfileWithBLOBs;
import run.app.mapper.BlogContentMapper;
import run.app.mapper.BlogMapper;
import run.app.mapper.BloggerProfileMapper;
import run.app.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/14 19:51
 * Description: ://TODO ${END}
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    BlogContentMapper blogContentMapper;


    @Autowired
    BloggerProfileMapper bloggerProfileMapper;

    @Override
    public DataGrid getList(int pageNum, int pageSize) {


        BlogExample blogExample = new BlogExample();

        blogExample.setOrderByClause("release_date");
        BlogExample.Criteria criteria = blogExample.createCriteria();
        criteria.andStatusEqualTo("PUBLISHED");


        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.selectByExample(blogExample);

        PageInfo<Blog> list = new PageInfo<>(blogs);

        List<Blog> result = list.getList();

        result = result.stream().map(item->{return new Blog(item.getId()
                ,item.getStatus(),
                item.getTitle(),
                item.getSummary(),
                item.getReleaseDate(),
                item.getNearestModifyDate(),
                item.getTagTitle());}).collect(Collectors.toList());


        DataGrid dataGrid = new DataGrid();

        dataGrid.setRows(result);
        dataGrid.setTotal(list.getTotal());


        return dataGrid;
    }

    @Override
    public BlogDetailWithAuthor getDetail(Integer blogId) {

        Blog blog = blogMapper.selectByPrimaryKey(blogId);

        BlogContent blogContent = blogContentMapper.selectByPrimaryKey(blogId);

        BloggerProfileWithBLOBs bloggerProfileWithBLOBs = bloggerProfileMapper.selectByPrimaryKey(blog.getBloggerId());


        BlogDetailWithAuthor blogDetailWithAuthor = new BlogDetailWithAuthor(blogId,blog.getTitle(),blog.getReleaseDate(),blog.getTagTitle(),blogContent.getContent(),bloggerProfileWithBLOBs.getIntro(),bloggerProfileWithBLOBs.getAvatarId());


        return blogDetailWithAuthor;
    }
}
