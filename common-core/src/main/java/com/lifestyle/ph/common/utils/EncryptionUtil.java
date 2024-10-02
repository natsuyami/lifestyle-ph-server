package com.lifestyle.ph.common.utils;

import com.lifestyle.ph.common.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionUtil.class);

    @Value("${spring.key.encryption.path}")
    private String pathPublic;

    @Value("${spring.key.decryption.path}")
    private String pathPrivate;

    @Value("${spring.key.secret.aes.path}")
    private String pathSecret;

    @Value("${spring.key.base.path}")
    private String keyBasePath;

    @Value("${spring.key.iv.code}")
    private String ivCode;

    public String encryptViaAES(String value) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivCode.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, getAESKey(), ivParameterSpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            return "";
        }
    }

    public String decryptViaAES(String encryptedMessage) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivCode.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, getAESKey(), ivParameterSpec);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encryptViaRSA(String value) throws Exception {
        try {
            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
            byte[] secretMessageBytes = value.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);

            return Base64.getEncoder().encodeToString(encryptedMessageBytes);
        } catch (Exception e) {
            LOGGER.error("Failed to Encrypt the value using the Key");
            e.printStackTrace();
            throw new VerificationException(e.getMessage());
        }
    }

    public String decryptViaRSA(String encryptedMessage) throws Exception {
        try {
            Cipher decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
            byte[] decryptedMessageBytes = decryptCipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("Failed to Decrypt the value using the Key");
            e.printStackTrace();
            throw new VerificationException(e.getMessage());
        }
    }

    public void generateKeys() {

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();

            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            Files.write(Paths.get(keyBasePath.concat(pathPublic)), publicKey.getEncoded());

            PKCS8EncodedKeySpec pkcs8Spec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            byte[] pkcs8EncodedKey = pkcs8Spec.getEncoded();
            Files.write(Paths.get(keyBasePath.concat(pathPrivate)), pkcs8EncodedKey);
        } catch(Exception e) {
            LOGGER.error("Failed to Generate Keys");
            e.printStackTrace();
            throw new VerificationException(e.getMessage());
        }
    }

    public void generateAESSecret() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");

            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();

            try (FileOutputStream fos = new FileOutputStream(keyBasePath.concat(pathSecret))) {
                fos.write(secretKey.getEncoded());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PublicKey getPublicKey() {
        byte[] publicKeyBytes;
        try {
            publicKeyBytes = Files.readAllBytes((Paths.get(keyBasePath.concat(pathPublic))));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            LOGGER.error("Failed to Get the Public Key");
            e.printStackTrace();
            throw new VerificationException(e.getMessage());
        }
    }

    private PrivateKey getPrivateKey() {
        byte[] privateKeyBytes;
        try {
            privateKeyBytes = Files.readAllBytes((Paths.get(keyBasePath.concat(pathPrivate))));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpecs = new PKCS8EncodedKeySpec(privateKeyBytes);
            return keyFactory.generatePrivate(privateKeySpecs);
        } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            LOGGER.error("Failed to Get the Private Key");
            e.printStackTrace();
            throw new VerificationException(e.getMessage());
        }
    }

    private SecretKey getAESKey() {
        File secretFilePath = new File(keyBasePath.concat(pathSecret));
        byte[] secretKeyBytes;
        try {
            secretKeyBytes = Files.readAllBytes(secretFilePath.toPath());
            return new SecretKeySpec(secretKeyBytes, 0, secretKeyBytes.length, "AES");
        } catch (Exception e) {
            LOGGER.error("Failed to Get the Secret Key");
            e.printStackTrace();
            throw new VerificationException(e.getMessage());
        }
    }

    public String getPrivateKeyAsString() {
        PrivateKey privateKey = getPrivateKey();
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public String getAESKeyAsString() {
        SecretKey secretKey = getAESKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
