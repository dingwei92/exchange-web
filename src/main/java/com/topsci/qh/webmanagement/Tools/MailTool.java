package com.topsci.qh.webmanagement.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Created by lzw on 2017/6/27.
 */
public class MailTool {
    private static String host; // smtp服务器
    private static String port; // smtp服务器端口
    private static String from; // 发件人地址
    private static String user ; // 用户名
    private static String pwd; // 密码
    private static Properties properties;
    private static Logger logger = LoggerFactory.getLogger(MailTool.class);
    private static Config config;
    private static boolean initialized = false;

    private static void initialized()
    {
        if(!initialized) {
            config = Config.getConfig();
//            config = new Config();
            user = config.getEmail_user();
            pwd = config.getEmail_pwd();
            host = config.getEmail_smtp();
            port = config.getEmail_port();
            from = config.getEmail_account();
            properties = new Properties();
            // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "false") ;
            properties.put("mail.smtp.socketFactory.port",port) ;

            // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.ssl.enable", "true");
            initialized = true;
        }
    }


    public static void send(String to,String subject,String content) {
        initialized();
        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(properties);

        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(false);

        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(from));
            // 加载收件人地址
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 加载标题
            message.setSubject(subject);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(content);
            multipart.addBodyPart(contentPart);

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport transport = session.getTransport("smtp");
            // 连接服务器的邮箱
            transport.connect( user, pwd);
            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            logger.error("向"+to+"发送邮件失败");
        }
    }

    public static void sendWithAppendix(String to,String subject,String content,String filepath,String filename) {
        initialized();
        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(properties);

        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(false);

        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(from));
            // 加载收件人地址
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 加载标题
            message.setSubject(subject);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(content);
            multipart.addBodyPart(contentPart);
            // 添加附件
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filepath);
            // 添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            // 添加附件的标题
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            messageBodyPart.setFileName("=?GBK?B?"
                    + enc.encode(filename.getBytes()) + "?=");
            multipart.addBodyPart(messageBodyPart);

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport transport = session.getTransport("smtp");
            // 连接服务器的邮箱
            transport.connect(host, user, pwd);
            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            logger.error("向"+to+"发送邮件失败");
        }
    }

    public static void main(String[] args)
    {
        send("zwlu@cm-topsci.com","sub","test");
    }
}
