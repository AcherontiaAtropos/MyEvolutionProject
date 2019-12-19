package Evolution;

public enum MoveDirection {
    FORWARD,
    FL,
    LEFT,
    BL,
    BACKWARD,
    BR,
    RIGHT,
    FR;

    public int toNumber(){
        switch (this) {
            case FORWARD:
                return 0;
            case FR:
                return 1;
            case RIGHT:
                return 2;
            case BR:
                return 3;
            case BACKWARD:
                return 4;
            case BL:
                return 5;
            case LEFT:
                return 6;
            case FL:
                return 7;
        }
        return 0;
    }
    public static MoveDirection fromNumber (int n){
        switch (n) {
            case 0:
                return FORWARD;
            case 1:
                return FR;
            case 2:
                return RIGHT;
            case 3:
                return BR;
            case 4:
                return BACKWARD;
            case 5:
                return BL;
            case 6:
                return LEFT;
            case 7:
                return FL;
        }
        return null;
    }

}
