package com.alexjmohr.graphics.rendering;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

import java.io.*;
import java.util.logging.Logger;

/**
 * Wrapper class for a GL shader
 * 
 * @author Alex Mohr
 *
 */
public class Shader {
	
	/**
	 * The shader id
	 */
	private int shader;
	
	/**
	 * Creates the shader with specified type and source code
	 * @param type   the shader type
	 * @param source the shader source
	 */
	public Shader(int type, CharSequence source) {
		// Create the shader
		shader = glCreateShader(type);
		// Upload the shader source code
		glShaderSource(shader, source);
		// Compile and check
		glCompileShader(shader);
		checkStatus();
	}
	
	/**
	 * If the compile status is not a success, prints the info log
	 */
	private void checkStatus() {
		int status = glGetShaderi(shader, GL_COMPILE_STATUS);
		if (status != GL_TRUE) {
			String infoLog = glGetShaderInfoLog(shader);
			Logger.getLogger(Shader.class.getName()).severe("Shader compilation failed" + System.lineSeparator() + infoLog);
		}
	}
	
	/**
	 * Loads a shader from with specified type from the specified file path
	 * @param  type the shader type
	 * @param  file the file to load from
	 * @return the created shader
	 */
	public static Shader loadShader(int type, String file) {
		StringBuilder builder = new StringBuilder();
		
		try (InputStream in = Shader.class.getResourceAsStream(file);
				InputStreamReader streamReader = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(streamReader)) {
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
		} catch (Exception ex) {
			throw new RuntimeException("Failed to load a shader file." + System.lineSeparator() + ex.getMessage());
		}
		
		CharSequence source = builder.toString();
		return new Shader(type, source);
	}
	
	/**
	 * Deletes the shader
	 */
	public void delete() {
		glDeleteShader(shader);
	}
	
	/**
	 * Gets the shader id
	 * @return the shader id
	 */
	public int getId() {
		return shader;
	}
}
