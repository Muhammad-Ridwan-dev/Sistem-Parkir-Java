/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package p17;

import java.awt.Cursor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Master_Data extends javax.swing.JFrame {

    private String usernameLogin;
    private String hakAksesLogin;
    /**
     * Creates new form Master_Data
     */
    public Master_Data() {
        initComponents();

        this.usernameLogin = "admin";
        this.hakAksesLogin = "Admin";

        setSize(1380, 780);
        setLocationRelativeTo(null);
        setResizable(false);

        tampilkanHakAksesLogin();

        pasangEventTombol();
        rapikanTampilan();
        tampilkanMasterDataSementara();
        aturHakAkses();
    }

    public Master_Data(String username, String hakAkses) {
        initComponents();

        this.usernameLogin = username;
        this.hakAksesLogin = hakAkses;

        setSize(1380, 780);
        setLocationRelativeTo(null);
        setResizable(false);

        tampilkanHakAksesLogin();

        pasangEventTombol();
        rapikanTampilan();
        tampilkanMasterDataSementara();
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

        btnTambah.addActionListener(evt -> btnTambahActionPerformed(evt));
        btnSimpan.addActionListener(evt -> btnSimpanActionPerformed(evt));
        btnUbah.addActionListener(evt -> btnUbahActionPerformed(evt));
        btnHapus.addActionListener(evt -> btnHapusActionPerformed(evt));
        btnCari.addActionListener(evt -> btnCariActionPerformed(evt));
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));

        tabMasterData.addChangeListener(evt -> {
            bersihkanFormAktif();

            JTable tabel = tabelAktif();
            if (tabel != null) {
                tabel.clearSelection();
            }
        });
    }

    private void rapikanTampilan() {
        javax.swing.JButton[] tombol = {
            btnDashboard, btnMasterData, btnTransaksi, btnLaporan,
            btnLogout, btnExit, btnTambah, btnSimpan,
            btnUbah, btnHapus, btnCari, btnRefresh
        };

        for (javax.swing.JButton item : tombol) {
            item.setFocusPainted(false);
            item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        lblUserLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserLogin.setOpaque(true);
        cmbJenisKendaraan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmbJenisTarif.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmbShiftPetugas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmbStatusKendaraan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmbStatusTarif.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmbStatusPetugas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmbHakAksesUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmbStatusUser.setCursor(new Cursor(Cursor.HAND_CURSOR));

        rapikanTabel(tblDataKendaraan);
        rapikanTabel(tblTarifParkir);
        rapikanTabel(tblDataPetugas);
        rapikanTabel(tblDataUser);
    }

    private void rapikanTabel(JTable tabel) {
        tabel.setRowHeight(28);
        tabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        tabel.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        tabel.setGridColor(new java.awt.Color(226, 232, 240));
        tabel.setSelectionBackground(new java.awt.Color(219, 234, 254));
        tabel.setSelectionForeground(new java.awt.Color(15, 23, 42));
    }
    
    private void tampilkanMasterDataSementara() {
        tblDataKendaraan.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Kendaraan", "Plat Nomor", "Jenis", "Merk", "Warna", "Status"}
        ));

        tblTarifParkir.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Tarif", "Jenis Kendaraan", "Tarif Awal", "Tarif Per Jam", "Status"}
        ));

        tblDataPetugas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Petugas", "Nama Petugas", "No HP", "Shift", "Status"}
        ));

        tblDataUser.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID User", "Username", "Hak Akses", "Status"}
        ));

        loadDataKendaraan();
        loadDataTarif();
        loadDataPetugas();
        loadDataUser();

        updateTotalData();
        bersihkanSemuaForm();
    }

    private void loadDataKendaraan() {
        DefaultTableModel model = (DefaultTableModel) tblDataKendaraan.getModel();
        model.setRowCount(0);

        try {
            Connection conn = Koneksi.getKoneksi();
            String sql = "SELECT * FROM kendaraan ORDER BY id_kendaraan ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_kendaraan"),
                    rs.getString("plat_nomor"),
                    rs.getString("jenis_kendaraan"),
                    rs.getString("merk"),
                    rs.getString("warna"),
                    rs.getString("status")
                });
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load kendaraan: " + e.getMessage());
        }
    }

    private void loadDataTarif() {
        DefaultTableModel model = (DefaultTableModel) tblTarifParkir.getModel();
        model.setRowCount(0);

        try {
            Connection conn = Koneksi.getKoneksi();
            String sql = "SELECT * FROM tarif_parkir ORDER BY id_tarif ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_tarif"),
                    rs.getString("jenis_kendaraan"),
                    rs.getInt("tarif_awal"),
                    rs.getInt("tarif_per_jam"),
                    rs.getString("status")
                });
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load tarif: " + e.getMessage());
        }
    }

    private void loadDataPetugas() {
        DefaultTableModel model = (DefaultTableModel) tblDataPetugas.getModel();
        model.setRowCount(0);

        try {
            Connection conn = Koneksi.getKoneksi();
            String sql = "SELECT * FROM petugas ORDER BY id_petugas ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_petugas"),
                    rs.getString("nama_petugas"),
                    rs.getString("no_hp"),
                    rs.getString("shift"),
                    rs.getString("status")
                });
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load petugas: " + e.getMessage());
        }
    }

    private void loadDataUser() {
        DefaultTableModel model = (DefaultTableModel) tblDataUser.getModel();
        model.setRowCount(0);

        try {
            Connection conn = Koneksi.getKoneksi();
            String sql = "SELECT id_user, username, hak_akses, status FROM users ORDER BY id_user ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_user"),
                    rs.getString("username"),
                    rs.getString("hak_akses"),
                    rs.getString("status")
                });
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load user: " + e.getMessage());
        }
    }
    
    private void updateTotalData() {
        lblTotalKendaraan.setText(String.valueOf(tblDataKendaraan.getRowCount()));
        lblTotalPetugas.setText(String.valueOf(tblDataPetugas.getRowCount()));
        lblTotalUser.setText(String.valueOf(tblDataUser.getRowCount()));
    }
    
    private JTable tabelAktif() {
        int index = tabMasterData.getSelectedIndex();

        if (index == 0) {
            return tblDataKendaraan;
        } else if (index == 1) {
            return tblTarifParkir;
        } else if (index == 2) {
            return tblDataPetugas;
        } else if (index == 3) {
            return tblDataUser;
        }

        return null;
    }

    private DefaultTableModel modelAktif() {
        JTable tabel = tabelAktif();

        if (tabel == null) {
            return null;
        }

        return (DefaultTableModel) tabel.getModel();
    }

    private String buatIdOtomatis(String prefix, JTable tabel) {
        int nomor = tabel.getRowCount() + 1;
        return prefix + String.format("%03d", nomor);
    }

    private String isiTeks(Object nilai) {
        return nilai == null ? "" : nilai.toString();
    }
    
    private void bersihkanSemuaForm() {
    bersihkanFormKendaraan();
    bersihkanFormTarif();
    bersihkanFormPetugas();
    bersihkanFormUser();
}

    private void bersihkanFormAktif() {
        int index = tabMasterData.getSelectedIndex();

        if (index == 0) {
            bersihkanFormKendaraan();
        } else if (index == 1) {
            bersihkanFormTarif();
        } else if (index == 2) {
            bersihkanFormPetugas();
        } else if (index == 3) {
            bersihkanFormUser();
        }
    }

    private void bersihkanFormKendaraan() {
        txtIdKendaraan.setText("");
        txtPlatNomor.setText("");
        cmbJenisKendaraan.setSelectedIndex(0);
        txtMerk.setText("");
        txtWarna.setText("");
        cmbStatusKendaraan.setSelectedIndex(0);
    }

    private void bersihkanFormTarif() {
        txtIdTarif.setText("");
        cmbJenisTarif.setSelectedIndex(0);
        txtTarifAwal.setText("");
        txtTarifPerJam.setText("");
        cmbStatusTarif.setSelectedIndex(0);
    }

    private void bersihkanFormPetugas() {
        txtIdPetugas.setText("");
        txtNamaPetugas.setText("");
        txtNoHpPetugas.setText("");
        cmbShiftPetugas.setSelectedIndex(0);
        cmbStatusPetugas.setSelectedIndex(0);
    }

    private void bersihkanFormUser() {
        txtIdUser.setText("");
        txtUsernameUser.setText("");
        txtPasswordUser.setText("");
        cmbHakAksesUser.setSelectedIndex(0);
        cmbStatusUser.setSelectedIndex(0);
    }
    
    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {
    new Menu_Utama(usernameLogin, hakAksesLogin).setVisible(true);
    dispose();
    }

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
    
    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {
        bersihkanFormAktif();

        int index = tabMasterData.getSelectedIndex();

        if (index == 0) {
            txtIdKendaraan.setText(buatIdOtomatis("KDR", tblDataKendaraan));
            txtPlatNomor.requestFocus();
        } else if (index == 1) {
            txtIdTarif.setText(buatIdOtomatis("TRF", tblTarifParkir));
            txtTarifAwal.requestFocus();
        } else if (index == 2) {
            txtIdPetugas.setText(buatIdOtomatis("PTG", tblDataPetugas));
            txtNamaPetugas.requestFocus();
        } else if (index == 3) {
            txtIdUser.setText(buatIdOtomatis("USR", tblDataUser));
            txtUsernameUser.requestFocus();
        }
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        JTable tabel = tabelAktif();

        if (tabel != null) {
            tabel.clearSelection();
        }

        bersihkanFormAktif();
        updateTotalData();
    }
    
    private void aturHakAkses() {
        if ("Operator".equalsIgnoreCase(hakAksesLogin)) {
            btnTambah.setEnabled(false);
            btnSimpan.setEnabled(false);
            btnUbah.setEnabled(false);
            btnHapus.setEnabled(false);

            JOptionPane.showMessageDialog(
                    this,
                    "Anda login sebagai Operator. Akses ubah Master Data dibatasi."
            );
        } else {
            btnTambah.setEnabled(true);
            btnSimpan.setEnabled(true);
            btnUbah.setEnabled(true);
            btnHapus.setEnabled(true);
        }
    }

    private boolean formKendaraanValid() {
        if (txtIdKendaraan.getText().trim().isEmpty()
                || txtPlatNomor.getText().trim().isEmpty()
                || txtMerk.getText().trim().isEmpty()
                || txtWarna.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Data kendaraan belum lengkap.");
            return false;
        }

        return true;
    }

    private boolean formTarifValid() {
        if (txtIdTarif.getText().trim().isEmpty()
                || txtTarifAwal.getText().trim().isEmpty()
                || txtTarifPerJam.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Data tarif parkir belum lengkap.");
            return false;
        }

        return true;
    }

    private boolean formPetugasValid() {
        if (txtIdPetugas.getText().trim().isEmpty()
                || txtNamaPetugas.getText().trim().isEmpty()
                || txtNoHpPetugas.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Data petugas belum lengkap.");
            return false;
        }

        return true;
    }

    private boolean formUserValid() {
        if (txtIdUser.getText().trim().isEmpty()
                || txtUsernameUser.getText().trim().isEmpty()
                || String.valueOf(txtPasswordUser.getPassword()).trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Data user belum lengkap.");
            return false;
        }

        return true;
    }

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
       int index = tabMasterData.getSelectedIndex();

       try {
           Connection conn = Koneksi.getKoneksi();

           if (conn == null) {
               JOptionPane.showMessageDialog(this, "Koneksi database gagal.");
               return;
           }

           if (index == 0) {
               if (!formKendaraanValid()) return;

               String sql = "INSERT INTO kendaraan VALUES (?, ?, ?, ?, ?, ?)";
               PreparedStatement pst = conn.prepareStatement(sql);
               pst.setString(1, txtIdKendaraan.getText().trim());
               pst.setString(2, txtPlatNomor.getText().trim().toUpperCase());
               pst.setString(3, cmbJenisKendaraan.getSelectedItem().toString());
               pst.setString(4, txtMerk.getText().trim());
               pst.setString(5, txtWarna.getText().trim());
               pst.setString(6, cmbStatusKendaraan.getSelectedItem().toString());
               pst.executeUpdate();
               pst.close();

               loadDataKendaraan();

           } else if (index == 1) {
               if (!formTarifValid()) return;

               String sql = "INSERT INTO tarif_parkir VALUES (?, ?, ?, ?, ?)";
               PreparedStatement pst = conn.prepareStatement(sql);
               pst.setString(1, txtIdTarif.getText().trim());
               pst.setString(2, cmbJenisTarif.getSelectedItem().toString());
               pst.setInt(3, Integer.parseInt(txtTarifAwal.getText().trim()));
               pst.setInt(4, Integer.parseInt(txtTarifPerJam.getText().trim()));
               pst.setString(5, cmbStatusTarif.getSelectedItem().toString());
               pst.executeUpdate();
               pst.close();

               loadDataTarif();

           } else if (index == 2) {
               if (!formPetugasValid()) return;

               String sql = "INSERT INTO petugas VALUES (?, ?, ?, ?, ?)";
               PreparedStatement pst = conn.prepareStatement(sql);
               pst.setString(1, txtIdPetugas.getText().trim());
               pst.setString(2, txtNamaPetugas.getText().trim());
               pst.setString(3, txtNoHpPetugas.getText().trim());
               pst.setString(4, cmbShiftPetugas.getSelectedItem().toString());
               pst.setString(5, cmbStatusPetugas.getSelectedItem().toString());
               pst.executeUpdate();
               pst.close();

               loadDataPetugas();

           } else if (index == 3) {
               if (!formUserValid()) return;

               String sql = "INSERT INTO users VALUES (?, ?, ?, ?, ?)";
               PreparedStatement pst = conn.prepareStatement(sql);
               pst.setString(1, txtIdUser.getText().trim());
               pst.setString(2, txtUsernameUser.getText().trim());
               pst.setString(3, Koneksi.sha256(String.valueOf(txtPasswordUser.getPassword()).trim()));
               pst.setString(4, cmbHakAksesUser.getSelectedItem().toString());
               pst.setString(5, cmbStatusUser.getSelectedItem().toString());
               pst.executeUpdate();
               pst.close();

               loadDataUser();
           }

           conn.close();

           updateTotalData();
           bersihkanFormAktif();
           JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke database.");

       } catch (Exception e) {
           JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
       }
    }

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {
        JTable tabel = tabelAktif();
        DefaultTableModel model = modelAktif();
        int index = tabMasterData.getSelectedIndex();

        if (tabel == null || model == null) {
            return;
        }

        int baris = tabel.getSelectedRow();

        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah terlebih dahulu.");
            return;
        }

        if (index == 0) {
            if (!formKendaraanValid()) {
                return;
            }

            model.setValueAt(txtIdKendaraan.getText(), baris, 0);
            model.setValueAt(txtPlatNomor.getText(), baris, 1);
            model.setValueAt(cmbJenisKendaraan.getSelectedItem(), baris, 2);
            model.setValueAt(txtMerk.getText(), baris, 3);
            model.setValueAt(txtWarna.getText(), baris, 4);
            model.setValueAt(cmbStatusKendaraan.getSelectedItem(), baris, 5);

        } else if (index == 1) {
            if (!formTarifValid()) {
                return;
            }

            model.setValueAt(txtIdTarif.getText(), baris, 0);
            model.setValueAt(cmbJenisTarif.getSelectedItem(), baris, 1);
            model.setValueAt(txtTarifAwal.getText(), baris, 2);
            model.setValueAt(txtTarifPerJam.getText(), baris, 3);
            model.setValueAt(cmbStatusTarif.getSelectedItem(), baris, 4);

        } else if (index == 2) {
            if (!formPetugasValid()) {
                return;
            }

            model.setValueAt(txtIdPetugas.getText(), baris, 0);
            model.setValueAt(txtNamaPetugas.getText(), baris, 1);
            model.setValueAt(txtNoHpPetugas.getText(), baris, 2);
            model.setValueAt(cmbShiftPetugas.getSelectedItem(), baris, 3);
            model.setValueAt(cmbStatusPetugas.getSelectedItem(), baris, 4);

        } else if (index == 3) {
            if (!formUserValid()) {
                return;
            }

            model.setValueAt(txtIdUser.getText(), baris, 0);
            model.setValueAt(txtUsernameUser.getText(), baris, 1);
            model.setValueAt(cmbHakAksesUser.getSelectedItem(), baris, 2);
            model.setValueAt(cmbStatusUser.getSelectedItem(), baris, 3);
        }

        updateTotalData();
        JOptionPane.showMessageDialog(this, "Data berhasil diubah.");
    }

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        JTable tabel = tabelAktif();
        DefaultTableModel model = modelAktif();

        if (tabel == null || model == null) {
            return;
        }

        int baris = tabel.getSelectedRow();

        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu.");
            return;
        }

        int pilih = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION
        );

        if (pilih == JOptionPane.YES_OPTION) {
            model.removeRow(baris);
            updateTotalData();
            bersihkanFormAktif();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
        }
    }

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {
        JTable tabel = tabelAktif();
        DefaultTableModel model = modelAktif();

        if (tabel == null || model == null) {
            return;
        }

        String kataKunci = JOptionPane.showInputDialog(this, "Masukkan kata kunci pencarian:");

        if (kataKunci == null || kataKunci.trim().isEmpty()) {
            return;
        }

        String cari = kataKunci.trim().toLowerCase();

        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                String isi = isiTeks(model.getValueAt(i, j)).toLowerCase();

                if (isi.contains(cari)) {
                    tabel.setRowSelectionInterval(i, i);
                    tabel.scrollRectToVisible(tabel.getCellRect(i, 0, true));
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Data tidak ditemukan.");
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
        laneSidebar2 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        lblMenuIkon = new javax.swing.JLabel();
        lblJudulHeader = new javax.swing.JLabel();
        lblUserLogin = new javax.swing.JLabel();
        panelKonten = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        cardTotalUser = new javax.swing.JPanel();
        lblTittleTotalUser = new javax.swing.JLabel();
        lblTotalUser = new javax.swing.JLabel();
        lblKetTotalUser = new javax.swing.JLabel();
        lblIconUser = new javax.swing.JLabel();
        cardTotalKendaraan = new javax.swing.JPanel();
        lblTittleTotalKendaraan = new javax.swing.JLabel();
        lblTotalKendaraan = new javax.swing.JLabel();
        lblKetTotalKendaraan = new javax.swing.JLabel();
        lblIconKendaraan = new javax.swing.JLabel();
        cardTotalPetugas = new javax.swing.JPanel();
        lblTittleTotalPetugas = new javax.swing.JLabel();
        lblTotalPetugas = new javax.swing.JLabel();
        lblKetTotalPetugas = new javax.swing.JLabel();
        lblIconPetugas = new javax.swing.JLabel();
        tabMasterData = new javax.swing.JTabbedPane();
        tabDataKendaraan = new javax.swing.JPanel();
        lblFormKendaraan = new javax.swing.JLabel();
        lblIdKendaraan = new javax.swing.JLabel();
        txtIdKendaraan = new javax.swing.JTextField();
        lblPlatNomor = new javax.swing.JLabel();
        txtPlatNomor = new javax.swing.JTextField();
        lblJenisKendaraan = new javax.swing.JLabel();
        cmbJenisKendaraan = new javax.swing.JComboBox<>();
        lblMerk = new javax.swing.JLabel();
        txtMerk = new javax.swing.JTextField();
        lblWarna = new javax.swing.JLabel();
        txtWarna = new javax.swing.JTextField();
        lblStatusKendaraan = new javax.swing.JLabel();
        cmbStatusKendaraan = new javax.swing.JComboBox<>();
        lblDaftarKendaraan = new javax.swing.JLabel();
        scrollKendaraan = new javax.swing.JScrollPane();
        tblKendaraan = new javax.swing.JScrollPane();
        tblDataKendaraan = new javax.swing.JTable();
        tabTarifParkir = new javax.swing.JPanel();
        lblJudulTarifParkir = new javax.swing.JLabel();
        lblIdTarif = new javax.swing.JLabel();
        txtIdTarif = new javax.swing.JTextField();
        lblJenisTarif = new javax.swing.JLabel();
        lblTarifAwal = new javax.swing.JLabel();
        cmbJenisTarif = new javax.swing.JComboBox<>();
        lblTarifPerJam = new javax.swing.JLabel();
        txtTarifPerJam = new javax.swing.JTextField();
        lblStatusTarif = new javax.swing.JLabel();
        cmbStatusTarif = new javax.swing.JComboBox<>();
        lblDaftarTarifParkirKendaraan = new javax.swing.JLabel();
        scrollTarif = new javax.swing.JScrollPane();
        tblTarif = new javax.swing.JScrollPane();
        tblTarifParkir = new javax.swing.JTable();
        txtTarifAwal = new javax.swing.JTextField();
        tabPetugas = new javax.swing.JPanel();
        lblFormPetugas = new javax.swing.JLabel();
        lblIdPetugas = new javax.swing.JLabel();
        txtIdPetugas = new javax.swing.JTextField();
        lblStatusPetugas = new javax.swing.JLabel();
        lblNamaPetugas = new javax.swing.JLabel();
        cmbStatusPetugas = new javax.swing.JComboBox<>();
        lblNoHpPetugas = new javax.swing.JLabel();
        txtNoHpPetugas = new javax.swing.JTextField();
        lblShiftPetugas = new javax.swing.JLabel();
        cmbShiftPetugas = new javax.swing.JComboBox<>();
        lblDaftarPetugas = new javax.swing.JLabel();
        scrollPetugas = new javax.swing.JScrollPane();
        tblPetugas = new javax.swing.JScrollPane();
        tblDataPetugas = new javax.swing.JTable();
        txtNamaPetugas = new javax.swing.JTextField();
        tabUser = new javax.swing.JPanel();
        lblFormUser = new javax.swing.JLabel();
        lblIdUser = new javax.swing.JLabel();
        txtIdUser = new javax.swing.JTextField();
        lblStatusUser = new javax.swing.JLabel();
        lblUsernameUser = new javax.swing.JLabel();
        cmbStatusUser = new javax.swing.JComboBox<>();
        lblPaswordUser = new javax.swing.JLabel();
        lblHakAksesUser = new javax.swing.JLabel();
        cmbHakAksesUser = new javax.swing.JComboBox<>();
        lblDaftarUser = new javax.swing.JLabel();
        scrollUser = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JScrollPane();
        tblDataUser = new javax.swing.JTable();
        txtUsernameUser = new javax.swing.JTextField();
        txtPasswordUser = new javax.swing.JPasswordField();
        panelfooter = new javax.swing.JPanel();
        lblVersi = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Master Data - Sistem Parkir");
        setMinimumSize(new java.awt.Dimension(1360, 780));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelUtama.setBackground(new java.awt.Color(245, 247, 250));
        panelUtama.setMinimumSize(new java.awt.Dimension(1360, 780));
        panelUtama.setPreferredSize(new java.awt.Dimension(1360, 780));
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

        laneSidebar2.setBackground(new java.awt.Color(51, 111, 161));

        javax.swing.GroupLayout laneSidebar2Layout = new javax.swing.GroupLayout(laneSidebar2);
        laneSidebar2.setLayout(laneSidebar2Layout);
        laneSidebar2Layout.setHorizontalGroup(
            laneSidebar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        laneSidebar2Layout.setVerticalGroup(
            laneSidebar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        panelSidebar.add(laneSidebar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 220, 2));

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
        lblJudulHeader.setText("Master Data");
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

        btnTambah.setBackground(new java.awt.Color(0, 126, 224));
        btnTambah.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setText("Tambah");
        panelKonten.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, 40));

        btnSimpan.setBackground(new java.awt.Color(34, 197, 94));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        panelKonten.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 110, 40));

        btnUbah.setBackground(new java.awt.Color(245, 158, 11));
        btnUbah.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnUbah.setForeground(new java.awt.Color(255, 255, 255));
        btnUbah.setText("Ubah");
        panelKonten.add(btnUbah, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 110, 40));

        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(30, 41, 59));
        btnRefresh.setText("Refresh");
        panelKonten.add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 74, 95, 30));

        btnHapus.setBackground(new java.awt.Color(220, 38, 38));
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setText("Hapus");
        panelKonten.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 110, 40));

        btnCari.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnCari.setForeground(new java.awt.Color(0, 126, 224));
        btnCari.setText("Cari");
        panelKonten.add(btnCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 74, 95, 30));

        cardTotalUser.setBackground(new java.awt.Color(255, 255, 255));
        cardTotalUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        cardTotalUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTittleTotalUser.setForeground(new java.awt.Color(100, 116, 139));
        lblTittleTotalUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTittleTotalUser.setText("Total User");
        cardTotalUser.add(lblTittleTotalUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 15, 100, 20));

        lblTotalUser.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTotalUser.setForeground(new java.awt.Color(204, 0, 204));
        lblTotalUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalUser.setText("0");
        cardTotalUser.add(lblTotalUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 35, 90, 30));

        lblKetTotalUser.setForeground(new java.awt.Color(100, 116, 139));
        lblKetTotalUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKetTotalUser.setText("User");
        cardTotalUser.add(lblKetTotalUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 63, 90, 20));

        lblIconUser.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblIconUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconUser.setText("<html> <table bgcolor=\"#6F42C1\" cellpadding=\"5\" cellspacing=\"0\"> <tr> <td align=\"center\"> <font color=\"white\" size=\"7\" face=\"Segoe UI Symbol\">&#128101;</font> </td> </tr> </table> </html>");
        lblIconUser.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        cardTotalUser.add(lblIconUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 17, 60, 60));

        panelKonten.add(cardTotalUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 20, 160, 90));

        cardTotalKendaraan.setBackground(new java.awt.Color(255, 255, 255));
        cardTotalKendaraan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        cardTotalKendaraan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTittleTotalKendaraan.setForeground(new java.awt.Color(100, 116, 139));
        lblTittleTotalKendaraan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTittleTotalKendaraan.setText("Total Kendaraan");
        cardTotalKendaraan.add(lblTittleTotalKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 15, 100, 20));

        lblTotalKendaraan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTotalKendaraan.setForeground(new java.awt.Color(0, 126, 224));
        lblTotalKendaraan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalKendaraan.setText("0");
        cardTotalKendaraan.add(lblTotalKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 35, 90, 30));

        lblKetTotalKendaraan.setForeground(new java.awt.Color(100, 116, 139));
        lblKetTotalKendaraan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKetTotalKendaraan.setText("Kendaraan");
        cardTotalKendaraan.add(lblKetTotalKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 63, 90, 20));

        lblIconKendaraan.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblIconKendaraan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconKendaraan.setText("<html> <table bgcolor=\"#1976D2\" cellpadding=\"5\" cellspacing=\"0\"> <tr> <td align=\"center\"> <font color=\"white\" size=\"7\" face=\"Segoe UI Symbol\">&#128664;</font> </td> </tr> </table> </html>");
        lblIconKendaraan.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        cardTotalKendaraan.add(lblIconKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 17, 60, 60));

        panelKonten.add(cardTotalKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 160, 90));

        cardTotalPetugas.setBackground(new java.awt.Color(255, 255, 255));
        cardTotalPetugas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        cardTotalPetugas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTittleTotalPetugas.setForeground(new java.awt.Color(100, 116, 139));
        lblTittleTotalPetugas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTittleTotalPetugas.setText("Total Petugas");
        cardTotalPetugas.add(lblTittleTotalPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 15, 100, 20));

        lblTotalPetugas.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTotalPetugas.setForeground(new java.awt.Color(0, 204, 0));
        lblTotalPetugas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalPetugas.setText("0");
        cardTotalPetugas.add(lblTotalPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 35, 90, 30));

        lblKetTotalPetugas.setForeground(new java.awt.Color(100, 116, 139));
        lblKetTotalPetugas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKetTotalPetugas.setText("Petugas");
        cardTotalPetugas.add(lblKetTotalPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 63, 90, 20));

        lblIconPetugas.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblIconPetugas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconPetugas.setText("<html> <table bgcolor=\"#28A745\" cellpadding=\"5\" cellspacing=\"0\"> <tr> <td align=\"center\"> <font color=\"white\" size=\"7\" face=\"Segoe UI Symbol\">&#128100;</font> </td> </tr> </table> </html>");
        lblIconPetugas.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        cardTotalPetugas.add(lblIconPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 17, 60, 60));

        panelKonten.add(cardTotalPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(735, 20, 160, 90));

        tabMasterData.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N

        tabDataKendaraan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFormKendaraan.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblFormKendaraan.setForeground(new java.awt.Color(0, 126, 224));
        lblFormKendaraan.setText("Form Data Kendaraan");
        tabDataKendaraan.add(lblFormKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 250, 25));

        lblIdKendaraan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdKendaraan.setText("ID Kendaraan ");
        tabDataKendaraan.add(lblIdKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 130, 25));
        tabDataKendaraan.add(txtIdKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 55, 280, 35));

        lblPlatNomor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPlatNomor.setText("Plat Nomor");
        tabDataKendaraan.add(lblPlatNomor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 130, 25));
        tabDataKendaraan.add(txtPlatNomor, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 105, 280, 35));

        lblJenisKendaraan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblJenisKendaraan.setText("Jenis Kendaraan");
        tabDataKendaraan.add(lblJenisKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 130, 25));

        cmbJenisKendaraan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Motor", "Mobil", "Bus", "Truk" }));
        tabDataKendaraan.add(cmbJenisKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 155, 280, 35));

        lblMerk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMerk.setText("Merk");
        tabDataKendaraan.add(lblMerk, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 130, 25));
        tabDataKendaraan.add(txtMerk, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 55, 280, 35));

        lblWarna.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblWarna.setText("Warna");
        tabDataKendaraan.add(lblWarna, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, 130, 25));
        tabDataKendaraan.add(txtWarna, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 105, 280, 35));

        lblStatusKendaraan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStatusKendaraan.setText("Status Kendaraan");
        tabDataKendaraan.add(lblStatusKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 160, 130, 25));

        cmbStatusKendaraan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif", "Nonaktif" }));
        tabDataKendaraan.add(cmbStatusKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 155, 280, 35));

        lblDaftarKendaraan.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblDaftarKendaraan.setForeground(new java.awt.Color(0, 126, 224));
        lblDaftarKendaraan.setText("Daftar Data Kendaraan");
        tabDataKendaraan.add(lblDaftarKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 215, 250, 25));

        tblDataKendaraan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Kendaraan", "Plat Nomor", "Jenis", "Merk", "Warna", "Status"
            }
        ));
        tblKendaraan.setViewportView(tblDataKendaraan);

        scrollKendaraan.setViewportView(tblKendaraan);

        tabDataKendaraan.add(scrollKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 1000, 160));

        tabMasterData.addTab("Data Kendaraan", tabDataKendaraan);

        tabTarifParkir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJudulTarifParkir.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblJudulTarifParkir.setForeground(new java.awt.Color(0, 126, 224));
        lblJudulTarifParkir.setText("Form Tarif Parkir");
        tabTarifParkir.add(lblJudulTarifParkir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 250, 25));

        lblIdTarif.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdTarif.setText("ID Tarif");
        tabTarifParkir.add(lblIdTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 130, 25));
        tabTarifParkir.add(txtIdTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 55, 280, 35));

        lblJenisTarif.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblJenisTarif.setText("Jenis Kendaraan");
        tabTarifParkir.add(lblJenisTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 130, 25));

        lblTarifAwal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTarifAwal.setText("Tarif Awal");
        tabTarifParkir.add(lblTarifAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 130, 25));

        cmbJenisTarif.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Motor", "Mobil", "Bus", "Truk" }));
        tabTarifParkir.add(cmbJenisTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 105, 280, 35));

        lblTarifPerJam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTarifPerJam.setText("Tarif Per Jam");
        tabTarifParkir.add(lblTarifPerJam, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 130, 25));
        tabTarifParkir.add(txtTarifPerJam, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 55, 280, 35));

        lblStatusTarif.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStatusTarif.setText("Status Tarif");
        tabTarifParkir.add(lblStatusTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, 130, 25));

        cmbStatusTarif.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif", "Nonaktif" }));
        tabTarifParkir.add(cmbStatusTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 105, 280, 35));

        lblDaftarTarifParkirKendaraan.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblDaftarTarifParkirKendaraan.setForeground(new java.awt.Color(0, 126, 224));
        lblDaftarTarifParkirKendaraan.setText("Daftar Tarif Parkir Kendaraan");
        tabTarifParkir.add(lblDaftarTarifParkirKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 215, 250, 25));

        tblTarifParkir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Tarif", "Jenis Kendaraan", "Tarif Awal", "Tarif Per Jam", "Status"
            }
        ));
        tblTarif.setViewportView(tblTarifParkir);

        scrollTarif.setViewportView(tblTarif);

        tabTarifParkir.add(scrollTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 1000, 160));
        tabTarifParkir.add(txtTarifAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 155, 280, 35));

        tabMasterData.addTab("Tarif Parkir", tabTarifParkir);

        tabPetugas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFormPetugas.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblFormPetugas.setForeground(new java.awt.Color(0, 126, 224));
        lblFormPetugas.setText("Form Data Petugas");
        tabPetugas.add(lblFormPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 250, 25));

        lblIdPetugas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdPetugas.setText("ID Petugas");
        tabPetugas.add(lblIdPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 130, 25));
        tabPetugas.add(txtIdPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 55, 280, 35));

        lblStatusPetugas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStatusPetugas.setText("Status");
        tabPetugas.add(lblStatusPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, 130, 25));

        lblNamaPetugas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNamaPetugas.setText("Nama Petugas");
        tabPetugas.add(lblNamaPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 130, 25));

        cmbStatusPetugas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif", "Nonaktif" }));
        tabPetugas.add(cmbStatusPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 105, 280, 35));

        lblNoHpPetugas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNoHpPetugas.setText("No HP");
        tabPetugas.add(lblNoHpPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 130, 25));
        tabPetugas.add(txtNoHpPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 155, 280, 35));

        lblShiftPetugas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblShiftPetugas.setText("Shift");
        tabPetugas.add(lblShiftPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 130, 25));

        cmbShiftPetugas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pagi", "Siang", "Sore", "Malam" }));
        tabPetugas.add(cmbShiftPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 55, 280, 35));

        lblDaftarPetugas.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblDaftarPetugas.setForeground(new java.awt.Color(0, 126, 224));
        lblDaftarPetugas.setText("Daftar Data Petugas");
        tabPetugas.add(lblDaftarPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 215, 250, 25));

        tblDataPetugas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Petugas", "Nama Petugas", "No HP", "Shift", "Status"
            }
        ));
        tblPetugas.setViewportView(tblDataPetugas);

        scrollPetugas.setViewportView(tblPetugas);

        tabPetugas.add(scrollPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 1000, 160));
        tabPetugas.add(txtNamaPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 105, 280, 35));

        tabMasterData.addTab("Petugas", tabPetugas);

        tabUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFormUser.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblFormUser.setForeground(new java.awt.Color(0, 126, 224));
        lblFormUser.setText("Form User");
        tabUser.add(lblFormUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 250, 25));

        lblIdUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdUser.setText("ID User");
        tabUser.add(lblIdUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 130, 25));
        tabUser.add(txtIdUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 55, 280, 35));

        lblStatusUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStatusUser.setText("Status User");
        tabUser.add(lblStatusUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, 130, 25));

        lblUsernameUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblUsernameUser.setText("Username");
        tabUser.add(lblUsernameUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 130, 25));

        cmbStatusUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif", "Nonaktif" }));
        cmbStatusUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusUserActionPerformed(evt);
            }
        });
        tabUser.add(cmbStatusUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 105, 280, 35));

        lblPaswordUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPaswordUser.setText("Password");
        tabUser.add(lblPaswordUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 130, 25));

        lblHakAksesUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHakAksesUser.setText("Hak Akses");
        tabUser.add(lblHakAksesUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 130, 25));

        cmbHakAksesUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Operator" }));
        tabUser.add(cmbHakAksesUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 55, 280, 35));

        lblDaftarUser.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblDaftarUser.setForeground(new java.awt.Color(0, 126, 224));
        lblDaftarUser.setText("Daftar Data User");
        tabUser.add(lblDaftarUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 215, 250, 25));

        tblDataUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID User", "Username", "Hak Akses", "Status"
            }
        ));
        tblUser.setViewportView(tblDataUser);

        scrollUser.setViewportView(tblUser);

        tabUser.add(scrollUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 1000, 160));
        tabUser.add(txtUsernameUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 105, 280, 35));
        tabUser.add(txtPasswordUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 155, 280, 35));

        tabMasterData.addTab("User", tabUser);

        panelKonten.add(tabMasterData, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 1050, 455));

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

        getContentPane().add(panelUtama, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 780));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMasterDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasterDataActionPerformed
        tabMasterData.setSelectedIndex(0);
    }//GEN-LAST:event_btnMasterDataActionPerformed

    private void cmbStatusUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusUserActionPerformed

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
            java.util.logging.Logger.getLogger(Master_Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master_Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master_Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master_Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_Data().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMasterData;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnTransaksi;
    private javax.swing.JButton btnUbah;
    private javax.swing.JPanel cardTotalKendaraan;
    private javax.swing.JPanel cardTotalPetugas;
    private javax.swing.JPanel cardTotalUser;
    private javax.swing.JComboBox<String> cmbHakAksesUser;
    private javax.swing.JComboBox<String> cmbJenisKendaraan;
    private javax.swing.JComboBox<String> cmbJenisTarif;
    private javax.swing.JComboBox<String> cmbShiftPetugas;
    private javax.swing.JComboBox<String> cmbStatusKendaraan;
    private javax.swing.JComboBox<String> cmbStatusPetugas;
    private javax.swing.JComboBox<String> cmbStatusTarif;
    private javax.swing.JComboBox<String> cmbStatusUser;
    private javax.swing.JPanel laneSidebar2;
    private javax.swing.JLabel lblDaftarKendaraan;
    private javax.swing.JLabel lblDaftarPetugas;
    private javax.swing.JLabel lblDaftarTarifParkirKendaraan;
    private javax.swing.JLabel lblDaftarUser;
    private javax.swing.JLabel lblFormKendaraan;
    private javax.swing.JLabel lblFormPetugas;
    private javax.swing.JLabel lblFormUser;
    private javax.swing.JLabel lblHakAksesUser;
    private javax.swing.JLabel lblIconKendaraan;
    private javax.swing.JLabel lblIconPetugas;
    private javax.swing.JLabel lblIconUser;
    private javax.swing.JLabel lblIdKendaraan;
    private javax.swing.JLabel lblIdPetugas;
    private javax.swing.JLabel lblIdTarif;
    private javax.swing.JLabel lblIdUser;
    private javax.swing.JLabel lblJenisKendaraan;
    private javax.swing.JLabel lblJenisTarif;
    private javax.swing.JLabel lblJudulHeader;
    private javax.swing.JLabel lblJudulTarifParkir;
    private javax.swing.JLabel lblKetTotalKendaraan;
    private javax.swing.JLabel lblKetTotalPetugas;
    private javax.swing.JLabel lblKetTotalUser;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMenuIkon;
    private javax.swing.JLabel lblMerk;
    private javax.swing.JLabel lblNamaAplikasi;
    private javax.swing.JLabel lblNamaPetugas;
    private javax.swing.JLabel lblNoHpPetugas;
    private javax.swing.JLabel lblPaswordUser;
    private javax.swing.JLabel lblPlatNomor;
    private javax.swing.JLabel lblShiftPetugas;
    private javax.swing.JLabel lblStatusKendaraan;
    private javax.swing.JLabel lblStatusPetugas;
    private javax.swing.JLabel lblStatusTarif;
    private javax.swing.JLabel lblStatusUser;
    private javax.swing.JLabel lblSubAplikasi;
    private javax.swing.JLabel lblTarifAwal;
    private javax.swing.JLabel lblTarifPerJam;
    private javax.swing.JLabel lblTittleTotalKendaraan;
    private javax.swing.JLabel lblTittleTotalPetugas;
    private javax.swing.JLabel lblTittleTotalUser;
    private javax.swing.JLabel lblTotalKendaraan;
    private javax.swing.JLabel lblTotalPetugas;
    private javax.swing.JLabel lblTotalUser;
    private javax.swing.JLabel lblUserLogin;
    private javax.swing.JLabel lblUsernameUser;
    private javax.swing.JLabel lblVersi;
    private javax.swing.JLabel lblWarna;
    private javax.swing.JPanel lineSidebar;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelKonten;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel panelUtama;
    private javax.swing.JPanel panelfooter;
    private javax.swing.JScrollPane scrollKendaraan;
    private javax.swing.JScrollPane scrollPetugas;
    private javax.swing.JScrollPane scrollTarif;
    private javax.swing.JScrollPane scrollUser;
    private javax.swing.JPanel tabDataKendaraan;
    private javax.swing.JTabbedPane tabMasterData;
    private javax.swing.JPanel tabPetugas;
    private javax.swing.JPanel tabTarifParkir;
    private javax.swing.JPanel tabUser;
    private javax.swing.JTable tblDataKendaraan;
    private javax.swing.JTable tblDataPetugas;
    private javax.swing.JTable tblDataUser;
    private javax.swing.JScrollPane tblKendaraan;
    private javax.swing.JScrollPane tblPetugas;
    private javax.swing.JScrollPane tblTarif;
    private javax.swing.JTable tblTarifParkir;
    private javax.swing.JScrollPane tblUser;
    private javax.swing.JTextField txtIdKendaraan;
    private javax.swing.JTextField txtIdPetugas;
    private javax.swing.JTextField txtIdTarif;
    private javax.swing.JTextField txtIdUser;
    private javax.swing.JTextField txtMerk;
    private javax.swing.JTextField txtNamaPetugas;
    private javax.swing.JTextField txtNoHpPetugas;
    private javax.swing.JPasswordField txtPasswordUser;
    private javax.swing.JTextField txtPlatNomor;
    private javax.swing.JTextField txtTarifAwal;
    private javax.swing.JTextField txtTarifPerJam;
    private javax.swing.JTextField txtUsernameUser;
    private javax.swing.JTextField txtWarna;
    // End of variables declaration//GEN-END:variables
}
