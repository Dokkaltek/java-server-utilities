package com.github.dokkaltek.constant.literal;

/**
 * Enum for literal number characters.
 */
public enum Numbers {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    private final int number;

    /**
     * Default constructor.
     * @param number The int value of the symbol.
     */
    Numbers(int number) {
        this.number = number;
    }

    /**
     * Accessor to get the <b>int</b> value of the number.
     * @return The <b>int</b> value.
     */
    public int value() {
        return number;
    }

    /**
     * Accessor to get the <b>long</b> value of the number.
     * @return The <b>long</b> value.
     */
    public long asLong() {
        return number;
    }

    /**
     * Accessor to get the <b>float</b> value of the number.
     * @return The <b>float</b> value.
     */
    public float asFloat() {
        return number;
    }

    /**
     * Accessor to get the <b>double</b> value of the number.
     * @return The <b>double</b> value.
     */
    public double asDouble() {
        return number;
    }

    /**
     * Accessor to get the <b>double</b> value of the number.
     * @return The <b>double</b> value.
     */
    public short asShort() {
        return (short) number;
    }

    /**
     * Accessor to get the char value of the number.
     * @return The char value.
     */
    public char asChar() {
        return String.valueOf(number).charAt(0);
    }

    /**
     * Gets a {@link Numbers} from the searched number.
     * @param numToSearch The value to get.
     * @return The {@link Numbers} or null if not found.
     */
    public static Numbers of(int numToSearch) {
        for (Numbers num : Numbers.values()) {
            if (num.number == numToSearch) {
                return num;
            }
        }
        return null;
    }

    /**
     * Converts the object into a {@link String} representation.
     * @return The {@link String} representation of the object.
     */
    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
