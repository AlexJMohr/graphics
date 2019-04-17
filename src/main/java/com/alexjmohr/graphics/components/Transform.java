package com.alexjmohr.graphics.components;

import com.alexjmohr.graphics.Entity;
import org.joml.Vector3f;

public class Transform extends Component {

    /**
     * The transform of the parent entity
     */
    private Transform parent;

    /**
     * The entity's position
     */
    private Vector3f position;

    /**
     * The entity's rotation in euler angles
     */
    private Vector3f eulerAngles;

    /**
     * The entity's local scale
     */
    private Vector3f localScale;

    @Override
    public void start() {
        this.position = new Vector3f();
        this.eulerAngles = new Vector3f();
        this.localScale = new Vector3f();
    }

    /**
     * Move the position by delta
     * @param delta the amount to move the position by
     */
    public void translate(Vector3f delta) {
        position.add(delta);
    }

    /**
     * Get a copy the position vector
     * @return a copy of the position vector
     */
    public Vector3f getPosition() {
        return new Vector3f(position);
    }

    /**
     * Set the position
     * @param position the position to set
     */
    public void setPosition(Vector3f position) {
        this.position.set(position);
    }
}
