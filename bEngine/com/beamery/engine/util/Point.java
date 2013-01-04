package com.beamery.engine.util;

public class Point {

	public float x;
	public float y;

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Point add(Point other) {

		float x = this.x + other.x;
		float y = this.y + other.y;

		return new Point(x, y);
	}

	public Point subtract(Point other) {

		float x = this.x - other.x;
		float y = this.y - other.y;

		return new Point(x, y);
	}
}
