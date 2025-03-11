package com.github.dokkaltek.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Http header key constants.
 */
@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpHeaderKeys {
    /**
     * Contains the credentials to authenticate a user-agent with a server.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/WWW-Authenticate">Mozilla docs</a>
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * Defines the authentication method that should be used to access a resource.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Authorization">Mozilla docs</a>
     */
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

    /**
     * Defines the authentication method that should be used to access a resource behind a proxy server.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Proxy-Authenticate">Mozilla docs</a>
     */
    public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";

    /**
     * Contains the credentials to authenticate a user agent with a proxy server.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Proxy-Authorization">Mozilla docs</a>
     */
    public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";

    /**
     * The time, in seconds, that the object has been in a proxy cache.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Age">Mozilla docs</a>
     */
    public static final String AGE = "Age";

    /**
     * Directives for caching mechanisms in both requests and responses.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control">Mozilla docs</a>
     */
    public static final String CACHE_CONTROL = "Cache-Control";

    /**
     * Clears browsing data (e.g. cookies, storage, cache) associated with the requesting website.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Clear-Site-Data">Mozilla docs</a>
     */
    public static final String CLEAR_SITE_DATA = "Clear-Site-Data";

    /**
     * The date/time after which the response is considered stale.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expires">Mozilla docs</a>
     */
    public static final String EXPIRES = "Expires";

    /**
     * Specifies a set of rules that define how a URL's query parameters will affect cache matching.
     * <br>
     * These rules dictate whether the same URL with different URL parameters should be saved as separate
     * browser cache entries.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/No-Vary-Search">Mozilla docs</a>
     */
    public static final String NO_VARY_SEARCH = "No-Vary-Search";

    /**
     * The last modification date of the resource, used to compare several versions of the same resource.
     * <br>
     * It is less accurate than <code>ETag</code>, but easier to calculate in some environments. <br>
     * Conditional requests using <code>If-Modified-Since</code> and <code>If-Unmodified-Since</code> use this
     * value to change the behavior of the request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Last-Modified">Mozilla docs</a>
     */
    public static final String LAST_MODIFIED = "Last-Modified";

    /**
     * A unique string identifying the version of the resource.
     * <br>
     * Conditional requests using <code>If-Match</code> and <code>If-None-Match</code> use this value to
     * change the behavior of the request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/ETag">Mozilla docs</a>
     */
    public static final String ETAG = "ETag";

    /**
     * Makes the request conditional, and applies the method only if the stored resource matches one of the given ETags.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/If-Match">Mozilla docs</a>
     */
    public static final String IF_MATCH = "If-Match";

    /**
     * Makes the request conditional, and applies the method only if the stored resource doesn't match any of the
     * given ETags. This is used to update caches (for safe requests), or to prevent uploading a new resource when
     * one already exists.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/If-None-Match">Mozilla docs</a>
     */
    public static final String IF_NONE_MATCH = "If-None-Match";

    /**
     * Makes the request conditional, and expects the resource to be transmitted only if it has been modified after
     * the given date. This is used to transmit data only when the cache is out of date.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/If-Modified-Since">Mozilla docs</a>
     */
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";

    /**
     * Makes the request conditional, and expects the resource to be transmitted only if it has not been modified
     * after the given date. This ensures the coherence of a new fragment of a specific range with previous ones,
     * or to implement an optimistic concurrency control system when modifying existing documents.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/If-Unmodified-Since">Mozilla docs</a>
     */
    public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";

    /**
     * Determines how to match request headers to decide whether a cached response can be used rather than requesting
     * a fresh one from the origin server.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Vary">Mozilla docs</a>
     */
    public static final String VARY = "Vary";

    /**
     * Controls whether the network connection stays open after the current transaction finishes.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Connection">Mozilla docs</a>
     */
    public static final String CONNECTION = "Connection";

    /**
     * Controls how long a persistent connection should stay open.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Keep-Alive">Mozilla docs</a>
     */
    public static final String KEEP_ALIVE = "Keep-Alive";

    /**
     * Informs the server about the types of data that can be sent back.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept">Mozilla docs</a>
     */
    public static final String ACCEPT = "Accept";

    /**
     * The encoding algorithm, usually a compression algorithm, that can be used on the resource sent back.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Encoding">Mozilla docs</a>
     */
    public static final String ACCEPT_ENCODING = "Accept-Encoding";

    /**
     * Informs the server about the human language the server is expected to send back.
     * <br>
     * This is a hint and is not necessarily under the full control of the user: the server should always pay
     * attention not to override an explicit user choice (like selecting a language from a dropdown).
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Language">Mozilla docs</a>
     */
    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    /**
     * A <i>request content negotiation</i> response header that advertises which media type the server is able
     * to understand in a <code>PATCH</code> request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Patch">Mozilla docs</a>
     */
    public static final String ACCEPT_PATCH = "Accept-Patch";

    /**
     * A <i>request content negotiation</i> response header that advertises which media type the server is able
     * to understand in a <code>POST</code> request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Post">Mozilla docs</a>
     */
    public static final String ACCEPT_POST = "Accept-Post";

    /**
     * Indicates expectations that need to be fulfilled by the server to properly handle the request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expect">Mozilla docs</a>
     */
    public static final String EXPECT = "Expect";

    /**
     * When using <code>TRACE</code>, indicates the maximum number of hops the request can do before being reflected
     * to the sender.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Max-Forwards">Mozilla docs</a>
     */
    public static final String MAX_FORWARDS = "Max-Forwards";

    /**
     * Contains stored <b>HTTP cookies</b> previously sent by the server with the <code>Set-Cookie</code> header.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie">Mozilla docs</a>
     */
    public static final String COOKIE = "Cookie";

    /**
     * Send cookies from the server to the user-agent.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Set-Cookie">Mozilla docs</a>
     */
    public static final String SET_COOKIE = "Set-Cookie";

    /**
     * CORS header that indicates whether the response to the request can be exposed when the credentials flag is true.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Credentials">
     *     Mozilla docs</a>
     */
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

    /**
     * CORS header used in response to a preflight request to indicate which HTTP headers can be used when making
     * the actual request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Headers">
     *      Mozilla docs</a>
     */
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    /**
     * CORS header that specifies the methods allowed when accessing the resource in response to a preflight request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Methods">
     *      Mozilla docs</a>
     */
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    /**
     * CORS header that indicates whether the response can be shared.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Allow-Origin">
     *      Mozilla docs</a>
     */
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

    /**
     * CORS header that indicates which headers can be exposed as part of the response by listing their names.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers">
     *      Mozilla docs</a>
     */
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    /**
     * CORS header that indicates how long the results of a preflight request can be cached.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age">
     *      Mozilla docs</a>
     */
    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

    /**
     * CORS header used when issuing a preflight request to let the server know which HTTP headers will be used when
     * the actual request is made.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age">
     *      Mozilla docs</a>
     */
    public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";

    /**
     * CORS header used when issuing a preflight request to let the server know which HTTP method will be used when
     * the actual request is made.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Request-Method">
     *      Mozilla docs</a>
     */
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";

    /**
     * CORS header that indicates where a fetch originates from.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Origin">Mozilla docs</a>
     */
    public static final String ORIGIN = "Origin";

    /**
     * CORS header that specifies origins that are allowed to see values of attributes retrieved via features of the
     * Resource Timing API, which would otherwise be reported as zero due to cross-origin restrictions.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Timing-Allow-Origin">Mozilla docs</a>
     */
    public static final String TIMING_ALLOW_ORIGIN = "Timing-Allow-Origin";

    /**
     * Indicates if the resource transmitted should be displayed inline (default behavior without the header),
     * or if it should be handled like a download and the browser should present a "Save As" dialog.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition">Mozilla docs</a>
     */
    public static final String CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * Provides a digest of the stream of octets framed in an HTTP message (the message content) dependent on
     * <code>Content-Encoding</code> and <code>Content-Range</code>.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Digest">Mozilla docs</a>
     */
    public static final String CONTENT_DIGEST = "Content-Digest";

    /**
     * Provides a digest of the selected representation of the target resource before transmission.
     * Unlike the <code>Content-Digest</code>, the digest does not consider <code>Content-Encoding</code> or
     * <code>Content-Range</code>.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Repr-Digest">Mozilla docs</a>
     */
    public static final String REPR_DIGEST = "Repr-Digest";

    /**
     * States the wish for a <code>Content-Digest</code> header. It is the <code>Content-</code> analogue of
     * <code>Want-Repr-Digest</code>.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Want-Content-Digest">Mozilla docs</a>
     */
    public static final String WANT_CONTENT_DIGEST = "Want-Content-Digest";

    /**
     * States the wish for a <code>Repr-Digest</code> header. It is the <code>Repr-</code> analogue of
     * <code>Want-Content-Digest</code>.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Want-Repr-Digest">Mozilla docs</a>
     */
    public static final String WANT_REPR_DIGEST = "Want-Repr-Digest";

    /**
     * The size of the resource, in decimal number of bytes.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Length">Mozilla docs</a>
     */
    public static final String CONTENT_LENGTH = "Content-Length";

    /**
     * Indicates the media type of the resource.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type">Mozilla docs</a>
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * Used to specify the compression algorithm.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Encoding">Mozilla docs</a>
     */
    public static final String CONTENT_ENCODING = "Content-Encoding";

    /**
     * Describes the human language(s) intended for the audience, so that it allows a user to differentiate according
     * to the users' own preferred language.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Language">Mozilla docs</a>
     */
    public static final String CONTENT_LANGUAGE = "Content-Language";

    /**
     * Indicates an alternate location for the returned data.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Location">Mozilla docs</a>
     */
    public static final String CONTENT_LOCATION = "Content-Location";

    /**
     * Contains information from the client-facing side of proxy servers that is altered or lost when a proxy is
     * involved in the path of the request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Forwarded">Mozilla docs</a>
     */
    public static final String FORWARDED = "Forwarded";

    /**
     * Added by proxies, both forward and reverse proxies, and can appear in the request headers and the response
     * headers.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Via">Mozilla docs</a>
     */
    public static final String VIA = "Via";

    /**
     * Indicates if the server supports range requests, and if so in which unit the range can be expressed.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Ranges">Mozilla docs</a>
     */
    public static final String ACCEPT_RANGES = "Accept-Ranges";

    /**
     * Indicates the part of a document that the server should return.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Range">Mozilla docs</a>
     */
    public static final String RANGE = "Range";

    /**
     * Creates a conditional range request that is only fulfilled if the given etag or date matches the remote resource.
     * <br>
     * Used to prevent downloading two ranges from incompatible version of the resource.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/If-Range">Mozilla docs</a>
     */
    public static final String IF_RANGE = "If-Range";

    /**
     * Indicates where in a full body message a partial message belongs.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Range">Mozilla docs</a>
     */
    public static final String CONTENT_RANGE = "Content-Range";

    /**
     * Indicates the URL to redirect a page to.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Location">Mozilla docs</a>
     */
    public static final String LOCATION = "Location";

    /**
     * Directs the browser to reload the page or redirect to another. Takes the same value as the meta element with
     * <code>http-equiv="refresh"</code>.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Refresh">Mozilla docs</a>
     */
    public static final String REFRESH = "Refresh";

    /**
     * Contains an Internet email address for a human user who controls the requesting user agent.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/From">Mozilla docs</a>
     */
    public static final String FROM = "From";

    /**
     * Specifies the domain name of the server (for virtual hosting), and (optionally) the TCP port number on which
     * the server is listening.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Host">Mozilla docs</a>
     */
    public static final String HOST = "Host";

    /**
     * The address of the previous web page from which a link to the currently requested page was followed.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Referer">Mozilla docs</a>
     */
    public static final String REFERER = "Referer";

    /**
     * Governs which referrer information sent in the Referer header should be included with requests made.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Referrer-Policy">Mozilla docs</a>
     */
    public static final String REFERER_POLICY = "Referrer-Policy";

    /**
     * Contains a characteristic string that allows the network protocol peers to identify the application type,
     * operating system, software vendor or software version of the requesting software user agent.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/User-Agent">Mozilla docs</a>
     */
    public static final String USER_AGENT = "User-Agent";

    /**
     * Lists the set of HTTP request methods supported by a resource.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Allow">Mozilla docs</a>
     */
    public static final String ALLOW = "Allow";

    /**
     * Contains information about the software used by the origin server to handle the request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Server">Mozilla docs</a>
     */
    public static final String SERVER = "Server";

    /**
     * (COEP) Allows a server to declare an embedder policy for a given document.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cross-Origin-Embedder-Policy">
     *     Mozilla docs</a>
     */
    public static final String CROSS_ORIGIN_EMBEDDER_POLICY = "Cross-Origin-Embedder-Policy";

    /**
     * (COOP) Prevents other domains from opening/controlling a window.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cross-Origin-Opener-Policy">
     *     Mozilla docs</a>
     */
    public static final String CROSS_ORIGIN_OPENER_POLICY = "Cross-Origin-Opener-Policy";

    /**
     * (<a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Cross-Origin_Resource_Policy">CORP</a>)
     * Prevents other domains from reading the response of the resources to which this header is applied.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cross-Origin-Resource-Policy">
     *     Mozilla docs</a>
     */
    public static final String CROSS_ORIGIN_RESOURCE_POLICY = "Cross-Origin-Resource-Policy";

    /**
     * (<a href="https://developer.mozilla.org/en-US/docs/Glossary/CSP">CSP</a>) Controls resources the user agent is
     * allowed to load for a given page.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy">
     *     Mozilla docs</a>
     */
    public static final String CONTENT_SECURITY_POLICY = "Content-Security-Policy";

    /**
     * Allows web developers to experiment with policies by monitoring, but not enforcing, their effects.
     * <br>
     * These violation reports consist of JSON documents sent via an HTTP POST request to the specified URI.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy-Report-Only">
     *     Mozilla docs</a>
     */
    public static final String CONTENT_SECURITY_POLICY_REPORT_ONLY = "Content-Security-Policy-Report-Only";

    /**
     * Lets sites opt in to reporting and enforcement of Certificate Transparency to detect use of mis-issued
     * certificates for that site.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expect-CT">Mozilla docs</a>
     */
    public static final String EXPECT_CT = "Expect-CT";

    /**
     * Provides a mechanism to allow and deny the use of browser features in a website's own frame,
     * and in <code>iframe</code>s that it embeds.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Permissions-Policy">Mozilla docs</a>
     */
    public static final String PERMISSIONS_POLICY = "Permissions-Policy";

    /**
     * Response header that allows website owners to specify one or more endpoints used to receive errors such as
     * CSP violation reports, <code>Cross-Origin-Opener-Policy</code> reports, or other generic violations.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Reporting-Endpoints">Mozilla docs</a>
     */
    public static final String REPORTING_ENDPOINTS = "Reporting-Endpoints";

    /**
     * (<a href="https://developer.mozilla.org/en-US/docs/Glossary/HSTS">HSTS</a>)
     * Force communication using HTTPS instead of HTTP.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Strict-Transport-Security">
     *     Mozilla docs</a>
     */
    public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";

    /**
     * Sends a signal to the server expressing the client's preference for an encrypted and authenticated response,
     * and that it can successfully handle the <code>upgrade-insecure-requests</code> directive.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Upgrade-Insecure-Requests">
     *     Mozilla docs</a>
     */
    public static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";

    /**
     * Disables MIME sniffing and forces browser to use the type given in <code>Content-Type</code>.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options">
     *     Mozilla docs</a>
     */
    public static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";

    /**
     * (XFO) Indicates whether a browser should be allowed to render a page in a <code>frame</code>,
     * <code>iframe</code>, <code>embed</code> or <code>object</code>.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options">
     *     Mozilla docs</a>
     */
    public static final String X_FRAME_OPTIONS = "X-Frame-Options";

    /**
     * A cross-domain policy file may grant clients, such as Adobe Acrobat or Apache Flex (among others),
     * permission to handle data across domains that would otherwise be restricted due to the Same-Origin Policy.
     * <br>
     * The <code>X-Permitted-Cross-Domain-Policies</code> header overrides such policy files so that clients still
     * block unwanted requests.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Permitted-Cross-Domain-Policies">
     *     Mozilla docs</a>
     */
    public static final String X_PERMITTED_CROSS_DOMAIN_POLICIES = "X-Permitted-Cross-Domain-Policies";

    /**
     * May be set by hosting environments or other frameworks and contains information about them while not providing
     * any usefulness to the application or its visitors.
     * <br>
     * Unset this header to avoid exposing potential vulnerabilities.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Powered-By">Mozilla docs</a>
     */
    public static final String X_POWERED_BY = "X-Powered-By";

    /**
     * Enables cross-site scripting filtering.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection">Mozilla docs</a>
     */
    public static final String X_XSS_PROTECTION = "X-XSS-Protection";

    /**
     * Indicates the relationship between a request initiator's origin and its target's origin. <br>
     * It is a Structured Header whose value is a token with possible values <code>cross-site</code>,
     * <code>same-origin</code>, <code>same-site</code>, and <code>none</code>.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-Fetch-Site">Mozilla docs</a>
     */
    public static final String SEC_FETCH_SITE = "Sec-Fetch-Site";

    /**
     * Indicates the request's mode to a server. It is a Structured Header whose value is a token with
     * possible values <code>cors</code>, <code>navigate</code>, <code>no-cors</code>, <code>same-origin</code>,
     * and <code>websocket</code>.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-Fetch-Mode">Mozilla docs</a>
     */
    public static final String SEC_FETCH_MODE = "Sec-Fetch-Mode";

    /**
     * Indicates whether a navigation request was triggered by user activation. <br>
     * It is a Structured Header whose value is a boolean so possible values are <code>?0</code> for false and
     * <code>?1</code> for true.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-Fetch-User">Mozilla docs</a>
     */
    public static final String SEC_FETCH_USER = "Sec-Fetch-User";

    /**
     * Indicates the request's destination. <br>
     * It is a Structured Header whose value is a token with possible values <code>audio</code>,
     * <code>audioworklet</code>, <code>document</code>, <code>embed</code>, <code>empty</code>, <code>font</code>,
     * <code>image</code>, <code>manifest</code>, <code>object</code>, <code>paintworklet</code>, <code>report</code>,
     * <code>script</code>, <code>serviceworker</code>, <code>sharedworker</code>, <code>style</code>,
     * <code>track</code>, <code>video</code>, <code>worker</code>, and <code>xslt</code>.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-Fetch-Dest">Mozilla docs</a>
     */
    public static final String SEC_FETCH_DEST = "Sec-Fetch-Dest";

    /**
     * Indicates the purpose of the request, when the purpose is something other than immediate use by the user-agent.
     * The header currently has one possible value, <code>prefetch</code>, which indicates that the resource is being
     * fetched preemptively for a possible future navigation.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-Purpose">Mozilla docs</a>
     */
    public static final String SEC_PURPOSE = "Sec-Purpose";

    /**
     * A request header sent in preemptive request to <code>fetch()</code> a resource during service worker boot. <br>
     * The value, which is set with <code>NavigationPreloadManager.setHeaderValue()</code>, can be used to inform a
     * server that a different resource should be returned than in a normal <code>fetch()</code> operation.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Service-Worker-Navigation-Preload">
     *     Mozilla docs</a>
     */
    public static final String SERVICE_WORKER_NAVIGATION_PRELOAD = "Service-Worker-Navigation-Preload";

    /**
     * Specifies the form of encoding used to safely transfer the resource to the user.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Transfer-Encoding">Mozilla docs</a>
     */
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";

    /**
     * Specifies the transfer encodings the user agent is willing to accept.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/TE">Mozilla docs</a>
     */
    public static final String TE = "TE";

    /**
     * Allows the sender to include additional fields at the end of chunked message.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Trailer">Mozilla docs</a>
     */
    public static final String TRAILER = "Trailer";

    /**
     * Response header that indicates that the server is willing to upgrade to a WebSocket connection.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-WebSocket-Accept">Mozilla docs</a>
     */
    public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";

    /**
     * In requests, this header indicates the WebSocket extensions supported by the client in preferred order. <br>
     * In responses, it indicates the extension selected by the server from the client's preferences.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-WebSocket-Extensions">
     *     Mozilla docs</a>
     */
    public static final String SEC_WEBSOCKET_EXTENSIONS = "Sec-WebSocket-Extensions";

    /**
     * Request header containing a key that verifies that the client explicitly intends to open a WebSocket.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-WebSocket-Key">Mozilla docs</a>
     */
    public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";

    /**
     * In requests, this header indicates the sub-protocols supported by the client in preferred order. <br>
     * In responses, it indicates the sub-protocol selected by the server from the client's preferences.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-WebSocket-Protocol">Mozilla docs</a>
     */
    public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";

    /**
     * In requests, this header indicates the version of the WebSocket protocol used by the client. In responses,
     * it is sent only if the requested protocol version is not supported by the server, and lists the versions
     * that the server supports.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-WebSocket-Version">Mozilla docs</a>
     */
    public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";

    /**
     * Used to list alternate ways to reach this service.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Alt-Svc">Mozilla docs</a>
     */
    public static final String ALT_SVC = "Alt-Svc";

    /**
     * Used to identify the alternative service in use.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Alt-Used">Mozilla docs</a>
     */
    public static final String ALT_USED = "Alt-Used";

    /**
     * Contains the date and time at which the message was originated.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Date">Mozilla docs</a>
     */
    public static final String DATE = "Date";

    /**
     * This entity-header field provides a means for serializing one or more links in HTTP headers. <br>
     * It is semantically equivalent to the HTML <code>link</code> element.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Link">Mozilla docs</a>
     */
    public static final String LINK = "Link";

    /**
     * Indicates how long the user agent should wait before making a follow-up request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Retry-After">Mozilla docs</a>
     */
    public static final String RETRY_AFTER = "Retry-After";

    /**
     * Communicates one or more metrics and descriptions for the given request-response cycle.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Server-Timing">Mozilla docs</a>
     */
    public static final String SERVER_TIMING = "Server-Timing";

    /**
     * This HTTP/1.1 (only) header can be used to upgrade an already established client/server connection to a
     * different protocol (over the same transport protocol). For example, it can be used by a client to upgrade a
     * connection from HTTP 1.1 to HTTP 2.0, or an HTTP or HTTPS connection into a WebSocket.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Upgrade">Mozilla docs</a>
     */
    public static final String UPGRADE = "Upgrade";

    /**
     * Provides a hint from about the priority of a particular resource request on a particular connection. <br>
     * The value can be sent in a request to indicate the client priority, or in a response if the server chooses
     * to re-prioritize the request.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Priority">Mozilla docs</a>
     */
    public static final String PRIORITY = "Priority";

    /**
     * Used to indicate that the response corresponding to the current request is eligible to take part in
     * attribution reporting, by registering either an attribution source or trigger.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Attribution-Reporting-Eligible">
     *     Mozilla docs</a>
     */
    public static final String ATTRIBUTION_REPORTING_ELIGIBLE = "Attribution-Reporting-Eligible";

    /**
     * Included as part of a response to a request that included an <code>Attribution-Reporting-Eligible</code> header,
     * this is used to register an attribution source.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Attribution-Reporting-Register-Source">
     *     Mozilla docs</a>
     */
    public static final String ATTRIBUTION_REPORTING_REGISTER_SOURCE = "Attribution-Reporting-Register-Source";

    /**
     * Included as part of a response to a request that included an <code>Attribution-Reporting-Eligible</code> header,
     * this is used to register an attribution trigger.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Attribution-Reporting-Register-Trigger">
     *     Mozilla docs</a>
     */
    public static final String ATTRIBUTION_REPORTING_REGISTER_TRIGGER = "Attribution-Reporting-Register-Trigger";

    /**
     * Servers can advertise support for Client Hints using the Accept-CH header field or an equivalent HTML
     * <code>meta</code> element with <code>http-equiv</code> attribute.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-CH">Mozilla docs</a>
     */
    public static final String ACCEPT_CH = "Accept-CH";

    /**
     * Servers use <code>Critical-CH</code> along with <code>Accept-CH</code> to specify that accepted client hints
     * are also critical client hints.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Critical-CH">Mozilla docs</a>
     */
    public static final String CRITICAL_CH = "Critical-CH";

    /**
     * User agent's branding and version.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-UA">Mozilla docs</a>
     */
    public static final String SEC_CH_UA = "Sec-CH-UA";

    /**
     * User agent's underlying platform architecture.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-UA-Arch">Mozilla docs</a>
     */
    public static final String SEC_CH_UA_ARCH = "Sec-CH-UA-Arch";

    /**
     * User agent's underlying CPU architecture bitness (for example "64" bit).
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-UA-Bitness">Mozilla docs</a>
     */
    public static final String SEC_CH_UA_BITNESS = "Sec-CH-UA-Bitness";

    /**
     * User agent's form-factors, describing how the user interacts with the user-agent.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-UA-Form-Factors">Mozilla docs</a>
     */
    public static final String SEC_CH_UA_FORM_FACTORS = "Sec-CH-UA-Form-Factors";

    /**
     * User agent's full version string.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-UA-Full-Version">Mozilla docs</a>
     */
    public static final String SEC_CH_UA_FULL_VERSION = "Sec-CH-UA-Full-Version";

    /**
     * Full version for each brand in the user agent's brand list.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-UA-Full-Version-List">
     *     Mozilla docs</a>
     */
    public static final String SEC_CH_UA_FULL_VERSION_LIST = "Sec-CH-UA-Full-Version-List";

    /**
     * User agent is running on a mobile device or, more generally, prefers a "mobile" user experience.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-UA-Mobile">Mozilla docs</a>
     */
    public static final String SEC_CH_UA_MOBILE = "Sec-CH-UA-Mobile";

    /**
     * User agent's device model.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-UA-Model">Mozilla docs</a>
     */
    public static final String SEC_CH_UA_MODEL = "Sec-CH-UA-Model";

    /**
     * User agent's underlying operation system/platform.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-UA-Platform">Mozilla docs</a>
     */
    public static final String SEC_CH_UA_PLATFORM = "Sec-CH-UA-Platform";

    /**
     * User agent's underlying operation system version.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-UA-Platform-Version">Mozilla docs</a>
     */
    public static final String SEC_CH_UA_PLATFORM_VERSION = "Sec-CH-UA-Platform-Version";

    /**
     * Whether the user agent binary is running in 32-bit mode on 64-bit Windows.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-UA-WoW64">Mozilla docs</a>
     */
    public static final String SEC_CH_UA_WOW64 = "Sec-CH-UA-WoW64";

    /**
     * User's preference of dark or light color scheme.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-Prefers-Color-Scheme">
     *     Mozilla docs</a>
     */
    public static final String SEC_CH_PREFERS_COLOR_SCHEME = "Sec-CH-Prefers-Color-Scheme";

    /**
     * User's preference to see fewer animations and content layout shifts.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-Prefers-Reduced-Motion">
     *     Mozilla docs</a>
     */
    public static final String SEC_CH_PREFERS_REDUCED_MOTION = "Sec-CH-Prefers-Reduced-Motion";

    /**
     * Request header indicates the user agent's preference for reduced transparency.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-CH-Prefers-Reduced-Transparency">
     *     Mozilla docs</a>
     */
    public static final String SEC_CH_PREFERS_REDUCED_TRANSPARENCY = "Sec-CH-Prefers-Reduced-Transparency";

    /**
     * Response header used to confirm the image device to pixel ratio (DPR) in requests where the screen DPR client
     * hint was used to select an image resource.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-DPR">Mozilla docs</a>
     */
    public static final String CONTENT_DPR = "Content-DPR";

    /**
     * Approximate amount of available client RAM memory.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Device-Memory">Mozilla docs</a>
     */
    public static final String DEVICE_MEMORY = "Device-Memory";

    /**
     * Request header that provides the client device pixel ratio (the number of physical device pixels for
     * each CSS pixel).
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/DPR">Mozilla docs</a>
     */
    public static final String DPR = "DPR";

    /**
     * Request header provides the client's layout viewport width in CSS pixels.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Viewport-Width">Mozilla docs</a>
     */
    public static final String VIEWPORT_WIDTH = "Viewport-Width";

    /**
     * Request header indicates the desired resource width in physical pixels (the intrinsic size of an image).
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Width">Mozilla docs</a>
     */
    public static final String WIDTH = "Width";

    /**
     * Approximate bandwidth of the client's connection to the server, in Mbps.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Downlink">Mozilla docs</a>
     */
    public static final String DOWNLINK = "Downlink";

    /**
     * The effective connection type ("network profile") that best matches the connection's latency and bandwidth.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/ECT">Mozilla docs</a>
     */
    public static final String ECT = "ECT";

    /**
     * Application layer round trip time (RTT) in milliseconds, which includes the server processing time.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/RTT">Mozilla docs</a>
     */
    public static final String RTT = "RTT";

    /**
     * A string <code>on</code> that indicates the user agent's preference for reduced data usage.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Save-Data">Mozilla docs</a>
     */
    public static final String SAVE_DATA = "Save-Data";

    /**
     * Request header that indicates the user's tracking preference (Do Not Track).
     * Deprecated in favor of Global Privacy Control (GPC), which is communicated to servers using the
     * <code>Sec-GPC</code> header.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/DNT">Mozilla docs</a>
     */
    public static final String DNT = "DNT";

    /**
     * Response header that indicates the tracking status that applied to the corresponding request.
     * Used in conjunction with <code>DNT</code>.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Tk">Mozilla docs</a>
     */
    public static final String TK = "Tk";

    /**
     * Indicates whether the user consents to a website or service selling or sharing their personal information
     * with third parties.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Sec-GPC">Mozilla docs</a>
     */
    public static final String SEC_GPC = "Sec-GPC";

    /**
     * Response header used to indicate that the associated <code>Document</code> should be placed in an origin-keyed
     * agent cluster.
     * <br>
     * This isolation allows user agents to allocate implementation-specific resources for agent clusters,
     * such as processes or threads, more efficiently.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Origin-Agent-Cluster">Mozilla docs</a>
     */
    public static final String ORIGIN_AGENT_CLUSTER = "Origin-Agent-Cluster";

    /**
     * Defines a mechanism that enables developers to declare a network error reporting policy.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/NEL">Mozilla docs</a>
     */
    public static final String NEL = "NEL";

    /**
     * A client can send the Accept-Signature header field to indicate intention to take advantage of any available
     * signatures and to indicate what kinds of signatures it supports.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Signature">Mozilla docs</a>
     */
    public static final String ACCEPT_SIGNATURE = "Accept-Signature";

    /**
     * Indicates that the request has been conveyed in TLS early data.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Early-Data">Mozilla docs</a>
     */
    public static final String EARLY_DATA = "Early-Data";

    /**
     * The Signature header field conveys a list of signatures for an exchange, each one accompanied by information
     * about how to determine the authority of and refresh that signature.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Signature">Mozilla docs</a>
     */
    public static final String SIGNATURE = "Signature";

    /**
     * Identifies an ordered list of response header fields to include in a signature.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Signed-Headers">Mozilla docs</a>
     */
    public static final String SIGNED_HEADERS = "Signed-Headers";

    /**
     * Provides a list of URLs pointing to text resources containing speculation rule JSON definitions. <br>
     * When the response is an HTML document, these rules will be added to the document's speculation rule set.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Speculation-Rules">Mozilla docs</a>
     */
    public static final String SPECULATION_RULES = "Speculation-Rules";

    /**
     * Identifies the originating IP addresses of a client connecting to a web server through an HTTP proxy or a
     * load balancer.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Forwarded-For">Mozilla docs</a>
     */
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";

    /**
     * Identifies the original host requested that a client used to connect to your proxy or load balancer.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Forwarded-Host">Mozilla docs</a>
     */
    public static final String X_FORWARDED_HOST = "X-Forwarded-Host";

    /**
     * Identifies the protocol (HTTP or HTTPS) that a client used to connect to your proxy or load balancer.
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Forwarded-Proto">Mozilla docs</a>
     */
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
}
