package world;



import java.util.ArrayList;

import bug.Bug;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import plant.Plant;

public class BugMovementHandler implements EventHandler<ActionEvent>{

	// Attributes
	ArrayList <Bug> bugs;
	ArrayList <Plant> plants;
	int width;
	int heigth;
	
	
	private static boolean isColliding(Bug b, Plant p) {
		return Math.sqrt(b.getTranslateX() + p.getX()) < (b.getRadius() + p.getRadius());
	}
	
	
	
	public BugMovementHandler(ArrayList<Bug>bugs, ArrayList<Plant> plants, int width, int heigth){
		this.bugs = bugs;
		this.plants= plants;
		this.width= width;
		this.heigth= heigth;
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		for (Bug b : bugs) {
			// checking the bug against the world's dimension
			if (b.getCenterX() + b.getTranslateX() < b.getRadius()
					|| b.getCenterX() + b.getTranslateX() + b.getRadius() > width) {

				// This sets the bug's dx to the opposite value
				b.getS().setDx(-b.getS().getDx());
			}

			if (b.getCenterY() + b.getTranslateY() < b.getRadius()
					|| b.getCenterY() + b.getTranslateY() + b.getRadius() > heigth) {

				// This sets the bug's dy to the opposite value
				b.getS().setDy(-b.getS().getDy());
			}

			// This is setting the value of the Translate X and Y using
			// the
			// new dx and dy value that was set above
			// translateX and translateY is the new position of the bug
			b.setTranslateX(b.getTranslateX() + b.getS().getDx());
			b.setTranslateY(b.getTranslateY() + b.getS().getDy());

			// This next section will check if the
			// bug and the plant is coliding
			for (int i = 0; i < plants.size(); i++) {
				if (isColliding(b, plants.get(i))) {
					// if they are colliding,
					// remove the plant from the world
					plants.remove(i);
				}

			}
		}
	}


}
