package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.app.entity.model.BlogLabel;
import run.app.entity.model.BlogLabelExample;
import run.app.entity.model.BlogTagMapExample;
import run.app.entity.model.BlogTagMapKey;
import run.app.mapper.BlogLabelMapper;
import run.app.mapper.BlogTagMapMapper;
import run.app.service.TagService;
import run.app.util.AppUtil;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/23 21:49
 * Description: :文章标签功能的具体实现
 */
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    BlogTagMapMapper blogTagMapMapper;

    @Autowired
    BlogLabelMapper blogLabelMapper;



    @Override
    public String submitArticleWithTagString(List<String> tags,Long blogId) {

//        首先要去重
       tags = (List<String>) AppUtil.removeDuplicateListItem(tags);

        BlogLabelExample blogLabelExample = new BlogLabelExample();

        List<Long> nowTags = new ArrayList<>();

        tags.stream().filter(Objects::nonNull).forEach(item ->{
            blogLabelExample.clear();
            BlogLabelExample.Criteria blogLabelExampleCriteria1 = blogLabelExample.createCriteria();
            blogLabelExampleCriteria1.andTitleEqualTo(item);

            Optional<String> temp = Optional.ofNullable( blogLabelMapper.selectByExampleForPrimaryKey(blogLabelExample));
            if(!temp.isPresent()){
//                        需要新建标签
                BlogLabel blogLabel = new BlogLabel();
                blogLabel.setCiteNum(1);
                blogLabel.setCreateDate(new Date());
                blogLabel.setTitle(item);
                blogLabel.setId(AppUtil.nextId());
                blogLabelMapper.insertSelective(blogLabel);

                blogTagMapMapper.insert(new BlogTagMapKey(blogLabel.getId(),blogId));

                nowTags.add(blogLabel.getId());


            }else{

                blogLabelMapper.updateByPrimaryKeyForAddNum(Long.valueOf(temp.get()));
                blogTagMapMapper.insert(new BlogTagMapKey(Long.valueOf(temp.get()),blogId));
                nowTags.add(Long.valueOf(temp.get()));

            }




        });

        return StringUtils.join(nowTags,",");

    }

    @Override
    @Transactional
    public String updateTagsWithId(Long blogId,List<String> tagsParams) {
        /**
         * 功能描述: 增加更新文章代码
         * 内容描述：主要是在标签生成方面 本次修改伴随着数据库底层代码修改
         * @Author: WHOAMI
         * @Date: 2019/8/23 17:35
         */

//        查询更新之前到相应的标签id 并且每一个都要在tag标签使用人数中自减1 并且删除掉tag_map标签中的 集合

        BlogTagMapExample blogTagMapExample = new BlogTagMapExample();
        BlogTagMapExample.Criteria criteria = blogTagMapExample.createCriteria();
        criteria.andBlogIdEqualTo(blogId);


        List<Long> updateTags = blogTagMapMapper.selectByExampleForTag(blogTagMapExample);

        updateTags.stream().filter(Objects::nonNull)
                .forEach(tags->{
                    blogLabelMapper.updateByPrimaryKeyForReduceNum(tags);
                    blogTagMapMapper.deleteByPrimaryKey(new BlogTagMapKey(tags,blogId));
                });


//        查询到现在的标签id没有的话新增
        BlogLabelExample blogLabelExample = new BlogLabelExample();

        List<Long> nowTags = new ArrayList<>();


        tagsParams.stream().filter(Objects::nonNull)
                .forEach(value->{
                    log.info(value);
                    blogLabelExample.clear();
                    BlogLabelExample.Criteria blogLabelExampleCriteria1 = blogLabelExample.createCriteria();
                    blogLabelExampleCriteria1.andTitleEqualTo(value);

                    Optional<String> temp = Optional.ofNullable( blogLabelMapper.selectByExampleForPrimaryKey(blogLabelExample));

                    if(!temp.isPresent()){
//                        需要新建标签
                        BlogLabel blogLabel = new BlogLabel();
                        blogLabel.setCiteNum(1);
                        blogLabel.setCreateDate(new Date());
                        blogLabel.setTitle(value);
                        blogLabel.setId(AppUtil.nextId());
                        blogLabelMapper.insertSelective(blogLabel);


                        blogTagMapMapper.insertSelective(new BlogTagMapKey(blogLabel.getId(),blogId));

                        nowTags.add(blogLabel.getId());


                    }else{
                        log.info("id:"+temp.get()) ;
                        blogLabelMapper.updateByPrimaryKeyForAddNum(Long.valueOf(temp.get()));
                        blogTagMapMapper.insert(new BlogTagMapKey(Long.valueOf(temp.get()),blogId));
                        nowTags.add(Long.valueOf(temp.get()));
                    }

                });


        return StringUtils.join(nowTags,",");


    }

    @Override
    public List<String> selectTagTitleByIdString(String ids) {
        List<String> list = Arrays.asList(ids.split(","));


        List<String> tags = new ArrayList<>();

        list.stream().filter(Objects::nonNull)
                .forEach(x-> tags.add(blogLabelMapper.selectByExampleForTitleByPrimaryKey(Long.valueOf(x))));


        return tags;
    }

    @Override
    @Transactional
    public void dealWithNumByIdString(String ids,boolean status) {
        List<String> list  = Arrays.asList(ids.split(","));

        list.stream().filter(Objects::nonNull)
                .forEach(x->{
                    if(status){
                        blogLabelMapper.updateByPrimaryKeyForAddNum(Long.valueOf(x));
                    }else{
                        blogLabelMapper.updateByPrimaryKeyForReduceNum(Long.valueOf(x));
                    }
                });
    }

    @Override
    @Transactional
    public void deleteTagsKeyByBlogId(Long blogId) {
        BlogTagMapExample blogTagMapExample = new BlogTagMapExample();
        BlogTagMapExample.Criteria criteria = blogTagMapExample.createCriteria();
        criteria.andBlogIdEqualTo(blogId);
        blogTagMapMapper.deleteByExample(blogTagMapExample);
    }

    @Override
    public Long selectIdWithName(String tag) {

        BlogLabelExample labelExample = new BlogLabelExample();

        BlogLabelExample.Criteria criteria = labelExample.createCriteria();
        criteria.andTitleEqualTo(tag);

        List<BlogLabel> blogLabels = blogLabelMapper.selectByExample(labelExample);

        return blogLabels.get(0).getId();


    }

    @Override
    public List<Long> selectBlogIdByTagId(int pageSize,int pageNum,Long id) {
        BlogTagMapExample blogTagMapExample = new BlogTagMapExample();
        BlogTagMapExample.Criteria criteria = blogTagMapExample.createCriteria();
        criteria.andTagIdEqualTo(id);

        PageHelper.startPage(pageNum,pageSize);


        return  blogTagMapMapper.selectByExampleForBlogId(blogTagMapExample);


    }
}
