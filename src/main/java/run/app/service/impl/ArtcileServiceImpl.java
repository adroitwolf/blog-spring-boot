package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.app.entity.DTO.DataGrid;
import run.app.entity.model.Blog;
import run.app.entity.model.BlogExample;
import run.app.entity.model.BlogWithBLOBs;
import run.app.entity.params.ArticleParams;
import run.app.exception.BadRequestException;
import run.app.mapper.BlogMapper;
import run.app.service.ArticleService;
import run.app.service.UserService;
import run.app.util.AppUtil;

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

    @Override
    public @NonNull boolean submitArticle(@NonNull ArticleParams articleParams,@NonNull String token) {

        BlogWithBLOBs blog = new BlogWithBLOBs();
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
        blog.setContent(articleParams.getHtmlContent());
        blog.setContentMd(articleParams.getContent());
        blog.setStatus("PUBLISHED");
        int id = blogMapper.insertSelective(blog);

        return true;
    }

    @Override
    public boolean updateArticle(@NonNull ArticleParams articleParams, @NonNull Integer blogId, @NonNull String token) {

        BlogWithBLOBs blog = new BlogWithBLOBs();
        if((userService.getUserIdByToken(token)) == -1){
            throw  new BadRequestException("用户信息错误！");
        }
        blog.setId(blogId);
        blog.setNearestModifyDate(new Date());
        blog.setSummary(articleParams.getSummary());
        blog.setTitle(articleParams.getTitle());
        blog.setTagTitle(articleParams.getTag());
        blog.setContent(articleParams.getHtmlContent());
        blog.setContentMd(articleParams.getContent());

        blogMapper.updateByPrimaryKeySelective(blog);

        return true;
    }

    @Override
    public boolean updateArticleStatus(@NonNull Integer blogId, @NonNull String status) {

        BlogWithBLOBs blogWithBLOBs = new BlogWithBLOBs();
        blogWithBLOBs.setStatus(status);
        blogWithBLOBs.setId(blogId);

        blogMapper.updateByPrimaryKeySelective(blogWithBLOBs);
        return true;
    }

    @Override
    public @NonNull DataGrid getArticleList(@NonNull int pageNum, @NonNull int pageSize,@NonNull String token) {

        Integer blogger_id = userService.getUserIdByToken(token);

        BlogExample blogExample = new BlogExample();
        BlogExample.Criteria criteria = blogExample.createCriteria();
        criteria.andBloggerIdEqualTo(blogger_id);

        PageHelper.startPage(pageNum,pageSize);
        List<BlogWithBLOBs> blogWithBLOBs = blogMapper.selectByExampleWithBLOBs(blogExample);


        PageInfo<BlogWithBLOBs> list = new PageInfo<>(blogWithBLOBs);

        DataGrid dataGrid = new DataGrid();

        dataGrid.setTotal(list.getTotal());

        List<BlogWithBLOBs> result = list.getList();

        result = result.stream().map(item->{return new BlogWithBLOBs(item.getId()
                ,item.getStatus(),
                item.getTitle(),
                item.getSummary(),
                item.getReleaseDate(),
                item.getNearestModifyDate(),
                item.getTagTitle(),
                item.getContent(),
                item.getContentMd());}).collect(Collectors.toList());


        dataGrid.setRows(result);

        return dataGrid;
    }

    @Override
    public @NonNull DataGrid getArticleList(@NonNull int pageNum, @NonNull int pageSize) {
        BlogExample blogExample = new BlogExample();

        blogExample.setOrderByClause("release_date");

        PageHelper.startPage(pageNum,pageSize);
        List<BlogWithBLOBs> blogWithBLOBs = blogMapper.selectByExampleWithBLOBs(blogExample);


        PageInfo<BlogWithBLOBs> list = new PageInfo<>(blogWithBLOBs);

        DataGrid dataGrid = new DataGrid();

        dataGrid.setTotal(list.getTotal());

        List<BlogWithBLOBs> result = list.getList();

        result = result.stream().map(item->{return new BlogWithBLOBs(item.getId()
                ,item.getStatus(),
                item.getTitle(),
                item.getSummary(),
                item.getReleaseDate(),
                item.getNearestModifyDate(),
                item.getTagTitle(),
                item.getContent(),
                item.getContentMd());}).collect(Collectors.toList());


        dataGrid.setRows(result);

        return dataGrid;
    }

    @Override
    public void deleteBlog(@NonNull Integer blogId) {
        blogMapper.deleteByPrimaryKey(blogId);
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
