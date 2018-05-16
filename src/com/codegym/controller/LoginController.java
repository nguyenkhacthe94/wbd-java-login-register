package com.codegym.controller;

import com.codegym.model.User;
import com.codegym.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("btn_login") != null){
            String username = request.getParameter("txt_username");
            String password = request.getParameter("txt_password");

            User user = new User();

            user.setUsername(username);
            user.setPassword(password);

            UserService loginDao = new UserService();

            String authorize = loginDao.authorizeLogin(user);

            if (authorize.equals("SUCCESS LOGIN")) {
                HttpSession session = request.getSession();
                session.setAttribute("login", user.getUsername());
                RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("WrongLoginMsg", authorize);
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.include(request, response);
            }
        }
    }
}
