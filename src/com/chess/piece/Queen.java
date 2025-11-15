package com.chess.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chess.Board.Board;
import com.chess.common.Location;
import com.chess.common.LocationFactory;
import com.chess.squares.Square;

/**
 * Represents a Queen chess piece. The Queen is the most powerful piece
 * because she combines the full movement range of both the Rook (horizontal
 * and vertical sliding) and the Bishop (diagonal sliding).
 *
 * <p>
 * Movement rules implemented:
 * <ul>
 * <li>May move any number of squares horizontally</li>
 * <li>May move any number of squares vertically</li>
 * <li>May move any number of squares diagonally</li>
 * <li>Movement stops when a piece is encountered</li>
 * <li>Capturing allowed only if the encountered piece is of opposite color</li>
 * </ul>
 * </p>
 *
 * This class does not perform move execution. The {@link Board} class is
 * responsible for updating squares and piece positions.
 */
public class Queen extends AbstractPiece implements Movable {

    /**
     * Constructs a Queen of the specified color.
     *
     * @param pieceColor the color of this Queen (LIGHT or DARK)
     */
    public Queen(PieceColor pieceColor) {
        super(pieceColor);
        this.name = "Queen";
    }

    /**
     * Generates all valid squares that the Queen can move to, based on
     * sliding movement in 8 directions: horizontal, vertical, and diagonal.
     *
     * <p>
     * The Queen continues sliding in a direction until:
     * <ul>
     * <li>It goes out of bounds</li>
     * <li>It hits a piece</li>
     * </ul>
     * If the piece is an opponent's piece, the square is included as a capture.
     * If it is the same color, the Queen must stop before that square.
     * </p>
     *
     * @param board the current board state
     * @return a list of valid move locations
     */
    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> moves = new ArrayList<>();
        Location current = this.getCurrentSquare().geLocation();
        Map<Location, Square> map = board.getLocationSquareMap();

        // Directions: 4 rook + 4 bishop = 8 total
        int[][] directions = {
                { 1, 0 }, { -1, 0 }, // horizontal (rook)
                { 0, 1 }, { 0, -1 }, // vertical (rook)

                { 1, 1 }, { -1, 1 }, // diagonal (bishop)
                { 1, -1 }, { -1, -1 }
        };

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];

            Location next = LocationFactory.build(current, dx, dy);

            // Slide until blocked or off-board
            while (map.containsKey(next)) {
                Square sq = map.get(next);

                if (!sq.isOccupied()) {
                    moves.add(next);
                } else {
                    // Capture allowed if opponent
                    if (sq.getCurrentAbstractPiece().getPieceColor() != this.getPieceColor()) {
                        moves.add(next);
                    }
                    break; // blocked, stop sliding
                }

                next = LocationFactory.build(next, dx, dy);
            }
        }

        return moves;
    }
}
