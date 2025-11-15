package com.chess.common;

/**
 * Factory class for generating new board locations relative to an existing
 * position. This utility is primarily used by chess pieces when calculating
 * candidate moves based on directional offsets.
 *
 * <p>
 * The method applies a file (column) offset and a rank (row) offset to the
 * supplied current {@link Location}. Bounds checking is not performed here;
 * callers must validate that the resulting location lies within the valid
 * chessboard range (A–H, 1–8).
 * </p>
 */
public class LocationFactory {

    private static final File[] files = File.values();

    /**
     * Creates a new location based on file and rank offsets from an existing
     * location.
     *
     * @param current    the starting location
     * @param fileOffset offset to apply to the file (negative or positive)
     * @param rankOffset offset to apply to the rank (negative or positive)
     * @return a new Location representing the offset position
     * @throws IllegalArgumentException if the computed file index is outside A–H
     */
    public static Location build(Location current, int fileOffset, int rankOffset) {

        int newFileIndex = current.getFile().ordinal() + fileOffset;
        int newRank = current.getRank() + rankOffset;

        return new Location(files[newFileIndex], newRank);
    }
}
