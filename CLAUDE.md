# live-sign-subtitles

Proyecto personal de David (3.º de Ingeniería Informática, UCM) para su portfolio de GitHub y su CV. Tendrá que defender todo el código en entrevistas, así que lo prioritario es que lo entienda.

## Objetivo

Aplicación de escritorio de accesibilidad para personas sordas: captura el audio que suena en el PC, lo transcribe en tiempo real y lo muestra en una pequeña ventana superpuesta en una esquina de la pantalla. Más adelante mostrará también la traducción a lengua de signos española (LSE).

## Forma de trabajar

- Antes de implementar algo nuevo: explicar brevemente el enfoque y por qué, y esperar su OK.
- Pasos pequeños y comprobables; nunca el proyecto entero de golpe.
- Algunas partes las escribe David: cuando lo pida, revisarlas y explicar los errores en lugar de reescribirlas.
- Recordarle hacer commits pequeños y frecuentes (mensajes en inglés, Conventional Commits). Explicar cada comando de Git la primera vez que aparezca.
- Hablar con él en español. Código, nombres y comentarios en inglés.

## Nivel de David

- Se maneja bien con Java (POO, herencia, polimorfismo, Swing) y C++.
- Ha hecho proyectos MVC / por capas (un casino con Java y MySQL).
- No ha usado Maven a fondo ni librerías de reconocimiento de voz.

## Stack y entorno

- Windows; David usa Eclipse (Claude trabaja desde la terminal y David refresca el proyecto).
- Ruta del proyecto: `C:\Users\david\OneDrive\Escritorio\Sordos\live-sign-subtitles` (decisión de David, dentro de OneDrive).
- JDK Temurin 21 en `C:\Program Files\Eclipse Adoptium\jdk-21.0.13.9-hotspot`. En el PATH puede seguir apareciendo antes Java 8: comprobar `java -version`.
- Maven mediante Maven Wrapper (`mvnw.cmd`).
- Reconocimiento de voz offline: Vosk (`com.alphacephei:vosk:0.3.45`, última versión en Maven Central) con `vosk-model-small-es-0.42` en `models/`, excluido de Git.
- Interfaz con Swing: ventana sin bordes, siempre visible y semitransparente.
- `groupId` y paquete base: `io.github.davidgesc06` / `io.github.davidgesc06.livesign`. Usuario de GitHub: `davidgesc06`.

## Captura de audio

Mezcla estéreo no aparece entre los dispositivos del PC de David. Plan:

1. Probar la cadena completa con el micrófono Realtek.
2. Pasar a VB-Cable como dispositivo de entrada.
3. En la fase 4, valorar WASAPI loopback con código nativo para evitar el driver virtual.

## Arquitectura (MVC)

- **Modelo:** captura de audio (`model.audio`), reconocimiento de voz (`model.speech`) y, más adelante, traducción de texto a secuencia de signos.
- **Vista** (`view`): ventana superpuesta con subtítulos y, más adelante, el signo correspondiente.
- **Controlador** (`controller`): conecta modelo y vista; gestiona iniciar, pausar y configuración.
- Interfaces previstas: `AudioSource` y `SpeechRecognizer`, para poder cambiar de fuente de audio o sustituir Vosk.

## Fases

1. Subtítulos en directo: audio del PC → Vosk → ventana superpuesta. ← ACTUAL
2. Deletreo con el alfabeto dactilológico de la LSE (imágenes estáticas de fuente fiable y licencia compatible).
3. Signos de palabras frecuentes con vídeos reales de LSE (con licencia adecuada).
4. Pulido: configuración (posición, tamaño, fuente), README completo con GIF de demostración y release descargable.

## Estado actual

- Hecho: esqueleto del proyecto Maven con paquetes MVC, Maven Wrapper, `.gitignore`, README y este archivo. Compila con `mvnw.cmd`. Publicado en https://github.com/davidgesc06/live-sign-subtitles (repositorio público, rama `main`).
- Git en este PC: cuenta `davidgesc06`, indicada en la URL del remoto porque el gestor de credenciales también guarda `davgon29`.
- Siguiente: primer hito técnico, capturar audio de un dispositivo de entrada y mostrar la transcripción en la consola. Diseño propuesto a David, pendiente de su OK.
