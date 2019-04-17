package com.alexjmohr.graphics.rendering;

import static org.lwjgl.opengl.GL30.*;

/**
 * Wrapper class for a Vertex Array Object (VAO)
 * @author Alex Mohr
 *
 */
public class VertexArrayObject {
	
	/**
	 * The VAO id
	 */
	private int array;
	
	/**
	 * Generates a VAO
	 */
	public VertexArrayObject() {
		array = glGenVertexArrays();
	}
	
	/**
	 * Binds the VAO
	 */
	public void bind() {
		glBindVertexArray(array);
	}
	
	/**
	 * Unbinds the VAO
	 */
	public void unbind() {
		glBindVertexArray(0);
	}
	
	/**
	 * Deletes the VAO
	 */
	public void delete() {
		glDeleteVertexArrays(array);
	}
	
}
