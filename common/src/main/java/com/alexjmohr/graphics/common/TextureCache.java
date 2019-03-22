package com.alexjmohr.graphics.common;

import java.util.HashMap;

/**
 * Singleton TextureCache caches loaded Textures so they aren't loaded twice
 */
public class TextureCache {

    /**
     * The singleton instance
     */
    private static TextureCache instance;
    
    /**
     * The map of texture file name to Texture object
     */
    private HashMap<String, Texture> cache;

    /**
     * Initializes the cache
     */
    private TextureCache() {
        cache = new HashMap<>();
    }

    /**
     * Get te texture cache instance. Initializes it if this is the first call.
     * @return the singleton instance
     */
    public static synchronized TextureCache getInstance() {
        if (instance == null) {
            instance = new TextureCache();
        }
        return instance;
    }

    /**
     * Get the texture from the cache. Loads the texture first if it isn't in the cache.
     * @param path the texture file path
     * @return the texture object
     */
    public Texture getTexture(String path) throws Exception {
        Texture texture = cache.get(path);
        if (texture == null) {
            texture = new Texture(path);
            cache.put(path, texture);
        }
        return texture;
    }
}