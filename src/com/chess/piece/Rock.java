package com.chess.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chess.Board.Board;
import com.chess.common.Location;
import com.chess.common.LocationFactory;
import com.chess.squares.Square;

/**
 * Represents a Rook (named "Rock" in this code) chess piece.
 * 
 * <p>
 * The rook is a long-range sliding piece that moves horizontally or vertically
 * any number of squares until blocked. It cannot jump over pieces.
 * Movement stops when:
 * <ul>
 * <li>The rook reaches the edge of the board</li>
 * <li>The rook encounters another piece</li>
 * </ul>
 * If the encountered piece belongs to the opponent, the rook may capture it;
 * if the encountered piece is friendly, the rook must stop immediately before
 * it.
 * </p>
 *
 * <p>
 * This class does not execute moves on the board; it only computes valid
 * destination locations. Move execution is handled by {@link Board#movePiece}.
 * </p>
 */
public class Rock extends AbstractPiece implements Movable {

    /**
     * Constructs a rook ("Rock") with the specified color.
     *
     * @param pieceColor the color of this rook (LIGHT or DARK)
     */
    public Rock(PieceColor pieceColor) {
        super(pieceColor);
        this.name = "Rock"; // note: correct chess name is "Rook"
    }

    /**
     * Computes all valid rook moves. The rook moves along four straight
     * directions: left, right, up, and down. The rook continues sliding until
     * it reaches a piece or the board boundary.
     *
     * @param board the current board state
     * @return a list of legal move destinations for this rook
     */
    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> moves = new ArrayList<>();
        Location current = this.getCurrentSquare().geLocation();
        Map<Location, Square> map = board.getLocationSquareMap();

        // Rook movement: horizontal + vertical
        int[][] directions = {
                { 1, 0 }, // right
                { -1, 0 }, // left
                { 0, 1 }, // up
                { 0, -1 } // down
        };

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];

            Location next = LocationFactory.build(current, dx, dy);

            // Slide in this direction until blocked
            while (map.containsKey(next)) {
                Square sq = map.get(next);

                if (!sq.isOccupied()) {
                    moves.add(next);
                } else {
                    // capture allowed if opposite color
                    if (sq.getCurrentAbstractPiece().getPieceColor() != this.getPieceColor()) {
                        moves.add(next);
                    }
                    break; // stop sliding
                }

                next = LocationFactory.build(next, dx, dy);
            }
        }

        return moves;
    }
}
