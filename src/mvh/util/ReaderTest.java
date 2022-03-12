package mvh.util;

import mvh.world.Entity;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;

import java.io.File;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(world.getColumns(), 3);
        assertEquals(world.getRows(), 3);

        // checks that there are entities at position (0,0) and (2,2)
        assertEquals(world.getEntity(0, 0) instanceof Entity, true);
        assertEquals(world.getEntity(2, 2) instanceof Entity, true);
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
        assertEquals(monster.getWeaponType().getWeaponStrength(), 4);
        assertEquals(monster.getSymbol(), 'M');
        assertEquals(monster.getHealth(), 10);

        // checks hero attributes
        Hero hero = (Hero) world.getEntity(2, 2);
        assertEquals(hero.weaponStrength(), 3);
        assertEquals(hero.armorStrength(), 1);
        assertEquals(hero.getHealth(), 10);
    }
}