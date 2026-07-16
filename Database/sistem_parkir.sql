-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 30 Jun 2026 pada 18.35
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sistem_parkir`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `kendaraan`
--

CREATE TABLE `kendaraan` (
  `id_kendaraan` varchar(20) NOT NULL,
  `plat_nomor` varchar(20) NOT NULL,
  `jenis_kendaraan` varchar(20) NOT NULL,
  `merk` varchar(50) NOT NULL,
  `warna` varchar(30) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `kendaraan`
--

INSERT INTO `kendaraan` (`id_kendaraan`, `plat_nomor`, `jenis_kendaraan`, `merk`, `warna`, `status`) VALUES
('KDR001', 'B 1234 ABC', 'Mobil', 'Toyota Avanza', 'Hitam', 'Aktif'),
('KDR002', 'D 5678 DEF', 'Motor', 'Honda Beat', 'Merah', 'Aktif'),
('KDR003', 'B 9876 GHI', 'Mobil', 'Daihatsu Xenia', 'Putih', 'Aktif'),
('KDR004', 'F 2468 JKL', 'Motor', 'Yamaha NMAX', 'Biru', 'Aktif'),
('KDR005', 'A 1357 MNO', 'Mobil', 'Honda Brio', 'Silver', 'Aktif'),
('KDR006', 'B 8642 PQR', 'Motor', 'Honda Vario', 'Hitam', 'Aktif'),
('KDR007', 'D 1122 STU', 'Mobil', 'Suzuki Ertiga', 'Abu-Abu', 'Aktif'),
('KDR008', 'F 3344 VWX', 'Motor', 'Yamaha Aerox', 'Kuning', 'Aktif'),
('KDR009', 'B 5566 YZA', 'Mobil', 'Mitsubishi Xpander', 'Putih', 'Aktif'),
('KDR010', 'A 7788 BCD', 'Motor', 'Honda Scoopy', 'Coklat', 'Aktif'),
('KDR011', 'B 9090 EFG', 'Mobil', 'Toyota Innova', 'Hitam', 'Aktif'),
('KDR012', 'D 1212 HIJ', 'Motor', 'Suzuki Satria', 'Merah', 'Aktif'),
('KDR013', 'F 3434 KLM', 'Mobil', 'Honda Jazz', 'Biru', 'Aktif'),
('KDR014', 'B 5656 NOP', 'Motor', 'Yamaha Mio', 'Putih', 'Aktif'),
('KDR015', 'A 7878 QRS', 'Mobil', 'Toyota Rush', 'Silver', 'Aktif'),
('KDR016', 'B 9898 TUV', 'Motor', 'Honda PCX', 'Hitam', 'Aktif'),
('KDR017', 'D 2020 WXY', 'Mobil', 'Daihatsu Terios', 'Merah', 'Aktif'),
('KDR018', 'F 3030 ZAB', 'Motor', 'Kawasaki Ninja', 'Hijau', 'Aktif'),
('KDR019', 'B 4040 CDE', 'Bus', 'Hino Bus', 'Kuning', 'Aktif'),
('KDR020', 'D 5050 FGH', 'Truk', 'Mitsubishi Fuso', 'Orange', 'Aktif'),
('KDR021', 'B 0802 RDW', 'Mobil', 'BMW', 'Hitam', 'Aktif'),
('KDR022', 'B 0302 PNG', 'Motor', 'Beat', 'Biru', 'Aktif'),
('KDR023', 'B 02125 ASF', 'Motor', 'Yamaha', 'Hitam', 'Aktif'),
('KDR024', 'B 2006 RDWN', 'Motor', 'Yamaha Nmax', 'Biru', 'Aktif');

-- --------------------------------------------------------

--
-- Struktur dari tabel `petugas`
--

CREATE TABLE `petugas` (
  `id_petugas` varchar(20) NOT NULL,
  `nama_petugas` varchar(100) NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `shift` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `petugas`
--

INSERT INTO `petugas` (`id_petugas`, `nama_petugas`, `no_hp`, `shift`, `status`) VALUES
('PTG001', 'Budi Santoso', '081234567890', 'Pagi', 'Aktif'),
('PTG002', 'Siti Rahma', '081298765432', 'Siang', 'Aktif'),
('PTG003', 'Rudi Hartono', '081345678901', 'Malam', 'Aktif'),
('PTG004', 'Dewi Anggraini', '081456789012', 'Pagi', 'Aktif'),
('PTG005', 'Andi Pratama', '081567890123', 'Siang', 'Aktif'),
('PTG006', 'Fajar Nugroho', '081678901234', 'Malam', 'Aktif'),
('PTG007', 'Rina Aprilia', '081789012345', 'Pagi', 'Aktif'),
('PTG008', 'Agus Setiawan', '081890123456', 'Siang', 'Aktif'),
('PTG009', 'Maya Lestari', '081901234567', 'Malam', 'Aktif'),
('PTG010', 'Dimas Saputra', '082112345678', 'Pagi', 'Aktif'),
('PTG011', 'Nanda Putri', '082123456789', 'Siang', 'Aktif'),
('PTG012', 'Rizky Maulana', '082134567890', 'Malam', 'Aktif'),
('PTG013', 'Teguh Prakoso', '082145678901', 'Pagi', 'Aktif'),
('PTG014', 'Ayu Wulandari', '082156789012', 'Siang', 'Aktif'),
('PTG015', 'Yoga Firmansyah', '082167890123', 'Malam', 'Aktif'),
('PTG016', 'Nabila Sari', '082178901234', 'Pagi', 'Aktif'),
('PTG017', 'Hendra Wijaya', '082189012345', 'Siang', 'Aktif'),
('PTG018', 'Putri Anjani', '082190123456', 'Malam', 'Aktif'),
('PTG019', 'Arif Hidayat', '083112345678', 'Pagi', 'Aktif'),
('PTG020', 'Lina Marlina', '083123456789', 'Siang', 'Aktif');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tarif_parkir`
--

