package io.github.dokkaltek.util;

import io.github.dokkaltek.constant.AESKeyBits;
import io.github.dokkaltek.exception.CryptoException;
import io.github.dokkaltek.samples.SamplePojo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

import static io.github.dokkaltek.util.AESUtils.decryptAESFileWithCBC;
import static io.github.dokkaltek.util.AESUtils.decryptAESFileWithGCM;
import static io.github.dokkaltek.util.AESUtils.encryptBytesToAESWithCBC;
import static io.github.dokkaltek.util.AESUtils.encryptBytesToAESWithGCM;
import static io.github.dokkaltek.util.AESUtils.encryptFileToAESWithCBC;
import static io.github.dokkaltek.util.AESUtils.encryptFileToAESWithGCM;
import static io.github.dokkaltek.util.AESUtils.encryptObjectToAESWithCBC;
import static io.github.dokkaltek.util.AESUtils.encryptObjectToAESWithGCM;
import static io.github.dokkaltek.util.AESUtils.encryptToAESWithCBC;
import static io.github.dokkaltek.util.AESUtils.encryptToAESWithGCM;
import static io.github.dokkaltek.util.AESUtils.generateAESKey;
import static io.github.dokkaltek.util.AESUtils.generateAESKeyFromPassword;
import static io.github.dokkaltek.util.AESUtils.generateIv;
import static io.github.dokkaltek.util.AESUtils.generateIvForGCM;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

/**
 * Tests for {@link AESUtils} class.
 */
class AESUtilsTest {
    private static final String SAMPLE_MESSAGE = "this is a sample message";

    /**
     * Tests {@link AESUtils#generateAESKey(AESKeyBits)} method.
     */
    @Test
    @DisplayName("Test generating an AES key")
    void testGenerateAESKey() {
        assertEquals(16, generateAESKey(AESKeyBits.KEY_128).getEncoded().length);
        assertEquals(24, generateAESKey(AESKeyBits.KEY_192).getEncoded().length);
        assertEquals(32, generateAESKey(AESKeyBits.KEY_256).getEncoded().length);
    }

    /**
     * Tests {@link AESUtils#generateAESKeyFromPassword(AESKeyBits, String, String)} method.
     */
    @Test
    @DisplayName("Test generating an AES key with a password")
    void testGenerateAESKeyFromPassword() {
        String password = GeneratorUtils.generateSecureRandomString(16);
        String salt = GeneratorUtils.generateSecureRandomString(10);
        assertEquals(16, generateAESKeyFromPassword(AESKeyBits.KEY_128, password, salt).getEncoded().length);
        assertEquals(24, generateAESKeyFromPassword(AESKeyBits.KEY_192, password, salt).getEncoded().length);
        assertEquals(32, generateAESKeyFromPassword(AESKeyBits.KEY_256, password, salt).getEncoded().length);

        // Test error case too.
        try(MockedStatic<SecretKeyFactory> keyGenerator = mockStatic(SecretKeyFactory.class)) {
            keyGenerator.when(() -> SecretKeyFactory.getInstance(anyString())).thenThrow(NoSuchAlgorithmException.class);
            assertThrows(CryptoException.class, () -> generateAESKeyFromPassword(AESKeyBits.KEY_128, password, salt));
        }
    }

    /**
     * Tests {@link AESUtils#generateIv()} method.
     */
    @Test
    @DisplayName("Test generating the initialization vector")
    void testGenerateIv() {
        assertEquals(16, generateIv().getIV().length);
    }

    /**
     * Tests {@link AESUtils#generateIv(String)} method.
     */
    @Test
    @DisplayName("Test generating the initialization vector from a string")
    void testGenerateIvFromString() {
        String input = GeneratorUtils.generateSecureRandomString(16);
        assertEquals(16, generateIv(input).getIV().length);
    }

    /**
     * Tests {@link AESUtils#generateIv()} method.
     */
    @Test
    @DisplayName("Test generating the initialization vector for GCM")
    void testGenerateIvForGCM() {
        assertEquals(16, generateIvForGCM().getIV().length);
    }

    /**
     * Tests {@link AESUtils#generateIv(String)} method.
     */
    @Test
    @DisplayName("Test generating the initialization vector for GCM from a string")
    void testGenerateIvForGCMFromString() {
        String input = GeneratorUtils.generateSecureRandomString(16);
        assertEquals(16, generateIvForGCM(input).getIV().length);
    }

