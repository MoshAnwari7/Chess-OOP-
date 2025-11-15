package com.chess.Main;

import java.util.Scanner;

import com.chess.Board.Board;
import com.chess.piece.Movable;
import com.chess.squares.Square;
import com.chess.common.File;
import com.chess.common.Location;

/**
 * The Game class serves as the main entry point for running the console-based
 * chess application. It initializes the board, prints the initial game state,
 * and processes user input commands to move pieces on the board.
 *
 * <p>
 * Users enter moves in standard algebraic grid format (e.g., "E2->E4"). The
 * class parses the input, identifies the corresponding squares, and delegates
 * the move execution to the {@link Board} class.
 * </p>
 */
public class Game {

    /**
     * Main method that starts the chess game. It initializes the board, displays
     * it, and repeatedly reads user input to perform piece movements.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();

        Scanner scan = new Scanner(System.in);

        while (true) {
            // Move format: E2->E4
            String line = scan.nextLine();
            String[] fromTo = line.split("->");

            File fromFile = File.valueOf(String.valueOf(Character.toUpperCase(fromTo[0].charAt(0))));
            int fromRank = Integer.parseInt(String.valueOf(fromTo[0].charAt(1)));

            File toFile = File.valueOf(String.valueOf(Character.toUpperCase(fromTo[1].charAt(0))));
            int toRank = Integer.parseInt(String.valueOf(fromTo[1].charAt(1)));

            Square fromSquare = board.getLocationSquareMap().get(new Location(fromFile, fromRank));
            Square toSquare = board.getLocationSquareMap().get(new Location(toFile, toRank));

            // Delegate the move to the board
            board.movePiece(fromSquare, toSquare);
            board.printBoard();
        }
    }

    /**
     * Utility method for debugging pieces and displaying their valid moves.
     *
     * @param piece a piece implementing the Movable interface
     */
    public static void printPiece(Movable piece) {
        piece.getValidMoves(null);
    }
}
