package com.alexjmohr.graphics;

import org.joml.Vector2f;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    /**
     * GLFW window handle, set by init()
     */
    private static long win;

    /**
     * Keys that were just pressed this update
     */
    private static boolean[] keysPressed = new boolean[GLFW_KEY_LAST + 1];

    /**
     * Keys that were pressed last update
     */
    private static boolean[] keysPressedLastUpdate = new boolean[GLFW_KEY_LAST + 1];

    /**
     * Current mouse position
     */
    private static Vector2f mousePosition = new Vector2f(0, 0);

    /**
     * Mouse position last update.
     */
    private static Vector2f lastMousePosition = new Vector2f(0, 0);

    /**
     * Used to prevent the first getMouseDelta() from returning a larger-than-expected vector
     */
    private static boolean mouseInitialized = false;


    /**
     * Set up the callbacks
     * @param window The window handle to attach callbacks to
     */
    public static void init(Window window) {
        win = window.getWindow();

        // Hide the cursor and lock it inside the window
        glfwSetInputMode(win, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        // Polling all keys every frame instead of using a callback for most keys, so keys should
        // remain in GLFW_PRESS state until glfwGetKey is called, at which point they will change
        // to GLFW_RELEASE state. This is to prevent missing key events between frames.
        glfwSetInputMode(win, GLFW_STICKY_KEYS, GLFW_TRUE);
    }

    /**
     * Return the current mouse position in screen space
     * @return the current mouse position
     */
    public static Vector2f getMousePosition() {
        return new Vector2f(mousePosition);
    }

    /**
     * Return the difference between the mouse's current position and last position
     * @return change in mouse position from last update.
     */
    public static Vector2f getMouseDelta() {
        if (!mouseInitialized) {
            lastMousePosition.set(mousePosition);
            mouseInitialized = true;
            return new Vector2f(0, 0);
        }
        return new Vector2f(mousePosition).sub(lastMousePosition);
    }

    /**
     * Returns true if the key was pressed and has not been released yet.
     * @param key GLFW key code (e.g. GLFW_KEY_A)
     * @return true if key is down
     */
    public static boolean getKey(int key) {
        if (key < 0 || key > GLFW_KEY_LAST) {
            return false;
        }
        return keysPressed[key] || keysPressedLastUpdate[key];
    }

    /**
     * Returns true if key was just pressed, and was not pressed last update.
     * @param key GLFW key code (e.g. GLFW_KEY_A)
     * @return true if the key was just pressed
     */
    public static boolean getKeyDown(int key) {
        if (key < 0 || key > GLFW_KEY_LAST) {
            return false;
        }
        return keysPressed[key] && !keysPressedLastUpdate[key];
    }

    /**
     * Poll for mouse movement and key state changes
     */
    public static void update() {

        // Save last mouse position
        lastMousePosition.set(mousePosition);

        // Get new mouse position
        try (MemoryStack stack = MemoryStack.stackPush()) {
            DoubleBuffer xpos = stack.mallocDouble(1);
            DoubleBuffer ypos = stack.mallocDouble(1);
            glfwGetCursorPos(win, xpos, ypos);

            mousePosition.set((float) xpos.get(), (float) ypos.get());
        }

        // Swap key buffers
        boolean[] temp = keysPressed;
        keysPressed = keysPressedLastUpdate;
        keysPressedLastUpdate = temp;

        // Poll for key state changes. GLFW_KEY_SPACE is the first valid key and GLFW_KEY_LAST is
        // the last valid key.
        for (int key = GLFW_KEY_SPACE; key <= GLFW_KEY_LAST; key++) {
            int status = glfwGetKey(win, key);

            if (status == GLFW_PRESS) {
                keysPressed[key] = true;
            } else if (status == GLFW_RELEASE) {
                keysPressed[key] = false;
            }
        }
    }
}
