# Level-Up-AppMovil

Esta es una aplicación para Android desarrollada con Jetpack Compose que implementa las siguientes funcionalidades:

## Características Principales

1.  **Inicio + Logo**: Una pantalla de bienvenida (`SplashScreen`) que muestra el logo de la aplicación.
2.  **Autenticación**:
    *   **Registro de Usuario** (`RegisterScreen`): Permite a los nuevos usuarios crear una cuenta.
    *   **Inicio de Sesión** (`LoginScreen`): Permite a los usuarios existentes acceder a la aplicación.
3.  **Catálogo de Productos** (`CatalogoScreen`): Muestra una lista de productos disponibles. La arquitectura está preparada para conectar a una fuente de datos real.
4.  **Perfil de Usuario** (`PerfilScreen`): Muestra la información del usuario que ha iniciado sesión y ofrece la opción de "Cerrar Sesión".

## Arquitectura

El proyecto sigue una arquitectura moderna de Android (MVVM) utilizando:
*   **Jetpack Compose** para la interfaz de usuario.
*   **ViewModels** para separar la lógica de negocio de la UI.
*   **StateFlow** para un flujo de datos reactivo y unidireccional.
*   **Patrón Repositorio** para abstraer las fuentes de datos.
*   **Navegación de Compose** para gestionar el flujo entre pantallas.