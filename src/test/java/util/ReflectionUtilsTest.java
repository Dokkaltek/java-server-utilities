package util;

import com.github.dokkaltek.exception.ReflectionException;
import com.github.dokkaltek.util.ReflectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.SampleAnnotation;
import samples.SamplePojo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link ReflectionUtils} class.
 */
class ReflectionUtilsTest {
    private static final String SAMPLE_VALUE = "KEVIN";
    private SamplePojo samplePojo;


    @BeforeEach
    void setup() {
        samplePojo = new SamplePojo();
        samplePojo.setName("John");
        samplePojo.setAge(30);
        samplePojo.setDescription("test");
    }

    /**
     * Test for {@link ReflectionUtils#getField(Object, String)} method.
     */
    @Test
    @DisplayName("Test getting a field")
    void testGetField() {
        assertNull(ReflectionUtils.getField(new SamplePojo(), "name"));
        assertEquals(samplePojo.getName(), ReflectionUtils.getField(samplePojo, "name"));
        assertEquals(samplePojo.getAge(), (int) ReflectionUtils.getField(samplePojo, "age"));
        assertDoesNotThrow(() -> ReflectionUtils.getField(samplePojo, "description"));
        assertEquals(samplePojo.getDescription(), ReflectionUtils.getField(samplePojo, "description"));
        assertThrows(ReflectionException.class, () -> ReflectionUtils.getField(samplePojo, "nonexistent_field"));
    }

    /**
     * Test for {@link ReflectionUtils#getFieldOrElse(Object, String, Object)} method.
     */
    @Test
    @DisplayName("Test getting a field or else a default value")
    void testGetFieldOrElse() {
        assertEquals(SAMPLE_VALUE, ReflectionUtils.getFieldOrElse(new SamplePojo(), "name", SAMPLE_VALUE));
        assertEquals(samplePojo.getName(), ReflectionUtils.getFieldOrElse(samplePojo, "name", SAMPLE_VALUE));
        assertEquals(samplePojo.getAge(), ReflectionUtils.getFieldOrElse(samplePojo, "age", 50));
    }

    /**
     * Test for {@link ReflectionUtils#getFieldOrThrow(Object, String)} method.
     */
    @Test
    @DisplayName("Test getting a field or else a default value")
    void testGetFieldOrThrow() {
        SamplePojo emptyPojo = new SamplePojo();
        assertThrows(NullPointerException.class, () -> ReflectionUtils.getFieldOrThrow(emptyPojo, "name"));
        assertEquals(samplePojo.getName(), ReflectionUtils.getFieldOrThrow(samplePojo, "name"));
        assertEquals(samplePojo.getAge(), (int) ReflectionUtils.getField(samplePojo, "age"));
    }

    /**
     * Test for {@link ReflectionUtils#setFieldIfNewIsNotNull(Object, String, Object)} method.
     */
    @Test
    @DisplayName("Test setting a field if new value is not null")
    void testSetFieldIfNewIsNotNull() {
        SamplePojo emptyPojo = new SamplePojo();
        ReflectionUtils.setFieldIfNewIsNotNull(emptyPojo, "name", SAMPLE_VALUE);
        assertEquals(SAMPLE_VALUE, emptyPojo.getName());
        ReflectionUtils.setFieldIfNewIsNotNull(emptyPojo, "name", null);
        assertEquals(SAMPLE_VALUE, emptyPojo.getName());
    }

    /**
     * Test for {@link ReflectionUtils#setFieldIfNull(Object, String, Object)} method.
     */
    @Test
    @DisplayName("Test setting a field if current value is null")
    void testSetFieldIfNull() {
        SamplePojo emptyPojo = new SamplePojo();
        ReflectionUtils.setFieldIfNull(emptyPojo, "name", SAMPLE_VALUE);
        assertEquals(SAMPLE_VALUE, emptyPojo.getName());
        ReflectionUtils.setFieldIfNull(emptyPojo, "name", "test123");
        assertEquals(SAMPLE_VALUE, emptyPojo.getName());
    }

    /**
     * Test for {@link ReflectionUtils#invokeMethod(Object, String, Object...)} method.
     */
    @Test
    @DisplayName("Test invoking a method")
    void testInvokeMethod() {
        SamplePojo emptyPojo = new SamplePojo();
        ReflectionUtils.invokeMethod(emptyPojo, "setDescription", SAMPLE_VALUE);
        assertEquals(SAMPLE_VALUE, emptyPojo.getDescription());
        assertEquals(SAMPLE_VALUE, ReflectionUtils.invokeMethod(emptyPojo, "getDescription"));
        assertThrows(ReflectionException.class, () -> ReflectionUtils.invokeMethod(emptyPojo, "nonexistent_method"));
    }

    /**
     * Test for {@link ReflectionUtils#getFieldAnnotation(Class, String, Class)} method.
     */
    @Test
    @DisplayName("Test getting a field annotation")
    void testGetFieldAnnotation() {
        assertNotNull(ReflectionUtils.getFieldAnnotation(SamplePojo.class, "age", SampleAnnotation.class));
        assertNull(ReflectionUtils.getFieldAnnotation(SamplePojo.class, "name", SampleAnnotation.class));
    }

    /**
     * Test for {@link ReflectionUtils#getMethodAnnotation(Class, String, Class, Class[])} method.
     */
    @Test
    @DisplayName("Test getting a method annotation")
    void testGetMethodAnnotation() {
        assertNotNull(ReflectionUtils.getMethodAnnotation(SamplePojo.class, "getAge", SampleAnnotation.class));
        assertNull(ReflectionUtils.getMethodAnnotation(SamplePojo.class, "getName", SampleAnnotation.class));
    }
}
