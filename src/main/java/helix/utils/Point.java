package helix.utils;

public class Point {

	private float x, y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void sub(Point other) {
		this.x -= other.x;
		this.y -= other.y;
	}
	
	public void add(Point other) {
		this.x += other.x;
		this.y += other.y;
	}
	
	public void add(Vector2 other) {
		this.add(new Point(other.getX(), other.getY()));
	}
	
	public void multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}
	
	public Point getUnitVector(Point other) {
		double xD = other.x - x;
		double yD = other.y - y;
		double mag = this.getDistTo(other);
		
		xD /= mag;
		yD /= mag;
		
		return new Point((float)xD, (float)yD);
	}
	
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
}
