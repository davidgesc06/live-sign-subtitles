package io.github.davidgesc06.livesign;

import java.util.List;

import javax.sound.sampled.Mixer;

import io.github.davidgesc06.livesign.model.audio.AudioCaptureException;
import io.github.davidgesc06.livesign.model.audio.AudioSource;
import io.github.davidgesc06.livesign.model.audio.JavaSoundAudioSource;

/**
 * Application entry point.
 *
 * <p>Without arguments it lists the input devices. With a device number it shows the
 * input level of that device until the process is stopped with Ctrl+C.
 */
public final class App {

    private static final int BUFFER_SIZE = 4096;
    private static final int MAX_BAR_LENGTH = 50;
    private static final int LEVEL_PER_BAR_CHARACTER = 200;

    private App() {
    }

    public static void main(String[] args) throws AudioCaptureException {
        List<Mixer.Info> devices = JavaSoundAudioSource.availableDevices();

        if (args.length == 0) {
            printDevices(devices);
            return;
        }

        Mixer.Info device = devices.get(Integer.parseInt(args[0]));
        System.out.println("Capturing from: " + device.getName());
        showInputLevel(new JavaSoundAudioSource(device));
    }

    private static void printDevices(List<Mixer.Info> devices) {
        System.out.println("Input devices:");
        for (int i = 0; i < devices.size(); i++) {
            System.out.printf("  [%d] %s%n", i, devices.get(i).getName());
        }
    }

    private static void showInputLevel(AudioSource source) throws AudioCaptureException {
        try (source) {
            source.start();
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                int bytesRead = source.read(buffer);
                int level = rootMeanSquare(buffer, bytesRead);
                int barLength = Math.min(level / LEVEL_PER_BAR_CHARACTER, MAX_BAR_LENGTH);
                System.out.printf("\r%5d %-" + MAX_BAR_LENGTH + "s", level, "#".repeat(barLength));
            }
        }
    }

    /**
     * Calculates the average loudness of a block of 16-bit little-endian samples.
     */
    private static int rootMeanSquare(byte[] audio, int length) {
        long sum = 0;
        for (int i = 0; i < length; i += 2) {
            int sample = (audio[i + 1] << 8) | (audio[i] & 0xFF);
            sum += (long) sample * sample;
        }
        return (int) Math.sqrt((double) sum / (length / 2));
    }
}
