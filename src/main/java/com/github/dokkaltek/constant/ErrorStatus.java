package com.github.dokkaltek.constant;

/**
 * Http error status codes.
 */
public enum ErrorStatus {
    /**
     * The <b>400 (Bad Request)</b> status code indicates that the server cannot or will not process the request due to an
     * apparent client error (e.g., malformed request syntax, size too large, invalid request message framing,
     * or deceptive request routing).
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.1">IETF reference</a>
     */
    BAD_REQUEST(400, "Bad Request"),

    /**
     * The <b>401 (Unauthorized)</b> status code indicates that the request has not been applied because it lacks valid
     * authentication credentials for the target resource.
     * <br>
     * Similar to 403 Forbidden, but specifically for use when authentication is required and has failed or has not
     * yet been provided.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.2">IETF reference</a>
     */
    UNAUTHORIZED(401, "Unauthorized"),

    /**
     * The <b>402 (Payment Required)</b> status code is reserved for future use.
     * <br>
     * The original intention was that this code might be used as part of some form of
     * digital cash or micropayment scheme, but that has not yet happened, and this code is not widely used.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.3">IETF reference</a>
     */
    PAYMENT_REQUIRED(402, "Payment Required"),

    /**
     * The <b>403 (Forbidden)</b> status code indicates that the request contained valid data and was understood
     * by the server, but the server is refusing action.
     * <br>
     * This may be due to the user not having the necessary permissions for a resource or needing an account
     * of some sort, or attempting a prohibited action (e.g. creating a duplicate record where only one is allowed).
     * <br>
     * This code is also typically used if the request provided authentication by answering the WWW-Authenticate
     * header field challenge, but the server did not accept that authentication. The request should not be repeated.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.4">IETF reference</a>
     */
    FORBIDDEN(403, "Forbidden"),

    /**
     * The <b>404 (Not Found)</b> status code indicates that the requested resource could not be found but may be
     * available in the future. Subsequent requests by the client are permissible.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.5">IETF reference</a>
     */
    NOT_FOUND(404, "Not Found"),

