package mvh.util;

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
            int lineNumber = 0;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                if (lineNumber == 0){
                    int rows = Integer.parseInt(line);

                }

                if (lineNumber == 1){
                    int columns = Integer.parseInt(line);

                }

                lineNumber++;
            }
            fileReader.close();
            System.out.println("rows:" + rows);
            System.out.println("columns:" + columns);

            // World.world(rows, columns);


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return null;
    }
}
