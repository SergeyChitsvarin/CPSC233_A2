package mvh.util;

import mvh.enums.WeaponType;
import mvh.world.Entity;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;
import java.io.*;
import java.util.*;

/**
 * Class to assist reading in world file
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Reader {

    public static World loadWorld(File fileWorld) {
        try {
            Scanner fileReader = new Scanner(fileWorld);

            String firstLine = fileReader.nextLine();
            int rowCount = Integer.parseInt(firstLine);

            String secondLine = fileReader.nextLine();
            int columnCount = Integer.parseInt(secondLine);

            World world = new World(rowCount, columnCount);

            int lineNum = rowCount * columnCount;
            while (lineNum > 0){
                String line = fileReader.nextLine();
                String[] lineObjects = line.split(",");
                int numOfElementsInLine = lineObjects.length;

                if (numOfElementsInLine > 2) {
                    int row = Integer.parseInt(lineObjects[0]);
                    int column = Integer.parseInt(lineObjects[1]);
                    String type = lineObjects[2];
                    char symbol = lineObjects[3].charAt(0);
                    int health = Integer.parseInt(lineObjects[4]);

                    if (type.equals("MONSTER")) {
                        char weaponTypeChar = lineObjects[5].charAt(0);
                        WeaponType weaponType = WeaponType.getWeaponType(weaponTypeChar);
                        Monster monster = new Monster(health, symbol, weaponType);
                        world.addEntity(row, column, monster);

                    }

                    if (type.equals("HERO")) {
                        int weaponStrength = Integer.parseInt(lineObjects[5]);
                        int armorStrength = Integer.parseInt(lineObjects[6]);
                        Hero hero = new Hero(health, symbol, weaponStrength, armorStrength);
                        world.addEntity(row, column, hero);
                    }
                }

                lineNum--;

            }

        } catch (FileNotFoundException error) {
            System.out.println("File was not found.");
            error.printStackTrace();
            System.exit(1);
        } catch (Exception error){
            System.out.println("Unexpected error happened while loading world.");
            error.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
