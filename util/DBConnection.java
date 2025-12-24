package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // 1) BURASI: DOĞRU URL
    private static final String URL =
        "jdbc:sqlserver://localhost:1433;"
      + "databaseName=HospitalSurgeryDB;"
      + "encrypt=false;"
      + "trustServerCertificate=true;";

    // 2) KULLANICI BİLGİLERİ
    private static final String USER = "sa";       
    private static final String PASSWORD = "Aa123456*";  

    public static Connection getConnection() {
        try {
            // 3) DRIVER’I YÜKLE
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Bağlantı başarılı (DBConnection içi)!");
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("SQL Server JDBC driver bulunamadı!");
            e.printStackTrace();
            return null;

        } catch (SQLException e) {
            System.out.println("SQL Server bağlantı hatası!");
            e.printStackTrace();   // HATAYI GÖRMEK İÇİN ÇOK ÖNEMLİ
            return null;
        }
    }
}
