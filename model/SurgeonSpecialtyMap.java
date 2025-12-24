package model;

/**
 * SurgeonSpecialtyMap Model - Cerrah ve uzmanlık alanları arasındaki ilişki
 */
public class SurgeonSpecialtyMap {

    private int surgeonId;
    private int specialtyId;
    private String surgeonName;      // JOIN sonucu için
    private String specialtyName;    // JOIN sonucu için

    // Constructors
    public SurgeonSpecialtyMap() {
    }

    public SurgeonSpecialtyMap(int surgeonId, int specialtyId) {
        this.surgeonId = surgeonId;
        this.specialtyId = specialtyId;
    }

    public SurgeonSpecialtyMap(int surgeonId, int specialtyId,
            String surgeonName, String specialtyName) {
        this.surgeonId = surgeonId;
        this.specialtyId = specialtyId;
        this.surgeonName = surgeonName;
        this.specialtyName = specialtyName;
    }

    // Getters and Setters
    public int getSurgeonId() {
        return surgeonId;
    }

    public void setSurgeonId(int surgeonId) {
        this.surgeonId = surgeonId;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getSurgeonName() {
        return surgeonName;
    }

    public void setSurgeonName(String surgeonName) {
        this.surgeonName = surgeonName;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    @Override
    public String toString() {
        return "SurgeonSpecialtyMap{"
                + "surgeonId=" + surgeonId
                + ", specialtyId=" + specialtyId
                + ", surgeonName='" + surgeonName + '\''
                + ", specialtyName='" + specialtyName + '\''
                + '}';
    }
}
