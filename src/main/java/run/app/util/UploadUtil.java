package run.app.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import run.app.entity.DTO.ImageFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/9/2 9:32
 * Description: :上传的工具类
 */


@Slf4j
@Component
public class UploadUtil {

    private UploadUtil(){

    }


    private static final UploadUtil uploadUtil = new UploadUtil();


    public static UploadUtil getInstance(){
        return uploadUtil;
    }


    private static String imgPath;

    @Value(value = "${web.upload-path}")
    public void setImgPath(String imgPath){
        this.imgPath = imgPath;
    }

    public Optional<ImageFile> uploadFile(MultipartFile file){


        ImageFile imageFile = new ImageFile();


        String originFilename = file.getOriginalFilename();

        String type = originFilename.indexOf(".") == -1 ?null:originFilename.substring(originFilename.lastIndexOf(".")+1);

        String filename = UUID.randomUUID().toString();

        String finalFilename  =imgPath + File.separator + filename  + (null == type ? "" :("."+ type)) ;

        File file1 = new File(finalFilename);

        try {
            file.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
            return  Optional.ofNullable(null);
        }

        imageFile.setFileName(filename  + (null == type ? "" :("."+ type)));

        return  Optional.ofNullable();
    }



    public void delFile(String filename){
        File file = new File(imgPath+File.separator+ filename);
        if(file.exists()){
            log.info("文件删除"+file.delete() );
        }
    }

}
