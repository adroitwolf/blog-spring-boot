package run.app.ServiceTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.PageInfo;
import run.app.entity.VO.AttachmentQueryParams;
import run.app.service.AttachmentService;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/13 14:00
 * Description: 附件服务测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AttachmentServiceTest {

    @Autowired
    AttachmentService attachmentService;


    @Test
    public void listTest(){
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(1);
        pageInfo.setPageSize(5);
        BaseResponse attachmentList = attachmentService.getAttachmentList(pageInfo, new AttachmentQueryParams("用户头像",null), "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiMzg3MTkzNzE3MDg3MjcyOTYwIiwidGVzdCJdLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6IldIT0FNSSIsImV4cCI6MTU3MzYzMjI1OCwidXNlcklkIjozODcxOTM3MTcwODcyNzI5NjB9.NpNj9MDGQPCJN6L5btoExJM0DrDGbN9KjaDXS_DsUGA");

        log.debug(attachmentList.getData().toString());
    }

}
