package io.github.dokkaltek.exception;

import lombok.Getter;

import static io.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;

/**
 * Invalid JSON exception class.
 */
@Getter
public class JSONException extends GenericException {
    /**
     * Default {@link JSONException} constructor.
     * @param ex The underlying exception thrown.
     */
    public JSONException(Throwable ex) {
        super("Invalid JSON", ex.getMessage(), INTERNAL_SERVER_ERROR.code());
    }
}
