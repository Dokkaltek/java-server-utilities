package io.github.dokkaltek.util;

import io.github.dokkaltek.constant.RSAKeyBits;
import io.github.dokkaltek.exception.CryptoException;
import io.github.dokkaltek.exception.GenericException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import static io.github.dokkaltek.util.RSAUtils.readPrivateKeyFromFile;
import static io.github.dokkaltek.util.RSAUtils.readPublicKeyFromFile;
import static io.github.dokkaltek.util.RSAUtils.writePrivateKeyToFile;
import static io.github.dokkaltek.util.RSAUtils.writePublicKeyToFile;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

/**
 * Tests for {@link RSAUtils} class.
 */
class RSAUtilsTest {

    /**
     * Tests {@link RSAUtils#generateRSAKeyPair(RSAKeyBits)} method.
     */
    @Test
    @DisplayName("Test generating an RSA key pair")
    void testGenerateRSAKeyPair() {
        KeyPair result = RSAUtils.generateRSAKeyPair(RSAKeyBits.KEY_2048);
        assertNotNull(result);
        assertNotNull(result.getPrivate());
        assertNotNull(result.getPublic());

        try (MockedStatic<KeyPairGenerator> mockedStatic = mockStatic(KeyPairGenerator.class)) {
            mockedStatic.when(() -> KeyPairGenerator.getInstance(anyString())).thenThrow(NoSuchAlgorithmException.class);
            assertThrows(CryptoException.class, () -> RSAUtils.generateRSAKeyPair(RSAKeyBits.KEY_2048));
        }
    }

    /**
     * Tests {@link RSAUtils#writePublicKeyToFile(PublicKey, File)} and
     * {@link RSAUtils#writePrivateKeyToFile(PrivateKey, File)} methods, along with the
     * {@link RSAUtils#readPublicKeyFromFile(File)} and {@link RSAUtils#readPrivateKeyFromFile(File)} ones.
     */
    @Test
    @DisplayName("Test writing a key to a file")
    void testWriteKeyToFile() throws IOException {
        File publicFile = File.createTempFile("publicKey", "rsa-test");
        File privateFile = File.createTempFile("privateKey", "rsa-test");
        publicFile.deleteOnExit();
        privateFile.deleteOnExit();

        KeyPair result = RSAUtils.generateRSAKeyPair(RSAKeyBits.KEY_2048);
        PublicKey pubKey = result.getPublic();
        PrivateKey privKey = result.getPrivate();

        // Test results
        writePublicKeyToFile(pubKey, publicFile);
        writePrivateKeyToFile(privKey, privateFile);
        assertEquals(result.getPublic(), readPublicKeyFromFile(publicFile));
        assertEquals(result.getPrivate(), readPrivateKeyFromFile(privateFile));

        // Null cases
        assertThrows(NullPointerException.class, () -> writePublicKeyToFile(null, publicFile));
        assertThrows(NullPointerException.class, () -> writePublicKeyToFile(pubKey, null));
        assertThrows(NullPointerException.class, () -> writePrivateKeyToFile(privKey, null));
        assertThrows(NullPointerException.class, () -> writePrivateKeyToFile(null, privateFile));
        assertThrows(NullPointerException.class, () -> readPublicKeyFromFile(null));
        assertThrows(NullPointerException.class, () -> readPrivateKeyFromFile(null));

        // Exceptions
        try (MockedStatic<Files> mockedStatic = mockStatic(Files.class)) {
            mockedStatic.when(() -> Files.write(any(Path.class), any(byte[].class))).thenThrow(IOException.class);
            mockedStatic.when(() -> Files.readAllBytes(any(Path.class))).thenThrow(IOException.class);
            assertThrows(GenericException.class, () -> writePublicKeyToFile(pubKey, publicFile));
            assertThrows(GenericException.class, () -> writePrivateKeyToFile(privKey, privateFile));
            assertThrows(GenericException.class, () -> readPublicKeyFromFile(publicFile));
            assertThrows(GenericException.class, () -> readPrivateKeyFromFile(privateFile));
        }

        try (MockedStatic<KeyFactory> mockedStatic = mockStatic(KeyFactory.class)) {
            mockedStatic.when(() -> KeyFactory.getInstance(anyString())).thenThrow(NoSuchAlgorithmException.class);
            assertThrows(CryptoException.class, () -> readPublicKeyFromFile(publicFile));
            assertThrows(CryptoException.class, () -> readPrivateKeyFromFile(privateFile));
        }
    }

    /**
     * Tests {@link RSAUtils#encryptToRSA(String, PublicKey)} and
     * {@link RSAUtils#decryptRSA(String, PrivateKey)} methods.
     */
    @Test
    @DisplayName("Test encrypting and decrypting a string with RSA")
    void testEncryptToRSA() {
        String sampleString = "Hello world!";
        KeyPair keyPair = RSAUtils.generateRSAKeyPair(RSAKeyBits.KEY_2048);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String encryptedMessage = RSAUtils.encryptToRSA(sampleString, publicKey);
        assertEquals(sampleString, RSAUtils.decryptRSA(encryptedMessage, privateKey));

        try (MockedStatic<Cipher> mockedStatic = mockStatic(Cipher.class)) {
            mockedStatic.when(() -> Cipher.getInstance(anyString())).thenThrow(NoSuchAlgorithmException.class);
            assertThrows(CryptoException.class, () -> RSAUtils.encryptToRSA(sampleString, publicKey));
            assertThrows(CryptoException.class, () -> RSAUtils.decryptRSA(encryptedMessage, privateKey));
        }
    }

    /**
     * Tests {@link RSAUtils#encryptBytesToRSA(byte[], PublicKey)} and
     * {@link RSAUtils#decryptRSABytes(byte[], PrivateKey)} methods.
     */
    @Test
    @DisplayName("Test encrypting and decrypting bytes with RSA")
    void testEncryptBytesToRSA() {
        byte[] sampleBytes = "Hello world!".getBytes(StandardCharsets.UTF_8);
        KeyPair keyPair = RSAUtils.generateRSAKeyPair(RSAKeyBits.KEY_2048);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] encryptedBytes = RSAUtils.encryptBytesToRSA(sampleBytes, publicKey);
        assertArrayEquals(sampleBytes, RSAUtils.decryptRSABytes(encryptedBytes, privateKey));
    }
}
