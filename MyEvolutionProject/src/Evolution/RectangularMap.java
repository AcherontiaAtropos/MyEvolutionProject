package Evolution;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.*;

import static java.lang.Math.toIntExact;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import org.json.simple.parser.ParseException;
public class RectangularMap {

    private final Vector2d startOfTheMap = new Vector2d(0,0);
    private static final String filePath = "Evolution/parameters.json";

    public final int width;
    public final int height;

    int moveEnergy = 1;
    int plantEnergy = 1;
    int numberOfGrass = 8;
    double jungleRatio = 0.25;

    protected final Vector2d endOfTheMap;
    public final Jungle jungle;
    public List<Animal> animalsList = new ArrayList<>();
    protected Map<Vector2d, Grass> grassMap = new LinkedHashMap<>();

    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.endOfTheMap = new Vector2d(width - 1, height - 1);

        try (FileReader reader = new FileReader(ClassLoader.getSystemResource(filePath).getFile())) {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            this.moveEnergy = toIntExact((long) jsonObject.get("moveEnergy"));
            this.plantEnergy = toIntExact((long) jsonObject.get("plantEnergy"));
            this.numberOfGrass = toIntExact((long) jsonObject.get("initialNumberOfGrass"));;

            String s = (String) jsonObject.get("jungleRatio");
            this.jungleRatio = Double.parseDouble(s);
            this.jungleRatio = Math.sqrt(this.jungleRatio);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int jungleWidth = (int) Math.ceil(width *jungleRatio);
        int jungleHeight = (int) Math.ceil(height *jungleRatio);
        this.jungle = new Jungle(jungleWidth, jungleHeight);

        for (int i = 0; i < this.numberOfGrass; i++) {
            addGrass();
        }
    }

    public void addGrass(){
        //add in Jungle;
        if (!jungleIsFull()) {
            boolean toBeContinued = true;
            int forceToStop = 3000; //usprawnienie dla naprawdę dużej planszy
            while (toBeContinued) {
                toBeContinued = false;
                Vector2d pos = new Vector2d(getRandomNumberInRange(0, width - 1), getRandomNumberInRange(0, height - 1));
                Grass insertGrass = new Grass(pos);

                if (grassMap.containsKey(insertGrass.getPosition())) {
                    toBeContinued = true;
                    forceToStop -= 1;
                }
                if (animalObjectAt(insertGrass.getPosition()).size() >= 1) {
                    toBeContinued = true;
                    forceToStop -= 1;
                } else if (!pos.precedes(jungle.endOfTheJungle) && pos.follows(jungle.startOfTheJungle)) {
                    toBeContinued = true;
                    forceToStop -= 1;
                } else this.grassMap.put(insertGrass.getPosition(), insertGrass);
                if (forceToStop <= 0) break;
            }
        }
        //add in step;
        if (!stepIsFull()) {
            boolean toBeContinued = true;
            int forceToStop = 3000;
            while (toBeContinued) {
                toBeContinued = false;
                Vector2d pos = new Vector2d(getRandomNumberInRange(0, width - 1), getRandomNumberInRange(0, height - 1));
                Grass insertGrass = new Grass(pos);

                if (grassMap.containsKey(insertGrass.getPosition())) {
                    toBeContinued = true;
                    forceToStop -= 1;
                }
                if (animalObjectAt(insertGrass.getPosition()).size() >= 1) {
                    toBeContinued = true;
                    forceToStop -= 1;
                } else if (pos.precedes(jungle.endOfTheJungle) && pos.follows(jungle.startOfTheJungle)) {
                    toBeContinued = true;
                    forceToStop -= 1;
                } else this.grassMap.put(insertGrass.getPosition(), insertGrass);

                if (forceToStop <= 0) return;
            }
        }
    }

    public boolean jungleIsFull(){
        for (int i = 0; i<jungle.width; i++)
            for (int j = 0; j<jungle.height; j++){
                if ( !isOccupied(new Vector2d(i,j))) return false;
            }
        return true;
    }
    public boolean stepIsFull(){
        for (int i = jungle.width; i<width; i++)
            for (int j = 0; j<jungle.height; j++){
                if ( !isOccupied(new Vector2d(i,j))) return false;
            }
        for (int i = 0; i<width; i++)
            for (int j = jungle.height; j<height; j++){
                if ( isOccupied(new Vector2d(i,j))) return false;
            }
        return true;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        StringBuilder builder = new StringBuilder();
        builder.append(mapVisualizer.draw(this.startOfTheMap, this.endOfTheMap));
        return builder.toString();
    }

    public boolean isOccupied(Vector2d position) {

        for (int i=0; i<animalsList.size(); i++){
            if (this.animalsList.get(i).getPosition().equals(position)) return true;
        }

        if (grassMap.containsKey(position)) return true;
        return false;
    }

    public void place(Animal animal) {
            this.animalsList.add(animal);
    }

    public Object objectAt(Vector2d position) {
        for (int i=0; i<animalsList.size(); i++) {
            if (this.animalsList.get(i).getPosition().equals(position)) return animalsList.get(i);
        }
        return (grassObjectAt(position));
    }

    public Grass grassObjectAt (Vector2d position){
        if (this.grassMap.containsKey(position))
            return  this.grassMap.get(position);
        return null;
    }

