package io.github.dokkaltek.util;

import io.github.dokkaltek.exception.JSONException;
import io.github.dokkaltek.samples.SamplePojo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.github.dokkaltek.util.JsonUtils.convertObjectToBytes;
import static io.github.dokkaltek.util.JsonUtils.convertToJSONString;
import static io.github.dokkaltek.util.JsonUtils.convertToJSONStringOrElse;
import static io.github.dokkaltek.util.JsonUtils.setObjectMapperInstance;
import static io.github.dokkaltek.util.ReflectionUtils.getStaticField;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link JsonUtils} class.
 */
class JsonUtilsTest {
    private static final String SAMPLE_JSON_POJO = "{\"description\":null,\"name\":\"John Doe\",\"age\":30}";
    private static final String INVALID_JSON = "{,.";
    private static final Object INVALID_OBJECT = new Object();
    private SamplePojo samplePojo;

    @BeforeEach
    void setUp() {
        samplePojo = new SamplePojo();
        samplePojo.setName("John Doe");
        samplePojo.setAge(30);
    }

    @AfterEach
    void cleanUp() {
        setObjectMapperInstance(ReflectionUtils.invokeStaticMethod(JsonUtils.class, "initObjectMapper"));
    }


    /**
     * Test for {@link JsonUtils#setObjectMapperInstance(ObjectMapper)} method.
     */
    @Test
    @DisplayName("Test replacing the object mapper")
    void testSetObjectMapperInstance() {
        ObjectMapper defaultMapper = getStaticField(JsonUtils.class, "objectMapper");
        ObjectMapper newMapper = new ObjectMapper();
        setObjectMapperInstance(newMapper);
        assertNotEquals(defaultMapper, getStaticField(JsonUtils.class, "objectMapper"));
        assertEquals(newMapper, getStaticField(JsonUtils.class, "objectMapper"));
    }

