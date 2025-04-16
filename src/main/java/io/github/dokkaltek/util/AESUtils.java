package io.github.dokkaltek.util;

import io.github.dokkaltek.constant.AESKeyBits;
import io.github.dokkaltek.exception.CryptoException;
import io.github.dokkaltek.exception.GenericException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import static io.github.dokkaltek.constant.ErrorStatus.INTERNAL_SERVER_ERROR;
import static io.github.dokkaltek.util.GeneratorUtils.getSecureRandomInstance;
import static io.github.dokkaltek.util.StringUtils.isBlankOrNull;

/**
 * Utility class for AES cryptography related operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AESUtils {
    private static final String ALGORITHM_NAME = "AES";
    private static final String ERROR_CODE = "AES Exception";
    private static final String GCM_ALGORITHM_NAME = "AES/GCM/NoPadding";
    private static final String CBC_ALGORITHM_NAME = "AES/CBC/PKCS5PADDING";

    /**
     * Generates a new AES key.
     * @param bits The key size.
     * @return The generated key.
     */
    public static SecretKey generateAESKey(AESKeyBits bits) {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(ALGORITHM_NAME);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(ERROR_CODE, e);
        }
        keyGenerator.init(bits.bits());
        return keyGenerator.generateKey();
    }

    /**
     * Generates a new AES key from a password using the PBKDF2WithHmacSHA256 algorithm with 65,536 iterations
     * @param bits The key size.
     * @param password The password.
     * @param salt The salt.
     * @return The generated key.
     */
    public static SecretKey generateAESKeyFromPassword(AESKeyBits bits, String password, String salt) {
        if (isBlankOrNull(password))
            throw new CryptoException("The given password was null or empty.");
        if (isBlankOrNull(salt))
            throw new CryptoException("The given salt was null or empty.");
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, bits.bits());
            return new SecretKeySpec(factory.generateSecret(spec)
                    .getEncoded(), ALGORITHM_NAME);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CryptoException(ERROR_CODE, e);
        }
    }

    /**
     * Generates a 128-bit initialization vector.
     *
     * @return The {@link IvParameterSpec} with the generated initialization vector.
     */
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        getSecureRandomInstance().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    /**
     * Generates a 128-bit initialization vector, which is the recommended size for performance and security for GCM.
     *
     * @return The {@link IvParameterSpec} with the generated initialization vector.
     */
    public static GCMParameterSpec generateIvForGCM() {
        byte[] iv = new byte[16];
        getSecureRandomInstance().nextBytes(iv);
        return new GCMParameterSpec(128, iv);
    }

    /**
     * Generates an initialization vector from the input string. To generate a 128-bit initialization vector,
     * use a string of 16 characters.
     *
     * @return The {@link IvParameterSpec} with the generated initialization vector.
     * @see GeneratorUtils#generateSecureRandomString(int)
     */
    public static IvParameterSpec generateIv(String input) {
        if (isBlankOrNull(input))
            throw new CryptoException("The given input was null or empty.");
        return new IvParameterSpec(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates an initialization vector from the input string. To generate a 128-bit initialization vector,
     * use a string of 16 characters.
     *
     * @return The {@link IvParameterSpec} with the generated initialization vector.
     * @see GeneratorUtils#generateSecureRandomString(int)
     */
    public static GCMParameterSpec generateIvForGCM(String input) {
        if (isBlankOrNull(input) || input.length() != 16)
            throw new CryptoException("The given input was null, empty, or was longer than 16 characters.");
        return new GCMParameterSpec(128, input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Encrypts the given input using AES-CBC with PKCS5 padding and encodes it to base64 for safe string conversion.
     * @param input The input to encrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static String encryptToAESWithCBC(String input, SecretKey key, IvParameterSpec iv) {
        return encryptToAESWithAlgorithm(CBC_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Encrypts the given input using AES-CBC with PKCS5 padding.
     * @param input The input to encrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static byte[] encryptBytesToAESWithCBC(byte[] input, SecretKey key, IvParameterSpec iv) {
        return encryptBytesToAESWithAlgorithm(CBC_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Encrypts the given input using AES-GCM and encodes it to base64 for safe string conversion.
     * @param input The input to encrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static String encryptToAESWithGCM(String input, SecretKey key, GCMParameterSpec iv) {
        return encryptToAESWithAlgorithm(GCM_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Encrypts the given input using AES-CBC with PKCS5 padding.
     * @param input The input to encrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static byte[] encryptBytesToAESWithGCM(byte[] input, SecretKey key, GCMParameterSpec iv) {
        return encryptBytesToAESWithAlgorithm(GCM_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Encrypts the given input using AES-CBC with PKCS5 padding.
     * @param inputFile The input file to encrypt.
     * @param outputFile The output file to write the encrypted file to.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     */
    public static void encryptFileToAESWithCBC(File inputFile, File outputFile, SecretKey key, IvParameterSpec iv) {
        encryptFileToAESWithAlgorithm(CBC_ALGORITHM_NAME, key, iv, inputFile, outputFile);
    }

    /**
     * Encrypts the given input using AES-GCM.
     * @param inputFile The input file to encrypt.
     * @param outputFile The output file to write the encrypted file to.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     */
    public static void encryptFileToAESWithGCM(File inputFile, File outputFile, SecretKey key, GCMParameterSpec iv) {
        encryptFileToAESWithAlgorithm(GCM_ALGORITHM_NAME, key, iv, inputFile, outputFile);
    }

    /**
     * Encrypts the given input using AES-CBC with PKCS5 padding.
     * @param input The input to encrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static SealedObject encryptObjectToAESWithCBC(Serializable input, SecretKey key, IvParameterSpec iv) {
        return encryptObjectToAESWithAlgorithm(CBC_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Encrypts the given input using AES-GCM.
     * @param input The input to encrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static SealedObject encryptObjectToAESWithGCM(Serializable input, SecretKey key, GCMParameterSpec iv) {
        return encryptObjectToAESWithAlgorithm(GCM_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Decrypts the given input using AES-CBC with PKCS5 padding decoding it from base64 first.
     * @param input The input to encrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static String decryptAESWithCBC(String input, SecretKey key, IvParameterSpec iv) {
        return decryptAESWithAlgorithm(CBC_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Decrypts the given input using AES-CBC with PKCS5 padding.
     * @param input The input to decrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static byte[] decryptAESBytesWithCBC(byte[] input, SecretKey key, IvParameterSpec iv) {
        return decryptAESBytesWithAlgorithm(CBC_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Decrypts the given input using AES-GCM decoding it from base64 first.
     * @param input The input to decrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static String decryptAESWithGCM(String input, SecretKey key, GCMParameterSpec iv) {
        return decryptAESWithAlgorithm(GCM_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Decrypts the given input using AES-GCM with PKCS5 padding.
     * @param input The input to decrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static byte[] decryptAESBytesWithGCM(byte[] input, SecretKey key, GCMParameterSpec iv) {
        return decryptAESBytesWithAlgorithm(GCM_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Decrypts the given input using AES-CBC with PKCS5 padding.
     * @param inputFile The input file to decrypt.
     * @param outputFile The output file to write the decrypted file to.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     */
    public static void decryptAESFileWithCBC(File inputFile, File outputFile, SecretKey key, IvParameterSpec iv) {
        decryptAESFileWithAlgorithm(CBC_ALGORITHM_NAME, key, iv, inputFile, outputFile);
    }

    /**
     * Decrypts the given input using AES-GCM.
     * @param inputFile The input file to decrypt.
     * @param outputFile The output file to write the decrypted file to.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     */
    public static void decryptAESFileWithGCM(File inputFile, File outputFile, SecretKey key, GCMParameterSpec iv) {
        decryptAESFileWithAlgorithm(GCM_ALGORITHM_NAME, key, iv, inputFile, outputFile);
    }

    /**
     * Decrypts the given input using AES-CBC with PKCS5 padding.
     * @param input The input to decrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The decrypted input.
     */
    public static Serializable decryptAESObjectWithCBC(SealedObject input, SecretKey key, IvParameterSpec iv) {
        return decryptAESObjectWithAlgorithm(CBC_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Decrypts the given input using AES-GCM.
     * @param input The input to decrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The decrypted input.
     */
    public static Serializable decryptAESObjectWithGCM(SealedObject input, SecretKey key, GCMParameterSpec iv) {
        return decryptAESObjectWithAlgorithm(GCM_ALGORITHM_NAME, input, key, iv);
    }

    /**
     * Encrypts the given input using AES and returns it in base64 for a safe string conversion.
     * @param algorithm The algorithm to use.
     * @param input The input to encrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static String encryptToAESWithAlgorithm(String algorithm, String input, SecretKey key, AlgorithmParameterSpec iv) {
        if (algorithm == null)
            return null;
        byte[] cipherText = encryptBytesToAESWithAlgorithm(algorithm, input.getBytes(StandardCharsets.UTF_8), key, iv);
        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * Encrypts the given input using AES.
     * @param algorithm The algorithm to use.
     * @param input The input to encrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted input.
     */
    public static byte[] encryptBytesToAESWithAlgorithm(String algorithm, byte[] input, SecretKey key, AlgorithmParameterSpec iv) {
        if (algorithm == null)
            return new byte[0];
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            return cipher.doFinal(input);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException ex) {
            throw new CryptoException(ERROR_CODE, ex);
        }
    }

    /**
     * Encrypts the given file to the output file using AES.
     * @param algorithm The algorithm to use for encryption.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @param inputFile The input file to encrypt.
     * @param outputFile The output file to write the encrypted file to.
     */
    public static void encryptFileToAESWithAlgorithm(String algorithm, SecretKey key, AlgorithmParameterSpec iv,
                                                     File inputFile, File outputFile) {
        if (inputFile == null || outputFile == null)
            throw new NullPointerException("Input file and output file to encrypt must not be null");

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
        } catch (IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | BadPaddingException | InvalidKeyException e) {
            throw new CryptoException(ERROR_CODE, e);
        } catch (IOException e) {
            throw new GenericException("Error writing file", e, INTERNAL_SERVER_ERROR.code());
        }
    }

    /**
     * Encrypts an object using AES.
     * @param algorithm The algorithm to use.
     * @param object The object to encrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The encrypted object.
     */
    public static SealedObject encryptObjectToAESWithAlgorithm(String algorithm, Serializable object,
                                                               SecretKey key, AlgorithmParameterSpec iv) {
        if (object == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            return new SealedObject(object, cipher);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 InvalidAlgorithmParameterException | InvalidKeyException ex) {
            throw new CryptoException(ERROR_CODE, ex);
        } catch (IOException e) {
            throw new GenericException("Error generating sealed object", e, INTERNAL_SERVER_ERROR.code());
        }
    }

    /**
     * Decrypts the given input using AES after decoding it from base64 first.
     * @param algorithm The algorithm to use.
     * @param cipherText The cipher text to decrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The decrypted input.
     */
    public static String decryptAESWithAlgorithm(String algorithm, String cipherText, SecretKey key,
                                                 AlgorithmParameterSpec iv) {
        if (isBlankOrNull(cipherText))
            return null;
        byte[] plainText = decryptAESBytesWithAlgorithm(algorithm, Base64.getDecoder().decode(cipherText), key, iv);
        return new String(plainText);
    }

    /**
     * Decrypts the given input using AES.
     * @param algorithm The algorithm to use.
     * @param cipherText The cipher text to decrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The decrypted input.
     */
    public static byte[] decryptAESBytesWithAlgorithm(String algorithm, byte[] cipherText, SecretKey key,
                                                      AlgorithmParameterSpec iv) {
        if (cipherText == null || cipherText.length == 0)
            return new byte[0];
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            return cipher.doFinal(cipherText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException ex) {
            throw new CryptoException(ERROR_CODE, ex);
        }
    }

    /**
     * Decrypts the given file to the output file using AES.
     * @param algorithm The algorithm to use for decryption.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @param inputFile The input file to decrypt.
     * @param outputFile The output file to write the decrypted file to.
     */
    public static void decryptAESFileWithAlgorithm(String algorithm, SecretKey key, AlgorithmParameterSpec iv,
                                                   File inputFile, File outputFile) {
        if (inputFile == null || outputFile == null)
            throw new NullPointerException("Input file and output file to decrypt must not be null");

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);

            byte[] buffer = new byte[64];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
        } catch (IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | BadPaddingException | InvalidKeyException e) {
            throw new CryptoException(ERROR_CODE, e);
        } catch (IOException e) {
            throw new GenericException("Error writing file", e, INTERNAL_SERVER_ERROR.code());
        }
    }

    /**
     * Decrypts an object using AES.
     * @param algorithm The algorithm to use.
     * @param sealedObject The object to decrypt.
     * @param key The key to use.
     * @param iv The initialization vector to use.
     * @return The decrypted object.
     */
    public static Serializable decryptAESObjectWithAlgorithm(String algorithm, SealedObject sealedObject,
                                                             SecretKey key, AlgorithmParameterSpec iv) {
        if (sealedObject == null)
            return null;

        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            return (Serializable) sealedObject.getObject(cipher);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException | ClassNotFoundException ex) {
            throw new CryptoException(ERROR_CODE, ex);
        } catch (IOException e) {
            throw new CryptoException("Error decrypting object", e);
        }
    }
}
