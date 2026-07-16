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
import javax.swing.Icon;

public class Menu_Utama extends javax.swing.JFrame {
private String usernameLogin;
private String hakAksesLogin;
    /**
     * Creates new form Menu_Utama
     */
    public Menu_Utama() {
        initComponents();
        pasangEventTombol();

        setSize(1380, 780);
        setLocationRelativeTo(null);
        setResizable(false);
        
        tampilkanHakAksesLogin();

        rapikanTampilan();
        tampilkanDashboardSementara();
        isiTabelTransaksiTerakhir();
    }

    public Menu_Utama(String username, String hakAkses) {
        initComponents();
        pasangEventTombol();

        this.usernameLogin = username;
        this.hakAksesLogin = hakAkses;

        setSize(1380, 780);
        setLocationRelativeTo(null);
        setResizable(false);

        tampilkanHakAksesLogin();

        rapikanTampilan();
        tampilkanDashboardSementara();
        isiTabelTransaksiTerakhir();
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
    }
    
    private void rapikanTampilan() {
        btnDashboard.setText("<html><span style='font-size:20px;'>⌂</span>&nbsp;&nbsp;Dashboard</html>");

        btnDashboard.setFocusPainted(false);
        btnMasterData.setFocusPainted(false);
        btnTransaksi.setFocusPainted(false);
        btnLaporan.setFocusPainted(false);
        btnLogout.setFocusPainted(false);
        btnExit.setFocusPainted(false);
        btnLihatSemua.setFocusPainted(false);

        btnDashboard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMasterData.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTransaksi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLaporan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLihatSemua.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblUserLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserLogin.setOpaque(true);

        btnDashboard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMasterData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLaporan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnLihatSemua.setIconTextGap(10);
        btnLihatSemua.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        // Perbaikan icon Dashboard agar tidak kotak-kotak saat dijalankan

        // Icon Transaksi Hari Ini - bentuk nota/struk transaksi
        lblIconTransaksi.setText("");
        lblIconTransaksi.setIcon(buatIconDokumenTransaksi(new java.awt.Color(76, 175, 80)));
        lblIconTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconTransaksi.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconTransaksi.setPreferredSize(new java.awt.Dimension(58, 58));

        lblIconPendapatan.setText("");
        lblIconPendapatan.setIcon(buatIconRupiah(new java.awt.Color(23, 162, 184)));
        lblIconPendapatan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconPendapatan.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconPendapatan.setPreferredSize(new java.awt.Dimension(58, 58));
        
        lblIconTransaksi.setText("");
        lblIconTransaksi.setIcon(buatIconDokumenTransaksi(new java.awt.Color(76, 175, 80)));
        lblIconTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconTransaksi.setVerticalAlignment(javax.swing.SwingConstants.CENTER);

        tblTransaksiTerakhir.setRowHeight(28);
        tblTransaksiTerakhir.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tblTransaksiTerakhir.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        tblTransaksiTerakhir.setGridColor(new java.awt.Color(226, 232, 240));
        tblTransaksiTerakhir.setSelectionBackground(new java.awt.Color(219, 234, 254));
        tblTransaksiTerakhir.setSelectionForeground(new java.awt.Color(15, 23, 42));
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

        // background hijau
        g.setColor(warna);
        g.fillRect(0, 0, w, h);

        // dokumen putih di tengah
        int docW = 20;
        int docH = 28;
        int docX = (w - docW) / 2;
        int docY = (h - docH) / 2;

        g.setColor(java.awt.Color.WHITE);
        g.fillRoundRect(docX, docY, docW, docH, 3, 3);

        // lipatan pojok
        java.awt.Polygon fold = new java.awt.Polygon();
        fold.addPoint(docX + docW - 7, docY);
        fold.addPoint(docX + docW, docY + 7);
        fold.addPoint(docX + docW - 7, docY + 7);
        g.setColor(new java.awt.Color(220, 240, 220));
        g.fillPolygon(fold);

        // garis isi dokumen
        g.setColor(warna);
        g.setStroke(new java.awt.BasicStroke(2f));
        g.drawLine(docX + 4, docY + 10, docX + 15, docY + 10);
        g.drawLine(docX + 4, docY + 15, docX + 15, docY + 15);
        g.drawLine(docX + 4, docY + 20, docX + 13, docY + 20);

        g.dispose();
        return new javax.swing.ImageIcon(img);
    }
        
