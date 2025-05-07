# Server utilities
Server utilities library for java server applications. 

The minimum supported version is **Java 8**.

This library tries to be lightweight without having too many transitive dependencies.

## Things you can do with this library:

### Utility classes
- **AESUtils** -> Easy AES encryption and decryption.
- **RSAUtils** -> Easy RSA encryption and decryption.
- **DateUtils** -> Easy manipulation of date objects and date strings.
- **GeneratorUtils** -> Generate placeholder values and secure strings.
- **JsonUtils** -> Use ObjectMapper without the need of error handling explicitly.
- **LoggingUtils** -> Prevent log forging on strings, or mask values.
- **ReflectionUtils** -> Easy use of reflection API to get values of objects.
- **StringUtils** -> Check if objects are blank on java 8, capitalize the first letter of a string, repeat a string, truncate it, or switch its case.
- **UriUtils** -> Join paths, get parts of a uri without having to cast it to URI explicitly, get the query parameters as a map, or update only one query param.
- **ValidationUtils** -> Validate Strings to be in valid format for emails, date, json, url, uuid, DNI, NIE, or NIF.
- **FunctionalUtils** -> Perform conditional checks with a more fluent API, or chain them.


### Helper classes
- **Duo** and **Trio** -> Similar to `Pair` in Spring, or objects that hold 2 or 3 additional objects inside them.
- **WrapperList** -> A wrapper over `ArrayList` for easier access to the last index of the list, the first and last elements, list creation, and other sugar syntax methods.

### Constants 
- **CurrencyChars** -> Keeps the unicode chars for common currencies.
- **GreekChars** -> Keeps the unicode chars for greek letters.
- **Letters** -> Keeps the whole abecedary as an enum that you can use to avoid having to specify 1-letter constants on your classes.
- **Numbers** -> Same as letters, but for numbers up to 10.
- **SpecialChars** -> Holds special characters to used as constants, so that you can use them at any time, including empty string and space.
- **HttpHeaderKeys** -> Holds most common Http header keys.
- **AuthHeaderPrefixes** -> Holds most commonly used authorization prefixes, like "Bearer ".
- **MimeTypes** -> Holds most common MimeTypes for headers.

## Installation
You just need to add the dependency to your pom:

``` xml
<dependency>
    <groupId>io.github.dokkaltek</groupId>
    <artifactId>server-utilities</artifactId>
    <version>1.1.1</version>
</dependency>
```
