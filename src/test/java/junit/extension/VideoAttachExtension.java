package junit.extension;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class VideoAttachExtension implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        try (var files = Files.list(Path.of("videos"))) {
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
        try (var files = Files.list(Path.of("videos"))) {
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