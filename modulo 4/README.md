# Alke Wallet Mobile

## Tecnologías

- Android Studio
- Java
- XML
- Android SDK

## Pantallas desarrolladas

- Splash Screen
- Login / Signup
- Login
- Signup
- Home
- Home Empty
- Profile
- Send Money
- Request Money

## Componentes Android utilizados

- Activities
- Intents
- LinearLayout
- ConstraintLayout
- ImageView
- TextView
- EditText
- Button

## Evidencias

![Splash Screen](./modulo%204/img/Screenshot_1.png)

### Sección 3 - Login / Signup Page

#### Requerimiento implementado

Pantalla inicial de acceso que permite al usuario elegir entre iniciar sesión o crear una nueva cuenta.

#### Elementos solicitados por la pauta

- Logo de la aplicación.
- Nombre de la aplicación.
- Botón principal "Crear nueva cuenta".
- Botón secundario "Ya tiene cuenta".
- Diseño con colores corporativos.

#### Objetivo

Servir como punto de entrada para las opciones de autenticación de la billetera virtual.

## Evidencias

![Splash Screen](./modulo%204/img/Screenshot_2.png)

### Sección 5 - Login Page

#### Requerimiento implementado

Pantalla de inicio de sesión para acceder a la billetera virtual.

#### Elementos implementados

- Campo Email.
- Campo Contraseña.
- Texto de recuperación de contraseña.
- Botón Login.
- Botón para crear una nueva cuenta.

#### Componentes Android utilizados

- TextView
- EditText
- Button
- ScrollView

#### Resultado

La aplicación dispone de una pantalla dedicada para la autenticación de usuarios.

### Sección 6 - Navegación Login

#### Requerimiento implementado

Implementación de navegación entre pantallas mediante Intents.

#### Actividades realizadas

- Configuración del botón "Ya tiene cuenta".
- Implementación de Intent explícito.
- Navegación desde LoginSignupActivity hacia LoginActivity.

#### Componentes Android utilizados

- Button
- Intent
- Activity

#### Resultado

El usuario puede acceder a la pantalla de inicio de sesión desde la pantalla inicial de autenticación.

### Sección 8 - Navegación principal

#### Requerimiento implementado

Implementación de navegación entre las principales pantallas de la aplicación mediante Intents.

#### Flujo implementado

Splash → LoginSignup

LoginSignup → Login

LoginSignup → Signup

Login → Home

Home → Send Money

Home → Request Money

#### Componentes Android utilizados

- Activity
- Intent
- Button
- OnClickListener

#### Resultado

El usuario puede recorrer las pantallas principales de la aplicación simulando el flujo de una billetera digital.