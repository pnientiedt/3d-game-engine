package com.base.engine.core;

public class Time {

	public final static long SECOND = 1000000000L;

	public static double delta;

	public static double getTime() {
		return (double) System.nanoTime() / (double) SECOND;
	}

}
