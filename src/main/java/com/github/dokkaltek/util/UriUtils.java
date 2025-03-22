package com.github.dokkaltek.util;

import com.github.dokkaltek.exception.InvalidUriException;
import com.github.dokkaltek.exception.InvalidUrlException;
import com.github.dokkaltek.helper.WrapperList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.github.dokkaltek.constant.literal.SpecialChars.AMPERSAND;
import static com.github.dokkaltek.constant.literal.SpecialChars.BACKSLASH;
import static com.github.dokkaltek.constant.literal.SpecialChars.EQUAL_SIGN;
import static com.github.dokkaltek.constant.literal.SpecialChars.HASH;
import static com.github.dokkaltek.constant.literal.SpecialChars.QUESTION_MARK;
import static com.github.dokkaltek.constant.literal.SpecialChars.SLASH;
import static com.github.dokkaltek.util.StringUtils.isBlank;
import static com.github.dokkaltek.util.StringUtils.isBlankOrNull;

/**
 * Utility class for uri and url related operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UriUtils {
    private static final String PROTOCOL_SEPARATOR = "://";
    private static final String EMPTY_STRING = "";

    private static final Pattern URL_FRAGMENTS_PATTERN = Pattern.compile(
            "(.*://)?([a-zA-Z0-9.-]+)?(:\\d+)?(/[^?]*)?(\\\\?.+)?");
    private static final Pattern HOST_PATTERN = Pattern.compile(
            "^([a-zA-Z0-9-]+\\.[^/\\s?]+|localhost)(?:/[^?]+)?");

    /**
     * Validates that a given url is valid and is not empty.
     * @param uri The uri to validate.
     * @return True if the uri is valid, false otherwise.
     */
    public static boolean validateUri(String uri) {
        if (isBlankOrNull(uri)) {
            return false;
        }

        try {
            new URI(uri);
        } catch (URISyntaxException e) {
            return false;
        }

        return true;
    }

    /**
     * Validates that a given uri is valid and throws an exception otherwise.
     * @param uri The uri to validate.
     * @throws InvalidUriException If the url is not valid or is null.
     */
    public static void validateUriWithEx(String uri) {
        try {
            new URI(uri);
        } catch (URISyntaxException | NullPointerException e) {
            throw new InvalidUriException(e);
        }
    }

    /**
     * Validates that a given url is valid.
     * @param url The url to validate.
     * @return True if the url is valid, false otherwise.
     */
    public static boolean validateUrl(String url) {
        if (isBlankOrNull(url)) {
            return false;
        }

        try {
            new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }

        return true;
    }

    /**
     * Validates that a given url is valid and throws an exception otherwise.
     * @param url The url to validate.
     * @throws InvalidUriException If the url is not valid or is null.
     */
    public static void validateUrlWithEx(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException | NullPointerException e) {
            throw new InvalidUrlException(e);
        }
    }

    /**
     * Get the protocol from any uri.
     * @param uri The uri to get the protocol of.
     * @return The protocol of the uri if it has one, otherwise an empty string.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String getProtocol(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        validateUriWithEx(uri);

        if (uri.contains(PROTOCOL_SEPARATOR)) {
            return uri.split(PROTOCOL_SEPARATOR)[0];
        }

        return EMPTY_STRING;
    }

    /**
     * Get the host part of any uri.
     * @param uri The uri to get the host part from.
     * @return The host part of the uri, or an empty string if there was none.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String getHost(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        try {
            String host = new URI(uri).getHost();
            return host == null ? EMPTY_STRING : host;
        } catch (URISyntaxException ex) {
            throw new InvalidUriException(ex);
        }
    }

    /**
     * Get the port part of any uri.
     * @param uri The uri to get the port part from.
     * @return The port part of the uri, or an empty string if there was none.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String getPort(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        try {
            int port = new URI(uri).getPort();
            return port == -1 ? EMPTY_STRING : String.valueOf(port);
        } catch (URISyntaxException ex) {
            throw new InvalidUriException(ex);
        }
    }

    /**
     * Get the path from any uri.
     * @param uri The uri to get the protocol of.
     * @return The path of the uri if it has one, otherwise an empty string.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String getPath(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        try {
            return sanitizeUriStart(new URI(uri).getPath());
        } catch (URISyntaxException ex) {
            throw new InvalidUriException(ex);
        }
    }

    /**
     * Get the query part of any uri.
     * @param uri The uri to get the query part from.
     * @return The query part of the uri, or an empty string if there was none.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String getQuery(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        try {
            String query = new URI(uri).getQuery();
            return query == null ? EMPTY_STRING : query;
        } catch (URISyntaxException ex) {
            throw new InvalidUriException(ex);
        }
    }

    /**
     * Get the query params from any uri.
     * @param uri The uri to get the query params from.
     * @return The query parameters as a map.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static Map<String, WrapperList<String>> getQueryParams(String uri) {
        String query = getQuery(uri);

        if (!query.isEmpty()) {
            String[] params = query.split(AMPERSAND);
            Map<String, WrapperList<String>> queryParams = new HashMap<>(params.length);
            for (String param : params) {
                String[] keyAndValue = param.split(EQUAL_SIGN);
                if (!isBlank(keyAndValue[0])) {
                    String key = keyAndValue[0];
                    WrapperList<String> values = new WrapperList<>(1);
                    if (keyAndValue.length > 1) {
                        if (queryParams.containsKey(key)) {
                            values = new WrapperList<>(queryParams.get(key));
                        }
                        values.add(keyAndValue[1]);
                    }
                    queryParams.put(key, values);
                }
            }
            return queryParams;
        }

        return Collections.emptyMap();
    }

    /**
     * Get the fragment part of any uri.
     * @param uri The uri to get the fragment part from.
     * @return The fragment part of the uri, or an empty string if there was none.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String getFragment(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        try {
            String fragment = new URI(uri).getFragment();
            return fragment == null ? EMPTY_STRING : fragment;
        } catch (URISyntaxException ex) {
            throw new InvalidUriException(ex);
        }
    }

    /**
     * Sanitizes the ending of the uri so that it doesn't end in a slash.
     * @param uri The uri to sanitize the ending of.
     * @return The sanitized ending uri.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String sanitizeUriEnd(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        String sanitizedUri = uri.trim().replace(BACKSLASH, SLASH);
        if (sanitizedUri.endsWith(SLASH)) {
            sanitizedUri = sanitizedUri.substring(0, sanitizedUri.length() - 1);
        }

        // Try to normalize the uri if it was valid, otherwise just return the sanitized uri
        try {
            return new URI(sanitizedUri).normalize().toString();
        } catch (URISyntaxException ex) {
            throw new InvalidUriException(ex);
        }
    }

    /**
     * Makes sure that the uri path starts with a slash.
     * @param path The uri path.
     * @return The sanitized path.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String sanitizeUriStart(String path) {
        if (isBlankOrNull(path)) {
            return EMPTY_STRING;
        }

        String sanitizedPath = path.trim().replace(BACKSLASH, SLASH);
        if (!sanitizedPath.startsWith(SLASH)) {
            sanitizedPath = SLASH + sanitizedPath;
        }

        // Try to normalize the path if it was valid, otherwise throw exception
        try {
            URI pathUri = new URI(sanitizedPath);

            // If the path actually holds a protocol or a host, or starts with a question mark or a hash, return it
            if (path.contains(PROTOCOL_SEPARATOR) || HOST_PATTERN.matcher(path).find()) {
                return sanitizeUriEnd(path);
            } else if (path.startsWith(QUESTION_MARK) || path.startsWith(HASH)) {
                return path;
            }

            return pathUri.normalize().toString();
        } catch (URISyntaxException ex) {
            throw new InvalidUriException(ex);
        }
    }

    /**
     * URL encodes a given string using UTF-8. If the encoding fails, it returns the original string.
     * @param stringToEncode The url to encode.
     * @return The encoded url.
     */
    public static String encodeUrl(String stringToEncode) {
        if (isBlankOrNull(stringToEncode)) {
            return EMPTY_STRING;
        }

        try {
            return URLEncoder.encode(stringToEncode, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            return stringToEncode;
        }
    }

    /**
     * URL Decodes a given string using UTF-8. If the decoding fails, it returns the original string.
     * @param stringToDecode The url to decode.
     * @return The decoded url.
     */
    public static String decodeUrl(String stringToDecode) {
        if (isBlankOrNull(stringToDecode)) {
            return EMPTY_STRING;
        }

        try {
            return URLDecoder.decode(stringToDecode, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            return stringToDecode;
        }
    }

    /**
     * Joins the parts of the uri making sure that no double slashes are added when joining.
     * @param paths The main uri and paths to join.
     * @return The joined url.
     * @throws InvalidUriException If any of the paths is invalid (but not on null).
     */
    public static String joinUriPaths(String... paths) {
        if (paths == null || paths.length == 0) {
            return EMPTY_STRING;
        }

        StringBuilder builder = new StringBuilder(sanitizeUriEnd(paths[0]));
        for (int i = 1; i < paths.length; i++) {
            builder.append(sanitizeUriStart(paths[i]));
        }
        return builder.toString();
    }

    /**
     * Sets the protocol of the uri.  If the uri isn't valid, it returns the protocol.
     * If it already had a protocol, it replaces it.
     * @param uri The uri to set the protocol to.
     * @param protocol The uri protocol.
     * @return The uri with the protocol changed.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String setProtocol(String uri, String protocol) {
        if (isBlankOrNull(uri)) {
            uri = EMPTY_STRING;
        }

        validateUriWithEx(uri);

        String sanitizedProtocol = protocol.trim();
        if (!protocol.endsWith("://")) {
            sanitizedProtocol += "://";
        }

        if (EMPTY_STRING.equals(uri)) {
            return sanitizedProtocol;
        }

        // If the domain is not in the uri, we don't set the protocol
        if (!uri.contains(PROTOCOL_SEPARATOR) && !HOST_PATTERN.matcher(uri).find()) {
            return uri;
        }

        return URL_FRAGMENTS_PATTERN.matcher(uri).replaceFirst(sanitizedProtocol + "$2$3$4$5");
    }

    /**
     * Sets the host of the uri. If the uri isn't valid, it returns the host.
     * If it already had a host, it replaces it.
     * @param uri The url to set the host to.
     * @param host The uri host.
     * @return The uri with the host changed.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String setHost(String uri, String host) {
        if (isBlankOrNull(uri)) {
            uri = EMPTY_STRING;
        }

        validateUriWithEx(uri);

        String sanitizedHost = sanitizeUriEnd(host);
        if (EMPTY_STRING.equals(uri)) {
            return sanitizedHost;
        }

        // If the domain is not in the uri, we add the host right before the first path segment
        if (!uri.contains(PROTOCOL_SEPARATOR) && !HOST_PATTERN.matcher(uri).find()) {
            if (uri.startsWith(QUESTION_MARK) || uri.startsWith(SLASH) || uri.startsWith(HASH)) {
                return sanitizedHost + uri;
            } else {
                return sanitizedHost + SLASH + uri;
            }
        }

        return URL_FRAGMENTS_PATTERN.matcher(uri).replaceFirst("$1" + sanitizedHost + "$3$4$5");
    }

    /**
     * Sets the port of the uri. If the uri isn't valid, it returns an empty string.
     * If it already had a port, it replaces it.
     * @param uri The uri to set the port to.
     * @param port The uri port.
     * @return The uri with the port attached.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String setPort(String uri, int port) {
        if (isBlankOrNull(uri)) {
            uri = EMPTY_STRING;
        }

        validateUriWithEx(uri);

        if (EMPTY_STRING.equals(uri)) {
            return uri;
        }

        // split by path, and then add the port right before the first path segment
        String portStr = ":" + port;

        // If the domain is not in the uri, we don't add the port
        if (!uri.contains(PROTOCOL_SEPARATOR) && !HOST_PATTERN.matcher(uri).find()) {
            return uri;
        }

        return URL_FRAGMENTS_PATTERN.matcher(uri).replaceFirst("$1$2" + portStr + "$4$5");
    }

    /**
     * Sets the path of the uri. If the uri isn't valid, it returns the path. If it already had a path, it replaces it.
     * @param uri The uri to set the path to.
     * @param path The uri path.
     * @return The uri with the path attached.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String setPath(String uri, String path) {
        if (isBlankOrNull(uri)) {
            uri = EMPTY_STRING;
        }

        validateUriWithEx(uri);

        String sanitizedPath = sanitizeUriStart(path);
        if (EMPTY_STRING.equals(uri)) {
            return sanitizedPath;
        }

        // If the domain is not in the uri, we add the path right before the query params or fragment
        if (!uri.contains(PROTOCOL_SEPARATOR) && !HOST_PATTERN.matcher(uri).find()) {
            String uriWithoutPath = EMPTY_STRING;
            if (uri.contains(QUESTION_MARK)) {
                uriWithoutPath = QUESTION_MARK + uri.split(BACKSLASH + QUESTION_MARK)[1];
            } else if (uri.contains(HASH)) {
                uriWithoutPath = HASH + uri.split(HASH)[1];
            }
            return sanitizedPath + uriWithoutPath;
        }

        return URL_FRAGMENTS_PATTERN.matcher(uri).replaceFirst("$1$2$3" + sanitizedPath + "$5");
    }

    /**
     * Sets the query of the uri. If it already had a query, it replaces it.
     * @param uri The uri to set the query to.
     * @param query The uri query.
     * @return The uri with the query attached.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String setQuery(String uri, String query) {
        if (isBlankOrNull(uri)) {
            uri = EMPTY_STRING;
            if (isBlankOrNull(query)) {
                return EMPTY_STRING;
            }
        }

        validateUriWithEx(uri);

        if (isBlankOrNull(query)) {
            query = EMPTY_STRING;
        }

        // Add question mark if it was missing
        String sanitizedQuery = query.trim();
        if (!sanitizedQuery.startsWith(QUESTION_MARK) && !sanitizedQuery.isEmpty()) {
             sanitizedQuery = QUESTION_MARK + sanitizedQuery;
        }

        // Remove fragment to add it later
        String[] fragmentParts = uri.split(HASH);
        String fragment = EMPTY_STRING;
        uri = fragmentParts[0];
        if (fragmentParts.length > 1) {
            fragment = HASH + fragmentParts[1];
        }

        if (EMPTY_STRING.equals(uri)) {
            return sanitizedQuery;
        }

        return URL_FRAGMENTS_PATTERN.matcher(uri).replaceFirst("$1$2$3$4" + sanitizedQuery + fragment);
    }

    /**
     * Sets the query parameters in the uri from a map.
     * @param uri The uri to add the query parameters to.
     * @param queryParamsMap The map with the query parameters.
     * @return The uri with the query parameters attached or replaced.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String setQueryParams(String uri, Map<String, String> queryParamsMap) {
        return setQuery(uri, generateQuery(queryParamsMap));
    }

    /**
     * Sets the multi-value query parameters in the uri from a map.
     * @param uri The uri to add the query parameters to.
     * @param queryParamsMap The map with the query parameters.
     * @return The uri with the query parameters attached or replaced.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String setMultiValueQueryParams(String uri, Map<String, List<String>> queryParamsMap) {
        return setQuery(uri, generateMultiValueParamsQuery(queryParamsMap));
    }

    /**
     * Sets the fragment of the uri.
     * @param uri The uri to set the fragment to.
     * @param fragment The fragment value.
     * @return The uri with the fragment attached.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String setFragment(String uri, String fragment) {
        if (isBlankOrNull(uri)) {
            uri = EMPTY_STRING;
        }

        validateUriWithEx(uri);

        if (!fragment.startsWith(HASH)) {
            fragment = HASH + fragment;
        }

        // Remove fragment if it had one, and add the new one
        String[] fragmentParts = uri.split(HASH);
        return fragmentParts[0] + fragment;
    }

    /**
     * Adds a query parameter in the uri.
     * @param uri The uri to set the query param to.
     * @param key The key to add to the query parameters.
     * @param value The value of the parameter.
     * @return The updated uri.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String addQueryParam(String uri, String key, String value) {
        if (isBlankOrNull(uri)) {
            uri = EMPTY_STRING;
            if (isBlankOrNull(key)) {
                return EMPTY_STRING;
            }
        }

        validateUriWithEx(uri);

        if (isBlankOrNull(key)) {
            return uri;
        }

        if (value != null) {
            value = EQUAL_SIGN + value;
        } else {
            value = EMPTY_STRING;
        }

        String[] fragmentParts = uri.split(HASH);
        String fragment = EMPTY_STRING;
        if (fragmentParts.length > 1) {
            fragment = HASH + fragmentParts[1];
        }

        String updatedUri = fragmentParts[0];

        if (updatedUri.contains(QUESTION_MARK)) {
            updatedUri += AMPERSAND + key + value;
        } else {
            updatedUri += QUESTION_MARK + key + value;
        }

        return updatedUri + fragment;
    }

    /**
     * Sets a query parameter in the uri with multiple values.
     * @param uri The uri to set the query param to.
     * @param key The key to add to the query parameters.
     * @param values The list of values of the parameter.
     * @return The updated uri.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String addMultiValueQueryParam(String uri, String key, List<String> values) {
        Map<String, List<String>> queryParamsMap = new HashMap<>();
        queryParamsMap.put(key, values);
        return addMultiValueQueryParams(uri, queryParamsMap);
    }

    /**
     * Sets a query parameter in the uri with multiple values.
     * @param uri The uri to set the query param to.
     * @param key The key to add to the query parameters.
     * @param values The array of values of the parameter.
     * @return The updated uri.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String addMultiValueQueryParam(String uri, String key, String... values) {
        return addMultiValueQueryParam(uri, key, Arrays.asList(values));
    }

    /**
     * Sets the query parameters in the uri from a map. Accepts multiple values for the same key as well.
     * @param uri The uri to set the query parameters to.
     * @param queryParamsMap The map with the query parameters.
     * @return The updated uri.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String addMultiValueQueryParams(String uri, Map<String, List<String>> queryParamsMap) {
        if (isBlankOrNull(uri)) {
            uri = EMPTY_STRING;
        }

        String query = generateMultiValueParamsQuery(queryParamsMap);

        // Set params back on query if it already had some
        String prevQuery = getQuery(uri);

        if (!prevQuery.isEmpty()) {
            return setQuery(uri, prevQuery + AMPERSAND + query);
        }
        return setQuery(uri, query);
    }

    /**
     * Sets the query parameters in the uri from a map.
     * @param uri The uri to set the query parameters to.
     * @param queryParamsMap The map with the query parameters.
     * @return The updated uri.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String addQueryParams(String uri, Map<String, String> queryParamsMap) {
        if (isBlankOrNull(uri)) {
            uri = EMPTY_STRING;
        }

        String query = generateQuery(queryParamsMap);

        // Set params back on query if it already had some
        String prevQuery = getQuery(uri);

        if (!prevQuery.isEmpty()) {
            return setQuery(uri, prevQuery + AMPERSAND + query);
        }
        return setQuery(uri, query);
    }

    /**
     * Updates the value of the first occurrence of a query param if it exists, otherwise it gets added.
     * @param uri The uri to update the param.
     * @param key The key of the param to update.
     * @param value The value of the param to update.
     * @return The updated uri.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String updateQueryParam(String uri, String key, String value) {
        if (isBlankOrNull(uri)) {
            if (isBlankOrNull(key)) {
                return EMPTY_STRING;
            }
            uri = EMPTY_STRING;
        }

        String query = getQuery(uri);

        if (isBlankOrNull(query)) {
            return addQueryParam(uri, key, value);
        }

        // Try to replace the value first
        String result = uri.replaceFirst(key + EQUAL_SIGN + "[^&]*", key + EQUAL_SIGN + value);

        // If the value wasn't replaced it didn't exist, so we add it
        if (result.equals(uri)) {
            return addQueryParam(uri, key, value);
        }

        return result;
    }

    /**
     * Removes the protocol from the uri.
     * @param uri The uri to remove the protocol from.
     * @return The uri with the protocol removed.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String removeProtocol(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        validateUriWithEx(uri);

        return URL_FRAGMENTS_PATTERN.matcher(uri).replaceFirst("$2$3$4$5");
    }

    /**
     * Removes the host, protocol and port from the uri.
     * @param uri The uri to remove the host, protocol and port from.
     * @return The uri with the host, protocol and port removed.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String removeHost(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        validateUriWithEx(uri);

        if (!uri.contains(PROTOCOL_SEPARATOR) && !HOST_PATTERN.matcher(uri).find()) {
            if (uri.startsWith(QUESTION_MARK) || uri.startsWith(SLASH) || uri.startsWith(HASH)) {
                return uri;
            } else {
                return sanitizeUriStart(uri);
            }
        }

        return URL_FRAGMENTS_PATTERN.matcher(uri).replaceFirst("$4$5");
    }

    /**
     * Removes the port from the uri.
     * @param uri The uri to remove the port from.
     * @return The uri with the port removed.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String removePort(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        validateUriWithEx(uri);

        if (!uri.contains(PROTOCOL_SEPARATOR) && !HOST_PATTERN.matcher(uri).find()) {
            if (uri.startsWith(QUESTION_MARK) || uri.startsWith(SLASH) || uri.startsWith(HASH)) {
                return uri;
            } else {
                return sanitizeUriStart(uri);
            }
        }

        return URL_FRAGMENTS_PATTERN.matcher(uri).replaceFirst("$1$2$4$5");
    }

    /**
     * Removes the path from the uri.
     * @param uri The uri to remove the path from.
     * @return The uri with the path removed.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String removePath(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        validateUriWithEx(uri);

        if (!uri.contains(PROTOCOL_SEPARATOR) && !HOST_PATTERN.matcher(uri).find())
            uri = sanitizeUriStart(uri);

        return URL_FRAGMENTS_PATTERN.matcher(uri).replaceFirst("$1$2$3$5");
    }

    /**
     * Removes a query parameter.
     * @param uri The uri to remove the query parameter from.
     * @param key The key to remove.
     * @return The updated uri.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String removeQueryParam(String uri, String key) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        if (isBlankOrNull(getQuery(uri))) {
            return uri;
        }

        // Remove fragment for the checks
        String[] fragmentParts = uri.split(HASH);
        String result = fragmentParts[0];
        String fragment = EMPTY_STRING;
        if (fragmentParts.length > 1) {
            fragment = HASH + fragmentParts[1];
        }

        int queryStartInd = result.indexOf(QUESTION_MARK);
        List<String> possibleMatches = new ArrayList<>(2);
        possibleMatches.add(AMPERSAND + key);
        possibleMatches.add(key);
        for (String possibleMath : possibleMatches) {
            if (result.contains(possibleMath)) {
                int keyInd = result.indexOf(possibleMath, queryStartInd + 1);

                if (keyInd == -1) {
                    return result;
                }

                int keyEndInd = result.indexOf(AMPERSAND, keyInd + 1);

                if (keyEndInd == -1) {
                    keyEndInd = result.length();
                }

                result = result.substring(0, keyInd) + result.substring(keyEndInd);
                break;
            }
        }

        // Make sure that if the query was empty, the question mark is removed
        if (result.trim().endsWith(QUESTION_MARK)) {
            result = result.substring(0, result.length() - 1);
        }

        return sanitizeUriStart(result) + fragment;
    }

    /**
     * Removes the query part from the url.
     * @param uri The uri to remove the query from.
     * @return The uri without the query.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String removeQuery(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        validateUriWithEx(uri);

        // Store the fragment to add it later
        String[] fragmentParts = uri.split(HASH);
        String fragment = EMPTY_STRING;
        uri = fragmentParts[0];
        if (fragmentParts.length > 1) {
            fragment = HASH + fragmentParts[1];
        }

        String uriWithoutQuery = uri.split(BACKSLASH + QUESTION_MARK)[0];
        return sanitizeUriStart(uriWithoutQuery) + fragment;
    }

    /**
     * Removes the fragment of the uri.
     * @param uri The uri to set the fragment to.
     * @return The uri without the fragment.
     * @throws InvalidUriException If the uri is not valid (but not on null).
     */
    public static String removeFragment(String uri) {
        if (isBlankOrNull(uri)) {
            return EMPTY_STRING;
        }

        validateUriWithEx(uri);

        // Remove fragment if it had one, and add the new one
        String[] fragmentParts = uri.split(HASH);
        return sanitizeUriStart(fragmentParts[0]);
    }

    /**
     * Generates the query of single values from the map.
     * @param queryParamsMap The query parameters map.
     * @return The string with the query parameters as <code>key=value</code> pairs.
     */
    private static String generateQuery(Map<String, String> queryParamsMap) {
        if (queryParamsMap == null || queryParamsMap.isEmpty()) {
            return EMPTY_STRING;
        }

        // Start building uri with params
        StringBuilder queryBuilder = new StringBuilder();
        int paramsProcessed = 0;
        int totalParams = queryParamsMap.size();
        for (Map.Entry<String, String> entry : queryParamsMap.entrySet()) {
            paramsProcessed++;
            queryBuilder.append(entry.getKey());

            // Append value if not null
            if (entry.getValue() != null) {
                queryBuilder.append(EQUAL_SIGN).append(entry.getValue());
            }

            if (paramsProcessed < totalParams) {
                queryBuilder.append(AMPERSAND);
            }
        }

        return queryBuilder.toString();
    }

    /**
     * Generates the query of multiple value keys from the map.
     * @param queryParamsMap The query parameters map.
     * @return The string with the query parameters as <code>key=value</code> pairs.
     */
    private static String generateMultiValueParamsQuery(Map<String, List<String>> queryParamsMap) {
        if (queryParamsMap == null || queryParamsMap.isEmpty()) {
            return EMPTY_STRING;
        }

        // Start building uri with params
        StringBuilder queryBuilder = new StringBuilder();
        int paramsProcessed = 0;
        int totalParams = queryParamsMap.size();
        for (Map.Entry<String, List<String>> entry : queryParamsMap.entrySet()) {
            paramsProcessed++;
            addMultiValueQueryParam(queryBuilder, entry);
            if (paramsProcessed < totalParams) {
                queryBuilder.append(AMPERSAND);
            }
        }
        return queryBuilder.toString();
    }

    /**
     * Adds a multi-value query parameter to a {@link StringBuilder}.
     * @param queryBuilder The query builder with the rest of the query parameters.
     * @param entry The entry to add.
     */
    private static void addMultiValueQueryParam(StringBuilder queryBuilder, Map.Entry<String, List<String>> entry) {
        List<String> values = entry.getValue();
        // Make sure that the list is not null
        int valueCount = 1;
        if (values != null) {
            valueCount = values.size();
        }

        for (int i = 0; i < valueCount; i++) {
            queryBuilder.append(entry.getKey());

            // Append value if not null
            if (values != null && values.get(i) != null) {
                queryBuilder.append(EQUAL_SIGN).append(values.get(i));
            }

            // Append '&' if not last
            if (i + 1 < valueCount) {
                queryBuilder.append(AMPERSAND);
            }
        }
    }
}
