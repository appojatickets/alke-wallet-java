````md
# Alke Wallet

Proyecto desarrollado en Java utilizando Programación Orientada a Objetos (POO) y fundamentos de Bases de Datos Relacionales mediante SQL.

---

# Descripción

Alke Wallet es una billetera digital que permite:

- Crear cuentas de usuario
- Visualizar saldo
- Depositar dinero
- Retirar dinero
- Convertir moneda
- Registrar transacciones
- Gestionar usuarios y monedas mediante SQL
- Consultar historial de transacciones

Este proyecto integra conceptos de:

- Programación Orientada a Objetos (POO)
- Bases de Datos Relacionales
- SQL (DDL y DML)
- Relaciones entre tablas
- Integridad referencial
- Consultas JOIN

---

# Tecnologías utilizadas

- Java
- SQL
- SQLite Online (modo MS SQL)
- VS Code
- Programación Orientada a Objetos
- Interfaces
- Bases de Datos Relacionales

---

# Funcionalidades

## Administración de fondos

- Depósitos
- Retiros
- Visualización de saldo
- Historial de movimientos

## Conversión de moneda

- Conversión de CLP a USD

## Base de Datos Relacional

- Creación de tablas
- Relaciones entre entidades
- Inserción de datos
- Consultas SQL
- INNER JOIN
- UPDATE
- DELETE

---

# Modelo Relacional

El sistema utiliza las siguientes entidades:

## Usuario

Representa a cada usuario de la wallet.

### Atributos

- user_id
- nombre
- correo
- contraseña
- saldo

---

## Moneda

Representa las monedas disponibles en el sistema.

### Atributos

- currency_id
- currency_name
- currency_symbol

---

## Transacción

Representa transferencias entre usuarios.

### Atributos

- transaction_id
- sender_user_id
- receiver_user_id
- importe
- transaction_date

---

# Script SQL

```sql
-- =========================================
-- ALKE WALLET DATABASE
-- =========================================

-- =========================================
-- TABLA USUARIO
-- =========================================

CREATE TABLE usuario (

    user_id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(100) NOT NULL,
    saldo DECIMAL(10,2) NOT NULL

);

-- =========================================
-- TABLA MONEDA
-- =========================================

CREATE TABLE moneda (

    currency_id INTEGER PRIMARY KEY,
    currency_name VARCHAR(50) NOT NULL,
    currency_symbol VARCHAR(10) NOT NULL

);

-- =========================================
-- TABLA TRANSACCION
-- =========================================

CREATE TABLE transaccion (

    transaction_id INTEGER PRIMARY KEY,
    sender_user_id INTEGER NOT NULL,
    receiver_user_id INTEGER NOT NULL,
    importe DECIMAL(10,2) NOT NULL,
    transaction_date DATETIME NOT NULL,

    FOREIGN KEY (sender_user_id) REFERENCES usuario(user_id),
    FOREIGN KEY (receiver_user_id) REFERENCES usuario(user_id)

);

-- =========================================
-- INSERT USUARIOS
-- =========================================

INSERT INTO usuario (user_id, nombre, correo, contraseña, saldo)
VALUES

(1, 'Matias', 'matias@email.com', '1234', 100000),

(2, 'Carlos', 'carlos@email.com', 'abcd', 50000),

(3, 'Fernanda', 'fernanda@email.com', 'xyz789', 75000);

-- =========================================
-- INSERT MONEDAS
-- =========================================

INSERT INTO moneda (currency_id, currency_name, currency_symbol)
VALUES

(1, 'Peso Chileno', 'CLP'),

(2, 'Dólar Estadounidense', 'USD'),

(3, 'Euro', 'EUR');

-- =========================================
-- INSERT TRANSACCIONES
-- =========================================

INSERT INTO transaccion
(transaction_id, sender_user_id, receiver_user_id, importe, transaction_date)

VALUES

(1, 1, 2, 15000, '2026-05-23 10:00:00'),

(2, 2, 3, 5000, '2026-05-23 11:30:00'),

(3, 1, 3, 25000, '2026-05-23 12:15:00');

-- =========================================
-- CONSULTAS SQL
-- =========================================

SELECT * FROM usuario;

SELECT * FROM moneda;

SELECT * FROM transaccion;

-- =========================================
-- INNER JOIN
-- =========================================

SELECT
    t.transaction_id,
    u1.nombre AS emisor,
    u2.nombre AS receptor,
    t.importe,
    t.transaction_date

FROM transaccion t

INNER JOIN usuario u1
ON t.sender_user_id = u1.user_id

INNER JOIN usuario u2
ON t.receiver_user_id = u2.user_id;

-- =========================================
-- UPDATE
-- =========================================

UPDATE usuario
SET correo = 'nuevo_correo@email.com'
WHERE user_id = 1;

-- =========================================
-- DELETE
-- =========================================

DELETE FROM transaccion
WHERE transaction_id = 2;
```

---

# Estructura del proyecto

```bash
src/
│
├── Main.java
├── Cuenta.java
├── CuentaTest.java
├── IConversor.java
└── ConversorMoneda.java
```

---

# Evidencias SQL

## Consulta de usuarios

![Consulta Usuarios](/modulo%203/img/Screenshot_1.png)

---

## Consulta de monedas

![Consulta Monedas]((/modulo%203/img/Screenshot_2.png))

---

## Consulta de transacciones

![Consulta Transacciones](/modulo%203/img/Screenshot_3.png)

---

## INNER JOIN entre tablas

![INNER JOIN](/modulo%203/img/Screenshot_4.png)

---

# Funcionalidades Java

## Ejecución del programa

- Menú interactivo
- Depósitos
- Retiros
- Conversión de moneda
- Historial de movimientos
- Pruebas unitarias

---

# Diagrama de clases

```text
+----------------+
|    Cuenta      |
+----------------+
| - saldo        |
| - historial    |
+----------------+
| +depositar()   |
| +retirar()     |
| +getSaldo()    |
| +mostrarHistorial() |
+----------------+

        |
        |
        v

+------------------------+
|      IConversor        |
+------------------------+
| +convertir()           |
+------------------------+

        ^
        |
        |

+------------------------+
|   ConversorMoneda      |
+------------------------+
| - tasaCambio           |
+------------------------+
| +convertir()           |
+------------------------+

        |
        |
        v

+------------------------+
|         Main           |
+------------------------+
| +main()                |
+------------------------+
```

---

# Objetivos cumplidos

- Diseño de base de datos relacional
- Creación de entidades
- Integridad referencial
- Uso de DDL y DML
- Consultas SQL
- Relaciones entre tablas
- INNER JOIN
- Manipulación de datos
- Aplicación de Programación Orientada a Objetos
- Desarrollo de wallet funcional
````
