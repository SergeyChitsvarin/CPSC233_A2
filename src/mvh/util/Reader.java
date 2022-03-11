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

                if (numOfElementsInLine == 2){
                    System.out.println("floor");
                }
                if (numOfElementsInLine == 6){
                    System.out.println("monster");
                    int row = Integer.parseInt(lineObjects[0]);
                    int column = Integer.parseInt(lineObjects[1]);
                    String type = lineObjects[2];

                    char symbol = lineObjects[3].charAt(0);
                    int health = Integer.parseInt(lineObjects[4]);

                    char weaponTypeChar = lineObjects[5].charAt(0);
                    WeaponType weaponType = WeaponType.getWeaponType(weaponTypeChar);
                    Monster monster = new Monster(health, symbol, weaponType);
                    world.addEntity(row, column, monster);
                    System.out.println(row + column + type + symbol + health + weaponType);

                }
                if (numOfElementsInLine == 7){
                    System.out.println("hero");
                    int row = Integer.parseInt(lineObjects[0]);
                    int column = Integer.parseInt(lineObjects[1]);
                    String type = lineObjects[2];

                    char symbol = lineObjects[3].charAt(0);
                    int health = Integer.parseInt(lineObjects[4]);

                    int weaponStrength = Integer.parseInt(lineObjects[5]);
                    int armorStrength = Integer.parseInt(lineObjects[6]);
                    Hero hero = new Hero(health, symbol, weaponStrength, armorStrength);
                    //Hero(int health, char symbol, int weaponStrength, int armorStrength)
                    world.addEntity(row, column, hero);
                }

                lineNum--;

            }
//            int lineNumber = 0;
//            int rows = 0;
//            int columns = 0;
//            while (fileReader.hasNextLine()) {
//                String line = fileReader.nextLine();
//                if (lineNumber == 0){
//                    rows = Integer.parseInt(line);
//                }
//
//                if (lineNumber == 1){
//                    columns = Integer.parseInt(line);
//                }
//                System.out.println(line);
//                lineNumber++;
//            }
//            fileReader.close();
            // World world = new World(rows, columns);
            //return world;


        } catch (FileNotFoundException error) {
            System.out.println("File was not found.");
            error.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
