Alke Wallet - Documentación Técnica (Módulo 6)
1. Arquitectura general: MVVM

La aplicación sigue el patrón Model-View-ViewModel (MVVM), con tres capas claramente separadas:

┌─────────────┐      ┌──────────────┐      ┌────────────────────┐
│    VIEW     │◄────►│  VIEWMODEL   │◄────►│   MODEL / DATA      │
│ (Activities)│      │ (LiveData)   │      │ (Repository/Retrofit│
│             │      │              │      │  /Room)             │
└─────────────┘      └──────────────┘      └────────────────────┘
View: LoginActivity, SignupActivity, HomeActivity, ProfileActivity. Solo dibujan la UI y reaccionan a LiveData; no contienen lógica de negocio ni llamadas a red o base de datos.
ViewModel: AuthViewModel, HomeViewModel, ProfileViewModel. Coordinan al WalletRepository, exponen LiveData a la View, y sobreviven a cambios de configuración (ej. rotar pantalla).
Model / Data: Transaction (modelo + entidad Room), UserDto (modelo de red), WalletRepository (decide si los datos vienen de Retrofit o de Room), ApiService/RetrofitClient (red), AppDatabase/ TransactionDao (persistencia local).
2. Comunicación con la API REST (Retrofit)
ApiService define los endpoints (GET/POST para usuarios y transacciones) como una interfaz anotada; Retrofit genera la implementación real.
RetrofitClient configura una única instancia de Retrofit (patrón Singleton) con GsonConverterFactory para mapear JSON ↔ objetos Java, y un HttpLoggingInterceptor para depurar las peticiones.
Todas las llamadas son asíncronas (enqueue), nunca bloquean el hilo principal.
3. Persistencia local (Room)
Transaction está anotada con @Entity, por lo que sirve tanto de modelo de red (Gson) como de tabla local (Room).
TransactionDao expone las operaciones CRUD, devolviendo LiveData para que la Vista se actualice sola cuando cambian los datos, sin necesidad de refrescar manualmente.
AppDatabase configura la base SQLite local (alkewallet_db).
El acceso sin conexión se logra así: HomeViewModel siempre lee el historial desde Room (getLocalTransactions); en paralelo, intenta refrescar desde la API (refreshTransactionsFromApi) y guarda el resultado en Room. Si no hay conexión, la Vista sigue mostrando lo último guardado localmente.
4. Repository: el puente entre Retrofit y Room

WalletRepository es el único punto que el ViewModel consulta. No expone detalles de Retrofit ni Room hacia arriba — solo un callback genérico (RepositoryCallback<T>) con onSuccess/onError. Esto permite:

Cambiar la fuente de datos (API, local, o ambas) sin tocar los ViewModels.
Centralizar el manejo de errores de red en un solo lugar, traduciendo excepciones técnicas (IOException, códigos HTTP) a mensajes legibles para el usuario (requerimiento de manejo de errores de la consigna).
5. Carga de imágenes (Picasso)

ProfileActivity usa Picasso para cargar profileImageUrl (viene del UserDto de la API) en un ImageView, con placeholder() mientras carga y error() si la URL falla — así la UI nunca se rompe por una imagen faltante.

6. Gestión de sesión

Session es una clase estática simple que guarda los datos del usuario logueado (id, email, nombre, foto) mientras la app está abierta. Se llena en AuthViewModel.login() tras una respuesta exitosa de la API, y se consulta desde HomeActivity/ProfileActivity para saber quién está logueado y armar las peticiones (ej. filtrar transacciones por userEmail).

Nota de alcance: la consigna aclara que la autenticación completa "puede ser un alcance futuro" — por eso el login valida usuario/clave contra la API pero no usa tokens JWT ni renovación de sesión; queda identificado como próximo paso natural del proyecto.

7. Manejo de errores
Fallos de red (onFailure de Retrofit): se traducen a mensajes como "No se pudo conectar al servidor. Revisa tu conexión a internet."
Respuestas HTTP no exitosas (response.isSuccessful() == false): se traducen según el contexto (ej. "Email o contraseña incorrectos").
Ninguna excepción técnica ni stacktrace llega a la Vista: todo pasa por RepositoryCallback.onError(String mensaje).
8. Pruebas
Unitarias (src/test): BalanceCalculatorTest prueba la lógica pura de cálculo de saldo sin depender de Android.
Integración - Room (src/androidTest): TransactionDaoTest prueba inserciones, actualizaciones y borrados sobre una base de datos Room en memoria.
Integración - Retrofit (src/test): ApiServiceTest usa MockWebServer para simular respuestas de la API y verificar que Retrofit arme las peticiones y parsee las respuestas correctamente, sin depender de una API real.
9. Estructura de paquetes
com.alkewallet/
├── model/          → Transaction (modelo + entidad Room)
├── data/
│   ├── remote/      → ApiService, RetrofitClient, UserDto
│   ├── local/        → AppDatabase, TransactionDao
│   ├── repository/   → WalletRepository
│   └── Session.java
├── viewmodel/       → AuthViewModel, HomeViewModel, ProfileViewModel
├── view/            → TransactionAdapter
├── util/            → BalanceCalculator
└── *Activity.java   → Login, Signup, Home, Profile, Splash, LoginSignup