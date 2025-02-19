import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Driller class represents a driller in the game.
 * It contains methods to control the driller's movement and fuel consumption.
 */
public class Driller {
    // Constants for fuel consumption and gravity delay
    private static final double FUEL_CONSUMPTION = 0.25;
    private static final double FUEL_CONSUMPTION_FLYING = 2;
    private static final double FUEL_CONSUMPTION_DIGGING = 50;
    private static final double FUEL_LEVEL = 1500;
    private static final int GRAVITY_DELAY = 300_000_000;
    private static final int FUEL_DELAY = 300_000_000;

    // Driller's position and image views
    private int x;
    private int y;
    private final ImageView drillerView;
    private final Image flyingOnImage, flyingOffImage, downImage, leftImage, rightImage;
    private final Game game;

    // Drillers state
    private boolean isFlying = false;
    private double fuel;

    /**
     * Constructor for Driller class.
     * Initializes the driller's position, images, and fuel level.
     */
    public Driller(int startX, int startY, int blockSize, Game game) {
        this.x = startX;
        this.y = startY;
        this.game = game;
        fuel = FUEL_LEVEL;
        this.flyingOnImage = new Image("assets/drill/drill_51.png");
        this.flyingOffImage = new Image("assets/drill/drill_49.png");
        this.downImage = new Image("assets/drill/drill_41.png");
        this.leftImage = new Image("assets/drill/drill_01.png");
        this.rightImage = new Image("assets/drill/drill_58.png");
        this.drillerView = new ImageView(flyingOnImage);
        drillerView.setTranslateX(x * blockSize - 15);
        drillerView.setTranslateY(y * blockSize);
    }

    /**
     * Starts the gravity.
     * The driller will fall down if there is no block below it and flying mode is off.
     */
    public void startGravity() {
        // Create a new timer
        new AnimationTimer() {
            private long lastUpdate = 0;
            //This method is called in every frame while the AnimationTimer is active.
            @Override
            public void handle(long now) {
                // If the game is over stop the timer
                if (game.gameOver) {
                    this.stop();
                    return;
                }
                // If the driller is flying or the gravity delay has not passed(to obtain a smooth fall), gravity off.
                if (isFlying || now - lastUpdate < GRAVITY_DELAY) return;
                // If there is no block below the driller, fall down
                if (y < Main.GRID_HEIGHT - 1 && (game.getGrid()[y + 1][x] instanceof SkyBlock || game.getGrid()[y + 1][x] instanceof EmptyBlock)) {
                    y++;
                    setImage(flyingOffImage, -15, 0);
                }
                lastUpdate = now; // Update the last update time
            }
        }.start(); // Start the timer
    }

    /**
     * Starts the fuel timer.
     * It will decrease fuel over time.
     */
    public void startFuelTimer() {
        // Create a new animation timer
        new AnimationTimer() {
            private long lastUpdate = 0;

            //This method is called in every frame
            @Override
            public void handle(long now) {
                // If the game is over stop the timer
                if (game.gameOver) {
                    this.stop();
                    return;
                }
                if (now - lastUpdate >= FUEL_DELAY) {
                    game.updateFuel(fuel - (isFlying ? FUEL_CONSUMPTION_FLYING: FUEL_CONSUMPTION));
                    lastUpdate = now;
                }
            }
        }.start();
    }

    /**
     * Returns the current fuel level.
     */
    public double getFuel() {
        return fuel;
    }

    /**
     * Returns the current flying state.
     */
    public boolean getIsFlying() {
        return isFlying;
    }

    /**
     * Returns the driller's image view.
     */
    public ImageView getDrillerView() {
        return drillerView;
    }

    /**
     * Sets the fuel level.
     */
    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    /**
     * Sets the image of the driller. Offset values are for adjust the image position.
     */
    public void setImage(Image image, int xOffset, int yOffset) {
        drillerView.setTranslateX(x * Main.BLOCK_SIZE + xOffset);
        drillerView.setTranslateY(y * Main.BLOCK_SIZE + yOffset);
        drillerView.setImage(image);
    }

    /**
     * Moves the driller up.
     */
    public void moveUp() {
        if(game.gameOver) return; // If game is over game will not run at the background
        game.updateFlying(isFlying = true); // Update flying status
        if (y > 0) { // If the driller is not at the top of the grid
            Block blockType = game.getGrid()[y - 1][x]; // Get the block above the driller
            // If the block above the driller is sky or empty, move up
            if (blockType instanceof SkyBlock || blockType instanceof EmptyBlock) {
                y--;
                setImage(flyingOnImage, -15, 0);
            } else { // If the block above the driller is not sky or empty, prevent digging
                setImage(flyingOnImage, -15, 0);
            }
        }
    }

