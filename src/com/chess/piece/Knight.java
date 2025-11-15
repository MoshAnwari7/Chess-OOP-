package com.chess.piece;

import com.chess.Board.Board;
import com.chess.common.Location;
import com.chess.common.LocationFactory;
import com.chess.squares.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a Knight chess piece. The knight moves in fixed "L-shaped" jumps:
 * two squares in one direction and one square perpendicular to it.
 *
 * <p>
 * Unlike other pieces, the knight is the only piece that can jump over other
 * pieces. Therefore, this implementation does not use sliding movement loops.
 * Each of the eight possible L-shaped offsets is checked individually.
 * </p>
 */
public class Knight extends AbstractPiece implements Movable {

    /**
     * Constructs a Knight of the given color.
     *
     * @param pieceColor the color of the knight (LIGHT or DARK)
     */
    public Knight(PieceColor pieceColor) {
        super(pieceColor);
        this.name = "Knight";
    }

    /**
     * Computes all legal knight moves. Knights move in L-shaped jumps:
     * <ul>
     * <li>2 squares in one direction, 1 square perpendicular</li>
     * <li>Cannot land on a friendly piece</li>
     * <li>May capture an opposing piece</li>
     * <li>Can jump over all intervening pieces</li>
     * </ul>
     *
     * @param board the current board state
     * @return a list of valid move locations for this knight
     */
    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> moves = new ArrayList<>();
        Location current = this.getCurrentSquare().geLocation();
        Map<Location, Square> map = board.getLocationSquareMap();

        // All 8 L-shaped moves for a knight
        int[][] L_MOVES = {
                { 2, 1 },
                { 2, -1 },
                { -2, 1 },
                { -2, -1 },
                { 1, 2 },
                { 1, -2 },
                { -1, 2 },
                { -1, -2 }
        };

        for (int[] offset : L_MOVES) {
            int dx = offset[0];
            int dy = offset[1];

            Location next = LocationFactory.build(current, dx, dy);

            if (map.containsKey(next)) {
                Square sq = map.get(next);

                // empty square is allowed
                if (!sq.isOccupied()) {
                    moves.add(next);
                }
                // capture allowed if opponent
                else if (sq.getCurrentAbstractPiece().getPieceColor() != this.getPieceColor()) {
                    moves.add(next);
                }
            }
        }

        return moves;
    }
}
