package com.chess.common;

import java.util.Objects;

/**
 * Represents a specific coordinate on the chessboard, defined by a
 * {@link File} (column) and a rank (row).
 *
 * <p>
 * Locations are immutable and are used as keys throughout the system for
 * mapping board positions to squares and pieces. Equality and hashing are
 * fully overridden, making this class safe and reliable for use in
 * {@link java.util.Map} and other collections.
 * </p>
 *
 * <p>
 * Example: A Location constructed as new Location(File.E, 4) represents the
 * position "E4" on the chessboard.
 * </p>
 */
public class Location {

    /** The file (column) of this location: A–H. */
    private final File file;

    /** The rank (row) of this location: 1–8. */
    private final Integer rank;

    /**
     * Constructs a new Location with the specified file and rank.
     *
     * @param file the file (A–H)
     * @param rank the rank (1–8)
     */
    public Location(File file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    /**
     * Returns the file component of this location.
     *
     * @return the file (A–H)
     */
    public File getFile() {
        return file;
    }

    /**
     * Returns the rank component of this location.
     *
     * @return the rank (1–8)
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * Compares this location to another object for equality. Two locations are
     * equal if they share the same file and rank.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Location))
            return false;

        Location location = (Location) o;
        return file == location.file && Objects.equals(rank, location.rank);
    }

    /**
     * Computes a hash code using both file and rank. Ensures consistent hashing
     * behavior for use in hash-based collections.
     */
    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

    /**
     * Returns a string representation of this location, useful for debugging.
     */
    @Override
    public String toString() {
        return "Location{" +
                "file=" + file +
                ", rank=" + rank +
                '}';
    }
}
