package com.chess.piece;

import com.chess.Board.Board;
import com.chess.common.Location;

import java.util.List;

/**
 * Interface representing the movement capabilities of a chess piece.
 *
 * <p>
 * Any piece that can generate valid moves must implement this interface.
 * Each implementing class is responsible for providing its own movement
 * logic based on the rules of chess (e.g., bishop moves diagonally, rook
 * moves in straight lines, knight jumps in L-shapes).
 * </p>
 *
 * <p>
 * This interface does not handle move execution or board updates. Those
 * responsibilities belong to the {@link Board} class. It only defines how
 * to obtain legal movement squares.
 * </p>
 */
public interface Movable {

    /**
     * Generates a list of all valid movement locations for the piece based on
     * the current state of the board. Implementing classes must apply their
     * specific movement rules and consider occupancy and capturing rules.
     *
     * @param board the chess board used to evaluate legal move destinations
     * @return a list of valid {@link Location} objects where the piece can move
     */
    List<Location> getValidMoves(Board board);

    /**
     * Checks whether a given destination location is included within the piece's
     * valid move set. This is a convenience method that simply calls
     * {@link #getValidMoves(Board)} and checks list membership.
     *
     * @param board the board used to compute valid moves
     * @param to    the target location being evaluated
     * @return true if the piece can legally move to the given location; false
     *         otherwise
     */
    default boolean isValidMove(Board board, Location to) {
        return getValidMoves(board).contains(to);
    }
}
