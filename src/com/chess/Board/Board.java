package com.chess.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.common.File;
import com.chess.common.Location;
import com.chess.piece.AbstractPiece;
import com.chess.piece.PieceColor;
import com.chess.piece.PieceFactory;
import com.chess.squares.Square;
import com.chess.squares.SquareColor;

/**
 * The Board class represents the 8x8 chessboard and manages all squares,
 * pieces, and positional updates during gameplay.
 *
 * <p>
 * During construction, the board initializes all 64 squares, assigns their
 * colors, populates piece positions using {@link PieceFactory}, and stores
 * references to all light and dark pieces for easy access.
 * </p>
 *
 * <p>
 * This class provides core functionality such as retrieving squares, printing
 * the board state, and executing piece movements. All move execution and square
 * updates occur through this class to maintain consistent game state.
 * </p>
 */
public class Board {

    private static final int BOARD_LENGTH = 8;

    /** Maps each Location (file + rank) to its corresponding Square. */
    private final Map<Location, Square> locationSquareMap;

    /** 2D array representation of the board indexed by row and file. */
    private final Square[][] boardSquares = new Square[BOARD_LENGTH][BOARD_LENGTH];

    /** List of all white pieces on the board. */
    private final List<AbstractPiece> lightPieces = new ArrayList<>();

    /** List of all black pieces on the board. */
    private final List<AbstractPiece> darkPieces = new ArrayList<>();

    /**
     * Constructs a new chess board, initializes all squares, assigns correct
     * square colors, and places all chess pieces in their standard starting
     * positions using {@link PieceFactory}.
     */
    public Board() {
        locationSquareMap = new HashMap<>();
        Map<Location, AbstractPiece> pieces = PieceFactory.getPieces();

        // Build the board row by row
        for (int row = 0; row < BOARD_LENGTH; row++) {

            // Determine starting color for the row
            SquareColor squareColor = (row % 2 == 0)
                    ? SquareColor.LIGHT
                    : SquareColor.DARK;

            for (File file : File.values()) {

                int rank = BOARD_LENGTH - row; // 8 → 1
                Location loc = new Location(file, rank);
                Square square = new Square(squareColor, loc);

                // Set piece on this square if one exists in starting layout
                if (pieces.containsKey(loc)) {
                    AbstractPiece piece = pieces.get(loc);
                    square.setCurrentAbstractPiece(piece);
                    piece.setCurrentSquare(square);

                    if (piece.getPieceColor() == PieceColor.LIGHT) {
                        lightPieces.add(piece);
                    } else {
                        darkPieces.add(piece);
                    }
                }

                // Correct indexing by file ordinal (A=0 ... H=7)
                boardSquares[row][file.ordinal()] = square;

                // Register square in lookup map
                locationSquareMap.put(loc, square);

                // Alternate color horizontally
                squareColor = (squareColor == SquareColor.LIGHT)
                        ? SquareColor.DARK
                        : SquareColor.LIGHT;
            }
        }
    }

    /**
     * Returns a mapping of all board locations to their corresponding squares.
     *
     * @return map containing all squares indexed by Location
     */
    public Map<Location, Square> getLocationSquareMap() {
        return locationSquareMap;
    }

    /**
     * Returns a list of all white pieces currently on the board.
     *
     * @return list of white pieces
     */
    public List<AbstractPiece> getLightPieces() {
        return lightPieces;
    }

    /**
     * Returns a list of all black pieces currently on the board.
     *
     * @return list of black pieces
     */
    public List<AbstractPiece> getDarkPieces() {
        return darkPieces;
    }

    /**
     * Moves a piece from one square to another, updating both the square state
     * and the internal piece position.
     *
     * <p>
     * This method performs no legality checks; move validation must be handled
     * externally before calling this method.
     * </p>
     *
     * @param from the source square containing the piece to move
     * @param to   the destination square where the piece will be placed
     */
    public void movePiece(Square from, Square to) {
        AbstractPiece piece = from.getCurrentAbstractPiece();
        if (piece == null)
            return;

        to.setCurrentAbstractPiece(piece);
        from.setCurrentAbstractPiece(null);

        piece.setCurrentSquare(to);
    }

    /**
     * Prints a textual representation of the chessboard to the console,
     * displaying piece initials on occupied squares and underscores on empty
     * squares. Ranks are printed from 8 to 1, and file labels (A–H) appear at
     * the bottom.
     */
    public void printBoard() {

        for (int row = 0; row < BOARD_LENGTH; row++) {

            // Print rank number
            System.out.print((BOARD_LENGTH - row) + " ");

            for (int col = 0; col < BOARD_LENGTH; col++) {

                Square sq = boardSquares[row][col];

                if (sq.isOccupied()) {
                    AbstractPiece p = sq.getCurrentAbstractPiece();
                    System.out.print(p.getName().charAt(0) + " ");
                } else {
                    System.out.print("_ ");
                }
            }

            System.out.println();
        }

        // Print file letters A–H
        System.out.print("  ");
        for (File file : File.values()) {
            System.out.print(file.name() + " ");
        }
        System.out.println();
    }
}
