package bug;

import javafx.scene.shape.Circle;

public class Bug extends Circle {

	private Speed s; 
	private int x;
	private int y;
	
	// Constructor
	public Bug (int x, int y, int radius, Speed s){
		super (x, y, radius);
		this.s = s;
		this.setX(x);
		this.setY(y);
	}

	// Getter and Setter
	public Speed getS() {return s;}
	public void setS(Speed s) {this.s = s;}
	
	// Methods
	
	public void move(){
		
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
