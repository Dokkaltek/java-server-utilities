package io.github.dokkaltek.constant.literal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants for literal special characters.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SpecialChars {
    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
    public static final String QUESTION_MARK = "?";
    public static final String REVERSE_QUESTION_MARK = "¿";
    public static final String EXCLAMATION_MARK = "!";
    public static final String REVERSE_EXCLAMATION_MARK = "¡";
    public static final String HASH = "#";
    public static final String AMPERSAND = "&";
    public static final String PERCENTAGE = "%";
    public static final String EQUAL_SIGN = "=";
    public static final String TILDE = "~";
    public static final String ASTERISK = "*";
    public static final String DOLLAR = "$";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String OPEN_PARENTHESIS = "(";
    public static final String CLOSE_PARENTHESIS = ")";
    public static final String OPEN_SQUARE_BRACKET = "[";
    public static final String CLOSE_SQUARE_BRACKET = "]";
    public static final String OPEN_CURLY_BRACKET = "{";
    public static final String CLOSE_CURLY_BRACKET = "}";
    public static final String OPEN_LENTICULAR_BRACKET = "【";
    public static final String CLOSE_LENTICULAR_BRACKET = "】";
    public static final String OPEN_CORNER_BRACKET = "「";
    public static final String CLOSE_CORNER_BRACKET = "」";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String PIPE = "|";
    public static final String AT_SYMBOL = "@";
    public static final String DOUBLE_QUOTE = "\"";
    public static final String SINGLE_QUOTE = "'";
    public static final String OPEN_QUOTATION_MARK = "«";
    public static final String CLOSE_QUOTATION_MARK = "»";
    public static final String RIGHT_DOUBLE_QUOTE = "”";
    public static final String LEFT_DOUBLE_QUOTE = "‟";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String MINUS = "-";
    public static final String PLUS = "+";
    public static final String UNDERSCORE = "_";
    public static final String GREATER_THAN = ">";
    public static final String LESS_THAN = "<";
    public static final String CIRCUMFLEX = "^";
    public static final String DOUBLE_ACCENT = "¨";
    public static final String RIGHTWARDS_ACCENT = "´";
    public static final String LEFTWARDS_ACCENT = "`";
    public static final String MASCULINE_ORDINAL = "º";
    public static final String FEMININE_ORDINAL = "ª";
    public static final String TRADEMARK = "™";
    public static final String REGISTERED = "®";
    public static final String COPYRIGHT = "©";
    public static final String SOUND_RECORDING_COPYRIGHT = "℗";
    public static final String SERVICE_MARK = "℠";
    public static final String SECTION_SIGN = "§";
    public static final String FRACTION_HALF = "½";
    public static final String FRACTION_ONE_THIRD = "⅓";
    public static final String FRACTION_ONE_QUARTER = "¼";
    public static final String FRACTION_ONE_FIFTH = "⅕";
    public static final String FRACTION_ONE_SIXTH = "⅙";
    public static final String FRACTION_ONE_SEVENTH = "⅐";
    public static final String FRACTION_ONE_EIGHTH = "⅛";
    public static final String FRACTION_ONE_NINTH = "⅑";
    public static final String FRACTION_ONE_TENTH = "⅒";
    public static final String SQUARE_ROOT = "√";
    public static final String CHECKMARK = "✓";
    public static final String HEART = "❤";
    public static final String DIAMOND = "♦";
    public static final String CLUB = "♣";
    public static final String SPADE = "♠";
    public static final String STAR = "⭐";
    public static final String TRIANGLE_POINTER_RIGHT = "►";
    public static final String TRIANGLE_POINTER_LEFT = "◄";
    public static final String CENTRAL_DOT = "⋅";
    public static final String CIRCLE = "○";
    public static final String CIRCLE_WITH_DOT = "⊙";
    public static final String CIRCLE_WITH_CROSS = "⊗";
    public static final String CIRCLE_WITH_PLUS = "⊕";
    public static final String RADIOACTIVE = "☢";
    public static final String MULTIPLICATION = "×";
    public static final String DIVISION = "÷";
    public static final String INFINITY = "∞";
    public static final String PLUS_MINUS = "±";
    public static final String LESS_THAN_OR_EQUAL_TO = "≤";
    public static final String GREATER_THAN_OR_EQUAL_TO = "≥";
    public static final String CHECKBOX_UNCHECKED = "☐";
    public static final String CHECKBOX_CHECKED = "☑";
    public static final String CHECKBOX_CROSSED = "☒";
    public static final String GENDER_MALE = "♂";
    public static final String GENDER_FEMALE = "♀";
    public static final String GENDER_BISEXUAL = "⚧";
    public static final String GENDER_LESBIAN = "⚢";
    public static final String GENDER_GAY = "⚣";
    public static final String GENDER_HETEROSEXUAL = "⚤";
    public static final String GENDER_TRANSGENDER = "⚧";
    public static final String ARROW_RIGHT = "🠆";
    public static final String ARROW_LEFT = "🡐";
    public static final String ARROW_UP = "🠅";
    public static final String ARROW_DOWN = "🠇";

    /**
     * Converts the first character of a String to a char.
     * @param specialChar The String to convert.
     * @return The first character of the String.
     */
    public static char toChar(String specialChar) {
        return specialChar.charAt(0);
    }
}
