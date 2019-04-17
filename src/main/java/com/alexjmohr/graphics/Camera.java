package com.alexjmohr.graphics;

import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Camera class contains position and forward and up axis used for projection matrix calculations
 * @author Alex Mohr
 *
 */
public class Camera {

	public static final float MOVE_SPEED = 0.1f;

	public static final float SENSITIVITY = 0.05f;
	
	/**
	 * The eye position of the camera
	 */
	private Vector3f position;
	
	/**
	 * The direction the camera is looking
	 */
	private Vector3f forward;
	
	/**
	 * The up direction of the camera
	 */
	private Vector3f up;
	
	/**
	 * Creates a camera with specified position, forward direction, and up axis
	 * @param position the eye position
	 * @param forward  the look direction
	 * @param up       the up axis
	 */
	public Camera(Vector3f position, Vector3f forward, Vector3f up) {
		this.position = position;
		this.forward = forward;
		this.up = up;
	}
	
	/**
	 * Creates a camera with specified position and forward direction, with default up axis 0,1,0
	 * @param position
	 * @param forward
	 */
	public Camera(Vector3f position, Vector3f forward) {
		this(position, forward, new Vector3f(0, 1, 0));
	}

	/**
	 * Update the camera
	 * @param delta time since last update
	 */
	public void update(float delta) {
		Vector3f move = new Vector3f(0);

		if (Input.getKey(GLFW_KEY_A)) {
			move.x -= MOVE_SPEED;
		}
		if (Input.getKey(GLFW_KEY_D)) {
			move.x += MOVE_SPEED;
		}
		if (Input.getKey(GLFW_KEY_W)) {
			move.z += MOVE_SPEED;
		}
		if (Input.getKey(GLFW_KEY_S)) {
			move.z -= MOVE_SPEED;
		}
		if (Input.getKey(GLFW_KEY_SPACE)) {
			move.y += MOVE_SPEED;
		}
		if (Input.getKey(GLFW_KEY_LEFT_SHIFT)) {
			move.y -= MOVE_SPEED;
		}

		Vector3f right = new Vector3f(forward).cross(up).normalize();

		position.add(new Vector3f(right).mul(move.x));
		position.add(new Vector3f(up).mul(move.y));
		position.add(new Vector3f(forward).mul(move.z));

		Vector2f mouseDelta = Input.getMouseDelta().mul(SENSITIVITY);

		Quaternionf vertical = new Quaternionf().fromAxisAngleDeg(right, -mouseDelta.y);
		Quaternionf horizontal = new Quaternionf().fromAxisAngleDeg(up, -mouseDelta.x);

		Vector3f newForward = new Vector3f(forward).rotate(vertical).rotate(horizontal);
		forward = newForward;
	}
	
	/**
	 * Set the camera position
	 * @param position the new position to set
	 */
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	/**
	 * Set the camera position
	 * @param x the x position
	 * @param y the y position
	 * @param z the z position
	 */
	public void setPosition(float x, float y, float z) {
		this.position = new Vector3f(x, y, z);
	}
	
	/**
	 * Get the current position
	 * @return the current position
	 */
	public Vector3f getPosition() {
		return new Vector3f(position);
	}
	
	/**
	 * Set the camera forward axis
	 * @param forward the forward axis to set
	 */
	public void setForward(Vector3f forward) {
		this.forward = forward;
	}
	
	/**
	 * Set the camera forward axis
	 * @param x the x component
	 * @param y the y component
	 * @param z the z component
	 */
	public void setForward(float x, float y, float z) {
		this.forward = new Vector3f(x, y, z);
	}
	
	/**
	 * Get the camera forward axis
	 * @return the camera forward axis
	 */
	public Vector3f getForward() {
		return new Vector3f(forward);
	}
	
	/**
	 * Set the camera's up axis
	 * @param up the up axis to set
	 */
	public void setUp(Vector3f up) {
		this.up = up;
	}
	
	/**
	 * Set the camera's up axis
	 * @param x the x component
	 * @param y the y component
	 * @param z the z component
	 */
	public void setUp(float x, float y, float z) {
		this.up = new Vector3f(x, y, z);
	}
	
	/**
	 * Get the camera up axis
	 * @return the up axis
	 */
	public Vector3f getUp() {
		return new Vector3f(up);
	}
}
