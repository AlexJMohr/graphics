package com.alexjmohr.graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;

/**
 * Singleton class for running the app
 */
public class GraphicsApp {

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;
    private static final String WINDOW_TITLE = "Alex J Mohr Graphics";
    private static final boolean VSYNC_ENABLED = true;

    /**
     * The singleton instance
     */
    private static GraphicsApp instance;

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

    /**
     * The main camera
     */
    private Camera camera;

    /**
     * The mesh renderer
     */
    private MeshRenderer meshRenderer;

    /**
     * The model loader
     */
    private ModelLoader modelLoader;

    /**
     * The current angle of the mesh rotated about the y axis
     */
    float angle = 0.0f;

    /**
     * The speed at which teh mesh rotates
     */
    float rotateSpeed = 0.5f;

    /**
     * Shader programs used by the mesh renderer
     */
    ShaderProgram program;

    private GraphicsApp() {
        timer = new Timer();
        window = new Window(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT, VSYNC_ENABLED);
    }

    /**
     * Get the singleton instance of the app. It will be instantiated if this is
     * the first time calling it.
     * @return the app instance
     */
    public static GraphicsApp getInstance() {
        if (instance == null) {
            instance = new GraphicsApp();
        }
        return instance;
    }

    /**
     * Get the window
     * @return the window
     */
    public Window getWindow() {
        return window;
    }

    public void run() {
        init();
        loop();
        destroy();
    }

    /**
     * Load a shader program from the given vertex and fragment shaders
     *
     * @param vertPath the resource path to the vertex shader
     * @param fragPath the resource path to the fragment shader
     * @return the created shader program
     */
    private ShaderProgram loadShaderProgram(String vertPath, String fragPath) {
        ShaderProgram program = new ShaderProgram();
        Shader vertexShader = Shader.loadShader(GL_VERTEX_SHADER, vertPath);
        Shader fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, fragPath);
        program.attachShader(vertexShader);
        program.attachShader(fragmentShader);
        program.link();
        // Can delete the vertex and fragment shaders, since they are linked to the program now.
        vertexShader.delete();
        fragmentShader.delete();

        return program;
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

        // Load shader program
        program = loadShaderProgram("/shaders/default.vert", "/shaders/default.frag");

        // Create the camera
        camera = new Camera(new Vector3f(0, 0, 3), new Vector3f(0, 0, -1).normalize());

        // Create the mesh renderer with the shader program
        meshRenderer = new MeshRenderer(program);

        // Load model
        modelLoader = new ModelLoader();
        try {
            modelLoader.loadModel("src/main/resources/models/bricks/bricks.obj", "/models/bricks");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Input.init(window);
    }

    /**
     * Update the scene objects
     * @param delta Time since last update
     */
    private void update(float delta) {
        angle += rotateSpeed * delta;

        Input.update();
        if (Input.getKey(GLFW_KEY_ESCAPE)) {
            window.setShouldClose(true);
        }

        camera.update(delta);
    }

    /**
     * Render the scene objects
     */
    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Vector3f meshPosition = new Vector3f(0, 0, 0);
        Quaternionf meshRotation = new Quaternionf().fromAxisAngleRad(0, 1, 0, angle);
        Vector3f meshScale = new Vector3f(1, 1, 1);

        // Render the model at the origin
        for (int i = 0; i < modelLoader.getNumMeshes(); i++) {
            meshRenderer.renderMesh(modelLoader.getMesh(i), camera, meshPosition, meshRotation, meshScale);
        }
    }

    /**
     * The render/update loop
     */
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
        GraphicsApp.getInstance().run();
    }
}
