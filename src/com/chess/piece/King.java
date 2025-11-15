package com.chess.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chess.Board.Board;
import com.chess.common.Location;
import com.chess.common.LocationFactory;
import com.chess.squares.Square;

/**
 * Represents a King chess piece. The king moves exactly one square in any
 * direction (horizontal, vertical, or diagonal), provided the destination
 * square is within board boundaries and not occupied by a friendly piece.
 *
 * <p>
 * This implementation does not currently check for advanced rules such as:
 * <ul>
 * <li>castling</li>
 * <li>moving into check</li>
 * <li>check, checkmate, or stalemate detection</li>
 * </ul>
 * It only computes the king's geometric movement possibilities.
 * </p>
 */
public class King extends AbstractPiece implements Movable {

    /**
     * Constructs a King of the specified color.
     *
     * @param pieceColor the color of the king (LIGHT or DARK)
     */
    public King(PieceColor pieceColor) {
        super(pieceColor);
        this.name = "King";
    }

    /**
     * Generates all valid king moves. The king may move one square in any
     * direction (8 possible directions), provided:
     * <ul>
     * <li>the destination square exists on the board</li>
     * <li>it is empty, or</li>
     * <li>it contains an opponent's piece (capture)</li>
     * </ul>
     *
     * <p>
     * Note: This method does not check for check or castling conditions.
     * </p>
     *
     * @param board the current board position
     * @return a list of legal move locations for this king
     */
    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> moves = new ArrayList<>();
        Location current = this.getCurrentSquare().geLocation();
        Map<Location, Square> map = board.getLocationSquareMap();

        // Directions the king can move: 8 directions, one step each
        int[][] direction = {
                { 1, 0 }, // right
                { -1, 0 }, // left
                { 0, 1 }, // up
                { 0, -1 }, // down
                { 1, 1 }, // up-right
                { -1, 1 }, // up-left
                { 1, -1 }, // down-right
                { -1, -1 } // down-left
        };

        for (int[] dir : direction) {
            int dx = dir[0];
            int dy = dir[1];

            // King moves exactly one step in each direction
            Location next = LocationFactory.build(current, dx, dy);

            if (map.containsKey(next)) {
                Square sq = map.get(next);

                if (!sq.isOccupied()) {
                    moves.add(next);
                } else if (sq.getCurrentAbstractPiece().getPieceColor() != this.getPieceColor()) {
                    moves.add(next); // capture allowed
                }
            }
        }

        return moves;
    }
}
