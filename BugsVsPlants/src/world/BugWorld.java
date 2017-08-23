package world;

import java.util.ArrayList;

import bug.Bug;
import bug.Speed;
import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import plant.Plant;

/**
 * Creates a world with bugs and plants.
 * 
 * @author John Espina <espinajohn@myvuw.ac.nz>
 * @version 1.1 Creates a World presented in 2D layout. The world comprises of
 *          one hero bug, multiple enemy bugs, and multiple plants. The bugs eat
 *          the plants and allows them to grow their size. If a bigger enemy bug
 *          intersects with the hero bug, the hero bug's size decreases. If the
 *          hero bug is bigger, it will reduces the enemy bug's size.
 * @since 1.0
 * 
 */
public class BugWorld extends Application {

	/* Attributes of the BugWorld */

	// The world will of course include bug and plants
	private Bug bug;
	static ArrayList<Plant> plants = new ArrayList<>();
	static ArrayList<Bug> enemyBugs = new ArrayList<>();

	// Size of the world
	private int width = 400;
	private int heigth = 400;

	// Counters for mavailable moves and eaten plants
	private static int movesAvailable = 30;
	private static int plantsEaten = 0;

	// Text to appear in different parts of the screen
	Text title = new Text();
	Text score = new Text("Plants Eaten:\n" + getPlantsEaten());
	Text moves = new Text("Moves Available:\n" + getMovesAvailable());

	// Buttons that will control the movement of the bug
	Button up = new Button("North");
	Button down = new Button("South");
	Button left = new Button("West");
	Button right = new Button("East");
	Button restart = new Button("Restart");

	/*
	 * The following are methods that belongs to this class
	 * 
	 */

	// Getters.
	public int getWidth() {
		return width;
	}

	public int getHeigth() {
		return heigth;
	}

	public static int getMovesAvailable() {
		return movesAvailable;
	}

	public static int getPlantsEaten() {
		return plantsEaten;
	}

	public Bug getBug() {
		return bug;
	}

	// Setters.
	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public static void setMovesAvailable(int movesAvailable) {
		BugWorld.movesAvailable = movesAvailable;
	}

	public static void setPlantsEaten(int plantsEaten) {
		BugWorld.plantsEaten = plantsEaten;
	}

	public void setBug(Bug bug) {
		this.bug = bug;
	}

	/*
	 * The following are methods that will be used for the final frame design of
	 * the window pane
	 * 
	 */

