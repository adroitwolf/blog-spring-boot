package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import run.app.entity.DTO.*;
import run.app.entity.VO.AttachmentParams;
import run.app.entity.model.BloggerPicture;
import run.app.entity.model.BloggerPictureExample;
import run.app.exception.BadRequestException;
import run.app.mapper.BloggerPictureMapper;
import run.app.security.token.TokenService;
import run.app.service.ArticleService;
import run.app.service.AttachmentService;
import run.app.service.UserService;
import run.app.util.AppUtil;
import run.app.util.UploadUtil;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/9/3 19:38
 * Description: ://TODO ${END}
 */
@Slf4j
@Service
public class AttachmentServiceImpl implements AttachmentService {


    @Autowired
    BloggerPictureMapper bloggerPictureMapper;



    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    TokenService tokenService;



    AppUtil appUtil;

    UploadUtil uploadUtil;

    public AttachmentServiceImpl() {
        this.appUtil =  AppUtil.getInstance();
        this.uploadUtil = UploadUtil.getInstance();
    }


    @Override
    public String selectPicById(Long id) {
        return bloggerPictureMapper.selectByPrimaryKey(id).getFileKey();
    }


    @Override
    public BaseResponse getAttachmentList(int pageSize, int pageNum, String token) {

        Long id = tokenService.getUserIdWithToken(token);

        BloggerPictureExample bloggerPictureExample = new BloggerPictureExample();

        bloggerPictureExample.setOrderByClause("upload_date desc");

        BloggerPictureExample.Criteria criteria = bloggerPictureExample.createCriteria();

        criteria.andBloggerIdEqualTo(id);

        PageHelper.startPage(pageNum,pageSize);

        List<BloggerPicture> bloggerPictures = bloggerPictureMapper.selectByExample(bloggerPictureExample);

        PageInfo<BloggerPicture> bloggerPicturePageInfo = new PageInfo<>(bloggerPictures);


        //todo:
        List<Picture> pictures = bloggerPictures.stream().filter(Objects::nonNull).map(item ->{
            return new Picture(item.getId(),item.getPath(),item.getTitle());

                }
        ).collect(Collectors.toList());


        DataGrid dataGrid = new DataGrid();

        dataGrid.setTotal(bloggerPicturePageInfo.getTotal());
        dataGrid.setRows(pictures);

        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setData(dataGrid);

        return baseResponse;
    }

    @Override
    @Transactional
    public BaseResponse uploadFile(MultipartFile file, String token) {
        UploadUtil instance = UploadUtil.getInstance();
        ImageFile imageFile = instance.uploadFile(file).orElseThrow(()->new BadRequestException("用户上传图片失败"));

        Long user_id = tokenService.getUserIdWithToken(token);

        BloggerPicture bloggerPicture = new BloggerPicture();

        bloggerPicture.setId(appUtil.nextId());

        bloggerPicture.setBloggerId(user_id);

        bloggerPicture.setUploadDate(new Date());

        bloggerPicture.setUpdateDate(new Date());

//        修复上传逻辑错误，开始上传的图片引用人数应该是0
        bloggerPicture.setCiteNum(0);

        bloggerPicture.setMediaType(imageFile.getMediaType().getType());

        BeanUtils.copyProperties(imageFile,bloggerPicture);

        bloggerPictureMapper.insertSelective(bloggerPicture);
        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setData(imageFile.getTitle());

        return baseResponse;
    }

    @Override
    public Long getIdByTitle(String title) {
        BloggerPictureExample bloggerPictureExample = new BloggerPictureExample();
        BloggerPictureExample.Criteria criteria = bloggerPictureExample.createCriteria();
        criteria.andFileKeyEqualTo(title);
        List<BloggerPicture> bloggerPictures = bloggerPictureMapper.selectByExample(bloggerPictureExample);
        BloggerPicture bloggerPicture = bloggerPictures.stream().filter(Objects::nonNull).findFirst().orElseThrow(() -> new BadRequestException("图片名称有误,或附件已被删除！"));
        return bloggerPicture.getId();

    }

    @Override
    @Transactional
    public BaseResponse updateInfo(Long id, AttachmentParams attachmentParams , String token) {

        BloggerPicture bloggerPicture = bloggerPictureMapper.selectByPrimaryKey(id);

        tokenService.authentication(bloggerPicture.getBloggerId(),token);

        BloggerPicture bloggerPicture1 = new BloggerPicture();

        bloggerPicture1.setTitle(attachmentParams.getTitle());
        bloggerPicture1.setId(id);
        bloggerPictureMapper.updateByPrimaryKeySelective(bloggerPicture1);

        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setStatus(HttpStatus.OK.value());

        return baseResponse;
    }

    @Override
    public BaseResponse getInfo(Long id, String token) {
        BloggerPicture bloggerPicture = bloggerPictureMapper.selectByPrimaryKey(id);


        //需要处理空字符问题
        tokenService.authentication(bloggerPicture.getBloggerId(),token);

        PictureInfo pictureInfo = new PictureInfo();

        BeanUtils.copyProperties(bloggerPicture,pictureInfo);

        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setData(pictureInfo);

        baseResponse.setStatus(HttpStatus.OK.value());
        return baseResponse;
    }

    @Override
    @Transactional
    public BaseResponse deletePic(Long id, String token) {
        BloggerPicture bloggerPicture = bloggerPictureMapper.selectByPrimaryKey(id);


        //需要处理空字符问题
        tokenService.authentication(bloggerPicture.getBloggerId(),token);

        bloggerPictureMapper.deleteByPrimaryKey(id);

        articleService.deleteQuotePic(id);

        uploadUtil.delFile(bloggerPicture.getPath());
        uploadUtil.delFile(bloggerPicture.getThumbPath());

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());

        baseResponse.setMessage("图片删除成功");



        return baseResponse;
    }


}
