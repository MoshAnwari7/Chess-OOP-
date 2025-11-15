package com.chess.piece;

import com.chess.Board.Board;
import java.util.*;

import com.chess.common.Location;
import com.chess.common.LocationFactory;
import com.chess.squares.Square;

/**
 * Represents a Pawn chess piece. Pawns move forward one square at a time,
 * with the option to move two squares forward on their first move. Pawns
 * capture diagonally one square forward-left or forward-right.
 *
 * <p>
 * This implementation does not include special pawn rules such as:
 * <ul>
 * <li>en passant</li>
 * <li>promotion</li>
 * </ul>
 * It focuses only on standard forward movement and diagonal captures.
 * </p>
 */
public class Pawn extends AbstractPiece implements Movable {

    /**
     * Tracks whether the pawn has moved before (two-step move allowed only once).
     */
    private boolean isFirstMove = true;

    /**
     * Constructs a Pawn with the specified color.
     *
     * @param pieceColor the color of this pawn (LIGHT or DARK)
     */
    public Pawn(PieceColor pieceColor) {
        super(pieceColor);
        this.name = "Pawn";
    }

    /**
     * Computes all legal pawn moves based on standard pawn movement rules:
     * <ul>
     * <li>Move one square forward if empty</li>
     * <li>Move two squares forward if first move and both squares empty</li>
     * <li>Capture diagonally forward-left or forward-right</li>
     * </ul>
     *
     * @param board the current board state
     * @return a list of valid move locations for this pawn
     */
    @Override
    public List<Location> getValidMoves(Board board) {

        List<Location> moves = new ArrayList<>();
        Location current = this.getCurrentSquare().geLocation();
        Map<Location, Square> map = board.getLocationSquareMap();

        // Direction: LIGHT pawns move "up" (rank +1), DARK pawns move "down" (rank -1)
        int direction = (this.getPieceColor() == PieceColor.LIGHT) ? 1 : -1;

        // --- Forward one square ---
        Location oneStep = LocationFactory.build(current, 0, direction);
        if (map.containsKey(oneStep) && !map.get(oneStep).isOccupied()) {
            moves.add(oneStep);

            // --- Forward two squares (first move only) ---
            Location twoStep = LocationFactory.build(current, 0, 2 * direction);
            if (isFirstMove && map.containsKey(twoStep) && !map.get(twoStep).isOccupied()) {
                moves.add(twoStep);
            }
        }

        // --- Diagonal captures ---
        Location diagLeft = LocationFactory.build(current, -1, direction);
        Location diagRight = LocationFactory.build(current, 1, direction);

        // capture left
        if (map.containsKey(diagLeft) &&
                map.get(diagLeft).isOccupied() &&
                map.get(diagLeft).getCurrentAbstractPiece().getPieceColor() != this.getPieceColor()) {
            moves.add(diagLeft);
        }

        // capture right
        if (map.containsKey(diagRight) &&
                map.get(diagRight).isOccupied() &&
                map.get(diagRight).getCurrentAbstractPiece().getPieceColor() != this.getPieceColor()) {
            moves.add(diagRight);
        }

        return moves;
    }
}
