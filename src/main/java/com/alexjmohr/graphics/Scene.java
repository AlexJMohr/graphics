package com.alexjmohr.graphics;

import java.util.*;

/**
 * Scene object that holds a reference to the main camera and a list of entities in the scene.
 */
public class Scene {

    /**
     * The camera to render from
     */
    private Camera camera;

    /**
     * List of entities in the scene
     */
    private List<Entity> entityList;

    public Scene(Camera camera) {
        entityList = new ArrayList<>();
        setCamera(camera);
    }

    /**
     * Initialize entityList
     */
    public void start() {
        for (Entity entity : entityList) {
            entity.start();
        }
    }

    /**
     * Update the camera, then all entites in the scene.
     * @param delta
     */
    public void update(float delta) {
        camera.update(delta);
        for (Entity entity : entityList) {
            entity.update(delta);
        }
    }

    /**
     * Render all entities
     */
    public void render() {

    }

    /**
     * Add an entity to the scene
     * @param entity the entity to add
     */
    public void addEntity(Entity entity) {
        this.entityList.add(entity);
    }

    /**
     * Remove an entity from the scene
     * @param entity the entity to remove
     */
    public void removeEntity(Entity entity) {
        this.entityList.remove(entity);
    }

    /**
     * Set the camera
     * @param camera the camera to set
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Get the camera
     * @return the camera
     */
    public Camera getCamera() {
        return camera;
    }
}
