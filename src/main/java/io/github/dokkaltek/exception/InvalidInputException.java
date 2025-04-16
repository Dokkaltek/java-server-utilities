package io.github.dokkaltek.exception;

import lombok.Getter;

import static io.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;

/**
 * Invalid input exception class.
 */
@Getter
public class InvalidInputException extends GenericException {
    /**
     * Default {@link InvalidInputException} constructor.
     * @param message The error message to show.
     */
    public InvalidInputException(String message) {
        super("Invalid input", message, INTERNAL_SERVER_ERROR.code());
    }
}
