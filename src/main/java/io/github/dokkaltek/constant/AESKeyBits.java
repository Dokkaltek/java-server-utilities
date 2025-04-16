package io.github.dokkaltek.constant;

/**
 * Key bit constant
 */
public enum AESKeyBits {
    KEY_128(128), KEY_192(192), KEY_256(256);

    private final int bits;

    AESKeyBits(int bits) {
        this.bits = bits;
    }

    /**
     * Returns the bits of the key.
     * @return The bits.
     */
    public int bits() {
        return bits;
    }
}
