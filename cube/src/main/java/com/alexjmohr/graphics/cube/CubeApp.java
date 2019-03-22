package com.alexjmohr.graphics.cube;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

//import org.lwjgl.system.MemoryStack;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.alexjmohr.graphics.common.*;

/**
 * Renders a cube in the center of the screen
 * @author Alex Mohr
 *
 */
public class CubeApp extends BaseApp {
	
	/**
	 * The main camera
	 */
	Camera camera;
	
//	/**
//	 * The number of indices in the element buffer
//	 */
//	int numIndices;
	
	/**
	 * The camera's projection matrix
	 */
	Matrix4f projection;
	
	/**
	 * The speed at which the cube rotates in radians per second
	 */
	float cubeRotateSpeed = 2.0f;
	
	/**
	 * The current angle of the cube
	 */
	float cubeAngleY = 0.0f;
	
	/**
	 * The mesh renderer used to render the cube
	 */
	private MeshRenderer meshRenderer;
	
	/**
	 * The mesh that contains the cube
	 */
	private Mesh cubeMesh;
	
	public CubeApp() {
		super();
		// Override default window title
		windowTitle = "Alex Mohr Graphics - Cube";
	}
	
	@Override
	protected void init() {
		super.init();
		
		// Create the camera
		camera = new Camera(new Vector3f(5, 5, 5), new Vector3f(-1, -1, -1).normalize());
		
		// Load the default shader program
		ShaderProgram program = new ShaderProgram();
		Shader vertexShader = Shader.loadShader(GL_VERTEX_SHADER, "/default.vert");
		Shader fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, "/default.frag");
		program.attachShader(vertexShader);
		program.attachShader(fragmentShader);
		program.link();
		
		// Can delete the vertex and fragment shaders, since they are linked to the program now.
		vertexShader.delete();
		fragmentShader.delete();
		
		// Create the mesh renderer with the shader program
		meshRenderer = new MeshRenderer(program);
		
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
		
		cubeMesh = new Mesh(verts, null, null, indices);
	}

	@Override
	protected void render() {
		// Clear the render buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		// position
		Vector3f cubePosition = new Vector3f(0.0f, 1.0f, -4.0f);
		
		// rotation
		Quaternionf cubeRotation = new Quaternionf().fromAxisAngleRad(new Vector3f(0, 1, 0), cubeAngleY);
		
		// scale between 0.5 and 1.0 on y axiss
		float scaleY = ((float) Math.cos(timer.getTime()) * 0.25f) + 0.75f;
		Vector3f cubeScale = new Vector3f(1.0f, scaleY, 1.0f);
		
		// render the cube with given position, rotation, and scale
		meshRenderer.renderMesh(cubeMesh, camera, cubePosition, cubeRotation, cubeScale);
	}

	@Override
	protected void update(float delta) {
		// Rotate the cube over time
		cubeAngleY += cubeRotateSpeed * delta;
	}
	
	@Override
	protected void destroy() {
		cubeMesh.delete();
		meshRenderer.delete();
		super.destroy();
	}
	
    public static void main(String[] args) {
    	new CubeApp().run();
    }
}
