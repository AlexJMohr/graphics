package com.alexjmohr.graphics.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryUtil;

/**
 * ResourceLoader
 */
public class ResourceLoader {

    /**
     * Loads a resource to a ByteBuffer from the given path
     * @param path the path to the resource file to load
     */
    public static ByteBuffer loadResource(String path) throws IOException {
        ByteBuffer buffer = null;
        try (InputStream in = ResourceLoader.class.getResourceAsStream(path)) {
            byte[] data = in.readAllBytes();
            buffer = MemoryUtil.memAlloc(data.length);
            buffer.put(data).flip();
        }
        return buffer;
    }
}