package mvh.world;

import mvh.Main;
import mvh.Menu;
import mvh.enums.Direction;
import mvh.enums.Symbol;
import mvh.enums.WeaponType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A World is a 2D grid of entities, null Spots are floor spots
 * @author Jonathan Hudson
 * @version 1.0a
 */
public class World {

    public static String stateOfEntity(Boolean state){
        if (true){
            return "ALIVE";
        }
        return "DEAD";
    }

    /**
     * returns string representation of the world
     * @return returns string representation of the world
     */
    public String worldString(){

        // calculate number of rows and columns
        int rows = world.length;
        int columns = world[0].length;

        // calculate number of horizontal walls
        int numOfWallsHorizontal = columns + 2;

        // initialize game map
        String gameMap = "";

        // add top border (#)
        for (int wall=0; wall < numOfWallsHorizontal; wall++){
            gameMap = gameMap + "#";
        }

        // add new line
        gameMap = gameMap + "\n";

        // check rows from top to bottom
        for (int row = 0; row < rows; row++){

            // add right wall
            gameMap = gameMap + "#";

            // check columns left to right
            for (int column = 0; column < columns; column++){

                // find entity at current row and column
                Entity entity = world[row][column];

                // declare symbol
                char mySymbol;

                // if entity is null, then add floor symbol
                if (entity == null){
                    mySymbol = mvh.enums.Symbol.FLOOR.getSymbol();

                    // if entity is wall, return wall symbol
                } else if (entity instanceof Wall) {
                    mySymbol = '#';
                }// if alive entity is a monster or a hero, return corresponding symbol
                //if entity is dead, return dead symbol
                else {
                    mySymbol = entity.isDead() ? Symbol.DEAD.getSymbol() : entity.getSymbol();
                }

                // add string value of symbol
                String symbolString = String.valueOf(mySymbol);
                gameMap = gameMap + symbolString;
            }
            // add new line
            gameMap = gameMap + "#\n";
        }
        // add bottom border/wall
        for (int wall=0; wall < numOfWallsHorizontal; wall++){
            gameMap = gameMap + "#";
        }
        // return gameMap
        return gameMap;
    }

