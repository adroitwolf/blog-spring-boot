package run.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import run.app.service.QMailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/3/10 10:48
 * Description: QQ邮箱服务实现类
 */
@Service
@Slf4j
public class QMailServiceImpl implements QMailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.nickname}")
    private String nickname;


    @Value("${spring.mail.from}")
    private String from;

    @Override
    @Async
    public void sendSimpleMail(String to, String subject, String content) {
        log.info("开始发送邮件");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(nickname + "<" + from + ">");

        simpleMailMessage.setTo(to);

        simpleMailMessage.setSubject(subject);

        simpleMailMessage.setText(content);

        try{
            javaMailSender.send(simpleMailMessage);
        }catch (Exception e){
            log.error("发送失败");
            log.error(e.getMessage());

        }

    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
//获取MimeMessage对象
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(subject);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            javaMailSender.send(message);
            //日志信息
            log.info("邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
            log.error(e.getMessage());
        }
    }
}
