# Alke Wallet - Módulo 5

Dynamic Web Project (Java, patrón MVC) + JSP, según consigna del Módulo 5.

## Cómo importarlo en Eclipse

1. `File > Import > Existing Projects into Workspace` (o `General > Projects from Folder or Archive`).
2. Seleccionar la carpeta `AlkeWalletModulo5`.
3. Click derecho sobre el proyecto > `Properties > Project Facets` y verificar que esté marcado **Dynamic Web Module** y **Java**.
4. Agregar el proyecto a un servidor Tomcat (`Run As > Run on Server`).
5. Abrirá en `http://localhost:8080/AlkeWalletModulo5/` → redirige a `login.jsp`.

Usuario de prueba ya cargado: `demo@alkewallet.com` / `1234`.

## Estructura (patrón MVC)

- **Model** (`src/com/alkewallet/model`): `User`, `Account` — contienen los datos y la lógica de negocio (depósito/retiro, validaciones).
- **DAO** (`src/com/alkewallet/dao`): `UserDAO` — simula la persistencia en memoria (fácil de reemplazar por JDBC más adelante).
- **Controller** (`src/com/alkewallet/controller`): `LoginServlet`, `SignupServlet`, `HomeServlet`, `DepositServlet`, `WithdrawServlet`.
- **View** (`WebContent/*.jsp`): `login.jsp`, `signup.jsp`, `home.jsp`.

## Qué se reutilizó del Módulo 4 (Android)

- Nombre del paquete base: `com.alkewallet`.
- Nombres y flujo de pantallas: Login → Signup → Home, igual que `LoginActivity` → `SignupActivity` → `HomeActivity`.
- Concepto de administración de fondos (saldo, depósito, retiro).

Lo que **no** se pudo reutilizar directamente (por ser tecnologías distintas):
- Las `Activity.java` de Android (usan el ciclo de vida y widgets de Android, no aplican a Servlets).
- Los layouts XML de Android (se rediseñaron como JSP con HTML/CSS).

## Pendiente para completar el entregable

1. Subir esta carpeta a un repositorio de GitHub.
2. Verificar que se pueda ver correctamente la vista de la wallet (`home.jsp`) con saldo, depósito y retiro funcionando.
3. Copiar el link del repo en Moodle.
4. Subir el entregable a tu portafolio.
