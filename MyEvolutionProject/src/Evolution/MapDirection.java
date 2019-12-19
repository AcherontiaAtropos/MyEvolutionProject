package Evolution;

public enum MapDirection {
    NORTH,
    NE,
    EAST,
    ES,
    SOUTH,
    SW,
    WEST,
    WN;

    public MapDirection next() {
        switch (this) {
            case NORTH:
                return NE;
            case NE:
                return EAST;
            case EAST:
                return ES;
            case ES:
                return SOUTH;
            case SOUTH:
                return SW;
            case SW:
                return WEST;
            case WEST:
                return WN;
            case WN:
                return NORTH;
        }
        return null;
    }

    public Vector2d toUnitVector(){
        switch(this){
            case NORTH:
                return new Vector2d(0,1);
            case NE:
                return new Vector2d(1,1);
            case EAST:
                return new Vector2d(1,0);
            case ES:
                return new Vector2d(1, -1);
            case SOUTH:
                return new Vector2d(0,-1);
            case SW:
                return new Vector2d(-1,-1);
            case WEST:
                return new Vector2d(-1,0);
            case WN:
                return new Vector2d(-1,1);
            default:
                return new Vector2d(0,0);
        }
    }

}