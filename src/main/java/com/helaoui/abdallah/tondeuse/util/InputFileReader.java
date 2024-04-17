package com.helaoui.abdallah.tondeuse.util;

import com.helaoui.abdallah.tondeuse.model.Pelouse;
import com.helaoui.abdallah.tondeuse.model.Tondeuse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputFileReader {
    public static void readInputFile(String filePath) throws IOException {
        List<Tondeuse> tondeuses = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for(String line : lines) {
            System.out.println(line); // This will show exactly what is read from the file
        }
        Pelouse pelouse = parsePelouse(lines.get(0));

        for (int i = 1; i < lines.size(); i++) {
            String[] mowerInfo = lines.get(i).split(" ");
            int x = Integer.parseInt(mowerInfo[0]);
            int y = Integer.parseInt(mowerInfo[1]);
            char orientation = mowerInfo[2].charAt(0);
            String instructions = mowerInfo[3]; // Instructions are now part of the same line
            Tondeuse tondeuse = new Tondeuse(x, y, orientation);
            tondeuse.setInstructions(instructions);
            tondeuses.add(tondeuse);
        }


    }

    private static Pelouse parsePelouse(String line) {
        String[] lawnInfo = line.split(" ");
        int topRightX = Integer.parseInt(lawnInfo[0]);
        int topRightY = Integer.parseInt(lawnInfo[1]);
        return new Pelouse(topRightX, topRightY);
    }
}
