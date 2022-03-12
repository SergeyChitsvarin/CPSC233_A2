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

    @Override
    public Direction chooseMove(World local) {
        for(int row = local.getRows() - 1 ; row >= 0; row--){
            for(int column = local.getColumns() - 1; column >= 0; column-- ){

               Entity entity = local.getEntity(row, column);

                if (entity instanceof Hero && entity.isAlive()){

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
        Direction direction = Direction.SOUTHWEST;
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
        for(int row = local.getRows() - 1 ; row >= 0; row--){
            for(int column = local.getColumns() - 1; column >= 0; column-- ){

                Entity entity = local.getEntity(row, column);

                if (entity instanceof Hero && entity.isAlive()){
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
    public String toString() {
        return super.toString() + "\t" + weaponType;
    }
}
