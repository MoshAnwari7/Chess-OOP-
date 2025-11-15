package com.chess.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chess.Board.Board;
import com.chess.common.Location;
import com.chess.common.LocationFactory;
import com.chess.squares.Square;

/**
 * Represents a Bishop chess piece. A bishop moves diagonally in all four
 * directions until it encounters another piece or the boundary of the board.
 *
 * <p>
 * This class computes all valid diagonal destinations from the bishop's current
 * position. Movement continues along each diagonal direction until:
 * <ul>
 * <li>the bishop reaches the edge of the board, or</li>
 * <li>the next square contains a piece (capture allowed only if opposite
 * color)</li>
 * </ul>
 * </p>
 */
public class Bishop extends AbstractPiece implements Movable {

    /**
     * Constructs a Bishop of the given color.
     *
     * @param pieceColor the color of the bishop (LIGHT or DARK)
     */
    public Bishop(PieceColor pieceColor) {
        super(pieceColor);
        this.name = "Bishop";
    }

    /**
     * Generates all valid diagonal move locations for this bishop. A bishop
     * moves in straight diagonal lines, and its movement stops when obstructed
     * by another piece or the edge of the board.
     *
     * <p>
     * The method does not validate check/checkmate conditions — it only computes
     * geometrically legal squares based on board occupancy.
     * </p>
     *
     * @param board the current board state
     * @return a list of all legal destination squares for this bishop
     */
    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> moves = new ArrayList<>();
        Location current = this.getCurrentSquare().geLocation();
        Map<Location, Square> map = board.getLocationSquareMap();

        // All diagonal directions: up-right, up-left, down-right, down-left
        int[][] direction = {
                { 1, 1 },
                { -1, 1 },
                { 1, -1 },
                { -1, -1 }
        };

        for (int[] dir : direction) {
            int dx = dir[0];
            int dy = dir[1];

            Location next = LocationFactory.build(current, dx, dy);

            // Continue sliding along the diagonal until blocked
            while (map.containsKey(next)) {
                Square sq = map.get(next);

                if (!sq.isOccupied()) {
                    moves.add(next);
                } else {
                    // Capture allowed only if enemy piece
                    if (sq.getCurrentAbstractPiece().getPieceColor() != this.getPieceColor()) {
                        moves.add(next);
                    }
                    break; // cannot move past any piece
                }

                next = LocationFactory.build(next, dx, dy);
            }
        }

        return moves;
    }
}