	/**
	 * This sets the Title frame. Creates a new VBox object with the title Text
	 * object and a bug object as children
	 * 
	 * @return VBox object
	 */
	public VBox addTitle() {
		Bug b2 = new Bug(150, 20, 15, new Speed(-0.3f, -0.3f));
		Image image2 = new Image("http://nmrwiki.org/wiki/images/7/7d/Bug.png");
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15, 15, 15, 15));
		vbox.setStyle("-fx-border-color: darkblue");
		b2.setFill(new ImagePattern(image2));
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(title);
		vbox.getChildren().add(b2);
		return vbox;
	}

	/**
	 * This creates a new GridPane that lays out the controls of the bug's
	 * direction
	 * 
	 * @return GridPane
	 */
	public GridPane addControls() {
		GridPane controls = new GridPane();
		controls.setPadding(new Insets(15, 15, 15, 15));
		controls.setMaxHeight(200);
		controls.setVgap(5);
		controls.setHgap(5);
		controls.setStyle("-fx-border-color: darkblue");
		controls.setConstraints(up, 2, 0);
		controls.getChildren().add(up);
		controls.setConstraints(down, 2, 2);
		controls.getChildren().add(down);
		controls.setConstraints(left, 1, 1);
		controls.getChildren().add(left);
		controls.setConstraints(right, 3, 1);
		controls.getChildren().add(right);
		controls.setConstraints(restart, 2, 1);
		controls.getChildren().add(restart);
		controls.setAlignment(Pos.CENTER);
		return controls;

	}

	/**
	 * This creates new Hbox layout. The Hbox will be used for each side of
	 * Border Pane that will be created later.
	 * 
	 * @param Node
	 *            n that can be added as children to the HBox
	 * @return new HBox layout
	 */
	public HBox addHbox(Node n) {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setStyle("-fx-border-color: darkblue");
		hbox.setPrefWidth(250);
		hbox.getChildren().add(n);
		return hbox;

	}

	/**
	 * Checks if movesAvailable is equal to zero
	 */
	public void checkFinalPrompt() {
		if (movesAvailable == 0) {
			moves.setText("No more moves available!\nGAME OVER");
			score.setText("Total plants eaten:\n" + BugWorld.getPlantsEaten());
			title.setText("GAME OVER!!!");
		}
	}

	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		// Setting the values of movesAvailable to 50
		// Also setting the value of plantsEaten to 0
		// These will be the values each time the application is restarted
		BugWorld.setMovesAvailable(30);
		BugWorld.setPlantsEaten(0);
		// Instantiating a new Bug object
		Bug b1 = new Bug(100, 100, 10, new Speed(-0.2f, -0.2f));
		// Adding an image to the bug
		Image image = new Image("http://nmrwiki.org/wiki/images/7/7d/Bug.png");
		b1.setFill(new ImagePattern(image));

		// Instantiating plant objects
		// Random numbers are used to assign x and y position
		int randomX;
		int randomY;
		for (int i = 0; i < 20; i++) {
			randomX = (int) (Math.random() * 400);
			randomY = (int) (Math.random() * 350);
			Plant p1 = new Plant(randomX, randomY, 10);
			Image pImage = new Image("https://thumbs.dreamstime.com/z/green-vector-plant-no-mash-8580750.jpg");
			p1.setFill(new ImagePattern(pImage));
			plants.add(p1);
		}

		// Adding more bugs
		float randomDx;
		float randomDy;
		for (int i = 0; i < 5; i++) {
			randomX = (int) (Math.random() * 400);
			randomY = (int) (Math.random() * 350);
			randomDx = (float) (Math.random() * -0.2f);
			randomDy = (float) (Math.random() * -0.2f);
			Bug enemyBug = new Bug(randomX, randomY, 10, new Speed(randomDx, randomDy));
			Image enemyBugImg = new Image("https://venturebeat.com/wp-content/uploads/2013/05/scared-bug.jpg");
			enemyBug.setFill(new ImagePattern(enemyBugImg));
			enemyBugs.add(enemyBug);

		}

		// Grouping the bug and plants
		Group root = new Group();

		for (Plant p : plants) {
			root.getChildren().add(p);
		}

		for (Bug b : enemyBugs) {
			root.getChildren().addAll(b);
		}
		root.getChildren().add(b1);

		/*
		 * 
		 * The following blocks of code sets up the action for each buttons
		 * 
		 */

		// This sets up action for the up button
		up.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Everytime a move is made (by clucking direction button)
				// movesAvailable will be updated
				setMovesAvailable(getMovesAvailable() - 1);
				KeyFrame frame = new KeyFrame(Duration.millis(16),
						new BugMovementHandler(b1, plants, root, getWidth(), getHeigth(), "N"));
				// cycle count is set to 150 to limit the movement of the bug
				TimelineBuilder.create().cycleCount(150).keyFrames(frame).build().play();
				moves.setText("Moves Available:\n" + getMovesAvailable());
				score.setText("Plants Eaten:\n" + getPlantsEaten());
				checkFinalPrompt();
			}

		});

		down.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setMovesAvailable(getMovesAvailable() - 1);
				KeyFrame frame = new KeyFrame(Duration.millis(16),
						new BugMovementHandler(b1, plants, root, getWidth(), getHeigth(), "S"));
				TimelineBuilder.create().cycleCount(150).keyFrames(frame).build().play();
				moves.setText("Moves Available:\n" + getMovesAvailable());
				score.setText("Plants Eaten:\n" + getPlantsEaten());
				checkFinalPrompt();

			}
		});

		left.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setMovesAvailable(getMovesAvailable() - 1);
				KeyFrame frame = new KeyFrame(Duration.millis(16),
						new BugMovementHandler(b1, plants, root, getWidth(), getHeigth(), "W"));
				TimelineBuilder.create().cycleCount(150).keyFrames(frame).build().play();
				moves.setText("Moves Available:\n" + getMovesAvailable());
				score.setText("Plants Eaten:\n" + getPlantsEaten());
				checkFinalPrompt();
			}
		});

		right.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setMovesAvailable(getMovesAvailable() - 1);
				KeyFrame frame = new KeyFrame(Duration.millis(16),
						new BugMovementHandler(b1, plants, root, getWidth(), getHeigth(), "E"));
				TimelineBuilder.create().cycleCount(150).keyFrames(frame).build().play();
				moves.setText("Moves Available:\n" + getMovesAvailable());
				score.setText("Plants Eaten:\n" + getPlantsEaten());
				checkFinalPrompt();
			}
		});

		restart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				BugWorld newGame = new BugWorld();
				try {
					start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		// Code to make enemy bugs move
		KeyFrame enemyFrame = new KeyFrame(Duration.millis(16),
				new enemyBugHandler(enemyBugs, plants, root, getWidth(), getHeigth()));
		TimelineBuilder.create().cycleCount(javafx.animation.Animation.INDEFINITE).keyFrames(enemyFrame).build().play();

		/*
		 * The next block of codes defines the arrangement of each elements in
		 * the BorderFrame
		 */

		BorderPane border = new BorderPane();
		border.setPadding(new Insets(0, 0, 0, 0));
		border.setRight(addHbox(score));
		border.setBackground(null);
		title.setFont(new Font(35));
		title.setText("Bugs Vs Plants");
		border.setLeft(addHbox(moves));
		border.setCenter(root);
		border.setAlignment(root, Pos.CENTER);
		border.setTop(addTitle());
		border.setBottom(addControls());

		// This sets the title that will appear on the top left of the window
		primaryStage.setTitle("Hello Bugs");

		/*
		 * The next line of code sets the scene and show it
		 * 
		 */
		Scene scene = new Scene(border, 1500, 650);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch();

	}
}
