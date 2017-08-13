package world;

import java.util.ArrayList;

import bug.Bug;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import plant.Plant;

/*
 * This class handles the KeyFrame event from the BugWorld
 * 
 * */
public class enemyBugHandler implements EventHandler<ActionEvent> {

	// Attributes
	Bug bug;
	ArrayList<Plant> plants;
	ArrayList<Bug> enemyBugs;
	static Group rootHandler;
	int width;
	int heigth;
	String direction;

	// Constructor
	public enemyBugHandler(ArrayList<Bug> enemyBugs, ArrayList<Plant> plants, Group root, int width, int heigth) {
		this.plants = plants;
		this.width = width;
		this.heigth = heigth;
		this.rootHandler = root;
		this.enemyBugs = enemyBugs;

	}

	@Override
	public void handle(ActionEvent event) {

		for (Bug b : enemyBugs) {
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

		}
	}
}
