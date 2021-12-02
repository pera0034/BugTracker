package com.container.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserRole {
    int manager = 1;
    int user_role;
    String user_name;
    String role;
    Boolean status = false;

    public UserRole(HttpServletRequest req){
         HttpSession user_session = req.getSession();
         this.role = user_session.getAttribute("userrole").toString();
         this.user_name = user_session.getAttribute("userFname").toString();
         this.status = Boolean.valueOf(user_session.getAttribute("userStatus").toString());
         this.user_role =  Integer.parseInt(role);
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

    public void checkLoginStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(status){
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
