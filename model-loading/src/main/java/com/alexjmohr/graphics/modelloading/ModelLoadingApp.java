package com.alexjmohr.graphics.modelloading;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.alexjmohr.graphics.common.*;

/**
 * Renders a cube in the center of the screen
 * @author Alex Mohr
 *
 */
public class ModelLoadingApp extends BaseApp {
	
	/**
	 * The main camera
	 */
	Camera camera;
	
	/**
	 * The mesh renderer used to render the model
	 */
	MeshRenderer meshRenderer;
	
	/**
	 * The model loader
	 */
	ModelLoader modelLoader;

	/**
	 * Y-Angle of the model
	 */
	float angle = 0.0f;

	/**
	 * The speed at which the model rotates
	 */
	float rotateSpeed = 1.0f;
	
	public ModelLoadingApp() {
		super();
		// Override default window title
		windowTitle = "Alex Mohr Graphics - Model Loading";
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
		
		// Load model
		modelLoader = new ModelLoader();
		try {
			modelLoader.loadModel("model-loading/src/main/resources/ST_MARIA/ST_MARIA.obj", "/ST_MARIA");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Wireframe mode
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}

	@Override
	protected void render() {
		// Clear the render buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		Vector3f meshPosition = new Vector3f();
		Quaternionf meshRotation = new Quaternionf().fromAxisAngleRad(0, 1, 0, angle);
		Vector3f meshScale = new Vector3f(1.0f);
		
		// render the model at the origin
		for (int i = 0; i < modelLoader.getNumMeshes(); i++) {
			meshRenderer.renderMesh(modelLoader.getMesh(i), camera, meshPosition, meshRotation, meshScale);
		}
	}

	@Override
	protected void update(float delta) {
		angle += rotateSpeed * delta;
	}
	
	@Override
	protected void destroy() {
		modelLoader.delete();
		meshRenderer.delete();
		super.destroy();
	}

    public static void main(String[] args) {
    	new ModelLoadingApp().run();
    }
}
