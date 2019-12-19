package Evolution;

import java.util.Vector;

public class Grass {
    final Vector2d position;
    public Grass(Vector2d position) {
        this.position = position;
    }

    public String toString (){
        return "*";
    }

    public Vector2d getPosition(){
        return this.position;
    }
}
