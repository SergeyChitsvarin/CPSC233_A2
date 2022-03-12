package mvh.world;

import mvh.util.Reader;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    /**
     * Returns string representation of the world (world scenario world.txt)
     */
    @Test
    void worldStringSuccess() {
        // reading file
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);

        // expected string
        String expectedString =
                "#####\n" +
                "#M..#\n" +
                "#...#\n" +
                "#..H#\n" +
                "#####";
        assertEquals(expectedString, world.worldString());
    }

    /**
     * Returns string representation of the game (world scenario world.txt)
     */
    @Test
    void gameStringSuccess() {
        // reading file
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);

        // expected string
        String expectedString =
                "Mons(1)\tM\t10\tALIVE\tSWORD\n" +
                "Hero(2)\tH\t10\tALIVE\t1  3\n";
        assertEquals(expectedString, world.gameString(world));
    }

    /**
     * Tests scenario with attackWorldSize 3 from Monster point (world scenario world.txt)
     */
    @Test
    void getLocalAttackWorldSize3() {
        // reading file
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);

        World local = world.getLocal(3, 0, 0);

        // expected string
        String expectedString =
                "#####\n" +
                "#####\n" +
                "##M.#\n" +
                "##..#\n" +
                "#####";
        assertEquals(expectedString, local.worldString());
    }

    /**
     * Tests scenario with attackWorldSize 3 from Hero point (world scenario world.txt)
     */
    @Test
    void getLocalAttackWorldSize3FromHeroPoint() {
        // reading file
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);

        World local = world.getLocal(3, 2, 2);

        // expected string
        String expectedString =
                "#####\n" +
                "#..##\n" +
                "#.H##\n" +
                "#####\n" +
                "#####";
        assertEquals(expectedString, local.worldString());
    }

    /**
     * Tests scenario with attackWorldSize 5 from Monster point (world scenario world.txt)
     */
    @Test
    void getLocalAttackWorldSize5() {
        // reading file
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);

        World local = world.getLocal(5, 0, 0);

        // expected string
        String expectedString =
                "#######\n" +
                "#######\n" +
                "#######\n" +
                "###M..#\n" +
                "###...#\n" +
                "###..H#\n" +
                "#######";
        assertEquals(expectedString, local.worldString());
    }
}