package helix.utils.math;


public class Rectangle extends com.badlogic.gdx.math.Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2175678676093573672L;

	public boolean contains(Point p) {
		return this.contains(new Vector2(p.getX(), p.getY()));
	}
	
	public boolean contains(Vector2 v) {
		return super.contains(new com.badlogic.gdx.math.Vector2(v.getX(), v.getY()));
	}
	
}
