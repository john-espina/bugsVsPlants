package plant;

import javafx.scene.shape.Circle;

public class Plant extends Circle {

	// Attributes
	private int x;
	private int y;
	
	// Constructor
	public Plant(int x, int y, int radius){
		super(x,y,radius);
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
