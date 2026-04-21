package com.safenepal.user.controller;

import com.safenepal.user.model.User;
import com.safenepal.user.model.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("pages/login.jsp").forward(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try{
            UserDAO userDAO = new UserDAO();
            User userobj = userDAO.loginUser(email,password);
            if(userobj!=null){
                HttpSession session = req.getSession();
                session.setAttribute("user",userobj);
                resp.sendRedirect("/dashboard");
            }else{
                req.setAttribute("error", "Incorrect username or password");
                req.getRequestDispatcher("pages/login.jsp").forward(req, resp);
            }
        }catch(Exception e){
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("pages/login.jsp").forward(req, resp);
        }
    }

}
