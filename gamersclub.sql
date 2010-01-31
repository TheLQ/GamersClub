-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 31, 2010 at 01:30 PM
-- Server version: 5.1.41
-- PHP Version: 5.3.1

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
-- Table structure for table `dirlist`
--

CREATE TABLE IF NOT EXISTS `dirlist` (
  `counter` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `folder` varchar(100) DEFAULT NULL,
  `gameID` int(2) DEFAULT NULL,
  PRIMARY KEY (`counter`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `dirlist`
--

INSERT INTO `dirlist` (`counter`, `name`, `folder`, `gameID`) VALUES
(1, 'Portabe Penis', '625fdc603f554c2e93053a869984f873', 1);

-- --------------------------------------------------------

--
-- Table structure for table `filelist`
--

CREATE TABLE IF NOT EXISTS `filelist` (
  `Counter` int(100) NOT NULL AUTO_INCREMENT,
  `Source` varchar(255) DEFAULT NULL,
  `Dest` varchar(255) DEFAULT NULL,
  `type` varchar(5) DEFAULT NULL,
  `folderID` int(3) NOT NULL,
  KEY `Counter` (`Counter`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=126 ;

--
-- Dumping data for table `filelist`
--

INSERT INTO `filelist` (`Counter`, `Source`, `Dest`, `type`, `folderID`) VALUES
(1, 'Data\\settings\\savegame\\Leon\\feat.bjo', 'ec1c509ddb49401b8fd02330fa6afc52', 'FILE', 1),
(2, 'Data\\settings\\savegame\\ARCHIEEE\\visstate.bjo', 'f598a970b3e64d7eb2abb7d503aca16e', 'FILE', 1),
(3, 'Data\\settings\\savegame\\Leon\\proxstate.bjo', '710f744e70ee4b9385db2c205c34142e', 'FILE', 1),
(4, 'Data\\settings\\savegame\\ARCHIEEE\\struct.bjo', 'ad8f6977544442739b45a93c394b8f0a', 'FILE', 1),
(5, 'App\\Warzone 2100\\fonts\\fonts.conf', 'c0b1a3f669a54ddbbb5f73e49c51dbe1', 'FILE', 1),
(6, 'Data\\settings\\savegame\\Leon\\game.map', '5b48fe1aecea4bf785cf704be08516d5', 'FILE', 1),
(7, 'Data\\settings\\savegame\\ARCHIEEE\\templ.bjo', '003af4b6429b46a69f3a4c8dac508a53', 'FILE', 1),
(8, 'Other\\Source\\ReadINIStrWithDefault.nsh', '1d9eb2dc8b154ad38600ccb9ab04e36b', 'FILE', 1),
(9, 'Other\\Source\\GetParametersUnicode.nsh', 'e4dfa8e71faf4d608c92a27a86a0eff9', 'FILE', 1),
(10, 'Data\\settings\\savegame\\Leon\\resstate.bjo', '08c8134bd16f4505b2812a2c0de68971', 'FILE', 1),
(11, 'App\\Warzone 2100\\fonts\\DejaVuSansMono-Bold.ttf', 'fb48ea2088f342d78647fae71c9883f4', 'FILE', 1),
(12, 'Data\\settings\\savegame\\ARCHIEEE\\ttypes.ttp', '304d0c9799dc4379a7bffb68b3a90dd8', 'FILE', 1),
(13, 'Other\\Source\\PortableApps.comInstaller.bmp', 'd329c23cd82c43699330b60bac1efb83', 'FILE', 1),
(14, 'Other\\Help\\images\\help_logo_top.png', 'af51b7d7c8b441aaa21118509783b725', 'FILE', 1),
(15, 'App\\DefaultData\\settings\\warzone2100\\netplay.log', '95a6f68a2c5a43c6a04aebdcb1a65d27', 'FILE', 1),
(16, 'App\\Warzone 2100\\locale\\tr\\LC_MESSAGES\\warzone2100.mo', 'f4671f1e44094ceb886d1ae1460456b8', 'FILE', 1),
(17, 'Data\\settings\\savegame\\Leon\\score.tag', 'a9dbd20994aa40aea7a1dc509cdb6c03', 'FILE', 1),
(18, 'App\\Warzone 2100\\locale\\de\\LC_MESSAGES\\warzone2100.mo', '09e781a92e3049849001aeabef05341e', 'FILE', 1),
(19, 'Other\\Source\\Readme.txt', 'ccb94771981d4ab98057393a5cd8153c', 'FILE', 1),
(20, 'App\\AppInfo\\Thumbs.db', 'b13fa635c8d74de8ac824b45909c3f1a', 'FILE', 1),
(21, 'Data\\settings\\savegame\\Leon\\limits.bjo', 'ec05787ffbe24a74998d8fe1e16ca580', 'FILE', 1),
(22, 'App\\Warzone 2100\\locale\\ru\\LC_MESSAGES\\warzone2100.mo', '6b7ea6e254fb48f29e00507905e31490', 'FILE', 1),
(23, 'App\\Warzone 2100\\keymap.map', '6ea0d47ad7414955b8c7f418c460b872', 'FILE', 1),
(24, 'App\\Warzone 2100\\locale\\ga\\LC_MESSAGES\\warzone2100.mo', '7aa6c0a671bf4a5ebb9610ce428089a0', 'FILE', 1),
(25, 'Other\\Help\\images\\help_background_footer.png', '5080033f48274e66a152804b24e6c101', 'FILE', 1),
(26, 'help.html', 'b8bef080b2b44dd8b1b8db707559bc37', 'FILE', 1),
(27, 'App\\Warzone 2100\\netplay.log', 'f3fe0342f3ce45c589e91b05138051f0', 'FILE', 1),
(28, 'Other\\Source\\PortableApps.comInstallerConfig.nsh', '2f831bfd28b04772b27d14acb475bd01', 'FILE', 1),
(29, 'Data\\settings\\savegame\\ARCHIEEE\\messtate.bjo', 'a41ccd4bebf244db88e7738096f89d0c', 'FILE', 1),
(30, 'App\\Warzone 2100\\locale\\en_GB\\LC_MESSAGES\\warzone2100.mo', 'defa1895cfb14ab393c0c0b51de928f6', 'FILE', 1),
(31, 'App\\Warzone 2100\\dbghelp.dll', '7151ba375c6f49da9b0318bf79fc2754', 'FILE', 1),
(32, 'Other\\Source\\Warzone2100Portable.ini', '83c0557837d644ee84bb3bb06c0fd448', 'FILE', 1),
(33, 'App\\Warzone 2100\\locale\\nl\\LC_MESSAGES\\warzone2100.mo', '2683145b870145afb325e611bb7043bb', 'FILE', 1),
(34, 'App\\Warzone 2100\\locale\\nb\\LC_MESSAGES\\warzone2100.mo', '52b4ba77b6ae4bf1853e37d7f39ed3d2', 'FILE', 1),
(35, 'Data\\settings\\savegame\\ARCHIEEE\\prodstate.bjo', '7ca0e3efac624e19a31a63c34ac77027', 'FILE', 1),
(36, 'App\\Warzone 2100\\License.txt', '45fa8fec1a8648528fafd1d7c4de5e2c', 'FILE', 1),
(37, 'Data\\settings\\savegame\\Leon\\templ.bjo', '6aa1566396e643698275bab8cfec29f8', 'FILE', 1),
(38, 'Other\\Source\\Warzone2100Portable.nsi', '51e6df4c46264f5dac75adb94e6e1772', 'FILE', 1),
(39, 'Data\\settings\\savegame\\ARCHIEEE.gam', '6a7653d8e4f044dd8722cf5182d93188', 'FILE', 1),
(40, 'App\\Warzone 2100\\stderr.txt', 'e40afcbbab0f40f99e894e019aebdb9f', 'FILE', 1),
(41, 'App\\Warzone 2100\\locale\\fr\\LC_MESSAGES\\warzone2100.mo', '56f1a03acb6649a19b83a9ee0e4efa00', 'FILE', 1),
(42, 'App\\Warzone 2100\\warzone.wz', '2afcd03d1f014a4e9716ed61f86501ee', 'FILE', 1),
(43, 'App\\Warzone 2100\\locale\\es\\LC_MESSAGES\\warzone2100.mo', '612f6cfcc1f54d98a37f21eb91a6a557', 'FILE', 1),
(44, 'Data\\settings\\savegame\\Leon\\ttypes.ttp', 'b2038a5fab794815981565f0456d3b1c', 'FILE', 1),
(45, 'Data\\settings\\savegame\\ARCHIEEE\\limbo.bjo', '12db5bd23ff84535b62c46d39068d191', 'FILE', 1),
(46, 'Data\\settings\\savegame\\Leon\\struct.bjo', '10258bb002fa4d50b641f2f06af70166', 'FILE', 1),
(47, 'App\\Warzone 2100\\mp.wz', 'c3ecfd2859da4612bb7a971ceca0a2cf', 'FILE', 1),
(48, 'App\\Warzone 2100\\locale\\pt\\LC_MESSAGES\\warzone2100.mo', '58db07d545524f5ca8a94bab1940fbbd', 'FILE', 1),
(49, 'App\\Warzone 2100\\locale\\da\\LC_MESSAGES\\warzone2100.mo', '3fa793d2e0824cc38b471c6f3c7ccb8c', 'FILE', 1),
(50, 'Data\\settings\\savegame\\Leon\\firesupport.tag', '4dd6acdc709f4ae395d31dfb7488ecff', 'FILE', 1),
(51, 'Data\\settings\\savegame\\ARCHIEEE\\unit.bjo', '72f433dbfa964e4fb5da9c2fa476a26d', 'FILE', 1),
(52, 'Other\\Help\\images\\donation_button.png', '4c402cc8f7dc4e3394489fdf72b42f63', 'FILE', 1),
(53, 'App\\Warzone 2100\\Readme.en.txt', 'c46e61abd7444cddb49aeca040840d47', 'FILE', 1),
(54, 'App\\Warzone 2100\\locale\\lt\\LC_MESSAGES\\warzone2100.mo', 'af714a54274d473ba80cf182110c1970', 'FILE', 1),
(55, 'Data\\settings\\savegame\\Leon.gam', 'a63fddf4992a4af88f78e609467302a5', 'FILE', 1),
(56, 'App\\Warzone 2100\\config', 'f580086170d4464fae5d592f64567cec', 'FILE', 1),
(57, 'App\\AppInfo\\appinfo.ini', '1d8753797dad447d9874756b4c06083e', 'FILE', 1),
(58, 'Data\\settings\\savegame\\ARCHIEEE\\munit.bjo', '7f72128453364eed80bf14bf75a6b750', 'FILE', 1),
(59, 'Data\\settings\\savegame\\ARCHIEEE\\resstate.bjo', '70b0a9c8bccc45c79aa22e58fdf7461e', 'FILE', 1),
(60, 'Data\\settings\\savegame\\ARCHIEEE\\firesupport.tag', '18454c1dface440585f73437a804f6d8', 'FILE', 1),
(61, 'Warzone2100Portable.jpg', '54c5037710e04a4fb422fbe24a220681', 'FILE', 1),
(62, 'App\\Warzone 2100\\styles\\readme.screen.css', '06d06c4be53f4fbaa6adb52d21468d9a', 'FILE', 1),
(63, 'App\\Warzone 2100\\locale\\la\\LC_MESSAGES\\warzone2100.mo', '5481bc99bf664a62971fdc156d802d14', 'FILE', 1),
(64, 'Data\\settings\\savegame\\Leon\\prodstate.bjo', '785c9305daf8439287dd3db089ba7ca1', 'FILE', 1),
(65, 'App\\Warzone 2100\\locale\\fi\\LC_MESSAGES\\warzone2100.mo', '7556dfc635c54f99a9de211fe47e8113', 'FILE', 1),
(66, 'App\\Warzone 2100\\Readme.de.html', '57dd72f346e44cbfab0f1c73d99c5c7f', 'FILE', 1),
(67, 'Data\\settings\\savegame\\ARCHIEEE\\strtype.bjo', '6272c804e17b491eb0577c2b1a178352', 'FILE', 1),
(68, 'App\\Warzone 2100\\styles\\readme.print.css', 'cc4ac502144f4e8fb1b0ce6f66b044a9', 'FILE', 1),
(69, 'Data\\settings\\savegame\\Leon\\visstate.bjo', 'eb6ac1c6ea7f49a2b5e25d9052d738dc', 'FILE', 1),
(70, 'Other\\Source\\PortableApps.comInstallerLANG_ENGLISH.nsh', '670a28afb22f48fab5a678ead9313e54', 'FILE', 1),
(71, 'App\\Warzone 2100\\locale\\sl\\LC_MESSAGES\\warzone2100.mo', 'dd793c288fa44cc99b7bc36c3a32b38f', 'FILE', 1),
(72, 'Data\\settings\\savegame\\Leon\\strtype.bjo', '8c3a6f4270c6405f948ed5598bb7b5b8', 'FILE', 1),
(73, 'App\\Warzone 2100\\locale\\pt_BR\\LC_MESSAGES\\warzone2100.mo', '451246d0168d4efab1da64b1267d6cbe', 'FILE', 1),
(74, 'App\\Warzone 2100\\OpenAL32.dll', '26b0183841c548ca92c13fb37768d0a5', 'FILE', 1),
(75, 'Data\\settings\\savegame\\Leon\\unit.bjo', '19c49727d3c041f28e402241e2ff7d40', 'FILE', 1),
(76, 'App\\Warzone 2100\\locale\\it\\LC_MESSAGES\\warzone2100.mo', 'de1b75eec4a0457d9d506291a1354e69', 'FILE', 1),
(77, 'App\\Warzone 2100\\Authors.txt', 'e9de09581c0b4edb93dcf19ddddd29b5', 'FILE', 1),
(78, 'Data\\settings\\savegame\\Leon\\limbo.bjo', '829acef6e185455fbe95cca2f4c307ac', 'FILE', 1),
(79, 'App\\Warzone 2100\\locale\\nn\\LC_MESSAGES\\warzone2100.mo', 'c960be9d392040ef99aae43355d71a91', 'FILE', 1),
(80, 'App\\Warzone 2100\\fonts\\DejaVuSansMono.ttf', 'f71c8fdb332543fbab1fc852ff7c9287', 'FILE', 1),
(81, 'Other\\Source\\PortableApps.comLauncherLANG_ENGLISH.nsh', '26a7749f0d2c4f14af321c74cac1b61d', 'FILE', 1),
(82, 'Data\\settings\\savegame\\Leon.es', 'dca9d1236c3d4d699fbce544b6ae5375', 'FILE', 1),
(83, 'App\\Warzone 2100\\Readme.en.html', '3215132e071641a598aeea823198b5b9', 'FILE', 1),
(84, 'App\\Warzone 2100\\locale\\sv\\LC_MESSAGES\\warzone2100.mo', 'e636e61c538b4165bb4dab557fc7a03a', 'FILE', 1),
(85, 'App\\AppInfo\\appicon.ico', 'ec913c473bff43d382171b6c6b5d766d', 'FILE', 1),
(86, 'App\\Warzone 2100\\locale\\eu\\LC_MESSAGES\\warzone2100.mo', 'c4c8abb3130943b4812f7ed18115167b', 'FILE', 1),
(87, 'App\\Warzone 2100\\locale\\sv_SE\\LC_MESSAGES\\warzone2100.mo', 'df7c707743064c4f9a106e7aa015dd98', 'FILE', 1),
(88, 'Data\\settings\\savegame\\ARCHIEEE\\flagstate.bjo', '0709579ffd22445a8fb1e2ce80a58d42', 'FILE', 1),
(89, 'App\\Warzone 2100\\locale\\cs\\LC_MESSAGES\\warzone2100.mo', 'ad056fecc2914b2ba942e7a0c70eddb2', 'FILE', 1),
(90, 'App\\Warzone 2100\\wrap_oal.dll', '74a72a2178a846fc8d2fae62765cd058', 'FILE', 1),
(91, 'Data\\settings\\savegame\\Leon\\munit.bjo', '6d355f226ef54f399c21e436c883d545', 'FILE', 1),
(92, 'App\\Warzone 2100\\debug.txt', 'ec1ef3c6b2634cf6baf9faf125b0dcf3', 'FILE', 1),
(93, 'Other\\Source\\PortableApps.comInstaller.nsi', '9bd36659a06849f3a6fae5b735c276a6', 'FILE', 1),
(94, 'Data\\settings\\savegame\\ARCHIEEE\\compl.bjo', 'e0a5c69c395c4caa8be57905b6569b3a', 'FILE', 1),
(95, 'Warzone2100Portable_orig.exe', '04c6ff704b874daabc5553726df308bc', 'FILE', 1),
(96, 'App\\Warzone 2100\\locale\\fy\\LC_MESSAGES\\warzone2100.mo', '8736775050b445c190b2a0e41ed21dfa', 'FILE', 1),
(97, 'Data\\settings\\savegame\\ARCHIEEE\\feat.bjo', '88cb29f7bafe452bb63479bc3b27340f', 'FILE', 1),
(98, 'App\\DefaultData\\settings\\warzone2100\\config', '8c6f2b87d74d46e2b1e425cde5284b34', 'FILE', 1),
(99, 'App\\Warzone 2100\\locale\\pl\\LC_MESSAGES\\warzone2100.mo', '7594c12675ff46188e1bf535b946851b', 'FILE', 1),
(100, 'Data\\settings\\savegame\\ARCHIEEE\\limits.bjo', 'd5f32fb80f9c4dcabcf257da56c5e7c9', 'FILE', 1),
(101, 'Other\\Help\\images\\favicon.ico', '7fbee28582fe4755be7c0b611ff3c55b', 'FILE', 1),
(102, 'App\\DefaultData\\settings\\mods\\global\\grim.wz', 'c4816f67601149c59b3cb24853e20da2', 'FILE', 1),
(103, 'App\\Warzone 2100\\locale\\lv\\LC_MESSAGES\\warzone2100.mo', '075b003e84234655a3b00d56c594d5ff', 'FILE', 1),
(104, 'Data\\settings\\savegame\\ARCHIEEE\\game.map', 'a89fce50759f4314baa90e3ac6d04ab7', 'FILE', 1),
(105, 'Data\\settings\\savegame\\Leon\\messtate.bjo', '4b718d8b125a420f84f1c67a87f51616', 'FILE', 1),
(106, 'App\\Warzone 2100\\warzone2100.exe', 'e9234e5d11cd41febe4229f3b3027ed2', 'FILE', 1),
(107, 'App\\Warzone 2100\\locale\\zh_CN\\LC_MESSAGES\\warzone2100.mo', 'e53090cc3d1f424c849c9c4a8acb0a14', 'FILE', 1),
(108, 'Data\\settings\\savegame\\ARCHIEEE\\score.tag', 'f61f3af676a14bde8db65b015cc30498', 'FILE', 1),
(109, 'Data\\settings\\keymap.map', '024afd06ca6b4b7e943fac04e1be6415', 'FILE', 1),
(110, 'Data\\settings\\savegame\\Leon\\fxstate.tag', 'e9cae5e93da845c599700e4fd5e7a9f9', 'FILE', 1),
(111, 'App\\DefaultData\\settings\\Warzone2100Portable.ini', 'ecf472fce1974647be9e14ea538e9a33', 'FILE', 1),
(112, 'Data\\settings\\netplay.log', 'e63b7e3636c84014b4ff07c8fc5b4a90', 'FILE', 1),
(113, 'Data\\settings\\config', 'adcb0f2c212f413cbba70de0a15b2eda', 'FILE', 1),
(114, 'Data\\settings\\savegame\\ARCHIEEE\\proxstate.bjo', '152fa7c60d4a43cfb8edda54e96ee895', 'FILE', 1),
(115, 'App\\DefaultData\\settings\\warzone2100\\keymap.map', 'd7dea7b77c304a4a8e5c97b54eac37a3', 'FILE', 1),
(116, 'App\\Warzone 2100\\ChangeLog.txt', 'b4f6a96e5dc14bb3b73cb6128b24025e', 'FILE', 1),
(117, 'Data\\settings\\savegame\\ARCHIEEE\\fxstate.tag', '805ca95348d94336a5b7c85eccc01ddb', 'FILE', 1),
(118, 'Data\\settings\\savegame\\Leon\\flagstate.bjo', '9d9443a18bd14a79b2f6c6f5004504ff', 'FILE', 1),
(119, 'App\\Warzone 2100\\dbghelp.dll.license.txt', 'fca7957542bb44b081a95de64bc59ddf', 'FILE', 1),
(120, 'Other\\Help\\images\\help_background_header.png', '6e1351f5f19d4f4e92dbfac8574018a0', 'FILE', 1),
(121, 'App\\Warzone 2100\\locale\\uz\\LC_MESSAGES\\warzone2100.mo', '6f5fed8463a745f7a47e4f7d9c842f17', 'FILE', 1),
(122, 'Other\\Source\\Warzone2100Portable.jpg', '019d334a77f4483aaf54e3946645f5d7', 'FILE', 1),
(123, 'Data\\settings\\savegame\\Leon\\compl.bjo', '3956e60f3dd847098d0f1d61689a473d', 'FILE', 1),
(124, 'App\\Warzone 2100\\locale\\ro\\LC_MESSAGES\\warzone2100.mo', '4b10ec6ad7674d6cb9063e796935f141', 'FILE', 1),
(125, 'App\\Warzone 2100\\Readme.de.txt', '5f00cf9a5ba94cf8ac3cf95b4c12c2b0', 'FILE', 1);

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

CREATE TABLE IF NOT EXISTS `games` (
  `counter` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `desc` longtext,
  `type` varchar(20) DEFAULT NULL,
  `picture` varchar(100) DEFAULT NULL,
  `addDate` varchar(40) DEFAULT NULL,
  `createDate` varchar(40) DEFAULT NULL,
  `addBy` int(2) DEFAULT NULL,
  UNIQUE KEY `counter` (`counter`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `games`
--

INSERT INTO `games` (`counter`, `name`, `desc`, `type`, `picture`, `addDate`, `createDate`, `addBy`) VALUES
(1, 'Warzone test 1', 'Muhahahhah', 'RTS', '625fdc603f554c2e93053a869984f873\\b3beabd057104b259bd53adec4193244', '1264962100753', '-23889686400000', 6);

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
