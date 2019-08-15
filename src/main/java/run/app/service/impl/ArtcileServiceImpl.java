package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.app.entity.DTO.BlogDetail;
import run.app.entity.DTO.DataGrid;
import run.app.entity.model.Blog;
import run.app.entity.model.BlogContent;
import run.app.entity.model.BlogExample;
import run.app.entity.params.ArticleParams;
import run.app.exception.BadRequestException;
import run.app.exception.ServiceException;
import run.app.mapper.BlogContentMapper;
import run.app.mapper.BlogMapper;
import run.app.service.ArticleService;
import run.app.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/26 7:24
 * Description: ://TODO ${END}
 */
@Slf4j
@Service
public class ArtcileServiceImpl implements ArticleService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    UserService userService;

    @Autowired
    BlogContentMapper blogContentMapper;

    @Override
    @Transactional
    public @NonNull boolean submitArticle(@NonNull ArticleParams articleParams,@NonNull String token) {

        Blog blog = new Blog();
        Integer bloggerId;
        if((bloggerId =userService.getUserIdByToken(token)) == -1){
            throw  new BadRequestException("用户信息错误！");
        }
        blog.setBloggerId(bloggerId);
        blog.setReleaseDate(new Date());
        blog.setNearestModifyDate(new Date());
        blog.setSummary(articleParams.getSummary());
        blog.setTitle(articleParams.getTitle());
        blog.setTagTitle(articleParams.getTag());

        blog.setStatus("PUBLISHED");
        int id = blogMapper.insertSelective(blog);
        
        if(id == 0){
            throw new ServiceException("服务器出现错误，请重试");
        }

        BlogContent blogContent = new BlogContent();

        blogContent.setId(blog.getId());
        blogContent.setContent(articleParams.getHtmlContent());
        blogContent.setContentMd(articleParams.getContent());

        blogContentMapper.insert(blogContent);
        return true;
    }

    @Override
    @Transactional
    public boolean updateArticle(@NonNull ArticleParams articleParams, @NonNull Integer blogId, @NonNull String token) {

        Blog blog = new Blog();
        if((userService.getUserIdByToken(token)) == -1){
            throw  new BadRequestException("用户信息错误！");
        }
        blog.setId(blogId);
        blog.setNearestModifyDate(new Date());
        blog.setSummary(articleParams.getSummary());
        blog.setTitle(articleParams.getTitle());
        blog.setTagTitle(articleParams.getTag());


        blogMapper.updateByPrimaryKeySelective(blog);

        BlogContent blogContent = new BlogContent();

        blogContent.setId(blogId);
        blogContent.setContentMd(articleParams.getContent());
        blogContent.setContent(articleParams.getHtmlContent());
        blogContentMapper.insert(blogContent);
        return true;
    }

    @Override
    public boolean updateArticleStatus(@NonNull Integer blogId, @NonNull String status) {

        Blog blog = new Blog();
        blog.setStatus(status);
        blog.setId(blogId);
        blogMapper.updateByPrimaryKeySelective(blog);
        return true;
    }

    @Override
    public BlogDetail getArticleDetail(@NonNull Integer blogId) {

        Blog blog = blogMapper.selectByPrimaryKey(blogId);


        BlogContent blogContent = blogContentMapper.selectByPrimaryKey(blogId);

        BlogDetail blogDetail = new BlogDetail();

        blogDetail.setId(blog.getId());
        blogDetail.setContent(blogContent.getContent());
        blogDetail.setContentMd(blogContent.getContentMd());
        blogDetail.setNearestModifyDate(blog.getNearestModifyDate());
        blogDetail.setReleaseDate(blog.getReleaseDate());
        blogDetail.setStatus(blog.getStatus());
        blogDetail.setTagTitle(blog.getTagTitle());
        blogDetail.setTitle(blog.getTitle());
        blogDetail.setSummary(blog.getSummary());

        return blogDetail;
    }

    @Override

    public @NonNull DataGrid getArticleList(@NonNull int pageNum, @NonNull int pageSize,@NonNull String token) {

        Integer blogger_id = userService.getUserIdByToken(token);

        BlogExample blogExample = new BlogExample();
        BlogExample.Criteria criteria = blogExample.createCriteria();
        criteria.andBloggerIdEqualTo(blogger_id);

        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.selectByExample(blogExample);


        PageInfo<Blog> list = new PageInfo<>(blogs);

        DataGrid dataGrid = new DataGrid();

        dataGrid.setTotal(list.getTotal());

        List<Blog> result = list.getList();

        result = result.stream().map(item->{return new Blog(item.getId()
                ,item.getStatus(),
                item.getTitle(),
                item.getSummary(),
                item.getReleaseDate(),
                item.getNearestModifyDate(),
                item.getTagTitle());}).collect(Collectors.toList());


        dataGrid.setRows(result);

        return dataGrid;
    }

    @Override
    public @NonNull DataGrid getArticleList(@NonNull int pageNum, @NonNull int pageSize) {
        BlogExample blogExample = new BlogExample();

        blogExample.setOrderByClause("release_date");

        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.selectByExample(blogExample);


        PageInfo<Blog> list = new PageInfo<>(blogs);

        DataGrid dataGrid = new DataGrid();

        dataGrid.setTotal(list.getTotal());

        List<Blog> result = list.getList();

        result = result.stream().map(item->{return new Blog(item.getId()
                ,item.getStatus(),
                item.getTitle(),
                item.getSummary(),
                item.getReleaseDate(),
                item.getNearestModifyDate(),
                item.getTagTitle());}).collect(Collectors.toList());


        dataGrid.setRows(result);

        return dataGrid;
    }

    @Override
    @Transactional
    public void deleteBlog(@NonNull Integer blogId) {
        blogMapper.deleteByPrimaryKey(blogId);
        blogContentMapper.deleteByPrimaryKey(blogId);

    }

    @Override
    public long getArticleCount(@NonNull String token) {
        Integer bloggerId = userService.getUserIdByToken(token);

        BlogExample blogExample = new BlogExample();

        BlogExample.Criteria criteria = blogExample.createCriteria();
        criteria.andBloggerIdEqualTo(bloggerId);
       return blogMapper.countByExample(blogExample);

    }
}