    /**
     * Tests {@link AESUtils#encryptToAESWithCBC(String, SecretKey, IvParameterSpec)} and
     * {@link AESUtils#decryptAESWithCBC(String, SecretKey, IvParameterSpec)} methods.
     */
    @Test
    @DisplayName("Test encrypting and decrypting a string with CBC")
    void testEncryptAndDecryptWithCBC() {
        SecretKey key = generateAESKey(AESKeyBits.KEY_128);
        IvParameterSpec iv = generateIv();
        String encryptedMessage = encryptToAESWithCBC(SAMPLE_MESSAGE, key, iv);
        assertEquals(SAMPLE_MESSAGE, AESUtils.decryptAESWithCBC(encryptedMessage, key, iv));

        // Try exception
        try(MockedStatic<Cipher> cipher = mockStatic(Cipher.class)) {
            cipher.when(() -> Cipher.getInstance(anyString())).thenThrow(NoSuchAlgorithmException.class);
            assertThrows(CryptoException.class, () -> encryptToAESWithCBC(SAMPLE_MESSAGE, key, iv));
        }
    }

    /**
     * Tests {@link AESUtils#encryptToAESWithGCM(String, SecretKey, GCMParameterSpec)} and
     * {@link AESUtils#decryptAESWithGCM(String, SecretKey, GCMParameterSpec)} methods.
     */
    @Test
    @DisplayName("Test encrypting and decrypting a string with GCM")
    void testEncryptAndDecryptWithGCM() {
        SecretKey key = generateAESKey(AESKeyBits.KEY_128);
        GCMParameterSpec iv = generateIvForGCM();
        String encryptedMessage = encryptToAESWithGCM(SAMPLE_MESSAGE, key, iv);
        assertEquals(SAMPLE_MESSAGE, AESUtils.decryptAESWithGCM(encryptedMessage, key, iv));
    }

    /**
     * Tests {@link AESUtils#encryptBytesToAESWithCBC(byte[], SecretKey, IvParameterSpec)} and
     * {@link AESUtils#decryptAESBytesWithCBC(byte[], SecretKey, IvParameterSpec)} methods.
     */
    @Test
    @DisplayName("Test encrypting and decrypting bytes with CBC")
    void testEncryptAndDecryptBytesWithCBC() {
        SecretKey key = generateAESKey(AESKeyBits.KEY_128);
        IvParameterSpec iv = generateIv();
        byte[] encryptedbytes = encryptBytesToAESWithCBC(SAMPLE_MESSAGE.getBytes(StandardCharsets.UTF_8), key, iv);
        assertArrayEquals(SAMPLE_MESSAGE.getBytes(StandardCharsets.UTF_8),
                AESUtils.decryptAESBytesWithCBC(encryptedbytes, key, iv));
    }

    /**
     * Tests {@link AESUtils#encryptBytesToAESWithGCM(byte[], SecretKey, GCMParameterSpec)} and
     * {@link AESUtils#decryptAESBytesWithGCM(byte[], SecretKey, GCMParameterSpec)} methods.
     */
    @Test
    @DisplayName("Test encrypting and decrypting bytes with GCM")
    void testEncryptAndDecryptBytesWithGCM() {
        SecretKey key = generateAESKey(AESKeyBits.KEY_128);
        GCMParameterSpec iv = generateIvForGCM();
        byte[] encryptedBytes = encryptBytesToAESWithGCM(SAMPLE_MESSAGE.getBytes(StandardCharsets.UTF_8), key, iv);
        assertArrayEquals(SAMPLE_MESSAGE.getBytes(StandardCharsets.UTF_8),
                AESUtils.decryptAESBytesWithGCM(encryptedBytes, key, iv));
    }

