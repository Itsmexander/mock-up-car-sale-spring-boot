-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 30, 2024 at 11:25 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

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
  `notation` varchar(255) NOT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `manufactured_year` int(11) DEFAULT NULL,
  `last_modified_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `creation_timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `car`
--

INSERT INTO `car` (`car_id`, `name`, `price`, `notation`, `manufacturer`, `manufactured_year`, `last_modified_timestamp`, `creation_timestamp`) VALUES
(1, 'test1', 1234.56, '', 'test1', 0, '2024-10-30 09:24:22', '2024-10-30 09:24:22'),
(2, 'test4', 2345.6, 'test4', 'test1', 0, '2024-10-30 09:43:22', '2024-10-30 09:25:25'),
(3, 'test1', 1234.56, '', 'test1', 0, '2024-10-30 09:25:54', '2024-10-30 09:25:54'),
(4, 'test1', 1234.56, 'asdf', 'test1', 0, '2024-10-30 09:38:22', '2024-10-30 09:38:22'),
(5, 'test4', 1234.56, '', 'test1', 0, '2024-10-30 09:40:46', '2024-10-30 09:40:46');

-- --------------------------------------------------------

--
-- Table structure for table `car_seq`
--

CREATE TABLE `car_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `car_seq`
--

INSERT INTO `car_seq` (`next_val`) VALUES
(101);

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
(11, '9/35 phaholyothin 44', '2024-10-30 10:14:04.000000', 'test1@test.com', 'Chawish', '2024-10-30 10:30:42.000000', '$2a$12$i/sRYFCzPygX17N3itUY5OP/3ZTYvjT6Xljaqs1DfgqKX4WCVXjeO', 'Pipopsophonchai', '0614953963');

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
  MODIFY `car_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
