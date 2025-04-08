package com.github.dokkaltek.exception;

import lombok.Getter;

import static com.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;

/**
 * Invalid email exception class.
 */
@Getter
public class InvalidEmailException extends GenericException {
    /**
     * Default {@link InvalidEmailException} constructor.
     * @param message The error message to show.
     */
    public InvalidEmailException(String message) {
        super("Invalid Email", message, INTERNAL_SERVER_ERROR.code());
    }
}
