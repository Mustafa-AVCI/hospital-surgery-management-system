/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Mustafa AVCI
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * PasswordUtil - Şifre hashleme ve doğrulama için yardımcı sınıf SHA-256
 * algoritması kullanır
 */
public class PasswordUtil {

    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    public static String hashPassword(String password) {
        try {
            // Salt oluştur
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            // Hash oluştur
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Salt ve hash'i birleştir
            String saltString = Base64.getEncoder().encodeToString(salt);
            String hashString = Base64.getEncoder().encodeToString(hashedPassword);

            return saltString + ":" + hashString;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }

    /**
     * Şifreyi doğrular
     *
     * @param password Kullanıcının girdiği şifre
     * @param storedHash Veritabanında saklanan hash (salt:hash formatında)
     * @return Şifre doğru mu?
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Salt ve hash'i ayır
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHashBytes = Base64.getDecoder().decode(parts[1]);

            // Girilen şifreyi aynı salt ile hashle
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Karşılaştır
            return MessageDigest.isEqual(hashedPassword, storedHashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Basit hash (salt olmadan) - Geriye dönük uyumluluk için NOT: Güvenli
     * değil, sadece eski sistemler için
     */
    public static String simpleHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }

    /**
     * Şifre güçlülüğünü kontrol eder
     *
     * @param password Kontrol edilecek şifre
     * @return Şifre güçlü mü?
     */
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    /**
     * Şifre güçlülük mesajı döndürür
     */
    public static String getPasswordStrengthMessage(String password) {
        if (password == null || password.isEmpty()) {
            return "Şifre boş olamaz";
        }

        if (password.length() < 8) {
            return "Şifre en az 8 karakter olmalıdır";
        }

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[^A-Za-z0-9].*");

        if (!hasUpper) {
            return "Şifre en az bir büyük harf içermelidir";
        }
        if (!hasLower) {
            return "Şifre en az bir küçük harf içermelidir";
        }
        if (!hasDigit) {
            return "Şifre en az bir rakam içermelidir";
        }
        if (!hasSpecial) {
            return "Şifre en az bir özel karakter içermelidir";
        }

        return "Güçlü şifre";
    }
}
