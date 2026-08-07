<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Alke Wallet - Registro</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Alke Wallet</h1>
        <h2>Crear cuenta</h2>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>

        <form action="signup" method="post">
            <label for="firstName">Nombre</label>
            <input type="text" id="firstName" name="firstName" placeholder="Nombre" required>

            <label for="lastName">Apellido</label>
            <input type="text" id="lastName" name="lastName" placeholder="Apellido" required>

            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Correo electrónico" required>

            <label for="password">Contraseña</label>
            <input type="password" id="password" name="password" placeholder="Contraseña" required>

            <label for="confirmPassword">Confirmar contraseña</label>
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirmar contraseña" required>

            <button type="submit">Crear Cuenta</button>
        </form>

        <p>¿Ya tienes cuenta? <a href="login">Inicia sesión</a></p>
    </div>
</body>
</html>
