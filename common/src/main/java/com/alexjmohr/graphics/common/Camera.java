package com.alexjmohr.graphics.common;

import org.joml.Vector3f;

/**
 * Camera class contains position and forward and up axis used for projection matrix calculations
 * @author Alex Mohr
 *
 */
public class Camera {
	
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
		return position;
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
		return forward;
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
		return up;
	}
}
