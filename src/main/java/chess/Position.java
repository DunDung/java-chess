package chess;

import chess.domain.Coordinate;

public class Position {
    private final Coordinate x;
    private final Coordinate y;

    public Position(int x, char y) {
        this(new Coordinate(x), new Coordinate(y));
    }

    public Position(Coordinate x, Coordinate y) {
        this.x = x;
        this.y = y;
    }
}