    /**
     * Moves the driller down.
     */
    public void moveDown() {
        if(game.gameOver) return; // If game is over game will not run at the background
        game.updateFlying(isFlying = false); // Update flying status

        if (y < Main.GRID_HEIGHT - 1) { // If the driller is not at the bottom of the grid
            Block blockType = game.getGrid()[y + 1][x]; // Get the block below the driller
            if (blockType instanceof BoulderBlock) { // If the block below the driller is boulder, prevent digging
                game.updateFuel(fuel - FUEL_CONSUMPTION_DIGGING);
                setImage(downImage, -15, 0);
            }
            else if (blockType instanceof LavaBlock) { // If the block below the driller is lava, call update method
                setImage(downImage, -15, 0);
                game.update(x, y, new LavaBlock());
            }
            else if (!(blockType instanceof SkyBlock) && !(blockType instanceof EmptyBlock)) { // Dig
                game.updateFuel(fuel - FUEL_CONSUMPTION_DIGGING);
                game.update(x, y + 1, blockType);
                y++;
                setImage(downImage, -15, 0);
            }
        }
    }

    /**
     * Moves the driller left.
     */
    public void moveLeft() {
        if(game.gameOver) return; // If game is over game will not run at the background
        game.updateFlying(isFlying = false);

        if (x > 0) { // If the driller is not at the left edge of the grid
            Block blockType = game.getGrid()[y][x - 1]; // Get the block left of the driller

            if (blockType instanceof BoulderBlock) { // If the block left of the driller is boulder, prevent digging
                game.updateFuel(fuel - FUEL_CONSUMPTION_DIGGING);
                setImage(leftImage, -10, -10);
            }
            else if (blockType instanceof LavaBlock) { // If the block left of the driller is lava, call update method
                setImage(leftImage, -10, -10);
                game.update(x, y, new LavaBlock());
            }
            else if (game.getGrid()[y][x]instanceof EmptyBlock && game.getGrid()[y+1][x] instanceof EmptyBlock && !(blockType instanceof EmptyBlock)) { // Prevent drilling when flying
                setImage(flyingOnImage, -15, 0);
            }
            else if (blockType instanceof SkyBlock || blockType instanceof EmptyBlock) { // Don't dig it is in the air

                if (!(game.getGrid()[y+1][x-1] instanceof SkyBlock) && !(game.getGrid()[y+1][x-1] instanceof EmptyBlock)) { // It means it lands on a block if it is not sky or empty
                    x--;
                    setImage(leftImage, -10, -10);
                } else { // It means it is flying
                    x--;
                    setImage(flyingOnImage, -15, 0);
                }
            }
            else { // Dig
                game.updateFuel(fuel - FUEL_CONSUMPTION_DIGGING);
                if(game.gameOver) return;
                game.update(x - 1, y, blockType);
                x--;
                setImage(leftImage, -10, -10);
            }
        }
    }

    /**
     * Moves the driller right.
     */
    public void moveRight() {
        if(game.gameOver) return;
        game.updateFlying(isFlying = false);

        if (x < Main.GRID_WIDTH - 1) { // If the driller is not at the right edge of the grid
            Block blockType = game.getGrid()[y][x + 1]; // Get the block right of the driller
            if (blockType instanceof BoulderBlock) { // If the block right of the driller is boulder, prevent digging
                game.updateFuel(fuel - FUEL_CONSUMPTION_DIGGING);
                setImage(rightImage, 10, -5);
            }
            else if (blockType instanceof LavaBlock) { // If the block right of the driller is lava, call update method
                setImage(rightImage, 10, -5);
                game.update(x, y, new LavaBlock());
            }
            else if (game.getGrid()[y][x] instanceof EmptyBlock && game.getGrid()[y+1][x] instanceof EmptyBlock && !(blockType instanceof EmptyBlock)) { // Prevent drilling when flying
                setImage(flyingOnImage, -15, 0);
            }
            else if (blockType instanceof SkyBlock || blockType instanceof EmptyBlock) { // Don't dig it is in the air
                if (!(game.getGrid()[y+1][x+1] instanceof SkyBlock) && !(game.getGrid()[y+1][x+1] instanceof EmptyBlock)) { // It means it lands on a block if it is not sky or empty
                    x++;
                    setImage(rightImage, 10, -5);
                } else { // It means it is flying
                    x++;
                    setImage(flyingOnImage, -15, 0);
                }
            } else { // Dig
                game.updateFuel(fuel - FUEL_CONSUMPTION_DIGGING);
                if(game.gameOver) return;
                game.update(x + 1, y, blockType);
                x++;
                setImage(rightImage, 10, -5);
            }
        }
    }
}
