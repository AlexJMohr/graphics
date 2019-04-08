package com.alexjmohr.graphics.lights;

import com.alexjmohr.graphics.ShaderProgram;
import org.joml.Vector3f;

public class DirectionalLight extends Light {

    public static Vector3f DEFAULT_DIRECTION = new Vector3f(0, -1, 0);

    private Vector3f direction;

    public DirectionalLight() {
        super();
        direction = DEFAULT_DIRECTION;

    }
    public DirectionalLight(Vector3f direction) {
        this.direction = direction;
        this.direction.normalize();
    }

    public DirectionalLight(Vector3f position, Vector3f color, Vector3f direction) {
        super(position, color);
        this.direction = direction;
        this.direction.normalize();
    }

    @Override
    public void setShaderProgramUniforms(ShaderProgram program, String uniformName) {
        super.setShaderProgramUniforms(program, uniformName);
        program.setUniform(uniformName + ".direction", direction);
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = new Vector3f(direction).normalize();
    }
}
