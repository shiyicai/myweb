package com.springboot.controller;



import com.springboot.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    @Autowired
    UsersServiceImpl usersService;

    @RequestMapping(method = RequestMethod.GET,value = "/current")
    public Authentication currentLoginUser(){
       return SecurityContextHolder.getContext().getAuthentication();
    }
}
