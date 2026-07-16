/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package p17;

import javax.swing.JOptionPane;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Aplikasi_Sistem_Parkir extends javax.swing.JFrame {

    public Aplikasi_Sistem_Parkir() {
        initComponents();
        setSize(1380, 780);
        setLocationRelativeTo(null);
        setResizable(false);
        rapikanTampilan();

        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });
    }
    private void rapikanTampilan() {
           btnLogin.setFocusPainted(false);
           btnLogin.setBorderPainted(false);
           btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

           btnBatal.setFocusPainted(false);
           btnBatal.setCursor(new Cursor(Cursor.HAND_CURSOR));

           txtUsername.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                   javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)),
                   javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)
           ));

           txtPassword.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                   javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)),
                   javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)
           ));

           cmbHakAkses.setBorder(javax.swing.BorderFactory.createLineBorder(
                   new java.awt.Color(226, 232, 240)
           ));

           panelInfoAman.removeAll();

           javax.swing.JLabel lblInfo = new javax.swing.JLabel("Password akan diamankan menggunakan hash.");
           lblInfo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
           lblInfo.setForeground(new java.awt.Color(30, 64, 175));

           panelInfoAman.add(lblInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 370, 25));
           panelInfoAman.revalidate();
           panelInfoAman.repaint();
       }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        panelUtama = new javax.swing.JPanel();
        panelKanan = new javax.swing.JPanel();
        panelLoginCard = new javax.swing.JPanel();
        lblLogoLogin = new javax.swing.JLabel();
        lblJudulLogin = new javax.swing.JLabel();
        lblInfoLogin = new javax.swing.JLabel();
        lblHakAkses = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        cmbHakAkses = new javax.swing.JComboBox<>();
        btnBatal = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        panelInfoAman = new javax.swing.JPanel();
        txtPassword = new javax.swing.JPasswordField();
        panelKiri = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblNamaAplikasi = new javax.swing.JLabel();
        lineKiri = new javax.swing.JPanel();
        lblFitur3 = new javax.swing.JLabel();
        lblFitur1 = new javax.swing.JLabel();
        lblFitur2 = new javax.swing.JLabel();
        panelfooter = new javax.swing.JPanel();
        lblVersi = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        menuBantuan = new javax.swing.JMenu();
        itemTentang = new javax.swing.JMenuItem();
        itemKeluar = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistem Parkir - Login");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelUtama.setBackground(new java.awt.Color(245, 247, 250));
        panelUtama.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelKanan.setBackground(new java.awt.Color(245, 247, 250));
        panelKanan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelLoginCard.setBackground(new java.awt.Color(255, 255, 255));
        panelLoginCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        panelLoginCard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogoLogin.setBackground(new java.awt.Color(0, 126, 224));
        lblLogoLogin.setFont(new java.awt.Font("Segoe UI", 1, 44)); // NOI18N
        lblLogoLogin.setForeground(new java.awt.Color(255, 255, 255));
        lblLogoLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogoLogin.setText("P");
        lblLogoLogin.setOpaque(true);
        panelLoginCard.add(lblLogoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 90, 75));

        lblJudulLogin.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJudulLogin.setForeground(new java.awt.Color(15, 23, 42));
        lblJudulLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJudulLogin.setText("Login Sistem Parkir");
        lblJudulLogin.setToolTipText("");
        panelLoginCard.add(lblJudulLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 350, 35));

        lblInfoLogin.setForeground(new java.awt.Color(100, 116, 139));
        lblInfoLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfoLogin.setText("Silahkan Masuk Untuk Melanjutkan");
        panelLoginCard.add(lblInfoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 230, 25));

        lblHakAkses.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblHakAkses.setForeground(new java.awt.Color(30, 41, 59));
        lblHakAkses.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHakAkses.setText("Hak Akses   :");
        lblHakAkses.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelLoginCard.add(lblHakAkses, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 120, 25));

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        panelLoginCard.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 300, 40));

        lblUsername.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(30, 41, 59));
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsername.setText("Username   :");
        lblUsername.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelLoginCard.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 120, 20));

        lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(30, 41, 59));
        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPassword.setText("Password    :");
        lblPassword.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelLoginCard.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 120, 25));

        cmbHakAkses.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbHakAkses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Operator" }));
        panelLoginCard.add(cmbHakAkses, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 300, 40));

        btnBatal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(30, 41, 59));
        btnBatal.setText("Batal");
        panelLoginCard.add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, 190, 50));

        btnLogin.setBackground(new java.awt.Color(0, 126, 224));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        panelLoginCard.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, 190, 50));

        panelInfoAman.setBackground(new java.awt.Color(239, 246, 255));
        panelInfoAman.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(191, 219, 254)));
        panelInfoAman.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelLoginCard.add(panelInfoAman, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 467, 400, 55));
        panelLoginCard.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 300, 40));

        panelKanan.add(panelLoginCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 500, 530));

        panelUtama.add(panelKanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 640, 610));

        getContentPane().add(panelUtama, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 1000, 640));

        panelKiri.setBackground(new java.awt.Color(3, 70, 122));
        panelKiri.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogo.setBackground(new java.awt.Color(0, 126, 224));
        lblLogo.setFont(new java.awt.Font("Segoe UI", 1, 52)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(255, 255, 255));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setText("P");
        lblLogo.setOpaque(true);
        panelKiri.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 6, 130, 55));

        lblNamaAplikasi.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblNamaAplikasi.setForeground(new java.awt.Color(255, 255, 255));
        lblNamaAplikasi.setText("Sistem Parkir");
        panelKiri.add(lblNamaAplikasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 67, -1, -1));

        lineKiri.setBackground(new java.awt.Color(51, 111, 161));

        javax.swing.GroupLayout lineKiriLayout = new javax.swing.GroupLayout(lineKiri);
        lineKiri.setLayout(lineKiriLayout);
        lineKiriLayout.setHorizontalGroup(
            lineKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        lineKiriLayout.setVerticalGroup(
            lineKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        panelKiri.add(lineKiri, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 300, 2));

        lblFitur3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblFitur3.setForeground(new java.awt.Color(255, 255, 255));
        lblFitur3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFitur3.setText("Terintegrasi - Java Swing + MySQL");
        panelKiri.add(lblFitur3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, 270, 25));

        lblFitur1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblFitur1.setForeground(new java.awt.Color(255, 255, 255));
        lblFitur1.setText("Aman - Data Terenkripsi");
        panelKiri.add(lblFitur1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 150, 25));

        lblFitur2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblFitur2.setForeground(new java.awt.Color(255, 255, 255));
        lblFitur2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFitur2.setText("Efisien - Mudah digunakan");
        panelKiri.add(lblFitur2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 250, 25));

        getContentPane().add(panelKiri, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 640));

        panelfooter.setBackground(new java.awt.Color(3, 60, 130));
        panelfooter.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVersi.setForeground(new java.awt.Color(255, 255, 255));
        lblVersi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblVersi.setText("Versi 1.0.0");
        lblVersi.setToolTipText("");
        lblVersi.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panelfooter.add(lblVersi, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 10, 288, 20));

        getContentPane().add(panelfooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 640, 1340, 40));

        menuBantuan.setText("Bantuan");

        itemTentang.setText("Tentang Aplikasi");
        itemTentang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemTentangActionPerformed(evt);
            }
        });
        menuBantuan.add(itemTentang);

        itemKeluar.setText("Keluar");
        itemKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemKeluarActionPerformed(evt);
            }
        });
        menuBantuan.add(itemKeluar);

        jMenuBar2.add(menuBantuan);

        setJMenuBar(jMenuBar2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
    String username = txtUsername.getText().trim();
    String password = new String(txtPassword.getPassword()).trim();
    String hakAkses = cmbHakAkses.getSelectedItem().toString();

    if (username.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Username tidak boleh kosong.");
        txtUsername.requestFocus();
        return;
    }

    if (password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Password tidak boleh kosong.");
        txtPassword.requestFocus();
        return;
    }

    try {
        Connection conn = Koneksi.getKoneksi();

        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Koneksi database gagal.");
            return;
        }

        String sql = "SELECT * FROM users "
                + "WHERE username = ? "
                + "AND password_hash = ? "
                + "AND hak_akses = ? "
                + "AND status = 'Aktif'";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, Koneksi.sha256(password));
        pst.setString(3, hakAkses);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Login berhasil sebagai " + hakAkses);
            new Menu_Utama(username, hakAkses).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username, password, atau hak akses salah.");
            txtPassword.setText("");
            txtPassword.requestFocus();
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Login gagal: " + e.getMessage());
}
    }//GEN-LAST:event_btnLoginActionPerformed

    private void itemTentangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemTentangActionPerformed
    JOptionPane.showMessageDialog(
            this,
            "Aplikasi Sistem Parkir\n"
            + "Versi 1.0.0\n\n"
            + "Silakan login untuk masuk ke sistem.",
            "Tentang Aplikasi",
            JOptionPane.INFORMATION_MESSAGE
    );
    }//GEN-LAST:event_itemTentangActionPerformed

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
    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {
        txtUsername.setText("");
        txtPassword.setText("");
        cmbHakAkses.setSelectedIndex(0);
        txtUsername.requestFocus();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnLogin;
    private javax.swing.JComboBox<String> cmbHakAkses;
    private javax.swing.JMenuItem itemKeluar;
    private javax.swing.JMenuItem itemTentang;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JLabel lblFitur1;
    private javax.swing.JLabel lblFitur2;
    private javax.swing.JLabel lblFitur3;
    private javax.swing.JLabel lblHakAkses;
    private javax.swing.JLabel lblInfoLogin;
    private javax.swing.JLabel lblJudulLogin;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblLogoLogin;
    private javax.swing.JLabel lblNamaAplikasi;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblVersi;
    private javax.swing.JPanel lineKiri;
    private javax.swing.JMenu menuBantuan;
    private javax.swing.JPanel panelInfoAman;
    private javax.swing.JPanel panelKanan;
    private javax.swing.JPanel panelKiri;
    private javax.swing.JPanel panelLoginCard;
    private javax.swing.JPanel panelUtama;
    private javax.swing.JPanel panelfooter;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
