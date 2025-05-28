/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author HuyDV
 */
public class PasswordUtil {
     private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int SALT_LENGTH = 16; // 16 bytes = 128 bits
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256; // 256 bits
    
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] hashPassword(char[] password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password, salt, PasswordUtil.ITERATION_COUNT, PasswordUtil.KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        return factory.generateSecret(spec).getEncoded();
    }
}
