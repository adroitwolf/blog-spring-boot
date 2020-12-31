package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.app.entity.DTO.*;
import run.app.entity.VO.CommentParams;
import run.app.entity.VO.PageInfo;
import run.app.entity.enums.CommentTypeEnum;
import run.app.entity.model.Blog;
import run.app.entity.model.Comments;
import run.app.entity.model.CommentsExample;
import run.app.exception.BadRequestException;
import run.app.exception.NotFoundException;
import run.app.mapper.CommentsMapper;
import run.app.service.ArticleService;
import run.app.service.CommentService;
import run.app.service.TokenService;
import run.app.service.UserService;
import run.app.util.AppUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/1/26 10:32
 * Description:评论服务实现类
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentsMapper commentsMapper;

    @Autowired
    TokenService tokenService;


    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Override
    public BaseResponse comment(CommentParams commentParams, String token) {

        Comments comments = new Comments();

        BeanUtils.copyProperties(commentParams, comments);

        comments.setType(CommentTypeEnum.valueOf(commentParams.getType()).getValue());
        Long userId = tokenService.getUserIdWithToken(token);

        // 这里来判断一下是否为作者
        // 这里应该确定文章是否存在

        Blog blog = articleService.getBlogByBlogId(commentParams.getObjectId());

        //这里应该确定父评论id是否存在

        if (!commentParams.getPid().equals(0L)) { //如果这是一个子评论
            Comments parent = commentsMapper.selectByPrimaryKey(commentParams.getPid());

            if (null == parent) {
                throw new NotFoundException("评论失败");
            } else {
                comments.setRoot(parent.getRoot().equals(0L) ? parent.getId() : parent.getRoot());
            }
        } else {
            comments.setRoot(0L);
        }

        if (null == blog) {
            throw new NotFoundException("该文章已飞到火星,评论失败");
        }

        Byte isAuthor = userId.equals(blog.getBloggerId()) ? (byte) 1 : (byte) 0;

        comments.setIsAuthor(isAuthor);

        Long commentId = AppUtil.nextId();

        comments.setId(commentId);

        comments.setFromId(userId);

        comments.setAuthorId(blog.getBloggerId());

        comments.setCreateTime(new Date());

        comments.setUpdateTime(new Date());

        String content = wrapContent(commentParams.getToId(), commentParams.getContent());

        comments.setContent(content);

        //设定评论未被删除
        comments.setIsDel((byte) 0);

        commentsMapper.insertSelective(comments);

        return new BaseResponse(HttpStatus.OK.value(), "评论成功", null);
    }

    @Override
    public BaseResponse getList(Long id, String type, PageInfo pageInfo) {

        DataGrid dataGrid = CommentTypeEnum.BLOG_COMMENT.equals(CommentTypeEnum.valueOf(type)) ?
                getPCommentsList(id, pageInfo) : getChildCommentsList(id, pageInfo);
        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setStatus(HttpStatus.OK.value());

        baseResponse.setData(dataGrid);

        return baseResponse;
    }

    @Override
    public BaseResponse getListByToken(PageInfo pageInfo, String token) {

        /**
         * 功能描述: 注意这里其实是可以看到那些删除评论相关的子评论的  具体结构是本体评论并且带上父评论 和哔哩哔哩的评论机制差不多
         * @Return: run.app.entity.DTO.BaseResponse
         * @Author: WHOAMI
         * @Date: 2020/2/17 12:16
         */
        BaseResponse baseResponse = new BaseResponse();

        Long userId = tokenService.getUserIdWithToken(token);

        CommentsExample example = new CommentsExample();

        String sortSql = pageInfo.convertToSortSql();
        //排序规则
        example.setOrderByClause(sortSql);

        CommentsExample.Criteria criteria = example.createCriteria();

        criteria.andAuthorIdEqualTo(userId);

        criteria.andIsDelEqualTo((byte) 0);


        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());


        List<Comments> commentsList = commentsMapper.selectByExample(example);


        com.github.pagehelper.PageInfo<Comments> list = new com.github.pagehelper.PageInfo<>(commentsList);

        //这里应该是一个子评论加一个父评论的效果

        List<MComment> collect = list.getList().stream().map(item -> {

            MComment mComment = new MComment();


            Comment comment = commentsConvertToDto(item);

            mComment.setSelf(comment);


            if (0L != comment.getPid()) {  //这时候的父评论不管是否删除过都会显示
                mComment.setParent(commentsConvertToDto(commentsMapper.selectByPrimaryKey(comment.getPid())));
            }

            mComment.setBlog(articleService.getBaseBlogById(item.getObjectId()));

            return mComment;

        }).collect(Collectors.toList());


        DataGrid dataGrid = new DataGrid();

        dataGrid.setTotal(list.getTotal());


        dataGrid.setRows(collect);


        baseResponse.setData(dataGrid);


        baseResponse.setStatus(HttpStatus.OK.value());
        return baseResponse;
    }

    @Override
    @Transactional
    public BaseResponse deleteComment(Long commentId, String token) {
        /**
         * 功能描述:这里的逻辑是 逻辑删除,物理删除需要通过专门的服务进行清理
         * @Author: WHOAMI
         * @Date: 2020/2/15 19:56
         */
        Comments comments = commentsMapper.selectByPrimaryKey(commentId);


        if (null == comments) {
            throw new BadRequestException("请不要进行注入操作");
        }
        tokenService.authentication(comments.getAuthorId(), token);

        comments.setIsDel((byte) 1);


        commentsMapper.updateByPrimaryKeySelective(comments);

        return new BaseResponse(HttpStatus.OK.value(), "删除评论成功", null);
    }


    private DataGrid getPCommentsList(Long id, PageInfo pageInfo) {

        DataGrid dataGrid = new DataGrid();

        String sortSql = pageInfo.convertToSortSql();

        CommentsExample commentsExample = new CommentsExample();

        commentsExample.setOrderByClause(sortSql);

        CommentsExample.Criteria criteria = commentsExample.createCriteria();

        criteria.andPidEqualTo(0L);

        criteria.andObjectIdEqualTo(id);

        criteria.andIsDelEqualTo((byte) 0);

        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());

        List<Comments> comments = commentsMapper.selectByExample(commentsExample);

        com.github.pagehelper.PageInfo<Comments> list = new com.github.pagehelper.PageInfo<>(comments);

        dataGrid.setTotal(list.getTotal());

        List<PComment> data = list.getList().stream().map(parent -> {
            PComment comment = new PComment();

            BeanUtils.copyProperties(parent, comment);

            //获取到评论人相关信息
            UserDTO user = userService.getUserDTOById(parent.getFromId());

            comment.setUser(user);

            //这里只会获取前三条子评论

            commentsExample.clear();

            commentsExample.setOrderByClause(sortSql);


            CommentsExample.Criteria criteria1 = commentsExample.createCriteria();

            criteria1.andRootEqualTo(parent.getId());

            PageHelper.startPage(1, 3);

            List<Comments> commentsList = commentsMapper.selectByExample(commentsExample);

            com.github.pagehelper.PageInfo<Comments> commentsPageInfo = new com.github.pagehelper.PageInfo<>(commentsList);

            List<Comment> childs = commentsPageInfo.getList().stream().map(child -> {

                return commentsConvertToDto(child);
            }).collect(Collectors.toList());

            comment.setChildren(childs);
            comment.setChildren_count(commentsPageInfo.getTotal());

            return comment;

        }).collect(Collectors.toList());

        dataGrid.setRows(data);

        return dataGrid;

    }

    private DataGrid getChildCommentsList(Long id, PageInfo pageInfo) { //获取子评论，这里的id是父评论id

        DataGrid dataGrid = new DataGrid();

        String sortSql = pageInfo.convertToSortSql();

        CommentsExample commentsExample = new CommentsExample();

        commentsExample.setOrderByClause(sortSql);

        CommentsExample.Criteria criteria = commentsExample.createCriteria();

        criteria.andRootEqualTo(id);

        criteria.andIsDelEqualTo((byte) 0);

        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());

        List<Comments> commentsList = commentsMapper.selectByExample(commentsExample);


        com.github.pagehelper.PageInfo<Comments> commentsPageInfo = new com.github.pagehelper.PageInfo<>(commentsList);

        List<Comment> comments = commentsPageInfo.getList().stream().map(child -> {

            return commentsConvertToDto(child);

        }).collect(Collectors.toList());


        dataGrid.setRows(comments);


        dataGrid.setTotal(commentsPageInfo.getTotal());
        return dataGrid;

    }


    private String wrapContent(Long id, String content) {


        StringBuilder builder = new StringBuilder();

        if (null == id || id.equals(0L)) { //这说明是一个父评论

            builder.append(content);

        } else {
            builder.append("回复");
            builder.append(userService.getNicknameById(id));
            builder.append(": ");
            builder.append(content);
        }

        return builder.toString();
    }


    private Comment commentsConvertToDto(Comments comments) {

        Comment comment1 = new Comment();
        BeanUtils.copyProperties(comments, comment1);

        UserDTO user1 = userService.getUserDTOById(comments.getFromId());

        comment1.setUser(user1);
        return comment1;
    }


}
