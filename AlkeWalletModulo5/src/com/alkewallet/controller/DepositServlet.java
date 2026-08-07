package com.alkewallet.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alkewallet.model.User;

/**
 * Controlador para depósito de fondos.
 * Cubre el requerimiento general: "realizar depósitos de fondos".
 */
@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User usuario = (session != null) ? (User) session.getAttribute("usuarioLogueado") : null;

        if (usuario == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            double monto = Double.parseDouble(request.getParameter("amount"));
            usuario.getAccount().deposit(monto);
            request.getSession().setAttribute("mensaje", "Depósito realizado con éxito");
        } catch (NumberFormatException | IllegalArgumentException e) {
            request.getSession().setAttribute("mensaje", "Error: " + e.getMessage());
        }

        response.sendRedirect("home");
    }
}
