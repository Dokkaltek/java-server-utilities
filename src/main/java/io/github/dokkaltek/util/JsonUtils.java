package io.github.dokkaltek.util;


import io.github.dokkaltek.exception.InvalidInputException;
import io.github.dokkaltek.exception.JSONException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import tools.jackson.core.JacksonException;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.util.TokenBuffer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.github.dokkaltek.util.StringUtils.isBlankOrNull;

/**
 * Utility class to perform operations related to json strings.
 */
@Log
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {
    private static ObjectMapper objectMapper = initObjectMapper();

    /**
     * Changes the current {@link ObjectMapper} used for json operations for another one.
     * @param objectMapper The new object mapper to use for json operations.
     */
    public static void setObjectMapperInstance(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }

    /**
     * Gets the {@link ObjectMapper} instance used by this class.
     * @return The {@link ObjectMapper} instance used by this class.
     */
    public static ObjectMapper getObjectMapperInstance() {
        return objectMapper;
    }

    /**
     * Validates that a given json string is valid.
     * @param json The json string to validate.
     * @return True if the json string is valid, false otherwise.
     */
    public static boolean validateJSON(String json) {
        if (isBlankOrNull(json))
            return false;

        try {
            objectMapper.readTree(json);
            return true;
        } catch (JacksonException e) {
            return false;
        }
    }

    /**
     * Validates that a given json string is valid and throws an exception otherwise.
     * @param json The json string to validate.
     * @throws JSONException If the json string is not valid.
     */
    public static void validateJSONWithEx(String json) {
        if (isBlankOrNull(json))
            throw new InvalidInputException("The given JSON was null or empty.");

        try {
            objectMapper.readTree(json);
        } catch (JacksonException | IllegalArgumentException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts an object to a json string, unless the object is null, in which case it returns null.
     * @param object The object to convert.
     * @return The json string representation of the object.
     * @throws JSONException If the object cannot be converted to a json string.
     */
    public static <T> String convertToJSONString(T object) {
        if (object == null)
            return null;
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts an object to a json string, unless the object is null, or an error occurs, in which case the default
     * value will be returned.
     * @param object The object to convert.
     * @param defaultValue The default value to return in case the object is null or an error occurs.
     * @return The json string representation of the object.
     */
    public static <T> String convertToJSONStringOrElse(T object, String defaultValue) {
        if (object == null)
            return defaultValue;
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JacksonException e) {
            log.info(String.format("Error converting object to json string, returning default value '%s' instead.",
                    defaultValue));
            return defaultValue;
        }
    }

    /**
     * Converts a json string to the given class object.
     * @param json The json string to convert.
     * @param clazz The class to convert the json string to.
     * @return The object representation of the json string.
     * @param <T> The type of the object to convert the json string to.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> T parseJSON(String json, Class<T> clazz) {
        if (isBlankOrNull(json))
            return null;
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts a json string to the given class object unless the json string is null, empty, or not valid, in which
     * case it will return the default value.
     * @param json The json string to convert.
     * @param clazz The class to convert the json string to.
     * @param defaultValue The default value to use in case the json string is null, empty, or not valid.
     * @return The object representation of the json string.
     * @param <T> The type of the object to convert the json string to.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> T parseJSONOrElse(String json, Class<T> clazz, T defaultValue) {
        if (isBlankOrNull(json))
            return defaultValue;

        try {
            return objectMapper.readValue(json, clazz);
        } catch (JacksonException e) {
            log.info(String.format("Error converting json string to object, returning default value '%s' instead.",
                    defaultValue));
            return defaultValue;
        }
    }

    /**
     * Converts an object to a byte array. If the object is null it returns an empty byte array.
     * @param json The object to convert.
     * @return The byte array representation of the object.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> byte[] convertObjectToBytes(T json) {
        if (json == null)
            return new byte[]{};
        try {
            return objectMapper.writeValueAsBytes(json);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts a json string to a map.
     * @param json The json string to convert.
     * @return The map representation of the json string.
     */
    public static <T> Map<String, T> convertJSONToMap(String json) {
        if (isBlankOrNull(json))
            return Collections.emptyMap();
        try {
            TypeReference<HashMap<String, T>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(json, typeRef);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts a json string to a map.
     * @param object The json string to convert.
     * @return The map representation of the json string.
     */
    public static <T, I> Map<String, T> convertObjectToMap(I object) {
        if (object == null)
            return Collections.emptyMap();
        try {
            TypeReference<HashMap<String, T>> typeRef = new TypeReference<>() {};
            return objectMapper.convertValue(object, typeRef);
        } catch (IllegalArgumentException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts a json string to a list of a type.
     * @param json The json string to convert.
     * @return The list representation of the json string.
     */
    public static <T> List<T> convertJSONToList(String json) {
        if (isBlankOrNull(json))
            return Collections.emptyList();
        try {
            TypeReference<ArrayList<T>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(json, typeRef);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts a json string to an object of a parametrized type.
     * @param json The json string to convert.
     * @param typeRef the {@link TypeReference} to use.
     * @return The parametrized type representation of the json string.
     */
    public static <T> T convertJSONToParametrizedType(String json, TypeReference<T> typeRef) {
        if (isBlankOrNull(json))
            return null;
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }


    /**
     * Converts a byte array to an object. If the byte array is null or empty it returns null.
     * @param byteArray The byte array to convert.
     * @param clazz The class to convert the byte array to.
     * @return The instance of the class from the byte array.
     * @param <T> The class type of the return object.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> T parseByteArray(byte[] byteArray, Class<T> clazz) {
        if (byteArray == null || byteArray.length == 0)
            return null;
        try {
            return objectMapper.readValue(byteArray, clazz);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts a byte array to an object. If the byte array is null, empty, or invalid, it returns the default value.
     * @param byteArray The byte array to convert.
     * @param clazz The class to convert the byte array to.
     * @return The instance of the class from the byte array.
     * @param <T> The class type of the return object.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> T parseByteArrayOrElse(byte[] byteArray, Class<T> clazz, T defaultValue) {
        if (byteArray == null || byteArray.length == 0)
            return defaultValue;
        try {
            return objectMapper.readValue(byteArray, clazz);
        } catch (JacksonException e) {
            log.info(String.format("Error converting json bytes to object, returning default value '%s' instead.",
                    defaultValue));
            return defaultValue;
        }
    }

    /**
     * Converts a byte array to a list of the return type. If the byte array is null or empty it returns an empty list.
     * @param byteArray The byte array to convert.
     * @return The instance of the class from the byte array.
     * @param <T> The class type of the return object.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> List<T> parseByteArrayToList(byte[] byteArray) {
        if (byteArray == null || byteArray.length == 0)
            return Collections.emptyList();
        try {
            TypeReference<ArrayList<T>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(byteArray, typeRef);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts a byte array to a map of the return type. If the byte array is null or empty it returns an empty map.
     * @param byteArray The byte array to convert.
     * @return The instance of the class from the byte array.
     * @param <T> The class type of the return object.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> Map<String, T> parseByteArrayToMap(byte[] byteArray) {
        if (byteArray == null || byteArray.length == 0)
            return Collections.emptyMap();
        try {
            TypeReference<HashMap<String, T>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(byteArray, typeRef);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts a byte array to a parametrized object. If the byte array is null or empty it returns null.
     * @param byteArray The byte array to convert.
     * @param typeRef the {@link TypeReference} to use.
     * @return The instance of the class from the byte array.
     * @param <T> The class type of the return object.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> T parseByteArrayToParametrizedType(byte[] byteArray, TypeReference<T> typeRef) {
        if (byteArray == null || byteArray.length == 0)
            return null;
        try {
            return objectMapper.readValue(byteArray, typeRef);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts an input stream to an object. If the input stream is null or empty it returns null.
     * @param stream The input stream to convert.
     * @param clazz The class to convert the input stream to.
     * @return The instance of the class from the input stream.
     * @param <T> The class type of the return object.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> T parseInputStream(InputStream stream, Class<T> clazz) {
        if (stream == null)
            return null;
        try {
            return objectMapper.readValue(stream, clazz);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts an input stream to an object. If the input stream is null, empty, or invalid, it returns the default.
     * @param stream The input stream to convert.
     * @param clazz The class to convert the input stream to.
     * @param defaultValue The value to return in case of null or error.
     * @return The instance of the class from the input stream.
     * @param <T> The class type of the return object.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> T parseInputStreamOrElse(InputStream stream, Class<T> clazz, T defaultValue) {
        if (stream == null)
            return defaultValue;
        try {
            return objectMapper.readValue(stream, clazz);
        } catch (JacksonException e) {
            log.info(String.format("Error converting json input stream to object, " +
                            "returning default value '%s' instead.", defaultValue));
            return defaultValue;
        }
    }

    /**
     * Converts an input stream to a list. If the input stream is null it returns an empty list.
     * @param stream The input stream to convert.
     * @return The instance of the class from the input stream.
     * @param <T> The class type of the return object.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> List<T> parseInputStreamToList(InputStream stream) {
        if (stream == null)
            return Collections.emptyList();
        try {
            TypeReference<ArrayList<T>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(stream, typeRef);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts an input stream to a map. If the input stream is null it returns an empty map.
     * @param stream The input stream to convert.
     * @return The instance of the class from the input stream.
     * @param <T> The class type of the return object.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> Map<String, T> parseInputStreamToMap(InputStream stream) {
        if (stream == null)
            return Collections.emptyMap();
        try {
            TypeReference<HashMap<String, T>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(stream, typeRef);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts an input stream to a parametrized type. If the input stream is null it returns null.
     * @param stream The input stream to convert.
     * @return The instance of the class from the input stream.
     * @param <T> The class type of the return object.
     * @throws JSONException If the object cannot be converted.
     */
    public static <T> T parseInputStreamToParametrizedType(InputStream stream, TypeReference<T> typeRef) {
        if (stream == null)
            return null;
        try {
            return objectMapper.readValue(stream, typeRef);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Reads a json and converts it into a {@link JsonNode}. It returns null if the json is null or empty.
     * You can then read any field using the methods of the {@link JsonNode} class, like:
     * <ul>
     *     <li>{@link JsonNode#get(String)}</li> -> Gets one value directly at the highest level of the json.
     *     <li>{@link JsonNode#get(String)}.asText()</li> -> Gets a value as a certain type.
     *     <li>{@link JsonNode#findValue(String)}</li> -> Finds the first occurrence of a value in the json.
     *     <li>{@link JsonNode#findValues(String)}</li> -> Finds all occurrences of a value in the json.
     * </ul>
     *
     * @param json The json string to read.
     * @return The {@link JsonNode} representation.
     */
    public static JsonNode readJSON(String json) {
        if (isBlankOrNull(json))
            return null;

        try {
            return objectMapper.readTree(json);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Reads a json and converts it into a {@link JsonNode}. It returns null if the json is null or empty.
     * You can then read any field using the methods of the {@link JsonNode} class, like:
     * <ul>
     *     <li>{@link JsonNode#get(String)}</li> -> Gets one value directly at the highest level of the json.
     *     <li>{@link JsonNode#get(String)}.asText()</li> -> Gets a value as a certain type.
     *     <li>{@link JsonNode#findValue(String)}</li> -> Finds the first occurrence of a value in the json.
     *     <li>{@link JsonNode#findValues(String)}</li> -> Finds all occurrences of a value in the json.
     * </ul>
     *
     * @param jsonBytes The json bytes to read.
     * @return The {@link JsonNode} representation.
     */
    public static JsonNode readJSON(byte[] jsonBytes) {
        if (jsonBytes == null || jsonBytes.length == 0)
            return null;

        try {
            return objectMapper.readTree(jsonBytes);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Reads a json and converts it into a {@link JsonNode}. It returns null if the json is null or empty.
     * You can then read any field using the methods of the {@link JsonNode} class, like:
     * <ul>
     *     <li>{@link JsonNode#get(String)}</li> -> Gets one value directly at the highest level of the json.
     *     <li>{@link JsonNode#get(String)}.asText()</li> -> Gets a value as a certain type.
     *     <li>{@link JsonNode#findValue(String)}</li> -> Finds the first occurrence of a value in the json.
     *     <li>{@link JsonNode#findValues(String)}</li> -> Finds all occurrences of a value in the json.
     * </ul>
     *
     * @param jsonStream The json stream to read.
     * @return The {@link JsonNode} representation.
     */
    public static JsonNode readJSON(InputStream jsonStream) {
        if (jsonStream == null)
            return null;

        try {
            return objectMapper.readTree(jsonStream);
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Reads a json and converts it into an {@link ArrayNode} if it is an array. It returns null if the json is null,
     * blank, or not an array.
     * You can then read any field using the methods of the {@link ArrayNode} class, like:
     * <ul>
     *     <li>{@link ArrayNode#get(int)}</li> -> Gets one value directly at the highest level of the json.
     *     <li>{@link ArrayNode#get(int)}.asText()</li> -> Gets a value as a certain type.
     *     <li>{@link ArrayNode#findValue(String)}</li> -> Finds the first occurrence of a value in the json.
     *     <li>{@link ArrayNode#findValues(String)}</li> -> Finds all occurrences of a value in the json.
     * </ul>
     *
     * @param json The json string to read.
     * @return The {@link ArrayNode} representation.
     */
    public static ArrayNode readJSONArray(String json) {
        if (isBlankOrNull(json))
            return null;

        try {
            JsonNode tree = objectMapper.readTree(json);
            if (!tree.isArray())
                return null;
            return (ArrayNode) tree;
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Reads a byte array and converts it into an {@link ArrayNode} if it is an array. It returns null if the
     * byte array is null or empty, or if it is not an array.
     * You can then read any field using the methods of the {@link ArrayNode} class, like:
     * <ul>
     *     <li>{@link ArrayNode#get(int)}</li> -> Gets one value directly at the highest level of the json.
     *     <li>{@link ArrayNode#get(int)}.asText()</li> -> Gets a value as a certain type.
     *     <li>{@link ArrayNode#findValue(String)}</li> -> Finds the first occurrence of a value in the json.
     *     <li>{@link ArrayNode#findValues(String)}</li> -> Finds all occurrences of a value in the json.
     * </ul>
     *
     * @param jsonBytes The byte array to read.
     * @return The {@link ArrayNode} representation.
     */
    public static ArrayNode readJSONArray(byte[] jsonBytes) {
        if (jsonBytes == null || jsonBytes.length == 0)
            return null;

        try {
            JsonNode tree = objectMapper.readTree(jsonBytes);
            if (!tree.isArray())
                return null;
            return (ArrayNode) tree;
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Reads an input stream and converts it into an {@link ArrayNode} if it is an array. It returns null if the
     * input stream is null, or if it is not an array.
     * You can then read any field using the methods of the {@link ArrayNode} class, like:
     * <ul>
     *     <li>{@link ArrayNode#get(int)}</li> -> Gets one value directly at the highest level of the json.
     *     <li>{@link ArrayNode#get(int)}.asText()</li> -> Gets a value as a certain type.
     *     <li>{@link ArrayNode#findValue(String)}</li> -> Finds the first occurrence of a value in the json.
     *     <li>{@link ArrayNode#findValues(String)}</li> -> Finds all occurrences of a value in the json.
     * </ul>
     *
     * @param stream The input stream to read.
     * @return The {@link ArrayNode} representation.
     */
    public static ArrayNode readJSONArray(InputStream stream) {
        if (stream == null)
            return null;

        try {
            JsonNode tree = objectMapper.readTree(stream);
            if (!tree.isArray())
                return null;
            return (ArrayNode) tree;
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Creates an empty {@link ObjectNode}.
     * @return The empty node.
     */
    public static ObjectNode createObjectNode() {
        return objectMapper.createObjectNode();
    }

    /**
     * Creates an empty {@link ArrayNode}.
     * @return The empty node.
     */
    public static ArrayNode createArrayNode() {
        return objectMapper.createArrayNode();
    }

    /**
     * Gets a field of a json as a string.
     * @param node The node to get the field from.
     * @param fieldName The field name to get.
     * @return The field value as a string or null if the field does not exist.
     */
    public static String getFieldAsString(JsonNode node, String fieldName) {
        if (node == null) {
            return null;
        }
        JsonNode field = node.get(fieldName);
        if (field == null || field.isMissingNode()) {
            return null;
        }
        return node.get(fieldName).asString();
    }

    /**
     * Gets a field of a json as an integer.
     * @param node The node to get the field from.
     * @param fieldName The field name to get.
     * @return The field value as an integer or null if the field does not exist.
     */
    public static Integer getFieldAsInt(JsonNode node, String fieldName) {
        if (node == null) {
            return null;
        }
        JsonNode field = node.get(fieldName);
        if (field == null || field.isMissingNode()) {
            return null;
        }
        return node.get(fieldName).asInt();
    }

    /**
     * Gets a field of a json as a boolean.
     * @param node The node to get the field from.
     * @param fieldName The field name to get.
     * @return The field value as a boolean or null if the field does not exist.
     */
    public static Boolean getFieldAsBoolean(JsonNode node, String fieldName) {
        if (node == null) {
            return null;
        }
        JsonNode field = node.get(fieldName);
        if (field == null || field.isMissingNode()) {
            return null;
        }
        return node.get(fieldName).asBoolean();
    }

    /**
     * Gets a field of a json as a double.
     * @param node The node to get the field from.
     * @param fieldName The field name to get.
     * @return The field value as a double or null if the field does not exist.
     */
    public static Double getFieldAsDouble(JsonNode node, String fieldName) {
        if (node == null) {
            return null;
        }
        JsonNode field = node.get(fieldName);
        if (field == null || field.isMissingNode()) {
            return null;
        }
        return node.get(fieldName).asDouble();
    }

    /**
     * Gets a field of a json as a long.
     * @param node The node to get the field from.
     * @param fieldName The field name to get.
     * @return The field value as a long or null if the field does not exist.
     */
    public static Long getFieldAsLong(JsonNode node, String fieldName) {
        if (node == null) {
            return null;
        }
        JsonNode field = node.get(fieldName);
        if (field == null || field.isMissingNode()) {
            return null;
        }
        return node.get(fieldName).asLong();
    }

    /**
     * Gets a field of a json as a long.
     * @param node The node to get the field from.
     * @param fieldName The field name to get.
     * @return The field value as a long or null if the field does not exist.
     */
    public static Optional<JsonNode> getFieldAsOptional(JsonNode node, String fieldName) {
        if (node == null) {
            return Optional.empty();
        }
        JsonNode field = node.get(fieldName);
        if (field == null || field.isMissingNode()) {
            return Optional.empty();
        }
        return node.get(fieldName).asOptional();
    }

    /**
     * Creates a deep copy of an object using Jackson library.
     * @param object The object to clone.
     * @return The cloned object.
     * @param <T> The type of the object.
     */
    public static <T> T deepCopy(T object) {
        if (object == null)
            return null;
        try {
            TokenBuffer tb = new TokenBuffer(ObjectWriteContext.empty(), false);
            objectMapper.writeValue(tb, object);
            return (T) objectMapper.readValue(tb.asParser(), object.getClass());
        } catch (JacksonException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Initializes the default object mapper.
     * @return The default object mapper.
     */
    private static JsonMapper initObjectMapper() {
        return JsonMapper.builder().findAndAddModules().build();
    }
}
