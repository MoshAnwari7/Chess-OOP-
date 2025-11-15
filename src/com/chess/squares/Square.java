package com.chess.squares;

import com.chess.common.Location;
import com.chess.piece.AbstractPiece;

/**
 * Represents a single square on the chessboard.
 *
 * <p>
 * A square contains:
 * <ul>
 * <li>Its color (LIGHT or DARK)</li>
 * <li>Its grid location (file and rank)</li>
 * <li>Whether it is currently occupied</li>
 * <li>The piece that occupies it, if any</li>
 * </ul>
 * </p>
 *
 * <p>
 * The {@code Square} class does not enforce movement rules—that is handled by
 * the chess pieces and the {@link com.chess.Board.Board} class. It simply
 * stores the state of a single board tile and provides methods to manipulate
 * or inspect occupancy.
 * </p>
 */
public class Square {

    /** The color (light or dark) of this square. */
    private final SquareColor squareColor;

    /** The board location (file and rank) represented by this square. */
    private final Location location;

    /** True if a piece currently occupies this square. */
    private boolean isOccupied;

    /** The chess piece currently standing on this square, or null if empty. */
    private AbstractPiece currentAbstractPiece;

    /**
     * Creates a new square with the specified color and board location.
     * The square is initially empty.
     *
     * @param squareColor the color of this square (LIGHT or DARK)
     * @param location    the board location of this square
     */
    public Square(SquareColor squareColor, Location location) {
        this.squareColor = squareColor;
        this.location = location;
        this.isOccupied = false;
        this.currentAbstractPiece = null;
    }

    /**
     * Clears the square by removing any piece and marking it unoccupied.
     * This is typically used after a piece moves away from the square.
     */
    public void reset() {
        this.isOccupied = false;
        this.currentAbstractPiece = null;
    }

    /**
     * Returns the piece currently occupying this square.
     *
     * @return the occupying {@link AbstractPiece}, or null if empty
     */
    public AbstractPiece getCurrentAbstractPiece() {
        return currentAbstractPiece;
    }

    /**
     * Places a piece on this square and updates the occupancy flag.
     * Passing {@code null} removes any piece from the square.
     *
     * @param piece the piece to place, or null to clear the square
     */
    public void setCurrentAbstractPiece(AbstractPiece piece) {
        this.currentAbstractPiece = piece;
        this.isOccupied = (piece != null);
    }

    /**
     * Checks whether this square is currently occupied by a piece.
     *
     * @return true if occupied, false if empty
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Returns the color of this square.
     *
     * @return the square's color
     */
    public SquareColor getSquareColor() {
        return squareColor;
    }

    /**
     * Returns the board location of this square.
     *
     * @return the square's {@link Location}
     */
    public Location geLocation() {
        return location;
    }

    /**
     * Returns a string describing the square, including its color, location,
     * and occupancy state.
     *
     * @return a formatted string describing this square
     */
    @Override
    public String toString() {
        return "Square{" +
                "color=" + squareColor +
                ", location=" + location +
                ", occupied=" + isOccupied +
                '}';
    }
}
