package com.alkewallet.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alkewallet.dao.UserDAO;
import com.alkewallet.model.User;

/**
 * Controlador equivalente a SignupActivity del módulo 4.
 * GET  -> muestra el formulario (signup.jsp)
 * POST -> crea el usuario y lo manda a login
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Campos alineados 1 a 1 con activity_signup.xml del Módulo 4:
        // etNombre, etApellido, etEmail, etPassword, etConfirmPassword
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        UserDAO dao = UserDAO.getInstance();

        if (dao.existsByEmail(email)) {
            request.setAttribute("error", "Ya existe una cuenta con ese email");
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Las contraseñas no coinciden");
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
            return;
        }

        User newUser = new User(firstName, lastName, email, password);
        dao.save(newUser);

        response.sendRedirect("login");
    }
}
