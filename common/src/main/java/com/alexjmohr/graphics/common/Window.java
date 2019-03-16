package com.alexjmohr.graphics.common;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
	
	/**
	 * The GLFW window handle
	 */
	private long window = NULL;
	
	/**
	 * The keyboard callback
	 */
	private GLFWKeyCallback keyCallback;
	
	/**
	 * The window title
	 */
	private String title;
	
	/**
	 * The window width
	 */
	private int width;
	
	/**
	 * The window height
	 */
	private int height;
	
	/**
	 * Whether vsync is enabled
	 */
	private boolean vsync;
	
	/**
	 * Creates a Window with specified title, width, height, and whether to enable vsync
	 * @param title		The title
	 * @param width		The window width
	 * @param height	The window height
	 * @param vsync		Should vsync be used
	 */
	public Window(String title, int width, int height, boolean vsync) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.vsync = vsync;
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
	 * Initializes and shows the window
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
		
		// Create the window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the window");
		}
		glfwMakeContextCurrent(window);
		
		// Set the keyboard callback
		keyCallback = new GLFWKeyCallback() {
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
					// Set the window to close when the ESC key is PRESSED
					glfwSetWindowShouldClose(window, true);
				}
			}
		};
		glfwSetKeyCallback(window, keyCallback);
		
		// Finally show the window
		setVsync(vsync);
		glfwShowWindow(window);
	}
	
	/**
	 * Poll for events and swap buffers
	 */
	public void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
	}
	
	/**
	 * Destroys the window and frees the keyboard callback
	 */
	public void destroy() {
		if (window != NULL ) {
			glfwDestroyWindow(window);	
		}
		if (keyCallback != null) {
			keyCallback.free();
		}
	}
	
	/**
	 * Gets whether the window should close
	 * @return true if the window should close, false if not.
	 */
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	/**
	 * Sets the window width
	 * @param width the width to set (greater than 0)
	 */
	public void setWidth(int width) {
		if (width <= 0) {
			throw new IllegalArgumentException("Window width must be greater than 0");
		}
		// If the window has been created, update it's size
		if (window != NULL) {
			glfwSetWindowSize(window, width, this.height);
		}
		this.width = width;
	}
	
	/**
	 * Gets the window width
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Sets the window height
	 * @param height the height to set (greater than 0) 
	 */
	public void setHeight(int height) {
		if (height <= 0) {
			throw new IllegalArgumentException("Window height must be greater than 0");
		}
		// If the window has been created, update it's size
		if (window != NULL) {
			glfwSetWindowSize(window, this.width, height);
		}
		this.height = height;
	}
	
	/**
	 * Gets the window height
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets the window title
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		// If the window has been created, set it's title
		if (window != NULL) {
			glfwSetWindowTitle(window, title);	
		}
		this.title = title;
	}
	
	/**
	 * Gets the window title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets vsync
	 * @param vsync true to enable, false to disable.
	 */
	public void setVsync(boolean vsync) {
		if (vsync) {
			glfwSwapInterval(1);
		} else {
			glfwSwapInterval(0);
		}
		this.vsync = vsync;
	}
	
	/**
	 * Gets whether vsync is enabled
	 * @return true if enabled, false if disabled
	 */
	public boolean getVsync() {
		return vsync;
	}
}
