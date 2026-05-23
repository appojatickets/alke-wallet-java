# Alke Wallet

Proyecto desarrollado en Java utilizando Programación Orientada a Objetos (POO).

## Descripción

Alke Wallet es una billetera digital que permite:

- Crear una cuenta
- Ver saldo
- Depositar dinero
- Retirar dinero
- Convertir moneda

## Tecnologías utilizadas

- Java
- VS Code
- Programación Orientada a Objetos
- Interfaces

## Funcionalidades

### Administración de fondos
- Depósitos
- Retiros
- Visualización de saldo

### Conversión de moneda
- Conversión de CLP a USD

## Estructura del proyecto

```bash
src/
│
├── Main.java
├── Cuenta.java
├── IConversor.java
└── ConversorMoneda.java

# Evidencias

## Ejecución del programa

![Ejecución Wallet](/modulo%202/img/Screenshot_1.png)

## Menú interactivo

![Menú interactivo](/modulo%202/img/Screenshot_2.png)

## Historial de transacciones

![Historial](/modulo%202/img/Screenshot_3.png)

## Pruebas unitarias

![Tests](/modulo%202/img/Screenshot_4.png)


## Diagrama de clases 

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