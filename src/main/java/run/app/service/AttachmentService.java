package run.app.service;

import org.springframework.web.multipart.MultipartFile;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.DataGrid;
import run.app.entity.VO.AttachmentParams;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/9/3 19:38
 * Description: :附件库的服务层
 */
public interface AttachmentService {

    String selectPicById(Long id);

    BaseResponse getAttachmentList(int pageSize,int pageNum,String token);

    BaseResponse uploadFile(MultipartFile file,String token);

    Long getIdByTitle(String title);

    BaseResponse updateInfo(Long id, AttachmentParams attachmentParams, String token);

    BaseResponse getInfo(Long id,String token);

    BaseResponse deletePic(Long id,String token);


}
