import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * GridDrawer class is responsible for drawing the game grid on the scene.
 * It contains methods to create image views and draw the grid.
 */
public class GridDrawer {
    private final Pane gameScene;
    private final Driller driller;
    private final Block[][] grid;

    /**
     * Constructor for GridDrawer class.
     * Initializes the game scene, driller, and grid.
     * Calls the drawGrid method to draw the initial grid.
     */
    public GridDrawer(Pane gameScene, Driller driller, Block[][] grid) {
        this.gameScene = gameScene;
        this.driller = driller;
        this.grid = grid;
        drawGrid();
    }

    /**
     * Creates an ImageView object with the given image file and coordinates.
     * @param imageFile The image file to be displayed
     * @param x The x coordinate of the image
     * @param y The y coordinate of the image
     * @return The ImageView object with the given image and coordinates
     */
    private ImageView createImageView(String imageFile, int x, int y) {
        ImageView imageView = new ImageView(imageFile);
        imageView.setTranslateX(x * Main.BLOCK_SIZE);
        imageView.setTranslateY(y * Main.BLOCK_SIZE);
        return imageView;
    }

    /**
     * Draws the grid on the game scene.
     * Loops through the grid and draws the blocks on the scene.
     * Adds the driller view to the scene at the end.
     */
    public void drawGrid() {
        gameScene.getChildren().clear(); // Clear the scene before drawing

        for (int y = 0; y < Main.GRID_HEIGHT; y++) {
            for (int x = 0; x < Main.GRID_WIDTH; x++) {
                Block block = grid[y][x]; // Get the block at the current position
                if (block instanceof SkyBlock) { // Draw sky blocks we are treating this case differently because there is no image file for sky blocks
                    Rectangle skyRectangle = new Rectangle(Main.BLOCK_SIZE, Main.BLOCK_SIZE+3, Color.SKYBLUE);
                    skyRectangle.setTranslateX(x * Main.BLOCK_SIZE);
                    skyRectangle.setTranslateY(y * Main.BLOCK_SIZE);
                    gameScene.getChildren().add(skyRectangle);
                }
                else { // Draw other blocks
                    String imageFile = block.getImageFile();
                    if (imageFile != null) {
                        ImageView blockView = createImageView(imageFile, x, y);
                        gameScene.getChildren().add(blockView);
                    }
                }
            }
        }
        gameScene.getChildren().add(driller.getDrillerView()); // Add the driller
    }
}
