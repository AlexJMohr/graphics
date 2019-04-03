package com.alexjmohr.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Holds a shader program, and renders meshes with that program
 * @author Alex Mohr
 *
 */
public class MeshRenderer {

	/**
	 * The shader program used to render meshes
	 */
	private ShaderProgram program;
	
	/**
	 * Creates a mesh renderer with the specified shader program
	 * @param program
	 */
	public MeshRenderer(ShaderProgram program) {
		this.program = program;
	}
	
	/**
	 * Renders the given mesh from the point of view of the given camera, at the given mesh
	 * position, rotation, and scale
	 * @param mesh         the mesh to render
	 * @param camera       the camera to render from
	 * @param meshPosition the mesh position
	 * @param meshRotation the mesh rotation
	 * @param meshScale    the mesh scale
	 */
	public void renderMesh(Mesh mesh, Camera camera, Vector3f meshPosition, Quaternionf meshRotation, Vector3f meshScale) {
		Material material = mesh.getMaterial();

		// Use the shader program
		program.use();
		
		// Calculate projection matrix and set the uniform
		// TODO: get window width and height from somewhere
		Matrix4f projection = new Matrix4f().perspective(70.0f, 800 / (float) 600, 0.1f, 100.0f);
		program.setUniform("projection", projection);
		
		// Calculate view matrix and set the uniform
		Vector3f center = new Vector3f(camera.getForward()).sub(camera.getPosition());
		Matrix4f view = new Matrix4f().lookAt(camera.getPosition(), center, camera.getUp());
		program.setUniform("view", view);
		
		// Calculate the model matrix and set the uniform
		Matrix4f model = new Matrix4f().translate(meshPosition).rotate(meshRotation).scale(meshScale);
		program.setUniform("model", model);
		
		// Bind texture if material has it
		if (material.hasTexture()) {
			glActiveTexture(GL_TEXTURE0);
			Texture texture = material.getTexture();
			texture.bind();
			program.setUniform("tex", 0);
			program.setUniform("hasTexture", 1);
		} else {
			program.setUniform("hasTexture", 0);
		}

		// bind normal map if material has it
		if (material.hasNormalMap()) {
			glActiveTexture(GL_TEXTURE1);
			Texture normalMap = material.getNormalMap();
			normalMap.bind();
			program.setUniform("normalMap", 1);
			program.setUniform("hasNormalMap", 1);
		} else {
			program.setUniform("hasNormalMap", 0);
		}

		// Set the colours
		program.setUniform("ambient", material.getAmbient());
		program.setUniform("diffuse", material.getDiffuse());
		program.setUniform("specular", material.getSpecular());

		// Bind the VAO and the EBO and draw the cube
		mesh.bind();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glDrawElements(GL_TRIANGLES, mesh.getNumElements(), GL_UNSIGNED_INT, 0);
		
		// Unbind everything
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, 0);

		mesh.unbind();
		program.unuse();
	}
	
	/**
	 * Delete the mesh renderer and its resources
	 */
	public void delete() {
		program.delete();
	}
}
