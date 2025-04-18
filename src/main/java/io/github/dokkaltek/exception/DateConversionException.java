package io.github.dokkaltek.exception;

import lombok.Getter;

import static io.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;

/**
 * Date conversion exception class.
 */
@Getter
public class DateConversionException extends GenericException {
    /**
     * Default {@link DateConversionException} constructor.
     * @param ex The underlying exception thrown.
     */
    public DateConversionException(Throwable ex) {
        super("Invalid date format", ex.getMessage(), INTERNAL_SERVER_ERROR.code());
    }
}
