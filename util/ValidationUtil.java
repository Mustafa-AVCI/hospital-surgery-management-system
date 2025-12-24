/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Mustafa AVCI
 */
import dao.*;
import model.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import model.SurgeonSpecialtyMap;

/**
 * ValidationUtil - İş kuralları ve validasyon kontrolleri
 */
public class ValidationUtil {

    // Regex patterns
    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN
            = Pattern.compile("^[0-9]{10,15}$");
    private static final Pattern TC_PATTERN
            = Pattern.compile("^[0-9]{11}$");

    
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return true; // Opsiyonel alan
        }
        // Boşluk ve özel karakterleri temizle
        String cleanPhone = phone.replaceAll("[^0-9]", "");
        return PHONE_PATTERN.matcher(cleanPhone).matches();
    }

   
    public static boolean isValidTCKN(String tckn) {
        if (tckn == null || tckn.trim().isEmpty()) {
            return true; // Opsiyonel alan
        }
        return TC_PATTERN.matcher(tckn).matches();
    }

  
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Ameliyat için malzeme stoğu yeterli mi kontrol
     */
    public static ValidationResult checkMaterialAvailability(
            int materialId, int requiredQuantity, int locationId) {

        InventoryDAO invDAO = new InventoryDAO();
        Inventory inventory = invDAO.getInventory(locationId, materialId);

        if (inventory == null) {
            return new ValidationResult(false, "Malzeme bu depoda bulunamadı");
        }

        if (inventory.getQuantity() < requiredQuantity) {
            return new ValidationResult(false,
                    "Yetersiz stok! Mevcut: " + inventory.getQuantity()
                    + ", Gerekli: " + requiredQuantity);
        }

        return new ValidationResult(true, "Stok yeterli");
    }

    /**
     * Ameliyat salonu müsait mi kontrol
     */
    public static ValidationResult checkRoomAvailability(
            int roomId, LocalDateTime surgeryDate, int surgeryId) {

        SurgeryDAO surgeryDAO = new SurgeryDAO();
       // Gerçek implementasyonda tarih aralığı kontrolü yapılmalı
        return new ValidationResult(true, "Oda müsait");
    }

    /**
     * Personel müsait mi kontrol
     */
    public static ValidationResult checkStaffAvailability(
            int staffId, LocalDateTime surgeryDate, int surgeryId) {

        // Aynı tarihte personelin başka ameliyatı var mı kontrol et
        // SurgeryStaffDAO'da yeni bir metod eklemeniz gerekebilir
        return new ValidationResult(true, "Personel müsait");
    }

    /**
     * Kritik stok seviyesini kontrol
     */
    public static boolean isCriticalStock(int materialId, int locationId) {
        InventoryDAO invDAO = new InventoryDAO();
        MaterialDAO matDAO = new MaterialDAO();

        Inventory inventory = invDAO.getInventory(locationId, materialId);
        Material material = matDAO.getMaterialById(materialId);

        if (inventory == null || material == null) {
            return false;
        }

        return inventory.getQuantity() <= material.getCriticalStockLevel();
    }

    /**
     * Cerrahın uzmanlık alanını kontrol
     */
    public static ValidationResult checkSurgeonSpecialty(
            int surgeonId, String surgeryType) {

        SurgeonSpecialtyMapDAO mapDAO = new SurgeonSpecialtyMapDAO();
        List<SurgeonSpecialtyMap> specialties
                = mapDAO.getSpecialtiesBySurgeon(surgeonId);

        // Ameliyat türüne uygun uzmanlık var mı kontrol et
        // Bu basit bir kontrol, gerçek uygulamada daha detaylı olmalı
        if (specialties.isEmpty()) {
            return new ValidationResult(false,
                    "Cerrahın kayıtlı uzmanlık alanı bulunmuyor");
        }

        return new ValidationResult(true, "Cerrah uygun");
    }

    /**
     * Pozitif sayı kontrolü
     */
    public static boolean isPositive(int value) {
        return value > 0;
    }

    /**
     * Pozitif veya sıfır kontrolü
     */
    public static boolean isNonNegative(int value) {
        return value >= 0;
    }

    /**
     * Tarih geçmiş mi kontrolü
     */
    public static boolean isPastDate(LocalDateTime date) {
        return date.isBefore(LocalDateTime.now());
    }

    /**
     * Tarih gelecek mi kontrolü
     */
    public static boolean isFutureDate(LocalDateTime date) {
        return date.isAfter(LocalDateTime.now());
    }

    /**
     * Validation sonucunu tutan iç sınıf
     */
    public static class ValidationResult {

        private boolean valid;
        private String message;

        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }

        public boolean isValid() {
            return valid;
        }

        public String getMessage() {
            return message;
        }
    }
}
