package io.github.dokkaltek.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Http header commonly used authentication prefixes.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthHeaderPrefixes {
    /**
     * Basic authentication prefix.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Authentication#basic">Mozilla docs</a>
     */
    public static final String BASIC = "Basic ";

    /**
     * Bearer authentication prefix.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Authentication#bearer">Mozilla docs</a>
     */
    public static final String BEARER = "Bearer ";

    /**
     * Digest authentication prefix.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Authentication#digest">Mozilla docs</a>
     */
    public static final String DIGEST = "Digest ";

    /**
     * Mutual authentication prefix.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Authentication#mutual">Mozilla docs</a>
     */
    public static final String MUTUAL = "Mutual ";

    /**
     * Negotiate / NTLM authentication prefix.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Authentication#negotiate">Mozilla docs</a>
     */
    public static final String NEGOTIATE = "Negotiate ";


    /**
     * Mutual authentication prefix.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Authentication#vapid">Mozilla docs</a>
     */
    public static final String VAPID = "vapid ";

    /**
     * AWS4-HMAC-SHA256 authentication prefix.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Authentication#aws4-hmac-sha256">Mozilla docs</a>
     */
    public static final String AWS4_HMAC_SHA256 = "AWS4-HMAC-SHA256 ";
}
