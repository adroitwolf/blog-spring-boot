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
import run.app.entity.params.ArticleParams;
import run.app.entity.params.PostQueryParams;
import run.app.exception.BadRequestException;
import run.app.exception.ServiceException;
import run.app.mapper.BlogContentMapper;
import run.app.mapper.BlogLabelMapper;
import run.app.mapper.BlogMapper;
import run.app.mapper.BlogTagMapMapper;
import run.app.security.token.TokenService;
import run.app.service.ArticleService;
import run.app.service.TagService;
import run.app.service.UserService;

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
        Integer bloggerId;
//        if((bloggerId =userService.getUserIdByToken(token)) == -1){
        if((bloggerId =tokenService.getUserIdWithToken(token)) == -1){
            throw  new BadRequestException("用户信息错误！");
        }
        blog.setBloggerId(bloggerId);
        blog.setReleaseDate(new Date());
        blog.setNearestModifyDate(new Date());
        blog.setSummary(articleParams.getSummary());
        blog.setTitle(articleParams.getTitle());



        //todo  tag问题
//        blog.setTagTitle(articleParams.getTag());

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


        /**
        * 功能描述: 增加文章标签功能
        * @Author: WHOAMI
         */
        String nowTagsString = tagService.updateTagsWithId(blogId,articleParams.getTagList());

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



        blogMapper.updateByPrimaryKeySelective(blog);

        BlogContent blogContent = new BlogContent();

        blogContent.setId(blogId);
        blogContent.setContentMd(articleParams.getContent());
        blogContent.setContent(articleParams.getHtmlContent());
        blogContentMapper.updateByPrimaryKeyWithBLOBs(blogContent);
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
        blogDetail.setTitle(blog.getTitle());
//        todo tag标签问题



        if(!StringUtils.isBlank(blog.getTagTitle())) {
            String nowTagsId = blog.getTagTitle();
            List<String> nowTagsIdList = Arrays.asList(nowTagsId.split(","));

            List<String> nowTags = new ArrayList<>();

            nowTagsIdList.stream().filter(Objects::nonNull)
                    .filter(string -> !string.isEmpty())
                    .forEach(id -> nowTags.add(blogLabelMapper.selectByExampleForTitleByPrimaryKey(Integer.valueOf(id))));

            log.info("现在的标签序列：" + nowTags.toString());

            blogDetail.setTagsTitle(nowTags);
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

            return new run.app.entity.DTO.Blog(item.getId()
                    ,item.getStatus(),
                    item.getTitle(),
                    item.getSummary(),
                    item.getReleaseDate(),
                    item.getNearestModifyDate(),
                    tagsTitle);
        }).collect(Collectors.toList());


        dataGrid.setRows(blogs);

        dataGrid.setTotal(list.getTotal());

        return dataGrid;
    }


    @Override
    @Transactional
    public void deleteBlog(@NonNull Integer blogId) {


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

        Integer bloggerId = tokenService.getUserIdWithToken(token);
        BlogExample blogExample = new BlogExample();

        BlogExample.Criteria criteria = blogExample.createCriteria();
        criteria.andBloggerIdEqualTo(bloggerId);
       return blogMapper.countByExample(blogExample);

    }
}
