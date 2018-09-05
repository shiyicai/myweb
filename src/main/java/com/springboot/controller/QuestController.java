package com.springboot.controller;


import com.springboot.config.email.MailConfig;
import com.springboot.mybatis.model.UsersDomain;
import com.springboot.response.BaseResponseBody;
import com.springboot.service.impl.CacheServiceImpl;
import com.springboot.service.impl.UsersServiceImpl;
import com.springboot.service.impl.MailServiceImpl;
import com.springboot.util.MyDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping(value = "/guests")
public class QuestController {
    Logger logger = LoggerFactory.getLogger(getClass());
    public static final String REGISTER_TYPE = "register";

    @Autowired
    UsersServiceImpl usersService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MailConfig mailConfig;
    @Autowired
    MailServiceImpl mailService;
    @Autowired
    CacheServiceImpl cacheService;


    @RequestMapping(value = "/register",method = RequestMethod.PUT)
    public BaseResponseBody register(@RequestParam(value = "email") String email,
                                       @RequestParam(value = "password") String password,
                                       @RequestParam(value = "userName") String userName,
                                       @RequestParam(value = "verCode")String verCode){
        if(usersService.findUserByEmail(email)!=null){
            throw new IllegalArgumentException("用户["+email+"]已注册");
        }

        String cachedCode = cacheService.getVerificationCode(email,REGISTER_TYPE);
        if(!cachedCode.equals(verCode)){
            throw new SecurityException("邮件验证码不正确！");
        }else{
            cacheService.removeCache(email,REGISTER_TYPE);
        }

        UsersDomain usersDomain = new UsersDomain().build(email,passwordEncoder.encode(password),userName);
        usersService.register(usersDomain);
        return new BaseResponseBody("1","用户["+email+"]注册成功!");

    }


    @RequestMapping(value = "/mail",method = RequestMethod.POST)
    public BaseResponseBody sendMail(@RequestParam(value = "email") String email,
                                     @RequestParam(value = "type") String type){

        if(usersService.findUserByEmail(email)!=null && type.equalsIgnoreCase(REGISTER_TYPE)){
            throw new IllegalArgumentException("用户["+email+"]已注册");
        }
        int times = getTimes(email);
        if(times >= 3){
            throw new IllegalArgumentException("用户["+email+"]只能发送邮件3次/天");
        }
        cacheService.removeCache(email,type);
        String cacheCode = cacheService.getVerificationCode(email,type);
        MimeMessage message = mailService.
                createMessage("用户注册验证","验证码:"+cacheCode,email);
        mailConfig.createSender().send(message);
        incrementalTimes(email,times);
        return new BaseResponseBody("1","验证码发送成功!");
    }

    @RequestMapping(value = "/times",method = RequestMethod.GET)
    public int getTimes(@RequestParam(value = "email") String email){
        return cacheService.times(email,MyDateUtil.today("yyyy-MM-dd"),"times");
    }

    private int incrementalTimes(String email,int currentTimes){
        String today = MyDateUtil.today("yyyy-MM-dd");
        currentTimes += 1;
        return cacheService.times(email,today,"times",currentTimes);

    }


}
