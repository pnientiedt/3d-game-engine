package com.base.engine.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.base.engine.rendering.Window;

public class Input {

	// All these constants come from LWJGL's Keyboard class
	public final static int KEY_NONE = 0x00;
	public final static int KEY_ESCAPE = 0x01;
	public final static int KEY_1 = 0x02;
	public final static int KEY_2 = 0x03;
	public final static int KEY_3 = 0x04;
	public final static int KEY_4 = 0x05;
	public final static int KEY_5 = 0x06;
	public final static int KEY_6 = 0x07;
	public final static int KEY_7 = 0x08;
	public final static int KEY_8 = 0x09;
	public final static int KEY_9 = 0x0A;
	public final static int KEY_0 = 0x0B;
	public final static int KEY_MINUS = 0x0C; /* - on main keyboard */
	public final static int KEY_EQUALS = 0x0D;
	public final static int KEY_BACK = 0x0E; /* backspace */
	public final static int KEY_TAB = 0x0F;
	public final static int KEY_Q = 0x10;
	public final static int KEY_W = 0x11;
	public final static int KEY_E = 0x12;
	public final static int KEY_R = 0x13;
	public final static int KEY_T = 0x14;
	public final static int KEY_Y = 0x15;
	public final static int KEY_U = 0x16;
	public final static int KEY_I = 0x17;
	public final static int KEY_O = 0x18;
	public final static int KEY_P = 0x19;
	public final static int KEY_LBRACKET = 0x1A;
	public final static int KEY_RBRACKET = 0x1B;
	public final static int KEY_RETURN = 0x1C; /* Enter on main keyboard */
	public final static int KEY_LCONTROL = 0x1D;
	public final static int KEY_A = 0x1E;
	public final static int KEY_S = 0x1F;
	public final static int KEY_D = 0x20;
	public final static int KEY_F = 0x21;
	public final static int KEY_G = 0x22;
	public final static int KEY_H = 0x23;
	public final static int KEY_J = 0x24;
	public final static int KEY_K = 0x25;
	public final static int KEY_L = 0x26;
	public final static int KEY_SEMICOLON = 0x27;
	public final static int KEY_APOSTROPHE = 0x28;
	public final static int KEY_GRAVE = 0x29; /* accent grave */
	public final static int KEY_LSHIFT = 0x2A;
	public final static int KEY_BACKSLASH = 0x2B;
	public final static int KEY_Z = 0x2C;
	public final static int KEY_X = 0x2D;
	public final static int KEY_C = 0x2E;
	public final static int KEY_V = 0x2F;
	public final static int KEY_B = 0x30;
	public final static int KEY_N = 0x31;
	public final static int KEY_M = 0x32;
	public final static int KEY_COMMA = 0x33;
	public final static int KEY_PERIOD = 0x34; /* . on main keyboard */
	public final static int KEY_SLASH = 0x35; /* / on main keyboard */
	public final static int KEY_RSHIFT = 0x36;
	public final static int KEY_MULTIPLY = 0x37; /* * on numeric keypad */
	public final static int KEY_LMENU = 0x38; /* left Alt */
	public final static int KEY_LALT = KEY_LMENU; /* left Alt */
	public final static int KEY_SPACE = 0x39;
	public final static int KEY_CAPITAL = 0x3A;
	public final static int KEY_F1 = 0x3B;
	public final static int KEY_F2 = 0x3C;
	public final static int KEY_F3 = 0x3D;
	public final static int KEY_F4 = 0x3E;
	public final static int KEY_F5 = 0x3F;
	public final static int KEY_F6 = 0x40;
	public final static int KEY_F7 = 0x41;
	public final static int KEY_F8 = 0x42;
	public final static int KEY_F9 = 0x43;
	public final static int KEY_F10 = 0x44;
	public final static int KEY_NUMLOCK = 0x45;
	public final static int KEY_SCROLL = 0x46; /* Scroll Lock */
	public final static int KEY_NUMPAD7 = 0x47;
	public final static int KEY_NUMPAD8 = 0x48;
	public final static int KEY_NUMPAD9 = 0x49;
	public final static int KEY_SUBTRACT = 0x4A; /* - on numeric keypad */
	public final static int KEY_NUMPAD4 = 0x4B;
	public final static int KEY_NUMPAD5 = 0x4C;
	public final static int KEY_NUMPAD6 = 0x4D;
	public final static int KEY_ADD = 0x4E; /* + on numeric keypad */
	public final static int KEY_NUMPAD1 = 0x4F;
	public final static int KEY_NUMPAD2 = 0x50;
	public final static int KEY_NUMPAD3 = 0x51;
	public final static int KEY_NUMPAD0 = 0x52;
	public final static int KEY_DECIMAL = 0x53; /* . on numeric keypad */
	public final static int KEY_F11 = 0x57;
	public final static int KEY_F12 = 0x58;
	public final static int KEY_F13 = 0x64; /* (NEC PC98) */
	public final static int KEY_F14 = 0x65; /* (NEC PC98) */
	public final static int KEY_F15 = 0x66; /* (NEC PC98) */
	public final static int KEY_KANA = 0x70; /* (Japanese keyboard) */
	public final static int KEY_CONVERT = 0x79; /* (Japanese keyboard) */
	public final static int KEY_NOCONVERT = 0x7B; /* (Japanese keyboard) */
	public final static int KEY_YEN = 0x7D; /* (Japanese keyboard) */
	public final static int KEY_NUMPADEQUALS = 0x8D; /*
													 * = on numeric keypad (NEC
													 * PC98)
													 */
	public final static int KEY_CIRCUMFLEX = 0x90; /* (Japanese keyboard) */
	public final static int KEY_AT = 0x91; /* (NEC PC98) */
	public final static int KEY_COLON = 0x92; /* (NEC PC98) */
	public final static int KEY_UNDERLINE = 0x93; /* (NEC PC98) */
	public final static int KEY_KANJI = 0x94; /* (Japanese keyboard) */
	public final static int KEY_STOP = 0x95; /* (NEC PC98) */
	public final static int KEY_AX = 0x96; /* (Japan AX) */
	public final static int KEY_UNLABELED = 0x97; /* (J3100) */
	public final static int KEY_NUMPADENTER = 0x9C; /* Enter on numeric keypad */
	public final static int KEY_RCONTROL = 0x9D;
	public final static int KEY_NUMPADCOMMA = 0xB3; /*
													 * , on numeric keypad (NEC
													 * PC98)
													 */
	public final static int KEY_DIVIDE = 0xB5; /* / on numeric keypad */
	public final static int KEY_SYSRQ = 0xB7;
	public final static int KEY_RMENU = 0xB8; /* right Alt */
	public final static int KEY_RALT = KEY_RMENU; /* right Alt */
	public final static int KEY_PAUSE = 0xC5; /* Pause */
	public final static int KEY_HOME = 0xC7; /* Home on arrow keypad */
	public final static int KEY_UP = 0xC8; /* UpArrow on arrow keypad */
	public final static int KEY_PRIOR = 0xC9; /* PgUp on arrow keypad */
	public final static int KEY_LEFT = 0xCB; /* LeftArrow on arrow keypad */
	public final static int KEY_RIGHT = 0xCD; /* RightArrow on arrow keypad */
	public final static int KEY_END = 0xCF; /* End on arrow keypad */
	public final static int KEY_DOWN = 0xD0; /* DownArrow on arrow keypad */
	public final static int KEY_NEXT = 0xD1; /* PgDn on arrow keypad */
	public final static int KEY_INSERT = 0xD2; /* Insert on arrow keypad */
	public final static int KEY_DELETE = 0xD3; /* Delete on arrow keypad */
	public final static int KEY_LMETA = 0xDB; /* Left Windows/Option key */
	public final static int KEY_LWIN = KEY_LMETA; /* Left Windows key */
	public final static int KEY_RMETA = 0xDC; /* Right Windows/Option key */
	public final static int KEY_RWIN = KEY_RMETA; /* Right Windows key */
	public final static int KEY_APPS = 0xDD; /* AppMenu key */
	public final static int KEY_POWER = 0xDE;
	public final static int KEY_SLEEP = 0xDF;
	
