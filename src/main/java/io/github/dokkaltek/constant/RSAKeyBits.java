package io.github.dokkaltek.constant;

/**
 * Key bit constant
 */
public enum RSAKeyBits {
    KEY_2048(2048), KEY_3072(3072), KEY_4096(4096), KEY_7680(7680), KEY_15360(15360);

    private final int bits;

    RSAKeyBits(int bits) {
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
