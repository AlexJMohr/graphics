package com.alexjmohr.graphics.common;

import java.util.logging.Logger;

/**
 * Creates a blank window
 * @author Alex Mohr
 *
 */
public class App extends BaseApp {
	
	private Logger logger;
	
	public App() {
		// Override the default window title from BaseApp
		windowTitle = "Alex Mohr Graphics - Common";
		logger = Logger.getLogger(App.class.getName());
	}

	@Override
	protected void render() {
		logger.info("FPS: " + timer.getFPS());
	}

	@Override
	protected void update(float delta) {
		logger.info("UPS " + timer.getUPS());
	}
	
	public static void main( String[] args ) {
    	new App().run();
    }
}
