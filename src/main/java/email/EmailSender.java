package email;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EmailSender {
    public static boolean send(String targetUser, String conent) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        MailSender mailSender = applicationContext.getBean(MailSender.class);
        SimpleMailMessage message = new SimpleMailMessage();//消息构造器
        message.setFrom("547240561@qq.com");//发件人
        message.setTo(targetUser);//收件人
        message.setSubject("邮箱提醒");//主题
        message.setText(conent);//正文
        mailSender.send(message);
        return true;
    }
}
