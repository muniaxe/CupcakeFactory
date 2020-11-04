package olskercupcakes.domain.user;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.Arrays;

public class User {
    private static final int PASSWORD_ITTERATIONS = 65536;
    private static final int PASSWORD_LENGTH = 256; //32 bytes
    private static final SecretKeyFactory PASSWORD_FACTORY;

    static {
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        PASSWORD_FACTORY = factory;
    }

    private final int id;
    private final String email;
    private final LocalDateTime createdAt;
    private final byte[] salt;
    private final byte[] secret;
    private final int balance;
    private final boolean admin;

    public User(int id, String email, LocalDateTime createdAt, byte[] salt, byte[] secret, int balance, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
        this.salt = salt;
        this.secret = secret;
        this.balance = balance;
        this.admin = isAdmin;
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] calculateSecret(byte[] salt, String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt,
                PASSWORD_ITTERATIONS,
                PASSWORD_LENGTH);
        try {
            return PASSWORD_FACTORY.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isPasswordCorrect(String password) {
        return Arrays.equals(this.secret, calculateSecret(salt, password));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", salt=" + byteArrayToHex(salt) +
                ", secret=" + byteArrayToHex(secret) +
                '}';
    }

    //thanks: https://stackoverflow.com/a/13006907
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static int getPasswordItterations() {
        return PASSWORD_ITTERATIONS;
    }

    public static int getPasswordLength() {
        return PASSWORD_LENGTH;
    }

    public static SecretKeyFactory getPasswordFactory() {
        return PASSWORD_FACTORY;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getSecret() {
        return secret;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isAdmin() {
        return admin;
    }
}
