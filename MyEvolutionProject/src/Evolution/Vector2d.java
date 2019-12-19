package Evolution;

import java.util.Random;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString(){
        String s =  "(" + this.x + ", " + this.y + ")";
        return s;
    }

    public boolean equals(Object other){
        if (other instanceof Vector2d) {
            Vector2d otherVector = (Vector2d) other;
            if (this.x == otherVector.x)
                return this.y == otherVector.y;
        }
        return false;
    }

    public Vector2d add (Vector2d other){

        return new Vector2d(this.x + other.x, this.y + other.y);
    }
    public Vector2d subtract (Vector2d other){

        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public int hashCode(){
        int hash = 0;
        hash += this.x*13;
        hash += this.y*57;
        return hash;
    }

    boolean precedes(Vector2d other){
        if (this.x <= other.x){
            return this.y <= other.y;
        }
        return false;
    }
    boolean follows(Vector2d other){
        if (this.x >= other.x){
            return this.y >= other.y;
        }
        return false;
    }

    Vector2d upperRight(Vector2d other){
        int x;
        if (this.x < other.x){x = other.x;}
        else {x = this.x;}
        int y;
        if (this.y < other.y){y = other.y;}
        else {y = this.y;}
        Vector2d output = new Vector2d(x,y);
        return output;
    }
    Vector2d lowerLeft(Vector2d other){
        int x;
        if (this.x > other.x){x = other.x;}
        else {x = this.x;}
        int y;
        if (this.y > other.y){y = other.y;}
        else {y = this.y;}
        Vector2d output = new Vector2d(x,y);
        return output;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    Vector2d randomNeighbour (){
        MapDirection mapDir = MapDirection.NORTH;
        int n = getRandomNumberInRange(0,8);
        for (int i = 0; i<n; i++) mapDir = mapDir.next();
        return this.add(mapDir.toUnitVector());

    }

}
