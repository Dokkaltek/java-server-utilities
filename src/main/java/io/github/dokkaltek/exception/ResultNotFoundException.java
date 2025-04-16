package io.github.dokkaltek.exception;

import lombok.Getter;

import static io.github.dokkaltek.constant.ErrorStatus.NOT_FOUND;

/**
 * Results not found exception class, useful for responses where no results were obtained for a given request.
 */
@Getter
public class ResultNotFoundException extends GenericException {
    private static final String DEFAULT_ERROR = "No results found";
    /**
     * Default {@link ResultNotFoundException} constructor.
     */
    public ResultNotFoundException() {
        super(DEFAULT_ERROR, "No results found for the given query.", NOT_FOUND.code());
    }

    /**
     * Default {@link ResultNotFoundException} constructor.
     * @param message The error message.
     */
    public ResultNotFoundException(String message) {
        super(DEFAULT_ERROR, message, NOT_FOUND.code());
    }
}