	public final static int MOUSE_LEFT = 0;
	public final static int MOUSE_RIGHT = 1;
	
	private static int deltaUsed = 0;
	
	public enum MouseState {
		CURSOR, DELTA
	}

	public final static int NUM_KEYCODES = 256;
	public final static int NUM_MOUSEBUTTONS = 10;

	private static boolean[] lastKeys = new boolean[NUM_KEYCODES];
	private static boolean[] lastMouse = new boolean[NUM_MOUSEBUTTONS];
	
	private static ArrayList<Integer> consumedKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> consumedMouse = new ArrayList<Integer>();
	
	private static MouseState mouseState = MouseState.CURSOR;
	private static Vector2f initialMousePosition;
	
	public static HashMap<Integer, Character> lowerCase;
	public static HashMap<Integer, Character> upperCase;

	public static void update() {
		consumedKeys.clear();
		consumedMouse.clear();
		
		for (int i = 0; i < NUM_KEYCODES; i++)
			lastKeys[i] = getKey(i);

		for (int i = 0; i < NUM_MOUSEBUTTONS; i++)
			lastMouse[i] = getMouse(i);
		
		if (Input.mouseState == MouseState.DELTA)
			setMousePosition(Window.getCenter());
	}

	public static boolean getKey(int keyCode) {
		return Keyboard.isKeyDown(keyCode);
	}

