package run.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.DataGrid;
import run.app.entity.DTO.ImageFile;
import run.app.entity.DTO.Picture;
import run.app.entity.model.BloggerPicture;
import run.app.entity.model.BloggerPictureExample;
import run.app.exception.BadRequestException;
import run.app.mapper.BloggerPictureMapper;
import run.app.security.token.TokenService;
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
    TokenService tokenService;


    AppUtil appUtil;

    public AttachmentServiceImpl() {
        this.appUtil =  AppUtil.getInstance();
    }


    @Override
    public String selectPicById(Long id) {
        return bloggerPictureMapper.selectByPrimaryKey(id).getFileKey();
    }


    @Override
    public DataGrid getAttachmentList(int pageSize, int pageNum, String token) {

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
            return new Picture(item.getId(),item.getPath());
                }
        ).collect(Collectors.toList());


        DataGrid dataGrid = new DataGrid();

        dataGrid.setTotal(bloggerPicturePageInfo.getTotal());
        dataGrid.setRows(pictures);

        return dataGrid;
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


        bloggerPicture.setTitle(imageFile.getTitle());

        bloggerPicture.setUploadDate(new Date());

        bloggerPicture.setUpdateDate(new Date());

        bloggerPicture.setCiteNum(1);

        bloggerPicture.setMediaType(imageFile.getMediaType().getType());

        bloggerPicture.setSuffx(imageFile.getSuffx());

        bloggerPicture.setThumbPath(imageFile.getThumbPath());

        bloggerPicture.setHeight(imageFile.getHeight());

        bloggerPicture.setWidth(imageFile.getWidth());

        bloggerPicture.setSize(imageFile.getSize());

        bloggerPicture.setPath(imageFile.getPath());

        bloggerPicture.setFileKey(imageFile.getFileKey());


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


}
