package com.loginapp.Controller;

import com.loginapp.Models.User;
import com.loginapp.Service.LoginService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class MainController {
    LoginService loginService = new LoginService();
    @GetMapping("/user/session")
    public @ResponseBody String getSession(HttpServletRequest request, HttpServletResponse response){
        System.out.println("into get session");
        return loginService.getSession(request, response);
    }

    @PostMapping("/user/register")
    public @ResponseBody String register(@RequestBody User user, HttpServletRequest request){
        try {
            return loginService.registerUser(user, request);
        }catch (Exception e){
            return "false";
        }
    }

    @PostMapping("/user/login")
    public @ResponseBody String loginUser(@RequestBody User user, HttpServletRequest request){
        try{
            return loginService.login(user,request);
        }catch (Exception e){
            return "false";
        }
    }

    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        try{
            loginService.logout(request);
            return "true";
        }catch (Exception e){
            return "false";
        }
    }

}
