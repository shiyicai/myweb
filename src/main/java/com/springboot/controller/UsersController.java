package com.springboot.controller;


import com.springboot.mybatis.model.UsersDomain;
import com.springboot.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    @Autowired
    UsersServiceImpl usersService;

    @RequestMapping(method = RequestMethod.GET)
    public UsersDomain findByEmail(@RequestParam(value = "email") String email){
        UsersDomain usersDomain = usersService.findUserByEmail(email);

        return usersDomain;
    }
}
