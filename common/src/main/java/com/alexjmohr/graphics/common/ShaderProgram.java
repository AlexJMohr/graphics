package com.alexjmohr.graphics.common;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.logging.Logger;

import org.joml.*;
import org.lwjgl.system.MemoryStack;

/**
 * Wrapper class for a GL shader program.
 * 
 * Attach shaders using attachShader(shader), then link()
 * 
 * @author Alex Mohr
 *
 */
public class ShaderProgram {
	
	/**
	 * The program id
	 */
	public int program;
	
	/**
	 * Creates the program
	 */
	public ShaderProgram() {
		program = glCreateProgram();
	}
	
	/**
	 * Use (bind) the program
	 */
	public void use() {
		glUseProgram(program);
	}
	
	/**
	 * Unuse (unbind) the program
	 */
	public void unuse() {
		glUseProgram(0);
	}
	
	/**
	 * Deletes the program
	 */
	public void delete() {
		glDeleteProgram(program);
	}
	
	/**
	 * Attach the specified shader to the program
	 * @param shader the shader to attach
	 */
	public void attachShader(Shader shader) {
		glAttachShader(program, shader.getId());
	}
	
	/**
	 * Links the shader program and checks the status
	 */
	public void link() {
		glLinkProgram(program);
		checkStatus();
	}
	
	/**
	 * Enables and sets the vertex attrib pointer specified by given name
	 * @param name   the name of the vertex attrib
	 * @param size   the size of attrib
	 * @param stride the stride (bytes between each element)
	 * @param offset the start of the attribs
	 */
	public void setVertexAttribPointer(String name, int size, int stride, int offset) {
		// Get the attribute location
		int location = glGetAttribLocation(program, name);
		glEnableVertexAttribArray(location);
		glVertexAttribPointer(location, size, GL_FLOAT, false, stride, offset);
	}
	
	/**
	 * If the program failed to link, log the info log
	 */
	private void checkStatus() {
		int status = glGetProgrami(program, GL_LINK_STATUS);
		if (status != GL_TRUE) {
			String infoLog = glGetProgramInfoLog(program);
			Logger.getLogger(ShaderProgram.class.getName()).severe("Shader program link failed" + System.lineSeparator() + infoLog);
		}
	}
	
	/**
	 * Sets the uniform specified by the given name to the given value
	 * @param name  the name of the uniform to set
	 * @param value the value to set
	 */
	public void setUniform(String name, int value) {
		int location = glGetUniformLocation(program, name);
		glUniform1i(location, value);
	}

	/**
	 * Sets the uniform specified by the given name to the given value
	 * @param name  the name of the uniform to set
	 * @param value the value to set
	 */
	public void setUniform(String name, Vector2f value) {
		int location = glGetUniformLocation(program, name);
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(2);
			value.get(buffer);
			glUniform2fv(location, buffer);
		}
	}

	/**
	 * Sets the uniform specified by the given name to the given value
	 * @param name  the name of the uniform to set
	 * @param value the value to set
	 */
	public void setUniform(String name, Vector3f value) {
		int location = glGetUniformLocation(program, name);
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(3);
			value.get(buffer);
			glUniform3fv(location, buffer);
		}
	}

	/**
	 * Sets the uniform specified by the given name to the given value
	 * @param name  the name of the uniform to set
	 * @param value the value to set
	 */
	public void setUniform(String name, Vector4f value) {
		int location = glGetUniformLocation(program, name);
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(4);
			value.get(buffer);
			glUniform4fv(location, buffer);
		}
	}

	/**
	 * Sets the uniform specified by the given name to the given value
	 * @param name  the name of the uniform to set
	 * @param value the value to set
	 */
	public void setUniform(String name, Matrix3f value) {
		int location = glGetUniformLocation(program, name);
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(3 * 3);
			value.get(buffer);
			glUniformMatrix3fv(location, false, buffer);
		}
	}

	/**
	 * Sets the uniform specified by the given name to the given value
	 * @param name  the name of the uniform to set
	 * @param value the value to set
	 */
	public void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(program, name);
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(4 * 4);
			value.get(buffer);
			glUniformMatrix4fv(location, false, buffer);
		}
	}
}
