package helix.utils.math;

import java.text.DecimalFormat;

public class Vector2 {
	private static final DecimalFormat df = new DecimalFormat("0.00");

	private float x, y;

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Number x, Number y) {
		this.x = x.floatValue();
		this.y = y.floatValue();
	}

	public Vector2 sub(Vector2 other) {
		return new Vector2(this.x - other.x, this.y - other.y);
	}

	public Vector2 sub(float x, float y) {
		return this.sub(new Vector2(x, y));
	}

	public Vector2 add(Vector2 other) {
		return new Vector2(other.x + x, other.y + y);
	}

	public Vector2 add(float x, float y) {
		return this.add(new Vector2(x, y));
	}

	public Vector2 multiply(float scalar) {
		return new Vector2(this.x * scalar, this.y * scalar);
	}

	public double length() {
		double disX = Math.pow(this.x, 2);
		double disY = Math.pow(this.y, 2);

		return Math.pow(disX + disY, 0.5);
	}

	public double getAngle() {
		double angle = 0;
		double sin = 0,
			   asin = 0,
			   cos = 0;
		
		double len = this.length();
		if (len == 0)
			return 0.0;

		sin = y / len;
		cos = x / len;
		asin = Math.asin(sin);
		
		boolean firstQuadrant = (sin >= 0 && cos >= 0);
		boolean secondQuadrant = (sin >= 0 && cos < 0);
		boolean thirdQuadrant = (sin < 0 && cos < 0);
		boolean fourthQuadrant = (sin < 0 && cos >= 0);
		
		if(firstQuadrant)
			angle = asin;
		else if(secondQuadrant)
			angle = Math.PI - asin;
		else if(thirdQuadrant)
			angle = Math.PI - asin;
		else if(fourthQuadrant)
			angle = 2 * Math.PI + asin;
		
		return Double.parseDouble(df.format(Math.toDegrees(angle)));
	}
	
	public Vector2 getUnitVector() {
		Vector2 vector = this.copy();
		double mag = vector.length();
		if(mag == 0)
			return new Vector2(0 ,0);
		
		vector = new Vector2(vector.getX() / mag, vector.getY() / mag);
		return vector;
	}

	public Vector2 copy() {
		return new Vector2(x, y);
	}
	
	public Point toPoint() {
		return new Point(x, y);
	}
	
	// Getters and Setters
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vector2 ["
				+ "x=" + x
				+ ",y=" + y
				+ ",len=" + this.length()
				+ "]";
	}
}
