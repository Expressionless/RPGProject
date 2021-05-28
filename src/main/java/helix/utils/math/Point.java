package helix.utils.math;

public class Point {

	private float x, y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(double x, double y) {
		this((float)x, (float)y);
	}
	
	public Point sub(Point other) {		
		return new Point(this.x - other.x, this.y - other.y);
	}
	
	public Point sub(Vector2 other) {
		return this.sub(other.toPoint()).copy();
	}
	
	public Point add(Point other) {
		return new Point(this.x + other.x, this.y + other.y);
	}
	
	public Point add(Vector2 other) {
		return this.add(new Point(other.getX(), other.getY()));
	}
	
	public Point multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;

		return new Point(this.x * scalar, this.y * scalar);
	}
	
	public Vector2 toVector2() {
		return new Vector2(x, y);
	}
	
	// Getters and Setters
	
	public float getDistTo(Point other) {
		double disX = Math.pow(other.x - x, 2);
		double disY = Math.pow(other.y - y, 2);
		return (float)Math.pow(disX + disY, 0.5);
	}
	
	public float getDistTo(float x, float y) {
		return getDistTo(new Point(x, y));
	}
	
	public String toString() {
		return "[Point x="+x+"y="+y+"]";
	}
	
	public Point copy() {
		return new Point(x, y);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public float length() {
		double disX = Math.pow(this.x, 2);
		double disY = Math.pow(this.y, 2);
		
		return (float)Math.pow(disX + disY, 0.5);
	}
}
