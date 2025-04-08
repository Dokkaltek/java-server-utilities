package com.github.dokkaltek.util;

import com.github.dokkaltek.exception.ReflectionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility class to get class related information.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionUtils {
    private static final String LOG_PREFIX = "Field ";

    /**
     * Gets a {@link Field} from an object.
     * @param objClass The object to get the {@link Field} from.
     * @param fieldName The name of the field to get.
     * @return The {@link Field} representing the field from the object.
     */
    public static Field getClassField(Class<?> objClass, String fieldName) {
        try {
            Field field = objClass.getDeclaredField(fieldName);
            field.setAccessible(Boolean.TRUE);
            return field;
        } catch (NoSuchFieldException e) {
            Class<?> superClass = objClass.getSuperclass();
            // If the super class reached the Object level, it didn't have a declared field with that name
            if (superClass != null && !superClass.equals(Object.class)) {
                return getClassField(superClass, fieldName);
            }
            throw new ReflectionException(LOG_PREFIX + fieldName + " not found for class " +
                    objClass.getCanonicalName());
        }
    }

    /**
     * Gets a field from an object.
     * @param object The object to get the field from.
     * @param fieldName The name of the field.
     * @return The value of the field.
     * @param <T> The type of the field.
     */
    public static <T> T getField(Object object, String fieldName) {
        try {
            return (T) getClassField(object.getClass(), fieldName).get(object);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * Gets a field from an object, or a default value if the field is null.
     * @param object The object to get the field from.
     * @param fieldName The name of the field.
     * @return The value of the field.
     * @param <T> The type of the field.
     */
    public static <T> T getFieldOrElse(Object object, String fieldName, T defaultValue) {
        T result = getField(object, fieldName);
        return result != null ? result : defaultValue;
    }

    /**
     * Gets a field from an object, or a default value if the field is null.
     * @param object The object to get the field from.
     * @param fieldName The name of the field.
     * @return The value of the field.
     * @param <T> The type of the field.
     * @throws NullPointerException If the field is null
     */
    public static <T> T getFieldOrThrow(Object object, String fieldName) {
        T result = getField(object, fieldName);
        if (result != null) {
            return result;
        }
        throw new NullPointerException(LOG_PREFIX + fieldName + " is null.");
    }

    /**
     * Gets a static field of a class.
     * @param objClass The object to get the field from.
     * @param fieldName The name of the field.
     * @return The value of the field.
     * @param <T> The type of the field.
     */
    public static <T> T getStaticField(Class<?> objClass, String fieldName) {
        try {
            return (T) getClassField(objClass, fieldName).get(null);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * Gets a static field from a class, or a default value if the field is null.
     * @param objClass The class to get the field from.
     * @param fieldName The name of the field.
     * @return The value of the field.
     * @param <T> The type of the field.
     */
    public static <T> T getStaticFieldOrElse(Class<?> objClass, String fieldName, T defaultValue) {
        T result = getStaticField(objClass, fieldName);
        return result != null ? result : defaultValue;
    }

    /**
     * Gets a static field from a class, or a default value if the field is null.
     * @param objClass The class to get the field from.
     * @param fieldName The name of the field.
     * @return The value of the field.
     * @param <T> The type of the field.
     * @throws NullPointerException If the field is null
     */
    public static <T> T getStaticFieldOrThrow(Class<?> objClass, String fieldName) {
        T result = getStaticField(objClass, fieldName);
        if (result != null) {
            return result;
        }
        throw new NullPointerException(LOG_PREFIX + fieldName + " is null.");
    }

    /**
     * Sets a field of an object.
     * @param object The object to set the field of.
     * @param fieldName The name of the field.
     * @param value The value to set the field to.
     * @param <T> The type of the field.
     */
    public static <T> void setField(Object object, String fieldName, T value) {
        try {
            getClassField(object.getClass(), fieldName).set(object, value);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * Sets a field of an object if the value we are setting it to is not null.
     * @param object The object to set the field of.
     * @param fieldName The name of the field.
     * @param value The value to set the field to.
     * @param <T> The type of the field.
     */
    public static <T> void setFieldIfNewIsNotNull(Object object, String fieldName, T value) {
        if (value == null)
            return;
        setField(object, fieldName, value);
    }

    /**
     * Sets a field of an object if the value it currently has is null.
     * @param object The object to set the field of.
     * @param fieldName The name of the field.
     * @param value The value to set the field to.
     * @param <T> The type of the field.
     */
    public static <T> void setFieldIfNull(Object object, String fieldName, T value) {
        if (value == null)
            return;

        if (getField(object, fieldName) == null) {
            setField(object, fieldName, value);
        }
    }

    /**
     * Sets a static field of a class.
     * @param objClass The class to set the field of.
     * @param fieldName The name of the field.
     * @param value The value to set the field to.
     * @param <T> The type of the field.
     */
    public static <T> void setStaticField(Class<?> objClass, String fieldName, T value) {
        try {
            getClassField(objClass, fieldName).set(null, value);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * Sets a static field of a class if the value we are setting it to is not null.
     * @param objClass The class to set the field of.
     * @param fieldName The name of the field.
     * @param value The value to set the field to.
     * @param <T> The type of the field.
     */
    public static <T> void setStaticFieldIfNewIsNotNull(Class<?> objClass, String fieldName, T value) {
        if (value == null)
            return;
        setStaticField(objClass, fieldName, value);
    }

    /**
     * Sets a static field of a class if the value it currently has is null.
     * @param objClass The class to set the field of.
     * @param fieldName The name of the field.
     * @param value The value to set the field to.
     * @param <T> The type of the field.
     */
    public static <T> void setStaticFieldIfNull(Class<?> objClass, String fieldName, T value) {
        if (value == null)
            return;

        if (getStaticField(objClass, fieldName) == null) {
            setStaticField(objClass, fieldName, value);
        }
    }

    /**
     * Get a method from a class (both static and non-static).
     * @param methodClass The class to get the method from.
     * @param methodName The method name to get.
     * @return the {@link Method} reference.
     */
    public static Method getMethod(Class<?> methodClass, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = methodClass.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(Boolean.TRUE);
            return method;
        } catch (NoSuchMethodException e) {
            Class<?> superClass = methodClass.getSuperclass();
            // If the super class reached the Object level, it didn't have a declared method with that name
            if (superClass != null && !superClass.equals(Object.class)) {
                return getMethod(superClass, methodName, parameterTypes);
            }
            throw new ReflectionException("Method " + e.getMessage() + " not found");
        }
    }

    /**
     * Invokes a method on an object.
     * @param object The object to invoke the method on.
     * @param methodName The name of the method to invoke.
     * @param args The arguments to pass to the method.
     */
    public static <T> T invokeMethod(Object object, String methodName, Object... args) {
        Class<?>[] parameterTypes = null;

        if (args != null) {
            parameterTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
        }

        try {
            return (T) getMethod(object.getClass(), methodName, parameterTypes).invoke(object, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * Invokes a static method on an object without any return value.
     * @param objClass The class to invoke the method on.
     * @param methodName The name of the method to invoke.
     * @param args The arguments to pass to the method.
     */
    public static <T> T invokeStaticMethod(Class<?> objClass, String methodName, Object... args) {
        Class<?>[] parameterTypes = null;

        if (args != null) {
            parameterTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
        }

        try {
            return (T) getMethod(objClass, methodName, parameterTypes).invoke(null, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * Gets an annotation from a field.
     * @param objClass The Class where the field is in.
     * @param fieldName The name of the field.
     * @param annotationClass The annotation class to get.
     * @return The annotation.
     * @param <A> The type of the annotation.
     */
    public static <A extends Annotation> A getFieldAnnotation(Class<?> objClass,
                                                              String fieldName,
                                                              Class<A> annotationClass) {
        return getClassField(objClass, fieldName).getAnnotation(annotationClass);
    }

    /**
     * Gets an annotation from a field.
     * @param objClass The class where the method is in.
     * @param methodName The name of the field.
     * @param annotationClass The annotation class to get.
     * @param parameterTypes The types of the input parameters of the method. Can be null.
     * @return The annotation.
     * @param <A> The type of the annotation.
     */
    public static <A extends Annotation> A getMethodAnnotation(Class<?> objClass,
                                                               String methodName,
                                                               Class<A> annotationClass,
                                                               Class<?>... parameterTypes) {
        return getMethod(objClass, methodName, parameterTypes).getAnnotation(annotationClass);
    }
}
