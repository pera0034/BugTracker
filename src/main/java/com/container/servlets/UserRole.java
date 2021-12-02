package com.container.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserRole {
    int manager = 1;
    int user_role;

    String user_name;
    String role;
    State logout;
    State login;
    State state;

    HttpServletRequest request;

    public UserRole(HttpServletRequest req){
         logout = new UserLoggedOut(this);
         login = new UserLoggedIn(this);

         HttpSession user_session = req.getSession();
         this.role = user_session.getAttribute("userrole").toString();
         this.user_name = user_session.getAttribute("userFname").toString();
         this.user_role =  Integer.parseInt(role);
         this.request = req;
    }

    public Boolean checkUser(){
        if(user_role == manager){
            return true;
        }
        return false;
    }

    public String helloUser(){
         return "Hello " + this.user_name;
    }

    public void loginUser(){
        state = login;
        state.handleRequest(request);
    }

    public State getUserLoggedIn(){
        return login;
    }

    public void logoutUser(){
        state = logout;
        state.handleRequest(request);
    }

    public State getUserLoggedOut(){
        return logout;
    }

    public void setState(State state){
        this.state = state;
    }
}
