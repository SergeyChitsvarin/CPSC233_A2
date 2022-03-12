package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;
import mvh.util.Reader;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    /**
     * Checks that monster chooses SOUTHEAST direction for world scenario world.txt and attackWorldSize 5
     */
    @Test
    void chooseMoveShouldReturnSouthEast() {
        // reading file
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);

        // create a monster
        Monster monster = new Monster(10, 'M', WeaponType.AXE);

        // checks that direction is SOUTHEAST
        assertEquals(Direction.SOUTHEAST, monster.chooseMove(world.getLocal(5, 0, 0)));
    }

    /**
     * Checks that monster chooses NORTH direction for world scenario world2.txt and attackWorldSize 5
     */
    @Test
    void chooseMoveShouldReturnNorth() {
        // reading file
        File fileWorld = new File("world2.txt");
        World world = Reader.loadWorld(fileWorld);

        // create a monster
        Monster monster = new Monster(10, 'M', WeaponType.SWORD);

        // checks that direction is  NORTH
        assertEquals(Direction.NORTH, monster.chooseMove(world.getLocal(5, 2, 0)));
    }

    /**
     * Checks that monster direction is null if there are no heroes nearby and attackWorldSize 3
     * (for world scenario world.txt)
     */
    @Test
    void attackWhereNoHeroNearby() {
        // reading file
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);

        // create a monster
        Monster monster = new Monster(10, 'M', WeaponType.SWORD);

        // check that direction is null
        assertNull(monster.attackWhere(world.getLocal(3, 0, 0)));
    }

    /**
     * Checks that monster chooses EAST direction for world scenario world5x5.txt and attackWorldSize 3
     */
    @Test
    void attackWhereHeroNearby() {
        // reading file
        File fileWorld = new File("world5x5.txt");
        World world = Reader.loadWorld(fileWorld);

        // create a monster
        Monster monster = new Monster(10, 'M', WeaponType.SWORD);

        // checks that direction is EAST
        assertEquals(Direction.EAST, monster.attackWhere(world.getLocal(3, 1, 2)));
    }
}