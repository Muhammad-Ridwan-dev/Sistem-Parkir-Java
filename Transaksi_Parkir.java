/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package p17;

import java.awt.Cursor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transaksi_Parkir extends javax.swing.JFrame {
    
    private String usernameLogin;
    private String hakAksesLogin;

    private int nomorUrutTransaksi = 1;
    private long durasiMenit = 0;
    private int totalBiaya = 0;

    private final DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter formatJam = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter formatTanggalJam = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    
    private void tampilkanTransaksiDariDatabase() {
        DefaultTableModel model = (DefaultTableModel) tblTransaksiHariIni.getModel();
        model.setRowCount(0);

        try {
            Connection conn = Koneksi.getKoneksi();

            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Koneksi database gagal.");
                return;
            }

            String sql = "SELECT no_transaksi, plat_nomor, "
                    + "CONCAT(DATE_FORMAT(tanggal_masuk, '%d-%m-%Y'), ' ', TIME_FORMAT(jam_masuk, '%H:%i')) AS masuk, "
                    + "CONCAT(DATE_FORMAT(tanggal_keluar, '%d-%m-%Y'), ' ', TIME_FORMAT(jam_keluar, '%H:%i')) AS keluar, "
                    + "durasi_text, total_biaya "
                    + "FROM transaksi_parkir "
                    + "ORDER BY tanggal_keluar DESC, jam_keluar DESC";

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("no_transaksi"),
                    rs.getString("plat_nomor"),
                    rs.getString("masuk"),
                    rs.getString("keluar"),
                    rs.getString("durasi_text"),
                    formatRupiah(rs.getInt("total_biaya"))
                });
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan transaksi: " + e.getMessage());
        }
    }

    public Transaksi_Parkir() {
        initComponents();

        this.usernameLogin = "admin";
        this.hakAksesLogin = "Admin";

        setSize(1380, 780);
        setLocationRelativeTo(null);
        setResizable(false);

        tampilkanHakAksesLogin();
        cmbPetugas.setSelectedItem(this.hakAksesLogin);

        pasangEventTombol();
        rapikanTampilan();
        loadDataPetugas();
        tampilkanTransaksiSementara();
        aturHakAkses();
    }

    public Transaksi_Parkir(String username, String hakAkses) {
        initComponents();

        this.usernameLogin = username;
        this.hakAksesLogin = hakAkses;

        setSize(1380, 780);
        setLocationRelativeTo(null);
        setResizable(false);

        tampilkanHakAksesLogin();
        cmbPetugas.setSelectedItem(this.hakAksesLogin);

        pasangEventTombol();
        rapikanTampilan();
        loadDataPetugas();
        tampilkanTransaksiSementara();
        aturHakAkses();
    }
    
    private void tampilkanHakAksesLogin() {
        if (hakAksesLogin == null || hakAksesLogin.trim().isEmpty()) {
            lblUserLogin.setText("Admin");
        } else {
            lblUserLogin.setText(hakAksesLogin);
        }
    }
    
    private void pasangEventTombol() {
        btnDashboard.addActionListener(evt -> btnDashboardActionPerformed(evt));
        btnTransaksi.addActionListener(evt -> btnTransaksiActionPerformed(evt));
        btnLaporan.addActionListener(evt -> btnLaporanActionPerformed(evt));
        btnLogout.addActionListener(evt -> btnLogoutActionPerformed(evt));
        btnExit.addActionListener(evt -> btnExitActionPerformed(evt));

        btnHitungDurasi.addActionListener(evt -> btnHitungDurasiActionPerformed(evt));
        btnHitungTarif.addActionListener(evt -> btnHitungTarifActionPerformed(evt));
        btnSimpanTransaksi2.addActionListener(evt -> btnSimpanTransaksiActionPerformed(evt));
        btnReset.addActionListener(evt -> btnResetActionPerformed(evt));
        btnCetakStruk.addActionListener(evt -> btnCetakStrukActionPerformed(evt));
        btnLihatSemua.addActionListener(evt -> btnLihatSemuaActionPerformed(evt));
    }

    private void rapikanTampilan() {
        javax.swing.JButton[] tombol = {
            btnDashboard, btnMasterData, btnTransaksi, btnLaporan,
            btnLogout, btnExit, btnHitungDurasi, btnHitungTarif,
            btnSimpanTransaksi2, btnReset, btnCetakStruk, btnLihatSemua
        };

        for (javax.swing.JButton item : tombol) {
            item.setFocusPainted(false);
            item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        lblUserLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserLogin.setOpaque(true);
        cmbJenisKendaraan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmbPetugas.setCursor(new Cursor(Cursor.HAND_CURSOR));

        txtNoTransaksi.setEditable(false);
        txtDurasiParkir.setEditable(false);
        txtTarifAwal.setEditable(false);
        txtTarifProgresif.setEditable(false);
        txtTotalBiaya.setEditable(false);
        
        // Samakan icon informasi tarif dengan icon yang berhasil di Laporan
        lblIconMotor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconMotor.setText(
                "<html>"
                + "<table width='48' height='48' bgcolor='#55B83F' cellspacing='0' cellpadding='0'>"
                + "<tr>"
                + "<td align='center' valign='middle'>"
                + "<font face='Segoe UI Symbol' color='white' size='7'>&#127949;&#65038;</font>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</html>"
        );

        lblIcoMobil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcoMobil.setText(
                "<html>"
                + "<table width='48' height='48' bgcolor='#1976D2' cellspacing='0' cellpadding='0'>"
                + "<tr>"
                + "<td align='center' valign='middle'>"
                + "<font face='Segoe UI Symbol' color='white' size='7'>&#128664;</font>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</html>"
        );
        
        btnSimpanTransaksi2.setText(
                "<html>"
                + "<table cellpadding='0' cellspacing='0' border='0'>"
                + "<tr>"
                + "<td><font face='Segoe MDL2 Assets' color='#FFFFFF' size='4'>&#xE74E;</font></td>"
                + "<td width='6'></td>"
                + "<td nowrap='nowrap'><b><font face='Segoe UI' color='#FFFFFF' size='3'>Simpan&nbsp;Transaksi</font></b></td>"
                + "</tr>"
                + "</table>"
                + "</html>"
        );

        rapikanTabel(tblTransaksiHariIni);
    }

    private void rapikanTabel(JTable tabel) {
        tabel.setRowHeight(28);
        tabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tabel.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        tabel.setGridColor(new java.awt.Color(226, 232, 240));
        tabel.setSelectionBackground(new java.awt.Color(219, 234, 254));
        tabel.setSelectionForeground(new java.awt.Color(15, 23, 42));
    }

    private void tampilkanTransaksiSementara() {
        tblTransaksiHariIni.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"No Transaksi", "Plat Nomor", "Masuk", "Keluar", "Durasi", "Total"}
        ));

        tampilkanTransaksiDariDatabase();
        resetFormTransaksi();
        updateTotalTransaksi();
    }

    private void aturHakAkses() {
        if ("Operator".equalsIgnoreCase(hakAksesLogin)) {
            btnMasterData.setEnabled(false);
            btnMasterData.setText("Master Data");
        } else {
            btnMasterData.setEnabled(true);
        }
    }
    
    private String buatNoTransaksi() {
        String tanggal = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "TRX" + tanggal;

        try {
            Connection conn = Koneksi.getKoneksi();

            if (conn == null) {
                return prefix + "001";
            }

            String sql = "SELECT no_transaksi FROM transaksi_parkir "
                    + "WHERE no_transaksi LIKE ? "
                    + "ORDER BY no_transaksi DESC "
                    + "LIMIT 1";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, prefix + "%");

            ResultSet rs = pst.executeQuery();

            int nomor = 1;
            if (rs.next()) {
                String terakhir = rs.getString("no_transaksi");
                String angka = terakhir.substring(prefix.length());
                nomor = Integer.parseInt(angka) + 1;
            }

            rs.close();
            pst.close();
            conn.close();

            return prefix + String.format("%03d", nomor);

        } catch (Exception e) {
            return prefix + "001";
        }
    }

    private String formatRupiah(int angka) {
        return "Rp " + String.format("%,d", angka).replace(",", ".");
    }

    private int angkaDariRupiah(String teks) {
        if (teks == null || teks.trim().isEmpty()) {
            return 0;
        }

        String angka = teks.replace("Rp", "")
                .replace(".", "")
                .replace(",", "")
                .trim();

        if (angka.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(angka);
    }

    private String formatDurasi(long menitTotal) {
        long jam = menitTotal / 60;
        long menit = menitTotal % 60;

        if (jam <= 0) {
            return menit + " menit";
        }

        if (menit == 0) {
            return jam + " jam";
        }

        return jam + " jam " + menit + " menit";
    }

    private LocalDateTime ambilWaktuMasuk() {
        String tanggal = txtTanggalMasuk.getText().trim();
        String jam = txtJamMasuk.getText().trim();

        return LocalDateTime.parse(tanggal + " " + jam, formatTanggalJam);
    }

    private LocalDateTime ambilWaktuKeluar() {
        String tanggal = txtTanggalKeluar.getText().trim();
        String jam = txtJamKeluar.getText().trim();

        return LocalDateTime.parse(tanggal + " " + jam, formatTanggalJam);
    }

    private void resetFormTransaksi() {
        LocalDateTime sekarang = LocalDateTime.now();
        LocalDateTime satuJamLalu = sekarang.minusHours(1);

        txtNoTransaksi.setText(buatNoTransaksi());
        txtPlatNomor.setText("");
        cmbJenisKendaraan.setSelectedIndex(0);

        txtTanggalMasuk.setText(satuJamLalu.format(formatTanggal));
        txtJamMasuk.setText(satuJamLalu.format(formatJam));

        txtTanggalKeluar.setText(sekarang.format(formatTanggal));
        txtJamKeluar.setText(sekarang.format(formatJam));

        txtDurasiParkir.setText("");
        txtTarifAwal.setText("Rp 0");
        txtTarifProgresif.setText("Rp 0");
        txtTotalBiaya.setText("Rp 0");

        cmbPetugas.setSelectedItem(hakAksesLogin);

        durasiMenit = 0;
        totalBiaya = 0;

        txtPlatNomor.requestFocus();
    }

    private void updateTotalTransaksi() {
        DefaultTableModel model = (DefaultTableModel) tblTransaksiHariIni.getModel();

        int total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += angkaDariRupiah(String.valueOf(model.getValueAt(i, 5)));
        }

        lblTotalTransaksi.setText("Total   :  " + formatRupiah(total));
    }

    private boolean formTransaksiValid() {
        if (txtNoTransaksi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transaksi belum dibuat.");
            return false;
        }

        if (txtPlatNomor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Plat nomor tidak boleh kosong.");
            txtPlatNomor.requestFocus();
            return false;
        }

        if (txtDurasiParkir.getText().trim().isEmpty() || durasiMenit <= 0) {
            JOptionPane.showMessageDialog(this, "Hitung durasi parkir terlebih dahulu.");
            return false;
        }

        if (totalBiaya <= 0) {
            JOptionPane.showMessageDialog(this, "Hitung tarif parkir terlebih dahulu.");
            return false;
        }

        return true;
    }
    
    private void hitungDurasiParkir() {
        try {
            LocalDateTime masuk = ambilWaktuMasuk();
            LocalDateTime keluar = ambilWaktuKeluar();

            if (!keluar.isAfter(masuk)) {
                JOptionPane.showMessageDialog(this, "Jam keluar harus lebih besar dari jam masuk.");
                durasiMenit = 0;
                txtDurasiParkir.setText("");
                return;
            }

            durasiMenit = ChronoUnit.MINUTES.between(masuk, keluar);

            if (durasiMenit <= 0) {
                JOptionPane.showMessageDialog(this, "Durasi parkir tidak valid.");
                txtDurasiParkir.setText("");
                return;
            }

            txtDurasiParkir.setText(formatDurasi(durasiMenit));

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Format tanggal atau jam salah.\n"
                    + "Tanggal gunakan format: dd-MM-yyyy\n"
                    + "Jam gunakan format: HH:mm\n\n"
                    + "Contoh tanggal: 16-06-2026\n"
                    + "Contoh jam: 14:30"
            );
        }
    }

    private int tarifAwalBerdasarkanJenis(String jenis) {
        if ("Motor".equalsIgnoreCase(jenis)) {
            return 2000;
        } else if ("Mobil".equalsIgnoreCase(jenis)) {
            return 5000;
        } else if ("Bus".equalsIgnoreCase(jenis)) {
            return 8000;
        } else if ("Truk".equalsIgnoreCase(jenis)) {
            return 10000;
        }

        return 0;
    }

    private int tarifProgresifBerdasarkanJenis(String jenis) {
        if ("Motor".equalsIgnoreCase(jenis)) {
            return 1000;
        } else if ("Mobil".equalsIgnoreCase(jenis)) {
            return 2000;
        } else if ("Bus".equalsIgnoreCase(jenis)) {
            return 3000;
        } else if ("Truk".equalsIgnoreCase(jenis)) {
            return 4000;
        }

        return 0;
    }

    private void hitungTarifParkir() {
        if (durasiMenit <= 0) {
            hitungDurasiParkir();
        }

        if (durasiMenit <= 0) {
            return;
        }

        String jenis = cmbJenisKendaraan.getSelectedItem().toString();

        int tarifAwal = 0;
        int tarifProgresifPerJam = 0;

        try {
            Connection conn = Koneksi.getKoneksi();

            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Koneksi database gagal.");
                return;
            }

            String sql = "SELECT tarif_awal, tarif_per_jam FROM tarif_parkir "
                    + "WHERE jenis_kendaraan = ? AND status = 'Aktif'";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, jenis);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tarifAwal = rs.getInt("tarif_awal");
                tarifProgresifPerJam = rs.getInt("tarif_per_jam");
            } else {
                JOptionPane.showMessageDialog(this, "Tarif untuk jenis kendaraan ini belum ada di database.");
                return;
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil tarif: " + e.getMessage());
            return;
        }

        long jamTagihan = (durasiMenit + 59) / 60;
        if (jamTagihan < 1) {
            jamTagihan = 1;
        }

        int biayaProgresif = 0;
        if (jamTagihan > 1) {
            biayaProgresif = (int) ((jamTagihan - 1) * tarifProgresifPerJam);
        }

        totalBiaya = tarifAwal + biayaProgresif;

        txtTarifAwal.setText(formatRupiah(tarifAwal));
        txtTarifProgresif.setText(formatRupiah(biayaProgresif));
        txtTotalBiaya.setText(formatRupiah(totalBiaya));
    }
    
    private java.sql.Date tanggalSql(String teks) {
        LocalDate tanggal = LocalDate.parse(teks, formatTanggal);
        return java.sql.Date.valueOf(tanggal);
    }

    private java.sql.Time jamSql(String teks) {
        LocalTime jam = LocalTime.parse(teks, formatJam);
        return java.sql.Time.valueOf(jam);
    }
    
    private void btnHitungDurasiActionPerformed(java.awt.event.ActionEvent evt) {
        hitungDurasiParkir();
    }

    private void btnHitungTarifActionPerformed(java.awt.event.ActionEvent evt) {
        hitungTarifParkir();
    }

    private void btnSimpanTransaksiActionPerformed(java.awt.event.ActionEvent evt) {
        if (durasiMenit <= 0) {
            hitungDurasiParkir();
        }

        if (totalBiaya <= 0) {
            hitungTarifParkir();
        }

        if (!formTransaksiValid()) {
            return;
        }

        try {
            Connection conn = Koneksi.getKoneksi();

            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Koneksi database gagal.");
                return;
            }

            String sql = "INSERT INTO transaksi_parkir "
                    + "(no_transaksi, plat_nomor, jenis_kendaraan, tanggal_masuk, jam_masuk, "
                    + "tanggal_keluar, jam_keluar, durasi_menit, durasi_text, tarif_awal, "
                    + "tarif_progresif, total_biaya, petugas, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Selesai')";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, txtNoTransaksi.getText().trim());
            pst.setString(2, txtPlatNomor.getText().trim().toUpperCase());
            pst.setString(3, cmbJenisKendaraan.getSelectedItem().toString());
            pst.setDate(4, tanggalSql(txtTanggalMasuk.getText().trim()));
            pst.setTime(5, jamSql(txtJamMasuk.getText().trim()));
            pst.setDate(6, tanggalSql(txtTanggalKeluar.getText().trim()));
            pst.setTime(7, jamSql(txtJamKeluar.getText().trim()));
            pst.setInt(8, (int) durasiMenit);
            pst.setString(9, txtDurasiParkir.getText().trim());
            pst.setInt(10, angkaDariRupiah(txtTarifAwal.getText()));
            pst.setInt(11, angkaDariRupiah(txtTarifProgresif.getText()));
            pst.setInt(12, angkaDariRupiah(txtTotalBiaya.getText()));
            pst.setString(13, cmbPetugas.getSelectedItem().toString());

            pst.executeUpdate();

            pst.close();
            conn.close();

            JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan ke database.");

            tampilkanTransaksiDariDatabase();
            updateTotalTransaksi();
            resetFormTransaksi();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi: " + e.getMessage());
        }
    }
    
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {
        resetFormTransaksi();
    }

    private void btnCetakStrukActionPerformed(java.awt.event.ActionEvent evt) {
        if (durasiMenit <= 0) {
            hitungDurasiParkir();
        }

        if (totalBiaya <= 0) {
            hitungTarifParkir();
        }

        if (!formTransaksiValid()) {
            return;
        }

        String struk
                = "STRUK PARKIR\n"
                + "==============================\n"
                + "No Transaksi  : " + txtNoTransaksi.getText() + "\n"
                + "Plat Nomor    : " + txtPlatNomor.getText().toUpperCase() + "\n"
                + "Jenis         : " + cmbJenisKendaraan.getSelectedItem() + "\n"
                + "Jam Masuk     : " + txtTanggalMasuk.getText() + " " + txtJamMasuk.getText() + "\n"
                + "Jam Keluar    : " + txtTanggalKeluar.getText() + " " + txtJamKeluar.getText() + "\n"
                + "Durasi        : " + txtDurasiParkir.getText() + "\n"
                + "Tarif Awal    : " + txtTarifAwal.getText() + "\n"
                + "Progresif     : " + txtTarifProgresif.getText() + "\n"
                + "Total Biaya   : " + txtTotalBiaya.getText() + "\n"
                + "Petugas       : " + cmbPetugas.getSelectedItem() + "\n"
                + "==============================\n"
                + "Terima kasih.";

        JOptionPane.showMessageDialog(this, struk, "Cetak Struk", JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnLihatSemuaActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblTransaksiHariIni.getModel();

        JOptionPane.showMessageDialog(
                this,
                "Jumlah transaksi hari ini: " + model.getRowCount() + "\n"
                + lblTotalTransaksi.getText()
        );
    }
    
    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {
        new Menu_Utama(usernameLogin, hakAksesLogin).setVisible(true);
        dispose();
    }

    private void btnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Anda sedang berada di menu Transaksi.");
    }

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usernameLogin;
        String hakAkses = hakAksesLogin;

        if (username == null || username.trim().isEmpty()) {
            username = "admin";
        }

        if (hakAkses == null || hakAkses.trim().isEmpty()) {
            hakAkses = lblUserLogin.getText().trim();
        }

        new Laporan_Parkir(username, hakAkses).setVisible(true);
        dispose();
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        int pilih = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (pilih == JOptionPane.YES_OPTION) {
            new Aplikasi_Sistem_Parkir().setVisible(true);
            dispose();
        }
    }

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
        int pilih = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin keluar dari aplikasi?",
                "Konfirmasi Keluar",
                JOptionPane.YES_NO_OPTION
        );

        if (pilih == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void loadDataPetugas() {
        cmbPetugas.removeAllItems();

        try {
            Connection conn = Koneksi.getKoneksi();

            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Koneksi database gagal.");
                return;
            }

            String sql = "SELECT nama_petugas FROM petugas WHERE status = 'Aktif' ORDER BY id_petugas ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                cmbPetugas.addItem(rs.getString("nama_petugas"));
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data petugas: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelUtama = new javax.swing.JPanel();
        panelSidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblNamaAplikasi = new javax.swing.JLabel();
        lblSubAplikasi = new javax.swing.JLabel();
        lineSidebar = new javax.swing.JPanel();
        btnDashboard = new javax.swing.JButton();
        btnMasterData = new javax.swing.JButton();
        btnTransaksi = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        lineSidebar1 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        lblMenuIkon = new javax.swing.JLabel();
        lblJudulHeader = new javax.swing.JLabel();
        lblUserLogin = new javax.swing.JLabel();
        panelKonten = new javax.swing.JPanel();
        panelFormTransaksi = new javax.swing.JPanel();
        lblFormTransaksi = new javax.swing.JLabel();
        lblNoTransaksi = new javax.swing.JLabel();
        txtNoTransaksi = new javax.swing.JTextField();
        lblPlatNomor = new javax.swing.JLabel();
        txtPlatNomor = new javax.swing.JTextField();
        lblJenisKendaraan = new javax.swing.JLabel();
        cmbJenisKendaraan = new javax.swing.JComboBox<>();
        lblJamMasuk = new javax.swing.JLabel();
        txtJamMasuk = new javax.swing.JTextField();
        txtTanggalMasuk = new javax.swing.JTextField();
        lblJamKeluar = new javax.swing.JLabel();
        txtTanggalKeluar = new javax.swing.JTextField();
        txtJamKeluar = new javax.swing.JTextField();
        lblDurasiParkir = new javax.swing.JLabel();
        txtDurasiParkir = new javax.swing.JTextField();
        btnHitungDurasi = new javax.swing.JButton();
        lblTarifAwal = new javax.swing.JLabel();
        txtTarifAwal = new javax.swing.JTextField();
        lblTarifProgresif = new javax.swing.JLabel();
        txtTarifProgresif = new javax.swing.JTextField();
        lblTotalBiaya = new javax.swing.JLabel();
        txtTotalBiaya = new javax.swing.JTextField();
        lblPetugas = new javax.swing.JLabel();
        cmbPetugas = new javax.swing.JComboBox<>();
        btnHitungTarif = new javax.swing.JButton();
        btnCetakStruk = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnSimpanTransaksi2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        panelInfoTarif = new javax.swing.JPanel();
        lblInfoTarif = new javax.swing.JLabel();
        lblIconInformasiTarif = new javax.swing.JLabel();
        laneInfoTarif = new javax.swing.JPanel();
        lblIconMotor = new javax.swing.JLabel();
        lblTarifMobil = new javax.swing.JLabel();
        lblTarifMotor = new javax.swing.JLabel();
        lblIcoMobil = new javax.swing.JLabel();
        laneInfoTarif1 = new javax.swing.JPanel();
        laneInfoTarif2 = new javax.swing.JPanel();
        lblKetTarif = new javax.swing.JLabel();
        panelTransaksiHariIni = new javax.swing.JPanel();
        lblIconMobilTransaksiHariIni = new javax.swing.JLabel();
        scrollTransaksiHariIni = new javax.swing.JScrollPane();
        tblTransaksiHariIni = new javax.swing.JTable();
        btnLihatSemua = new javax.swing.JButton();
        lblTotalTransaksi = new javax.swing.JLabel();
        lblJudulTransaksiHariIni = new javax.swing.JLabel();
        panelfooter = new javax.swing.JPanel();
        lblVersi = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transaksi Parkir - Sistem Parkir");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelUtama.setBackground(new java.awt.Color(245, 247, 250));
        panelUtama.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelSidebar.setBackground(new java.awt.Color(3, 70, 122));
        panelSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setBackground(new java.awt.Color(0, 126, 224));
        lblLogo.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(255, 255, 255));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setText("P");
        lblLogo.setOpaque(true);
        panelSidebar.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 30, 75, 65));

        lblNamaAplikasi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNamaAplikasi.setForeground(new java.awt.Color(255, 255, 255));
        lblNamaAplikasi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNamaAplikasi.setText("Sistem Parkir");
        panelSidebar.add(lblNamaAplikasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 100, 210, 30));

        lblSubAplikasi.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblSubAplikasi.setForeground(new java.awt.Color(191, 219, 254));
        lblSubAplikasi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubAplikasi.setText("Aplikasi Dekstop Java Swing + MySQL");
        panelSidebar.add(lblSubAplikasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 125, 220, 20));

        lineSidebar.setBackground(new java.awt.Color(51, 111, 161));

        javax.swing.GroupLayout lineSidebarLayout = new javax.swing.GroupLayout(lineSidebar);
        lineSidebar.setLayout(lineSidebarLayout);
        lineSidebarLayout.setHorizontalGroup(
            lineSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        lineSidebarLayout.setVerticalGroup(
            lineSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        panelSidebar.add(lineSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 220, 2));

        btnDashboard.setBackground(new java.awt.Color(0, 126, 224));
        btnDashboard.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboard.setText("<html><span style='font-size:20px;'>⌂</span>&nbsp;&nbsp;Dashboard</html>");
        btnDashboard.setMargin(new java.awt.Insets(2, 0, 3, 90));
        panelSidebar.add(btnDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 175, 220, 50));

        btnMasterData.setBackground(new java.awt.Color(3, 70, 122));
        btnMasterData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMasterData.setForeground(new java.awt.Color(255, 255, 255));
        btnMasterData.setText("Master Data          >");
        btnMasterData.setBorder(null);
        btnMasterData.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnMasterData.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMasterData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasterDataActionPerformed(evt);
            }
        });
        panelSidebar.add(btnMasterData, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 140, 45));

        btnTransaksi.setBackground(new java.awt.Color(3, 70, 122));
        btnTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnTransaksi.setText("Transaksi               >");
        btnTransaksi.setBorder(null);
        btnTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        panelSidebar.add(btnTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, 140, 45));

        btnLaporan.setBackground(new java.awt.Color(3, 70, 122));
        btnLaporan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLaporan.setForeground(new java.awt.Color(255, 255, 255));
        btnLaporan.setText("Laporan                 >");
        btnLaporan.setBorder(null);
        btnLaporan.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        panelSidebar.add(btnLaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 140, 45));

        btnLogout.setBackground(new java.awt.Color(3, 70, 122));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.setBorder(null);
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        panelSidebar.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, 140, 45));

        btnExit.setBackground(new java.awt.Color(3, 70, 122));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.setBorder(null);
        btnExit.setHideActionText(true);
        btnExit.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        panelSidebar.add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 570, 140, 45));

        lineSidebar1.setBackground(new java.awt.Color(51, 111, 161));

        javax.swing.GroupLayout lineSidebar1Layout = new javax.swing.GroupLayout(lineSidebar1);
        lineSidebar1.setLayout(lineSidebar1Layout);
        lineSidebar1Layout.setHorizontalGroup(
            lineSidebar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        lineSidebar1Layout.setVerticalGroup(
            lineSidebar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        panelSidebar.add(lineSidebar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 220, 2));

        panelUtama.add(panelSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 680));

        panelHeader.setBackground(new java.awt.Color(255, 255, 255));
        panelHeader.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        panelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMenuIkon.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblMenuIkon.setForeground(new java.awt.Color(51, 65, 85));
        lblMenuIkon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMenuIkon.setText("<html><span style='font-size:22px;'>☰</span></html>");
        panelHeader.add(lblMenuIkon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 18, 40, 30));

        lblJudulHeader.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblJudulHeader.setForeground(new java.awt.Color(30, 41, 59));
        lblJudulHeader.setText("Transaksi");
        panelHeader.add(lblJudulHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 18, 200, 30));

        lblUserLogin.setBackground(new java.awt.Color(0, 126, 224));
        lblUserLogin.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblUserLogin.setForeground(new java.awt.Color(255, 255, 255));
        lblUserLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserLogin.setText("Admin");
        lblUserLogin.setOpaque(true);
        panelHeader.add(lblUserLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(965, 15, 115, 35));

        panelUtama.add(panelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1100, 70));

        panelKonten.setBackground(new java.awt.Color(245, 247, 250));
        panelKonten.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelFormTransaksi.setBackground(new java.awt.Color(255, 255, 255));
        panelFormTransaksi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        panelFormTransaksi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFormTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblFormTransaksi.setForeground(new java.awt.Color(30, 41, 59));
        lblFormTransaksi.setText("Form Transaksi Parkir");
        panelFormTransaksi.add(lblFormTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 17, 250, 30));

        lblNoTransaksi.setForeground(new java.awt.Color(30, 41, 59));
        lblNoTransaksi.setText("No Transaksi");
        panelFormTransaksi.add(lblNoTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 120, 25));

        txtNoTransaksi.setEditable(false);
        panelFormTransaksi.add(txtNoTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 330, 32));

        lblPlatNomor.setForeground(new java.awt.Color(30, 41, 59));
        lblPlatNomor.setText("Plat Nomor");
        panelFormTransaksi.add(lblPlatNomor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 120, 25));
        panelFormTransaksi.add(txtPlatNomor, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 85, 330, 32));

        lblJenisKendaraan.setForeground(new java.awt.Color(30, 41, 59));
        lblJenisKendaraan.setText("Jenis Kendaraan");
        panelFormTransaksi.add(lblJenisKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 125, 120, 25));

        cmbJenisKendaraan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Motor", "Mobil", "Bus", "Truk" }));
        panelFormTransaksi.add(cmbJenisKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 330, 32));

        lblJamMasuk.setForeground(new java.awt.Color(30, 41, 59));
        lblJamMasuk.setText("Jam Masuk");
        panelFormTransaksi.add(lblJamMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 120, 25));
        panelFormTransaksi.add(txtJamMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 155, 105, 32));
        panelFormTransaksi.add(txtTanggalMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 155, 147, 32));

        lblJamKeluar.setForeground(new java.awt.Color(30, 41, 59));
        lblJamKeluar.setText("Jam Keluar");
        panelFormTransaksi.add(lblJamKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 195, 120, 25));
        panelFormTransaksi.add(txtTanggalKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 147, 32));
        panelFormTransaksi.add(txtJamKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(305, 190, 105, 32));

        lblDurasiParkir.setForeground(new java.awt.Color(30, 41, 59));
        lblDurasiParkir.setText("Durasi Parkir");
        panelFormTransaksi.add(lblDurasiParkir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 120, 25));

        txtDurasiParkir.setEditable(false);
        panelFormTransaksi.add(txtDurasiParkir, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 225, 190, 32));

        btnHitungDurasi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHitungDurasi.setForeground(new java.awt.Color(0, 126, 224));
        btnHitungDurasi.setText("Hitung Durasi");
        panelFormTransaksi.add(btnHitungDurasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 225, 120, 32));

        lblTarifAwal.setForeground(new java.awt.Color(30, 41, 59));
        lblTarifAwal.setText("Tarif Awal");
        panelFormTransaksi.add(lblTarifAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 55, 120, 25));

        txtTarifAwal.setEditable(false);
        txtTarifAwal.setText("Rp 0");
        panelFormTransaksi.add(txtTarifAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 330, 32));

        lblTarifProgresif.setForeground(new java.awt.Color(30, 41, 59));
        lblTarifProgresif.setText("Tarif Progresif");
        panelFormTransaksi.add(lblTarifProgresif, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, 120, 25));

        txtTarifProgresif.setEditable(false);
        txtTarifProgresif.setText("Rp 0");
        panelFormTransaksi.add(txtTarifProgresif, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 85, 330, 32));

        lblTotalBiaya.setForeground(new java.awt.Color(30, 41, 59));
        lblTotalBiaya.setText("Total Biaya");
        panelFormTransaksi.add(lblTotalBiaya, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 125, 120, 25));

        txtTotalBiaya.setBackground(new java.awt.Color(219, 234, 254));
        txtTotalBiaya.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotalBiaya.setText("Rp 0");
        txtTotalBiaya.setToolTipText("");
        panelFormTransaksi.add(txtTotalBiaya, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 120, 330, 32));

        lblPetugas.setForeground(new java.awt.Color(30, 41, 59));
        lblPetugas.setText("Petugas");
        panelFormTransaksi.add(lblPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 160, 120, 25));

        cmbPetugas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Operator" }));
        panelFormTransaksi.add(cmbPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 155, 330, 32));

        btnHitungTarif.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHitungTarif.setForeground(new java.awt.Color(0, 126, 224));
        btnHitungTarif.setText("Hitung Tarif");
        panelFormTransaksi.add(btnHitungTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 210, 150, 35));

        btnCetakStruk.setBackground(new java.awt.Color(0, 126, 224));
        btnCetakStruk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCetakStruk.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakStruk.setText("<html><table cellpadding='0' cellspacing='0' border='0'><tr><td><font face='Segoe MDL2 Assets' color='#FFFFFF' size='4'>&#xE749;</font></td><td width='6'></td><td nowrap='nowrap'><b><font face='Segoe UI' color='#FFFFFF' size='3'>Cetak&nbsp;Struk</font></b></td></tr></table></html>");
        panelFormTransaksi.add(btnCetakStruk, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 263, 150, 35));

        btnReset.setBackground(new java.awt.Color(245, 158, 11));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("<html><table cellpadding='0' cellspacing='0' border='0'><tr><td><font face='Segoe UI Symbol' color='#FFFFFF' size='6'>&#10226;</font></td><td width='6'></td><td nowrap='nowrap'><b><font face='Segoe UI' color='#FFFFFF' size='3'>Reset</font></b></td></tr></table></html>");
        panelFormTransaksi.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(462, 263, 100, 35));

        btnSimpanTransaksi2.setBackground(new java.awt.Color(34, 197, 94));
        btnSimpanTransaksi2.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanTransaksi2.setText("<html><table cellpadding='0' cellspacing='0' border='0'><tr><td><font face='Segoe UI Symbol' color='#FFFFFF' size='4'>&#128190;&#65038;</font></td><td width='5'></td><td nowrap='nowrap'><b><font face='Segoe UI' color='#FFFFFF' size='3'>Simpan&nbsp;Transaksi</font></b></td></tr></table></html>");
        panelFormTransaksi.add(btnSimpanTransaksi2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 263, 147, 35));

        jLabel2.setText("<html><span style='font-size:14px; color:#2f3640;'>&#128664;</span></html>");
        panelFormTransaksi.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        panelKonten.add(panelFormTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 1060, 310));

        panelInfoTarif.setBackground(new java.awt.Color(255, 255, 255));
        panelInfoTarif.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        panelInfoTarif.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInfoTarif.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblInfoTarif.setForeground(new java.awt.Color(0, 126, 224));
        lblInfoTarif.setText("Informasi Tarif Progresif");
        panelInfoTarif.add(lblInfoTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 15, 250, 25));

        lblIconInformasiTarif.setText("<html><b><font face=\"Segoe UI Symbol\" color=\"#2D8ACF\" size=\"6\">&#9432;</font></html>");
        panelInfoTarif.add(lblIconInformasiTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        laneInfoTarif.setBackground(new java.awt.Color(226, 232, 240));

        javax.swing.GroupLayout laneInfoTarifLayout = new javax.swing.GroupLayout(laneInfoTarif);
        laneInfoTarif.setLayout(laneInfoTarifLayout);
        laneInfoTarifLayout.setHorizontalGroup(
            laneInfoTarifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        laneInfoTarifLayout.setVerticalGroup(
            laneInfoTarifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        panelInfoTarif.add(laneInfoTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 45, 340, 2));

        lblIconMotor.setText("<html><font face=\"Segoe UI Symbol\" color=\"#2FA66A\" size=\"7\">&#127949;&#65038;</font></html>");
        panelInfoTarif.add(lblIconMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        lblTarifMobil.setText("<html><b>Mobil</b><br>- 1 jam pertama : Rp 5.000<br>- Setiap jam berikutnya : Rp 2.000</html>");
        panelInfoTarif.add(lblTarifMobil, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 135, -1, -1));

        lblTarifMotor.setText("<html><b>Motor</b><br>- 1 jam pertama : Rp 2.000<br>- Setiap jam berikutnya : Rp 1.000</html>");
        panelInfoTarif.add(lblTarifMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, -1, -1));

        lblIcoMobil.setText("<html><font face=\"Segoe UI Symbol\" color=\"#2D8ACF\" size=\"7\">&#128664;&#65038;</font></html>");
        panelInfoTarif.add(lblIcoMobil, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 125, -1, -1));

        laneInfoTarif1.setBackground(new java.awt.Color(226, 232, 240));

        javax.swing.GroupLayout laneInfoTarif1Layout = new javax.swing.GroupLayout(laneInfoTarif1);
        laneInfoTarif1.setLayout(laneInfoTarif1Layout);
        laneInfoTarif1Layout.setHorizontalGroup(
            laneInfoTarif1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        laneInfoTarif1Layout.setVerticalGroup(
            laneInfoTarif1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        panelInfoTarif.add(laneInfoTarif1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 122, 340, 2));

        laneInfoTarif2.setBackground(new java.awt.Color(226, 232, 240));

        javax.swing.GroupLayout laneInfoTarif2Layout = new javax.swing.GroupLayout(laneInfoTarif2);
        laneInfoTarif2.setLayout(laneInfoTarif2Layout);
        laneInfoTarif2Layout.setHorizontalGroup(
            laneInfoTarif2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        laneInfoTarif2Layout.setVerticalGroup(
            laneInfoTarif2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        panelInfoTarif.add(laneInfoTarif2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 198, 340, 2));

        lblKetTarif.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblKetTarif.setForeground(new java.awt.Color(100, 116, 139));
        lblKetTarif.setText("<html>Perhitungan biaya dilakukan secara otomatis berdasarkan durasi parkir.</html>");
        panelInfoTarif.add(lblKetTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 330, 40));

        panelKonten.add(panelInfoTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 380, 240));

        panelTransaksiHariIni.setBackground(new java.awt.Color(255, 255, 255));
        panelTransaksiHariIni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        panelTransaksiHariIni.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIconMobilTransaksiHariIni.setText("<html><span style='font-size:12px; color:#2f3640;'>&#128664;</span></span>&nbsp;</html>");
        panelTransaksiHariIni.add(lblIconMobilTransaksiHariIni, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 20, -1));

        tblTransaksiHariIni.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No Transaksi", "Plat Nomor", "Masuk", "Keluar", "Durasi", "Total", "Status"
            }
        ));
        scrollTransaksiHariIni.setViewportView(tblTransaksiHariIni);

        panelTransaksiHariIni.add(scrollTransaksiHariIni, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 610, 155));

        btnLihatSemua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLihatSemua.setText("<html><font face=\"Segoe UI Symbol\" color=\"#2D8ACF\" size=\"4\">&#9776;</font>&nbsp;<font face=\"Segoe UI\" color=\"#2D8ACF\" size=\"3\">Lihat Semua Transaksi</font></html>");
        panelTransaksiHariIni.add(btnLihatSemua, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 190, 30));

        lblTotalTransaksi.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotalTransaksi.setForeground(new java.awt.Color(0, 126, 224));
        lblTotalTransaksi.setText("Total   :  ");
        panelTransaksiHariIni.add(lblTotalTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 200, 30));

        lblJudulTransaksiHariIni.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblJudulTransaksiHariIni.setText("Transaksi Hari Ini");
        panelTransaksiHariIni.add(lblJudulTransaksiHariIni, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 13, -1, -1));

        panelKonten.add(panelTransaksiHariIni, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 350, 660, 240));

        panelUtama.add(panelKonten, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 1100, 610));

        panelfooter.setBackground(new java.awt.Color(3, 60, 130));
        panelfooter.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVersi.setForeground(new java.awt.Color(255, 255, 255));
        lblVersi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblVersi.setText("Versi 1.0.0");
        lblVersi.setToolTipText("");
        lblVersi.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelfooter.add(lblVersi, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 10, 288, 20));

        panelUtama.add(panelfooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 680, 1360, 40));

        getContentPane().add(panelUtama, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMasterDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasterDataActionPerformed
        String username = usernameLogin;
        String hakAkses = hakAksesLogin;

        if (username == null || username.trim().isEmpty()) {
            username = "admin";
        }

        if (hakAkses == null || hakAkses.trim().isEmpty()) {
            hakAkses = lblUserLogin.getText().trim();
        }

        new Master_Data(username, hakAkses).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnMasterDataActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Transaksi_Parkir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi_Parkir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi_Parkir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi_Parkir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi_Parkir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetakStruk;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHitungDurasi;
    private javax.swing.JButton btnHitungTarif;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnLihatSemua;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMasterData;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpanTransaksi2;
    private javax.swing.JButton btnTransaksi;
    private javax.swing.JComboBox<String> cmbJenisKendaraan;
    private javax.swing.JComboBox<String> cmbPetugas;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel laneInfoTarif;
    private javax.swing.JPanel laneInfoTarif1;
    private javax.swing.JPanel laneInfoTarif2;
    private javax.swing.JLabel lblDurasiParkir;
    private javax.swing.JLabel lblFormTransaksi;
    private javax.swing.JLabel lblIcoMobil;
    private javax.swing.JLabel lblIconInformasiTarif;
    private javax.swing.JLabel lblIconMobilTransaksiHariIni;
    private javax.swing.JLabel lblIconMotor;
    private javax.swing.JLabel lblInfoTarif;
    private javax.swing.JLabel lblJamKeluar;
    private javax.swing.JLabel lblJamMasuk;
    private javax.swing.JLabel lblJenisKendaraan;
    private javax.swing.JLabel lblJudulHeader;
    private javax.swing.JLabel lblJudulTransaksiHariIni;
    private javax.swing.JLabel lblKetTarif;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMenuIkon;
    private javax.swing.JLabel lblNamaAplikasi;
    private javax.swing.JLabel lblNoTransaksi;
    private javax.swing.JLabel lblPetugas;
    private javax.swing.JLabel lblPlatNomor;
    private javax.swing.JLabel lblSubAplikasi;
    private javax.swing.JLabel lblTarifAwal;
    private javax.swing.JLabel lblTarifMobil;
    private javax.swing.JLabel lblTarifMotor;
    private javax.swing.JLabel lblTarifProgresif;
    private javax.swing.JLabel lblTotalBiaya;
    private javax.swing.JLabel lblTotalTransaksi;
    private javax.swing.JLabel lblUserLogin;
    private javax.swing.JLabel lblVersi;
    private javax.swing.JPanel lineSidebar;
    private javax.swing.JPanel lineSidebar1;
    private javax.swing.JPanel panelFormTransaksi;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelInfoTarif;
    private javax.swing.JPanel panelKonten;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel panelTransaksiHariIni;
    private javax.swing.JPanel panelUtama;
    private javax.swing.JPanel panelfooter;
    private javax.swing.JScrollPane scrollTransaksiHariIni;
    private javax.swing.JTable tblTransaksiHariIni;
    private javax.swing.JTextField txtDurasiParkir;
    private javax.swing.JTextField txtJamKeluar;
    private javax.swing.JTextField txtJamMasuk;
    private javax.swing.JTextField txtNoTransaksi;
    private javax.swing.JTextField txtPlatNomor;
    private javax.swing.JTextField txtTanggalKeluar;
    private javax.swing.JTextField txtTanggalMasuk;
    private javax.swing.JTextField txtTarifAwal;
    private javax.swing.JTextField txtTarifProgresif;
    private javax.swing.JTextField txtTotalBiaya;
    // End of variables declaration//GEN-END:variables
}
