package p17;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class Koneksi {

    private static final String URL = "jdbc:mysql://localhost:3306/sistem_parkir?useSSL=false&serverTimezone=Asia/Jakarta";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getKoneksi() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL tidak ditemukan: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Koneksi database gagal: " + e.getMessage());
            return null;
        }
    }

    public static String sha256(String teks) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(teks.getBytes(StandardCharsets.UTF_8));

            StringBuilder hasil = new StringBuilder();
            for (byte b : hash) {
                hasil.append(String.format("%02x", b));
            }

            return hasil.toString();
        } catch (Exception e) {
            throw new RuntimeException("Gagal membuat hash password: " + e.getMessage());
        }
    }
}