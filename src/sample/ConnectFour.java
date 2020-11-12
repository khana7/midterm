package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConnectFour extends Application {
    // Indicates which player has a turn, initially it is the X player
    private char whoseTurn = 'R';

    // Create and initialize cell
    private Cell[][] cell = new Cell[6][7];

    // Create and initialize a status label
    private Label lblStatus = new Label("R's turn to play");

    @Override
    // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Pane to hold cell

        GridPane pane = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                cell[i][j] = new Cell();//add with constructor
                if (i == 5) {//last rows available at the beginning
                    cell[i][j].makeAvailable();
                }
                pane.add(cell[i][j], j, i);
            }
        }
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);


        // Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 450, 350);

        primaryStage.setTitle("ConnectFour"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

    }

    public boolean isFull() {
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 7; j++)
                if (cell[i][j].getToken() == ' ')
                    return false;

        return true;
    }

    public boolean isWon(char token) {
        // looking for vertical
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (cell[i][j + 0].getToken() == token
                        && cell[i][j + 1].getToken() == token
                        && cell[i][j + 2].getToken() == token
                        && cell[i][j + 3].getToken() == token) {
                    return true;
                }
            }
        }

        // looking for horizontal
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 3; i++) {
                if (cell[i + 0][j].getToken() == token
                        && cell[i + 1][j].getToken() == token
                        && cell[i + 2][j].getToken() == token
                        && cell[i + 3][j].getToken() == token) {
                    return true;
                }
            }
        }

        // upper aimed line
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                if (cell[i + 0][j + 0].getToken() == token
                        && cell[i + 1][j + 1].getToken() == token
                        && cell[i + 2][j + 2].getToken() == token
                        && cell[i + 3][j + 3].getToken() == token) {
                    return true;
                }
            }
        }

        // lower aimed line
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                if (cell[i + 0][j + 3].getToken() == token
                        && cell[i + 1][j + 2].getToken() == token
                        && cell[i + 2][j + 1].getToken() == token
                        && cell[i + 3][j + 0].getToken() == token) {
                    return true;
                }
            }
        }

        return false;
    }

    public void reAvailable() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                //if cell is available and occupied make upper bit available
                if (cell[i][j].isAvailable() && cell[i][j].getToken() != ' ') {
                    if (i != 0)//check is it upper row
                    {
                        cell[i - 1][j].makeAvailable();
                    }
                }
            }
        }
    }

    // An inner class for a cell
    public class Cell extends Pane {
        // Token used for this cell
        private char token = ' ';
        private boolean available = false;

        public Cell() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                    handleMouseClick();
                }
            });
        }

        public boolean isAvailable() {
            return this.available;
        }

        public void makeAvailable() {
            this.available = true;
        }

        public char getToken() {
            return token;
        }

        public void setToken(char c) {
            token = c;

            // If it is Yellow, draw a yellow ellipse; if it is a Red, draw a
            // red ellipse
            if (token == 'R') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2,
                        this.getHeight() / 2, this.getWidth() / 2 - 10,
                        this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(
                        this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(
                        this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.RED);

                getChildren().add(ellipse); // Add the ellipse to the pane
            } else if (token == 'Y') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2,
                        this.getHeight() / 2, this.getWidth() / 2 - 10,
                        this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(
                        this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(
                        this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.YELLOW);

                getChildren().add(ellipse); // Add the ellipse to the pane
            }
        }
        String w1="";

        private void handleMouseClick() {

            	try {
                File myObj = new File("C:\\Users\\User\\Desktop\\words.txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    w1 = w1+myReader.nextLine();

                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            // If cell is empty and game is not over
            if (token == ' ' && whoseTurn != ' ' && available) {
                setToken(whoseTurn); // Set token in the cell
                reAvailable();//make upper layer nodes available
                // Check game status
                if (isWon(whoseTurn)) {
                    lblStatus.setText(whoseTurn + " won! The game is over"+w1);
                    whoseTurn = ' '; // Game is over
                } else if (isFull()) {
                    lblStatus.setText("Draw! The game is over"+w1);
                    whoseTurn = ' '; // Game is over
                } else {
                    // Change the turn
                    whoseTurn = (whoseTurn == 'Y') ? 'R' : 'Y';
                    // Display whose turn
                    lblStatus.setText(whoseTurn + "'s turn");
                }
            }
        }



    public void main(String[] args) {
        launch(args);
    }
}}
