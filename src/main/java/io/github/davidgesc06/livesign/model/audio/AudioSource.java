package io.github.davidgesc06.livesign.model.audio;

/**
 * A stream of raw audio: 16 kHz, 16-bit, mono, signed little-endian PCM.
 */
public interface AudioSource extends AutoCloseable {

    /**
     * Starts capturing audio.
     *
     * @throws AudioCaptureException if the device cannot be opened
     */
    void start() throws AudioCaptureException;

    /**
     * Blocks until audio is available and copies it into the buffer.
     *
     * @param buffer destination for the audio; its length must be even, because each sample takes two bytes
     * @return the number of bytes read
     */
    int read(byte[] buffer);

    @Override
    void close();
}
