package io.github.dokkaltek.exception;

import lombok.Getter;

import static io.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;

/**
 * Invalid ID exception class.
 */
@Getter
public class InvalidIdException extends GenericException {
    /**
     * Default {@link InvalidIdException} constructor.
     * @param message The error message to show.
     */
    public InvalidIdException(String message) {
        super("Invalid ID", message, INTERNAL_SERVER_ERROR.code());
    }
}
