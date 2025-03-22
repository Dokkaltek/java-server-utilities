package com.github.dokkaltek.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class to map objects using reflection. This shouldn't be used for all your mapping needs, as other tools
 * like <b>MapStruct</b> or <b>ModelMapper</b> are better suited for this and won't have the overhead of reflection,
 * which might affect performance, but it can be useful if you can't or don't want to use those for any reason,
 * like on tests or on non-critical parts of the application.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectionMapper {
    // TODO - Implement class
}
