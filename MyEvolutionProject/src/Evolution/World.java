package Evolution;

import GUI.*;
import java.awt.*;
import static Evolution.MapDirection.*;

import static java.lang.Math.toIntExact;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class World {

    private static final String filePath = "Evolution/parameters.json";

    public static void main(String[] args) {
        try (FileReader reader = new FileReader(ClassLoader.getSystemResource(filePath).getFile())) {
            // read the json file


            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            final int width = toIntExact((long) jsonObject.get("width"));
            final int height = toIntExact((long) jsonObject.get("height"));
            final int startEnergy = toIntExact((long) jsonObject.get("startEnergy"));
            Animal.setStartEnergy(startEnergy);
            final int moveEnergy = toIntExact((long) jsonObject.get("moveEnergy"));
            final int plantEnergy = toIntExact((long) jsonObject.get("plantEnergy"));

            final double initialNumberOfAnimals = toIntExact((long) jsonObject.get("initialNumberOfAnimals"));

            RectangularMap map = new RectangularMap(width, height);

            Vector2d InitialPosition = new Vector2d(0, 0);

            MoveDirection[] InitGenes = new MoveDirection[32];
            for (int i = 0; i < 32; i++) InitGenes[i] = MoveDirection.FORWARD;

            for (int i = 0; i<initialNumberOfAnimals; i++) {
                Animal wolf = new Animal(map);
                map.place(wolf);
            }


            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MyFrame(map);
                }
            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}