CREATE TABLE `tarif_parkir` (
  `id_tarif` varchar(20) NOT NULL,
  `jenis_kendaraan` varchar(20) NOT NULL,
  `tarif_awal` int(11) NOT NULL,
  `tarif_per_jam` int(11) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tarif_parkir`
--

INSERT INTO `tarif_parkir` (`id_tarif`, `jenis_kendaraan`, `tarif_awal`, `tarif_per_jam`, `status`) VALUES
('TRF001', 'Motor', 2000, 1000, 'Aktif'),
('TRF002', 'Mobil', 5000, 2000, 'Aktif'),
('TRF003', 'Bus', 8000, 3000, 'Aktif'),
('TRF004', 'Truk', 10000, 4000, 'Aktif'),
('TRF005', 'Motor', 2000, 2500, 'Aktif');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi_parkir`
--

CREATE TABLE `transaksi_parkir` (
  `no_transaksi` varchar(30) NOT NULL,
  `plat_nomor` varchar(20) NOT NULL,
  `jenis_kendaraan` varchar(20) NOT NULL,
  `tanggal_masuk` date DEFAULT NULL,
  `jam_masuk` varchar(10) NOT NULL,
  `tanggal_keluar` date DEFAULT NULL,
  `jam_keluar` varchar(10) NOT NULL,
  `durasi_menit` int(11) NOT NULL DEFAULT 0,
  `durasi_text` varchar(50) NOT NULL,
  `tarif_awal` int(11) NOT NULL,
  `tarif_progresif` int(11) NOT NULL,
  `total_biaya` int(11) NOT NULL,
  `petugas` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(20) NOT NULL DEFAULT 'Selesai'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transaksi_parkir`
--

INSERT INTO `transaksi_parkir` (`no_transaksi`, `plat_nomor`, `jenis_kendaraan`, `tanggal_masuk`, `jam_masuk`, `tanggal_keluar`, `jam_keluar`, `durasi_menit`, `durasi_text`, `tarif_awal`, `tarif_progresif`, `total_biaya`, `petugas`, `created_at`, `status`) VALUES
('TRX20250620001', 'B 1234 ABC', 'Mobil', '2026-06-20', '07:45', '2026-06-20', '10:15', 0, '2 jam 30 menit', 5000, 4000, 9000, 'Budi Santoso', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620002', 'D 5678 DEF', 'Motor', '2026-06-20', '08:02', '2026-06-20', '09:05', 0, '1 jam 3 menit', 2000, 1000, 3000, 'Siti Rahma', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620003', 'B 9876 GHI', 'Mobil', '2026-06-20', '08:15', '2026-06-20', '12:20', 0, '4 jam 5 menit', 5000, 8000, 13000, 'Rudi Hartono', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620004', 'F 2468 JKL', 'Motor', '2026-06-20', '08:22', '2026-06-20', '08:52', 0, '30 menit', 2000, 0, 2000, 'Dewi Anggraini', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620005', 'A 1357 MNO', 'Mobil', '2026-06-20', '09:01', '2026-06-20', '11:31', 0, '2 jam 30 menit', 5000, 4000, 9000, 'Andi Pratama', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620006', 'B 8642 PQR', 'Motor', '2026-06-20', '09:10', '2026-06-20', '10:40', 0, '1 jam 30 menit', 2000, 1000, 3000, 'Fajar Nugroho', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620007', 'D 1122 STU', 'Mobil', '2026-06-20', '09:25', '2026-06-20', '13:25', 0, '4 jam', 5000, 6000, 11000, 'Rina Aprilia', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620008', 'F 3344 VWX', 'Motor', '2026-06-20', '09:35', '2026-06-20', '10:05', 0, '30 menit', 2000, 0, 2000, 'Agus Setiawan', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620009', 'B 5566 YZA', 'Mobil', '2026-06-20', '10:00', '2026-06-20', '15:30', 0, '5 jam 30 menit', 5000, 10000, 15000, 'Maya Lestari', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620010', 'A 7788 BCD', 'Motor', '2026-06-20', '10:15', '2026-06-20', '12:15', 0, '2 jam', 2000, 1000, 3000, 'Dimas Saputra', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620011', 'B 9090 EFG', 'Mobil', '2026-06-21', '07:30', '2026-06-21', '08:45', 0, '1 jam 15 menit', 5000, 2000, 7000, 'Nanda Putri', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620012', 'D 1212 HIJ', 'Motor', '2026-06-21', '08:00', '2026-06-21', '11:10', 0, '3 jam 10 menit', 2000, 3000, 5000, 'Rizky Maulana', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620013', 'F 3434 KLM', 'Mobil', '2026-06-21', '08:20', '2026-06-21', '14:20', 0, '6 jam', 5000, 10000, 15000, 'Teguh Prakoso', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620014', 'B 5656 NOP', 'Motor', '2026-06-21', '08:45', '2026-06-21', '09:20', 0, '35 menit', 2000, 0, 2000, 'Ayu Wulandari', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620015', 'A 7878 QRS', 'Mobil', '2026-06-21', '09:00', '2026-06-21', '11:00', 0, '2 jam', 5000, 2000, 7000, 'Yoga Firmansyah', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620016', 'B 9898 TUV', 'Motor', '2026-06-21', '09:30', '2026-06-21', '13:00', 0, '3 jam 30 menit', 2000, 3000, 5000, 'Nabila Sari', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620017', 'D 2020 WXY', 'Mobil', '2026-06-21', '10:00', '2026-06-21', '12:50', 0, '2 jam 50 menit', 5000, 4000, 9000, 'Hendra Wijaya', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620018', 'F 3030 ZAB', 'Motor', '2026-06-21', '10:10', '2026-06-21', '10:55', 0, '45 menit', 2000, 0, 2000, 'Putri Anjani', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620019', 'B 4040 CDE', 'Bus', '2026-06-21', '10:30', '2026-06-21', '13:30', 0, '3 jam', 8000, 6000, 14000, 'Arif Hidayat', '2026-06-18 15:33:24', 'Selesai'),
('TRX20250620020', 'D 5050 FGH', 'Truk', '2026-06-21', '11:00', '2026-06-21', '15:00', 0, '4 jam', 10000, 12000, 22000, 'Lina Marlina', '2026-06-18 15:33:24', 'Selesai'),
('TRX20260619001', 'B 0802 RDW', 'Mobil', '2026-06-18', '23:04:00', '2026-06-19', '00:04:00', 60, '1 jam', 5000, 0, 5000, 'Budi Santoso', '2026-06-18 17:04:45', 'Selesai'),
('TRX20260619002', 'B 0302 PNG', 'Motor', '2026-06-18', '23:29:00', '2026-06-19', '00:29:00', 60, '1 jam', 2000, 0, 2000, 'Budi Santoso', '2026-06-18 17:30:09', 'Selesai'),
('TRX20260619003', 'B 0802 RDW', 'Mobil', '2026-06-19', '18:10:00', '2026-06-19', '19:10:00', 60, '1 jam', 5000, 0, 5000, 'Rudi Hartono', '2026-06-19 12:10:27', 'Selesai'),
('TRX20260622001', 'B 2006 RDWN', 'Motor', '2026-06-21', '10:00:00', '2026-06-21', '14:30:00', 270, '4 jam 30 menit', 2000, 4000, 6000, 'Rina Aprilia', '2026-06-21 17:11:36', 'Selesai');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id_user` varchar(10) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password_hash` char(64) NOT NULL,
  `hak_akses` enum('Admin','Operator') NOT NULL,
  `status` enum('Aktif','Nonaktif') DEFAULT 'Aktif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id_user`, `username`, `password_hash`, `hak_akses`, `status`) VALUES
('USR001', 'admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Admin', 'Aktif'),
('USR002', 'operator', 'ec6e1c25258002eb1c67d15c7f45da7945fa4c58778fd7d88faa5e53e3b4698d', 'Operator', 'Aktif'),
('USR003', 'ridwan', 'b97a4ad0421b0303115d85881438c2f45337f6d2ab79b3900adf41371bd74b1e', 'Admin', 'Aktif'),
('USR004', 'ridwan1', 'b97a4ad0421b0303115d85881438c2f45337f6d2ab79b3900adf41371bd74b1e', 'Operator', 'Aktif');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `kendaraan`
--
ALTER TABLE `kendaraan`
  ADD PRIMARY KEY (`id_kendaraan`),
  ADD UNIQUE KEY `plat_nomor` (`plat_nomor`);

--
-- Indeks untuk tabel `petugas`
--
ALTER TABLE `petugas`
  ADD PRIMARY KEY (`id_petugas`);

--
-- Indeks untuk tabel `tarif_parkir`
--
ALTER TABLE `tarif_parkir`
  ADD PRIMARY KEY (`id_tarif`);

--
-- Indeks untuk tabel `transaksi_parkir`
--
ALTER TABLE `transaksi_parkir`
  ADD PRIMARY KEY (`no_transaksi`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
