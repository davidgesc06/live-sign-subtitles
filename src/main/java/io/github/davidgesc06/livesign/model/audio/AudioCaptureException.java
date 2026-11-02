package io.github.davidgesc06.livesign.model.audio;

/**
 * Signals that an {@link AudioSource} could not start capturing audio.
 */
public class AudioCaptureException extends Exception {

    private static final long serialVersionUID = 1L;

    public AudioCaptureException(String message, Throwable cause) {
        super(message, cause);
    }
}
