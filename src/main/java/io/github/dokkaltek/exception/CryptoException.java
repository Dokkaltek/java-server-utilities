package io.github.dokkaltek.exception;

import lombok.Getter;

import static io.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;

/**
 * Cryptography exception class.
 */
@Getter
public class CryptoException extends GenericException {
    private static final String DEFAULT_ERROR_CODE = "Cryptography error";
    /**
     * Default {@link CryptoException} constructor.
     * @param message The error message to show.
     */
    public CryptoException(String message) {
        super(DEFAULT_ERROR_CODE, message, INTERNAL_SERVER_ERROR.code());
    }

    /**
     * Default {@link CryptoException} constructor.
     * @param errorCode The error code.
     * @param ex The error thrown.
     */
    public CryptoException(String errorCode, Throwable ex) {
        super(errorCode, ex.getMessage(), INTERNAL_SERVER_ERROR.code());
    }
}
