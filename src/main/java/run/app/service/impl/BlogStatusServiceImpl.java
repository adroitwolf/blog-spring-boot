package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.app.entity.DTO.PopularBlog;
import run.app.entity.model.BlogStatus;
import run.app.entity.model.BlogStatusExample;
import run.app.mapper.BlogStatusMapper;
import run.app.service.ArticleService;
import run.app.service.BlogStatusService;
import run.app.service.RedisService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/29 13:41
 * Description: 博客状态实现类
 */
@Service
public class BlogStatusServiceImpl implements BlogStatusService {

    @Autowired
    RedisService redisService;


    @Autowired
    ArticleService articleService;

    @Autowired
    BlogStatusMapper blogStatusMapper;

    @Override
    @Transactional
    public void transClickedCountFromRedis2DB() {
        List<BlogStatus> statusList = redisService.listBlogClickedCounts();

        statusList.stream().filter(Objects::nonNull).forEach(entity->{
            BlogStatus status = blogStatusMapper.selectByPrimaryKey(entity.getId());

            if(null != status){
                status.setClickcount(status.getClickcount() + entity.getClickcount());
                status.setId(entity.getId());
                blogStatusMapper.updateByPrimaryKey(status);
            }else{
                blogStatusMapper.insert(entity);
            }
        });
    }

    @Override
    public List<PopularBlog> listTop5Posts() {
        BlogStatusExample example = new BlogStatusExample();
        example.setOrderByClause("clickCount desc");
        List<PopularBlog> result = new ArrayList<>();
        PageHelper.startPage(0,5);
        List<BlogStatus> statuses = blogStatusMapper.selectByExample(example);
        statuses.stream().filter(Objects::nonNull).forEach(status->{
            PopularBlog popularBlog = new PopularBlog();
            BeanUtils.copyProperties(status,popularBlog);
            popularBlog.setBlogName(articleService.getArticleNameByBlogId(popularBlog.getId()));
            result.add(popularBlog);
        });

        return result;
    }
}
