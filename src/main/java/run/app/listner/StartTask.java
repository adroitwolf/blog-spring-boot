package run.app.listner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/12/9 22:46
 * Description: 项目初始化
 */
@Slf4j
@Component
public class StartTask implements CommandLineRunner {

    @Value(value = "${web.upload-path}")
    private String imgUpload;

    @Override
    public void run(String... args) {
        initImgFileFolder();
    }

    private void initImgFileFolder() {

        // 初始化用户图片文件夹
        String filePath = imgUpload;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        log.info("图片初始化完毕");
    }
}

