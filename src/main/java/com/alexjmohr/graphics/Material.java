package com.alexjmohr.graphics;

import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * Material
 */
public class Material {

    public static final Vector3f DEFAULT_AMBIENT = new Vector3f(0.1f, 0.1f, 0.1f);
    public static final Vector3f DEFAULT_DIFFUSE = new Vector3f(0.3f, 0.3f, 0.3f);
    public static final Vector3f DEFAULT_SPECULAR = new Vector3f(0.1f, 0.1f, 0.1f);
    public static final float DEFAULT_SHININESS = 32;

    /**
     * Diffuse colour
     */
    private Vector3f diffuse;

    /**
     * Ambient colour
     */
    private Vector3f ambient;

    /**
     * Specular colour
     */
    private Vector3f specular;

    /**
     * The specular exponent
     */
    private float shininess;

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
        this(DEFAULT_DIFFUSE, DEFAULT_AMBIENT, DEFAULT_SPECULAR, DEFAULT_SHININESS);
    }

    /**
     * Creates a material with specified diffuse, ambient, specular colours. If any colour passed
     * is null, the DEFAULT_COLOUR will be used.
     * @param diffuse diffuse colour
     * @param ambient ambient colour
     * @param specular specular colour
     */
    public Material(Vector3f diffuse, Vector3f ambient, Vector3f specular, float shininess) {
        setAmbient(ambient);
        setDiffuse(diffuse);
        setSpecular(specular);
        this.shininess = shininess;
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

    @Override
    public String toString() {
        String out = "";
        out += String.format("ambient: %s\n", ambient.toString());
        out += String.format("diffuse: %s\n", diffuse.toString());
        out += String.format("specular: %s\n", specular.toString());
        return out;
    }

    /**
     * @return the diffuse
     */
    public Vector3f getDiffuse() {
        return diffuse;
    }

    /**
     * @param diffuse the diffuse to set
     */
    public void setDiffuse(Vector3f diffuse) {
        if (diffuse == null) {
            this.diffuse = DEFAULT_DIFFUSE;
        } else {
            this.diffuse = diffuse;
        }
    }

    /**
     * @return the ambient
     */
    public Vector3f getAmbient() {
        return ambient;
    }

    /**
     * @param ambient the ambient to set
     */
    public void setAmbient(Vector3f ambient) {
        if (ambient == null) {
            this.ambient = DEFAULT_AMBIENT;
        } else {
            this.ambient = ambient;
        }
    }

    /**
     * @return the specular
     */
    public Vector3f getSpecular() {
        return specular;
    }

    /**
     * @param specular the specular to set
     */
    public void setSpecular(Vector3f specular) {
        if (specular == null) {
            this.specular = DEFAULT_SPECULAR;
        } else {
            this.specular = specular;
        }
    }

    /**
     * Get the shininess
     * @return the shininess
     */
    public float getShininess() {
        return shininess;
    }

    /**
     * Set the shininess
     * @param shininess The shininess to set
     */
    public void setShininess(float shininess) {
        this.shininess = shininess;
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