package mvh.world;

import mvh.Main;
import mvh.enums.Direction;
import mvh.util.Reader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    /**
     * Checks to see that Hero chooses to move NORTHWEST in world.txt world scenario and
     * attackWorldSize 5
     */
    @Test
    void chooseMoveShouldReturnNorthWest() {
        // reading file and create a world
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);

        // create a hero
        Hero hero = new Hero(10, 'H', 3, 1);

        // check that direction is NORTHWEST
        assertEquals(Direction.NORTHWEST, hero.chooseMove(world.getLocal(5, 2, 2)));
    }

    /**
     *  Checks to see that Hero chooses to move SOUTH in world2.txt world scenario
     *  and attackWorldSize 5
     */
    @Test
    void chooseMoveShouldReturnSouth() {
        // reading file and create a world
        File fileWorld = new File("world2.txt");
        World world = Reader.loadWorld(fileWorld);

        // create a hero
        Hero hero = new Hero(10, 'H', 3, 1);

        // check that direction is SOUTH
        assertEquals(Direction.SOUTH, hero.chooseMove(world.getLocal(5, 0, 0)));
    }

    /**
     *  Checks that it returns null if no enemies nearby in world.txt world scenario and
     *  attackWorldSize 3
     */
    @Test
    void attackWhereNoEnemyNearby() {
        // reading file and create a world
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);

        // create a hero
        Hero hero = new Hero(10, 'H', 3, 1);

        // check that direction is Null
        assertNull(hero.attackWhere(world.getLocal(3, 2, 2)));
    }

    /**
     *  Checks to see that Hero chooses to move WEST in world5x5.txt world scenario
     *  and attackWorldSize 3
     */
    @Test
    void attackWhereEnemyNearby() {
        // reading file and create a world
        File fileWorld = new File("world5x5.txt");
        World world = Reader.loadWorld(fileWorld);

        // create a hero
        Hero hero = new Hero(10, 'H', 3, 1);

        // check that direction is WEST
        assertEquals(Direction.WEST, hero.attackWhere(world.getLocal(3, 1, 3)));
    }
}