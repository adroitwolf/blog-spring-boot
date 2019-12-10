package run.app.service;

import org.springframework.web.multipart.MultipartFile;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.DataGrid;
import run.app.entity.VO.AttachmentParams;
import run.app.entity.VO.AttachmentQueryParams;
import run.app.entity.enums.CiteNumEnum;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/9/3 19:38
 * Description: :附件库的服务层
 */
public interface AttachmentService {

    String selectPicById(Long id);

    BaseResponse getAttachmentList(int pageSize, int pageNum, AttachmentQueryParams attachmentQueryParams, String token);

    BaseResponse uploadAttachment(MultipartFile file,String token);

    Long uploadFile(MultipartFile file,Long userId,String title);

    Long getIdByTitle(String title);

    String getTitleById(Long id);

    String getPathById(Long id);

    BaseResponse updateInfo(Long id, AttachmentParams attachmentParams, String token);

    BaseResponse getInfo(Long id,String token);

    BaseResponse deleteAttachment(Long id,String token);

    void deletePic(Long id);

    BaseResponse findAllMediaType(String token);

    void changePictureStatus(Long id, CiteNumEnum citeNumEnum);
}
