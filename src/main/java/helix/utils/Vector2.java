package helix.utils;

public class Vector2 {

	private float x, y;

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void sub(Vector2 other) {
		this.x -= other.x;
		this.y -= other.y;
	}

	public void sub(float x, float y) {
		this.sub(new Vector2(x, y));
	}

	public void add(Vector2 other) {
		this.x += other.x;
		this.y += other.y;
	}

	public void add(float x, float y) {
		this.add(new Vector2(x, y));
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
		double sin = 0,
			   asin = 0,
			   cos = 0,
			   acos = 0,
			   tan = 0,
			   atan = 0;
		
		double len = this.length();
		if (len == 0)
			return 0.0;

		sin = y / len;
		cos = x / len;
		if(x != 0)
			tan = y / x;

		asin = Math.asin(sin);
		acos = Math.acos(cos);
		atan = Math.atan(tan);
		return asin;
	}

	public Vector2 copy() {
		return new Vector2(x, y);
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
		return "[x=" + x + ",y=" + y + "]";
	}
}
