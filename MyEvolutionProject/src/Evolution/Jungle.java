package Evolution;

public class Jungle {
    final Vector2d endOfTheJungle;
    final Vector2d startOfTheJungle = new Vector2d(0,0);
    public final int width;
    public final int height;

    public Jungle(int width, int height) {
        this.width = width;
        this.height = height;
        this.endOfTheJungle = new Vector2d(width - 1, height -1);
    }
}
