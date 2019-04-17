package com.alexjmohr.graphics.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.alexjmohr.graphics.loaders.ResourceLoader;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.stb.STBImage.*;

/**
 * Texture
 */
public class Texture {

    /**
     * The texture id
     */
    private final int texture;

    /**
     * The texture's width
     */
    private final int width;

    /**
     * The texture's height
     */
    private final int height;

    /**
     * Loads a texture from the given path
     * @param path the path of the texture file
     */
    public Texture(String path) throws IOException {
        this(ResourceLoader.loadResource(path));
    }

    /**
     * Creates a texture from a bytebuffer which was loaded directly from a file
     * @param data file data
     */
    public Texture(ByteBuffer data) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            
            // Decode the image data, save it's width and height
            ByteBuffer decodedImage = stbi_load_from_memory(data, w, h, channels, 4);
            this.width = w.get();
            this.height = h.get();

            // generate teture and bind it to unit 0
            texture = glGenTextures();
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture);
            // Tell opengl to unpack RGBA bytes so each channel is 1 byte
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            // Linear filtering, clamp to edges
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            // Upload decoded image data
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, decodedImage);
            // Generate mipmap
            glGenerateMipmap(GL_TEXTURE_2D);
            // Free decoded image data
            stbi_image_free(decodedImage);
        }
    }

    /**
     * Binds the texture
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    /**
     * Unbinds the texture
     */
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     * Get the texture id
     * @param the texture id
     */
    public int getId() {
        return texture;
    }
}