# Music Retro App 🎵

Una aplicación moderna de Android desarrollada en **Kotlin** y **Jetpack Compose** que permite a los usuarios explorar artistas musicales, buscar bandas, ver detalles de álbumes y gestionar una lista de artistas favoritos. La aplicación consume datos de la API de **TheAudioDB** y utiliza almacenamiento local para la persistencia de datos.

## 📱 Características Principales

* **Exploración por Categorías:** Navegación sencilla a través de un menú lateral (Drawer) con géneros predefinidos como Thrash Metal, Nu Metal, Rock Clásico, Grunge, entre otros.
* **Buscador de Artistas:** Funcionalidad para buscar artistas específicos mediante su nombre.
* **Detalle de Artista y Álbumes:** Visualización de información detallada de los artistas y listado de sus álbumes.
* **Favoritos (Base de Datos Local):** Capacidad para guardar y eliminar artistas en una lista de "Mis Favoritos" que persiste localmente usando Room.
* **Interfaz Moderna:** UI construida totalmente con Jetpack Compose siguiendo los lineamientos de Material Design 3.

## 🛠️ Stack Tecnológico

El proyecto está construido utilizando las últimas tecnologías y mejores prácticas de desarrollo Android moderno:

* **Lenguaje:** [Kotlin](https://kotlinlang.org/)
* **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material3)
* **Arquitectura:** MVVM (Model-View-ViewModel)
* **Inyección de Dependencias:** [Dagger Hilt](https://dagger.dev/hilt/)
* **Red (Networking):** [Retrofit 2](https://square.github.io/retrofit/) + Gson
* **Base de Datos Local:** [Room Database](https://developer.android.com/training/data-storage/room)
* **Carga de Imágenes:** [Coil](https://coil-kt.github.io/coil/)
* **Navegación:** Navigation Compose
* **Concurrencia:** Coroutines & Flow

## 📂 Estructura del Proyecto

El código está organizado siguiendo una arquitectura limpia y modular:

* `data/`: Contiene la configuración de la API (`ApiService`).
* `model/`: Clases de datos (DTOs) y entidades de Room (`FavoriteArtist`, `AlbumResponse`, etc.).
* `room/`: Configuración de la base de datos local y DAOs (`MusicDatabaseDao`).
* `di/`: Módulos de inyección de dependencias (Hilt).
* `views/`: Pantallas de la interfaz de usuario (`HomeView`, `DetailView`, `SearchArtistView`, etc.).
* `viewModel/`: Lógica de negocio y gestión de estado (`MusicViewModel`).
* `navigation/`: Gestión del grafo de navegación (`NavManager`).
* `utils/`: Constantes y funciones de utilidad (`Constants`).

## 🔧 Configuración y Requisitos

Para ejecutar este proyecto necesitas:

* **Android Studio:** Ladybug o superior (recomendado).
* **JDK:** Versión 17.
* **SDK Mínimo:** 24 (Android 7.0).
* **Target SDK:** 36.

### Instalación

1.  Clona este repositorio:
    ```bash
    git clone [https://github.com/tu-usuario/api-retro.git](https://github.com/tu-usuario/api-retro.git)
    ```
2.  Abre el proyecto en Android Studio.
3.  Sincroniza los archivos Gradle.
4.  Ejecuta la aplicación en un emulador o dispositivo físico.

> **Nota:** La aplicación utiliza la API de `theaudiodb.com` configurada en `Constants.kt`. Asegúrate de tener conexión a internet para cargar los datos.

## 📡 API Reference

La aplicación se comunica con los siguientes endpoints de **TheAudioDB**:

* `search.php?s={query}`: Búsqueda de artistas.
* `album.php?i={id}`: Obtención de álbumes por ID de artista.
* `track.php?m={id}`: Obtención de canciones por ID de álbum.

---
*Desarrollado con ❤️ usando Kotlin y Jetpack Compose.*
