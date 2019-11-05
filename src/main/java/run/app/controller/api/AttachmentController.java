package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.DataGrid;
import run.app.entity.VO.AttachmentParams;
import run.app.service.AttachmentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/9/3 19:28
 * Description: :附件控制层
 */
@RequestMapping("api/admin/attachment")
@RestController
@Slf4j
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    private final String  TOKEN = "Authentication";

    @GetMapping("list")
    @ApiOperation("获取所有附件")
    public BaseResponse getAttachmentList(@RequestParam("pageSize")int pageSize, @RequestParam("pageNum")int pageNum,
                                      HttpServletRequest request){
        return attachmentService.getAttachmentList(pageSize,pageNum,request.getHeader(TOKEN));
    }


    @PostMapping("upload")
    @ApiOperation("上传图片")
    public BaseResponse uploadFile(MultipartFile file,HttpServletRequest request){
        return attachmentService.uploadFile(file,request.getHeader(TOKEN));
    }


    @GetMapping("{picId:\\d+}/info")
    @ApiOperation("获取图片的详细信息")
    public BaseResponse getInfo(@PathVariable("picId") Long picId,HttpServletRequest request){
        return attachmentService.getInfo(picId,request.getHeader(TOKEN));
    }


    @PutMapping("{picId:\\d+}/info")
    @ApiOperation("更新图片信息")
    public BaseResponse updateInfo(@PathVariable("picId")Long picId, @Valid @RequestBody AttachmentParams attachmentParams, HttpServletRequest request){
        return attachmentService.updateInfo(picId,attachmentParams,request.getHeader(TOKEN));
    }


    @DeleteMapping("{picId:\\d+}")
    @ApiOperation("删除图片")
    public BaseResponse deleteAttachment(@PathVariable("picId")Long picId,HttpServletRequest request){
        return attachmentService.deletePic(picId,request.getHeader(TOKEN));
    }
}
