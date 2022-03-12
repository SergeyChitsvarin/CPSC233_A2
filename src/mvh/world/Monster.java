package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;

/**
 * A Monster is an Entity with a set ARMOR STRENGTH and a user provided WEAPON TYPE
 * @author Jonathan Hudson
 * @version 1.0a
 */
public final class Monster extends Entity {

    /**
     * The set armor strength of a Monster
     */
    private static final int MONSTER_ARMOR_STRENGTH = 2;

    /**
     * The user provided weapon type
     */
    private final WeaponType weaponType;

    /**
     * A Monster has regular health and symbol as well as a weapon type
     *
     * @param health     Health of Monster
     * @param symbol     Symbol for map to show Monster
     * @param weaponType The weapon type of the Monster
     */
    public Monster(int health, char symbol, WeaponType weaponType) {
        super(symbol, health);
        this.weaponType = weaponType;
    }

    /**
     * Gets Monster's weapon type
     * @return The Monster's weapon type
     */
    public WeaponType getWeaponType(){
        return this.weaponType;
    }

    /**
     * The weapon strength of monster is from their weapon type
     * @return The weapon strength of monster is from their weapon type
     */
    @Override
    public int weaponStrength() {
        return weaponType.getWeaponStrength();
    }

    /**
     * The armor strength of monster is from the stored constant
     * @return The armor strength of monster is from the stored constant
     */
    @Override
    public int armorStrength() {
        return MONSTER_ARMOR_STRENGTH;
    }

    /**
     * Chooses direction for next move
     * @param local The local view of the entity
     * @return direction for a desired move
     */
    @Override
    public Direction chooseMove(World local) {
        // check rows (bottom to top) and columns (right to left)
        for(int row = local.getRows() - 1 ; row >= 0; row--){
            for(int column = local.getColumns() - 1; column >= 0; column-- ){

                // find entity at current row and column
               Entity entity = local.getEntity(row, column);

               // check if enemy is an alive hero
                if (entity instanceof Hero && entity.isAlive()){

                    // calculate row and columns for change
                    int rowChange =  row - 2;
                    int columnChange = column - 2;

                    // find direction using calculated columns and rows
                    Direction[] directions = Direction.getDirections(rowChange, columnChange);

                    // for every direction in directions
                    for (Direction direction : directions) {

                        // choose direction if can move there
                        if (canMoveToDirection(direction, local, 2, 2)) {
                            return direction;
                        }
                    }
                }
            }
        }
        // choose SOUTHWEST if can go there
        Direction direction = Direction.SOUTHWEST;
        if (canMoveToDirection(direction, local, 2, 2)) {
            return direction;
        }

        // choose random direction if can go there
        direction = Direction.getRandomDirection();
        if (canMoveToDirection(direction, local, 2, 2)) {
            return direction;
        }

        // do not move, stay
        return Direction.STAY;
    }

    /**
     * returns direction to attack a hero
     * @param local The local view of the entity (immediate neighbors 3x3)
     * @return returns direction for an attack
     */
    @Override
    public Direction attackWhere(World local) {
        // check rows (bottom to top) and columns (right to left)
        for(int row = local.getRows() - 1 ; row >= 0; row--){
            for(int column = local.getColumns() - 1; column >= 0; column-- ){

                // find enemy at row and column
                Entity entity = local.getEntity(row, column);

                // check if enemy is an alive hero
                if (entity instanceof Hero && entity.isAlive()){
                    // calculate row and column for change
                    int rowChange =  row  - 1;
                    int columnChange = column - 1;

                    // calculate and return direction based on calculated rows and columns
                    return Direction.getDirection(rowChange, columnChange);
                }
            }
        }

        // default scenario
        return null;
    }

    /**
     * Can only be moved on top of if dad
     * @return isDead()
     */
    @Override
    public boolean canMoveOnTopOf() {
        return isDead();
    }

    /**
     * Can only be attacked if alive
     * @return isAlive()
     */
    @Override
    public boolean canBeAttacked() {
        return isAlive();
    }

    @Override
    public String toString() {
        return super.toString() + "\t" + weaponType;
    }
}
