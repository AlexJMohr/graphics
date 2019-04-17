package com.alexjmohr.graphics.components;

import com.alexjmohr.graphics.Entity;

/**
 * Base component class
 */
public abstract class Component {

    /**
     * The entity this component is attached to
     */
    protected Entity entity;

    /**
     * An easy reference to the entity's transform
     */
    protected Transform transform;

    /**
     * Called when the scene starts, before the first update
     */
    public void start() {}

    /**
     * Update the component
     * @param delta time since last update
     */
    public void update(float delta) {}

    /**
     * Set the entity that this component updates
     * @param entity the entity to update
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Get the entity the component is attached to
     * @return the entity the component is attached to
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Get the transform of the component
     * @return the transform
     */
    public Transform getTransform() {
        return transform;
    }
}
