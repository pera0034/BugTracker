package com.container.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserLoggedIn extends State {
    private UserRole user_status;

    public UserLoggedIn(UserRole user_status){
        this.user_status = user_status;
    }

    @Override
    public void handleRequest(HttpServletRequest req){
        HttpSession usersession = req.getSession();
        usersession.setAttribute("userStatus", true);
        user_status.setState(user_status.getUserLoggedIn());
    }
}