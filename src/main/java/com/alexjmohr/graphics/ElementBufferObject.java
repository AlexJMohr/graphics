package com.alexjmohr.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.nio.IntBuffer;

public class ElementBufferObject extends BufferObject {
	
	/**
	 * The target to use when binding or uploading data for all Vertex Buffer Objects
	 */
	private static final int TARGET = GL_ELEMENT_ARRAY_BUFFER;

	/**
	 * Generates the VBO
	 */
	public ElementBufferObject() {
		super(TARGET);
	}
	
	/**
	 * Uploads data to the VBO with the specified usage
	 * @param data	the data to upload
	 * @param usage see glBufferData
	 */
	public void uploadData(IntBuffer data, int usage) {
		bind();
		glBufferData(TARGET, data, usage);
	}
	
	/**
	 * Uploads an empty buffer with specified size and usage
	 * @param size	the buffer size
	 * @param usage	see glBufferData
	 */
	public void uploadNullData(long size, int usage) {
		bind();
		glBufferData(TARGET, size, usage);
	}
	
	/**
	 * Uploads data to an existing buffer with specified offset
	 * @param offset the offset of the existing buffer to put the data in
	 * @param data   the data to upload
	 */
	public void uploadSubData(long offset, IntBuffer data) {
		bind();
		glBufferSubData(TARGET, offset, data);
	}
}
