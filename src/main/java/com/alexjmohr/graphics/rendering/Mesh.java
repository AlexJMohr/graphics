package com.alexjmohr.graphics.rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL20.*;

/**
 * Contains VAO, VBOs, and EBO to describe a mesh
 * @author Alex Mohr
 *
 */
public class Mesh {
	
	/**
	 * The VAO
	 */
	private VertexArrayObject vao;
	
	/**
	 * The positions VBO
	 */
	private VertexBufferObject vboPositions;
	
	/**
	 * The normals VBO
	 */
	private VertexBufferObject vboNormals;
	
	/**
	 * The texture coordinates vbo
	 */
	private VertexBufferObject vboTexCoords;

	/**
	 * The tangents vbo
	 */
	private VertexBufferObject vboTangents;

	/**
	 * The bitangents vbo
	 */
	private VertexBufferObject vboBitangents;
	
	/**
	 * The EBO
	 */
	private ElementBufferObject ebo;
	
	/**
	 * The positions buffer
	 */
	private FloatBuffer positions;
	
	/**
	 * The normals buffer
	 */
	private FloatBuffer normals;
	
	/**
	 * The texture coordinates buffer
	 */
	private FloatBuffer texCoords;

	/**
	 * The tangent buffer
	 */
	private FloatBuffer tangents;

	/**
	 * The bitangent buffer
	 */
	private FloatBuffer bitangents;
	
	/**
	 * The index buffer
	 */
	private IntBuffer elements;

	/**
	 * The material used to render the mesh
	 */
	private Material material;
	
	/**
	 * Creates a mesh with specified positions, normals, texCoords, elements. normals and texcoords
	 * can be omitted by passing null. The buffers are saved, so they should not be freed by the
	 * caller.
	 * @param positions the positions buffer
	 * @param normals   the normals buffer
	 * @param texCoords the texture coordinates buffer
	 * @param elements  the index buffer. The number of elements is assumed to be this buffer's capacity.
	 */
	public Mesh(FloatBuffer positions, FloatBuffer normals, FloatBuffer texCoords, FloatBuffer tangents, FloatBuffer bitangents, IntBuffer elements) {
		this.material = new Material();
		// save buffers
		this.positions = positions;
		this.normals = normals;
		this.texCoords = texCoords;
		this.tangents = tangents;
		this.bitangents = bitangents;
		this.elements = elements;
		
		// upload buffers to VBOs and EBO
		upload();
	}
	
	/**
	 * Upload the buffers to the VBOs and EBO
	 */
	private void upload() {
		// Create and bind the vao
		vao = new VertexArrayObject();
		vao.bind();
		
		// positions
		vboPositions = new VertexBufferObject();
		vboPositions.bind();
		vboPositions.uploadData(positions, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		// normals
		if (normals != null) {
			vboNormals = new VertexBufferObject();
			vboNormals.bind();
			vboNormals.uploadData(normals, GL_STATIC_DRAW);
			glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		}
		
		// texture coordinates
		if (texCoords != null) {
			vboTexCoords = new VertexBufferObject();
			vboTexCoords.bind();
			vboTexCoords.uploadData(texCoords, GL_STATIC_DRAW);
			glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
		}

		// tangents
		if (tangents != null) {
			vboTangents = new VertexBufferObject();
			vboTangents.bind();
			vboTangents.uploadData(tangents, GL_STATIC_DRAW);
			glVertexAttribPointer(3, 3, GL_FLOAT, false, 0, 0);
		}

		// bitangents
		if (bitangents != null) {
			vboBitangents = new VertexBufferObject();
			vboBitangents.bind();
			vboBitangents.uploadData(bitangents, GL_STATIC_DRAW);
			glVertexAttribPointer(4, 3, GL_FLOAT, false, 0, 0);
		}
		
		// elements
		ebo = new ElementBufferObject();
		ebo.bind();
		ebo.uploadData(elements, GL_STATIC_DRAW);
		
		// unbind
		unbind();
	}
	
	/**
	 * Get the number of elements in the EBO
	 * @return the number of elements
	 */
	public int getNumElements() {
		return elements.capacity();
	}

	/**
	 * Gets the material
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Sets the material
	 * @param material the material to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	/**
	 * Bind the VAO and EBO
	 */
	public void bind() {
		vao.bind();
		ebo.bind();
	}
	
	/**
	 * Unbind the VAO and EBO
	 */
	public void unbind() {
		vao.unbind();
		ebo.unbind();
	}
	
	/**
	 * Frees the buffers
	 */
	public void delete() {
		MemoryUtil.memFree(positions);
		if (normals != null) {
			MemoryUtil.memFree(normals);	
		}
		if (texCoords != null) {
			MemoryUtil.memFree(texCoords);	
		}
		MemoryUtil.memFree(elements);
	}
}
