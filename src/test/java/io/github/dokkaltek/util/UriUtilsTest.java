package io.github.dokkaltek.util;

import io.github.dokkaltek.exception.InvalidUriException;
import io.github.dokkaltek.helper.WrapperList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link UriUtils} class.
 */
class UriUtilsTest {
    private static final String SAMPLE_URL_NO_PATH = "https://test.com";
    private static final String SAMPLE_URL_NO_PATH_WITH_PORT = "https://test.com:80";
    private static final String SAMPLE_URL_NO_PROTOCOL = "test.com";
    private static final String SAMPLE_URL_WITH_PATH = "https://test.com/some/path";
    private static final String SAMPLE_URL = "https://test.com/some/path?there=was&some=param#andAFragmentToo";
    private static final String SAMPLE_PATH = "some/path?there=was&some=param#andAFragmentToo";
    private static final String SAMPLE_PATH_WITH_FORWARD_SLASH = SAMPLE_PATH.replace("/", "\\");
    private static final String SAMPLE_URL_FTP = "ftp://test.com/some/path";
    private static final String INVALID_URI = "once upon a time";
    private static final String BLANK_STRING = " ";
    private static final String SAMPLE_QUERY = "?some=value";
    private static final String SAMPLE_FRAGMENT = "#fragment";

