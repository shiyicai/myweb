package com.springboot.config.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String userName;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${spring.mail.defaultEncoding}")
    private String defaultEncoding;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }


    public JavaMailSenderImpl createSender(){
        javaMailSender.setHost(this.getHost());
        javaMailSender.setDefaultEncoding(this.getDefaultEncoding());
        javaMailSender.setPort(this.getPort());
        javaMailSender.setUsername(this.getUserName());
        javaMailSender.setPassword(this.getPassword());
        javaMailSender.setProtocol(this.getProtocol());
        return javaMailSender;
    }
}
