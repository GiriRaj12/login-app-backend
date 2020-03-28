package com.loginapp.Service;

import com.google.gson.Gson;
import com.loginapp.Models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginService {
    File file = new File("/Users/giri/Documents/Web/user.txt");
    public String getSession(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        Map<String, Object> responseMap = new HashMap<>();
        try {
          if(session != null){
              System.out.println("Into true");
              String userName = request.getSession(false).getAttribute("username").toString();
              responseMap.put("isSession", true);
              responseMap.put("userName",userName);
          }else
              responseMap.put("isSession", false);
        }catch (Exception e){
            System.out.println("error");
            responseMap.put("isSession", false);
        }
        return new Gson().toJson(responseMap);
    }

    public String registerUser(User user, HttpServletRequest request) throws IOException {
        if(!file.exists())
            file.createNewFile();

        if(!checkUser(file, user)) {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(String.join(":", user.getUserName(), user.getPassoword()));
            fileWriter.write(System.getProperty("line.separator"));
            fileWriter.close();
            createSession(user,request);
            return "true";
        }else
            return "false";
    }

    public String login(User user, HttpServletRequest request) throws IOException {
        if(!file.exists())
            file.createNewFile();
        if(checkUser(file, user)) {
            createSession(user,request);
            return "true";
        }
        else
            return "false";
    }

    public void logout(HttpServletRequest request){
        request.getSession(false).invalidate();
    }

    public void createSession(User user, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null) {
            session = request.getSession(true);
            session.setAttribute("username", user.getUserName());
        }
    }

    private boolean checkUser(File file, User user) throws FileNotFoundException {
        Scanner lineReader = new Scanner(file);
        while(lineReader.hasNextLine()){
            String [] users = lineReader.nextLine().split(":");
            String userName = users[0];
            String password = users[1];
            if(user.getUserName().equals(userName) && user.getPassoword().equals(password)){
                System.out.println(userName+","+password);
                return true;
            }
        }
        return false;
    }
}