    /**
     * Returns string representation of the game
     * @param world instance of the game world
     * @return string representation of the game
     */
    public String gameString(World world) {
        // get number of rows and columns
        int rows = world.getRows();
        int columns = world.getColumns();

        // print representation of the game world
        System.out.println(worldString());

        // print header
        System.out.println("NAME \tS\tH\tSTATE\tINFO");

        // initialize gamestring
        String gameString = "";
        //check all rows and columns
        for (int currentRow = 0; currentRow < rows; currentRow++) {
            for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
                // find entity at current row and column
                Entity entity = world.getEntity(currentRow, currentColumn);

                // check if entity is an instance of Monster
                if (entity instanceof Monster){
                    // find entity symbol and corresponding string
                    char symbol = entity.getSymbol();
                    String symbolString = String.valueOf(symbol);

                    // find entity health and corresponding string
                    Integer health = entity.getHealth();
                    String healthString = String.valueOf(health);

                    // find entity state and corresponding string
                    Boolean state = entity.isAlive();
                    String stateString = World.stateOfEntity(state);

                    // find entity weapon and corresponding string
                    WeaponType weaponType = ((Monster) entity).getWeaponType();
                    String weaponTypeString = String.valueOf(weaponType);

                    // add string representation of a monster to gamestring
                    String monsterString = entity.shortString() + "\t"+ symbolString +"\t"+ healthString +"\t" + stateString + "\t" + weaponTypeString + "\n";
                    gameString = gameString + monsterString;
                }

                if (entity instanceof Hero){
                    // find entity symbol and corresponding string
                    char symbol = entity.getSymbol();
                    String symbolString = String.valueOf(symbol);

                    // find entity health and corresponding string
                    Integer health = entity.getHealth();
                    String healthString = String.valueOf(health);

                    // find entity state and corresponding string
                    Boolean state = entity.isAlive();
                    String stateString = World.stateOfEntity(state);

                    // find entity attack strength and corresponding string
                    int attackStrength = entity.weaponStrength();
                    String attackStrengthString = String.valueOf(attackStrength);

                    // find entity armor strength and corresponding string
                    int armorStrength = entity.armorStrength();
                    String armorStrengthString = String.valueOf(armorStrength);

                    // add string representation of a hero to game string
                    String heroString = entity.shortString() + "\t"+ symbolString +"\t"+ healthString +"\t" + stateString + "\t" + armorStrengthString + "  " + attackStrengthString + "\n";
                    gameString = gameString + heroString;
                }
            }
        }
        // return game string
        return gameString;
    }

    /**
     * World starts ACTIVE, but will turn INACTIVE after a simulation ends with only one type of Entity still ALIVE
     */
    private enum State {
        ACTIVE, INACTIVE
    }

    /**
     * The World starts ACTIVE
     */
    private State state;

    /**
     * The storage of entities in World, floor is null, Dead entities can be moved on top of (deleting them essentially from the map)
     */
    private final Entity[][] world;
    /**
     * We track the order that entities were added (this is used to determine order of actions each turn)
     * Entities remain in this list (Even if DEAD) ,unlike the world Entity[][] where they can be moved on top of causing deletion.
     */
    private final ArrayList<Entity> entities;
    /**
     * We use a HashMap to track entity location in world {row, column}
     * We will update this every time an Entity is shifted in the world Entity[][]
     */
    private final HashMap<Entity, Integer[]> locations;

    /**
     * The local view of world will be 3x3 grid for attacking
     */
    private static final int ATTACK_WORLD_SIZE = 3;
    /**
     * The local view of world will be 5x5 grid for moving
     */
    private static final int MOVE_WORLD_SIZE = 5;

    /**
     * A new world of ROWSxCOLUMNS in size
     *
     * @param rows    The 1D of the 2D world (rows)
     * @param columns The 2D of the 2D world (columns)
     */
    public World(int rows, int columns) {
        this.world = new Entity[rows][columns];
        this.entities = new ArrayList<>();
        this.locations = new HashMap<>();
        //Starts active
        this.state = State.ACTIVE;
    }

    /**
     * Is this simulation still considered ACTIVE
     *
     * @return True if the simulation still active, otherwise False
     */
    public boolean isActive() {
        return state == State.ACTIVE;
    }

    /**
     * End the simulation, (Set in INACTIVE)
     */
    public void endSimulation() {
        this.state = State.INACTIVE;
    }

    /**
     * Advance the simulation one step
     */
    public void advanceSimulation() {
        //Do not advance if simulation is done
        if (state == State.INACTIVE) {
            return;
        }
        //If not done go through all entities (this will be in order read and added from file)
        for (Entity entity : entities) {
            //If entity is something that is ALIVE, we want to give it a turn to ATTACK or MOVE
            if (entity.isAlive()) {
                //Get location of entity (only the world knows this, the entity does not itself)
                Integer[] location = locations.get(entity);
                //Pull out row,column
                int row = location[0];
                int column = location[1];
                //Determine if/where an entity wants to attack
                World attackWorld3X3 = getLocal(ATTACK_WORLD_SIZE, row, column);
                Direction attackWhere = entity.attackWhere(attackWorld3X3);
                //If I don't attack, then I must be moving
                if (attackWhere == null) {
                    //Figure out where entity wants to move
                    World moveWorld5x5 = getLocal(MOVE_WORLD_SIZE, row, column);
                    Direction moveWhere = entity.chooseMove(moveWorld5x5);
                    //Log moving
                    Menu.println(String.format("%s moving %s", entity.shortString(), moveWhere));
                    //If this move is valid, then move it
                    if (canMoveOnTopOf(row, column, moveWhere)) {
                        moveEntity(row, column, moveWhere);
                    } else {
                        //Otherwise, indicate an invalid attempt to move
                        Menu.println(String.format("%s  tried to move somewhere it could not!", entity.shortString()));
                    }
                } else {
                    //If we are here our earlier attack question was not null, and we are attacking a nearby entity
                    //Get the entity we are attacking
                    Entity attacked = getEntity(row, column, attackWhere);
                    Menu.println(String.format("%s attacking %s in direction %s", entity.shortString(), attackWhere, attacked.shortString()));
                    //Can we attack this entity
                    if (canBeAttacked(row, column, attackWhere)) {
                        //Determine damage using RNG
                        int damage = 1 + Main.random.nextInt(entity.weaponStrength());
                        int true_damage = Math.max(0, damage - attacked.armorStrength());
                        Menu.println(String.format("%s attacked %s for %d damage against %d defense for %d", entity.shortString(), attacked.shortString(), damage, attacked.armorStrength(), true_damage));
                        attacked.damage(true_damage);
                        if (!attacked.isAlive()) {
                            locations.remove(attacked);
                            Menu.println(String.format("%s died!", attacked.shortString()));
                        }
                    } else {
                        Menu.println(String.format("%s  tried to attack somewhere it could not!", entity.shortString()));
                    }
                }
            }
        }
        checkActive();
    }

    /**
     * Find local world visible by entity
     * @param attackWorldSize entity attack world size
     * @param row entity row
     * @param column entity column
     * @return local world visible by entity
     */
    protected World getLocal(int attackWorldSize, int row, int column) {
        // find min/max for rows and columns
        int minColumn = 0;
        int maxColumn = world[0].length -1;
        int minRows = 0;
        int maxRows = world.length -1;

        // find range
        int maxRange = (attackWorldSize-1)/2;

        // find start/stop for rows and columns
        int startRow = Math.max(minRows, (row - maxRange));
        int stopRow = Math.min(maxRows, (row + maxRange));
        int startColumn = Math.max(minColumn, (column - maxRange));
        int stopColumn = Math.min(maxColumn, (column + maxRange));

        // create a local world
        World attackWorld = new World(attackWorldSize, attackWorldSize);

        // initialize attack world row and columns
        int attackRow = 0;
        int attackColumn = 0;

        // check rows and columns in range
        for (int currentRow = row - maxRange; currentRow <= row + maxRange; currentRow++) {
            for (int currentColumn = column - maxRange; currentColumn <= column + maxRange; currentColumn++){

                // check if current row or column is out of bound
                if ((currentRow < startRow) || (currentRow > stopRow) || (currentColumn < startColumn) || (currentColumn > stopColumn)) {
                    attackWorld.addEntity(attackRow, attackColumn, Wall.getWall());
                }else{
                    // copy entity
                    Entity entity = world[currentRow][currentColumn];
                    attackWorld.addEntity(attackRow, attackColumn, entity);
                }
                // next column
                attackColumn++;
            }
            // next row, reset columns
            attackRow++;
            attackColumn = 0;
        }
        // return local/attack world
        return attackWorld;
    }

    /**
     * Check if simulation has now ended (only one of two versus Entity types is alive
     */
    private void checkActive() {
        boolean hero_alive = false;
        boolean monster_alive = false;
        for (Entity entity : entities) {
            if (entity.isAlive()) {
                if (entity instanceof Monster) {
                    monster_alive = true;
                } else if (entity instanceof Hero) {
                    hero_alive = true;
                }
            }
        }
        if (!(hero_alive && monster_alive)) {
            state = State.INACTIVE;
        }
    }

    /**
     * Move an existing entity
     *
     * @param row    The  row location of existing entity
     * @param column The  column location of existing entity
     * @param d      The direction to move the entity in
     */
    public void moveEntity(int row, int column, Direction d) {
        Entity entity = getEntity(row, column);
        int moveRow = row + d.getRowChange();
        int moveColumn = column + d.getColumnChange();
        this.world[moveRow][moveColumn] = entity;
        this.world[row][column] = null;
        this.locations.put(entity, new Integer[]{moveRow, moveColumn});
    }

    /**
     * Add a new entity
     *
     * @param row    The  row location of new entity
     * @param column The  column location of new entity
     * @param entity The entity to add
     */
    public void addEntity(int row, int column, Entity entity) {
        this.world[row][column] = entity;
        this.entities.add(entity);
        locations.put(entity, new Integer[]{row, column});
    }

    /**
     * Get entity at a location
     *
     * @param row    The row of the entity
     * @param column The column of the entity
     * @return The Entity at the given row, column
     */
    public Entity getEntity(int row, int column) {
        return this.world[row][column];
    }

    /**
     * Get entity at a location
     *
     * @param row    The row of the entity
     * @param column The column of the entity
     * @param d      The direction adjust look up towards
     * @return The Entity at the given row, column
     */
    public Entity getEntity(int row, int column, Direction d) {
        return getEntity(row + d.getRowChange(), column + d.getColumnChange());
    }

    /**
     * See if we can move to location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if we can move to that location
     */
    public boolean canMoveOnTopOf(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return true;
        }
        return entity.canMoveOnTopOf();
    }

    /**
     * See if we can move to location
     *
     * @param row    The row to check
     * @param column The column to check
     * @param d      The direction adjust look up towards
     * @return True if we can move to that location
     */
    public boolean canMoveOnTopOf(int row, int column, Direction d) {
        return canMoveOnTopOf(row + d.getRowChange(), column + d.getColumnChange());
    }

    /**
     * See if we can attack entity at a location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if we can attack entity at that location
     */
    public boolean canBeAttacked(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity.canBeAttacked();

    }

    /**
     * See if we can attack entity at a location
     *
     * @param row    The row to check
     * @param column The column to check
     * @param d      The direction adjust look up towards
     * @return True if we can attack entity at that location
     */
    public boolean canBeAttacked(int row, int column, Direction d) {
        return canBeAttacked(row + d.getRowChange(), column + d.getColumnChange());

    }

    /**
     * See if entity is hero at this location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if entity is a hero at that location
     */
    public boolean isHero(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity instanceof Hero;
    }


    /**
     * See if entity is monster at this location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if entity is a monster at that location
     */
    public boolean isMonster(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity instanceof Monster;
    }

    @Override
    public String toString() {
        return gameString(this);
    }

    /**
     * The rows of the world
     * @return The rows of the world
     */
    public int getRows(){
        return world.length;
    }

    /**
     * The columns of the world
     * @return The columns of the world
     */
    public int getColumns(){
        return world[0].length;
    }
}
