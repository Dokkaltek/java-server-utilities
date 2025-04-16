package io.github.dokkaltek.util;

import io.github.dokkaltek.exception.DateConversionException;
import io.github.dokkaltek.exception.InvalidEmailException;
import io.github.dokkaltek.exception.InvalidIdException;
import io.github.dokkaltek.exception.InvalidInputException;
import io.github.dokkaltek.exception.InvalidUUIDException;
import io.github.dokkaltek.exception.InvalidUriException;
import io.github.dokkaltek.exception.InvalidUrlException;
import io.github.dokkaltek.exception.JSONException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import static io.github.dokkaltek.util.StringUtils.isBlankOrNull;

/**
 * Utility class to validate data.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtils {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9.-]+(?<!\\.)$");
    private static final Pattern NIF_PATTERN = Pattern.compile("^\\d{8}[TRWAGMYFPDXBNJZSQVHLCKE]$");
    private static final Pattern NIE_PATTERN = Pattern.compile("^[XYZ]\\d{7}[TRWAGMYFPDXBNJZSQVHLCKE]$");
    private static final Pattern NIF_AND_NIE_PATTERN =
            Pattern.compile("^[0-9XYZ]\\d{7}[TRWAGMYFPDXBNJZSQVHLCKE]$");
    private static final String VALID_DNI_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKET";

    /**
     * Checks if a UUID is valid.
     * @param uuid The UUID to validate.
     * @return True if the uuid is valid, false otherwise.
     */
    public static boolean validateUUID(String uuid) {
        if (isBlankOrNull(uuid))
            return false;

        try {
            UUID.fromString(uuid.trim());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks if a UUID is valid and throws an exception if not.
     * @param uuid The UUID to validate.
     * @throws InvalidUUIDException If the uuid is not valid.
     */
    public static void validateUUIDWithEx(String uuid) {
        if (isBlankOrNull(uuid))
            throw new InvalidInputException("The given UUID was null or empty.");

        try {
            UUID.fromString(uuid.trim());
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDException(e);
        }
    }

    /**
     * Validates that a JSON string is valid.
     * @param json The JSON string to validate.
     * @return True if the JSON string is valid, false otherwise.
     */
    public static boolean validateJSON(String json) {
        return JsonUtils.validateJSON(json);
    }

    /**
     * Validates that a JSON string is valid.
     * @param json The JSON string to validate.
     * @throws JSONException If the JSON string is not valid.
     */
    public static void validateJSONWithEx(String json) {
        JsonUtils.validateJSONWithEx(json);
    }

    /**
     * Validates that a URI is valid.
     * @param uri The URI to validate.
     * @return True if the URI is valid, false otherwise.
     */
    public static boolean validateUri(String uri) {
        return UriUtils.validateUri(uri);
    }

    /**
     * Validates that a URI is valid.
     * @param uri The URI to validate.
     * @throws JSONException If the URI is not valid.
     */
    public static void validateUriWithEx(String uri) {
        if (isBlankOrNull(uri))
            throw new InvalidInputException("The given URI was null or empty.");

        try {
            new URI(uri);
        } catch (URISyntaxException | NullPointerException e) {
            throw new InvalidUriException(e);
        }
    }

    /**
     * Validates that a URL is valid.
     * @param url The URL to validate.
     * @return True if the URL is valid, false otherwise.
     */
    public static boolean validateUrl(String url) {
        return UriUtils.validateUrl(url);
    }

    /**
     * Validates that a URL is valid.
     * @param url The URL to validate.
     * @throws InvalidUrlException If the URL is not valid.
     * @throws InvalidInputException if the URL is null or empty.
     */
    public static void validateUrlWithEx(String url) {
        if (isBlankOrNull(url))
            throw new InvalidInputException("The given URL was null or empty.");

        try {
            new URL(url);
        } catch (MalformedURLException | NullPointerException e) {
            throw new InvalidUrlException(e);
        }
    }

    /**
     * Validates that a string is in the right date format using {@link SimpleDateFormat}.
     * @param date The date string to validate.
     * @param format The format of the date string to check.
     * @return True if the string is in the right format, false otherwise, or if the date or format are null or empty.
     */
    public static boolean validateDateFormat(String date, String format) {
        try {
            Date parsedDate = DateUtils.parseDate(date, format);
            return parsedDate != null;
        } catch (DateConversionException e) {
            return false;
        }
    }

    /**
     * Validates that a string is in the right date format using {@link SimpleDateFormat}.
     * @param date The date string to validate.
     * @param format The format of the date string to check.
     * @throws DateConversionException If the string is not in the right format.
     */
    public static void validateDateFormatWithEx(String date, String format) {
        Date parsedDate = DateUtils.parseDate(date, format);
        if (parsedDate == null)
            throw new InvalidInputException("The given date or format were null or empty.");
    }

    /**
     * Validates an email address (no ' or | characters allowed to avoid SQL injection).
     * @param email The email address to validate.
     * @return True if the email address is valid, false otherwise.
     * @see <a href="https://www.rfc-editor.org/info/rfc5322">IETF reference</a>
     */
    public static boolean validateEmail(String email) {
        if (isBlankOrNull(email))
            return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validates an email address (no ' or | characters allowed to avoid SQL injection).
     * @param email The email address to validate.
     * @throws InvalidEmailException If the email address is not valid.
     * @see <a href="https://www.rfc-editor.org/info/rfc5322">IETF reference</a>
     */
    public static void validateEmailWithEx(String email) {
        if (isBlankOrNull(email))
            throw new InvalidInputException("The given email was null or empty.");
        if (!validateEmail(email))
            throw new InvalidEmailException(String.format("The email '%s' was invalid.", email.trim()));
    }

    /**
     * Validates that a Spanish NIF is valid. It doesn't validate NIEs.
     * @param nif The NIF to validate.
     * @return True if the NIF is valid, false otherwise.
     */
    public static boolean validateNIF(String nif) {
        if (isBlankOrNull(nif) || nif.trim().length() != 9)
            return false;

        String upperCaseNif = nif.trim().toUpperCase();
        boolean validFormat = NIF_PATTERN.matcher(upperCaseNif).matches();

        if (!validFormat)
            return false;

        return checkDNIValidity(upperCaseNif);
    }

    /**
     * Validates that a Spanish NIF is valid. It doesn't validate NIEs.
     * @param nif The NIF to validate.
     * @throws InvalidIdException If the NIF is not valid.
     */
    public static void validateNIFWithEx(String nif) {
        if (isBlankOrNull(nif))
            throw new InvalidInputException("The given NIF was null or empty.");
        if (!validateNIF(nif))
            throw new InvalidIdException(String.format("The NIF '%s' was invalid.", nif.trim()));
    }

    /**
     * Validates that a Spanish NIE is valid. It doesn't validate NIFs.
     * @param nie The NIE to validate.
     * @return True if the NIE is valid, false otherwise.
     */
    public static boolean validateNIE(String nie) {
        if (isBlankOrNull(nie) || nie.trim().length() != 9)
            return false;

        String upperCaseNie = nie.trim().toUpperCase();

        boolean validFormat = NIE_PATTERN.matcher(upperCaseNie).matches();

        if (!validFormat)
            return false;

        String nieCheckDigit;
        if (upperCaseNie.startsWith("X"))
            nieCheckDigit = "0";
        else if (upperCaseNie.startsWith("Y"))
            nieCheckDigit = "1";
        else
            nieCheckDigit = "2";

        upperCaseNie = nieCheckDigit + upperCaseNie.substring(1);

        return checkDNIValidity(upperCaseNie);
    }

    /**
     * Validates that a Spanish NIE is valid. It doesn't validate NIFs.
     * @param nie The NIE to validate.
     * @throws InvalidIdException If the NIE is not valid.
     */
    public static void validateNIEWithEx(String nie) {
        if (isBlankOrNull(nie))
            throw new InvalidInputException("The given NIE was null or empty.");
        if (!validateNIE(nie))
            throw new InvalidIdException(String.format("The NIE '%s' was invalid.", nie));
    }

    /**
     * Validates both NIE and NIFs.
     * @param dni The NIF or NIE to validate.
     * @return True if the NIF or NIE is valid, false otherwise.
     */
    public static boolean validateDNI(String dni) {
        if (isBlankOrNull(dni))
            return false;

        String upperCaseDNI = dni.trim().toUpperCase();
        boolean validFormat = NIF_AND_NIE_PATTERN.matcher(upperCaseDNI).matches();

        if (!validFormat)
            return false;

        String nieCheckDigit;
        if (upperCaseDNI.startsWith("X"))
            nieCheckDigit = "0";
        else if (upperCaseDNI.startsWith("Y"))
            nieCheckDigit = "1";
        else if (upperCaseDNI.startsWith("Z"))
            nieCheckDigit = "2";
        else
            return checkDNIValidity(upperCaseDNI);

        upperCaseDNI = nieCheckDigit + upperCaseDNI.substring(1);

        return checkDNIValidity(upperCaseDNI);

    }

    /**
     * Validates both NIE and NIFs.
     * @param dni The NIF or NIE to validate.
     * @throws InvalidIdException If the NIF or NIE is not valid.
     */
    public static void validateDNIWithEx(String dni) {
        if (isBlankOrNull(dni))
            throw new InvalidInputException("The given DNI was null or empty.");
        if (!validateDNI(dni))
            throw new InvalidIdException(String.format("The DNI '%s' was invalid.", dni));
    }

    /**
     * Checks if a DNI is valid.
     * @param dni The DNI to check.
     * @return True if the DNI is valid, false otherwise.
     */
    private static boolean checkDNIValidity(String dni) {
        // To check if the number is actually valid, we divide it by 23, and check the position of the valid letters
        // for the rest of the result.
        int remainder = Integer.parseInt(dni.substring(0, dni.length() - 1)) % 23;
        return VALID_DNI_LETTERS.charAt(remainder) == dni.charAt(dni.length() - 1);
    }
}
