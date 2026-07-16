/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package p17;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Laporan_Parkir extends javax.swing.JFrame {
    
    private String usernameLogin;
    private String hakAksesLogin;
    
    private final DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
   
    public Laporan_Parkir() {
        initComponents();

        this.usernameLogin = "admin";
        this.hakAksesLogin = "Admin";

        setSize(1380, 780);
        setLocationRelativeTo(null);
        setResizable(false);

        tampilkanHakAksesLogin();

        pasangEventTombol();
        rapikanTampilan();
        setTanggalLaporanDariDatabase();
        loadComboPetugas();
        tampilkanDataSementara();
        setHalamanAktif(1);
        aturHakAkses();
    }
    
    public Laporan_Parkir(String username, String hakAkses) {
        initComponents();

        this.usernameLogin = username;
        this.hakAksesLogin = hakAkses;

        setSize(1380, 780);
        setLocationRelativeTo(null);
        setResizable(false);

        tampilkanHakAksesLogin();

        pasangEventTombol();
        rapikanTampilan();
        setTanggalLaporanDariDatabase();
        loadComboPetugas();
        tampilkanDataSementara();
        setHalamanAktif(1);
        aturHakAkses();
    }
 
        private void tampilkanHakAksesLogin() {
        if (hakAksesLogin == null || hakAksesLogin.trim().isEmpty()) {
            lblUserLogin.setText("Admin");
        } else {
            lblUserLogin.setText(hakAksesLogin);
        }
    }
        
    private void setTanggalLaporanDariDatabase() {
    try {
        Connection conn = Koneksi.getKoneksi();

        if (conn == null) {
            return;
        }

        String sql = "SELECT MIN(tanggal_keluar) AS tanggal_awal, "
                + "MAX(tanggal_keluar) AS tanggal_akhir "
                + "FROM transaksi_parkir";

        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            java.sql.Date awal = rs.getDate("tanggal_awal");
            java.sql.Date akhir = rs.getDate("tanggal_akhir");

            if (awal != null && akhir != null) {
                txtTanggalAwal.setText(awal.toLocalDate().format(formatTanggal));
                txtTanggalAkhir.setText(akhir.toLocalDate().format(formatTanggal));
            }
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengambil periode laporan: " + e.getMessage());
    }
}
    
    private void rapikanTampilan() {
        btnDashboard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMasterData.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTransaksi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLaporan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnTampilkan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPrev.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPage1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPage2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPage3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNext.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnDashboard.setFocusPainted(false);
        btnMasterData.setFocusPainted(false);
        btnTransaksi.setFocusPainted(false);
        btnLaporan.setFocusPainted(false);
        btnLogout.setFocusPainted(false);
        btnExit.setFocusPainted(false);

        btnTampilkan.setFocusPainted(false);
        btnPrev.setFocusPainted(false);
        btnPage1.setFocusPainted(false);
        btnPage2.setFocusPainted(false);
        btnPage3.setFocusPainted(false);
        btnNext.setFocusPainted(false);

        tblLaporanParkir.setRowHeight(24);
        tblLaporanParkir.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tblLaporanParkir.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        tblLaporanParkir.setGridColor(new java.awt.Color(226, 232, 240));
        tblLaporanParkir.setSelectionBackground(new java.awt.Color(219, 234, 254));
        tblLaporanParkir.setSelectionForeground(new java.awt.Color(15, 23, 42));

        txtPendapatanHarian.setEditable(false);
        txtPreviewLaporan.setEditable(false);
        lblIconTotalTransaksi.setText("");
        lblIconTotalTransaksi.setIcon(buatIconDokumenTransaksi(new java.awt.Color(25, 118, 210)));
        lblIconTotalTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconTotalTransaksi.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
    }
    
    private void aturHakAkses() {
        if ("Operator".equalsIgnoreCase(hakAksesLogin)) {
            btnMasterData.setEnabled(false);
            btnMasterData.setText("Master Data");
        } else {
            btnMasterData.setEnabled(true);
        }
    }
    
    private javax.swing.ImageIcon buatIconDokumenTransaksi(java.awt.Color warna) {
        int w = 48;
        int h = 58;

        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(
                w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB
        );

        java.awt.Graphics2D g = img.createGraphics();
        g.setRenderingHint(
                java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Background kotak
        g.setColor(warna);
        g.fillRoundRect(0, 0, w, h, 0, 0);

        // Bayangan dokumen
        g.setColor(new java.awt.Color(0, 0, 0, 35));
        g.fillRoundRect(15, 12, 24, 32, 2, 2);

        // Kertas dokumen
        g.setColor(java.awt.Color.WHITE);
        g.fillRoundRect(12, 10, 24, 34, 2, 2);

        // Lipatan pojok kanan atas
        java.awt.Polygon lipatan = new java.awt.Polygon();
        lipatan.addPoint(28, 10);
        lipatan.addPoint(36, 18);
        lipatan.addPoint(28, 18);
        g.setColor(new java.awt.Color(225, 235, 250));
        g.fillPolygon(lipatan);

        // Garis isi dokumen
        g.setColor(warna);
        g.setStroke(new java.awt.BasicStroke(2f));

        g.drawLine(17, 24, 31, 24);
        g.drawLine(17, 29, 31, 29);
        g.drawLine(17, 34, 29, 34);
        g.drawLine(17, 39, 25, 39);

        g.dispose();

        return new javax.swing.ImageIcon(img);
    }

    private void pasangEventTombol() {
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });

        btnTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransaksiActionPerformed(evt);
            }
        });

        btnLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanActionPerformed(evt);
            }
        });

        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnTampilkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanActionPerformed(evt);
            }
        });

        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnPage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPage1ActionPerformed(evt);
            }
        });

        btnPage2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPage2ActionPerformed(evt);
            }
        });

        btnPage3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPage3ActionPerformed(evt);
            }
        });

        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
    }
    
    private void tampilkanDataSementara() {
        DefaultTableModel model = (DefaultTableModel) tblLaporanParkir.getModel();
        model.setRowCount(0);

        try {
            Connection conn = Koneksi.getKoneksi();

            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Koneksi database gagal.");
                return;
            }

            String jenis = cmbJenisKendaraan.getSelectedItem().toString();
            String petugas = cmbPetugas.getSelectedItem().toString();
            String status = cmbStatus.getSelectedItem().toString();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DATE_FORMAT(tanggal_keluar, '%d/%m/%Y') AS tanggal, ");
            sql.append("no_transaksi, plat_nomor, jenis_kendaraan, durasi_text, total_biaya, petugas ");
            sql.append("FROM transaksi_parkir ");
            sql.append("WHERE tanggal_keluar BETWEEN ? AND ? ");

            if (!jenis.equalsIgnoreCase("Semua")) {
                if (jenis.equalsIgnoreCase("Mobil")) {
                    sql.append("AND jenis_kendaraan IN ('Mobil', 'Bus', 'Truk') ");
                } else {
                    sql.append("AND jenis_kendaraan = ? ");
                }
            }

            if (!petugas.equalsIgnoreCase("Semua")) {
                sql.append("AND petugas = ? ");
            }

            if (!status.equalsIgnoreCase("Semua")) {
                sql.append("AND status = ? ");
            }

            sql.append("ORDER BY tanggal_keluar DESC, jam_keluar DESC");

            PreparedStatement pst = conn.prepareStatement(sql.toString());

            LocalDate awal = LocalDate.parse(txtTanggalAwal.getText().trim(), formatTanggal);
            LocalDate akhir = LocalDate.parse(txtTanggalAkhir.getText().trim(), formatTanggal);

            int index = 1;
            pst.setDate(index++, java.sql.Date.valueOf(awal));
            pst.setDate(index++, java.sql.Date.valueOf(akhir));

            if (!jenis.equalsIgnoreCase("Semua")) {
                if (!jenis.equalsIgnoreCase("Mobil")) {
                    pst.setString(index++, jenis);
                }
            }

            if (!petugas.equalsIgnoreCase("Semua")) {
                pst.setString(index++, petugas);
            }

            if (!status.equalsIgnoreCase("Semua")) {
                pst.setString(index++, status);
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("tanggal"),
                    rs.getString("no_transaksi"),
                    rs.getString("plat_nomor"),
                    rs.getString("jenis_kendaraan"),
                    rs.getString("durasi_text"),
                    formatRupiah(rs.getInt("total_biaya")),
                    rs.getString("petugas")
                });
            }

            rs.close();
            pst.close();
            conn.close();

            updateRingkasanLaporan();
            updateInfoDataLaporan();
            updatePendapatanHarianDariTabel();
            updatePreviewLaporan();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan: " + e.getMessage());
        }
    }
    
    private void loadComboPetugas() {
    cmbPetugas.removeAllItems();
    cmbPetugas.addItem("Semua");

    try {
        Connection conn = Koneksi.getKoneksi();

        if (conn == null) {
            return;
        }

        String sql = "SELECT nama_petugas FROM petugas WHERE status = 'Aktif' ORDER BY nama_petugas ASC";
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

    private void updatePendapatanHarianDariTabel() {
        int total = 0;
        StringBuilder teks = new StringBuilder();

        for (int i = 0; i < tblLaporanParkir.getRowCount(); i++) {
            String tanggal = tblLaporanParkir.getValueAt(i, 0).toString();
            String biaya = tblLaporanParkir.getValueAt(i, 5).toString();

            int angka = ambilAngkaRupiah(biaya);
            total += angka;

            teks.append(tanggal)
                    .append(" - ")
                    .append(formatRupiah(angka))
                    .append("\n");
        }

        teks.append("Total - ").append(formatRupiah(total));
        txtPendapatanHarian.setText(teks.toString());
    }
    
    private void updateRingkasanLaporan() {
        int totalTransaksi = tblLaporanParkir.getRowCount();
        int totalMobil = 0;
        int totalMotor = 0;
        int totalPendapatan = 0;

        for (int i = 0; i < tblLaporanParkir.getRowCount(); i++) {
            String jenis = tblLaporanParkir.getValueAt(i, 3).toString();
            String biaya = tblLaporanParkir.getValueAt(i, 5).toString();

            if (jenis.equalsIgnoreCase("Mobil")
                    || jenis.equalsIgnoreCase("Bus")
                    || jenis.equalsIgnoreCase("Truk")) {
                totalMobil++;
            } else if (jenis.equalsIgnoreCase("Motor")) {
                totalMotor++;
            }

            totalPendapatan += ambilAngkaRupiah(biaya);
        }

        lblTotalTransaksi.setText(String.valueOf(totalTransaksi));
        lblTotalPendapatan.setText(formatRupiah(totalPendapatan));
        lblTotalMobil.setText(String.valueOf(totalMobil));
        lblTotalMotor.setText(String.valueOf(totalMotor));
    }
    
    private int ambilAngkaRupiah(String teks) {
        try {
            String angka = teks.replace("Rp", "")
                    .replace(".", "")
                    .replace(",", "")
                    .trim();

            return Integer.parseInt(angka);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String formatRupiah(int angka) {
        return "Rp " + String.format("%,d", angka).replace(",", ".");
    }
    
    private void updateInfoDataLaporan() {
        int jumlahData = tblLaporanParkir.getRowCount();

        if (jumlahData == 0) {
            lblInfoDataLaporan.setText("Menampilkan 0 data");
        } else {
            lblInfoDataLaporan.setText("Menampilkan 1 - " + jumlahData + " dari " + jumlahData + " data");
        }
    }

    private void updatePreviewLaporan() {
        txtPreviewLaporan.setText(
                "LAPORAN TRANSAKSI PARKIR\n"
                + "Periode : " + txtTanggalAwal.getText() + " s/d " + txtTanggalAkhir.getText() + "\n\n"
                + "Total Transaksi   : " + lblTotalTransaksi.getText() + "\n"
                + "Total Pendapatan  : " + lblTotalPendapatan.getText() + "\n"
                + "Mobil             : " + lblTotalMobil.getText() + "\n"
                + "Motor             : " + lblTotalMotor.getText() + "\n\n"
                + "TERIMA KASIH"
        );
    }
    
    private void setHalamanAktif(int halaman) {
        Color biru = new Color(0, 126, 224);
        Color putih = Color.WHITE;
        Color teksGelap = new Color(30, 41, 59);

        btnPage1.setBackground(putih);
        btnPage2.setBackground(putih);
        btnPage3.setBackground(putih);

        btnPage1.setForeground(teksGelap);
        btnPage2.setForeground(teksGelap);
        btnPage3.setForeground(teksGelap);

        if (halaman == 1) {
            btnPage1.setBackground(biru);
            btnPage1.setForeground(Color.WHITE);
        } else if (halaman == 2) {
            btnPage2.setBackground(biru);
            btnPage2.setForeground(Color.WHITE);
        } else if (halaman == 3) {
            btnPage3.setBackground(biru);
            btnPage3.setForeground(Color.WHITE);
        }
    }
    
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
        laneSidebar1 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        lblMenuIkon = new javax.swing.JLabel();
        lblJudulHeader = new javax.swing.JLabel();
        lblUserLogin = new javax.swing.JLabel();
        panelfooter = new javax.swing.JPanel();
        lblVersi = new javax.swing.JLabel();
        panelKonten = new javax.swing.JPanel();
        panelFilter = new javax.swing.JPanel();
        lblPeriodeTanggal = new javax.swing.JLabel();
        txtTanggalAwal = new javax.swing.JTextField();
        lblSampai = new javax.swing.JLabel();
        txtTanggalAkhir = new javax.swing.JTextField();
        lblJenisKendaraan = new javax.swing.JLabel();
        cmbJenisKendaraan = new javax.swing.JComboBox<>();
        lblPetugas = new javax.swing.JLabel();
        cmbPetugas = new javax.swing.JComboBox<>();
        lblStatus = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        btnTampilkan = new javax.swing.JButton();
        cardTotalMotor = new javax.swing.JPanel();
        lblIconMotor = new javax.swing.JLabel();
        lblTittleTotalMotor = new javax.swing.JLabel();
        lblKetTotalMotor = new javax.swing.JLabel();
        lblTotalMotor = new javax.swing.JLabel();
        cardTotalTransaksi = new javax.swing.JPanel();
        lblIconTotalTransaksi = new javax.swing.JLabel();
        lblTitleTotalTransaksi = new javax.swing.JLabel();
        lblTotalTransaksi = new javax.swing.JLabel();
        lblKetTotalTransaksi = new javax.swing.JLabel();
        cardTotalPendapatan = new javax.swing.JPanel();
        lblIconPendapatan = new javax.swing.JLabel();
        lblTittleTotalPendapatan = new javax.swing.JLabel();
        lblTotalPendapatan = new javax.swing.JLabel();
        lblKetTotalPendapatan = new javax.swing.JLabel();
        cardTotalMobil = new javax.swing.JPanel();
        lblIconMobil = new javax.swing.JLabel();
        lblTittleTotalMobi = new javax.swing.JLabel();
        lblTotalMobil = new javax.swing.JLabel();
        lblKetTotalMobil = new javax.swing.JLabel();
        panelTabelLaporan = new javax.swing.JPanel();
        lblJudulTabelLaporan = new javax.swing.JLabel();
        scrollLaporan = new javax.swing.JScrollPane();
        tblLaporanParkir = new javax.swing.JTable();
        lblInfoDataLaporan = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPage1 = new javax.swing.JButton();
        btnPage2 = new javax.swing.JButton();
        btnPage3 = new javax.swing.JButton();
        panelPendapatanHarian = new javax.swing.JPanel();
        lblPendapatanHarian = new javax.swing.JLabel();
        scrollPendapatanHarian = new javax.swing.JScrollPane();
        txtPendapatanHarian = new javax.swing.JTextArea();
        PanelPreviewLaporan = new javax.swing.JPanel();
        lblPreviewLaporan = new javax.swing.JLabel();
        scrollPreviewLaporan = new javax.swing.JScrollPane();
        txtPreviewLaporan = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Laporan Parkir - Sistem Parkir");
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

        laneSidebar1.setBackground(new java.awt.Color(51, 111, 161));

        javax.swing.GroupLayout laneSidebar1Layout = new javax.swing.GroupLayout(laneSidebar1);
        laneSidebar1.setLayout(laneSidebar1Layout);
        laneSidebar1Layout.setHorizontalGroup(
            laneSidebar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        laneSidebar1Layout.setVerticalGroup(
            laneSidebar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        panelSidebar.add(laneSidebar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 220, 2));

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
        lblJudulHeader.setText("Laporan");
        panelHeader.add(lblJudulHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 18, 200, 30));

        lblUserLogin.setBackground(new java.awt.Color(0, 126, 224));
        lblUserLogin.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblUserLogin.setForeground(new java.awt.Color(255, 255, 255));
        lblUserLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserLogin.setText("Admin");
        lblUserLogin.setOpaque(true);
        panelHeader.add(lblUserLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(965, 15, 115, 35));

        panelUtama.add(panelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1100, 70));

        panelfooter.setBackground(new java.awt.Color(3, 60, 130));
        panelfooter.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVersi.setForeground(new java.awt.Color(255, 255, 255));
        lblVersi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblVersi.setText("Versi 1.0.0");
        lblVersi.setToolTipText("");
        lblVersi.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelfooter.add(lblVersi, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 10, 288, 20));

        panelUtama.add(panelfooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 680, 1360, 40));

        panelKonten.setBackground(new java.awt.Color(245, 247, 250));
        panelKonten.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelFilter.setBackground(new java.awt.Color(255, 255, 255));
        panelFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        panelFilter.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPeriodeTanggal.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblPeriodeTanggal.setText("Periode Tanggal");
        panelFilter.add(lblPeriodeTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 150, 25));
        panelFilter.add(txtTanggalAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 130, 35));

        lblSampai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSampai.setText("s/d");
        panelFilter.add(lblSampai, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 55, 20, 25));
        panelFilter.add(txtTanggalAkhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 130, 35));

        lblJenisKendaraan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblJenisKendaraan.setText("Jenis Kendaraan");
        panelFilter.add(lblJenisKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 150, 25));

        cmbJenisKendaraan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua", "Motor", "Mobil", "Bus", "Truk" }));
        panelFilter.add(cmbJenisKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 150, 35));

        lblPetugas.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblPetugas.setText("Petugas");
        panelFilter.add(lblPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 120, 25));

        cmbPetugas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua", "Admin", "Operator" }));
        panelFilter.add(cmbPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, 140, 35));

        lblStatus.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblStatus.setText("Status");
        panelFilter.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 120, 25));

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua", "Selesai", "Masuk" }));
        panelFilter.add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 130, 35));

        btnTampilkan.setBackground(new java.awt.Color(0, 126, 224));
        btnTampilkan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnTampilkan.setForeground(new java.awt.Color(255, 255, 255));
        btnTampilkan.setText("Tampilkan");
        panelFilter.add(btnTampilkan, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 50, 130, 35));

        panelKonten.add(panelFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 1060, 120));

        cardTotalMotor.setBackground(new java.awt.Color(255, 255, 255));
        cardTotalMotor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        cardTotalMotor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIconMotor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconMotor.setText("<html><table width='48' height='48' bgcolor='#55B83F' cellspacing='0' cellpadding='0'><tr><td align='center' valign='middle'><font face='Segoe UI Symbol' color='white' size='7'>&#127949;&#65038;</font></td></tr></table></html>");
        cardTotalMotor.add(lblIconMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, 55, 50));

        lblTittleTotalMotor.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTittleTotalMotor.setForeground(new java.awt.Color(100, 116, 139));
        lblTittleTotalMotor.setText("Total Motor");
        cardTotalMotor.add(lblTittleTotalMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 15, 140, 20));

        lblKetTotalMotor.setForeground(new java.awt.Color(100, 116, 139));
        lblKetTotalMotor.setText("Transaksi");
        cardTotalMotor.add(lblKetTotalMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 63, 120, 20));

        lblTotalMotor.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTotalMotor.setForeground(new java.awt.Color(34, 197, 94));
        lblTotalMotor.setText("0");
        cardTotalMotor.add(lblTotalMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 35, 120, 30));

        panelKonten.add(cardTotalMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 160, 245, 90));

        cardTotalTransaksi.setBackground(new java.awt.Color(255, 255, 255));
        cardTotalTransaksi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        cardTotalTransaksi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIconTotalTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconTotalTransaksi.setText("<html>\n<table bgcolor=\"#1976D2\" cellpadding=\"6\" cellspacing=\"0\">\n<tr>\n<td align=\"center\">\n<font color=\"white\" size=\"6\" face=\"Segoe UI Symbol\"'>&#128196;</font>\n</td>\n</tr>\n</table>\n</html>");
        cardTotalTransaksi.add(lblIconTotalTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, 55, 50));

        lblTitleTotalTransaksi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTitleTotalTransaksi.setForeground(new java.awt.Color(100, 116, 139));
        lblTitleTotalTransaksi.setText("Total Transaksi");
        cardTotalTransaksi.add(lblTitleTotalTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 15, 140, 20));

        lblTotalTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblTotalTransaksi.setForeground(new java.awt.Color(0, 126, 224));
        lblTotalTransaksi.setText("0");
        cardTotalTransaksi.add(lblTotalTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 35, 120, 30));

        lblKetTotalTransaksi.setForeground(new java.awt.Color(100, 116, 139));
        lblKetTotalTransaksi.setText("Per Hari ini");
        cardTotalTransaksi.add(lblKetTotalTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 63, 120, 20));

        panelKonten.add(cardTotalTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 245, 90));

        cardTotalPendapatan.setBackground(new java.awt.Color(255, 255, 255));
        cardTotalPendapatan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        cardTotalPendapatan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIconPendapatan.setBackground(new java.awt.Color(0, 255, 255));
        lblIconPendapatan.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblIconPendapatan.setForeground(new java.awt.Color(0, 51, 51));
        lblIconPendapatan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconPendapatan.setText("<html><table width='42' height='42' bgcolor='#12AEB5' cellspacing='2' cellpadding='5'><tr><td align='center' valign='middle'><font face='Segoe UI' color='white' size='6'><b>Rp</b></font></td></tr></table></html>");
        cardTotalPendapatan.add(lblIconPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, 55, 50));

        lblTittleTotalPendapatan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTittleTotalPendapatan.setForeground(new java.awt.Color(100, 116, 139));
        lblTittleTotalPendapatan.setText("Total Pendapatan");
        cardTotalPendapatan.add(lblTittleTotalPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 15, 150, 20));

        lblTotalPendapatan.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTotalPendapatan.setForeground(new java.awt.Color(20, 184, 166));
        lblTotalPendapatan.setText("Rp 0");
        lblTotalPendapatan.setToolTipText("");
        cardTotalPendapatan.add(lblTotalPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 35, 150, 30));

        lblKetTotalPendapatan.setForeground(new java.awt.Color(100, 116, 139));
        lblKetTotalPendapatan.setText("Total Pemasukan");
        lblKetTotalPendapatan.setToolTipText("");
        cardTotalPendapatan.add(lblKetTotalPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 63, 150, 20));

        panelKonten.add(cardTotalPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 245, 90));

        cardTotalMobil.setBackground(new java.awt.Color(255, 255, 255));
        cardTotalMobil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        cardTotalMobil.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIconMobil.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblIconMobil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconMobil.setText("<html> <table bgcolor=\"#1976D2\" cellpadding=\"6\" cellspacing=\"0\"> <tr> <td align=\"center\"> <font color=\"white\" size=\"8\" face=\"Segoe UI Symbol\">&#128664;</font> </td> </tr> </table> </html>");
        cardTotalMobil.add(lblIconMobil, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, 55, 50));

        lblTittleTotalMobi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTittleTotalMobi.setForeground(new java.awt.Color(100, 116, 139));
        lblTittleTotalMobi.setText("Total Mobil");
        cardTotalMobil.add(lblTittleTotalMobi, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 15, 140, 20));

        lblTotalMobil.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTotalMobil.setForeground(new java.awt.Color(37, 99, 235));
        lblTotalMobil.setText("0");
        cardTotalMobil.add(lblTotalMobil, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 35, 120, 30));

        lblKetTotalMobil.setForeground(new java.awt.Color(100, 116, 139));
        lblKetTotalMobil.setText("Transaksi");
        cardTotalMobil.add(lblKetTotalMobil, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 63, 120, 20));

        panelKonten.add(cardTotalMobil, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, 245, 90));

        panelTabelLaporan.setBackground(new java.awt.Color(255, 255, 255));
        panelTabelLaporan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        panelTabelLaporan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJudulTabelLaporan.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblJudulTabelLaporan.setForeground(new java.awt.Color(30, 41, 59));
        lblJudulTabelLaporan.setText("Laporan Transaksi Parkir");
        panelTabelLaporan.add(lblJudulTabelLaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 250, 25));

        tblLaporanParkir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tanggal", "No Transaksi", "Plat Nomor", "Jenis", "Durasi", "Total Biaya", "Petugas"
            }
        ));
        scrollLaporan.setViewportView(tblLaporanParkir);

        panelTabelLaporan.add(scrollLaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 640, 220));

        lblInfoDataLaporan.setForeground(new java.awt.Color(100, 116, 139));
        lblInfoDataLaporan.setText("Menampilkan 0 Data");
        panelTabelLaporan.add(lblInfoDataLaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 285, 250, 25));

        btnPrev.setText("<");
        panelTabelLaporan.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 285, 45, 25));

        btnNext.setText(">");
        panelTabelLaporan.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 285, 45, 25));

        btnPage1.setBackground(new java.awt.Color(0, 126, 224));
        btnPage1.setForeground(new java.awt.Color(255, 255, 255));
        btnPage1.setText("1");
        panelTabelLaporan.add(btnPage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 285, 35, 25));

        btnPage2.setForeground(new java.awt.Color(30, 41, 59));
        btnPage2.setText("2");
        panelTabelLaporan.add(btnPage2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 285, 35, 25));

        btnPage3.setForeground(new java.awt.Color(30, 41, 59));
        btnPage3.setText("3");
        panelTabelLaporan.add(btnPage3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 285, 35, 25));

        panelKonten.add(panelTabelLaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 680, 320));

        panelPendapatanHarian.setBackground(new java.awt.Color(255, 255, 255));
        panelPendapatanHarian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        panelPendapatanHarian.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPendapatanHarian.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPendapatanHarian.setForeground(new java.awt.Color(30, 41, 59));
        lblPendapatanHarian.setText("Pendapatan Harian");
        panelPendapatanHarian.add(lblPendapatanHarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 200, 25));

        txtPendapatanHarian.setEditable(false);
        txtPendapatanHarian.setColumns(20);
        txtPendapatanHarian.setRows(5);
        txtPendapatanHarian.setText("14/05 - Rp 450.000\n15/05 - Rp 520.000\n16/05 - Rp 613.000\n17/05 - Rp 450.000\n20/05 - Rp 725.000");
        scrollPendapatanHarian.setViewportView(txtPendapatanHarian);

        panelPendapatanHarian.add(scrollPendapatanHarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 45, 320, 90));

        panelKonten.add(panelPendapatanHarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 270, 360, 150));

        PanelPreviewLaporan.setBackground(new java.awt.Color(255, 255, 255));
        PanelPreviewLaporan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        PanelPreviewLaporan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPreviewLaporan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPreviewLaporan.setForeground(new java.awt.Color(30, 41, 59));
        lblPreviewLaporan.setText("Preview Laporan");
        PanelPreviewLaporan.add(lblPreviewLaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, 25));

        txtPreviewLaporan.setEditable(false);
        txtPreviewLaporan.setColumns(20);
        txtPreviewLaporan.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        txtPreviewLaporan.setRows(5);
        txtPreviewLaporan.setText("LAPORAN TRANSAKSI PARKIR\nPeriode : 20/05/2025 s/d 20/06/2025\n\nTotal Transaksi   : 0\nTotal Pendapatan  : Rp 0\nMobil             : 0\nMotor             : 0\n\nTERIMA KASIH");
        scrollPreviewLaporan.setViewportView(txtPreviewLaporan);

        PanelPreviewLaporan.add(scrollPreviewLaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 320, 95));

        panelKonten.add(PanelPreviewLaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 440, 360, 150));

        panelUtama.add(panelKonten, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 1100, 610));

        getContentPane().add(panelUtama, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMasterDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasterDataActionPerformed
        if ("Operator".equalsIgnoreCase(hakAksesLogin)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Akses Master Data hanya untuk Admin.",
                    "Akses Ditolak",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        new Master_Data(usernameLogin, hakAksesLogin).setVisible(true);
        dispose();
    }//GEN-LAST:event_btnMasterDataActionPerformed
    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {
        new Menu_Utama(usernameLogin, hakAksesLogin).setVisible(true);
        dispose();
    }

    private void btnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {
        new Transaksi_Parkir(usernameLogin, hakAksesLogin).setVisible(true);
        dispose();
    }
    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Anda sedang berada di halaman Laporan.");
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

    private void btnTampilkanActionPerformed(java.awt.event.ActionEvent evt) {
        tampilkanDataSementara();
        JOptionPane.showMessageDialog(this, "Data laporan berhasil ditampilkan dari database.");
    }

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {
        setHalamanAktif(1);
        JOptionPane.showMessageDialog(this, "Menampilkan halaman sebelumnya.");
    }

    private void btnPage1ActionPerformed(java.awt.event.ActionEvent evt) {
        setHalamanAktif(1);
        lblInfoDataLaporan.setText("Menampilkan halaman 1");
    }

    private void btnPage2ActionPerformed(java.awt.event.ActionEvent evt) {
        setHalamanAktif(2);
        lblInfoDataLaporan.setText("Menampilkan halaman 2");
    }

    private void btnPage3ActionPerformed(java.awt.event.ActionEvent evt) {
        setHalamanAktif(3);
        lblInfoDataLaporan.setText("Menampilkan halaman 3");
    }

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {
        setHalamanAktif(2);
        JOptionPane.showMessageDialog(this, "Menampilkan halaman berikutnya.");
    }

    
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
            java.util.logging.Logger.getLogger(Laporan_Parkir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Laporan_Parkir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Laporan_Parkir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Laporan_Parkir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Laporan_Parkir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelPreviewLaporan;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMasterData;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPage1;
    private javax.swing.JButton btnPage2;
    private javax.swing.JButton btnPage3;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnTampilkan;
    private javax.swing.JButton btnTransaksi;
    private javax.swing.JPanel cardTotalMobil;
    private javax.swing.JPanel cardTotalMotor;
    private javax.swing.JPanel cardTotalPendapatan;
    private javax.swing.JPanel cardTotalTransaksi;
    private javax.swing.JComboBox<String> cmbJenisKendaraan;
    private javax.swing.JComboBox<String> cmbPetugas;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JPanel laneSidebar1;
    private javax.swing.JLabel lblIconMobil;
    private javax.swing.JLabel lblIconMotor;
    private javax.swing.JLabel lblIconPendapatan;
    private javax.swing.JLabel lblIconTotalTransaksi;
    private javax.swing.JLabel lblInfoDataLaporan;
    private javax.swing.JLabel lblJenisKendaraan;
    private javax.swing.JLabel lblJudulHeader;
    private javax.swing.JLabel lblJudulTabelLaporan;
    private javax.swing.JLabel lblKetTotalMobil;
    private javax.swing.JLabel lblKetTotalMotor;
    private javax.swing.JLabel lblKetTotalPendapatan;
    private javax.swing.JLabel lblKetTotalTransaksi;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMenuIkon;
    private javax.swing.JLabel lblNamaAplikasi;
    private javax.swing.JLabel lblPendapatanHarian;
    private javax.swing.JLabel lblPeriodeTanggal;
    private javax.swing.JLabel lblPetugas;
    private javax.swing.JLabel lblPreviewLaporan;
    private javax.swing.JLabel lblSampai;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSubAplikasi;
    private javax.swing.JLabel lblTitleTotalTransaksi;
    private javax.swing.JLabel lblTittleTotalMobi;
    private javax.swing.JLabel lblTittleTotalMotor;
    private javax.swing.JLabel lblTittleTotalPendapatan;
    private javax.swing.JLabel lblTotalMobil;
    private javax.swing.JLabel lblTotalMotor;
    private javax.swing.JLabel lblTotalPendapatan;
    private javax.swing.JLabel lblTotalTransaksi;
    private javax.swing.JLabel lblUserLogin;
    private javax.swing.JLabel lblVersi;
    private javax.swing.JPanel lineSidebar;
    private javax.swing.JPanel panelFilter;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelKonten;
    private javax.swing.JPanel panelPendapatanHarian;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel panelTabelLaporan;
    private javax.swing.JPanel panelUtama;
    private javax.swing.JPanel panelfooter;
    private javax.swing.JScrollPane scrollLaporan;
    private javax.swing.JScrollPane scrollPendapatanHarian;
    private javax.swing.JScrollPane scrollPreviewLaporan;
    private javax.swing.JTable tblLaporanParkir;
    private javax.swing.JTextArea txtPendapatanHarian;
    private javax.swing.JTextArea txtPreviewLaporan;
    private javax.swing.JTextField txtTanggalAkhir;
    private javax.swing.JTextField txtTanggalAwal;
    // End of variables declaration//GEN-END:variables
}
