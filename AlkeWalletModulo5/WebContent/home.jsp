<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Alke Wallet - Mi cuenta</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Alke Wallet</h1>
        <p>Hola, <%= ((com.alkewallet.model.User) request.getAttribute("usuario")).getFirstName() %></p>

        <% if (session.getAttribute("mensaje") != null) { %>
            <p class="info"><%= session.getAttribute("mensaje") %></p>
            <% session.removeAttribute("mensaje"); %>
        <% } %>

        <div class="saldo-card">
            <p>Balance Disponible</p>
            <h2>$<%= String.format("%.2f", (Double) request.getAttribute("saldo")) %></h2>
        </div>

        <div class="acciones">
            <div class="accion-box">
                <h3>Depositar fondos</h3>
                <form action="deposit" method="post">
                    <input type="number" step="0.01" min="0.01" name="amount" placeholder="Monto" required>
                    <button type="submit">Depositar</button>
                </form>
            </div>

            <div class="accion-box">
                <h3>Retirar fondos</h3>
                <form action="withdraw" method="post">
                    <input type="number" step="0.01" min="0.01" name="amount" placeholder="Monto" required>
                    <button type="submit">Retirar</button>
                </form>
            </div>
        </div>

        <p><a href="login">Cerrar sesión</a></p>
    </div>
</body>
</html>
