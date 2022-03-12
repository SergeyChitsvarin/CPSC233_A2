package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;
import mvh.util.Reader;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    @Test
    void chooseMoveShouldReturnSouthEast() {
        // reading file
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);
        Monster monster = new Monster(10, 'M', WeaponType.AXE);

        assertEquals(Direction.SOUTHEAST, monster.chooseMove(world.getLocal(5, 0, 0)));
    }

    @Test
    void chooseMoveShouldReturnNorth() {
        // reading file
        File fileWorld = new File("world2.txt");
        World world = Reader.loadWorld(fileWorld);
        Monster monster = new Monster(10, 'M', WeaponType.SWORD);

        assertEquals(Direction.NORTH, monster.chooseMove(world.getLocal(5, 2, 0)));
    }

    @Test
    void attackWhereNoHeroNearby() {
        // reading file
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);
        Monster monster = new Monster(10, 'M', WeaponType.SWORD);

        assertNull(monster.attackWhere(world.getLocal(3, 0, 0)));
    }

    @Test
    void attackWhereHeroNearby() {
        // reading file
        File fileWorld = new File("world5x5.txt");
        World world = Reader.loadWorld(fileWorld);
        Monster monster = new Monster(10, 'M', WeaponType.SWORD);

        assertEquals(Direction.EAST, monster.attackWhere(world.getLocal(3, 1, 2)));
    }
}