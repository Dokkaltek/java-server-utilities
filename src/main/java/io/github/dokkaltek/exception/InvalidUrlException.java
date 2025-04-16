package io.github.dokkaltek.exception;

import lombok.Getter;

import static io.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;

/**
 * Invalid URL exception class.
 */
@Getter
public class InvalidUrlException extends GenericException {
    /**
     * Default {@link InvalidUrlException} constructor.
     * @param ex The underlying exception thrown.
     */
    public InvalidUrlException(Throwable ex) {
        super("Invalid URL", ex.getMessage(), INTERNAL_SERVER_ERROR.code());
    }
}
