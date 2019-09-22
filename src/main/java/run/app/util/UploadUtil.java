package run.app.util;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import run.app.entity.DTO.ImageFile;
import run.app.entity.model.BloggerPicture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
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

        BufferedImage image;
        String originFilename = file.getOriginalFilename();

        String type = originFilename.indexOf(".") == -1 ?null:originFilename.substring(originFilename.lastIndexOf(".")+1);

        String filename = UUID.randomUUID().toString().replace("-","");

        String finalFilename  =imgPath + File.separator + filename  + (null == type ? "" :("."+ type)) ;

        File file1 = new File(finalFilename);

        try {
            String thumbFile = file1.getAbsolutePath() + filename + "-thumb.jpg";
            image = ImageIO.read(file.getInputStream());
            file.transferTo(file1);
            Thumbnails.of(file1)
                    .scale(0.25f)
                    .toFile(thumbFile);
            imageFile.setThumbPath(filename + "-thumb.jpg");

        } catch (IOException e) {
            e.printStackTrace();
            return  Optional.ofNullable(null);
        }

        //开始创建pojo对象
        imageFile.setPath(filename  + (null == type ? "" :("."+ type)));
        imageFile.setTitle(file.getOriginalFilename().indexOf(".") == -1 ? file.getOriginalFilename():
                file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf(".")));

        imageFile.setSuffx(type);

        imageFile.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())));

        imageFile.setFileKey(filename  + (null == type ? "" :("."+ type)));


        // 读取文件属性
        imageFile.setSize(file.getSize());
        imageFile.setHeight(image.getHeight());
        imageFile.setWidth(image.getWidth());



        return  Optional.ofNullable(imageFile);
    }



    public void delFile(String filename){
        File file = new File(imgPath+File.separator+ filename);
        if(file.exists()){
            log.info("文件删除"+file.delete() );
        }
    }

}
