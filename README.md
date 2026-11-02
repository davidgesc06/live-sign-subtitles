# live-sign-subtitles

Desktop accessibility app for deaf people. It captures the audio playing on the PC (videos, online classes, calls...), transcribes it in real time and shows the text in a small overlay window in a corner of the screen. A later version will also show the translation into Spanish Sign Language (LSE).

> **Status:** early development. Project skeleton only; audio capture and transcription are not implemented yet.

## Tech stack

- Java 21, Maven (through the included Maven Wrapper)
- [Vosk](https://alphacephei.com/vosk/) for offline speech recognition
- Swing for the overlay window

## Architecture

The project follows MVC:

| Package | Responsibility |
|---|---|
| `model.audio` | Captures audio from an input device |
| `model.speech` | Turns audio into text |
| `view` | Overlay window |
| `controller` | Connects model and view; handles start, pause and settings |

## Build and run

Requires JDK 21 or later. Windows:

```bat
.\mvnw.cmd compile
java -cp target/classes io.github.davidgesc06.livesign.App
```

## Roadmap

1. Live subtitles: PC audio → Vosk → overlay window
2. Fingerspelling with the LSE manual alphabet
3. Signs for frequent words, using real LSE videos
4. Polish: settings, demo GIF and downloadable release
