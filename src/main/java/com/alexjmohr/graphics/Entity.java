package com.alexjmohr.graphics;

import com.alexjmohr.graphics.components.*;

import java.util.*;

/**
 * Scene entity
 */
public class Entity {

    /**
     * A reference to the scene that the entity is in
     */
    private Scene scene;

    /**
     * Easy access to the entity's transform component
     */
    private Transform transform;

    /**
     * List of components attached to the entity
     */
    private List<Component> componentList;

    /**
     * Create an entity with a reference to the specified scene. The entity must be added manually
     * to the scene after creating it using scene.addEntity(entity)
     * @param scene the scene that the entity will be in
     */
    public Entity(Scene scene) {
        this.scene = scene;
        componentList = new ArrayList<>();
        transform = new Transform();
        addComponent(transform);
    }

    /**
     * Create and add a component with specified type using it's default constructor
     * @param type the type of the new component to create and add
     */
    public void addComponent(Class<? extends Component> type) {
        try {
            Component component = type.getDeclaredConstructor().newInstance();
            component.setEntity(this);
            componentList.add(component);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a component to the entity
     * @param component the component to add
     */
    public void addComponent(Component component) {
        componentList.add(component);
    }

    /**
     * Get the component with the specified class
     * @param type class of component to get
     * @param <T> type extends Component
     * @return Component with specified type
     */
    public <T extends Component> T getComponent(Class<T> type) {

        for (Component component : componentList) {
            if (type.isAssignableFrom(component.getClass())) {
                return (T) component;
            }
        }
        return null;
    }

    /**
     * Return true if the entity has a component of the specified type
     * @param type type of component to look for
     * @return true if entity has component, false otherwise
     */
    public boolean hasComponent(Class<? extends Component> type) {
        for (Component component : componentList) {
            if (type.isAssignableFrom(component.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Initialize the components
     */
    public void start() {
        for (Component component : componentList) {
            component.start();
        }
    }

    /**
     * Update the entity's components
     * @param delta time since last update
     */
    public void update(float delta) {
        for (Component component : componentList) {
            component.update(delta);
        }
    }

    /**
     * Get the scene the entity is in
     * @return the scene the entity is in
     */
    public Scene getScene() {
        return scene;
    }
}
