/**
 * This class represents a block in the grid.
 */
public abstract class Block {
    private int value;
    private int haul;

    /**
     * Constructor for the Block class.
     * @param value The value of the block.
     * @param haul The haul of the block.
     */
    public Block(int value, int haul) {
        this.value = value;
        this.haul = haul;
    }

    /**
     * Getter for the value of the block.
     * @return The value of the block.
     */
    public int getValue() {
        return value;
    }

    /**
     * Getter for the haul of the block.
     * @return The haul of the block.
     */
    public int getHaul() {
        return haul;
    }

    /**
     * Abstract method to get the image file of the block.
     * This method should be implemented by subclasses of Block.
     * @return The image file for this block type.
     */
    public abstract String getImageFile(); // returns the image file for this block type
}

/**
 * This class represents soil in the grid.
 */
class SoilBlock extends Block {

    /**
     * It initializes the block with a value and haul of 0.
     */
    public SoilBlock() {
        super(0, 0);
    }

    /**
     * This method overrides the abstract method from the Block class.
     * @return The image file name for a soil block.
     */
    @Override
    public String getImageFile() {
        return "/assets/underground/soil_01.png";
    }
}

/**
 * This class represents soil with grass on top of it in the grid.
 */
class TopBlock extends Block {
    /**
     * It initializes the block with a value and haul of 0.
     */
    public TopBlock() {
        super(0, 0);
    }

    /**
     * This method overrides the abstract method from the Block class.
     * @return The image file name for a top block.
     */
    @Override
    public String getImageFile() {
        return "/assets/underground/top_02.png";
    }
}

/**
 * This class represents a lava block in the grid.
 */
class LavaBlock extends Block {
    public LavaBlock() {
        super(0, 0);
    }

    /**
     * This method overrides the abstract method from the Block class.
     * @return The image file name for a lava block.
     */
    @Override
    public String getImageFile() {
        return "/assets/underground/lava_01.png";
    }
}

/**
 * This class represents a sky block in the grid.
 */
class SkyBlock extends Block {
    /**
     * It initializes the block with a value and haul of 0.
     */
    public SkyBlock() {
        super(0, 0);
    }

    /**
     * This method overrides the abstract method from the Block class.
     * @return null because we create our sky blocks.
     */
    @Override
    public String getImageFile() {
        return null;
    }
}

/**
 * This class represents a boulder block in the grid.
 */
class BoulderBlock extends Block {
    /**
     * It initializes the block with a value and haul of 0.
     */
    public BoulderBlock() {
        super(0, 0);
    }

    /**
     * This method overrides the abstract method from the Block class.
     * @return The image file name for a boulder block.
     */
    @Override
    public String getImageFile() {
        return "/assets/underground/obstacle_01.png"; // or return a transparent image file
    }
}

/**
 * This class represents an empty block in the grid.
 */
class EmptyBlock extends Block {
    /**
     * It initializes the block with a value and haul of 0.
     */
    public EmptyBlock() {
        super(0, 0);
    }

    /**
     * This method overrides the abstract method from the Block class.
     * @return The image file name for an empty block.
     */
    @Override
    public String getImageFile() {
        return "/assets/underground/empty_15.png";
    }
}

/**
 * This class represents a diamond block in the grid.
 */
class DiamondBlock extends Block {
    private static final int VALUE = 100000;
    private static final int HAUL = 100;
    /**
     * It initializes the block with a value and a haul.
     */
    public DiamondBlock() {
        super(VALUE, HAUL);
    }

    /**
     * This method overrides the abstract method from the Block class.
     * @return The image file name for a diamond block.
     */
    @Override
    public String getImageFile() {
        return "/assets/underground/valuable_diamond.png";
    }
}

/**
 * This class represents an emerald block in the grid.
 */
class EmeraldBlock extends Block {
    private static final int VALUE = 5000;
    private static final int HAUL = 60;
    /**
     * It initializes the block with a value of 60 and a haul of 10.
     */
    public EmeraldBlock() {
        super(VALUE, HAUL);
    }

    /**
     * This method overrides the abstract method from the Block class.
     * @return The image file name for an emerald block.
     */
    @Override
    public String getImageFile() {
        return "/assets/underground/valuable_emerald.png";
    }
}

/**
 * This class represents an amazonite block in the grid.
 */
class AmazoniteBlock extends Block {
    private static final int VALUE = 500000;
    private static final int HAUL = 120;
    /**
     * It initializes the block with a value and a haul.
     */
    public AmazoniteBlock() {
        super(VALUE, HAUL);
    }

    /**
     * This method overrides the abstract method from the Block class.
     * @return The image file name for an amazonite block.
     */
    @Override
    public String getImageFile() {
        return "/assets/underground/valuable_amazonite.png";
    }
}

/**
 * This class represents a platinum block in the grid.
 */
class PlatinumBlock extends Block {
    private static final int VALUE = 750;
    private static final int HAUL = 30;
    /**
     * It initializes the block with a value of 50 and a haul of 20.
     */
    public PlatinumBlock() {
        super(VALUE, HAUL);
    }

    /**
     * This method overrides the abstract method from the Block class.
     * @return The image file name for a platinum block.
     */
    @Override
    public String getImageFile() {
        return "/assets/underground/valuable_platinum.png";
    }
}


