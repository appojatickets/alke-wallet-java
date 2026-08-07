<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Alke Wallet - Login</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Alke Wallet</h1>
        <h2>Login</h2>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>

        <form action="login" method="post">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Ingrese su correo" required>

            <label for="password">Contraseña</label>
            <input type="password" id="password" name="password" placeholder="Ingrese su contraseña" required>

            <button type="submit">Login</button>
        </form>

        <p><a href="#">¿Olvidaste tu contraseña?</a></p>
        <p><a href="signup">Crear una nueva cuenta</a></p>
        <p><small>Usuario demo: demo@alkewallet.com / 1234</small></p>
    </div>
</body>
</html>
