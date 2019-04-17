package com.alexjmohr.graphics.lights;

import com.alexjmohr.graphics.rendering.ShaderProgram;
import org.joml.Vector3f;

public class PointLight extends Light {

    public static float DEFAULT_CONSTANT = 1.0f;
    public static float DEFAULT_LINEAR = 0.09f;
    public static float DEFAULT_QUADRATIC = 0.032f;

    private float constant;
    private float linear;
    private float quadratic;

    public PointLight() {
        super();
        constant = DEFAULT_CONSTANT;
        linear = DEFAULT_LINEAR;
        quadratic = DEFAULT_QUADRATIC;
    }

    public PointLight(float constant, float linear, float quadratic) {
        this.constant = constant;
        this.linear = linear;
        this.quadratic = quadratic;
    }

    public PointLight(Vector3f position, Vector3f color, float constant, float linear, float quadratic) {
        super(position, color);
        this.constant = constant;
        this.linear = linear;
        this.quadratic = quadratic;
    }

    @Override
    public void setShaderProgramUniforms(ShaderProgram program, String uniformName) {
        super.setShaderProgramUniforms(program, uniformName);
        program.setUniform(uniformName + ".constant", constant);
        program.setUniform(uniformName + ".linear", linear);
        program.setUniform(uniformName + ".quadratic", quadratic);
    }

    public float getConstant() {
        return constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }

    public float getLinear() {
        return linear;
    }

    public void setLinear(float linear) {
        this.linear = linear;
    }

    public float getQuadratic() {
        return quadratic;
    }

    public void setQuadratic(float quadratic) {
        this.quadratic = quadratic;
    }
}
