-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 15, 2010 at 06:03 PM
-- Server version: 5.1.37
-- PHP Version: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `gamersclub`
--

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

CREATE TABLE IF NOT EXISTS `games` (
  `counter` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `desc` longtext,
  `type` varchar(100) DEFAULT NULL,
  `picture` varchar(100) DEFAULT NULL,
  `dir` varchar(100) DEFAULT NULL,
  `download` tinytext,
  `addDate` bigint(50) DEFAULT NULL,
  `createDate` bigint(50) DEFAULT NULL,
  `addBy` int(2) DEFAULT NULL,
  UNIQUE KEY `counter` (`counter`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `games`
--

INSERT INTO `games` (`counter`, `name`, `desc`, `type`, `picture`, `dir`, `download`, `addDate`, `createDate`, `addBy`) VALUES
(1, 'Halo', 'Halo is an awesome FPS game, albet with bad graphics. Its pretty awesome. More rant and text. STuff. Things. Stuff that does things\r\n\r\n-heheheheh', 'FPS', 'halo.jpg', NULL, 'halo.exe', 0, 0, 0),
(2, 'Warzone 2100', 'Warzone 2100 is a real-time strategy and real-time tactics hybrid computer game, developed by Pumpkin Studios and published by Eidos Interactive. It was originally released in 1999 for Microsoft Windows and PlayStation. On December 6, 2004 the source code and most of its data was released under the GNU General Public License, thereby making it an open source game. On June 10, 2008 the license of the game was clarified, loosened and distribution of films and soundtrack was permitted.', 'RTS', 'Warzone2100Portable.jpg', 'C:\\Documents and Settings\\Lord.Quackstar\\Desktop\\Warzone2100Portable', NULL, 1262902124391, 1262832789, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(40) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `name` varchar(20) NOT NULL,
  `counter` tinyint(3) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`counter`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `admin`, `name`, `counter`) VALUES
('LTBlakey1', 1, 'Leon Test Blakey1', 1),
('LTBlakey2', 0, 'Leon Test Blakey2', 2),
('Susan', 1, 'Susan Blakey', 3),
('Owner', 1, 'Susan Blakey', 4),
('Lord.Quackstar', 1, 'Leon Blakey', 6);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
