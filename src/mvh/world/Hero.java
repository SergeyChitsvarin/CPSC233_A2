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

    @Override
    public Direction chooseMove(World local) {
        for(int row=0; row< local.getRows(); row++ ){
            for(int column=0; column < local.getColumns(); column++ ){

                Entity entity = local.getEntity(row, column);

                if (entity instanceof Monster && entity.isAlive()){

                    int rowChange =  row - 2;
                    int columnChange = column - 2;
                    Direction[] directions = Direction.getDirections(rowChange, columnChange);

                    for (Direction direction : directions) {
                        if (local.canMoveOnTopOf(2 + direction.getRowChange(), 2 + direction.getColumnChange())) {
                            return direction;
                        }
                    }
                }
            }
        }
        Direction direction = Direction.NORTHEAST;
        if (local.canMoveOnTopOf(2 + direction.getRowChange(), 2 + direction.getColumnChange())) {
            return direction;
        }
        direction = Direction.getRandomDirection();
        if (local.canMoveOnTopOf(2 + direction.getRowChange(), 2 + direction.getColumnChange())) {
            return direction;
        }
        return Direction.STAY;
    }

    @Override
    public Direction attackWhere(World local) {
        for(int row=0; row< local.getRows(); row++ ){
            for(int column=0; column < local.getColumns(); column++ ){
            Entity entity = local.getEntity(row, column);

                if (entity instanceof Monster && entity.isAlive()){
                    int rowChange =  row  - 1;
                    int columnChange = column - 1;
                    return Direction.getDirection(rowChange, columnChange);
                }
            }
        }
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
