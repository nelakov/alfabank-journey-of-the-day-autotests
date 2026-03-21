package junit.extension;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class VideoAttachExtension implements TestWatcher {

    private static final Path VIDEOS_DIR = Path.of("videos");

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        if (!Files.exists(VIDEOS_DIR)) return;
        try (var files = Files.list(VIDEOS_DIR)) {
            files.filter(f -> f.toString().endsWith(".mp4"))
                    .max(Comparator.comparingLong(f -> f.toFile().lastModified()))
                    .ifPresent(f -> {
                        try {
                            Allure.addAttachment(
                                    context.getDisplayName(),
                                    "video/mp4",
                                    Files.newInputStream(f),
                                    ".mp4");
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to attach video", e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read videos directory", e);
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        if (!Files.exists(VIDEOS_DIR)) return;
        try (var files = Files.list(VIDEOS_DIR)) {
            files.filter(f -> f.toString().endsWith(".mp4"))
                    .max(Comparator.comparingLong(f -> f.toFile().lastModified()))
                    .ifPresent(f -> {
                        try {
                            Files.delete(f);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to delete video", e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read videos directory", e);
        }
    }
}