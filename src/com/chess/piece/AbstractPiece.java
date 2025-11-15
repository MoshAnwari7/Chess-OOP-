package com.chess.piece;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

import com.chess.Board.Board;
import com.chess.common.Location;
import com.chess.squares.Square;

/**
 * Base class for all chess pieces. Provides core shared functionality such as
 * piece identity (name and color), tracking the piece's current square, and
 * helper methods used for move validation.
 *
 * <p>
 * Specific movement logic (e.g., rook, bishop, knight patterns) is implemented
 * in subclasses. This abstract class also implements the {@link Movable}
 * interface, requiring each concrete piece to define how it generates valid
 * moves.
 * </p>
 */
public abstract class AbstractPiece implements Movable {

    /** The display name of the piece (e.g., "Pawn", "Rook"). */
    protected String name;

    /** The color of the piece (LIGHT or DARK). */
    protected PieceColor pieceColor;

    /** The square on which this piece currently resides. */
    protected Square currentSquare;

    /**
     * Constructs a new piece with the specified color.
     *
     * @param pieceColor the color of the piece
     */
    public AbstractPiece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    /**
     * Returns the name of this piece.
     *
     * @return the piece name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the color of this piece.
     *
     * @return the piece color
     */
    public PieceColor getPieceColor() {
        return pieceColor;
    }

    /**
     * Returns the square this piece currently occupies.
     *
     * @return the current square
     */
    public Square getCurrentSquare() {
        return currentSquare;
    }

    /**
     * Assigns a new current square for this piece. Used after moves or board
     * initialization.
     *
     * @param currentSquare the square this piece will occupy
     */
    public void setCurrentSquare(Square currentSquare) {
        this.currentSquare = currentSquare;
    }

    /**
     * Filters a list of candidate move locations, removing:
     * <ul>
     * <li>Locations outside the board</li>
     * <li>Squares occupied by a piece of the same color</li>
     * </ul>
     *
     * <p>
     * This method is used by concrete pieces after generating raw move
     * candidates. It ensures only legal target squares remain.
     * </p>
     *
     * @param board      the board used to validate square states
     * @param candidates raw move locations before filtering
     * @return a filtered list of valid move locations
     */
    protected List<Location> cleanMoves(Board board, List<Location> candidates) {
        Map<Location, Square> map = board.getLocationSquareMap();

        return candidates.stream()
                .filter(map::containsKey)
                .filter(loc -> {
                    Square sq = map.get(loc);
                    if (!sq.isOccupied())
                        return true;
                    return sq.getCurrentAbstractPiece().getPieceColor() != this.getPieceColor();
                })
                .collect(Collectors.toList());
    }

    /**
     * Returns a string representation containing the piece's type and color.
     *
     * @return human-readable description of this piece
     */
    @Override
    public String toString() {
        return "AbstractPiece{" +
                "Name=" + name + '\'' +
                "Piece Color=" + pieceColor +
                '}';
    }
}
