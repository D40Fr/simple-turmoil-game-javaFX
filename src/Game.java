import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.Iterator;

/**
 * The Game class represents the main game logic.
 */
public class Game {
    public final Pane gameScene;
    private final Driller driller;
    private final Block[][] grid;
    private int money, haul;
    private Text moneyText, haulText, fuelText, flyingText; // Text variables to display the attributes
    public boolean gameOver = false; // Game over flag
    private boolean isRedGameOver; // Flag to obtain different behavior for red and green game over screens

    /**
     * Constructor for the Game class.
     * Initializes the game scene, grid, driller, and text displays.
     *
     * @param gameScene The Pane object representing the game scene.
     */
    public Game(Pane gameScene) {
        this.gameScene = gameScene;
        grid = new GridInitializer(Main.GRID_HEIGHT, Main.GRID_WIDTH).initializeGrid();
        driller = new Driller(1, 1, Main.BLOCK_SIZE, this);
        new GridDrawer(gameScene, driller, grid);
        money = 0;
        haul = 0;
        initializeTexts();
        gameScene.getChildren().addAll(moneyText, haulText, fuelText, flyingText);
    }

    /**
     * Getter for the Driller object.
     * @return The Driller object.
     */
    public Driller getDriller() {
        return driller;
    }

    /**
     * Getter for the grid.
     * @return The 2D array representing the grid.
     */
    public Block[][] getGrid() {
        return grid;
    }

    /**
     * Initializes the text displays for money, haul, fuel, and flying status.
     */
    private void initializeTexts() {
        moneyText = createText("Money: 0", 10, 60);
        haulText = createText("Haul: 0", 10, 40);
        fuelText = createText("Fuel: " + driller.getFuel(), 10, 20);
        flyingText = createText("Flying: " + (driller.getIsFlying() ? "ON" : "OFF"), 10, 80);
    }

    /**
     * Creates a Text object with the given parameters.
     *
     * @param text The text to display.
     * @param x The x coordinate for the text.
     * @param y The y coordinate for the text.
     * @return The created Text object.
     */
    private Text createText(String text, int x, int y) {
        Text textItem = new Text(text);
        textItem.setX(x);
        textItem.setY(y);
        return textItem;
    }

    /**
     * Updates the block at the given coordinates and updates the money and haul if necessary.
     *
     * @param x The x coordinate of the block.
     * @param y The y coordinate of the block.
     * @param block The new block to place at the coordinates.
     */
    public void update(int x, int y, Block block) {

        // Update the block in the grid and remove old one from the scene
        grid[y][x] = new EmptyBlock();
        removeOldBlockFromScene(x, y);

        if (block instanceof LavaBlock) { // If the block is lava the game is over
            displayGameOverScreen(isRedGameOver = true);
        } else { // Otherwise update the money and haul
            updateMoneyAndHaul(block);
        }
        // Update the money and text fields
        moneyText.setText("Money: " + money);
        haulText.setText("Haul: " + haul);
    }

    /**
     * Removes the old block from the scene.
     *
     * @param x The x coordinate of the block.
     * @param y The y coordinate of the block.
     */
    private void removeOldBlockFromScene(int x, int y) {
        // Node is a collection that we hold the scenes children and we can iterate over it
        Iterator<Node> iterator = gameScene.getChildren().iterator();
        // Iterate over the children of the scene
        while (iterator.hasNext()) {
            Node node = iterator.next();
            // If the node is an ImageView, and it is at the same position as the block
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                if (imageView.getTranslateX() == x * Main.BLOCK_SIZE && imageView.getTranslateY() == y * Main.BLOCK_SIZE) { // If the image view is at the same position as the block
                    iterator.remove(); // Remove the image view from the scene
                }
            }
        }
    }


    /**
     * Updates the money and haul based on the block.
     * @param block The block to base the update on.
     */
    private void updateMoneyAndHaul(Block block) {
        // If the block is a valuable block update the money and haul according to their values
        if (block instanceof DiamondBlock || block instanceof EmeraldBlock || block instanceof AmazoniteBlock || block instanceof PlatinumBlock) {
            money += block.getValue();
            haul += block.getHaul();
        }
    }

    /**
     * Updates the fuel text and checks if game over due to fuel.
     * @param fuel The new fuel value.
     */
    public void updateFuel(double fuel) {
        driller.setFuel(fuel);
        fuelText.setText("Fuel: " + String.format("%.2f", fuel));

        if (fuel <= 0) {
            displayGameOverScreen(isRedGameOver = false); // Display the game over screen
        }
    }

    /**
     * Updates the flying status text.
     * @param isFlying The new flying status.
     */
    public void updateFlying(boolean isFlying) {
        flyingText.setText("Flying: " + (isFlying ? "ON" : "OFF"));
    }

    /**
     * Displays the game over screen.
     * @param isRedGameOver True if the game over screen should be red, false if it should be green.
     */
    public void displayGameOverScreen(boolean isRedGameOver) {
        gameOver = true; // Set the game over flag to true
        gameScene.getChildren().clear(); // Clear the scene
        String gameOverMessage = "GAME OVER";
        if(isRedGameOver) { // If the game over cause is lava
            gameScene.setStyle("-fx-background-color: #881010"); // set the background color to red

        } else { // If the game over cause is fuel
            gameScene.setStyle("-fx-background-color: green"); // set the background color to green
            gameOverMessage += "\nScore: " + money;
        }
        // Create a text object with the game over message
        Text gameOverText = new Text(gameOverMessage);
        gameOverText.setX((double) (Main.GRID_WIDTH * Main.BLOCK_SIZE) / 2 -140);
        gameOverText.setY((double) (Main.GRID_HEIGHT * Main.BLOCK_SIZE) / 2 -30);
        gameOverText.setStyle("-fx-font-size: 50; -fx-fill: white;");
        gameScene.getChildren().add(gameOverText); // Add the game over text to the game scene
    }
}
