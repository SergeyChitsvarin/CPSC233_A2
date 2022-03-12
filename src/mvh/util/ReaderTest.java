package mvh.util;

import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReaderTest {

    /**
     * It creates a 3X3 world (3 columns and 3 rows) with Entities at position 0,0 and 2,2
     */
    @Test
    void loadWorldSuccess3X3WithEntities(){
        // reading file
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);

        // checks number of columns and rows
        assertEquals(3, world.getColumns());
        assertEquals(3, world.getRows());

        // checks that there are entities at position (0,0) and (2,2)
        assertNotNull(world.getEntity(0, 0));
        assertNotNull(world.getEntity(2, 2));
    }

    /**
     * It creates a Monster and a Hero with proper attribute values based on what is specified in a file
     */
    @Test
    void loadWorldSuccessWithHeroAndMonster(){
        // reading file
        File fileWorld = new File("world.txt");
        World world = Reader.loadWorld(fileWorld);

        // checks monster attributes
        Monster monster = (Monster) world.getEntity(0, 0);
        assertEquals(4, monster.getWeaponType().getWeaponStrength());
        assertEquals('M', monster.getSymbol());
        assertEquals(10, monster.getHealth());

        // checks hero attributes
        Hero hero = (Hero) world.getEntity(2, 2);
        assertEquals(3, hero.weaponStrength());
        assertEquals(1, hero.armorStrength());
        assertEquals(10, hero.getHealth());
    }
}