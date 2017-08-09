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

public class BugWorld extends Application {

	// Attributes of the BugWorld
	int width = 700;
	int heigth = 300;
	Text title = new Text();
	Text score = new Text("Score");
	Text moves = new Text("Moves Available");
	Button up = new Button("North");
	Button down = new Button ("South");
	Button left = new Button ("West");
	Button right = new Button ("East");
	
	
	ArrayList<Bug> bugs = new ArrayList<>();
	ArrayList<Plant> plants = new ArrayList<>();

	public VBox addTitle (){
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15,15,15,15));
		vbox.setStyle("-fx-border-color: darkblue");
		vbox.setMaxHeight(150);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(title);
		return vbox;
	}
	
	public GridPane addControls(){
		GridPane controls = new GridPane();
		controls.setPadding(new Insets (15,15,15,15));
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
		controls.setAlignment(Pos.CENTER);
		return controls;
		
	}
	
	public HBox addHbox (Node n){
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setStyle("-fx-border-color: darkblue");
		hbox.setPrefWidth(250);
		hbox.getChildren().add(n);
		return hbox;
		
	}
	
	public VBox setCenter(Group g){
		VBox centerpiece = new VBox();
		centerpiece.setPadding(new Insets(0,0,0,0));
		centerpiece.setPrefHeight(heigth);
		centerpiece.setPrefWidth(width);
		centerpiece.setAlignment(Pos.CENTER);
		centerpiece.getChildren().add(g);
		return centerpiece;
		
	}
	
	
	private static boolean isColliding(Bug b, Plant p) {
		return Math.sqrt(b.getTranslateX() + p.getX()) < (b.getRadius() + p.getRadius());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Bugs
		Bug b1 = new Bug(100, 100, 15, new Speed(-0.2f, -0.2f));
		Image image1 = new Image("https://venturebeat.com/wp-content/uploads/2013/05/scared-bug.jpg");
		b1.setFill(new ImagePattern(image1));
		Bug b2 = new Bug(150, 200, 15, new Speed(-0.3f, -0.3f));
		Image image2 = new Image("http://nmrwiki.org/wiki/images/7/7d/Bug.png");
		b2.setFill(new ImagePattern(image2));
		Bug b3 = new Bug(200, 100, 15, new Speed(-0.2f, -0.2f));
		Image image3 = new Image(
				"https://previews.123rf.com/images/idesign2000/idesign20001205/idesign2000120500366/13496491-Lady-bug-flying-Stock-Vector-cartoon-animals-ladybug.jpg");
		b3.setFill(new ImagePattern(image3));
		bugs.add(b1);
		bugs.add(b2);
		bugs.add(b3);

		// Plants
		int randomX;
		int randomY;
		for (int i = 0; i < 5; i++) {
			randomX = (int) (Math.random() * 250) + 1;
			randomY = (int) (Math.random() * 250) + 1;
			Plant p1 = new Plant(randomX, randomY, 20);
			Image pImage = new Image("https://thumbs.dreamstime.com/z/green-vector-plant-no-mash-8580750.jpg");
			p1.setFill(new ImagePattern(pImage));
			plants.add(p1);
		}
		// The group of plants and bugs
		Group root = new Group();

		for (Plant p : plants) {
			root.getChildren().add(p);
		}

		for (Bug b : bugs) {
			root.getChildren().add(b);
		}
		

		

		KeyFrame frame = new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {

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

		});

		TimelineBuilder.create().cycleCount(javafx.animation.Animation.INDEFINITE).keyFrames(frame).build().play();

		BorderPane border = new BorderPane();
		border.setPrefSize(1500, 650);
		
		
		border.setRight(addHbox(score));
		border.setLeft(addHbox(moves));
		border.setCenter(setCenter(root));
		
		border.setBackground(null);
		
		title.setFont(new Font(35));
		title.setText("Bugs Vs Plants");
		
		border.setTop(addTitle());
		border.setBottom(addControls());
		
		
		

		primaryStage.setTitle("Hello Bugs");

		Scene scene = new Scene(border, 1500, 650);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch();

	}

}
