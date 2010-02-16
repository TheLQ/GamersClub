-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 16, 2010 at 12:27 PM
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

DROP TABLE IF EXISTS `dirlist`;
CREATE TABLE IF NOT EXISTS `dirlist` (
  `counter` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `folder` varchar(100) DEFAULT NULL,
  `gameID` int(2) DEFAULT NULL,
  `byteSize` bigint(40) NOT NULL,
  PRIMARY KEY (`counter`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `dirlist`
--

INSERT INTO `dirlist` (`counter`, `name`, `folder`, `gameID`, `byteSize`) VALUES
(1, 'Download me!!!!', 'c9b001157d624c4a96ca8bd8bd383b90', 1, 38999090);

-- --------------------------------------------------------

--
-- Table structure for table `filelist`
--

DROP TABLE IF EXISTS `filelist`;
CREATE TABLE IF NOT EXISTS `filelist` (
  `Counter` int(100) NOT NULL AUTO_INCREMENT,
  `Source` varchar(255) DEFAULT NULL,
  `Dest` varchar(255) DEFAULT NULL,
  `type` varchar(5) DEFAULT NULL,
  `folderID` int(3) NOT NULL,
  KEY `Counter` (`Counter`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=268 ;

--
-- Dumping data for table `filelist`
--

INSERT INTO `filelist` (`Counter`, `Source`, `Dest`, `type`, `folderID`) VALUES
(1, 'Data\\settings\\savegame\\Leon\\feat.bjo', '381a223d2ef34cc5b4da2bc4643ca84d', 'FILE', 1),
(2, 'Data\\settings\\savegame\\ARCHIEEE\\visstate.bjo', 'b11f9e5cb8f84bf7b04448859b57053e', 'FILE', 1),
(3, 'Data\\settings\\savegame\\Leon\\proxstate.bjo', '5a4fac9abfef4c0288f40ad173dc7b57', 'FILE', 1),
(4, 'Data\\settings\\savegame\\ARCHIEEE\\struct.bjo', '4ab6477fafb74b05b2855908045bb11f', 'FILE', 1),
(5, 'App\\Warzone 2100\\fonts\\fonts.conf', 'e3c0c657d8ed4e5eb6ced33c7f54fbae', 'FILE', 1),
(6, 'Data\\settings\\savegame\\Leon\\game.map', '8e9a0e53bb704abd88087f6bc55762ce', 'FILE', 1),
(7, 'Data\\settings\\savegame\\ARCHIEEE\\templ.bjo', '798e089e70e94821ac4f9e3e29028935', 'FILE', 1),
(8, 'Other\\Source\\ReadINIStrWithDefault.nsh', '96d0f5e301d549b28f6fd7f2e543b476', 'FILE', 1),
(9, 'Other\\Source\\GetParametersUnicode.nsh', 'd28903e6325e4e8c9653f7fbe4eb3ba4', 'FILE', 1),
(10, 'Data\\settings\\savegame\\Leon\\resstate.bjo', 'ba29b2e6bf704358b604a93ae47cd395', 'FILE', 1),
(11, 'App\\Warzone 2100\\fonts\\DejaVuSansMono-Bold.ttf', '3baf5053f9fd4025aefd5dc387e9984f', 'FILE', 1),
(12, 'Data\\settings\\savegame\\ARCHIEEE\\ttypes.ttp', '3f84e04be4f949e9bba27a481baec89a', 'FILE', 1),
(13, 'Other\\Source\\PortableApps.comInstaller.bmp', '847029ef71854cabbba48712118136a9', 'FILE', 1),
(14, 'Other\\Help\\images\\help_logo_top.png', '5878a797d25f4301898c49b9d0fb620b', 'FILE', 1),
(15, 'App\\DefaultData\\settings\\warzone2100\\netplay.log', 'f97388c14f864803bf6837ea29fd04b9', 'FILE', 1),
(16, 'App\\Warzone 2100\\locale\\tr\\LC_MESSAGES\\warzone2100.mo', 'c152dfbc500a4ec7b3dcab057d8d655b', 'FILE', 1),
(17, 'Data\\settings\\savegame\\Leon\\score.tag', '20ea6c95c66f44d28b3e0817e17695cf', 'FILE', 1),
(18, 'App\\Warzone 2100\\locale\\de\\LC_MESSAGES\\warzone2100.mo', '5aeaccf3153a4d5ca892b2e1a39ee3b5', 'FILE', 1),
(19, 'Other\\Source\\Readme.txt', '4f7c5cface4e4bcb88402121d689edb8', 'FILE', 1),
(20, 'App\\AppInfo\\Thumbs.db', '79bd54027a95476289576f81b8396210', 'FILE', 1),
(21, 'Data\\settings\\savegame\\Leon\\limits.bjo', 'e0dc2503546d4db5b856569739d14e7c', 'FILE', 1),
(22, 'App\\Warzone 2100\\locale\\ru\\LC_MESSAGES\\warzone2100.mo', '4f0eb37b0ad7493194c2affd8371c36d', 'FILE', 1),
(23, 'App\\Warzone 2100\\keymap.map', '5379a3d606694361b5c3fd70e2b1b49b', 'FILE', 1),
(24, 'App\\Warzone 2100\\locale\\ga\\LC_MESSAGES\\warzone2100.mo', 'f724b15078184866a0590f1faafbe34a', 'FILE', 1),
(25, 'Other\\Help\\images\\help_background_footer.png', '7eb0461c78654df5b28a3a8e3b8baa0d', 'FILE', 1),
(26, 'help.html', '06ff439b6cdf4b9383def22c102fed6b', 'FILE', 1),
(27, 'App\\Warzone 2100\\netplay.log', '01ee48f851484b6ab4acef75768ceaf1', 'FILE', 1),
(28, 'Other\\Source\\PortableApps.comInstallerConfig.nsh', 'f8287298dcfe487184b77a71c462b997', 'FILE', 1),
(29, 'Data\\settings\\savegame\\ARCHIEEE\\messtate.bjo', 'e05f5e9847c5448ca9253bad3b733165', 'FILE', 1),
(30, 'App\\Warzone 2100\\locale\\en_GB\\LC_MESSAGES\\warzone2100.mo', '78eedd0190af424cb1094eb9cb48f08f', 'FILE', 1),
(31, 'App\\Warzone 2100\\dbghelp.dll', 'a18db6905c8847bba282fee7cdea1bbf', 'FILE', 1),
(32, 'Other\\Source\\Warzone2100Portable.ini', '3a678ed89d404a8eaae3b187c0187fb3', 'FILE', 1),
(33, 'App\\Warzone 2100\\locale\\nl\\LC_MESSAGES\\warzone2100.mo', 'bd6fb09a708247a1a726c534b677fa9c', 'FILE', 1),
(34, 'App\\Warzone 2100\\locale\\nb\\LC_MESSAGES\\warzone2100.mo', '4809dd23bc044d2d989b52504d363df3', 'FILE', 1),
(35, 'Data\\settings\\savegame\\ARCHIEEE\\prodstate.bjo', '2f40efc7ede24d7eabd4f028d2149970', 'FILE', 1),
(36, 'App\\Warzone 2100\\License.txt', 'bebc906098ec49f1b782733918cadbf2', 'FILE', 1),
(37, 'Data\\settings\\savegame\\Leon\\templ.bjo', '4f2e1241e3a745a3ad18866ae905d6fb', 'FILE', 1),
(38, 'Other\\Source\\Warzone2100Portable.nsi', '3e32f2ddb36349228e7001c089bcbb30', 'FILE', 1),
(39, 'Data\\settings\\savegame\\ARCHIEEE.gam', '6653135069b8471897ea6332a8d8c3ec', 'FILE', 1),
(40, 'App\\Warzone 2100\\stderr.txt', 'a0e6940ba1a4415b9d47af183e9f4bdc', 'FILE', 1),
(41, 'App\\Warzone 2100\\locale\\fr\\LC_MESSAGES\\warzone2100.mo', '23ba53e181614d67805ed45d624d2f4c', 'FILE', 1),
(42, 'App\\Warzone 2100\\warzone.wz', '9bf22c4853b64e3bbff73e991fa849e5', 'FILE', 1),
(43, 'App\\Warzone 2100\\locale\\es\\LC_MESSAGES\\warzone2100.mo', '64b13ede382a4a3fba13ebe958df81a7', 'FILE', 1),
(44, 'Data\\settings\\savegame\\Leon\\ttypes.ttp', '753dc2f4b2934a839f308fd6a836842f', 'FILE', 1),
(45, 'Data\\settings\\savegame\\ARCHIEEE\\limbo.bjo', 'd09d8c8e63ef49baaa3fd15c3eb087da', 'FILE', 1),
(46, 'Data\\settings\\savegame\\Leon\\struct.bjo', 'b2cf1de41d0847d6906f858fa40c063a', 'FILE', 1),
(47, 'App\\Warzone 2100\\mp.wz', '75365a2dd55442b5830b232533b4acf4', 'FILE', 1),
(48, 'App\\Warzone 2100\\locale\\pt\\LC_MESSAGES\\warzone2100.mo', '35b879534c0a4dbdb2cefa97159e56ec', 'FILE', 1),
(49, 'App\\Warzone 2100\\locale\\da\\LC_MESSAGES\\warzone2100.mo', 'bf7aa7c5fee54d0680855154092ab6e4', 'FILE', 1),
(50, 'Data\\settings\\savegame\\Leon\\firesupport.tag', 'c5a41df3b0c94ebc9db2dc579c0658b8', 'FILE', 1),
(51, 'Data\\settings\\savegame\\ARCHIEEE\\unit.bjo', 'd7131ec8725845e78fbb5b151697f4b8', 'FILE', 1),
(52, 'Other\\Help\\images\\donation_button.png', '693b5508575246d7b477680e9d858e4f', 'FILE', 1),
(53, 'App\\Warzone 2100\\Readme.en.txt', 'e152dc93bf044253860d0c40428ccee4', 'FILE', 1),
(54, 'App\\Warzone 2100\\locale\\lt\\LC_MESSAGES\\warzone2100.mo', '921a7b14cf5c4ae698d6dafd62aafe7e', 'FILE', 1),
(55, 'Data\\settings\\savegame\\Leon.gam', '5908649d35e3467fb1a6b407e337fd57', 'FILE', 1),
(56, 'App\\Warzone 2100\\config', '321311bd406c47388c67a6148b97636c', 'FILE', 1),
(57, 'App\\AppInfo\\appinfo.ini', '05e57f9b64384189a756e3159168af3b', 'FILE', 1),
(58, 'Data\\settings\\savegame\\ARCHIEEE\\munit.bjo', '3caf2a046e3f44758c50c6fcd58df403', 'FILE', 1),
(59, 'Data\\settings\\savegame\\ARCHIEEE\\resstate.bjo', 'db7ba41d3cfa4dd0a7a790b9ee69b5f7', 'FILE', 1),
(60, 'Data\\settings\\savegame\\ARCHIEEE\\firesupport.tag', 'd495b3e9111e43ca847c103e23ca31b4', 'FILE', 1),
(61, 'Warzone2100Portable.jpg', 'a8fad6656b904470bccaf60d93038fa5', 'FILE', 1),
(62, 'App\\Warzone 2100\\styles\\readme.screen.css', '9ad1dd231fa64b80bf03b8aa28e81aaa', 'FILE', 1),
(63, 'App\\Warzone 2100\\locale\\la\\LC_MESSAGES\\warzone2100.mo', 'ed99328bed984c878afd44cbf73eb41b', 'FILE', 1),
(64, 'Data\\settings\\savegame\\Leon\\prodstate.bjo', '96493405d2a24965b7f6b90d83ce8877', 'FILE', 1),
(65, 'App\\Warzone 2100\\locale\\fi\\LC_MESSAGES\\warzone2100.mo', 'f89a4c07b5d7443b928dc4fbae2c0cbf', 'FILE', 1),
(66, 'App\\Warzone 2100\\Readme.de.html', '45071ba55fee43f583a5f2561ccfd349', 'FILE', 1),
(67, 'Data\\settings\\savegame\\ARCHIEEE\\strtype.bjo', 'be734f9d212b43c6937128cc8d1eeb4f', 'FILE', 1),
(68, 'App\\Warzone 2100\\styles\\readme.print.css', '4db3608ddf134d77bfc02ceb647bbeef', 'FILE', 1),
(69, 'Data\\settings\\savegame\\Leon\\visstate.bjo', 'a49ff7a9f86e4ee091298399a9b68981', 'FILE', 1),
(70, 'Other\\Source\\PortableApps.comInstallerLANG_ENGLISH.nsh', 'c03b95d5633d4c83bba5e9418dee3cf9', 'FILE', 1),
(71, 'App\\Warzone 2100\\locale\\sl\\LC_MESSAGES\\warzone2100.mo', '039134e08c3d48459f8f7d3b80564bbf', 'FILE', 1),
(72, 'Data\\settings\\savegame\\Leon\\strtype.bjo', '3848dc1cc69a40ac9a90d87a3ceca7bf', 'FILE', 1),
(73, 'App\\Warzone 2100\\locale\\pt_BR\\LC_MESSAGES\\warzone2100.mo', 'd2d40e332e2b45c4a0b3625f3c90d5e7', 'FILE', 1),
(74, 'App\\Warzone 2100\\OpenAL32.dll', '164fe21a2158481492c68eccbd486137', 'FILE', 1),
(75, 'Data\\settings\\savegame\\Leon\\unit.bjo', '84fe4d321bbf4b44bc013023724e0aa2', 'FILE', 1),
(76, 'App\\Warzone 2100\\locale\\it\\LC_MESSAGES\\warzone2100.mo', '91aaf1254c8041b99156772319d3620d', 'FILE', 1),
(77, 'App\\Warzone 2100\\Authors.txt', '5c8776d198574f9a99739c344da76e59', 'FILE', 1),
(78, 'Data\\settings\\savegame\\Leon\\limbo.bjo', 'c06f657bab084523bf5c8e31a2fc7271', 'FILE', 1),
(79, 'App\\Warzone 2100\\locale\\nn\\LC_MESSAGES\\warzone2100.mo', 'e268c0c72a104dafbf1a79359d064c31', 'FILE', 1),
(80, 'App\\Warzone 2100\\fonts\\DejaVuSansMono.ttf', '057972213cb04177982c17e23ae8295a', 'FILE', 1),
(81, 'Other\\Source\\PortableApps.comLauncherLANG_ENGLISH.nsh', '9bcf75c0ca184455b84b91b19241c722', 'FILE', 1),
(82, 'Data\\settings\\savegame\\Leon.es', '999685ebcb914b8c932a360ebc312ce5', 'FILE', 1),
(83, 'App\\Warzone 2100\\Readme.en.html', 'da2c169b2c334d2cb01bee8e986a2a65', 'FILE', 1),
(84, 'App\\Warzone 2100\\locale\\sv\\LC_MESSAGES\\warzone2100.mo', 'ba839948404d478d94234add58a81326', 'FILE', 1),
(85, 'App\\AppInfo\\appicon.ico', 'd1ca4ab73b024166a8f03aeb4cacea53', 'FILE', 1),
(86, 'App\\Warzone 2100\\locale\\eu\\LC_MESSAGES\\warzone2100.mo', '426f643cbe9b4e31ba3aaf9630db01dc', 'FILE', 1),
(87, 'App\\Warzone 2100\\locale\\sv_SE\\LC_MESSAGES\\warzone2100.mo', 'f1af0f695a25493da40ff97dbcdaf813', 'FILE', 1),
(88, 'Data\\settings\\savegame\\ARCHIEEE\\flagstate.bjo', 'c98e4e8ed9ba469ba37c69767e2618e4', 'FILE', 1),
(89, 'App\\Warzone 2100\\locale\\cs\\LC_MESSAGES\\warzone2100.mo', '986afe4f5b5f4ec18374022f3ea43c3d', 'FILE', 1),
(90, 'App\\Warzone 2100\\wrap_oal.dll', '148cd94c3260422c8e529dcb7af529b8', 'FILE', 1),
(91, 'Data\\settings\\savegame\\Leon\\munit.bjo', '21ff79860f384d02852864b87b933e76', 'FILE', 1),
(92, 'App\\Warzone 2100\\debug.txt', 'fb556152049e4c04a541f817c54d902a', 'FILE', 1),
(93, 'Other\\Source\\PortableApps.comInstaller.nsi', 'f42244d125874cdf9283db7630ba9f0d', 'FILE', 1),
(94, 'Data\\settings\\savegame\\ARCHIEEE\\compl.bjo', '67efaf39e6434314ab7294844d9d72ea', 'FILE', 1),
(95, 'Warzone2100Portable_orig.exe', '05784e56ac434bd9adc5f8bf2ee71cd2', 'FILE', 1),
(96, 'App\\Warzone 2100\\locale\\fy\\LC_MESSAGES\\warzone2100.mo', 'aaeaad980b1944a4b39cb4b9f59fe420', 'FILE', 1),
(97, 'Data\\settings\\savegame\\ARCHIEEE\\feat.bjo', '3fdd21c3cb8243ac85fe49bf12f029b4', 'FILE', 1),
(98, 'App\\DefaultData\\settings\\warzone2100\\config', '1e3ca981ec0a43f98adf04f2ae841b35', 'FILE', 1),
(99, 'App\\Warzone 2100\\locale\\pl\\LC_MESSAGES\\warzone2100.mo', '94c17504b6dc4424bc25089e52ee3e78', 'FILE', 1),
(100, 'Data\\settings\\savegame\\ARCHIEEE\\limits.bjo', '63662bb288ca47de9d85df9bc19f5e08', 'FILE', 1),
(101, 'Other\\Help\\images\\favicon.ico', '84e0a6e887ac4b208a3c1c5515a61f00', 'FILE', 1),
(102, 'App\\DefaultData\\settings\\mods\\global\\grim.wz', 'f6129a43931b4ad0bbc856b5cbe72f55', 'FILE', 1),
(103, 'App\\Warzone 2100\\locale\\lv\\LC_MESSAGES\\warzone2100.mo', '62c5ca3b05804123a4c761ef0da1379f', 'FILE', 1),
(104, 'Data\\settings\\savegame\\ARCHIEEE\\game.map', '7e83d1e92a01411d98a02c036f0956bb', 'FILE', 1),
(105, 'Data\\settings\\savegame\\Leon\\messtate.bjo', 'f1bb938b7cc245f6ae02b66c7d025101', 'FILE', 1),
(106, 'App\\Warzone 2100\\warzone2100.exe', 'ac200fe4eef5471e9544003290f24a2d', 'FILE', 1),
(107, 'App\\Warzone 2100\\locale\\zh_CN\\LC_MESSAGES\\warzone2100.mo', '55648333499b4e5dabccc733641dd859', 'FILE', 1),
(108, 'Data\\settings\\savegame\\ARCHIEEE\\score.tag', '157c848020e84eedad692b5e38110f8e', 'FILE', 1),
(109, 'Data\\settings\\keymap.map', '231ea64ddb9d464ca7c5413b8ff5bbd5', 'FILE', 1),
(110, 'Data\\settings\\savegame\\Leon\\fxstate.tag', '01f5a16838bd4078bc20b4102bfbc527', 'FILE', 1),
(111, 'App\\DefaultData\\settings\\Warzone2100Portable.ini', '5c733dd4d00d422e85b5be1f3eb52c63', 'FILE', 1),
(112, 'Data\\settings\\netplay.log', '5a93c7e24cb04e7fbab40f2d4ac74712', 'FILE', 1),
(113, 'Data\\settings\\config', '1ce80898dae04506a60987e14ba27e57', 'FILE', 1),
(114, 'Data\\settings\\savegame\\ARCHIEEE\\proxstate.bjo', 'fecc51ae3ee842b69e989f414462bbf2', 'FILE', 1),
(115, 'App\\DefaultData\\settings\\warzone2100\\keymap.map', '0deb20114da54099b073e1904d040a67', 'FILE', 1),
(116, 'App\\Warzone 2100\\ChangeLog.txt', 'be2f3328fab3414d90fbe081ec25c295', 'FILE', 1),
(117, 'Data\\settings\\savegame\\ARCHIEEE\\fxstate.tag', '67067d03c1e0490581997bae66469aae', 'FILE', 1),
(118, 'Data\\settings\\savegame\\Leon\\flagstate.bjo', '5416e3cf564048ce8c3778a2845ad2bd', 'FILE', 1),
(119, 'App\\Warzone 2100\\dbghelp.dll.license.txt', '76adb9de39294fdbb69ace9e556d17cb', 'FILE', 1),
(120, 'Other\\Help\\images\\help_background_header.png', 'a8efe1bead3248278aef5e629da7f2c8', 'FILE', 1),
(121, 'App\\Warzone 2100\\locale\\uz\\LC_MESSAGES\\warzone2100.mo', 'c55be3931e4d433e8d0e5b38264c6167', 'FILE', 1),
(122, 'Other\\Source\\Warzone2100Portable.jpg', 'ba972f550bf543d4a67710eff4d1e28c', 'FILE', 1),
(123, 'Data\\settings\\savegame\\Leon\\compl.bjo', 'fc87374f5cbc40cf857c2ca6fda7db80', 'FILE', 1),
(124, 'App\\Warzone 2100\\locale\\ro\\LC_MESSAGES\\warzone2100.mo', '6de9c595a80740b28d8bf9e524ba6ba5', 'FILE', 1),
(125, 'App\\Warzone 2100\\Readme.de.txt', '9348704347c8497096180a011e9f9192', 'FILE', 1),
(126, 'Data\\settings\\multiplay\\custommaps', '', 'DIR', 1),
(127, 'App\\DefaultData\\settings\\maps', '', 'DIR', 1),
(128, 'App\\Warzone 2100\\locale\\fr', '', 'DIR', 1),
(129, 'App\\Warzone 2100\\locale\\lv', '', 'DIR', 1),
(130, 'Data\\settings\\multiplay', '', 'DIR', 1),
(131, 'App\\Warzone 2100\\locale\\lt', '', 'DIR', 1),
(132, 'App\\Warzone 2100\\locale\\ru', '', 'DIR', 1),
(133, 'App\\Warzone 2100\\locale\\fi', '', 'DIR', 1),
(134, 'App\\Warzone 2100\\locale\\uz\\LC_MESSAGES', '', 'DIR', 1),
(135, 'App\\Warzone 2100\\locale\\ro\\LC_MESSAGES', '', 'DIR', 1),
(136, 'App\\Warzone 2100\\locale\\ro', '', 'DIR', 1),
(137, 'App\\Warzone 2100\\locale\\lv\\LC_MESSAGES', '', 'DIR', 1),
(138, 'App\\Warzone 2100\\locale\\es\\LC_MESSAGES', '', 'DIR', 1),
(139, 'App\\Warzone 2100\\locale\\sv_SE', '', 'DIR', 1),
(140, 'App\\Warzone 2100\\locale\\la', '', 'DIR', 1),
(141, 'App\\DefaultData\\settings\\mods\\global', '', 'DIR', 1),
(142, 'Data\\settings\\multiplay\\forces', '', 'DIR', 1),
(143, 'App\\Warzone 2100\\locale\\eu', '', 'DIR', 1),
(144, 'Other\\Help\\images', '', 'DIR', 1),
(145, 'App\\Warzone 2100\\locale\\es', '', 'DIR', 1),
(146, 'App\\Warzone 2100\\locale\\lt\\LC_MESSAGES', '', 'DIR', 1),
(147, 'App', '', 'DIR', 1),
(148, 'App\\Warzone 2100\\locale\\pl\\LC_MESSAGES', '', 'DIR', 1),
(149, 'Data\\settings\\music', '', 'DIR', 1),
(150, 'App\\Warzone 2100\\fonts', '', 'DIR', 1),
(151, 'App\\Warzone 2100\\locale', '', 'DIR', 1),
(152, 'App\\Warzone 2100\\locale\\zh_CN\\LC_MESSAGES', '', 'DIR', 1),
(153, 'App\\Warzone 2100\\locale\\ga\\LC_MESSAGES', '', 'DIR', 1),
(154, 'App\\Warzone 2100\\locale\\pt_BR', '', 'DIR', 1),
(155, 'App\\DefaultData\\settings\\warzone2100\\multiplay\\players', '', 'DIR', 1),
(156, 'App\\DefaultData\\settings\\warzone2100\\multiplay', '', 'DIR', 1),
(157, 'App\\AppInfo', '', 'DIR', 1),
(158, 'Data\\settings\\savegame\\Leon', '', 'DIR', 1),
(159, 'App\\Warzone 2100\\locale\\nb\\LC_MESSAGES', '', 'DIR', 1),
(160, 'Data', '', 'DIR', 1),
(161, 'App\\Warzone 2100\\locale\\nn\\LC_MESSAGES', '', 'DIR', 1),
(162, 'App\\Warzone 2100\\locale\\cs\\LC_MESSAGES', '', 'DIR', 1),
(163, 'App\\Warzone 2100\\locale\\la\\LC_MESSAGES', '', 'DIR', 1),
(164, 'App\\Warzone 2100\\locale\\pt', '', 'DIR', 1),
(165, 'App\\Warzone 2100\\locale\\fy\\LC_MESSAGES', '', 'DIR', 1),
(166, 'App\\DefaultData\\settings\\warzone2100\\multiplay\\forces', '', 'DIR', 1),
(167, 'Data\\settings', '', 'DIR', 1),
(168, 'App\\Warzone 2100\\locale\\de', '', 'DIR', 1),
(169, 'App\\Warzone 2100\\locale\\pl', '', 'DIR', 1),
(170, 'App\\Warzone 2100\\locale\\da', '', 'DIR', 1),
(171, 'App\\DefaultData', '', 'DIR', 1),
(172, 'App\\Warzone 2100\\locale\\pt\\LC_MESSAGES', '', 'DIR', 1),
(173, 'Data\\settings\\multiplay\\players', '', 'DIR', 1),
(174, 'App\\Warzone 2100\\locale\\en_GB', '', 'DIR', 1),
(175, 'App\\Warzone 2100\\locale\\fr\\LC_MESSAGES', '', 'DIR', 1),
(176, 'App\\Warzone 2100\\locale\\de\\LC_MESSAGES', '', 'DIR', 1),
(177, 'App\\Warzone 2100\\locale\\cs', '', 'DIR', 1),
(178, 'App\\Warzone 2100\\locale\\it', '', 'DIR', 1),
(179, 'App\\DefaultData\\settings\\mods', '', 'DIR', 1),
(180, 'App\\Warzone 2100\\locale\\uz', '', 'DIR', 1),
(181, 'Data\\settings\\screendumps', '', 'DIR', 1),
(182, 'App\\DefaultData\\settings', '', 'DIR', 1),
(183, 'App\\Warzone 2100\\locale\\ru\\LC_MESSAGES', '', 'DIR', 1),
(184, 'App\\Warzone 2100\\locale\\sv_SE\\LC_MESSAGES', '', 'DIR', 1),
(185, 'App\\Warzone 2100\\locale\\nl\\LC_MESSAGES', '', 'DIR', 1),
(186, 'Data\\settings\\savegame\\ARCHIEEE', '', 'DIR', 1),
(187, 'App\\Warzone 2100\\multiplay\\forces', '', 'DIR', 1),
(188, 'App\\DefaultData\\settings\\warzone2100\\savegame', '', 'DIR', 1),
(189, 'App\\Warzone 2100\\locale\\sl\\LC_MESSAGES', '', 'DIR', 1),
(190, 'App\\Warzone 2100\\styles', '', 'DIR', 1),
(191, 'App\\Warzone 2100\\multiplay\\players', '', 'DIR', 1),
(192, 'App\\DefaultData\\settings\\warzone2100', '', 'DIR', 1),
(193, 'Other\\Help', '', 'DIR', 1),
(194, 'App\\Warzone 2100\\locale\\nn', '', 'DIR', 1),
(195, 'App\\Warzone 2100\\locale\\tr', '', 'DIR', 1),
(196, 'App\\Warzone 2100\\locale\\pt_BR\\LC_MESSAGES', '', 'DIR', 1),
(197, 'App\\Warzone 2100\\locale\\nl', '', 'DIR', 1),
(198, 'App\\Warzone 2100', '', 'DIR', 1),
(199, 'Other\\Source', '', 'DIR', 1),
(200, 'download', '', 'DIR', 1),
(201, 'App\\Warzone 2100\\music', '', 'DIR', 1),
(202, 'App\\Warzone 2100\\multiplay', '', 'DIR', 1),
(203, 'App\\Warzone 2100\\locale\\sv\\LC_MESSAGES', '', 'DIR', 1),
(204, 'App\\Warzone 2100\\locale\\da\\LC_MESSAGES', '', 'DIR', 1),
(205, 'App\\Warzone 2100\\locale\\it\\LC_MESSAGES', '', 'DIR', 1),
(206, 'Data\\settings\\maps', '', 'DIR', 1),
(207, 'App\\DefaultData\\settings\\warzone2100\\screendumps', '', 'DIR', 1),
(208, 'App\\Warzone 2100\\locale\\fi\\LC_MESSAGES', '', 'DIR', 1),
(209, 'App\\Warzone 2100\\maps', '', 'DIR', 1),
(210, 'App\\Warzone 2100\\locale\\nb', '', 'DIR', 1),
(211, 'App\\Warzone 2100\\multiplay\\custommaps', '', 'DIR', 1),
(212, 'Other', '', 'DIR', 1),
(213, 'App\\Warzone 2100\\locale\\sv', '', 'DIR', 1),
(214, 'App\\Warzone 2100\\locale\\eu\\LC_MESSAGES', '', 'DIR', 1),
(215, 'App\\Warzone 2100\\screendumps', '', 'DIR', 1),
(216, 'App\\Warzone 2100\\savegame', '', 'DIR', 1),
(217, 'App\\Warzone 2100\\locale\\en_GB\\LC_MESSAGES', '', 'DIR', 1),
(218, 'App\\Warzone 2100\\locale\\sl', '', 'DIR', 1),
(219, 'App\\Warzone 2100\\locale\\ga', '', 'DIR', 1),
(220, 'Data\\settings\\savegame', '', 'DIR', 1),
(221, 'App\\Warzone 2100\\locale\\zh_CN', '', 'DIR', 1),
(222, 'App\\DefaultData\\settings\\warzone2100\\multiplay\\custommaps', '', 'DIR', 1),
(223, 'App\\Warzone 2100\\locale\\tr\\LC_MESSAGES', '', 'DIR', 1),
(224, 'App\\Warzone 2100\\locale\\fy', '', 'DIR', 1);

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
CREATE TABLE IF NOT EXISTS `games` (
  `counter` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `desc` longtext,
  `type` varchar(20) DEFAULT NULL,
  `picture` varchar(100) DEFAULT NULL,
  `addDate` bigint(40) DEFAULT NULL,
  `createDate` bigint(40) DEFAULT NULL,
  `addBy` int(2) DEFAULT NULL,
  UNIQUE KEY `counter` (`counter`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `games`
--

INSERT INTO `games` (`counter`, `name`, `desc`, `type`, `picture`, `addDate`, `createDate`, `addBy`) VALUES
(1, 'Warzone Test', 'Muhahahahha', 'RTS', 'c9b001157d624c4a96ca8bd8bd383b90\\0b27bc5a315d4852b29183bda27a94eb', 1264976119701, -23889686400000, 6);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(40) DEFAULT NULL,
  `admin` tinyint(1) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `counter` tinyint(3) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(100) DEFAULT NULL,
  `gamersTag` varchar(100) DEFAULT NULL,
  `gradeNum` int(2) DEFAULT NULL,
  `bestAt` mediumtext,
  `favGames` mediumtext,
  `desc` mediumtext,
  `disabled` int(1) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`counter`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `admin`, `name`, `counter`, `avatar`, `gamersTag`, `gradeNum`, `bestAt`, `favGames`, `desc`, `disabled`, `password`) VALUES
('Owner', 1, 'Susan Blakey', 4, NULL, 'Lord.Mom', NULL, NULL, NULL, NULL, NULL, NULL),
('Lord.Quackstar', 1, 'LordQuackstar', 6, '69983a41e0754e5e8ec72893619b099a', 'Lord.Quackstar', 12, 'Shooting, Spray and Pray, Hording', 'Halo, Rise of Nations, Starcraft', 'I run Gamers Club, am a good programmer, and like doing crap', 0, 'FEA0F1F6FEDE90BD0A925B4194DEAC11');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
