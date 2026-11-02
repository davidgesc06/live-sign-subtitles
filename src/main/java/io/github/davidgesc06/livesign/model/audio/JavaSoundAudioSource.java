package io.github.davidgesc06.livesign.model.audio;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

/**
 * Captures audio from an input device using the Java Sound API.
 */
public final class JavaSoundAudioSource implements AudioSource {

    private static final float SAMPLE_RATE = 16000f;
    private static final int SAMPLE_SIZE_BITS = 16;
    private static final int CHANNELS = 1;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = false;

    private static final AudioFormat FORMAT =
            new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_BITS, CHANNELS, SIGNED, BIG_ENDIAN);

    private final Mixer.Info device;
    private TargetDataLine line;

    public JavaSoundAudioSource(Mixer.Info device) {
        this.device = device;
    }

    /**
     * Returns the input devices that can capture audio in the required format.
     */
    public static List<Mixer.Info> availableDevices() {
        DataLine.Info lineInfo = new DataLine.Info(TargetDataLine.class, FORMAT);
        List<Mixer.Info> devices = new ArrayList<>();
        for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
            if (AudioSystem.getMixer(mixerInfo).isLineSupported(lineInfo)) {
                devices.add(mixerInfo);
            }
        }
        return devices;
    }

    @Override
    public void start() throws AudioCaptureException {
        try {
            line = AudioSystem.getTargetDataLine(FORMAT, device);
            line.open(FORMAT);
            line.start();
        } catch (LineUnavailableException e) {
            throw new AudioCaptureException("Cannot open audio device: " + device.getName(), e);
        }
    }

    @Override
    public int read(byte[] buffer) {
        return line.read(buffer, 0, buffer.length);
    }

    @Override
    public void close() {
        if (line != null) {
            line.stop();
            line.close();
        }
    }
}
