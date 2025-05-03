<p align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/6/62/Logo_de_la_Universidad_El_Bosque.svg" width="200" height="200"/>
</p>
<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&height=200&section=header&text=ApoloBOT&fontSize=80&fontAlignY=35&animation=twinkling&fontColor=gradient" />
</p>
<p align="center">
  <img src="https://img.shields.io/github/issues/roca12/ApoloBOT?style=for-the-badge&color=blue" />
  <img src="https://img.shields.io/github/issues-pr/roca12/ApoloBOT?style=for-the-badge&color=purple" />
  <p align="center">
  <img src="https://img.shields.io/badge/Contributors-12-informational?logo=java&logoColor=white&color=green&style=for-the-badge" />
  <img src="https://img.shields.io/github/repo-size/roca12/ApoloBOT?style=for-the-badge&color=orange" />
  <img src="https://img.shields.io/github/commit-activity/w/roca12/ApoloBOT?style=for-the-badge" />
  <img src="https://img.shields.io/github/last-commit/roca12/ApoloBOT?style=for-the-badge&color=yellow" />
    </p>
  <p align="center">
  <img src="https://img.shields.io/badge/Java-17-informational?logo=java&logoColor=white&color=red&style=for-the-badge" />
</p>

<p align="center">
  <a href="https://github.com/corwindev/Discord-Bot">
    <img src="https://cdn.prod.website-files.com/6257adef93867e50d84d30e2/636e0b5061df29d55a92d945_full_logo_blurple_RGB.svg" alt="Pbot-plus" width="200" height="150">
  </a>

  <h3 align="center">ApoloBot</h3>

  <p align="center">
   Apolo es un bot de Discord diseñado para hacer tu servidor más eficiente y organizado. Ofrece herramientas avanzadas para coaches y administradores, como gestión de eventos, traducción de texto, y más.
  </p>
</p>

## <img src="https://cdn.discordapp.com/emojis/852881450667081728.gif" width="25px" height="25px">》Características principales
- :globe_with_meridians: Traducción de texto desde cualquier idioma al español.
- :calendar: Gestión y organización de eventos y entrenamientos.
- :white_check_mark: Diagnósticos para verificar conectividad del bot.
- :wrench: Herramientas avanzadas exclusivas para coaches.

