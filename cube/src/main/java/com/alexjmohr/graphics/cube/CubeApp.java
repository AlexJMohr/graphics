package com.alexjmohr.graphics.cube;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import com.alexjmohr.graphics.common.*;

/**
 * Renders a cube in the center of the screen
 * @author Alex Mohr
 *
 */
public class CubeApp extends BaseApp {
	
	/**
	 * The shader program that draws the cube
	 */
	ShaderProgram program;
	
	/**
	 * The VAO that holds the cube's VBOs
	 */
	VertexArrayObject vao;
	
	/**
	 * The vbo for positions data
	 */
	VertexBufferObject vboPositions;
	
	/**
	 * The buffer for indices
	 */
	ElementBufferObject eboIndices;
	
	/**
	 * The main camera
	 */
	Camera camera;
	
	/**
	 * The number of indices in the element buffer
	 */
	int numIndices;
	
	/**
	 * The camera's projection matrix
	 */
	Matrix4f projection;
	
	/**
	 * The speed at which the cube rotates per second
	 */
	float cubeRotateSpeed = 2.0f;
	
	/**
	 * The current angle of the cube
	 */
	float cubeAngleY = 0.0f;
	
	public CubeApp() {
		super();
		// Override default window title
		windowTitle = "Alex Mohr Graphics - Cube";
	}
	
	@Override
	protected void init() {
		super.init();
		
		// Initialize the camera
		camera = new Camera(new Vector3f(5, 5, 5), new Vector3f(-1, -1, -1).normalize());
		
		// Load the default shader program
		program = new ShaderProgram();
		Shader vertexShader = Shader.loadShader(GL_VERTEX_SHADER, "res/default.vert");
		Shader fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, "res/default.frag");
		program.attachShader(vertexShader);
		program.attachShader(fragmentShader);
		program.link();
		
		// Can delete the vertex and fragment shaders, since they are linked to the program now.
		vertexShader.delete();
		fragmentShader.delete();
		
		// Create the VAO and bind it for the following operations
		vao = new VertexArrayObject();
		vao.bind();
		
		// Create the VBO and EBO for the cube positions and indices respectively
		vboPositions = new VertexBufferObject();
		eboIndices = new ElementBufferObject();
		
		// Cube positions
		float[] verts = {
				// front
				-1.0f, -1.0f, 1.0f, // 0 - bottom left
				1.0f, -1.0f, 1.0f,  // 1 - bottom right
				-1.0f, 1.0f, 1.0f,  // 2 - top left
				1.0f, 1.0f, 1.0f,   // 3 - top right
				// back
				-1.0f, -1.0f, -1.0f, // 4 - bottom left
				1.0f, -1.0f, -1.0f,  // 5 - bottom right
				-1.0f, 1.0f, -1.0f,  // 6 - top left
				1.0f, 1.0f, -1.0f,   // 7 - top right
				
		};
		
		// Cube indices
		int[] indices = {
				// front
				0, 2, 1,
				2, 3, 1,
				// back
				5, 7, 4,
				7, 6, 4,
				// right
				1, 3, 5,
				3, 7, 5,
				// left
				4, 6, 0,
				6, 2, 0,
				// top
				2, 6, 3,
				6, 7, 3,
				// bottom
				0, 1, 4,
				1, 5, 4,
		};
		numIndices = indices.length;
		
		// Upload the positions and indices buffers to the VBO and EBO respectively
		try (MemoryStack stack = MemoryStack.stackPush()) {
			// Upload positions
			FloatBuffer posBuffer = stack.mallocFloat(verts.length);
			posBuffer.put(verts).flip();
			vboPositions.bind();
			vboPositions.uploadData(posBuffer, GL_STATIC_DRAW);
			// Enable the vertex attribute for positions
			program.setVertexAttribPointer("position", 3, 0, 0);
			
			// Upload indices
			IntBuffer indexBuffer = stack.mallocInt(indices.length);
			indexBuffer.put(indices).flip();
			eboIndices.uploadData(indexBuffer, GL_STATIC_DRAW);
		}
		vao.unbind();
	}
	
    public static void main( String[] args ) {
    	new CubeApp().run();
    }

	@Override
	protected void render() {
		// Clear the render buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		program.use();
		
		// Calculate projection matrix and set the uniform
		Matrix4f projection = new Matrix4f().perspective(70.0f, windowWidth / (float) windowHeight, 0.1f, 100.0f);
		program.setUniform("projection", projection);
		
		// Calculate view matrix and set the uniform
		Vector3f center = new Vector3f(camera.getForward()).sub(camera.getPosition());
		Matrix4f view = new Matrix4f().lookAt(camera.getPosition(), center, camera.getUp());
		program.setUniform("view", view);
		
		// Calculate the model matrix and set the uniform
		Matrix4f model = new Matrix4f().rotate(cubeAngleY, new Vector3f(0, 1, 0));
		program.setUniform("model", model);
		
		// Bind the VAO and the EBO and draw the cube
		vao.bind();
		eboIndices.bind();
		glDrawElements(GL_TRIANGLES, numIndices, GL_UNSIGNED_INT, 0);
		
		// Unbind everything
		eboIndices.unbind();
		vao.unbind();
		program.unuse();
	}

	@Override
	protected void update(float delta) {
		// Rotate the cube over time
		cubeAngleY += cubeRotateSpeed * delta;
	}
	
	@Override
	protected void destroy() {
		vboPositions.delete();
		eboIndices.delete();
		vao.delete();
		program.delete();
		
		super.destroy();
	}
}
