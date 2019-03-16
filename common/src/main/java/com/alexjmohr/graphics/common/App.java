package com.alexjmohr.graphics.common;

import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Creates a blank window
 * @author Alex Mohr
 *
 */
public class App {
	
	/**
	 * The window
	 */
	private Window window;
	
	/**
	 * The GLFW error callback
	 */
	private GLFWErrorCallback errorCallback;
	
	/**
	 * Initializes the app, then runs the main event/rendering loop until the app quits.
	 */
	public void run() {
		init();
		loop();
		destroy();
	}
	
	/**
	 * Initializes GLFW and creates a Window
	 */
	private void init() {
		// Set a GLFW error callback
		errorCallback = GLFWErrorCallback.createPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		
		// Initialize GLFW
		if (!glfwInit()) {
			throw new RuntimeException("Failed to initialize GLFW");
		}
		
		// Create and initialize the window
		window = new Window("Alex Mohr Graphics - Common", 800, 600, true);
		window.init();
	}
	
	/**
	 * The main rendering/event loop
	 */
	private void loop() {
		while (!window.shouldClose()) {
			window.update();
		}
	}
	
	/**
	 * Destroy the window and GFLW
	 */
	private void destroy() {
		if (window != null) {
			window.destroy();
		}
		if (errorCallback != null) {
			errorCallback.free();
		}
		glfwTerminate();
	}
	
    public static void main( String[] args ) {
    	new App().run();
    }
}
