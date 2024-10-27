package dev.ultreon.mods.lib.common;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @deprecated Mods shouldn't download stuff unless explicitly needed.
 * So it's marked as deprecated to make sure devs aren't randomly using this.
 */
@Deprecated
public interface IDownloader {
    void downloadSync() throws IOException, InterruptedException;

    default CompletableFuture<Void> downloadAsync() {
        return CompletableFuture.runAsync(() -> {
            try {
                this.downloadSync();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    int getBlockSize();

    long getBytesDownloaded();

    /**
     * The download percentage. Range 0,100.
     * Returns {@code -1} if the file length is unknown.
     */
    long getLength();

    /**
     * The download percentage. Range 0,100.
     * Returns {@link Float#MIN_VALUE} if the file length is unknown.
     */
    float getPercent();

    /**
     * The download percentage. Range 0,100.
     * Returns {@link Float#MIN_VALUE} if the file length is unknown.
     */
    float getRatio();
}
