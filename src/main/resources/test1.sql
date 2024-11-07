-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 07, 2024 at 07:46 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test1`
--

-- --------------------------------------------------------

--
-- Table structure for table `car`
--

CREATE TABLE `car` (
  `car_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `notation` varchar(4000) NOT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `manufactured_year` int(11) DEFAULT NULL,
  `last_modified_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `creation_timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `car`
--

INSERT INTO `car` (`car_id`, `name`, `price`, `notation`, `manufacturer`, `manufactured_year`, `last_modified_timestamp`, `creation_timestamp`) VALUES
(15, 'Sedan', 1010, '1990 sedan white', 'toyota', 1990, '2024-11-06 08:13:50', '2024-11-02 03:28:53'),
(16, 'test16', 16, 'test16', 'test16', 2016, '2024-11-06 08:09:51', '2024-11-02 07:21:16'),
(17, 'test17', 17.17, 'test17', 'test17', 2017, '2024-11-06 02:57:51', '2024-11-06 02:57:51'),
(18, 'test18', 18, '18test', 'test18', 2018, '2024-11-06 06:54:11', '2024-11-06 06:54:11'),
(20, 'Isuzu revo 2016', 800, 'Black Isuzu revo 2016', 'Isuzu', 2016, '2024-11-06 07:12:14', '2024-11-06 06:54:11'),
(21, 'Tesla truck 2023', 1250, 'Tesla truck 2023', 'Tesla', 2023, '2024-11-06 06:54:11', '2024-11-06 06:54:11'),
(22, 'Toyota camry 2018', 950, 'Red Toyota camry 2018', 'Toyota', 2018, '2024-11-06 06:54:11', '2024-11-06 06:54:11'),
(23, 'Tesla model s 2024', 1300, 'White Tesla model s 2024', 'Tesla', 2024, '2024-11-06 07:12:14', '2024-11-06 06:54:11'),
(24, 'Nissan almera 2024', 950, 'Blue nissan almera 2024', 'Nissan', 2024, '2024-11-06 08:05:27', '2024-11-06 06:54:11'),
(25, 'Isuzu mu-x 2023', 1000, 'Pearl white Isuzu mu-x 2023', 'Isuzu', 2023, '2024-11-06 07:30:26', '2024-11-06 06:54:11'),
(26, 'Deepal L07 2024', 1200, 'Gray Deepal L07 2024', 'Deepal', 2024, '2024-11-06 06:54:11', '2024-11-06 06:54:11'),
(27, 'BMW 320D 2023', 800, 'BMW 320D 2023', 'BMW', 2023, '2024-11-06 06:54:11', '2024-11-06 06:54:11'),
(28, 'Benz A200 2023', 1250, 'Black Benz A200 2023', 'Benz', 2023, '2024-11-06 06:54:11', '2024-11-06 06:54:11'),
(29, 'Mitsubishi Xpender 2021', 950, 'White Mitsubishi Xpender 2021', 'Mitsubishi', 2021, '2024-11-06 06:54:11', '2024-11-06 06:54:11'),
(30, 'Toyota new yaris 2023', 1300, 'White Toyota new yaris 2023', 'Toyota', 2023, '2024-11-06 06:54:11', '2024-11-06 06:54:11'),
(31, 'BYD Seal 2024', 950, 'White BYD Seal 2024', 'BYD', 2024, '2024-11-06 06:54:11', '2024-11-06 06:54:11'),
(32, 'Honda accord 2024', 1000, 'White Honda accord 2024', 'Honda', 2024, '2024-11-06 06:54:11', '2024-11-06 06:54:11'),
(33, 'Benz C350e 2018', 1200, 'White Benz C350e 2018', 'Benz', 2018, '2024-11-06 07:12:14', '2024-11-06 07:12:14'),
(34, 'Toyota Vellfire 2018', 800, 'White Toyota Vellfire 2018', 'Toyota', 2018, '2024-11-06 07:12:14', '2024-11-06 07:12:14'),
(35, 'MG HS 2022', 1250, 'Gray MG HS 2022', 'MG', 2023, '2024-11-06 07:12:14', '2024-11-06 07:12:14');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `creation_timestamp` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `last_modified_timestamp` datetime(6) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `telno` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `address`, `creation_timestamp`, `email`, `firstname`, `last_modified_timestamp`, `password`, `surname`, `telno`) VALUES
(1, '9/35 Phaholyothin 44 Phaholyothin Rd.', '2024-10-24 07:01:57.000000', 'chawishp@hotmail.com', 'Chawish', NULL, '$2a$12$cjzM2BXuQy4LTsYqMcBKUejuNdpm/7tZd43CGplYUXZTBhi0fe4Gm', 'Pipopsophonchai', '123213'),
(2, 'fsadfdsa', '2024-10-25 05:11:05.000000', 'your.name@email.com', 'asdad', NULL, '$2a$12$t3C27h6K5c8XbKm4VVR2..Mn9ZKmuIrrg3yF8N6DEVa5ArSAP0b3K', 'sadfdsafdsa', '1234231432'),
(3, '', '2024-10-25 08:49:46.000000', 'test@e.com', ' ', NULL, '$2a$12$VHF3WIx9a6hGkfY7jDGg3.IXNxfyibsOviHP36RgJO2tZh5dPNO0W', NULL, ''),
(4, '', '2024-10-25 08:50:43.000000', 'test@e.com', ' ', NULL, '$2a$12$uTL4Xm.Ovcazz58rTUb.6OCRZMNYNTY66cQf97PxZ0dyBrahlHzpy', NULL, 'dsaf'),
(5, 'test', '2024-10-25 10:21:12.000000', 'test@test.com', 'test', NULL, '$2a$12$O5aewCyPUeVD9ulDCvMW8.CVITtFkGONNWtBqlim/oFp660pqu9Y6', 'test', '123'),
(11, '9/35 phaholyothin 44', '2024-10-30 10:14:04.000000', 'test1@test.com', 'Chawish', '2024-10-30 10:30:42.000000', '$2a$12$i/sRYFCzPygX17N3itUY5OP/3ZTYvjT6Xljaqs1DfgqKX4WCVXjeO', 'Pipopsophonchai', '0614953963'),
(12, '9/35 Soi Phaholyothin 44', '2024-10-31 16:08:18.000000', 'shenlijun@138.cn', 'Shen', NULL, '$2a$12$mieCLgCns0pp8OvsGyxsGeCdl81zOJzwBUipZNwKOEOQCLiMk/cua', 'li jun', '1234535344'),
(14, '9/35 Soi Phaholyothin 44 Phaholyothin Rd.', '2024-10-31 16:50:15.000000', 'shenlijun@138.cn', 'chawish', NULL, '$2a$12$DDwKrdhdAnWW3CD6KVmLf.WOXCc76eyKnMH5iaUpPuvW1f1inoIO2', 'Pipopsophonchai', '1234567890'),
(15, 'test1', '2024-10-31 17:31:06.000000', 'test1@yahoo.com', 'test01', NULL, '$2a$12$/LBMOCLvVdK3BQMRWkc6Wu0ayGFpxloVQOmu4eq43syEZ7ZZAD2mO', 'lastname', '0123456789'),
(16, '9/35 Soi Phaholyothin 44', '2024-11-01 16:08:53.000000', 'shenlijun@138.cn', 'Shen', NULL, '$2a$12$N/BwccPmZNGc8rkRv8SvVuos72e69mw65LiEH.5g7B0gEBegoLbLq', 'li jun', '0123456789');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `car`
--
ALTER TABLE `car`
  ADD PRIMARY KEY (`car_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `car`
--
ALTER TABLE `car`
  MODIFY `car_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