    /**
     * Test for {@link UriUtils#getProtocol(String)} method.
     */
    @Test
    @DisplayName("Test getting the protocol of any URI")
    void testGetProtocol() {
        assertEquals("https", UriUtils.getProtocol(SAMPLE_URL_NO_PATH));
        assertEquals("ftp", UriUtils.getProtocol(SAMPLE_URL_FTP));
        assertEquals("", UriUtils.getProtocol(SAMPLE_URL_NO_PROTOCOL));
        assertEquals("", UriUtils.getProtocol(SAMPLE_PATH));
        assertEquals("", UriUtils.getProtocol(null));
        assertEquals("", UriUtils.getProtocol(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.getProtocol(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#getHost(String)} method.
     */
    @Test
    @DisplayName("Test getting the host of any URI")
    void testGetHost() {
        assertEquals("test.com", UriUtils.getHost(SAMPLE_URL_NO_PATH));
        assertEquals("test.com", UriUtils.getHost(SAMPLE_URL_NO_PATH_WITH_PORT));
        assertEquals("", UriUtils.getHost(SAMPLE_PATH));
        assertEquals("", UriUtils.getHost(null));
        assertEquals("", UriUtils.getHost(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.getHost(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#getPort(String)} method.
     */
    @Test
    @DisplayName("Test getting the port of any URI")
    void testGetPort() {
        assertEquals("", UriUtils.getPort(SAMPLE_URL_NO_PATH));
        assertEquals("80", UriUtils.getPort(SAMPLE_URL_NO_PATH_WITH_PORT));
        assertEquals("", UriUtils.getPort(SAMPLE_PATH));
        assertEquals("", UriUtils.getPort(null));
        assertEquals("", UriUtils.getPort(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.getPort(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#getPath(String)} method.
     */
    @Test
    @DisplayName("Test getting the path of any URI")
    void testGetPath() {
        assertEquals("", UriUtils.getPath(SAMPLE_URL_NO_PATH));
        assertEquals("/some/path", UriUtils.getPath(SAMPLE_URL_WITH_PATH));
        assertEquals("/some/path", UriUtils.getPath(SAMPLE_URL));
        assertEquals("/some/path", UriUtils.getPath(SAMPLE_PATH));
        assertEquals("/some/path", UriUtils.getPath("/" + SAMPLE_PATH));
        assertEquals("", UriUtils.getPath(null));
        assertEquals("", UriUtils.getPath(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.getPath(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#getQuery(String)} method.
     */
    @Test
    @DisplayName("Test getting the query of any URI")
    void testGetQuery() {
        assertEquals("", UriUtils.getQuery(SAMPLE_URL_NO_PATH));
        assertEquals("there=was&some=param", UriUtils.getQuery(SAMPLE_PATH));
        assertEquals("", UriUtils.getQuery(BLANK_STRING));
        assertEquals("", UriUtils.getQuery(null));
        assertThrows(InvalidUriException.class, () -> UriUtils.getQuery(INVALID_URI));
    }



    /**
     * Test for {@link UriUtils#getQueryParams(String)} method.
     */
    @Test
    @DisplayName("Test getting the query parameters of any URI")
    void testGetQueryParameters() {
        Map<String, WrapperList<String>> queryParameters = UriUtils.getQueryParams(SAMPLE_URL);
        assertEquals(2, queryParameters.size());
        assertEquals("was", queryParameters.get("there").first());
        assertEquals("param", queryParameters.get("some").first());
        assertEquals(2, UriUtils.getQueryParams("?some=param&some=otherVal").get("some").size());
        assertEquals(0, UriUtils.getQueryParams(BLANK_STRING).size());
        assertEquals(0, UriUtils.getQueryParams(null).size());
        assertThrows(InvalidUriException.class, () -> UriUtils.getQueryParams(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#getFragment(String)} method.
     */
    @Test
    @DisplayName("Test getting the fragment of any URI")
    void testGetFragment() {
        assertEquals("", UriUtils.getFragment(SAMPLE_URL_NO_PATH));
        assertEquals("andAFragmentToo", UriUtils.getFragment(SAMPLE_PATH));
        assertEquals("", UriUtils.getFragment(null));
        assertEquals("", UriUtils.getFragment(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.getFragment(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#sanitizePathEnd(String)} method.
     */
    @Test
    @DisplayName("Test sanitizing the end of a URI")
    void testSanitizePathEnd() {
        assertEquals(SAMPLE_URL_NO_PATH, UriUtils.sanitizePathEnd(SAMPLE_URL_NO_PATH + "/"));
        assertEquals(SAMPLE_URL_NO_PATH, UriUtils.sanitizePathEnd(SAMPLE_URL_NO_PATH + "\\"));
        assertEquals(SAMPLE_PATH, UriUtils.sanitizePathEnd(SAMPLE_PATH_WITH_FORWARD_SLASH));
        assertEquals("", UriUtils.sanitizePathEnd(null));
        assertEquals("", UriUtils.sanitizePathEnd(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.sanitizePathEnd(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#sanitizePathStart(String)} method.
     */
    @Test
    @DisplayName("Test sanitizing the start of a URI")
    void testSanitizePathStart() {
        assertEquals("/" + SAMPLE_PATH, UriUtils.sanitizePathStart(SAMPLE_PATH));
        assertEquals("/" + SAMPLE_PATH, UriUtils.sanitizePathStart("/" + SAMPLE_PATH));
        assertEquals("/" + SAMPLE_PATH, UriUtils.sanitizePathStart("\\" + SAMPLE_PATH));
        assertEquals("/" + SAMPLE_PATH,
                UriUtils.sanitizePathStart(SAMPLE_PATH_WITH_FORWARD_SLASH));
        assertEquals("", UriUtils.sanitizePathStart(null));
        assertEquals("", UriUtils.sanitizePathStart(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.sanitizePathStart(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#encodeUrl(String)} method.
     */
    @Test
    @DisplayName("Test URL encoding a given string")
    void testEncodeUrl() {
        assertEquals("https%3A%2F%2Ftest.com%2Fsome+path",
                UriUtils.encodeUrl("https://test.com/some path"));
        assertEquals("", UriUtils.encodeUrl(null));
        assertEquals("once+upon+a+time", UriUtils.encodeUrl(INVALID_URI));
        assertEquals("", UriUtils.encodeUrl(BLANK_STRING));
    }

    /**
     * Test for {@link UriUtils#decodeUrl(String)} method.
     */
    @Test
    @DisplayName("Test URL decoding a given string")
    void testDecodeUrl() {
        assertEquals("https://test.com/some path",
                UriUtils.decodeUrl("https%3A%2F%2Ftest.com%2Fsome+path"));
        assertEquals("", UriUtils.decodeUrl(null));
        assertEquals(INVALID_URI, UriUtils.decodeUrl("once+upon+a+time"));
        assertEquals("", UriUtils.decodeUrl(BLANK_STRING));
    }

    /**
     * Test for {@link UriUtils#joinUriPaths(String...)} method.
     */
    @Test
    @DisplayName("Test joining URIs")
    void testJoinUriPaths() {
        assertEquals(SAMPLE_URL, UriUtils.joinUriPaths(SAMPLE_URL_NO_PATH, SAMPLE_PATH));
        assertEquals(SAMPLE_URL, UriUtils.joinUriPaths(SAMPLE_URL_NO_PATH + "/", "/" + SAMPLE_PATH));
        assertEquals(SAMPLE_URL, UriUtils.joinUriPaths(SAMPLE_URL_NO_PATH + "\\", "/" + SAMPLE_PATH));
        assertEquals(SAMPLE_URL,
                UriUtils.joinUriPaths(SAMPLE_URL_NO_PATH, SAMPLE_PATH_WITH_FORWARD_SLASH));
        assertEquals(SAMPLE_URL, UriUtils.joinUriPaths(SAMPLE_URL_NO_PATH, SAMPLE_PATH.substring(0, 9),
                SAMPLE_PATH.substring(9, 20), SAMPLE_PATH.substring(19)));
        assertEquals("", UriUtils.joinUriPaths((String[]) null));
        assertEquals("", UriUtils.joinUriPaths((String) null));
        assertEquals("", UriUtils.joinUriPaths(BLANK_STRING, BLANK_STRING));
    }

    /**
     * Test for {@link UriUtils#setProtocol(String, String)} method.
     */
    @Test
    void testSetProtocol() {
        final String protocol = "ftp://";
        assertEquals(SAMPLE_URL_NO_PATH.replace("https://", protocol), UriUtils.setProtocol(SAMPLE_URL_NO_PATH, protocol));
        assertEquals(SAMPLE_URL.replace("https://", protocol), UriUtils.setProtocol(SAMPLE_URL, protocol));
        assertEquals(protocol + SAMPLE_URL_NO_PROTOCOL, UriUtils.setProtocol(SAMPLE_URL_NO_PROTOCOL, "ftp"));
        assertEquals(SAMPLE_PATH, UriUtils.setProtocol(SAMPLE_PATH, protocol));
    }

    /**
     * Test for {@link UriUtils#setHost(String, String)} method.
     */
    @Test
    void testSetHost() {
        final String host = "test2.com";
        assertEquals(SAMPLE_URL_NO_PATH.replace("test.com", host), UriUtils.setHost(SAMPLE_URL_NO_PATH, host));
        assertEquals(SAMPLE_URL.replace("test.com", host), UriUtils.setHost(SAMPLE_URL, host));
        assertEquals(host, UriUtils.setHost(SAMPLE_URL_NO_PROTOCOL, host));
        assertEquals(host + "/" + SAMPLE_PATH, UriUtils.setHost(SAMPLE_PATH, host));
        assertEquals(host + "/" + SAMPLE_PATH, UriUtils.setHost("/" + SAMPLE_PATH, host));
        assertEquals(host + "?some=param", UriUtils.setHost("?some=param", host));
        assertEquals(host + SAMPLE_QUERY, UriUtils.setHost(SAMPLE_QUERY, host));
        assertEquals(host + SAMPLE_FRAGMENT, UriUtils.setHost(SAMPLE_FRAGMENT, host));
        assertEquals(host, UriUtils.setHost(null, host));
        assertEquals(host, UriUtils.setHost(BLANK_STRING, host));
    }

    /**
     * Test for {@link UriUtils#setPort(String, int)} method.
     */
    @Test
    void testSetPort() {
        final String port = ":8080";
        assertEquals(SAMPLE_URL_NO_PATH + port, UriUtils.setPort(SAMPLE_URL_NO_PATH, 8080));
        assertEquals(SAMPLE_URL_NO_PATH + port, UriUtils.setPort(SAMPLE_URL_NO_PATH_WITH_PORT, 8080));
        assertEquals(SAMPLE_URL_NO_PROTOCOL + port, UriUtils.setPort(SAMPLE_URL_NO_PROTOCOL, 8080));
        assertEquals("https://test.com:8080/some/path?there=was&some=param#andAFragmentToo",
                UriUtils.setPort(SAMPLE_URL, 8080));
        assertEquals(SAMPLE_QUERY, UriUtils.setPort(SAMPLE_QUERY, 8080));
        assertEquals(SAMPLE_FRAGMENT, UriUtils.setPort(SAMPLE_FRAGMENT, 8080));
        assertEquals(SAMPLE_PATH, UriUtils.setPort(SAMPLE_PATH, 8080));
        assertEquals("", UriUtils.setPort(null, 8080));
        assertEquals("", UriUtils.setPort(BLANK_STRING, 8080));
    }

    /**
     * Test for {@link UriUtils#setPath(String, String)} method.
     */
    @Test
    void testSetPath() {
        final String path = "/new/path";
        assertEquals(SAMPLE_URL_NO_PATH + path, UriUtils.setPath(SAMPLE_URL_NO_PATH, path));
        assertEquals(SAMPLE_URL_NO_PATH + path, UriUtils.setPath(SAMPLE_URL_NO_PATH, path.substring(1)));
        assertEquals(SAMPLE_URL_NO_PATH_WITH_PORT + path, UriUtils.setPath(SAMPLE_URL_NO_PATH_WITH_PORT, path));
        assertEquals(SAMPLE_URL_NO_PROTOCOL + path, UriUtils.setPath(SAMPLE_URL_NO_PROTOCOL, path));
        assertEquals("https://test.com/new/path?there=was&some=param#andAFragmentToo",
                UriUtils.setPath(SAMPLE_URL, path));
        assertEquals(path + "?there=was&some=param#andAFragmentToo", UriUtils.setPath(SAMPLE_PATH, path));
        assertEquals(path + SAMPLE_QUERY, UriUtils.setPath(SAMPLE_QUERY, path));
        assertEquals(path + SAMPLE_FRAGMENT, UriUtils.setPath(SAMPLE_FRAGMENT, path));
        assertEquals(path, UriUtils.setPath(null, path));
        assertEquals(path, UriUtils.setPath(BLANK_STRING, path));
    }

    /**
     * Test for {@link UriUtils#setQuery(String, String)} method.
     */
    @Test
    void testSetQuery() {
        final String query = "?some=param&goes=here&test";
        assertEquals(SAMPLE_URL_NO_PATH + query, UriUtils.setQuery(SAMPLE_URL_NO_PATH, query));
        assertEquals(SAMPLE_URL_NO_PATH + query, UriUtils.setQuery(SAMPLE_URL_NO_PATH,
                query.substring(1)));
        assertEquals(SAMPLE_URL_NO_PATH_WITH_PORT + query, UriUtils.setQuery(SAMPLE_URL_NO_PATH_WITH_PORT, query));
        assertEquals(SAMPLE_URL_NO_PROTOCOL + query, UriUtils.setQuery(SAMPLE_URL_NO_PROTOCOL, query));
        assertEquals("https://test.com/some/path" + query + "#andAFragmentToo",
                UriUtils.setQuery(SAMPLE_URL, query));
        assertEquals("some/path" + query + "#andAFragmentToo", UriUtils.setQuery(SAMPLE_PATH, query));
        assertEquals(query, UriUtils.setQuery("?some=param", query));
        assertEquals(query, UriUtils.setQuery(null, query));
        assertEquals(query, UriUtils.setQuery(BLANK_STRING, query));
        assertThrows(InvalidUriException.class, () -> UriUtils.setQuery(INVALID_URI, query));
    }

    /**
     * Test for {@link UriUtils#setQueryParams(String, Map)} method.
     */
    @Test
    void testSetQueryParams() {
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("some", "param");
        queryParamsMap.put("goes", "here");
        queryParamsMap.put("sample", null);
        String newUrl = UriUtils.setQueryParams(SAMPLE_URL_NO_PATH, queryParamsMap);
        assertTrue(newUrl.contains("some=param"));
        assertTrue(newUrl.contains("goes=here"));
        assertTrue(newUrl.contains("sample"));
    }

    /**
     * Test for {@link UriUtils#setMultiValueQueryParams(String, Map)} method.
     */
    @Test
    void testSetMultiValueQueryParams() {
        Map<String, List<String>> queryParamsMap = new HashMap<>();
        queryParamsMap.put("some", WrapperList.of("param", "param2"));
        queryParamsMap.put("goes", WrapperList.of("here", "and_here"));
        queryParamsMap.put("sample", null);
        String newUrl = UriUtils.setMultiValueQueryParams(SAMPLE_URL_NO_PATH, queryParamsMap);
        assertTrue(newUrl.contains("some=param"));
        assertTrue(newUrl.contains("some=param2"));
        assertTrue(newUrl.contains("goes=here"));
        assertTrue(newUrl.contains("goes=and_here"));
        assertTrue(newUrl.contains("sample"));
    }

    /**
     * Test for {@link UriUtils#setFragment(String, String)} method.
     */
    @Test
    void testSetFragment() {
        assertEquals(SAMPLE_URL_NO_PATH + SAMPLE_FRAGMENT,
                UriUtils.setFragment(SAMPLE_URL_NO_PATH, SAMPLE_FRAGMENT));
        assertEquals(SAMPLE_URL_NO_PATH + SAMPLE_FRAGMENT,
                UriUtils.setFragment(SAMPLE_URL_NO_PATH, SAMPLE_FRAGMENT.substring(1)));
        assertEquals(SAMPLE_FRAGMENT, UriUtils.setFragment(null, SAMPLE_FRAGMENT));
        assertEquals(SAMPLE_FRAGMENT, UriUtils.setFragment(BLANK_STRING, SAMPLE_FRAGMENT));
        assertThrows(InvalidUriException.class, () -> UriUtils.setFragment(INVALID_URI, SAMPLE_FRAGMENT));
    }

    /**
     * Test for {@link UriUtils#addQueryParam(String, String, String)} method.
     */
    @Test
    void testAddQueryParam() {
        final String paramKey = "some";
        final String paramValue = "value";
        assertEquals(SAMPLE_URL_NO_PATH + SAMPLE_QUERY,
                UriUtils.addQueryParam(SAMPLE_URL_NO_PATH, paramKey, paramValue));
        assertEquals(SAMPLE_URL_NO_PATH + SAMPLE_QUERY,
                UriUtils.addQueryParam(SAMPLE_URL_NO_PATH, paramKey, paramValue));
        assertEquals(SAMPLE_QUERY, UriUtils.addQueryParam(null, paramKey, paramValue));
        assertEquals(SAMPLE_QUERY, UriUtils.addQueryParam(BLANK_STRING, paramKey, paramValue));
        assertThrows(InvalidUriException.class, () -> UriUtils.addQueryParam(INVALID_URI, paramKey, paramValue));

        // Test one that already had query params
        String existingQuery = UriUtils.addQueryParam(SAMPLE_URL, paramKey, paramValue);
        assertTrue(existingQuery.contains("there=was&some=param"));
        assertTrue(existingQuery.contains("some=value"));
    }

    /**
     * Test for {@link UriUtils#addMultiValueQueryParam(String, String, List)} method.
     */
    @Test
    void testAddMultiValueQueryParam() {
        final String paramKey = "some";
        final List<String> paramValue = WrapperList.of("value1", "value2");
        final String expectedQuery = "?some=value1&some=value2";
        assertEquals(SAMPLE_URL_NO_PATH + expectedQuery,
                UriUtils.addMultiValueQueryParam(SAMPLE_URL_NO_PATH, paramKey, paramValue));
        assertEquals(SAMPLE_URL_NO_PATH + expectedQuery,
                UriUtils.addMultiValueQueryParam(SAMPLE_URL_NO_PATH, paramKey, paramValue));
        assertEquals(expectedQuery, UriUtils.addMultiValueQueryParam(null, paramKey, paramValue));
        assertEquals(expectedQuery, UriUtils.addMultiValueQueryParam(BLANK_STRING, paramKey, paramValue));
        assertThrows(InvalidUriException.class, () -> UriUtils.addMultiValueQueryParam(INVALID_URI, paramKey, paramValue));

        // Test one that already had query params
        String existingQuery = UriUtils.addMultiValueQueryParam(SAMPLE_URL, paramKey, paramValue);
        assertTrue(existingQuery.contains("there=was&some=param"));
        assertTrue(existingQuery.contains("some=value1&some=value2"));

        // Test the one that calls the same method passing an array
        existingQuery = UriUtils.addMultiValueQueryParam(SAMPLE_URL, paramKey, paramValue.get(0), paramValue.get(1));
        assertTrue(existingQuery.contains("there=was&some=param"));
        assertTrue(existingQuery.contains("some=value1&some=value2"));
    }

    /**
     * Test for {@link UriUtils#addMultiValueQueryParams(String, Map)} method.
     */
    @Test
    void testAddMultiValueQueryParams() {
        Map<String, List<String>> queryParams = new HashMap<>();
        queryParams.put("some", WrapperList.of("value1", "value2"));
        final String expectedQuery = "?some=value1&some=value2";
        assertEquals(SAMPLE_URL_NO_PATH + expectedQuery,
                UriUtils.addMultiValueQueryParams(SAMPLE_URL_NO_PATH, queryParams));
        assertEquals(SAMPLE_URL_NO_PATH + expectedQuery,
                UriUtils.addMultiValueQueryParams(SAMPLE_URL_NO_PATH, queryParams));
        assertEquals(expectedQuery, UriUtils.addMultiValueQueryParams(null, queryParams));
        assertEquals(expectedQuery, UriUtils.addMultiValueQueryParams(BLANK_STRING, queryParams));
        assertThrows(InvalidUriException.class, () -> UriUtils.addMultiValueQueryParams(INVALID_URI, queryParams));

        // Test one that already had query params
        String existingQuery = UriUtils.addMultiValueQueryParams(SAMPLE_URL, queryParams);
        assertTrue(existingQuery.contains("there=was&some=param"));
        assertTrue(existingQuery.contains("some=value1&some=value2"));
    }

    /**
     * Test for {@link UriUtils#addQueryParams(String, Map)} method.
     */
    @Test
    void testAddQueryParams() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("some", "value");
        assertEquals(SAMPLE_URL_NO_PATH + SAMPLE_QUERY,
                UriUtils.addQueryParams(SAMPLE_URL_NO_PATH, queryParams));
        assertEquals(SAMPLE_URL_NO_PATH + SAMPLE_QUERY,
                UriUtils.addQueryParams(SAMPLE_URL_NO_PATH, queryParams));
        assertEquals(SAMPLE_QUERY, UriUtils.addQueryParams(null, queryParams));
        assertEquals(SAMPLE_QUERY, UriUtils.addQueryParams(BLANK_STRING, queryParams));
        assertThrows(InvalidUriException.class, () -> UriUtils.addQueryParams(INVALID_URI, queryParams));

        // Test one that already had query params
        String existingQuery = UriUtils.addQueryParams(SAMPLE_URL, queryParams);
        assertTrue(existingQuery.contains("there=was&some=param"));
        assertTrue(existingQuery.contains("some=value"));
    }

    /**
     * Test for {@link UriUtils#updateQueryParam(String, String, String)} method.
     */
    @Test
    void testUpdateQueryParams() {
        final String paramKey = "some";
        final String paramValue = "value";
        assertEquals(SAMPLE_URL_NO_PATH + SAMPLE_QUERY,
                UriUtils.updateQueryParam(SAMPLE_URL_NO_PATH, paramKey, paramValue));
        assertEquals(SAMPLE_URL_NO_PATH + SAMPLE_QUERY,
                UriUtils.updateQueryParam(SAMPLE_URL_NO_PATH, paramKey, paramValue));
        assertEquals(SAMPLE_QUERY, UriUtils.updateQueryParam(null, paramKey, paramValue));
        assertEquals(SAMPLE_QUERY, UriUtils.updateQueryParam(BLANK_STRING, paramKey, paramValue));
        assertThrows(InvalidUriException.class, () -> UriUtils.updateQueryParam(INVALID_URI, paramKey, paramValue));

        // Test one that already had query params
        String existingQuery = UriUtils.updateQueryParam(SAMPLE_URL, paramKey, paramValue);
        assertTrue(existingQuery.contains("there=was"));
        assertTrue(existingQuery.contains("some=value"));
        assertFalse(existingQuery.contains("some=param"));
    }

    /**
     * Test for {@link UriUtils#removeProtocol(String)} method.
     */
    @Test
    void testRemoveProtocol() {
        assertEquals(SAMPLE_URL_NO_PROTOCOL, UriUtils.removeProtocol(SAMPLE_URL_NO_PATH));
        assertEquals(SAMPLE_URL.substring(8), UriUtils.removeProtocol(SAMPLE_URL));
        assertEquals(SAMPLE_PATH, UriUtils.removeProtocol(SAMPLE_PATH));
        assertEquals(SAMPLE_QUERY, UriUtils.removeProtocol(SAMPLE_QUERY));
        assertEquals(SAMPLE_FRAGMENT, UriUtils.removeProtocol(SAMPLE_FRAGMENT));
        assertEquals("", UriUtils.removeProtocol(null));
        assertEquals("", UriUtils.removeProtocol(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.removeProtocol(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#removeHost(String)} method.
     */
    @Test
    void testRemoveHost() {
        assertEquals("", UriUtils.removeHost(SAMPLE_URL_NO_PATH));
        assertEquals(SAMPLE_URL.substring(16), UriUtils.removeHost(SAMPLE_URL));
        assertEquals("/" + SAMPLE_PATH, UriUtils.removeHost(SAMPLE_PATH));
        assertEquals(SAMPLE_QUERY, UriUtils.removeHost(SAMPLE_QUERY));
        assertEquals(SAMPLE_FRAGMENT, UriUtils.removeHost(SAMPLE_FRAGMENT));
        assertEquals("", UriUtils.removeHost(null));
        assertEquals("", UriUtils.removeHost(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.removeHost(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#removePort(String)} method.
     */
    @Test
    void testRemovePort() {
        assertEquals(SAMPLE_URL_NO_PATH, UriUtils.removePort(SAMPLE_URL_NO_PATH_WITH_PORT));
        assertEquals(SAMPLE_URL_NO_PATH, UriUtils.removePort(SAMPLE_URL_NO_PATH));
        assertEquals("/" + SAMPLE_PATH, UriUtils.removePort(SAMPLE_PATH));
        assertEquals(SAMPLE_URL, UriUtils.removePort(SAMPLE_URL_NO_PATH_WITH_PORT + "/" + SAMPLE_PATH));
        assertEquals(SAMPLE_QUERY, UriUtils.removePort(SAMPLE_QUERY));
        assertEquals(SAMPLE_FRAGMENT, UriUtils.removePort(SAMPLE_FRAGMENT));
        assertEquals("", UriUtils.removePort(null));
        assertEquals("", UriUtils.removePort(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.removePort(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#removePath(String)} method.
     */
    @Test
    void testRemovePath() {
        assertEquals(SAMPLE_URL_NO_PATH, UriUtils.removePath(SAMPLE_URL_NO_PATH));
        assertEquals("https://test.com?there=was&some=param#andAFragmentToo", UriUtils.removePath(SAMPLE_URL));
        assertEquals("?there=was&some=param#andAFragmentToo", UriUtils.removePath(SAMPLE_PATH));
        assertEquals(SAMPLE_QUERY, UriUtils.removePath(SAMPLE_QUERY));
        assertEquals(SAMPLE_FRAGMENT, UriUtils.removePath(SAMPLE_FRAGMENT));
        assertEquals("", UriUtils.removePath(null));
        assertEquals("", UriUtils.removePath(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.removePath(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#removeQueryParam(String, String)} method.
     */
    @Test
    void testRemoveQueryParam() {
        final String paramKey = "some";
        assertEquals(SAMPLE_URL_NO_PATH, UriUtils.removeQueryParam(SAMPLE_URL_NO_PATH, paramKey));
        assertEquals("https://test.com/some/path?there=was#andAFragmentToo",
                UriUtils.removeQueryParam(SAMPLE_URL, paramKey));
        assertEquals("/some/path?there=was#andAFragmentToo", UriUtils.removeQueryParam(SAMPLE_PATH, paramKey));
        assertEquals("", UriUtils.removeQueryParam(SAMPLE_QUERY, paramKey));
        assertEquals(SAMPLE_FRAGMENT, UriUtils.removeQueryParam(SAMPLE_FRAGMENT, paramKey));
        assertEquals("", UriUtils.removeQueryParam(null, paramKey));
        assertEquals("", UriUtils.removeQueryParam(BLANK_STRING, paramKey));
        assertThrows(InvalidUriException.class, () -> UriUtils.removeQueryParam(INVALID_URI, paramKey));
    }

    /**
     * Test for {@link UriUtils#removeQuery(String)} method.
     */
    @Test
    void testRemoveQuery() {
        assertEquals(SAMPLE_URL_NO_PATH, UriUtils.removeQuery(SAMPLE_URL_NO_PATH));
        assertEquals("https://test.com/some/path#andAFragmentToo",
                UriUtils.removeQuery(SAMPLE_URL));
        assertEquals("/some/path#andAFragmentToo", UriUtils.removeQuery(SAMPLE_PATH));
        assertEquals("", UriUtils.removeQuery(SAMPLE_QUERY));
        assertEquals(SAMPLE_FRAGMENT, UriUtils.removeQuery(SAMPLE_FRAGMENT));
        assertEquals("", UriUtils.removeQuery(null));
        assertEquals("", UriUtils.removeQuery(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.removeQuery(INVALID_URI));
    }

    /**
     * Test for {@link UriUtils#removeFragment(String)} method.
     */
    @Test
    void testRemoveFragment() {
        assertEquals(SAMPLE_URL_NO_PATH, UriUtils.removeFragment(SAMPLE_URL_NO_PATH));
        assertEquals("https://test.com/some/path?there=was&some=param",
                UriUtils.removeFragment(SAMPLE_URL));
        assertEquals("/some/path?there=was&some=param", UriUtils.removeFragment(SAMPLE_PATH));
        assertEquals(SAMPLE_QUERY, UriUtils.removeFragment(SAMPLE_QUERY));
        assertEquals("", UriUtils.removeFragment(SAMPLE_FRAGMENT));
        assertEquals("", UriUtils.removeFragment(null));
        assertEquals("", UriUtils.removeFragment(BLANK_STRING));
        assertThrows(InvalidUriException.class, () -> UriUtils.removeFragment(INVALID_URI));
    }
}