    /**
     * Tests {@link AESUtils#encryptFileToAESWithCBC(File, File, SecretKey, IvParameterSpec)} and
     * {@link AESUtils#decryptAESFileWithCBC(File, File, SecretKey, IvParameterSpec)} methods.
     */
    @Test
    @DisplayName("Test encrypting and decrypting a file with CBC")
    void testEncryptAndDecryptFileWithCBC() throws IOException {
        // Get test files ready first
        File input = File.createTempFile("input", "cbc-encryption", null);
        File output = File.createTempFile("output", "cbc-encryption", null);
        File decryptedOut = File.createTempFile("decryptedOut", "cbc-encryption", null);

        // Delete temporary files on exit
        input.deleteOnExit();
        output.deleteOnExit();
        decryptedOut.deleteOnExit();

        Files.write(input.toPath(), SAMPLE_MESSAGE.getBytes(StandardCharsets.UTF_8));

        SecretKey key = generateAESKey(AESKeyBits.KEY_128);
        IvParameterSpec iv = generateIv();
        assertDoesNotThrow(() -> encryptFileToAESWithCBC(input, output, key, iv));
        assertDoesNotThrow(() -> decryptAESFileWithCBC(output, decryptedOut, key, iv));
        assertArrayEquals(SAMPLE_MESSAGE.getBytes(StandardCharsets.UTF_8), Files.readAllBytes(decryptedOut.toPath()));

        // Try exception
        try(MockedStatic<Cipher> cipher = mockStatic(Cipher.class)) {
            cipher.when(() -> Cipher.getInstance(anyString())).thenThrow(NoSuchAlgorithmException.class);
            assertThrows(CryptoException.class, () -> encryptFileToAESWithCBC(input, output, key, iv));
        }
    }

    /**
     * Tests {@link AESUtils#encryptFileToAESWithGCM(File, File, SecretKey, GCMParameterSpec)} and
     * {@link AESUtils#decryptAESFileWithGCM(File, File, SecretKey, GCMParameterSpec)} methods.
     */
    @Test
    @DisplayName("Test encrypting and decrypting a file with GCM")
    void testEncryptAndDecryptFileWithGCM() throws IOException {
        // Get test files ready first
        File input = File.createTempFile("input", "cbc-encryption", null);
        File output = File.createTempFile("output", "cbc-encryption", null);
        File decryptedOut = File.createTempFile("decryptedOut", "cbc-encryption", null);

        // Delete temporary files on exit
        input.deleteOnExit();
        output.deleteOnExit();
        decryptedOut.deleteOnExit();

        Files.write(input.toPath(), SAMPLE_MESSAGE.getBytes(StandardCharsets.UTF_8));

        SecretKey key = generateAESKey(AESKeyBits.KEY_128);
        GCMParameterSpec iv = generateIvForGCM();
        assertDoesNotThrow(() -> encryptFileToAESWithGCM(input, output, key, iv));
        assertDoesNotThrow(() -> decryptAESFileWithGCM(output, decryptedOut, key, iv));
        assertArrayEquals(SAMPLE_MESSAGE.getBytes(StandardCharsets.UTF_8), Files.readAllBytes(decryptedOut.toPath()));
    }

    /**
     * Tests {@link AESUtils#encryptToAESWithCBC(String, SecretKey, IvParameterSpec)} and
     * {@link AESUtils#decryptAESWithCBC(String, SecretKey, IvParameterSpec)} methods.
     */
    @Test
    @DisplayName("Test encrypting and decrypting an object with CBC")
    void testEncryptAndDecryptObjectWithCBC() {
        SecretKey key = generateAESKey(AESKeyBits.KEY_128);
        IvParameterSpec iv = generateIv();
        SamplePojo sampleEntry = new SamplePojo();
        sampleEntry.setName("test");
        SealedObject encryptedObj = encryptObjectToAESWithCBC(sampleEntry, key, iv);
        assertEquals(sampleEntry, AESUtils.decryptAESObjectWithCBC(encryptedObj, key, iv));

        // Try exception
        try(MockedStatic<Cipher> cipher = mockStatic(Cipher.class)) {
            cipher.when(() -> Cipher.getInstance(anyString())).thenThrow(NoSuchAlgorithmException.class);
            assertThrows(CryptoException.class, () -> encryptObjectToAESWithCBC(sampleEntry, key, iv));
        }
    }

    /**
     * Tests {@link AESUtils#encryptToAESWithGCM(String, SecretKey, GCMParameterSpec)} and
     * {@link AESUtils#decryptAESWithGCM(String, SecretKey, GCMParameterSpec)} methods.
     */
    @Test
    @DisplayName("Test encrypting and decrypting an object with GCM")
    void testEncryptAndDecryptObjectWithGCM() {
        SecretKey key = generateAESKey(AESKeyBits.KEY_128);
        GCMParameterSpec iv = generateIvForGCM();
        SamplePojo sampleEntry = new SamplePojo();
        sampleEntry.setName("test");
        SealedObject encryptedObj = encryptObjectToAESWithGCM(sampleEntry, key, iv);
        assertEquals(sampleEntry, AESUtils.decryptAESObjectWithGCM(encryptedObj, key, iv));
    }
}