    /**
     * Test for {@link JsonUtils#getObjectMapperInstance()} method.
     */
    @Test
    @DisplayName("Test replacing the object mapper")
    void testGetObjectMapperInstance() {
        ObjectMapper defaultMapper = getStaticField(JsonUtils.class, "objectMapper");
        ObjectMapper instanceMapper = JsonUtils.getObjectMapperInstance();
        assertEquals(defaultMapper, instanceMapper);
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

        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsString(any(Object.class))).thenThrow(JacksonException.class);
        setObjectMapperInstance(objectMapper);
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

        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsBytes(any(Object.class))).thenThrow(JacksonException.class);
        setObjectMapperInstance(objectMapper);
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

        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.convertValue(any(Object.class), any(TypeReference.class)))
                .thenThrow(IllegalArgumentException.class);
        setObjectMapperInstance(objectMapper);
        assertThrows(JSONException.class, () -> JsonUtils.convertObjectToMap(INVALID_OBJECT));
    }

    /**
     * Test for {@link JsonUtils#convertJSONToList(String)} method.
     */
    @Test
    @DisplayName("Test converting a json string to a list")
    void testConvertJSONToList() {
        List<String> sampleList = new ArrayList<>();
        sampleList.add("test");
        sampleList.add("test2");
        assertEquals(sampleList, JsonUtils.convertJSONToList("[\"test\", \"test2\"]"));
        assertTrue(JsonUtils.convertJSONToList(null).isEmpty());
        assertThrows(JSONException.class, () -> JsonUtils.convertJSONToList(SAMPLE_JSON_POJO));
    }

    /**
     * Test for {@link JsonUtils#convertJSONToParametrizedType(String, TypeReference)}  method.
     */
    @Test
    @DisplayName("Test converting a json string to a parametrized type")
    void testConvertJSONToParametrizedType() {
        List<String> sampleList = new ArrayList<>();
        sampleList.add("test");
        sampleList.add("test2");
        TypeReference<ArrayList<String>> typeRef = new TypeReference<>(){};
        String sampleInput = "[\"test\", \"test2\"]";
        assertEquals(sampleList, JsonUtils.convertJSONToParametrizedType(sampleInput, typeRef));
        assertNull(JsonUtils.convertJSONToParametrizedType(null, typeRef));
        assertThrows(JSONException.class, () -> JsonUtils.convertJSONToParametrizedType(SAMPLE_JSON_POJO, typeRef));
    }

    /**
     * Test for {@link JsonUtils#parseByteArray(byte[], Class)} method.
     */
    @Test
    @DisplayName("Test parsing a byte array into an object")
    void testParseByteArray() {
        byte[] sampleBytes = JsonUtils.convertObjectToBytes(samplePojo);
        assertEquals(samplePojo, JsonUtils.parseByteArray(sampleBytes, SamplePojo.class));
        assertNull(JsonUtils.parseByteArray(null, SamplePojo.class));
        assertNull(JsonUtils.parseByteArray(new byte[]{}, SamplePojo.class));
        assertThrows(JSONException.class, () -> JsonUtils.parseByteArray(new byte[]{1, 2, 3}, Object.class));
    }

    /**
     * Test for {@link JsonUtils#parseByteArrayOrElse(byte[], Class, Object)} method.
     */
    @Test
    @DisplayName("Test parsing a byte array into an object with a default value")
    void testParseByteArrayOrElse() {
        byte[] sampleBytes = JsonUtils.convertObjectToBytes(samplePojo);
        assertEquals(samplePojo, JsonUtils.parseByteArrayOrElse(sampleBytes, SamplePojo.class, null));
        assertNull(JsonUtils.parseByteArrayOrElse(null, SamplePojo.class, null));
        assertNull(JsonUtils.parseByteArrayOrElse(new byte[]{}, SamplePojo.class, null));
        assertNull(JsonUtils.parseByteArrayOrElse(new byte[]{1, 2, 3}, Object.class, null));
    }

    /**
     * Test for {@link JsonUtils#parseByteArrayToList(byte[])} method.
     */
    @Test
    @DisplayName("Test parsing a byte array into a list")
    void testParseByteArrayToList() {
        List<Integer> sampleList = List.of(1);
        byte[] sampleBytes = JsonUtils.convertObjectToBytes(sampleList);
        List<Integer> result = JsonUtils.parseByteArrayToList(sampleBytes);
        assertEquals(sampleList, result);
        assertEquals(Collections.emptyList(), JsonUtils.parseByteArrayToList(null));
        assertThrows(JSONException.class, () -> JsonUtils.parseByteArrayToList(new byte[]{1, 2, 3}));
    }

    /**
     * Test for {@link JsonUtils#parseByteArrayToMap(byte[])} method.
     */
    @Test
    @DisplayName("Test parsing a byte array into a map")
    void testParseByteArrayToMap() {
        Map<String, Integer> sampleMap = Map.of("Test", 1);
        byte[] sampleBytes = JsonUtils.convertObjectToBytes(sampleMap);
        Map<String, Integer> result = JsonUtils.parseByteArrayToMap(sampleBytes);
        assertEquals(sampleMap, result);
        assertEquals(Collections.emptyMap(), JsonUtils.parseByteArrayToMap(null));
        assertThrows(JSONException.class, () -> JsonUtils.parseByteArrayToMap(new byte[]{1, 2, 3}));
    }

    /**
     * Test for {@link JsonUtils#parseByteArrayToParametrizedType(byte[], TypeReference)} method.
     */
    @Test
    @DisplayName("Test parsing a byte array into a parametrized type")
    void testParseByteArrayToParametrizedType() {
        List<Integer> sampleList = List.of(1);
        byte[] sampleBytes = JsonUtils.convertObjectToBytes(sampleList);
        TypeReference<List<Integer>> typeRef = new TypeReference<>() {};
        List<Integer> result = JsonUtils.parseByteArrayToParametrizedType(sampleBytes, typeRef);
        assertEquals(sampleList, result);
        assertNull(JsonUtils.parseByteArrayToParametrizedType(null, typeRef));
        assertThrows(JSONException.class, () -> JsonUtils.parseByteArrayToParametrizedType(new byte[]{1, 2, 3}, typeRef));
    }

    /**
     * Test for {@link JsonUtils#parseInputStream(InputStream, Class)} method.
     */
    @Test
    @DisplayName("Test parsing an input stream into an object")
    void testParseInputStream() {
        InputStream sampleStream = new ByteArrayInputStream(JsonUtils.convertObjectToBytes(samplePojo));
        assertEquals(samplePojo, JsonUtils.parseInputStream(sampleStream, SamplePojo.class));
        assertNull(JsonUtils.parseInputStream(null, SamplePojo.class));
        InputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);
        assertThrows(JSONException.class, () -> JsonUtils.parseInputStream(emptyInputStream, SamplePojo.class));
    }

    /**
     * Test for {@link JsonUtils#parseInputStreamOrElse(InputStream, Class, Object)} method.
     */
    @Test
    @DisplayName("Test parsing an input stream into an object with a default value")
    void testParseInputStreamOrElse() {
        InputStream sampleStream = new ByteArrayInputStream(JsonUtils.convertObjectToBytes(samplePojo));
        assertEquals(samplePojo, JsonUtils.parseInputStreamOrElse(sampleStream, SamplePojo.class, null));
        assertEquals(samplePojo, JsonUtils.parseInputStreamOrElse(null, SamplePojo.class, samplePojo));
        InputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);
        assertNull(JsonUtils.parseInputStreamOrElse(emptyInputStream, SamplePojo.class, null));
    }

    /**
     * Test for {@link JsonUtils#parseInputStreamToList(InputStream)} method.
     */
    @Test
    @DisplayName("Test parsing an input stream into a list")
    void testParseInputStreamToList() {
        List<Integer> sampleList = List.of(1);
        InputStream sampleStream = new ByteArrayInputStream(JsonUtils.convertObjectToBytes(sampleList));
        assertEquals(sampleList, JsonUtils.parseInputStreamToList(sampleStream));
        assertEquals(Collections.emptyList(), JsonUtils.parseInputStreamToList(null));
        InputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);
        assertThrows(JSONException.class, () -> JsonUtils.parseInputStreamToList(emptyInputStream));
    }

    /**
     * Test for {@link JsonUtils#parseInputStreamToMap(InputStream)} method.
     */
    @Test
    @DisplayName("Test parsing an input stream into a map")
    void testParseInputStreamToMap() {
        Map<String, Integer> sampleMap = Map.of("test", 1);
        InputStream sampleStream = new ByteArrayInputStream(JsonUtils.convertObjectToBytes(sampleMap));
        assertEquals(sampleMap, JsonUtils.parseInputStreamToMap(sampleStream));
        assertEquals(Collections.emptyMap(), JsonUtils.parseInputStreamToMap(null));
        InputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);
        assertThrows(JSONException.class, () -> JsonUtils.parseInputStreamToMap(emptyInputStream));
    }

    /**
     * Test for {@link JsonUtils#parseInputStreamToParametrizedType(InputStream, TypeReference)} method.
     */
    @Test
    @DisplayName("Test parsing an input stream into a parametrized type")
    void testParseInputStreamToParametrizedType() {
        List<Integer> sampleList = List.of(1);
        InputStream sampleStream = new ByteArrayInputStream(JsonUtils.convertObjectToBytes(sampleList));
        TypeReference<List<Integer>> typeRef = new TypeReference<>() {};
        assertEquals(sampleList, JsonUtils.parseInputStreamToParametrizedType(sampleStream, typeRef));
        assertNull(JsonUtils.parseInputStreamToParametrizedType(null, typeRef));
        InputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);
        assertThrows(JSONException.class,
                () -> JsonUtils.parseInputStreamToParametrizedType(emptyInputStream, typeRef));
    }

    /**
     * Test for {@link JsonUtils#readJSON(String)} method.
     */
    @Test
    @DisplayName("Test reading a json into a JsonNode")
    void testReadJSONFromString() {
        JsonNode result = Objects.requireNonNull(JsonUtils.readJSON(SAMPLE_JSON_POJO));
        assertEquals(samplePojo.getAge(), result.get("age").asInt());
        assertEquals(samplePojo.getName(), result.get("name").asString());
        assertNull(JsonUtils.readJSON((String) null));
        assertThrows(JSONException.class, () -> JsonUtils.readJSON(INVALID_JSON));
    }

    /**
     * Test for {@link JsonUtils#readJSON(byte[])} method.
     */
    @Test
    @DisplayName("Test reading a byte array into a JsonNode")
    void testReadJSONFromByteArray() {
        JsonNode result = Objects.requireNonNull(JsonUtils.readJSON(SAMPLE_JSON_POJO.getBytes(StandardCharsets.UTF_8)));
        assertEquals(samplePojo.getAge(), result.get("age").asInt());
        assertEquals(samplePojo.getName(), result.get("name").asString());
        assertNull(JsonUtils.readJSON(new byte[0]));
        byte[] invalidBytes = INVALID_JSON.getBytes(StandardCharsets.UTF_8);
        assertThrows(JSONException.class, () -> JsonUtils.readJSON(invalidBytes));
    }

    /**
     * Test for {@link JsonUtils#readJSON(InputStream)} method.
     */
    @Test
    @DisplayName("Test reading an InputStream into a JsonNode")
    void testReadJSONFromInputStream() {
        InputStream sampleStream = new ByteArrayInputStream(SAMPLE_JSON_POJO.getBytes(StandardCharsets.UTF_8));
        JsonNode result = Objects.requireNonNull(JsonUtils.readJSON(sampleStream));
        assertEquals(samplePojo.getAge(), result.get("age").asInt());
        assertEquals(samplePojo.getName(), result.get("name").asString());
        assertNull(JsonUtils.readJSON((InputStream) null));
        InputStream invalidInputStream = new ByteArrayInputStream(INVALID_JSON.getBytes(StandardCharsets.UTF_8));
        assertThrows(JSONException.class, () -> JsonUtils.readJSON(invalidInputStream));
    }

    /**
     * Test for {@link JsonUtils#readJSONArray(String)} method.
     */
    @Test
    @DisplayName("Test reading a json array into an ArrayNode")
    void testReadJSONArrayFromString() {
        ArrayNode result = Objects.requireNonNull(JsonUtils.readJSONArray("[" + SAMPLE_JSON_POJO + "]"));
        assertEquals(samplePojo.getAge(), result.get(0).get("age").asInt());
        assertEquals(samplePojo.getName(), result.get(0).get("name").asString());
        assertNull(JsonUtils.readJSONArray((String) null));
        assertThrows(JSONException.class, () -> JsonUtils.readJSONArray(INVALID_JSON));
    }

    /**
     * Test for {@link JsonUtils#readJSONArray(byte[])} method.
     */
    @Test
    @DisplayName("Test reading a byte array into an ArrayNode")
    void testReadJSONArrayFromByteArray() {
        String sampleArray = "[" + SAMPLE_JSON_POJO + "]";
        ArrayNode result = JsonUtils.readJSONArray(sampleArray.getBytes(StandardCharsets.UTF_8));
        assertEquals(samplePojo.getAge(), result.get(0).get("age").asInt());
        assertEquals(samplePojo.getName(), result.get(0).get("name").asString());
        assertNull(JsonUtils.readJSONArray(new byte[0]));
        byte[] invalidBytes = INVALID_JSON.getBytes(StandardCharsets.UTF_8);
        assertThrows(JSONException.class, () -> JsonUtils.readJSONArray(invalidBytes));
    }

    /**
     * Test for {@link JsonUtils#readJSONArray(InputStream)} method.
     */
    @Test
    @DisplayName("Test reading an InputStream into an ArrayNode")
    void testReadJSONArrayFromInputStream() {
        String sampleArray = "[" + SAMPLE_JSON_POJO + "]";
        InputStream sampleStream = new ByteArrayInputStream(sampleArray.getBytes(StandardCharsets.UTF_8));
        ArrayNode result = JsonUtils.readJSONArray(sampleStream);
        assertEquals(samplePojo.getAge(), result.get(0).get("age").asInt());
        assertEquals(samplePojo.getName(), result.get(0).get("name").asString());
        assertNull(JsonUtils.readJSONArray(new byte[0]));
        InputStream invalidStream = new ByteArrayInputStream(INVALID_JSON.getBytes(StandardCharsets.UTF_8));
        assertThrows(JSONException.class, () -> JsonUtils.readJSONArray(invalidStream));
    }

    /**
     * Test for {@link JsonUtils#createObjectNode()} method.
     */
    @Test
    @DisplayName("Test creating an empty ObjectNode")
    void testCreateObjectNode() {
        assertNotNull(JsonUtils.createObjectNode());
    }

    /**
     * Test for {@link JsonUtils#createArrayNode()} method.
     */
    @Test
    @DisplayName("Test creating an empty ArrayNode")
    void testCreateArrayNode() {
        assertNotNull(JsonUtils.createArrayNode());
    }

    /**
     * Test for {@link JsonUtils#getFieldAsString(JsonNode, String)} method.
     */
    @Test
    @DisplayName("Test getting a field of a json as a string")
    void testGetFieldAsString() {
        JsonNode result = Objects.requireNonNull(JsonUtils.readJSON(SAMPLE_JSON_POJO));
        assertEquals(samplePojo.getName(), JsonUtils.getFieldAsString(result, "name"));
        assertNull(JsonUtils.getFieldAsString(result, "invalidField"));
        assertNull(JsonUtils.getFieldAsString(null, "name"));
    }

    /**
     * Test for {@link JsonUtils#getFieldAsInt(JsonNode, String)} method.
     */
    @Test
    @DisplayName("Test getting a field of a json as an int")
    void testGetFieldAsInt() {
        JsonNode result = Objects.requireNonNull(JsonUtils.readJSON(SAMPLE_JSON_POJO));
        assertEquals(samplePojo.getAge(), JsonUtils.getFieldAsInt(result, "age"));
        assertNull(JsonUtils.getFieldAsInt(result, "invalidField"));
        assertNull(JsonUtils.getFieldAsInt(null, "age"));
    }

    /**
     * Test for {@link JsonUtils#getFieldAsDouble(JsonNode, String)} method.
     */
    @Test
    @DisplayName("Test getting a field of a json as a double")
    void testGetFieldAsDouble() {
        JsonNode result = Objects.requireNonNull(JsonUtils.readJSON(SAMPLE_JSON_POJO));
        assertEquals(samplePojo.getAge(), JsonUtils.getFieldAsDouble(result, "age"));
        assertNull(JsonUtils.getFieldAsDouble(result, "invalidField"));
        assertNull(JsonUtils.getFieldAsDouble(null, "age"));
    }

    /**
     * Test for {@link JsonUtils#getFieldAsBoolean(JsonNode, String)} method.
     */
    @Test
    @DisplayName("Test getting a field of a json as a boolean")
    void testGetFieldAsBoolean() {
        JsonNode result = Objects.requireNonNull(JsonUtils.readJSON("{\"isTrue\": true}"));
        assertTrue(JsonUtils.getFieldAsBoolean(result, "isTrue"));
        assertNull(JsonUtils.getFieldAsBoolean(result, "invalidField"));
        assertNull(JsonUtils.getFieldAsBoolean(null, "isTrue"));
    }

    /**
     * Test for {@link JsonUtils#getFieldAsLong(JsonNode, String)} method.
     */
    @Test
    @DisplayName("Test getting a field of a json as a long")
    void testGetFieldAsLong() {
        JsonNode result = Objects.requireNonNull(JsonUtils.readJSON(SAMPLE_JSON_POJO));
        assertEquals(samplePojo.getAge(), JsonUtils.getFieldAsLong(result, "age"));
        assertNull(JsonUtils.getFieldAsLong(result, "invalidField"));
        assertNull(JsonUtils.getFieldAsLong(null, "age"));
    }

    /**
     * Test for {@link JsonUtils#getFieldAsOptional(JsonNode, String)} method.
     */
    @Test
    @DisplayName("Test getting a field of a json as a optional")
    void testGetFieldAsOptional() {
        JsonNode result = Objects.requireNonNull(JsonUtils.readJSON(SAMPLE_JSON_POJO));
        assertTrue(JsonUtils.getFieldAsOptional(result, "age").isPresent());
        assertFalse(JsonUtils.getFieldAsOptional(result, "invalidField").isPresent());
        assertFalse(JsonUtils.getFieldAsOptional(null, "description").isPresent());
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
