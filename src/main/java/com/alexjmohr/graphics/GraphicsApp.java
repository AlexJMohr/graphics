package com.alexjmohr.graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.logging.*;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;

public class GraphicsApp {

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;
    private static final String WINDOW_TITLE = "Alex J Mohr Graphics";
    private static final boolean VSYNC_ENABLED = true;

    /**
     * The GLFW error callback
     */
    private GLFWErrorCallback errorCallback;

    /**
     * The window
     */
    private Window window;

    /**
     * The timer
     */
    private Timer timer;

    private Camera camera;
    private MeshRenderer meshRenderer;
    private ModelLoader modelLoader;
    float angle = 0.0f;
    float rotateSpeed = 1.0f;

    public GraphicsApp() {
        timer = new Timer();
        window = new Window(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT, VSYNC_ENABLED);
    }

    public void run() {
        init();
        loop();
        destroy();
    }

    private void init() {
        // Set an error callback for GLFW
        errorCallback = GLFWErrorCallback.createPrint(System.err);
        glfwSetErrorCallback(errorCallback);

        // Initialize GLFW
        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }

        // initialize the window and timer
        window.init();
        timer.init();

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

        // Create the camera
        camera = new Camera(new Vector3f(5, 5, 5), new Vector3f(-1, -1, -1).normalize());

        // Create the mesh renderer with the shader program
        meshRenderer = new MeshRenderer(program);

        // Load model
        modelLoader = new ModelLoader();
        try {
            modelLoader.loadModel("src/main/resources/ST_MARIA/ST_MARIA.obj", "/ST_MARIA");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loop() {
        float delta;

        while (!window.shouldClose()) {
            delta = timer.getDelta();

            update(delta);
            timer.updateUPS();

            render();
            timer.updateFPS();

            timer.update();

            window.update();
        }
    }

    /**
     * Update the scene objects
     * @param delta Time since last update
     */
    private void update(float delta) {
        angle += rotateSpeed * delta;
    }

    /**
     * Render the scene objects
     */
    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Vector3f meshPosition = new Vector3f();
        Quaternionf meshRotation = new Quaternionf().fromAxisAngleRad(0, 1, 0, angle);
        Vector3f meshScale = new Vector3f(1);

        // Render the model at the origin
        for (int i = 0; i < modelLoader.getNumMeshes(); i++) {
            meshRenderer.renderMesh(modelLoader.getMesh(i), camera, meshPosition, meshRotation, meshScale);
        }
    }

    /**
     * Destroys the window, releases error callback, and terminates GLFW
     */
    private void destroy() {
        modelLoader.delete();
        meshRenderer.delete();

        if (window != null) {
            window.destroy();
        }
        if (errorCallback != null) {
            errorCallback.free();
        }
        glfwTerminate();
    }

    public static void main(String[] args) {
        new GraphicsApp().run();
    }
}
