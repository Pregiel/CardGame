package com.pregiel.cardgame;

/**
 * Created by Pregiel on 30.05.2018.
 */


public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    public Direction getOpposite() {
        switch (this) {
            case NORTH:
                return SOUTH;

            case EAST:
                return WEST;

            case SOUTH:
                return NORTH;

//                case WEST:
//                    return EAST;
        }
        return EAST;
    }

    public Direction getNext() {
        switch (this) {
            case NORTH:
                return EAST;

            case EAST:
                return SOUTH;

            case SOUTH:
                return WEST;

        }
        return NORTH;
    }
}