[![Readme Card](https://github-readme-stats.vercel.app/api/pin/?username=roca12&repo=ApoloBOT&theme=tokyonight)](https://github.com/roca12/ApoloBOT)

### <img src="https://cdn.discordapp.com/emojis/1055803759831294013.png" width="20px" height="20px"> 》Moderación de Lenguaje 
> Al utilizar el comando de traducción, cualquier contenido con lenguaje ofensivo será censurado antes de mostrar el resultado traducido. Esta funcionalidad ayuda a fomentar una comunicación segura y profesional para todos los miembros del servidor.

## <img src="https://cdn.discordapp.com/emojis/1009754836314628146.gif" width="20px" height="20px">》Contenido
- :information_source: [Introducción](#introducción)
- :pencil: [Lista de Comandos](#lista-de-comandos)
- :computer: [Tecnologías Utilizadas](#tecnologías-utilizadas)
- :file_folder: [Estructura del Proyecto](#estructura-del-proyecto)
- :page_with_curl: [Documentación](#documentación)
- :clipboard: [Cómo Crear un Bot](#cómo-crear-un-bot)
- :turtle: [Contribuidores](#contribuidores)
- :uk: [English Documentation](#english-documentation)

## <img src="https://cdn.discordapp.com/emojis/1028680849195020308.png" width="20px" height="20px">》Introducción
Apolo es un asistente diseñado para simplificar las tareas de administración en servidores de Discord. Su diseño intuitivo y su capacidad de respuesta en tiempo real lo convierten en el compañero perfecto para comunidades que buscan:

- Organizar sus actividades de manera eficiente.
- Fomentar la colaboración entre miembros.
- Proporcionar herramientas útiles como traducción y estadísticas de usuarios.

## <img src="https://cdn.discordapp.com/emojis/814216203466965052.png" width="25px" height="25px">》Lista de Comandos
<table>
  <thead>
    <tr>
      <th>Comando</th>
      <th>Descripción</th>
      <th>Permisos Requeridos</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>/ping</code></td>
      <td>Retorna un saludo conocido.</td>
      <td>Ninguno</td>
    </tr>
    <tr>
      <td><code>/test</code></td>
      <td>Verifica la conectividad de todos los sistemas.</td>
      <td>Coach</td>
    </tr>
    <tr>
      <td><code>/ayuda</code></td>
      <td>Muestra la lista completa de comandos y quiénes pueden usarlos.</td>
      <td>Ninguno</td>
    </tr>
    <tr>
      <td><code>/evento</code></td>
      <td>Administra eventos futuros.</td>
      <td>Coach</td>
    </tr>
    <tr>
      <td><code>/entrenamiento</code></td>
      <td>Anuncia, actualiza o cancela entrenamientos.</td>
      <td>Coach</td>
    </tr>
    <tr>
      <td><code>/numerousuarios</code></td>
      <td>Cuenta la cantidad de usuarios existentes en el servidor.</td>
      <td>Coach</td>
    </tr>
    <tr>
      <td><code>/traducir</code></td>
      <td>Traduce un texto desde cualquier idioma al español, censurando palabras ofensivas.</td>
      <td>Ninguno</td>
    </tr>
  </tbody>
</table>

## <img src="https://cdn.discordapp.com/emojis/852881450667081728.gif" width="25px" height="25px">》Tecnologías Utilizadas
ApoloBOT ha sido desarrollado utilizando las siguientes tecnologías:

- Lenguaje de Programación: Java 17
- Frameworks y Librerías:
  - Javacord: Una interfaz para interactuar con la API de Discord en Java.
  - Spring Boot: Framework para facilitar la configuración y despliegue de aplicaciones Java.
  - Google Cloud Translate: API para servicios de traducción.
- Base de Datos:
  - MongoDB: Base de datos NoSQL para almacenar información de entrenamientos y eventos.
- Herramientas de Construcción:
  - Maven: Utilizado para la gestión de dependencias y construcción del proyecto.
- Control de Versiones:
  - Git: Para el seguimiento de cambios y colaboración en el código fuente.
- Plataforma de Desarrollo:
  - GitHub: Hospedaje del repositorio y gestión de versiones.
- Herramientas de Organización:
  - Trello: Utilizado para la planificación y el seguimiento del desarrollo del proyecto.

## <img src="https://cdn.discordapp.com/emojis/1009754836314628146.gif" width="20px" height="20px">》Estructura del Proyecto
El proyecto está organizado en una estructura de paquetes típica de Spring Boot:

```
src/main/java/com/roca12/apolobot/
├── ApolobotApplication.java       # Punto de entrada de la aplicación
├── controller/                    # Controladores
│   ├── AplMain.java               # Controlador principal del bot
│   └── handler/                   # Manejadores de eventos
│       ├── LessonMessageSender.java  # Envío de mensajes programados
│       ├── MessageListener.java      # Escucha de mensajes
│       ├── SlashBuilder.java         # Construcción de comandos slash
│       ├── SlashListener.java        # Escucha de comandos slash
│       └── TrainingAnnouncer.java    # Anunciador de entrenamientos
├── model/                         # Modelos de datos
│   ├── Embed.java                 # Modelo para mensajes enriquecidos
│   ├── ReRunApolo.java            # Modelo para reinicio del bot
│   └── Training.java              # Modelo para entrenamientos
├── repository/                    # Repositorios para acceso a datos
│   ├── ReRunApoloRepository.java  # Repositorio para ReRunApolo
│   └── TrainingRepository.java    # Repositorio para Training
├── service/                       # Servicios de negocio
│   ├── GroceryItemService.java    # Servicio para items
│   ├── ReRunApoloService.java     # Servicio para reinicio del bot
│   └── TrainingService.java       # Servicio para entrenamientos
└── util/                          # Utilidades
    ├── Encryptor.java             # Utilidad para encriptación
    ├── ILoveResponses.java        # Respuestas para comando teamo
    ├── PDFProcessor.java          # Procesador de PDFs
    ├── TranslationUtil.java       # Utilidad para traducción
    └── TranslateDocumentation.java # Utilidad para traducir documentación
```

## <img src="https://cdn.discordapp.com/emojis/814216203466965052.png" width="25px" height="25px">》Documentación
El proyecto cuenta con documentación completa en forma de comentarios Javadoc en todos los archivos principales. La documentación está disponible en dos idiomas:

- **Inglés**: Todos los archivos principales tienen comentarios Javadoc en inglés que explican el propósito de cada clase, método y campo.
- **Español**: Se han creado versiones traducidas de los archivos con sufijo `_es.java` que contienen la misma documentación pero en español.

### Utilidades de Documentación

El proyecto incluye dos utilidades para la gestión de la documentación:

1. **TranslationUtil.java**: Utilidad que usa la API de Google Cloud Translate para traducir texto de inglés a español.
2. **TranslateDocumentation.java**: Utilidad que procesa archivos Java, identifica los comentarios Javadoc, los traduce usando TranslationUtil, y genera nuevos archivos con la documentación traducida.

Para ejecutar la traducción de la documentación:

```bash
# Navegar al directorio del proyecto
cd ApoloBOT

# Compilar el proyecto
mvn compile

# Ejecutar la utilidad de traducción
java -cp target/classes com.roca12.apolobot.util.TranslateDocumentation
```

## <img src="https://cdn.discordapp.com/emojis/1009754836314628146.gif" width="20px" height="20px">》Cómo Crear un Bot
1. Crear una Aplicación en Discord  
   - Accede al [Portal de Desarrolladores de Discord](https://discord.com/developers/applications).  
   - Haz clic en **New Application** y asigna un nombre a tu aplicación.  
   - En la sección **Bot**, selecciona **Add Bot** para convertir tu aplicación en un bot.

2. Obtener el Token del Bot  
   - En la sección **Bot** de tu aplicación, encontrarás el **Token**.  
     Este es un identificador único que permite a tu código interactuar con Discord.  
     **¡Guárdalo de manera segura y no lo compartas!**

3. Invitar al Bot a tu Servidor  
   - Ve a la sección **OAuth2 > URL Generator** en el Portal de Desarrolladores.  
   - Configura lo siguiente:  
     - En **Scopes**, selecciona **bot**.  
     - En **Bot Permissions**, elige los permisos deseados.  
   - Copia la URL generada y pégala en tu navegador.  
   - Selecciona el servidor donde quieres añadir el bot y autoriza su acceso.

4. Configurar el Entorno de Desarrollo  
   - **Instalar Java**:  
     Asegúrate de tener Java instalado en tu sistema. Verifica la instalación ejecutando:  
     ```bash
     java -version
     ```  
   - **Configurar Maven**:  
     Si Maven no está instalado, descárgalo desde [Maven Downloads](https://maven.apache.org/download.cgi) y configúralo en tu sistema.

5. Iniciar un Proyecto con Javacord  
   - Crea un nuevo proyecto en tu IDE favorito.  
   - Añade la siguiente dependencia de `javacord` en el archivo `pom.xml`:  
     ```xml
     <dependency>
         <groupId>org.javacord</groupId>
         <artifactId>javacord</artifactId>
         <version>3.8.0</version>
     </dependency>
     ```

6. Configurar el Token del Bot en el Proyecto  
   - Busca en el código del proyecto dónde se inicializa el bot.  
     Generalmente, encontrarás una línea como esta:  
     ```java
     new DiscordApiBuilder().setToken("YOUR_TOKEN_HERE").login().join();
     ```  
   - Sustituye `"YOUR_TOKEN_HERE"` con el token que obtuviste en el paso 2.

7. Invitar al Bot a tu Servidor  
   - Ve a la sección **OAuth2 > URL Generator** en el Portal de Desarrolladores.  
   - Configura lo siguiente:  
     - En **Scopes**, selecciona **bot**.  
     - En **Bot Permissions**, elige los permisos deseados.  
   - Copia la URL generada y pégala en tu navegador.  
   - Selecciona el servidor donde quieres añadir el bot y autoriza su acceso.

8. Verificar el Funcionamiento del Bot  
   - Una vez que el bot esté en línea y añadido a tu servidor, pruébalo enviando algunos comandos básicos.  
   - Si necesitas personalizar comandos o funcionalidades, revisa el código fuente del proyecto.


## <img src="https://cdn.discordapp.com/emojis/1028680849195020308.png" width="25px" height="25px">》Contribuidores

### Diego Rodriguez
![roca12's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=roca12&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![roca12's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=roca12&theme=dracula)

---

### Juan Diego Gonzalez
![luxuan205's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=luxuan205&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![luxuan205's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=luxuan205&theme=dracula)

---

### Andres Espitia
![anfeespi's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=anfeespi&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![anfeespi's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=anfeespi&theme=dracula)

---

### Juan Pablo Urrego
![MJPU17's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=MJPU17&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![MJPU17's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=MJPU17&theme=dracula)

---

### Natalia Giraldo
![Nats62's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=Nats62&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![Nats62's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=Nats62&theme=dracula)

---

### Edwin Villaraga
![edanv1401's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=edanv1401&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![edanv1401's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=edanv1401&theme=dracula)

---

### Santiago Rueda
![HzKing8's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=HzKing8&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![HzKing8's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=HzKing8&theme=dracula)

---

### Andres Guerrero
![edanv1401's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=edanv1401&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![edanv1401's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=edanv1401&theme=dracula)

---

### Sebastian Carroz
![scarroz's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=scarroz&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![scarroz's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=scarroz&theme=dracula)

---

### Jeisson Parra
![sson1590's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=sson1590&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![sson1590's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=sson1590&theme=dracula)

---

### Joann Zamudio
![edanv1401's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=edanv1401&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![edanv1401's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=edanv1401&theme=dracula)

---

### Cristhian Diaz
![camid2512's Top Languages](https://github-readme-stats.vercel.app/api/top-langs/?username=camid2512&theme=vue-dark&show_icons=true&hide_border=true&layout=compact)
![camid2512's github activity graph](https://github-readme-activity-graph.vercel.app/graph?username=camid2512&theme=dracula)

---

## <img src="https://cdn.discordapp.com/emojis/1028680849195020308.png" width="25px" height="25px">》English Documentation

### Introduction
ApoloBOT is a Discord bot designed to make your server more efficient and organized. It offers advanced tools for coaches and administrators, such as event management, text translation, and more.

### Main Features
- :globe_with_meridians: Translation of text from any language to Spanish.
- :calendar: Management and organization of events and training sessions.
- :white_check_mark: Diagnostics to verify bot connectivity.
- :wrench: Advanced tools exclusively for coaches.

### Commands
| Command | Description | Required Permissions |
|---------|-------------|---------------------|
| `/ping` | Returns a known greeting. | None |
| `/test` | Verifies the connectivity of all systems. | Coach |
| `/ayuda` | Shows the complete list of commands and who can use them. | None |
| `/evento` | Manages future events. | Coach |
| `/entrenamiento` | Announces, updates, or cancels training sessions. | Coach |
| `/numerousuarios` | Counts the number of users in the server. | Coach |
| `/traducir` | Translates text from any language to Spanish, censoring offensive words. | None |

### Project Structure
The project is organized in a typical Spring Boot package structure:

```
src/main/java/com/roca12/apolobot/
├── ApolobotApplication.java       # Application entry point
├── controller/                    # Controllers
│   ├── AplMain.java               # Main bot controller
│   └── handler/                   # Event handlers
│       ├── LessonMessageSender.java  # Scheduled message sending
│       ├── MessageListener.java      # Message listening
│       ├── SlashBuilder.java         # Slash command building
│       ├── SlashListener.java        # Slash command listening
│       └── TrainingAnnouncer.java    # Training announcer
├── model/                         # Data models
│   ├── Embed.java                 # Model for rich messages
│   ├── ReRunApolo.java            # Model for bot restart
│   └── Training.java              # Model for training sessions
├── repository/                    # Repositories for data access
│   ├── ReRunApoloRepository.java  # Repository for ReRunApolo
│   └── TrainingRepository.java    # Repository for Training
├── service/                       # Business services
│   ├── GroceryItemService.java    # Service for items
│   ├── ReRunApoloService.java     # Service for bot restart
│   └── TrainingService.java       # Service for training sessions
└── util/                          # Utilities
    ├── Encryptor.java             # Encryption utility
    ├── ILoveResponses.java        # Responses for teamo command
    ├── PDFProcessor.java          # PDF processor
    ├── TranslationUtil.java       # Translation utility
    └── TranslateDocumentation.java # Documentation translation utility
```

### Documentation
The project has complete documentation in the form of Javadoc comments in all main files. The documentation is available in two languages:

- **English**: All main files have Javadoc comments in English that explain the purpose of each class, method, and field.
- **Spanish**: Translated versions of the files with the suffix `_es.java` have been created that contain the same documentation but in Spanish.

### Documentation Utilities
The project includes two utilities for documentation management:

1. **TranslationUtil.java**: Utility that uses the Google Cloud Translate API to translate text from English to Spanish.
2. **TranslateDocumentation.java**: Utility that processes Java files, identifies Javadoc comments, translates them using TranslationUtil, and generates new files with the translated documentation.

To run the documentation translation:

```bash
# Navigate to the project directory
cd ApoloBOT

# Compile the project
mvn compile

# Run the translation utility
java -cp target/classes com.roca12.apolobot.util.TranslateDocumentation
```

### Technologies Used
ApoloBOT has been developed using the following technologies:

- Programming Language: Java 17
- Frameworks and Libraries:
  - Javacord: An interface to interact with the Discord API in Java.
  - Spring Boot: Framework to facilitate the configuration and deployment of Java applications.
  - Google Cloud Translate: API for translation services.
- Database:
  - MongoDB: NoSQL database to store information about training sessions and events.
- Build Tools:
  - Maven: Used for dependency management and project building.
- Version Control:
  - Git: For tracking changes and collaboration in the source code.
- Development Platform:
  - GitHub: Repository hosting and version management.
- Organization Tools:
  - Trello: Used for planning and tracking project development.
