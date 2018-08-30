package com.springboot.controller;


import com.springboot.mybatis.model.UsersDomain;
import com.springboot.response.InsertResponseBody;
import com.springboot.service.impl.UsersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/guests")
public class QuestController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UsersServiceImpl usersService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/register",method = RequestMethod.PUT)
    public InsertResponseBody register(@RequestParam(value = "email") String email,
                                       @RequestParam(value = "password") String password,
                                       @RequestParam(value = "userName") String userName){

        UsersDomain usersDomain = new UsersDomain().build(email,passwordEncoder.encode(password),userName);
        int code = usersService.register(usersDomain);
        return new InsertResponseBody(code,usersDomain.getEmail()).build();

    }

}
