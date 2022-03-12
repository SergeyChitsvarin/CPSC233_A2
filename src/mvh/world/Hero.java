package mvh.world;

import mvh.enums.Direction;

/**
 * A Monster is an Entity with a user provide WEAPON STRENGTH and ARMOR STRENGTH
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Hero extends Entity{

    /**
     * The user provided weapon strength
     */
    private final int weaponStrength;

    /**
     * The user provided armor strength
     */
    private final int armorStrength;

    /**
     * A Hero has regular health and symbol as well as a weapon strength and armor strength
     * @param health Health of hero
     * @param symbol Symbol for map to show hero
     * @param weaponStrength The weapon strength of the hero
     * @param armorStrength The armor strength of the hero
     */
    public Hero(int health, char symbol, int weaponStrength, int armorStrength) {
        super(symbol, health);
        this.weaponStrength = weaponStrength;
        this.armorStrength = armorStrength;
    }

    /**
     * The weapon strength of monster is from user value
     * @return The weapon strength of monster is from user value
     */
    @Override
    public int weaponStrength() {
        return weaponStrength;
    }

    /**
     * The armor strength of monster is from user value
     * @return The armor strength of monster is from user value
     */
    @Override
    public int armorStrength() {
        return armorStrength;
    }

    /**
     * Returns direction to move
     * @param local The local view of the entity
     * @return return direction for the desired move
     */
    @Override
    public Direction chooseMove(World local) {

        // Check rows from top to bottom and columns from left to right
        for(int row=0; row< local.getRows(); row++ ){
            for(int column=0; column < local.getColumns(); column++ ){

                // find entity at current row/column
                Entity entity = local.getEntity(row, column);

                // check if entity is an alive monster
                if (entity instanceof Monster && entity.isAlive()){

                    // calculate row and column change
                    int rowChange =  row - 2;
                    int columnChange = column - 2;

                    // find direction for calculated row and column
                    Direction[] directions = Direction.getDirections(rowChange, columnChange);

                    // for direction in directions
                    for (Direction direction : directions) {
                        // if can move to direction, then return this direction
                        if (canMoveToDirection(direction, local, 2, 2)) {
                            return direction;
                        }
                    }
                }
            }
        }

        // return NORTHEAST direction if can move to NORTHEAST direction
        Direction direction = Direction.NORTHEAST;
        if (canMoveToDirection(direction, local, 2, 2)) {
            return direction;
        }

        //return random direction, if can move to random direction
        direction = Direction.getRandomDirection();
        if (canMoveToDirection(direction, local, 2, 2)) {
            return direction;
        }

        // do not move
        return Direction.STAY;
    }

    /**
     * returns direction to attack an enemy
     * @param local The local view of the entity (immediate neighbors 3x3)
     * @return returns direction for an attack
     */
    @Override
    public Direction attackWhere(World local) {
        // check local world rows (top to bottom) and columns (left to right)
        for(int row=0; row< local.getRows(); row++ ){
            for(int column=0; column < local.getColumns(); column++ ){

            // find entity at current row and column
            Entity entity = local.getEntity(row, column);

            // check if entity is an alive monster
                if (entity instanceof Monster && entity.isAlive()){
                    // calculate row and column change for direction and find corresponding direction
                    int rowChange =  row  - 1;
                    int columnChange = column - 1;
                    return Direction.getDirection(rowChange, columnChange);
                }
            }
        }
        // default case
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
    public String toString(){
        return super.toString()+"\t"+weaponStrength+"\t"+armorStrength;
    }
}