    public List<Animal> animalObjectAt (Vector2d position){
        List<Animal> rivals = new ArrayList<>();
        for (int i=0; i<animalsList.size(); i++) {
            if (this.animalsList.get(i).getPosition().equals(position)) rivals.add(animalsList.get(i));
        }
        return rivals;
    }
    public List<Animal> strongAnimalObjectAt (Vector2d position){
        List<Animal> rivals = new ArrayList<>();
        for (int i=0; i<animalsList.size(); i++) {
            if (this.animalsList.get(i).getPosition().equals(position) && this.animalsList.get(i).getEnergyDay()>=Animal.startEnergy/2) rivals.add(animalsList.get(i));
        }
        return rivals;
    }

    public void eatGrassAt (Vector2d position){
        this.grassMap.remove(position);
        List<Animal> rivals = strongAnimalObjectAt(position);
        if (rivals.size() == 1) {rivals.get(0).energyDay += plantEnergy; return;}
        double maxEnergyDay = 0;
        for (int i=0; i<rivals.size(); i++){
            if (rivals.get(i).getEnergyDay()>maxEnergyDay) maxEnergyDay = rivals.get(i).getEnergyDay();
        }
        int animalsToShare = 0;
        for (int i=0; i<rivals.size(); i++){
            if (rivals.get(i).getEnergyDay() == maxEnergyDay) animalsToShare += 1;
        }
        for (int i=0; i<rivals.size(); i++){
            if (rivals.get(i).getEnergyDay() == maxEnergyDay) rivals.get(i).energyDay += (plantEnergy/animalsToShare);
        }

    }


    public void copulateAt(Vector2d position) {
        List<Animal> rivals = animalObjectAt(position);
        if (rivals.size() == 2) {makeChild(rivals.get(0),rivals.get(1)); return;}
        double maxEnergyDay = 0;
        int maxEnergyHolders = 0;
        for (int i=0; i<rivals.size(); i++){
            if (rivals.get(i).getEnergyDay()>maxEnergyDay){
                maxEnergyDay = rivals.get(i).getEnergyDay();
                maxEnergyHolders = 1;
            }
            if (rivals.get(i).getEnergyDay()==maxEnergyDay)
                maxEnergyHolders += 1;
        }

        Animal parent1 = null;
        Animal parent2 = null;

        if (maxEnergyHolders >= 2){
            for (int i=0; i<rivals.size(); i++){
                if (rivals.get(i).getEnergyDay() == maxEnergyDay) {
                    if (parent1 == null) parent1 = rivals.get(i);
                    else {
                        parent2 = rivals.get(i);
                        makeChild(parent1,parent2);
                        return;
                    }
                }
            }
        }
        else {
            for (int i=0; i<rivals.size(); i++){
                if (rivals.get(i).getEnergyDay() == maxEnergyDay) parent1 = rivals.get(i);
            }
            double secMaxEnergyDay = 0;
            for (int i=0; i<rivals.size(); i++){
                if (rivals.get(i).getEnergyDay() >= secMaxEnergyDay &&
                rivals.get(i).getEnergyDay() < maxEnergyDay) secMaxEnergyDay = rivals.get(i).getEnergyDay();
            }
            for (int i=0; i<rivals.size(); i++){
                if (rivals.get(i).getEnergyDay() == secMaxEnergyDay) parent2 = rivals.get(i);
            }
            makeChild(parent1, parent2);

        }

    }

    private void makeChild(Animal parent1, Animal parent2) {

        int cut1 = getRandomNumberInRange(0,32);
        int cut2 = getRandomNumberInRange(0,32);

        if(cut1>cut2){
            int t = cut2; cut2 = cut1; cut1 = t;
        }

        final MoveDirection [] genotype = new MoveDirection[32];
        for (int i=0; i<cut1; i++)
            genotype[i] = parent1.genotype[i];
        for (int i=cut1; i<cut2; i++)
            genotype[i] = parent2.genotype[i];
        for (int i=cut2; i<32; i++)
            genotype[i] = parent1.genotype[i];

        parent1.energyDay = (parent1.energyDay * 3)/4;
        parent2.energyDay = (parent2.energyDay * 3)/4;
        parent1.noChildren += 1;
        parent2.noChildren += 1;

        Animal child = new Animal(this,parent1.getPosition().randomNeighbour(), genotype);
        child.energyDay = parent1.energyDay/3 + parent2.energyDay/3;
        child.repairGenotype();


        return;
    }

    private void removingDead(){
        for (int i=0; i<animalsList.size(); i++)
            if(animalsList.get(i).getEnergyDay() <= 0) animalsList.remove(animalsList.get(i));
    }

    private void running() {
        for (int i=0; i<animalsList.size(); i++)
            animalsList.get(i).move();
    }

    private void eating(){
        for (int i=0; i<animalsList.size(); i++)
            animalsList.get(i).tryToEat();
    }
    private void copulating(){
        List<Vector2d> places = new ArrayList<>();

        for (int i=0; i<animalsList.size(); i++)
            if(animalsList.get(i).tryToCopulate()) {
                Vector2d place = animalsList.get(i).getPosition();
                boolean newPlace = true;
                for(int j=0; j<places.size(); j++){
                    if(places.get(j).equals(place)) newPlace = false;
                }
            if (newPlace) places.add(place);
            }
        for (int i =0; i<places.size(); i++){
            copulateAt(places.get(i));
        }
    }
    private void sleeping(){
        for (int i=0; i<animalsList.size(); i++)
            animalsList.get(i).energyDay -= moveEnergy;
    }

    public void anotherDay(){
        removingDead();
        running();
        eating();
        copulating();
        sleeping();
        addGrass();
    }

}
