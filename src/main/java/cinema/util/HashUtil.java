package cinema.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.log4j.Logger;

public class HashUtil {
    private static final Logger LOGGER = Logger.getLogger(HashUtil.class);

    public static byte[] getRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(salt);
            byte[] diges = messageDigest.digest(password.getBytes());
            for (byte b : diges) {
                hashedPassword.append(String.format("%02x",b));
            }

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Failed hashing password", e);
        }

        return hashedPassword.toString();
    }
}