	public static boolean getKeyDown(int keyCode) {
		return getKey(keyCode) && !lastKeys[keyCode];
	}

	public static boolean getKeyUp(int keyCode) {
		return !getKey(keyCode) && lastKeys[keyCode];
	}
	
	public static void setKeyConsumed(int keyCode) {
		consumedKeys.add(keyCode);
	}
	
	public static boolean isKeyConsumed(int keyCode) {
		return consumedKeys.contains(keyCode);
	}

	public static boolean getMouse(int mouseButton) {
		return Mouse.isButtonDown(mouseButton);
	}

	public static boolean getMouseDown(int mouseButton) {
		return getMouse(mouseButton) && !lastMouse[mouseButton];
	}

	public static boolean getMouseUp(int mouseButton) {
		return !getMouse(mouseButton) && lastMouse[mouseButton];
	}
	
	public static void setMouseConsumed(int mouseButton) {
		consumedMouse.add(mouseButton);
	}
	
	public static boolean isMouseConsumed(int mouseButton) {
		return consumedMouse.contains(mouseButton);
	}

	public static Vector2f getMousePosition() {
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}

	public static void setMousePosition(Vector2f pos) {
		Mouse.setCursorPosition((int) pos.getX(), (int) pos.getY());
	}

	public static void setCursor(boolean enabled) {
		Mouse.setGrabbed(!enabled);
	}
	
	public static void subscribeDelta() {
		deltaUsed++;
		setMouseState(MouseState.DELTA);
	}
	
	public static void releaseDelta() {
		deltaUsed--;
		if (deltaUsed < 1)
			setMouseState(MouseState.CURSOR);
	}
	
	private static void setMouseState(MouseState mouseState) {
		if (Input.mouseState != mouseState) {
			Input.mouseState = mouseState;
			if(mouseState == MouseState.CURSOR) {
				setCursor(true);
				setMousePosition(initialMousePosition);
			}
			else if (mouseState == MouseState.DELTA) {
				setCursor(false);
				initialMousePosition = getMousePosition();
				setMousePosition(Window.getCenter());
			}
		}
	}
	
	public static Vector2f getMouseDelta() {
		if (Input.mouseState == MouseState.DELTA)
			return getMousePosition().sub(Window.getCenter());
		else
			return new Vector2f(0,0);
	}
	
	private static void initKeyMap() {
		if (lowerCase != null && upperCase != null)
			return;
		lowerCase = new HashMap<Integer, Character>();
		upperCase = new HashMap<Integer, Character>();
		
		addToMap(43, '^', '°');
		addToMap(2, '1', '!');
		addToMap(3, '2', '"');
		addToMap(4, '3', '§');
	}
	
	private static void addToMap(int keyCode, char lower, char upper) {
		lowerCase.put(keyCode, lower);
		upperCase.put(keyCode, upper);
	}
	
	public static Set<Integer> getWritableKeycodes() {
		initKeyMap();
		return lowerCase.keySet();
	}
	
	public static char getLowerCase(int keyCode) {
		initKeyMap();
		return lowerCase.get(keyCode);
	}
	
	public static char getUpperCase(int keyCode) {
		initKeyMap();
		return upperCase.get(keyCode);
	}
}
