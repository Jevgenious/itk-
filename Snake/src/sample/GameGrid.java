package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static javafx.scene.paint.Color.*;

public class GameGrid {

    public static final int BLOCK_SIZE = 20;
    public static final int width = 40 * BLOCK_SIZE;
    public static final int height = 30 * BLOCK_SIZE;

    public enum Direction {UP, DOWN, LEFT, RIGHT}
    public static Direction direction = Direction.RIGHT;
    public static Timeline timeline = new Timeline();
    public static boolean moved = false;
    public static boolean running = false;
    public static void gameover(){
        running = false;
        timeline.stop();
    }

    public static int Points;
    public static int GameFood;
    static Label points = new Label();
    static Label endscn = new Label();

    public static ObservableList<Node> snake;

    // Start and reset the game
    public static void startGame() {
        direction = Direction.RIGHT;
        Rectangle head = new Rectangle(BLOCK_SIZE / 2, BLOCK_SIZE / 2,BLOCK_SIZE /2, BLOCK_SIZE /2);
        head.setFill(Color.WHITE);
        snake.add(head);
        timeline.play();
        running = true;
    }
    public static void restartGame() {
        Points = 0;
        points.setText("Points: " + Points);
        points.setTextFill(Color.WHITE);
        points.setTranslateX(width / 2);
        points.setTranslateY(2);
        snake.clear();
        endscn.setVisible(false);
        startGame();
    }
    // Game parameeters list
    public static Parent details() {
        Pane window = new Pane();
        window.setPrefSize(width, height);
        window.setVisible(true);
        window.setStyle("-fx-background-color: #000000");

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(window);

        Group snakeBody = new Group();
        snake = snakeBody.getChildren();

        points.setText("Points: " + Points);
        points.setTextFill(WHITE);
        points.setTranslateX(width / 2);
        points.setTranslateY(2);

        // The food
        Circle food = new Circle(BLOCK_SIZE / 2, BLOCK_SIZE / 2, 6);
        food.setFill(Color.GREEN);
        food.setTranslateX((int) (Math.random() * (width - BLOCK_SIZE) / BLOCK_SIZE) * BLOCK_SIZE);
        food.setTranslateY((int) (Math.random() * (height - BLOCK_SIZE) / BLOCK_SIZE) * BLOCK_SIZE);
        GameFood = 1;

        //Game time cycle and snake parameeters
        KeyFrame frame = new KeyFrame(Duration.seconds(0.1), event -> {
            if (!running) return;

            boolean Movement = snake.size() > 1;
            Node tail = Movement ? snake.remove(snake.size() - 1) : snake.get(0);

            // get Snake coordinates
            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            // Snake control
            switch (direction) {
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - BLOCK_SIZE);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() + BLOCK_SIZE);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX() - BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX() + BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }
            moved = true;

            if (Movement) snake.add(0, tail);

            //
            for (Node self : snake) {
                // Gameover against the wall
                if (tail.getTranslateX() < 0 || tail.getTranslateX() == width || tail.getTranslateY() < 0
                        || tail.getTranslateY() == height) {
                    gameover();
                    endscn.setTextFill(Color.WHITE);
                    endscn.setText("weeener !");
                    endscn.setTranslateX(width / 2.5);
                    endscn.setTranslateY(height / 4);
                    endscn.setVisible(true);
                    break;
                }
                //Gameover against self
                if (self != tail && tail.getTranslateX() == self.getTranslateX() && tail.getTranslateY() == self.getTranslateY()) {
                    gameover();
                    endscn.setTextFill(Color.WHITE);
                    endscn.setText("weeener !");
                    endscn.setTranslateX(width / 2.5);
                    endscn.setTranslateY(height / 4);
                    endscn.setVisible(true);
                    break;
                }

                // Snake growth
                if (GameFood == 1) {
                    if (tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()) {
                        food.setTranslateX((int) (Math.random() * (width - BLOCK_SIZE) / BLOCK_SIZE) * BLOCK_SIZE);
                        food.setTranslateY((int) (Math.random() * (height - BLOCK_SIZE) / BLOCK_SIZE) * BLOCK_SIZE);

                        Points = Points + 10;
                        points.setText("Points: " + Points);
                        points.setTextFill(Color.WHITE);
                        points.setTranslateX(width / 2);
                        points.setTranslateY(3);

                        Rectangle fc = new Rectangle(BLOCK_SIZE / 2, BLOCK_SIZE / 2,BLOCK_SIZE /2, BLOCK_SIZE /2);
                        fc.setFill(Color.GREEN);
                        snake.add(fc);

                    }
                }
            }
        });
        // Setting the keyframe
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        window.getChildren().addAll(food, snakeBody, points, endscn);
        return borderPane;
    }

    }


