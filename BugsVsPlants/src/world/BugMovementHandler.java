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
public class BugMovementHandler implements EventHandler<ActionEvent> {

	// Attributes
	Bug bug;
	ArrayList<Plant> plants;
	ArrayList<Bug> enemyBugs;
	static Group rootHandler;
	int width;
	int heigth;
	String direction;

	// Constructor
	public BugMovementHandler(Bug b, ArrayList<Plant> plants, Group root, int width, int heigth, String d) {
		this.bug = b;
		this.plants = plants;
		this.width = width;
		this.heigth = heigth;
		this.direction = d;
		this.rootHandler = root;

	}

	public BugMovementHandler(ArrayList<Bug> enemyBugs, Bug b, ArrayList<Plant> plants, Group root, int width,
			int heigth) {
		this.bug = b;
		this.enemyBugs = enemyBugs;
		this.plants = plants;
		this.width = width;
		this.heigth = heigth;
		this.rootHandler = root;
	}

	public BugMovementHandler(ArrayList<Bug> enemyBugs, ArrayList<Plant> plants, Group root, int width, int heigth) {
		this.plants = plants;
		this.width = width;
		this.heigth = heigth;
		this.rootHandler = root;
		this.enemyBugs = enemyBugs;

	}

	private static void isBugColliding() {
		boolean bugColliding = false;
		// for (int i=0; )
	}

	// Checks if a bug is colliding with a plant
	private static void eatPlant(Bug bug, ArrayList<Plant> plants) {
		boolean eaten = false;
		for (int i = 0; i < plants.size(); i++) {
			boolean isColiding = false;
			if (bug.getBoundsInParent().intersects(plants.get(i).getBoundsInParent())) {
				isColiding = true;
				if (isColiding = true) {
					plants.remove(i);
					BugWorld.setPlantsEaten(BugWorld.getPlantsEaten() + 1);
					eaten = true;
					if (eaten == true) {
						rootHandler.getChildren().remove(i);
						bug.setRadius(bug.getRadius() + 0.2);
					}

				}
			}
		}
	}

	// This method will check that the bug stays within the world
	// This was inside the event handler previously
	// Decided to place in a separate method
	// This way, the event handler will be shorter and
	// will be easily maintainable
	private void checkWorldCollision() {

		if (bug.getCenterX() + bug.getTranslateX() < bug.getRadius()
				|| bug.getCenterX() + bug.getTranslateX() + bug.getRadius() > width) {

			// This sets the bug's dx to the opposite value
			bug.getS().setDx(-bug.getS().getDx());
		}

		if (bug.getCenterY() + bug.getTranslateY() < bug.getRadius()
				|| bug.getCenterY() + bug.getTranslateY() + bug.getRadius() > heigth) {

			// This sets the bug's dy to the opposite value
			bug.getS().setDy(-bug.getS().getDy());
		}
	}
	
	private void moveEnemyBugs(ArrayList<Bug> enemyBugs, int width, int heigth){
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

		

		}

	}

	// This method handles the event from the KeyFrame
	@Override
	public void handle(ActionEvent event) {
		// Includes the checking for collison with wall
		// so that the bug stays in the world
		checkWorldCollision();
		
		//moveEnemyBugs(enemyBugs, width, heigth);

		// Checks the direction that was passed on
		// the value of Dx and Dy will be changed
		// depending on the direction
		if (direction.equalsIgnoreCase("N")) {
			bug.setTranslateY(bug.getTranslateY() + bug.getS().getDy());
			eatPlant(bug, plants);
		}
		if (direction.equals("W")) {
			bug.getS().setDx(bug.getS().getDx());
			bug.setTranslateX(bug.getTranslateX() + bug.getS().getDx());
			eatPlant(bug, plants);
		}
		if (direction.equalsIgnoreCase("S")) {
			bug.setTranslateY(bug.getTranslateY() + (-bug.getS().getDy()));
			eatPlant(bug, plants);
		}
		if (direction.equals("E")) {
			bug.getS().setDx(bug.getS().getDx());
			bug.setTranslateX(bug.getTranslateX() + (-bug.getS().getDx()));
			eatPlant(bug, plants);
		}

	}
}