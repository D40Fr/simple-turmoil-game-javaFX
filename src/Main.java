import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Main class for the application.
 * This class extends the JavaFX Application class and starts the game.
 */
public class Main extends Application {
    public static final int BLOCK_SIZE = 50; // Size of each block
    public static final int GRID_WIDTH = 20; // Grid width in blocks
    public static final int GRID_HEIGHT = 13; // Grid height in blocks

    /**
     * This method is overrides start in Application.
     * It set up the game scene, input handling, and the primary stage.
     *
     * @param primaryStage the primary stage for this application which the scene will be set on.
     */
    @Override
    public void start(Stage primaryStage) {

        Pane gameScene = new Pane(); // Create a new Pane for the game scene

        // Set the preferred size of the game scene and the background color
        gameScene.setPrefSize(GRID_WIDTH * BLOCK_SIZE, GRID_HEIGHT * BLOCK_SIZE);
        gameScene.setStyle("-fx-background-color: #c07e3d;");

        // Start the game by starting the gravity and fuel timer
        Game game = new Game(gameScene);
        game.getDriller().startGravity();
        game.getDriller().startFuelTimer();

        final Scene scene = new Scene(gameScene); // Create a new Scene with the game scene as the root

        // Set up input handling for the scene
        InputHandler inputHandler = new InputHandler(game);
        scene.setOnKeyPressed(inputHandler::handleKeyPressed);

        // Set the title, scene, icon and show the primary stage
        primaryStage.setTitle("HU-Load");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Add this line to enter full screen mode
        primaryStage.getIcons().add(new Image("assets/drill/drill_11.png"));
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}