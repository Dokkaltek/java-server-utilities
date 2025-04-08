package com.github.dokkaltek.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.dokkaltek.exception.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.github.dokkaltek.samples.SamplePojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.github.dokkaltek.util.JsonUtils.convertObjectToBytes;
import static com.github.dokkaltek.util.JsonUtils.convertToJSONString;
import static com.github.dokkaltek.util.JsonUtils.convertToJSONStringOrElse;
import static com.github.dokkaltek.util.JsonUtils.parseByteArray;
import static com.github.dokkaltek.util.JsonUtils.replaceObjectMapper;
import static com.github.dokkaltek.util.ReflectionUtils.getStaticField;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link JsonUtils} class.
 */
class JsonUtilsTest {
    private static final String SAMPLE_JSON_POJO = "{\"description\":null,\"name\":\"John Doe\",\"age\":30}";
    private static final String INVALID_JSON = "{,.";
    private static final Object INVALID_OBJECT = new Object();
    private SamplePojo samplePojo;

    @BeforeEach
    void setup() {
        samplePojo = new SamplePojo();
        samplePojo.setName("John Doe");
        samplePojo.setAge(30);
    }


    /**
     * Test for {@link JsonUtils#replaceObjectMapper(ObjectMapper)} method.
     */
    @Test
    @DisplayName("Test replacing the object mapper")
    void testReplaceObjectMapper() {
        ObjectMapper defaultMapper = getStaticField(JsonUtils.class, "objectMapper");
        ObjectMapper newMapper = new ObjectMapper();
        replaceObjectMapper(newMapper);
        assertNotEquals(defaultMapper, getStaticField(JsonUtils.class, "objectMapper"));
        assertEquals(newMapper, getStaticField(JsonUtils.class, "objectMapper"));
    }

    /**
     * Test for {@link JsonUtils#convertToJSONString(Object)} method.
     */
    @Test
    @DisplayName("Test converting an object to a json string")
    void testConvertToJSONString() {
        assertEquals(SAMPLE_JSON_POJO, convertToJSONString(samplePojo));
        assertNull(convertToJSONString(null));
        samplePojo.setDescription("test");
        assertEquals(SAMPLE_JSON_POJO.replace("null", "\"test\""), convertToJSONString(samplePojo));
        assertThrows(JSONException.class, () -> convertToJSONString(INVALID_OBJECT));
    }

    /**
     * Test for {@link JsonUtils#convertToJSONStringOrElse(Object, String)} method.
     */
    @Test
    @DisplayName("Test converting an object to a json string with default fallback value")
    void testConvertToJSONStringOrElse() {
        String emptyJson = "{}";
        assertEquals(SAMPLE_JSON_POJO, convertToJSONStringOrElse(samplePojo, emptyJson));
        assertEquals(emptyJson, convertToJSONStringOrElse(null, emptyJson));
        samplePojo.setDescription("test");
        assertEquals(SAMPLE_JSON_POJO.replace("null", "\"test\""),
                convertToJSONStringOrElse(samplePojo, emptyJson));
        assertEquals("{}", convertToJSONStringOrElse(INVALID_OBJECT, emptyJson));
    }

    /**
     * Test for {@link JsonUtils#parseJSON(String, Class)} method.
     */
    @Test
    @DisplayName("Test parsing a json string into an object")
    void testParseJSON() {
        assertEquals(samplePojo, JsonUtils.parseJSON(SAMPLE_JSON_POJO, SamplePojo.class));
        assertNull(JsonUtils.parseJSON(null, SamplePojo.class));
        assertThrows(JSONException.class, () -> JsonUtils.parseJSON(INVALID_JSON, SamplePojo.class));
    }

    /**
     * Test for {@link JsonUtils#parseJSONOrElse(String, Class, Object)} method.
     */
    @Test
    @DisplayName("Test parsing a json string into an object")
    void testParseJSONOrElse() {
        SamplePojo defaultValue = new SamplePojo();
        assertEquals(samplePojo, JsonUtils.parseJSONOrElse(SAMPLE_JSON_POJO, SamplePojo.class, defaultValue));
        assertEquals(defaultValue, JsonUtils.parseJSONOrElse(null, SamplePojo.class, defaultValue));
        assertEquals(defaultValue, JsonUtils.parseJSONOrElse(INVALID_JSON, SamplePojo.class, defaultValue));
    }