    private javax.swing.ImageIcon buatIconRupiah(java.awt.Color warna) {
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

        // background cyan
        g.setColor(warna);
        g.fillRect(0, 0, w, h);

        // teks Rp di tengah
        g.setColor(java.awt.Color.WHITE);
        g.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));

        java.awt.FontMetrics fm = g.getFontMetrics();
        String teks = "Rp";
        int textW = fm.stringWidth(teks);
        int textH = fm.getAscent();

        int x = (w - textW) / 2;
        int y = (h - fm.getHeight()) / 2 + textH;

        g.drawString(teks, x, y);

        g.dispose();
        return new javax.swing.ImageIcon(img);
    }

    
    private void tampilkanDashboardSementara() {
        try {
            Connection conn = Koneksi.getKoneksi();

            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Koneksi database gagal.");
                return;
            }

            int totalKendaraan = ambilJumlah(conn, "SELECT COUNT(*) FROM kendaraan WHERE status = 'Aktif'");
            int transaksiHariIni = ambilJumlah(conn, "SELECT COUNT(*) FROM transaksi_parkir WHERE tanggal_keluar = CURDATE()");
            int kendaraanMasuk = ambilJumlah(conn, "SELECT COUNT(*) FROM transaksi_parkir WHERE tanggal_masuk = CURDATE()");
            int pendapatan = ambilJumlah(conn, "SELECT COALESCE(SUM(total_biaya), 0) FROM transaksi_parkir WHERE tanggal_keluar = CURDATE()");

            lblJumlahKendaraan.setText(String.valueOf(totalKendaraan));
            lblJumlahTransaksi.setText(String.valueOf(transaksiHariIni));
            lblJumlahKendaraanMasuk.setText(String.valueOf(kendaraanMasuk));
            lblJumlahPendapatan.setText("Rp " + formatRupiah(pendapatan));

            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan dashboard: " + e.getMessage());
        }
    }

    private int ambilJumlah(Connection conn, String sql) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        int hasil = 0;
        if (rs.next()) {
            hasil = rs.getInt(1);
        }

        rs.close();
        pst.close();

        return hasil;
    }
    private String formatRupiah(int angka) {
        return String.format("%,d", angka).replace(",", ".");
    }
    
    private void isiTabelTransaksiTerakhir() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"No", "Plat Nomor", "Jenis", "Jam Masuk", "Jam Keluar", "Durasi", "Biaya"}
        );

        tblTransaksiTerakhir.setModel(model);
        model.setRowCount(0);

        try {
            Connection conn = Koneksi.getKoneksi();

            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Koneksi database gagal.");
                return;
            }

            String sql = "SELECT no_transaksi, plat_nomor, jenis_kendaraan, "
                    + "CONCAT(DATE_FORMAT(tanggal_masuk, '%d-%m-%Y'), ' ', TIME_FORMAT(jam_masuk, '%H:%i')) AS masuk, "
                    + "CONCAT(DATE_FORMAT(tanggal_keluar, '%d-%m-%Y'), ' ', TIME_FORMAT(jam_keluar, '%H:%i')) AS keluar, "
                    + "durasi_text, total_biaya "
                    + "FROM transaksi_parkir "
                    + "ORDER BY created_at DESC "
                    + "LIMIT 10";

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("no_transaksi"),
                    rs.getString("plat_nomor"),
                    rs.getString("jenis_kendaraan"),
                    rs.getString("masuk"),
                    rs.getString("keluar"),
                    rs.getString("durasi_text"),
                    "Rp " + formatRupiah(rs.getInt("total_biaya"))
                });
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil transaksi terakhir: " + e.getMessage());
        }
    }
    
    private void aturHakAkses() {
        if ("Operator".equalsIgnoreCase(hakAksesLogin)) {
            btnMasterData.setEnabled(false);
            btnMasterData.setText("Master Data");
        } else {
            btnMasterData.setEnabled(true);
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
        jPanel1 = new javax.swing.JPanel();
        panelfooter = new javax.swing.JPanel();
        lblVersi = new javax.swing.JLabel();
        panelHeader = new javax.swing.JPanel();
        lblMenuIkon = new javax.swing.JLabel();
        lblJudulHeader = new javax.swing.JLabel();
        lblUserLogin = new javax.swing.JLabel();
        panelKonten = new javax.swing.JPanel();
        cardKendaraan = new javax.swing.JPanel();
        lblKendaraan = new javax.swing.JLabel();
        lblJumlahKendaraan = new javax.swing.JLabel();
        lblKetKendaraan = new javax.swing.JLabel();
        lblIconKendaraan = new javax.swing.JLabel();
        cardTransaksiHariIni = new javax.swing.JPanel();
        lblCardTransaksi = new javax.swing.JLabel();
        lblJumlahTransaksi = new javax.swing.JLabel();
        lblKetTransaksi = new javax.swing.JLabel();
        lblIconTransaksi = new javax.swing.JLabel();
        cardKendaraanMasuk = new javax.swing.JPanel();
        lblCardKendaraanMasuk = new javax.swing.JLabel();
        lblJumlahKendaraanMasuk = new javax.swing.JLabel();
        lblKetKendaraanMasuk = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cardPendapatan = new javax.swing.JPanel();
        lblCardKendaraan = new javax.swing.JLabel();
        lblJumlahPendapatan = new javax.swing.JLabel();
        lblIconPendapatan = new javax.swing.JLabel();
        lblKetPendapatan = new javax.swing.JLabel();
        panelTransaksiTerakhir = new javax.swing.JPanel();
        lblJudulTransaksi = new javax.swing.JLabel();
        lblSubTransaksi = new javax.swing.JLabel();
        scrollTransaksi = new javax.swing.JScrollPane();
        tblTransaksiTerakhir = new javax.swing.JTable();
        btnLihatSemua = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        itemDashboard = new javax.swing.JMenuItem();
        itemLogout = new javax.swing.JMenuItem();
        itemKeluar = new javax.swing.JMenuItem();
        menuBantuan = new javax.swing.JMenu();
        itemTentang = new javax.swing.JMenuItem();
        itemPanduan = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistem Parkir");
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

        jPanel1.setBackground(new java.awt.Color(51, 111, 161));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        panelSidebar.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 220, 2));

        panelUtama.add(panelSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 680));

        panelfooter.setBackground(new java.awt.Color(3, 60, 130));
        panelfooter.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVersi.setForeground(new java.awt.Color(255, 255, 255));
        lblVersi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblVersi.setText("Versi 1.0.0");
        lblVersi.setToolTipText("");
        lblVersi.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelfooter.add(lblVersi, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 10, 288, 20));

        panelUtama.add(panelfooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 680, 1360, 40));

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
        lblJudulHeader.setText("Menu Utama");
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

        cardKendaraan.setBackground(new java.awt.Color(255, 255, 255));
        cardKendaraan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        cardKendaraan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblKendaraan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblKendaraan.setForeground(new java.awt.Color(100, 116, 139));
        lblKendaraan.setText("Total Kendaraan");
        cardKendaraan.add(lblKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 12, 150, 25));

        lblJumlahKendaraan.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblJumlahKendaraan.setForeground(new java.awt.Color(0, 126, 224));
        lblJumlahKendaraan.setText("0");
        cardKendaraan.add(lblJumlahKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 35, 120, 40));

        lblKetKendaraan.setForeground(new java.awt.Color(100, 116, 139));
        lblKetKendaraan.setText("Kendaraan Terdaftar");
        cardKendaraan.add(lblKetKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 75, 200, 20));

        lblIconKendaraan.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblIconKendaraan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconKendaraan.setText("<html> <table bgcolor=\"#1976D2\" cellpadding=\"6\" cellspacing=\"0\"> <tr> <td align=\"center\"> <font color=\"white\" size=\"8\" face=\"Segoe UI Symbol\">&#128664;</font> </td> </tr> </table> </html>");
        cardKendaraan.add(lblIconKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 65, 65));

        panelKonten.add(cardKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 25, 240, 100));

        cardTransaksiHariIni.setBackground(new java.awt.Color(255, 255, 255));
        cardTransaksiHariIni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        cardTransaksiHariIni.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCardTransaksi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblCardTransaksi.setForeground(new java.awt.Color(100, 116, 139));
        lblCardTransaksi.setText("Transaksi Hari Ini");
        cardTransaksiHariIni.add(lblCardTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 12, 170, 25));

        lblJumlahTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblJumlahTransaksi.setForeground(new java.awt.Color(22, 163, 74));
        lblJumlahTransaksi.setText("0");
        cardTransaksiHariIni.add(lblJumlahTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 35, 120, 40));

        lblKetTransaksi.setForeground(new java.awt.Color(100, 116, 139));
        lblKetTransaksi.setText("Per Hari ini");
        cardTransaksiHariIni.add(lblKetTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 75, 210, 20));

        lblIconTransaksi.setText("<html>\n<table bgcolor=\"#4CAF50\" cellpadding=\"6\" cellspacing=\"0\">\n<tr>\n<td align=\"center\">\n<font color=\"white\" size=\"8\" face=\"Segoe UI Symbol\"'>&#128196;</font>\n</td>\n</tr>\n</table>\n</html>");
        cardTransaksiHariIni.add(lblIconTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, 85, 65));

        panelKonten.add(cardTransaksiHariIni, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 25, 240, 100));

        cardKendaraanMasuk.setBackground(new java.awt.Color(255, 255, 255));
        cardKendaraanMasuk.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        cardKendaraanMasuk.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCardKendaraanMasuk.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblCardKendaraanMasuk.setForeground(new java.awt.Color(100, 116, 139));
        lblCardKendaraanMasuk.setText("Kendaraan Masuk");
        cardKendaraanMasuk.add(lblCardKendaraanMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 12, 170, 25));

        lblJumlahKendaraanMasuk.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblJumlahKendaraanMasuk.setForeground(new java.awt.Color(245, 158, 11));
        lblJumlahKendaraanMasuk.setText("0");
        cardKendaraanMasuk.add(lblJumlahKendaraanMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 35, 120, 40));

        lblKetKendaraanMasuk.setForeground(new java.awt.Color(100, 116, 139));
        lblKetKendaraanMasuk.setText("Hari ini");
        cardKendaraanMasuk.add(lblKetKendaraanMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 75, 200, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("<html>\n<table bgcolor=\"#FFA000\" cellpadding=\"6\" cellspacing=\"4\">\n<tr>\n<td align=\"center\">\n<font color=\"white\" size=\"8\" face=\"Segoe UI Symbol\"'>&#128682;</font>\n</td>\n</tr>\n</table>\n</html>");
        cardKendaraanMasuk.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, -10, 90, 130));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html> <table \" cellpadding=\"6\" cellspacing=\"0\"> <tr> <td align=\"center\"> <font color=\"BLACK\" size=\"8\" face=\"Segoe UI Symbol\">&#8677;</font> </td> </tr> </table> </html>");
        cardKendaraanMasuk.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 35, 35, 35));

        panelKonten.add(cardKendaraanMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 25, 240, 100));

        cardPendapatan.setBackground(new java.awt.Color(255, 255, 255));
        cardPendapatan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        cardPendapatan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCardKendaraan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblCardKendaraan.setForeground(new java.awt.Color(100, 116, 139));
        lblCardKendaraan.setText("Pendapatan");
        cardPendapatan.add(lblCardKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 12, 170, 25));

        lblJumlahPendapatan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJumlahPendapatan.setForeground(new java.awt.Color(37, 99, 235));
        lblJumlahPendapatan.setText("Rp 0");
        lblJumlahPendapatan.setToolTipText("");
        cardPendapatan.add(lblJumlahPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 35, 260, 40));

        lblIconPendapatan.setText("<html> <table bgcolor=\"#17A2B8\" cellpadding=\"6\" cellspacing=\"0\"> <tr> <td align=\"center\"> <font color=\"white\" size=\"8\" face=\"Segoe UI Symbol\">&#128176;</font> </td> </tr> </table> </html>");
        cardPendapatan.add(lblIconPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, 60, 65));

        lblKetPendapatan.setForeground(new java.awt.Color(100, 116, 139));
        lblKetPendapatan.setText("Hari ini");
        cardPendapatan.add(lblKetPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 75, 210, 20));

        panelKonten.add(cardPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 25, 260, 100));

        panelTransaksiTerakhir.setBackground(new java.awt.Color(255, 255, 255));
        panelTransaksiTerakhir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        panelTransaksiTerakhir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJudulTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblJudulTransaksi.setForeground(new java.awt.Color(30, 41, 59));
        lblJudulTransaksi.setText("Transaksi Parkir Terakhir");
        panelTransaksiTerakhir.add(lblJudulTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 300, 30));

        lblSubTransaksi.setForeground(new java.awt.Color(100, 116, 139));
        lblSubTransaksi.setText("Data Transaksi Terbaru Yang Masuk Ke Sistem Parkir");
        panelTransaksiTerakhir.add(lblSubTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 45, 400, 20));

        tblTransaksiTerakhir.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
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
                "No", "Plat Nomor", "Jenis", "Jam Masuk", "Jam Keluar", "Durasi", "Biaya"
            }
        ));
        scrollTransaksi.setViewportView(tblTransaksiTerakhir);

        panelTransaksiTerakhir.add(scrollTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 1000, 320));

        btnLihatSemua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLihatSemua.setForeground(new java.awt.Color(0, 126, 224));
        btnLihatSemua.setText("<html><span style='font-size:18px; color:#0E7DBB;'>&#9776;</span>&nbsp;&nbsp;Lihat Semua Transaksi</html>");
        btnLihatSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLihatSemuaActionPerformed(evt);
            }
        });
        panelTransaksiTerakhir.add(btnLihatSemua, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 270, -1));

        panelKonten.add(panelTransaksiTerakhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 145, 1040, 450));

        panelUtama.add(panelKonten, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 1100, 650));

        getContentPane().add(panelUtama, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 720));

        menuFile.setText("File");
        menuFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileActionPerformed(evt);
            }
        });

        itemDashboard.setText("Dashboard");
        itemDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemDashboardActionPerformed(evt);
            }
        });
        menuFile.add(itemDashboard);

        itemLogout.setText("Logout");
        itemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemLogoutActionPerformed(evt);
            }
        });
        menuFile.add(itemLogout);

        itemKeluar.setText("Keluar");
        itemKeluar.setToolTipText("");
        itemKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemKeluarActionPerformed(evt);
            }
        });
        menuFile.add(itemKeluar);

        jMenuBar1.add(menuFile);

        menuBantuan.setText("Bantuan");

        itemTentang.setText("Tentang Aplikasi");
        itemTentang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemTentangActionPerformed(evt);
            }
        });
        menuBantuan.add(itemTentang);

        itemPanduan.setText("Panduan Penggunaan");
        itemPanduan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPanduanActionPerformed(evt);
            }
        });
        menuBantuan.add(itemPanduan);

        jMenuBar1.add(menuBantuan);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLihatSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLihatSemuaActionPerformed
        JOptionPane.showMessageDialog(this, "Form semua transaksi akan dibuat setelah ini.");
    }//GEN-LAST:event_btnLihatSemuaActionPerformed

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

    private void menuFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileActionPerformed

    }//GEN-LAST:event_menuFileActionPerformed

    private void itemKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemKeluarActionPerformed
    int pilih = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin keluar dari aplikasi?",
            "Konfirmasi Keluar",
            JOptionPane.YES_NO_OPTION
    );

    if (pilih == JOptionPane.YES_OPTION) {
        System.exit(0);
    }
    }//GEN-LAST:event_itemKeluarActionPerformed

    private void itemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemLogoutActionPerformed
    int pilih = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin logout dari aplikasi?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION
    );

    if (pilih == JOptionPane.YES_OPTION) {
        new Aplikasi_Sistem_Parkir().setVisible(true);
        dispose();
    }
    }//GEN-LAST:event_itemLogoutActionPerformed

    private void itemDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemDashboardActionPerformed
        JOptionPane.showMessageDialog(
                this,
                "Anda sudah berada di halaman Dashboard.",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
        );
    }//GEN-LAST:event_itemDashboardActionPerformed

    private void itemTentangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemTentangActionPerformed
        JOptionPane.showMessageDialog(
                this,
                "Aplikasi Sistem Parkir\n"
                + "Versi 1.0.0\n\n"
                + "Dibuat menggunakan Java Swing dan MySQL.\n"
                + "Fitur utama:\n"
                + "- Login\n"
                + "- Master Data\n"
                + "- Transaksi Parkir\n"
                + "- Laporan Parkir",
                "Tentang Aplikasi",
                JOptionPane.INFORMATION_MESSAGE
        );
    }//GEN-LAST:event_itemTentangActionPerformed

    private void itemPanduanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPanduanActionPerformed
        JOptionPane.showMessageDialog(
            this,
            "Panduan Penggunaan:\n\n"
            + "1. Login menggunakan username dan password.\n"
            + "2. Gunakan Master Data untuk mengelola kendaraan, petugas, tarif, dan user.\n"
            + "3. Gunakan Transaksi untuk mencatat parkir kendaraan.\n"
            + "4. Gunakan Laporan untuk melihat rekap transaksi parkir.\n"
            + "5. Gunakan Logout untuk keluar dari akun.",
            "Panduan Penggunaan",
            JOptionPane.INFORMATION_MESSAGE
    );
    }//GEN-LAST:event_itemPanduanActionPerformed
    
    private void btnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {                                             
        String username = usernameLogin;
        String hakAkses = hakAksesLogin;

        if (username == null || username.trim().isEmpty()) {
            username = "admin";
        }

        if (hakAkses == null || hakAkses.trim().isEmpty()) {
            hakAkses = lblUserLogin.getText().trim();
        }

        new Transaksi_Parkir(username, hakAkses).setVisible(true);
        dispose();
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
            java.util.logging.Logger.getLogger(Menu_Utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu_Utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu_Utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu_Utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_Utama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnLihatSemua;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMasterData;
    private javax.swing.JButton btnTransaksi;
    private javax.swing.JPanel cardKendaraan;
    private javax.swing.JPanel cardKendaraanMasuk;
    private javax.swing.JPanel cardPendapatan;
    private javax.swing.JPanel cardTransaksiHariIni;
    private javax.swing.JMenuItem itemDashboard;
    private javax.swing.JMenuItem itemKeluar;
    private javax.swing.JMenuItem itemLogout;
    private javax.swing.JMenuItem itemPanduan;
    private javax.swing.JMenuItem itemTentang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCardKendaraan;
    private javax.swing.JLabel lblCardKendaraanMasuk;
    private javax.swing.JLabel lblCardTransaksi;
    private javax.swing.JLabel lblIconKendaraan;
    private javax.swing.JLabel lblIconPendapatan;
    private javax.swing.JLabel lblIconTransaksi;
    private javax.swing.JLabel lblJudulHeader;
    private javax.swing.JLabel lblJudulTransaksi;
    private javax.swing.JLabel lblJumlahKendaraan;
    private javax.swing.JLabel lblJumlahKendaraanMasuk;
    private javax.swing.JLabel lblJumlahPendapatan;
    private javax.swing.JLabel lblJumlahTransaksi;
    private javax.swing.JLabel lblKendaraan;
    private javax.swing.JLabel lblKetKendaraan;
    private javax.swing.JLabel lblKetKendaraanMasuk;
    private javax.swing.JLabel lblKetPendapatan;
    private javax.swing.JLabel lblKetTransaksi;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMenuIkon;
    private javax.swing.JLabel lblNamaAplikasi;
    private javax.swing.JLabel lblSubAplikasi;
    private javax.swing.JLabel lblSubTransaksi;
    private javax.swing.JLabel lblUserLogin;
    private javax.swing.JLabel lblVersi;
    private javax.swing.JPanel lineSidebar;
    private javax.swing.JMenu menuBantuan;
    private javax.swing.JMenu menuFile;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelKonten;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel panelTransaksiTerakhir;
    private javax.swing.JPanel panelUtama;
    private javax.swing.JPanel panelfooter;
    private javax.swing.JScrollPane scrollTransaksi;
    private javax.swing.JTable tblTransaksiTerakhir;
    // End of variables declaration//GEN-END:variables
}
