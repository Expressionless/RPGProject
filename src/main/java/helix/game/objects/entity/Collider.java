package helix.game.objects.entity;

import helix.game.objects.Entity;
import helix.utils.math.Vector2;

public class Collider extends helix.utils.math.Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = -829325656055031009L;
	
	private Vector2 offset;
	
	public Collider(Entity entity) {
		this(entity, ColliderOffset.TOP_LEFT);
	}
	
	public Collider(Entity entity, ColliderOffset offsetType) {
		offset = new Vector2(0, 0);
		float entityWidth = entity.getWidth();
		float entityHeight = entity.getHeight();
		
		switch(offsetType) {
			case BOTTOM:
				offset.setX(entityWidth / 2);
				offset.setY(entityHeight);
				break;
			case BOTTOM_LEFT:
				offset.setX(0);
				offset.setY(entityHeight);
				break;
			case BOTTOM_RIGHT:
				offset.setX(entityWidth);
				offset.setY(entityHeight);
				break;
			case CENTER:
				offset.setX(entityWidth / 2);
				offset.setY(entityHeight / 2);
				break;
			case CENTER_LEFT:
				offset.setX(0);
				offset.setY(entityHeight / 2);
				break;
			case CENTER_RIGHT:
				offset.setX(entityWidth);
				offset.setY(entityHeight / 2);
				break;
			case TOP:
				offset.setX(entityWidth / 2);
				offset.setY(0);
				break;
			case TOP_LEFT:
				offset.setX(0);
				offset.setY(0);
				break;
			case TOP_RIGHT:
				offset.setX(entityWidth);
				offset.setY(0);
				break;
			default:
				offset.setX(0);
				offset.setY(0);
				break;
		}
	}
	
	public Vector2 getOffset() {
		return this.offset;
	}
	
	public float getXOffset() {
		return offset.getX();
	}
	
	public float getYOffset() {
		return offset.getY();
	}
	
	public void setOffset(float x, float y) {
		this.offset.setX(x);
		this.offset.setY(y);
	}
	
	public void setXOffset(float x) {
		this.offset.setX(x);
	}
	
	public void setYOffset(float y) {
		this.offset.setY(y);
	}
	
	public String toString() {
		return "Collider ["
				+ "offset=" + offset.toString()
				+ ",bounds=[w=" + this.getWidth() + ",h=" + this.getHeight()
				+ "]";
	}
}