    /**
     * Test for {@link JsonUtils#convertObjectToBytes(Object)} method.
     */
    @Test
    @DisplayName("Test converting an object to a byte array")
    void testConvertObjectToBytes() {
        assertDoesNotThrow(() -> JsonUtils.convertObjectToBytes(samplePojo));
        assertTrue(JsonUtils.convertObjectToBytes(samplePojo).length > 0);
        assertEquals(0, JsonUtils.convertObjectToBytes(null).length);
        assertThrows(JSONException.class, () -> convertObjectToBytes(INVALID_OBJECT));
    }

    /**
     * Test for {@link JsonUtils#convertJSONToMap(String)} method.
     */
    @Test
    @DisplayName("Test converting a json string to a map")
    void testConvertJSONToMap() {
        Map<String, Object> sampleMap = new HashMap<>();
        sampleMap.put("description", null);
        sampleMap.put("name", "John Doe");
        sampleMap.put("age", 30);
        assertEquals(sampleMap, JsonUtils.convertJSONToMap(SAMPLE_JSON_POJO));
        assertTrue(JsonUtils.convertJSONToMap(null).isEmpty());
        assertThrows(JSONException.class, () -> JsonUtils.validateJSONWithEx(INVALID_JSON));
    }

    /**
     * Test for {@link JsonUtils#convertObjectToMap(Object)} method.
     */
    @Test
    @DisplayName("Test converting an object to a map")
    void testConvertObjectToMap() {
        Map<String, Object> sampleMap = new HashMap<>();
        sampleMap.put("description", null);
        sampleMap.put("name", "John Doe");
        sampleMap.put("age", 30);
        assertEquals(sampleMap, JsonUtils.convertObjectToMap(samplePojo));
        assertTrue(JsonUtils.convertObjectToMap(null).isEmpty());
        assertThrows(JSONException.class, () -> JsonUtils.convertObjectToMap(INVALID_OBJECT));
    }

    /**
     * Test for {@link JsonUtils#parseByteArray(byte[], Class)} method.
     */
    @Test
    @DisplayName("Test parsing a byte array into an object")
    void testParseByteArray() {
        byte[] sampleBytes = JsonUtils.convertObjectToBytes(samplePojo);
        assertEquals(samplePojo, parseByteArray(sampleBytes, SamplePojo.class));
        assertNull(parseByteArray(null, SamplePojo.class));
        assertNull(parseByteArray(new byte[]{}, SamplePojo.class));
        assertThrows(JSONException.class, () -> parseByteArray(new byte[]{1, 2, 3}, Object.class));
    }

    /**
     * Test for {@link JsonUtils#readJSON(String)} method.
     */
    @Test
    @DisplayName("Test reading a json into a JsonNode")
    void testReadJSON() {
        JsonNode result = Objects.requireNonNull(JsonUtils.readJSON(SAMPLE_JSON_POJO));
        assertEquals(samplePojo.getAge(), result.get("age").asInt());
        assertEquals(samplePojo.getName(), result.get("name").asText());
        assertNull(JsonUtils.readJSON(null));
        assertThrows(JSONException.class, () -> JsonUtils.readJSON(INVALID_JSON));
    }

    /**
     * Test for {@link JsonUtils#readJSONArray(String)} method.
     */
    @Test
    @DisplayName("Test reading a json array into an ArrayNode")
    void testReadJSONArray() {
        ArrayNode result = Objects.requireNonNull(JsonUtils.readJSONArray("[" + SAMPLE_JSON_POJO + "]"));
        assertEquals(samplePojo.getAge(), result.get(0).get("age").asInt());
        assertEquals(samplePojo.getName(), result.get(0).get("name").asText());
        assertNull(JsonUtils.readJSONArray(null));
        assertThrows(JSONException.class, () -> JsonUtils.readJSONArray(INVALID_JSON));
    }

    /**
     * Test for {@link JsonUtils#deepCopy(Object)} method.
     */
    @Test
    @DisplayName("Test deep copying an object")
    void testDeepCopy() {
        SamplePojo deepCopy = JsonUtils.deepCopy(samplePojo);
        assertEquals(samplePojo, deepCopy);
        deepCopy.setDescription("test");
        assertNotEquals(samplePojo.getDescription(), deepCopy.getDescription());
        assertNull(JsonUtils.deepCopy(null));
    }
}
