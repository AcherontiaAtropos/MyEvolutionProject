package Evolution;

import java.util.Random;

import static Evolution.MapDirection.*;
import static Evolution.MoveDirection.FORWARD;
import static Evolution.MoveDirection.fromNumber;

public class Animal {
    private Vector2d position;
    MapDirection direction;
    static double startEnergy;
    double energyDay = 15;
    int noChildren = 0;
    public final MoveDirection [] genotype = new MoveDirection[32];
    final RectangularMap map;

    public Animal(RectangularMap map){
        this.direction = NORTH;
        int rand = getRandomNumberInRange(0, 7);
        for (int i = 0; i< rand; i++){
            this.direction = this.direction.next();
        }
        this.map = map;
        int width = map.width;
        int height = map.height;
        Vector2d initialPosition = new Vector2d(getRandomNumberInRange(0, width - 1), getRandomNumberInRange(0, height - 1));

        this.position = initialPosition;
        for (int i=0; i<32; i++)
            this.genotype [i] = fromNumber(getRandomNumberInRange(0,7));
        repairGenotype();
    }

    public Animal(RectangularMap map, Vector2d initialPosition,MoveDirection [] initialGenotype){
        this.direction = NORTH;
        int rand = getRandomNumberInRange(0, 7);
        for (int i = 0; i< rand; i++){
            this.direction = this.direction.next();
        }
        this.position = initialPosition;
        this.map = map;
        for (int i=0; i<32; i++)
            this.genotype [i] = initialGenotype [i];
    }

    static void setStartEnergy(int sE){startEnergy = sE;}
    public String toString (){
        return "A";
    }

    public Vector2d getPosition () {
        return this.position;
    }
    public double getEnergyDay () {
        return this.energyDay;
    }

    public MoveDirection getRandomMoveDirection() {
        int rnd = new Random().nextInt(this.genotype.length);
        return this.genotype[rnd];
    }

    public void move (){
        MoveDirection directionOfMove = getRandomMoveDirection();
        int turnings = directionOfMove.toNumber();
        for (int i = 0; i< turnings; i++){
            this.direction = direction.next();
        }

        Vector2d p = this.position.add(this.direction.toUnitVector());

        this.position = p;

        turnOverEdges();
    }

    public void turnOverEdges() {
        if (this.position.x > this.map.endOfTheMap.x)
            this.position = new Vector2d(0, this.position.y);
        else if (this.position.x < 0)
            this.position = new Vector2d(this.map.endOfTheMap.x, this.position.y);

        if (this.position.y > this.map.endOfTheMap.y)
            this.position = new Vector2d(this.position.x,0);
        else if (this.position.y < 0)
            this.position = new Vector2d(this.position.x , this.map.endOfTheMap.y);

    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void repairGenotype(){
        boolean correctGenes = false;
        while (! correctGenes) {
            correctGenes = true;

            int[] genes = new int[8];
            for (int i = 0; i < 8; i++) genes[i] = 0;
            for (int i = 0; i < 32; i++) genes[this.genotype[i].toNumber()] += 1;
            int j = getRandomNumberInRange(0,31);
            for (int i = 0; i < 8; i++) {
                if (genes[i] == 0) {
                    genotype[j] = MoveDirection.fromNumber(i);
                    correctGenes = false;
                }
            }
        }
    }

    public void tryToEat(){

        if(map.grassObjectAt(this.position) != null) this.map.eatGrassAt(this.position);
    }

    public boolean tryToCopulate(){
        if(map.animalObjectAt(this.position).size() > 1 && energyDay >= startEnergy/2)  return true;
        return false;
    }


}
