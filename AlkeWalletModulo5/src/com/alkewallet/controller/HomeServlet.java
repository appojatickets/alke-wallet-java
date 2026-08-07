package com.alkewallet.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alkewallet.model.User;

/**
 * Controlador equivalente a HomeActivity del módulo 4.
 * Es la vista principal de la wallet: muestra el saldo disponible
 * (requerimiento central de la consigna del Módulo 5).
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User usuario = (session != null) ? (User) session.getAttribute("usuarioLogueado") : null;

        if (usuario == null) {
            response.sendRedirect("login");
            return;
        }

        request.setAttribute("usuario", usuario);
        request.setAttribute("saldo", usuario.getAccount().getBalance());

        RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
        dispatcher.forward(request, response);
    }
}
