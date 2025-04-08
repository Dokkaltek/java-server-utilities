package com.github.dokkaltek.samples;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.Objects;

/**
 * Sample POJO class to test reflection.
 */
@JsonPropertyOrder({"description", "name", "age"})
public class SamplePojo extends SamplePojoParent implements Serializable {
    private static final String SAMPLE_STATIC_VALUE = "KEVIN";
    public static String emptyStaticValue;
    private String name;

    @SampleAnnotation
    private int age;

    public SamplePojo() {
        super();
    }

    /**
     * Sample static setter method.
     * @param emptyStaticValue The value to set the static field to.
     */
    public static void setEmptyStaticValue(String emptyStaticValue) {
        SamplePojo.emptyStaticValue = emptyStaticValue;
    }

    /**
     * Sample static getter method.
     * @return The value of the static field.
     */
    public static String getSampleStaticValue() {
        return SAMPLE_STATIC_VALUE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SampleAnnotation
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        SamplePojo that = (SamplePojo) o;
        return getAge() == that.getAge() && Objects.equals(getName(), that.getName())
                && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge());
    }
}
