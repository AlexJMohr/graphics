package com.alexjmohr.graphics;


import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    /**
     * The GLFW window handle
     */
    private long window = NULL;

    /**
     * Is v-sync enabled
     */
    private boolean vsyncEnabled;

    /**
     * The width in pixels
     */
    private int width;

    /**
     * The height in pixels
     */
    private int height;

    /**
     * The title
     */
    private String title;

    /**
     * Create a window with specified title, width, height, and whether v-sync is enabled or not.
     * <code>init()</code> must be called
     * @param title The title
     * @param width The width in pixels
     * @param height The height in pixels
     * @param vsyncEnabled Whether v-sync is enabled
     */
    public Window(String title, int width, int height, boolean vsyncEnabled) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vsyncEnabled = vsyncEnabled;
    }

    /**
     * Checks that an OpenGL 3.2+ context can be created on the graphics adapter
     * @return true if OpenGL 3.2+ is available, false if not.
     */
    private boolean isOpenGL32() {
        // Reset window hints
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation

        // Create temporary window and OpenGL context
        long temp = glfwCreateWindow(1, 1, "", NULL, NULL);
        if (temp == NULL) {
            throw new RuntimeException("Could not create temporary GLFW window");
        }
        glfwMakeContextCurrent(temp);
        GL.createCapabilities();

        // Get the capabilities of the created context
        GLCapabilities caps = GL.getCapabilities();

        // Destroy the temporary window
        glfwDestroyWindow(temp);

        // return whether opengl 3.2 is available
        return caps.OpenGL32;
    }

    /**
     * Create and show the window
     */
    public void init() {
        // First check that OpenGL 3.2 context can be created. If this happens on a laptop with dual graphics
        // adapters, then the discrete GPU should be used to run the program.
        if (!isOpenGL32()) {
            throw new RuntimeException("Could not create a modern OpenGL 3.2 context on your graphics adapter.");
        }

        // Set window hints
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // window will be hidden when it is created
        // Request a forward compatible OpenGL 3.2 Core context
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        // Create the window and set OpenGL context
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the window");
        }
        glfwMakeContextCurrent(window);

        setVsyncEnabled(vsyncEnabled);

        glEnable(GL_MULTISAMPLE);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        glViewport(0, 0, width, height);

        glfwShowWindow(window);
    }

    /**
     * Sets vsync
     * @param vsyncEnabled Whether vsync should be enabled or not
     */
    public void setVsyncEnabled(boolean vsyncEnabled) {
        this.vsyncEnabled = vsyncEnabled;
        if (vsyncEnabled) {
            glfwSwapInterval(1);
        } else {
            glfwSwapInterval(0);
        }
    }

    /**
     * Swap render buffers and poll for events
     */
    public void update() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    /**
     * Whether the window should close
     * @return true if the window should close, false if not
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    /**
     * Destroys the window and releases callbacks
     */
    public void destroy() {
        glfwDestroyWindow(window);
    }

    /**
     * Get the width
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the GLFW window handle
     * @return the GLFW window handle
     */
    public long getWindow() {
        return window;
    }

    /**
     * Set if the window should close next update
     * @param shouldClose should the window close
     */
    public void setShouldClose(boolean shouldClose) {
        glfwSetWindowShouldClose(window, shouldClose);
    }
}
