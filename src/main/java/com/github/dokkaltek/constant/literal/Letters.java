package com.github.dokkaltek.constant.literal;

import java.util.Locale;
import java.util.Objects;

/**
 * Enum for literal letter characters.
 */
public enum Letters {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I,
    J,
    K,
    L,
    M,
    N,
    O,
    P,
    Q,
    R,
    S,
    T,
    U,
    V,
    W,
    X,
    Y,
    Z;

    /**
     * The {@link String}  value of the letter.
     * @return The {@link String} value of the letter.
     */
    public String value() {
        return String.valueOf(this);
    }

    /**
     * The lowercase {@link String} value of the letter.
     * @return The lowercase {@link String} value of the letter.
     */
    public String lowercase() {
        return String.valueOf(this).toLowerCase(Locale.getDefault());
    }

    /**
     * Gets the <b>char</b> value of the letter.
     * @return The <b>char</b> value of the letter.
     */
    public char asChar() {
        return this.value().charAt(0);
    }

    /**
     * The lowercase <b>char</b> value of the letter.
     * @return The lowercase <b>char</b> value of the letter.
     */
    public char asLowerChar() {
        return String.valueOf(this).toLowerCase(Locale.getDefault()).charAt(0);
    }

    /**
     * Gets a {@link Letters} from the asChar.
     * @param character The asChar to get.
     * @return The {@link Letters} or null if not found.
     */
    public Letters of(char character) {
        String charToSearch = String.valueOf(character).toUpperCase(Locale.getDefault());
        for (Letters letter : Letters.values()) {
            if (Objects.equals(letter.value(), charToSearch)) {
                return letter;
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
        return this.value();
    }
}
