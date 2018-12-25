package email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by Administrator on 2018/12/19.
 */
@Configuration
public class RootConfig {
    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");//指定用来发送Email的邮件服务器主机名
        mailSender.setPort(25);//默认端口，标准的SMTP端口
        mailSender.setUsername("547240561@qq.com");//用户名
        mailSender.setPassword("sw19920914");//密码
        return mailSender;
    }
}
