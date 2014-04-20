package com.base.engine.rendering;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.base.engine.core.Vector2f;

public class Window {

	public static void createWindow(int width, int height, String title, boolean fullscreen, boolean vsync) {
		Display.setTitle(title);
		try {
			setDisplayMode(width, height, fullscreen);
			setVSync(vsync);
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void render() {
		Display.update();
	}

	public static void dispose() {
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}

	public static boolean isCloseRequested() {
		return Display.isCloseRequested();
	}

	public static int getWidth() {
		return Display.getDisplayMode().getWidth();
	}

	public static int getHeight() {
		return Display.getDisplayMode().getHeight();
	}

	public static String getTitle() {
		return Display.getTitle();
	}

	public static Vector2f getCenter() {
		return new Vector2f(getWidth() / 2, getHeight() / 2);
	}

	public static void setDisplayMode(int width, int height, boolean fullscreen) {

		try {
			DisplayMode targetDisplayMode = new DisplayMode(width, height);

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								System.out.println("found");
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width, height);
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
		}
		return;
	}
	
	public static void setVSync(boolean vsync) {
		Display.setVSyncEnabled(vsync);
	}
}