    /**
     * The <b>405 (Method Not Allowed)</b> status code indicates that a request method is not supported for the
     * requested resource.
     * <br>
     * For example, a GET request on a form that requires data to be presented via POST, or a PUT request on a
     * read-only resource.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.6">IETF reference</a>
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    /**
     * The <b>406 (Not Acceptable)</b> status code indicates that The requested resource is capable of generating only
     * content not acceptable according to the <code>Accept</code> headers sent in the request.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.7">IETF reference</a>
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),

    /**
     * The <b>407 (Proxy Authentication Required)</b> status code indicates that the client must first authenticate
     * itself with the proxy.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.8">IETF reference</a>
     */
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),

    /**
     * The <b>408 (Request Timeout)</b> status code indicates that the server timed out waiting for the request.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.9">IETF reference</a>
     */
    REQUEST_TIMEOUT(408, "Request Timeout"),

    /**
     * The <b>409 (Conflict)</b> status code indicates that the request could not be completed due to a conflict with
     * the current state of the resource, such as an edit conflict between multiple simultaneous updates.
     * <br><br>
     * The server <b>SHOULD</b> generate content that includes enough information for a user to recognize the source of
     * the conflict.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.10">IETF reference</a>
     */
    CONFLICT(409, "Conflict"),

    /**
     * The <b>410 (Gone)</b> status code indicates that the resource requested is no longer available and will not be
     * available again.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.11">IETF reference</a>
     */
    GONE(410, "Gone"),

    /**
     * The <b>411 (Length Required)</b> status code indicates that the server refuses to accept the request without a
     * defined <code>Content-Length</code>.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.12">IETF reference</a>
     */
    LENGTH_REQUIRED(411, "Length Required"),

    /**
     * The <b>412 (Precondition Failed)</b> status code indicates that one or more conditions given in the request
     * header fields evaluated to false when tested on the server.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.13">IETF reference</a>
     */
    PRECONDITION_FAILED(412, "Precondition Failed"),

    /**
     * The <b>413 (Payload Too Large)</b> status code indicates that the server is refusing to process the request
     * because the request entity is larger than the server is willing or able to process.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.14">IETF reference</a>
     */
    PAYLOAD_TOO_LARGE(413, "Payload Too Large"),

    /**
     * The <b>414 (URI Too Long)</b> status code indicates that the URI provided was too long for the server to process.
     * <br>
     * Often the result of too much data being encoded as a query-string of a GET request, in which case it should
     * be converted to a POST request.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.15">IETF reference</a>
     */
    URI_TOO_LONG(414, "URI Too Long"),

    /**
     * The <b>415 (Unsupported Media Type)</b> status code indicates that the request entity has a media type which
     * the server or resource does not support.
     * <br><br>
     * For example, the client uploads an image as image/svg+xml, but the server requires that images use a different
     * format.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.16">IETF reference</a>
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    /**
     * The <b>416 (Range Not Satisfiable)</b> status code indicates that the client has asked for a portion of a
     * file (byte serving), but the server cannot supply that portion.
     * <br><br>
     * For example, if the client asked for a part of the file that lies beyond the end of the file.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.17">IETF reference</a>
     */
    RANGE_NOT_SATISFIABLE(416, "Range Not Satisfiable"),

    /**
     * The <b>417 (Expectation Failed)</b> status code indicates that the expectation given in the request's
     * <code>Expect</code> header field could not be met by this server.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.18">IETF reference</a>
     */
    EXPECTATION_FAILED(417, "Expectation Failed"),

    /**
     * The <b>422 (Unprocessable Content)</b> status code indicates that the request was well-formed
     * (i.e., syntactically correct) but could not be processed
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.21">IETF reference</a>
     */
    UNPROCESSABLE_CONTENT(422, "Unprocessable Content"),

    /**
     * The <b>428 (Precondition Required)</b> status code indicates that the origin server requires the request to
     * be conditional.
     * <br>
     * Intended to prevent the 'lost update' problem, where a client GETs a resource's state, modifies it,
     * and PUTs it back to the server, when meanwhile a third party has modified the state on the server,
     * leading to a conflict.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc6585#section-3">IETF reference</a>
     */
    PRECONDITION_REQUIRED(428, "Precondition Required"),

    /**
     * The <b>429 (Too Many Requests)</b> status code indicates that the user has sent too many requests in a given
     * amount of time (Often used for rate limiting).
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc6585#section-4">IETF reference</a>
     */
    TOO_MANY_REQUESTS(429, "Too Many Requests"),

    /**
     * The <b>431 (Request Header Fields Too Large)</b> status code indicates that the server is unwilling to process
     * the request because its header fields are too large.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc6585#section-5">IETF reference</a>
     */
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large "),

    /**
     * The <b>451 (Unavailable For Legal Reasons)</b> status code indicates that the server is denying access to the
     * resource as a consequence of a legal demand.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc7725#section-3">IETF reference</a>
     */
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),

    /**
     *  The <b>500 (Internal Server Error)</b> status code indicates that a generic error happened.
     *  <br>
     *  Given when an unexpected condition was encountered and no more specific message is suitable.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.1">IETF reference</a>
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    /**
     * The <b>501 (Not Implemented)</b> status code indicates that the server either does not recognize the
     * request method, or it lacks the ability to fulfil the request.
     * <br>
     * Usually this implies future availability (e.g., a new feature of a web-service API).
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.2">IETF reference</a>
     */
    NOT_IMPLEMENTED(501, "Not Implemented"),

    /**
     * The <b>502 (Bad Gateway)</b> status code indicates that the server was acting as a gateway or proxy and
     * received an invalid response from the upstream server.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.3">IETF reference</a>
     */
    BAD_GATEWAY(502, "Bad Gateway"),

    /**
     * The <b>503 (Service Unavailable)</b> status code indicates that the server is currently unable to handle
     * the request due to a temporary overload or scheduled maintenance, which will likely be alleviated after
     * some delay.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.4">IETF reference</a>
     */
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),

    /**
     * The <b>504 (Gateway Timeout)</b> status code indicates that the server, while acting as a gateway or proxy,
     * did not receive a timely response from the upstream server that it needed to complete the request.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.5">IETF reference</a>
     */
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),

    /**
     * The <b>505 (HTTP Version Not Supported)</b> status code indicates that the server does not support the
     * HTTP protocol version that was used in the request.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.6">IETF reference</a>
     */
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),;

    private final int code;
    private final String reasonPhrase;

    ErrorStatus(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    /**
     * Gets an {@link ErrorStatus} from the status code.
     * @param code The status code to get the error status from.
     * @return The {@link ErrorStatus} if there was any, otherwise returns null.
     */
    public static ErrorStatus of(int code) {
        for (ErrorStatus status : ErrorStatus.values()) {
            if (status.code() == code) {
                return status;
            }
        }
        return null;
    }

    /**
     * Gets an {@link ErrorStatus} from the reason phrase.
     * @param reasonPhrase The status reason phrase to get the error status from.
     * @return The {@link ErrorStatus} if there was any, otherwise returns null.
     */
    public static ErrorStatus of(String reasonPhrase) {
        for (ErrorStatus status : ErrorStatus.values()) {
            if (status.reasonPhrase().equalsIgnoreCase(reasonPhrase)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Gets the status code.
     * @return The value of the status code.
     */
    public int code() {
        return this.code;
    }

    /**
     * Gets the reason phrase.
     * @return The value of the reason phrase.
     */
    public String reasonPhrase() {
        return this.reasonPhrase;
    }
}
