package com.alexjmohr.graphics.lights;

import com.alexjmohr.graphics.ShaderProgram;
import org.joml.Vector3f;

public abstract class Light {

    public static final Vector3f DEFAULT_COLOR = new Vector3f(1, 1, 1);

    private Vector3f position;
    private Vector3f color;

    public Light() {
        this.position = new Vector3f(0, 0, 0);
        this.color = new Vector3f(DEFAULT_COLOR);
    }

    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public void setShaderProgramUniforms(ShaderProgram program, String uniformName) {
        program.setUniform(uniformName + ".position", position);
        program.setUniform(uniformName + ".color", color);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
