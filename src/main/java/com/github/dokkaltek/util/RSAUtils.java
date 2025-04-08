package com.github.dokkaltek.util;

import com.github.dokkaltek.constant.RSAKeyBits;
import com.github.dokkaltek.exception.CryptoException;
import com.github.dokkaltek.exception.GenericException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.regex.Pattern;

import static com.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;
import static com.github.dokkaltek.constant.literal.SpecialChars.EMPTY_STRING;

/**
 * Utility class for RSA cryptography related operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RSAUtils {
    private static final String KEY_STRING_SEPARATOR = "-----";
    private static final Pattern KEY_SEPARATOR_PATTERN =
            Pattern.compile("(\\n)?" + KEY_STRING_SEPARATOR + ".+ .+ KEY" + KEY_STRING_SEPARATOR + "(\\n)?");
    private static final String ALGORITHM_NAME = "RSA";
    private static final String ALGORITHM_TYPE = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final String ERROR_CODE = "RSA Exception";

    /**
     * Generates a new RSA key pair with the given number of bits.
     * @param keyBits The key bits to use. By default, the best performance/security ratio one is of 2048 bits, which
     *                is also the most widely used.
     * @return The generated key pair.
     * @see <a href="https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-57pt1r5.pdf">NIST SP800-57</a>
     */
    public static KeyPair generateRSAKeyPair(RSAKeyBits keyBits) {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM_NAME);
            generator.initialize(keyBits.bits());
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            throw new CryptoException(ERROR_CODE, ex);
        }
    }

    /**
     * Writes a public key to a file.
     * @param key The public key to write.
     * @param file The file path to write the key to.
     */
    public static void writePublicKeyToFile(PublicKey key, File file) {
        if (key == null || file == null)
            throw new NullPointerException("The given key or file was null.");

        String beginPublic = KEY_STRING_SEPARATOR + "BEGIN PUBLIC KEY" + KEY_STRING_SEPARATOR + "\n";
        String keyContent = Base64.getEncoder().encodeToString(key.getEncoded());
        String endPublic = "\n" + KEY_STRING_SEPARATOR + "END PUBLIC KEY" + KEY_STRING_SEPARATOR;

        try {
            Files.write(file.toPath(), (beginPublic + keyContent + endPublic).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new GenericException("Error writing key to file", e, INTERNAL_SERVER_ERROR.code());
        }
    }

    /**
     * Writes a private key to a file.
     * @param key The private key to write.
     * @param file The file path to write the key to.
     */
    public static void writePrivateKeyToFile(PrivateKey key, File file) {
        if (key == null || file == null)
            throw new NullPointerException("The given key or file was null.");

        String beginPrivate = KEY_STRING_SEPARATOR + "BEGIN PRIVATE KEY" + KEY_STRING_SEPARATOR + "\n";
        String keyContent = Base64.getEncoder().encodeToString(key.getEncoded());
        String endPrivate = "\n" + KEY_STRING_SEPARATOR + "END PRIVATE KEY" + KEY_STRING_SEPARATOR;

        try {
            Files.write(file.toPath(), (beginPrivate + keyContent + endPrivate).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new GenericException("Error writing key to file", e, INTERNAL_SERVER_ERROR.code());
        }
    }

    /**
     * Reads a public key from a file.
     * @param file The file to read the public key from.
     */
    public static PublicKey readPublicKeyFromFile(File file) {
        if (file == null)
            throw new NullPointerException("The given file was null.");

        try {
            byte[] keyBytes = readKeyBytes(file);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CryptoException(ERROR_CODE, e);
        }
    }

    /**
     * Reads a private key from a file.
     * @param file The file to read the private key from.
     */
    public static PrivateKey readPrivateKeyFromFile(File file) {
        if (file == null)
            throw new NullPointerException("The given file was null.");

        try {
            byte[] keyBytes = readKeyBytes(file);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CryptoException(ERROR_CODE, e);
        }
    }

    /**
     * Encrypts a message using the given public key with RSA/None/OAEPWithSHA-256AndMGF1Padding.
     * @param message The message to encrypt.
     * @param publicKey The public key to use.
     */
    public static String encryptRSA(String message, PublicKey publicKey) {
        return encryptRSAWithAlgorithm(ALGORITHM_TYPE, message, publicKey);
    }

    /**
     * Encrypts a message using the given public key with the given algorithm.
     * @param algorithm The algorithm used to encrypt the message.
     * @param message The message to encrypt.
     * @param publicKey The public key to use.
     */
    public static String encryptRSAWithAlgorithm(String algorithm, String message, PublicKey publicKey) {
        try {
            Cipher encryptCipher = Cipher.getInstance(algorithm);
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedMessageBytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException ex) {
            throw new CryptoException(ERROR_CODE, ex);
        }
    }

    /**
     * Decrypts a message using the given private key with RSA/None/OAEPWithSHA-256AndMGF1Padding.
     * @param message The message to decrypt.
     * @param privateKey The private key to use.
     */
    public static String decryptRSA(String message, PrivateKey privateKey) {
        return decryptRSAWithAlgorithm(ALGORITHM_TYPE, message, privateKey);
    }

    /**
     * Decrypts a message using the given private key with the given algorithm.
     * @param message The message to decrypt.
     * @param privateKey The private key to use.
     */
    public static String decryptRSAWithAlgorithm(String algorithm, String message, PrivateKey privateKey) {
        try {
            Cipher encryptCipher = Cipher.getInstance(algorithm);
            encryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decodedMessage = Base64.getDecoder().decode(message);
            byte[] decryptedMessage = encryptCipher.doFinal(decodedMessage);
            return new String(decryptedMessage);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException ex) {
            throw new CryptoException(ERROR_CODE, ex);
        }
    }

    /**
     * Reads a key from a file.
     * @param file The file to read the key from.
     * @return The key read from the file.
     */
    private static byte[] readKeyBytes(File file) {
        if (file == null)
            throw new NullPointerException("The given file was null.");

        try {
            byte[] keyBytes = Files.readAllBytes(file.toPath());
            String privateKey = new String(keyBytes);
            privateKey = KEY_SEPARATOR_PATTERN.matcher(privateKey).replaceAll(EMPTY_STRING);
            return Base64.getDecoder().decode(privateKey);
        } catch (IOException e) {
            throw new GenericException("Error reading key from file", e, INTERNAL_SERVER_ERROR.code());
        }
    }
}
