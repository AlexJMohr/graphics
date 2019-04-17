package com.alexjmohr.graphics.lights;

import com.alexjmohr.graphics.rendering.ShaderProgram;
import org.joml.Vector3f;

public class SpotLight extends Light {

    public static Vector3f DEFAULT_DIRECTION = new Vector3f(0, 0, -1);

    public static float DEFAULT_CUT_OFF = 12.5f;

    private Vector3f direction;

    private float cutOff;

    public SpotLight() {
        super();
        setDirection(DEFAULT_DIRECTION);
        setCutOff(DEFAULT_CUT_OFF);
    }

    public SpotLight(Vector3f direction, float cutOff) {
        super();
        setDirection(direction);
        setCutOff(cutOff);
    }

    @Override
    public void setShaderProgramUniforms(ShaderProgram program, String uniformName) {
        super.setShaderProgramUniforms(program, uniformName);
        program.setUniform(uniformName + ".direction", direction);
        program.setUniform(uniformName + ".cutOff", cutOff);
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = new Vector3f(direction).normalize();
    }

    public float getCutOff() {
        return cutOff;
    }

    public void setCutOff(float cutOff) {
        this.cutOff = cutOff;
    }
}
