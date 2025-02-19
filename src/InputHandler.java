import javafx.scene.input.KeyEvent;

/**
 * This class is responsible for handling the keyboard input from user.
 * It contains a reference to the game and provides a method to handle key press events.
 */
class InputHandler {
    private final Game game;

    /**
     * Constructs a new InputHandler associated with the given game.
     *
     * @param game The game this input handler will be associated with
     */
    public InputHandler(Game game) {
        this.game = game;
    }


    /**
     * Handles key press events. Depending on the key pressed, it will move the driller in the game (up, down, left, or right).
     *
     * @param event The key event to handle
     */
    public void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                game.getDriller().moveUp();
                break;
            case DOWN:
                game.getDriller().moveDown();
                break;
            case LEFT:
                game.getDriller().moveLeft();
                break;
            case RIGHT:
                game.getDriller().moveRight();
                break;
            default:
                break;
        }
    }
}
