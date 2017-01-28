package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static sample.GameGrid.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(details());

        //Game controls and implementation
        scene.setOnKeyPressed(event -> {
            if (!GameGrid.moved)
                return;

            switch (event.getCode()) {
                case W:
                    if (direction != GameGrid.Direction.DOWN)
                        direction = GameGrid.Direction.UP;
                    break;
                case S:
                    if (direction != GameGrid.Direction.UP)
                        direction = GameGrid.Direction.DOWN;
                    break;
                case A:
                    if (direction != GameGrid.Direction.RIGHT)
                        direction = GameGrid.Direction.LEFT;
                    break;
                case D:
                    if (direction != GameGrid.Direction.LEFT)
                        direction = GameGrid.Direction.RIGHT;
                    break;
                case SPACE:GameGrid.restartGame();
                    break;
                case ESCAPE:System.exit(0);
            }
        });

        Pane menu = new Pane();
        menu.setStyle("-fx-background-color: #000000");
        menu.setVisible(true);


        Button start = new Button("START");
        start.setPadding(new Insets(20, 50, 20, 50));
        start.setLayoutX(width / 2);
        start.setLayoutY(width / 2);

        start.setOnAction(e -> {
            primaryStage.setScene(scene);
            startGame();
        });

        menu.getChildren().addAll(start);
        menu.setStyle("-fx-background-color: black;");

        Scene scene1 = new Scene(menu, 800, 600);
        start.setLayoutX(width /2);
        start.setLayoutY(height /2);
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
