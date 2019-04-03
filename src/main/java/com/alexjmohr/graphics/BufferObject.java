package com.alexjmohr.graphics;

import static org.lwjgl.opengl.GL20.*;

/**
 * Wrapper class for Vertex Buffer Object (VBO)
 * @author Alex Mohr
 *
 */
public abstract class BufferObject {
	/**
	 * The VBO id
	 */
	private int buffer;
	
	/**
	 * The buffer target (GL_ARRAY_OBJECT or GL_ELEMENT_ARRAY_TARGET)
	 */
	protected int target;
	
	/**
	 * Generates the VBO with the specified target.
	 * @param target GL_ARRAY_OBJECT or GL_ELEMENT_ARRAY_OBJECT
	 */
	protected BufferObject(int target) {
		buffer = glGenBuffers();
		this.target = target;
	}
	
	/**
	 * Binds the buffer object
	 */
	public void bind() {
		glBindBuffer(target, buffer);
	}
	
	/**
	 * Unbinds the buffer object
	 */
	public void unbind() {
		glBindBuffer(target, 0);
	}
	
	/**
	 * Deletes the buffer object
	 */
	public void delete() {
		glDeleteBuffers(buffer);
	}
}
