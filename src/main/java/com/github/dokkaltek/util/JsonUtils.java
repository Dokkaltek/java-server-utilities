package com.github.dokkaltek.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import com.github.dokkaltek.exception.InvalidInputException;
import com.github.dokkaltek.exception.JSONException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.github.dokkaltek.util.StringUtils.isBlankOrNull;

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
    public static void replaceObjectMapper(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
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
        } catch (JsonProcessingException e) {
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
        } catch (JsonProcessingException | IllegalArgumentException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts an object to a json string, unless the object is null, in which case it returns null.
     * @param object The object to convert.
     * @return The json string representation of the object.
     * @throws JSONException If the object cannot be converted to a json string.
     */
    public static String convertToJSONString(Object object) {
        if (object == null)
            return null;
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
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
    public static String convertToJSONStringOrElse(Object object, String defaultValue) {
        if (object == null)
            return defaultValue;
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
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
        } catch (JsonProcessingException e) {
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
        } catch (JsonProcessingException e) {
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
    public static byte[] convertObjectToBytes(Object json) {
        if (json == null)
            return new byte[]{};
        try {
            return objectMapper.writeValueAsBytes(json);
        } catch (JsonProcessingException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts a json string to a map.
     * @param json The json string to convert.
     * @return The map representation of the json string.
     */
    public static Map<String, Object> convertJSONToMap(String json) {
        if (isBlankOrNull(json))
            return Collections.emptyMap();
        try {
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
            return objectMapper.readValue(json, typeRef);
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Converts a json string to a map.
     * @param object The json string to convert.
     * @return The map representation of the json string.
     */
    public static Map<String, Object> convertObjectToMap(Object object) {
        if (object == null)
            return Collections.emptyMap();
        try {
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
            return objectMapper.convertValue(object, typeRef);
        } catch (IllegalArgumentException e) {
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
        } catch (IOException e) {
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
        } catch (IOException e) {
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
        } catch (IOException e) {
            throw new JSONException(e);
        }
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
            TokenBuffer tb = new TokenBuffer(new ObjectMapper(), false);
            objectMapper.writeValue(tb, object);
            return (T) objectMapper.readValue(tb.asParser(), object.getClass());
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    /**
     * Initializes the default object mapper.
     * @return The default object mapper.
     */
    private static ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
