import java.util.Arrays;
import java.util.Collections;

/**
 * GridInitializer is a class responsible for initializing a grid of blocks.
 * The grid is a 2D array of Block objects.
 */
public class GridInitializer {
    private final int gridHeight;
    private final int gridWidth;

    /**
     * Constructor for GridInitializer.
     * @param gridHeight The height of the grid to be initialized.
     * @param gridWidth The width of the grid to be initialized.
     */
    public GridInitializer(int gridHeight, int gridWidth) {
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        initializeGrid();
    }

    /**
     * Generates a random Block object.
     * @return A random Block object.
     */
    private Block getRandomBlock() {
        // Block types to choose from
        Block[] blockTypes = {new DiamondBlock(), new EmeraldBlock(), new AmazoniteBlock(), new PlatinumBlock(), new LavaBlock(), new SoilBlock()};
        return blockTypes[(int) (Math.random() * blockTypes.length)]; // Randomly choose a block type
    }

    /**
     * Generates an array of Block objects.
     * @return An array of Block objects.
     */
    private Block[] generateBlockArray() {
        int totalBlocks = (gridHeight-1) * (gridWidth-6); // Total blocks in the grid to randomly create, other blocks are predetermined (like sky, top, boulder at edges)
        int soilBlocks = (int)(totalBlocks * 0.80); // 80% of this ensures that soil blocks are majority (i don't know consider sky blocks to affect majority)
        Block[] blocks = new Block[totalBlocks];
        Arrays.fill(blocks, 0, soilBlocks, new SoilBlock()); // Fill the first 70% of the array with soil blocks
        // Fill the array with one lava block, one diamond block, one emerald block, one amazonite block, and one platinum block to ensure there is at least one of each
        blocks[soilBlocks] = new LavaBlock();
        blocks[soilBlocks+1] = new DiamondBlock();
        blocks[soilBlocks+2] = new EmeraldBlock();
        blocks[soilBlocks+3] = new AmazoniteBlock();
        blocks[soilBlocks+4] = new PlatinumBlock();
        // Fill the rest of the array with random blocks
        for (int i = soilBlocks+5; i < totalBlocks; i++) {
            blocks[i] = getRandomBlock();
        }
        Collections.shuffle(Arrays.asList(blocks)); // At the end, shuffle the array to randomize the block order
        return blocks; // We return a randomized array of blocks
    }

    /**
     * Initializes a grid of blocks.
     * @return A 2D array of Block objects.
     */
    public Block[][] initializeGrid() {
        Block[] blocks = generateBlockArray(); // Generate a randomized array of blocks
        int blockIndex = 0;
        Block[][] grid = new Block[gridHeight][gridWidth]; // Initialize the grid
        // Loop through the grid and assign blocks
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                if (y < 4) {
                    grid[y][x] = new SkyBlock(); // Top two rows are sky
                } else if (y == 4) {
                    grid[y][x] = new TopBlock(); // Third row is top
                } else if (x == 0 || x == gridWidth - 1 || y == gridHeight - 1) {
                    grid[y][x] = new BoulderBlock(); // Edges are boulder
                } else {
                    grid[y][x] = blocks[blockIndex++]; // Rest of the blocks are from the randomized array
                }
            }
        }
        return grid;
    }
}
