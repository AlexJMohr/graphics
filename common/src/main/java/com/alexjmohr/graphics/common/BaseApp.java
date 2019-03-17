package com.alexjmohr.graphics.common;
 
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.logging.*;
import org.lwjgl.glfw.GLFWErrorCallback;

/**
 * Abstract base app class that main classes in other modules can inherit from
 * @author Alex Mohr
 *
 */
public abstract class BaseApp {
	
	/**
	 * The target frames per second (when vsync is not enabled)
	 */
	public static final int TARGET_FPS = 60;
	
	/**
	 * The target updates per second
	 */
	public static final int TARGET_UPS = 30;
	
	/**
	 * The window
	 */
	protected Window window;
	
	/**
	 * The timer
	 */
	protected Timer timer;
	
	/**
	 * The window title
	 */
	protected String windowTitle = "Alex Mohr Graphics";
	
	/**
	 * The window width
	 */
	protected int windowWidth = 800;
	
	/**
	 * The window height
	 */
	protected int windowHeight = 600;
	
	/**
	 * Whether vsync is enabled
	 */
	protected boolean vsync = true;
	
	/**
	 * The error callback
	 */
	protected GLFWErrorCallback errorCallback;
	
	public BaseApp() {
		timer = new Timer();
	}
	
	/**
	 * Initializes the app, then runs the update/render loop until the app quits
	 */
	public void run() {
		init();
		loop();
		destroy();
	}
	
	/**
	 * Initializes GLFW and creates the window
	 */
	protected void init() {
		// Set a GLFW error callback
		errorCallback = GLFWErrorCallback.createPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		
		// Initialize GLFW
		if (!glfwInit()) {
			throw new RuntimeException("Failed to initialize GLFW");
		}
		
		// initialize the window and timer
		window = new Window(windowTitle, windowWidth, windowHeight, vsync);
		window.init();
		timer.init();
		
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
	}
	
	/**
	 * The update/render loop
	 */
	protected void loop() {
		float delta;
		
		while (!window.shouldClose()) {
			delta = timer.getDelta();
			
			// Update
			update(delta);
			timer.updateUPS();
			
			// Render
			render();
			timer.updateFPS();
			
			// Update the timer
			timer.update();
			
			// Update window
			window.update();
			
			// if vsync is disabled, synchronize the framerate
			if (!window.getVsync()) {
				sync(TARGET_FPS);
			}
		}
	}
	
	/**
	 * Destroys the window, frees the error callback, and terminates GLFW
	 */
	protected void destroy() {
		if (window != null) {
			window.destroy();
		}
		if (errorCallback != null) {
			errorCallback.free();
		}
		glfwTerminate();
	}
	
	/**
	 * Synchronizes to specified fps
	 * @param fps frames per second
	 */
	protected void sync(int fps) {
		double lastLoopTime = timer.getLastLoopTime();
		double now = timer.getTime();
		float targetTime = 1f / fps;
		
		while (now - lastLoopTime < targetTime) {
			Thread.yield();
			
			try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(BaseApp.class.getName()).log(Level.SEVERE, null, ex);
            }
			
			now = timer.getTime();
		}
	}
	
	/**
	 * Render the scene
	 */
	protected abstract void render();
	
	/**
	 * Update the scene
	 * @param delta Time since last update
	 */
	protected abstract void update(float delta);
	
	
}
