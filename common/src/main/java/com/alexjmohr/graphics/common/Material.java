package com.alexjmohr.graphics.common;

import org.joml.Vector4f;

/**
 * Material
 */
public class Material {

    public static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

    /**
     * Diffuse colour
     */
    private Vector4f diffuse;

    /**
     * Ambient colour
     */
    private Vector4f ambient;

    /**
     * Specular colour
     */
    private Vector4f specular;

    /**
     * Diffuse texture
     */
    private Texture texture;

    /**
     * Normal map
     */
    private Texture normalMap;

    /**
     * Creates a material with default colours
     */
    public Material() {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR);
    }

    /**
     * Creates a material with specified diffuse, ambient, specular colours. If any colour passed
     * is null, the DEFAULT_COLOUR will be used.
     * @param diffuse diffuse colour
     * @param ambient ambient colour
     * @param specular specular colour
     */
    public Material(Vector4f diffuse, Vector4f ambient, Vector4f specular) {
        this.diffuse = diffuse != null ? diffuse : DEFAULT_COLOUR;
        this.ambient = ambient != null ? diffuse : DEFAULT_COLOUR;
        this.specular = specular != null ? diffuse : DEFAULT_COLOUR;
    }

    /**
     * @return true if material has texture, false if not
     */
    public boolean hasTexture() {
        return texture != null;
    }

    /**
     * @return true if the material has a normal map, false if not
     */
    public boolean hasNormalMap() {
        return normalMap != null;
    }

    /**
     * @return the diffuse
     */
    public Vector4f getDiffuse() {
        return diffuse;
    }

    /**
     * @param diffuse the diffuse to set
     */
    public void setDiffuse(Vector4f diffuse) {
        this.diffuse = diffuse;
    }

    /**
     * @return the ambient
     */
    public Vector4f getAmbient() {
        return ambient;
    }

    /**
     * @param ambient the ambient to set
     */
    public void setAmbient(Vector4f ambient) {
        this.ambient = ambient;
    }

    /**
     * @return the specular
     */
    public Vector4f getSpecular() {
        return specular;
    }

    /**
     * @param specular the specular to set
     */
    public void setSpecular(Vector4f specular) {
        this.specular = specular;
    }

    /**
     * Sets the texture
     * @param texture the texture to set
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Get the texture
     * @return the texture
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Sets the normal map
     * @param normalMap the normal map to set
     */
    public void setNormalMap(Texture normalMap) {
        this.normalMap = normalMap;
    }

    /**
     * Get the normal map
     * @return the normal map
     */
    public Texture getNormalMap() {
        return normalMap;
    }
}