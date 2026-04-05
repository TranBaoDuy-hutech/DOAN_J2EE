-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 05, 2026 lúc 02:04 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `vietlutravel3d`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bookings`
--

CREATE TABLE `bookings` (
  `BookingID` int(11) NOT NULL,
  `CustomerID` int(11) NOT NULL,
  `TourID` int(11) NOT NULL,
  `BookingDate` date NOT NULL,
  `NumberOfPeople` int(11) NOT NULL,
  `totalPrice` double NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `specialRequests` varchar(255) DEFAULT NULL,
  `travelDate` date DEFAULT NULL,
  `GuideID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `bookings`
--

INSERT INTO `bookings` (`BookingID`, `CustomerID`, `TourID`, `BookingDate`, `NumberOfPeople`, `totalPrice`, `status`, `specialRequests`, `travelDate`, `GuideID`) VALUES
(27, 2, 1, '2026-02-27', 8, 12000000, 'Confirmed', '', '2026-04-15', 4),
(28, 2, 33, '2026-02-27', 4, 20000000, 'Confirmed', '', '2026-04-05', 1),
(29, 2, 5, '2026-02-27', 4, 24000000, 'Confirmed', '', '2026-04-07', 2),
(30, 2, 33, '2026-02-28', 4, 6000000, 'Confirmed', '', '2026-04-15', 2),
(31, 2, 9, '2026-03-21', 4, 18000000, 'Confirmed', '', '2026-04-25', 1),
(32, 2, 6, '2026-03-21', 4, 14000000, 'Confirmed', 'hdv đẹppp nhaaa', '2026-04-15', 1),
(33, 12, 5, '2026-03-30', 8, 48000000, 'Confirmed', '', '2026-04-20', 1),
(34, 11, 5, '2026-03-30', 2, 12000000, 'Confirmed', '', '2026-04-07', 2),
(35, 9, 1, '2026-03-30', 4, 6000000, 'Confirmed', '', '2026-03-31', 2),
(36, 2, 1, '2026-03-30', 4, 6000000, 'Confirmed', '', '2026-03-31', 2),
(46, 13, 1, '2026-04-04', 2, 3000000, 'Pending', '', '2026-05-01', NULL),
(47, 13, 1, '2026-04-04', 2, 3000000, 'Pending', '', '2026-04-26', NULL),
(48, 13, 1, '2026-04-04', 2, 3000000, 'Pending', '', '2026-04-25', NULL),
(49, 13, 4, '2026-04-04', 4, 20000000, 'Pending', '', '2026-05-02', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `contacts`
--

CREATE TABLE `contacts` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `message` varchar(2000) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `replied` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `contacts`
--

INSERT INTO `contacts` (`id`, `name`, `email`, `phone`, `message`, `created_at`, `replied`) VALUES
(1, 'Phạm Văn Duy', 'Duyhdv@gmail.com', '0966543288', 'helooo', '2026-02-11 15:38:42', 0),
(2, '0488_Trần Bảo Duy', 'baoduy10072004@gmail.com', '+84329810650', 'heloooo', '2026-02-22 13:07:27', 1),
(3, 'Trần Bảo Duy', 'baoduy.nasani@gmail.com', '0329810650', 'tôi mún đặt tour du lịch!\r\n', '2026-02-25 13:13:33', 0),
(4, 'Trần Bảo Duy', 'baoduy.nasani@gmail.com', '0329810650', 'đặt tour có giảm giá không vậy?', '2026-02-27 17:23:29', 0),
(5, 'Huyền Trang', 'trang.huyen@hcmut.edu.vn', '0837463524', 'chính sách giảm giá sao vậy?', '2026-02-27 17:24:58', 1),
(6, 'Trần Bảo Duy', 'vothanhdat@gmail.com', '0329810650', 'abccccccccccccccccccccc', '2026-03-30 18:31:42', 0),
(7, 'Trần Bảo Duy', 'baoduy10072004@gmail.com', '0329810650', 'accccccccccccccccccccccc', '2026-03-30 18:32:08', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customers`
--

CREATE TABLE `customers` (
  `CustomerID` int(11) NOT NULL,
  `UserName` varchar(50) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Phone` varchar(20) NOT NULL,
  `Address` varchar(200) DEFAULT NULL,
  `DateOfBirth` date DEFAULT NULL,
  `Gender` varchar(10) DEFAULT NULL,
  `PasswordHash` varchar(256) DEFAULT NULL,
  `Role` varchar(20) DEFAULT 'USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `customers`
--

INSERT INTO `customers` (`CustomerID`, `UserName`, `Email`, `Phone`, `Address`, `DateOfBirth`, `Gender`, `PasswordHash`, `Role`) VALUES
(1, 'Việt Lữ Travel', 'vietlutravell@gmail.com', '0329810650', '104, Đường 179 Hoàng Hữu Nam, Phường Tăng Nhơn Phú, TP. Hồ Chí Minh', '2004-07-10', NULL, 'Duy@1007', 'ADMIN'),
(2, 'Trần Bảo Duy', 'baoduy.nasani@gmail.com', '0329810650', '104, Đường 179 Hoàng Hữu Nam, Phường Tăng Nhơn Phú, TP. Hồ Chí Minh', '2004-07-10', 'Nam', 'Duy@1007', 'USER'),
(6, 'Huyền Trang', 'trang.huyen@hcmut.edu.vn', '0736452736', 'VGP, Nguyễn Xiễn, P. Long Thạnh Mỹ, TP. HCM', '2006-07-10', 'Nữ', 'Trang@1007', 'USER'),
(7, 'Đoàn Nguyên Khải', 'dngkhai2607@gmail.com', '0923456789', '78 Trần Hưng Đạo, Tịnh Biên, An Giang', '2005-06-26', 'Nam', '123', 'USER'),
(8, 'CSKH', 'cskh@gmail.com', '0945678901', '56 Tà Pạ, Tri Tôn, An Giang', '2004-03-15', 'Nữ', 'Duy@1007', 'STAFF'),
(9, 'Võ Thành Đạt', 'vothanhdat@gmail.com', '0956789012', '89 Thoại Ngọc Hầu, Long Xuyên, An Giang', '2003-11-22', 'Nam', '123', 'USER'),
(10, 'Đỗ Thị Hồng', 'dothihong@yahoo.com', '0967890123', '34 Núi Sam, Châu Đốc, An Giang', '2005-09-09', 'Nữ', '123', 'USER'),
(11, 'Duy Tran', 'duytest887@gmail.com', '0000000000', 'N/A', NULL, NULL, 'GOOGLE_AUTH_NO_PASS', 'USER'),
(12, 'Trần Duy', 'duy809274@gmail.com', '0383746353', 'VGP, Nguyễn Xiển, P. Long Thạnh Mỹ, TP.HCM', NULL, NULL, 'GOOGLE_AUTH_NO_PASS', 'USER'),
(13, 'Trần Bảo Duy', 'baoduy10072004@gmail.com', '0000000000', 'N/A', NULL, NULL, 'GOOGLE_LOGIN', 'USER'),
(14, 'Trần Bảo Duy', 'trbaoduy10072004@gmail.com', '0329810650', '', NULL, 'Nam', NULL, 'USER'),
(15, 'Test User', 'test_1774553201806@gmail.com', '0901234567', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(16, 'Test User', 'test_1774553208828@gmail.com', '0901234567', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(17, 'Test User', 'test_1774553216242@gmail.com', '0901234567', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(18, 'Test User', 'test_1774553223287@gmail.com', '0901234567', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(19, 'Test User', 'test_1774553230538@gmail.com', '0901234567', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(20, 'Test User', 'test_1774553237906@gmail.com', '0901234567', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(21, 'Test User', 'test_1774553246546@gmail.com', '0901234567', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(22, 'Test User', 'test_1774553252436@gmail.com', '0901234567', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(23, 'Nguyen Van Test', 'user_1774575286553@vietlu.com', '0901286553', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(24, 'Nguyen Van Test', 'user_1774575293014@vietlu.com', '0901293014', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(26, 'Nguyen Van A', 'tc01_1774575659158@vietlu.com', '0911659158', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(27, 'Nguyen Van A', 'tc01_1774575663951@vietlu.com', '0911663951', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(29, 'Nguyen Van A', 'tc01_1774575926547@vietlu.com', '0911926547', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(30, '0488_Trần Bảo Duy', 'trANbaoduy10072004@gmail.com', '+84329810650', NULL, NULL, NULL, 'Duy@1007', 'USER'),
(31, 'Nguyen Van A', 'tc01_1774576128444@vietlu.com', '0912128444', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(32, 'Nguyen Van A', 'tc01_1774576133552@vietlu.com', '0912133552', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(33, 'Nguyen Van A', 'tc01_1774576300116@vietlu.com', '0912300116', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(34, 'Nguyen Van B', 'tc02_1774576302716@vietlu.com', '0911000002', NULL, NULL, NULL, 'Abcd@1234', 'USER'),
(46, 'Duy@gmail.com', 'trbaoduy10072004@gmail.com', '0329810650', '', NULL, 'Nam', 'Duy@1007', 'USER');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `destinations`
--

CREATE TABLE `destinations` (
  `DestinationID` int(11) NOT NULL,
  `DestinationName` varchar(100) NOT NULL,
  `City` varchar(50) DEFAULT NULL,
  `Country` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `foods`
--

CREATE TABLE `foods` (
  `id` bigint(20) NOT NULL,
  `alt_text` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `ingredients` text DEFAULT NULL,
  `model_path` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `poster_path` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `reviewsCount` int(11) DEFAULT NULL,
  `story` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `foods`
--

INSERT INTO `foods` (`id`, `alt_text`, `description`, `ingredients`, `model_path`, `name`, `poster_path`, `price`, `rating`, `reviewsCount`, `story`) VALUES
(1, 'Gà Đốt Ô Thum đặc sản An Giang', 'Món gà đốt trứ danh của vùng đất Ô Thum, Tri Tôn. Gà vườn được tẩm ướp gia vị đặc trưng gồm lá chúc, sả, ớt, tỏi rồi đốt trong nồi đất đến khi da vàng giòn, thịt ngọt đậm đà. Ăn kèm gỏi hoa chuối và muối ớt chanh.', 'Gà ta, Lá chúc, Sả, Ớt, Tỏi', '/models/ga.glb', 'Gà Đốt Ô Thum', '/models/posters/ga.jpg', '250.000đ - 300.000đ/con', 4.9, 320, 'Gà đốt Ô Thum có nguồn gốc từ người Khmer vùng Bảy Núi. Bí quyết nằm ở lá chúc (chanh sần) tạo nên hương thơm độc bản không nơi nào có.'),
(2, 'Bánh Bò Thốt Nốt đặc sản An Giang', 'Bánh bò thốt nốt vàng ươm, thơm lừng vị đường thốt nốt nguyên chất. Bánh có độ xốp mềm, vị ngọt thanh tao, béo nhẹ của nước cốt dừa. Là thức quà quê dân dã nhưng khó quên.', 'Bột gạo, Đường thốt nốt, Nước cốt dừa', '/models/thotnot.glb', 'Bánh Bò Thốt Nốt', '/models/posters/thotnot.jpg', '5.000đ - 10.000đ/cái', 4.8, 512, 'Món bánh dân dã gắn liền với cây thốt nốt - linh hồn của vùng đất An Giang.'),
(3, 'Bún Quậy An Giang', 'Một biến tấu thú vị của món bún nước lèo. Nước dùng thanh ngọt từ hải sản, chả cá tự làm dai ngon. Thực khách tự pha nước chấm \'quậy\' theo khẩu vị riêng.', 'Bún tươi, Chả cá thác lác, Mực, Tôm', '/models/bunquay.glb', 'Bún Quậy An Giang', '/models/posters/bunquay.jpg', '35.000đ - 55.000đ', 4.5, 180, 'Bún quậy đòi hỏi thực khách phải \'quậy\' nước chấm, tạo nên trải nghiệm ăn uống thú vị và tương tác.'),
(4, 'Trái Thốt Nốt An Giang', 'Trái thốt nốt tươi với cơm mềm, dẻo, ngọt mát như thạch, giải nhiệt cực tốt. Nước thốt nốt ngọt thanh tự nhiên, thơm dịu.', 'Trái thốt nốt tươi', '/models/traithotnot.glb', 'Trái Thốt Nốt', '/models/posters/traithotnot.jpg', '20.000đ/chục', 4.7, 80, 'Cây thốt nốt là biểu tượng của vùng Bảy Núi, mọi bộ phận của cây đều có thể dùng được.'),
(5, 'Bò Leo Núi An Giang', 'Món ăn độc đáo vùng Tân Châu. Bò được tẩm ướp kỹ, nướng trên vỉ nướng chuyên dụng có hình dạng đặc biệt giống ngọn núi. Thịt bò mềm, ngọt ăn kèm rau rừng và trứng gà.', 'Thịt bò tơ, Trứng gà, Bơ, Rau rừng', '/models/boleonui.glb', 'Bò Leo Núi', '/models/posters/boleonui.jpg', '150.000đ - 200.000đ/phần', 4.8, 215, 'Tên gọi \'Bò leo núi\' xuất phát từ chiếc vỉ nướng nhô cao như ngọn núi, giữ nhiệt tốt giúp thịt chín đều mềm ngọt.'),
(6, 'Bún Cá Châu Đốc - An Giang', 'Đặc sản nổi tiếng miền Tây. Nước lèo vàng nghệ đậm đà, cá lóc đồng tươi ngon, ăn kèm bông điên điển và rau sống.', 'Cá lóc đồng, Nghệ tươi, Bông điên điển', '/models/bunca.glb', 'Bún Cá Châu Đốc', '/models/posters/bunca.jpg', '25.000đ - 40.000đ', 4.6, 900, 'Món ăn thể hiện sự trù phú của miền Tây mùa nước nổi với cá đồng và bông điên điển.'),
(7, 'Cơm Tấm Long Xuyên - An Giang', 'Hạt tấm nhuyễn đặc trưng, sườn rim đậm vị, bì thơm, trứng kho thấm đều. Kèm chén nước mắm kẹo và đồ chua làm nên hương vị khó lẫn.', 'Gạo tấm nhuyễn, Sườn heo, Trứng kho, Bì', '/models/comtam.glb', 'Cơm Tấm Long Xuyên', '/models/posters/comtam.jpg', '30.000đ - 60.000đ', 4.9, 1200, 'Khác với Sài Gòn, cơm tấm Long Xuyên dùng gạo tấm rất nhuyễn và thịt sườn được rim mềm cắt nhỏ.'),
(8, 'Lẩu Mắm Châu Đốc - An Giang', 'Lẩu mắm đậm đà hương vị mắm cá linh, cá sặc. Ăn kèm với hàng chục loại rau đồng nội, cá hú, thịt ba chỉ, tôm, mực.', 'Mắm cá linh, Cá hú, Thịt ba chỉ, Rau đồng', '/models/laumam.glb', 'Lẩu Mắm Châu Đốc', '/models/posters/laumam.jpg', '200.000đ - 400.000đ', 4.7, 560, 'Châu Đốc là vương quốc mắm, và lẩu mắm là bản giao hưởng của các loại mắm ngon nhất nơi đây.'),
(9, 'Bò bảy món Núi Sam - An Giang', 'Bảy món bò trứ danh gồm bò lụi, bò nướng lá lốt, cháo bò... Nguyên liệu bò tơ tươi ngon vùng Bảy Núi.', 'Thịt bò tơ, Lá lốt, Mỡ chài', '/models/bo7.glb', 'Bò Bảy Món Núi Sam', '/models/posters/bo7.jpg', '100.000đ - 150.000đ/món', 4.6, 340, 'Đặc sản trứ danh phục vụ khách hành hương vía Bà Chúa Xứ Núi Sam.'),
(10, 'Bánh Xèo - An Giang', 'Bánh xèo miền Tây vỏ mỏng giòn rụm, nhân tôm thịt, giá sống. Ăn kèm hàng tá loại rau rừng tươi xanh.', 'Bột gạo, Nghệ, Tôm, Thịt ba rọi, Giá', '/models/banhxeo.glb', 'Bánh Xèo', '/models/posters/banhxeo.jpg', '20.000đ - 40.000đ/cái', 4.8, 780, 'Bánh xèo mỗi miền mỗi khác, bánh xèo An Giang nổi tiếng với rau rừng ăn kèm phong phú nhất.'),
(11, 'Đường Thốt Nốt - An Giang', 'Đặc sản trứ danh vùng Bảy Núi. Đường nấu thủ công từ mật hoa thốt nốt, vị ngọt thanh, dùng nấu chè hay kho cá đều tuyệt hảo.', 'Mật hoa thốt nốt 100%', '/models/duong.glb', 'Đường Thốt Nốt', '/models/posters/duong.jpg', '35.000đ - 50.000đ/kg', 5, 410, 'Nghề nấu đường thốt nốt là di sản văn hóa phi vật thể quốc gia, gắn liền với đời sống người Khmer.'),
(12, 'Trái Chúc - An Giang', 'Loại chanh rừng vỏ xù xì nhưng cực thơm, đặc sản vùng Bảy Núi. Lá và trái chúc là gia vị không thể thiếu trong món gà đốt hay cháo bò.', 'Trái chúc tươi', '/models/traichuc.glb', 'Trái Chúc', '/models/posters/traichuc.jpg', '50.000đ - 80.000đ/kg', 4.9, 150, 'Cây chúc mọc hoang dã ở vùng Bảy Núi, được xem là loại gia vị \'linh hồn\' của ẩm thực nơi đây.'),
(13, 'Xôi Phồng Chợ Mới - An Giang', 'Xôi chiên phồng giòn rụm, bên trong dẻo thơm, ăn kèm gà quay.', 'Nếp dẻo, Đậu xanh, Gà quay', '/models/xoiphongchomoi.glb', 'Xôi Phồng Chợ Mới', '/models/posters/xoiphongchomoi.jpg', '60.000đ - 120.000đ', 4.7, 220, 'Kỹ thuật chiên xôi phồng điêu luyện tạo nên quả cầu vàng ươm đẹp mắt.'),
(14, 'Bánh Chăm - An Giang', 'Loại bánh truyền thống của người Chăm An Giang.', 'Bột gạo, Đường, Trứng', '/models/banhcham.glb', 'Bánh Chăm', '/models/posters/banhcham.jpg', '5.000đ - 10.000đ', 4.5, 90, 'Món bánh không thể thiếu trong các dịp lễ hội của đồng bào Chăm.'),
(15, 'Bánh Canh Vĩnh Trung - An Giang', 'Sợi bánh canh dai đặc biệt, nước dùng ngọt thanh.', 'Bột gạo Neang Nhen, Cá lóc, Giò heo', '/models/banhcanh.glb', 'Bánh Canh Vĩnh Trung', '/models/posters/banhcanh.jpg', '30.000đ - 40.000đ', 4.6, 310, 'Sợi bánh canh dẹp, dai, mềm được làm từ loại gạo lúa mùa đặc sản vùng Bảy Núi.'),
(16, 'Khô rắn An Phú - An Giang', 'Đặc sản mùa nước nổi, vị ngọt, dai ngon.', 'Rắn bông súng, Rắn nước', '/models/khorananphu.glb', 'Khô Rắn An Phú', '/models/posters/khorananphu.jpg', '300.000đ - 500.000đ/kg', 4.8, 140, 'Mùa nước nổi là mùa của các loại rắn, người dân chế biến thành khô để dành ăn quanh năm.'),
(17, 'Bánh Mằn Dè - An Giang', 'Bánh dân gian độc lạ vùng biên giới.', 'Bột nếp, Đậu xanh, Dừa', '/models/banhmande.glb', 'Bánh Mằn Dè', '/models/posters/banhmande.jpg', '5.000đ', 4.4, 45, 'Loại bánh ít người biết đến nhưng hương vị rất mộc mạc, đậm chất quê.'),
(18, 'Bánh Hạnh Nhân - An Giang', 'Bánh giòn tan, thơm béo vị hạnh nhân.', 'Bột mì, Hạnh nhân, Đường, Bơ', '/models/banhhanhnhan.glb', 'Bánh Hạnh Nhân', '/models/posters/banhhanhnhan.jpg', '50.000đ/hộp', 4.6, 280, 'Món bánh tây được Việt hóa, trở thành đặc sản biếu tặng phổ biến.'),
(19, 'Gỏi Cá Trích - An Giang', 'Cá trích tươi tái chanh, trộn gỏi chua ngọt.', 'Cá trích tươi, Dừa nạo, Đậu phộng, Hành tây', '/models/goicatrich.glb', 'Gỏi Cá Trích', '/models/posters/goicatrich.jpg', '100.000đ/dĩa', 4.7, 190, 'Món ăn tươi sống đòi hỏi cá phải thật tươi, xử lý kỹ để không bị tanh.'),
(20, 'Cơm Ghẹ - An Giang', 'Cơm chiên với thịt ghẹ tươi ngọt.', 'Gạo thơm, Thịt ghẹ, Rau củ', '/models/comghe.glb', 'Cơm Ghẹ', '/models/posters/comghe.jpg', '60.000đ - 80.000đ', 4.6, 250, 'Hương vị biển khơi hòa quyện trong từng hạt cơm chiên săn chắc.'),
(21, 'Nước Mắm Phú Quốc - An Giang', 'Nước mắm nhỉ cá cơm thượng hạng.', 'Cá cơm, Muối biển', '/models/nuocmam.glb', 'Nước Mắm Phú Quốc', '/models/posters/nuocmam.jpg', '150.000đ/lít', 5, 1500, 'Quốc hồn quốc túy, gia vị không thể thiếu trong bữa cơm người Việt.'),
(22, 'Tiêu Phú Quốc - An Giang', 'Tiêu sọ cay nồng đặc trưng.', 'Hạt tiêu chín', '/models/tieu.glb', 'Tiêu Phú Quốc', '/models/posters/tieu.jpg', '300.000đ/kg', 4.9, 860, 'Tiêu Phú Quốc nổi tiếng vỏ mẩy, hạt chắc, cay nồng và thơm đậm đà.'),
(23, 'Bánh Tét Mật Cật - An Giang', 'Bánh tét gói bằng lá mật cật độc đáo.', 'Nếp, Đậu xanh, Thịt mỡ, Lá mật cật', '/models/banhtet.glb', 'Bánh Tét Mật Cật', '/models/posters/banhtet.jpg', '150.000đ/đòn', 4.8, 420, 'Lá mật cật giúp bánh có màu xanh ngọc bích đẹp mắt và hương thơm đặc trưng.'),
(24, 'Bún Kèn Hà Tiên - An Giang', 'Bún nước lèo nấu từ cá và nước cốt dừa.', 'Cá nhồng, Nước cốt dừa, Bột cà ri, Bún', '/models/bunken.glb', 'Bún Kèn Hà Tiên', '/models/posters/bunken.jpg', '30.000đ', 4.7, 180, 'Vị béo ngậy của nước cốt dừa hòa quyện vị đậm đà của cá biển tạo nên món bún kèn lạ miệng.'),
(25, 'Hải Sản Hà Tiên, Phú Quốc - An Giang', 'Tôm cua ghẹ tươi sống vùng biển Kiên Giang.', 'Tôm, Cua, Ghẹ, Mực', '/models/haisan.glb', 'Hải Sản Hà Tiên', '/models/posters/haisan.jpg', 'Thời giá', 4.8, 670, 'Hải sản tươi sống vừa đánh bắt, chế biến đơn giản giữ trọn vị ngọt tự nhiên.'),
(26, 'Bánh ống lá dứa - An Giang', 'Bánh ống thơm lừng mùi lá dứa và dừa nạo.', 'Bột gạo, Lá dứa, Dừa nạo, Muối mè', '/models/banhduc.glb', 'Bánh Ống Lá Dứa', '/models/posters/banhduc.jpg', '5.000đ/cái', 4.6, 530, 'Tiếng còi tu tu của xe bán bánh ống là ký ức tuổi thơ của biết bao người miền Tây.'),
(27, 'Thịt chuột đồng quay lu - An Giang', 'Đặc sản mùa lúa, chuột đồng quay lu béo ngậy.', 'Chuột đồng, Sả ớt, Gia vị', '/models/chuot.glb', 'Thịt Chuột Đồng', '/models/posters/chuot.jpg', '120.000đ/dĩa', 4.5, 210, 'Món ăn thử thách lòng can đảm nhưng hương vị thơm ngon hơn cả thịt gà.'),
(28, 'Tung lò mò (Lạp xưởng bò Chăm) - An Giang', 'Lạp xưởng bò đặc biệt của người Chăm.', 'Thịt bò, Mỡ bò, Cơm nguội, Ruột bò', '/models/lapxuong.glb', 'Tung Lò Mò', '/models/posters/lapxuong.jpg', '280.000đ/kg', 4.8, 300, 'Món ăn chứa đựng tinh hoa ẩm thực của văn hóa Chăm Islam ở An Giang.');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `messages`
--

CREATE TABLE `messages` (
  `MessageID` int(11) NOT NULL,
  `CustomerID` int(11) DEFAULT NULL,
  `UserID` int(11) DEFAULT NULL,
  `Content` varchar(2000) DEFAULT NULL,
  `SentAt` datetime DEFAULT NULL,
  `IsFromAdmin` tinyint(1) DEFAULT NULL,
  `IsRead` tinyint(1) DEFAULT NULL,
  `CreatedAt` datetime DEFAULT NULL,
  `Sentiment` varchar(50) DEFAULT NULL,
  `Category` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `messages`
--

INSERT INTO `messages` (`MessageID`, `CustomerID`, `UserID`, `Content`, `SentAt`, `IsFromAdmin`, `IsRead`, `CreatedAt`, `Sentiment`, `Category`) VALUES
(18, 12, 2, 'hi ạ', '2026-02-20 14:08:55', 0, 0, '2026-02-20 14:08:54', NULL, NULL),
(19, 12, 2, 'Xin chào! Tôi có thể giúp gì cho bạn?', '2026-02-20 14:09:31', 1, 0, '2026-02-20 14:09:31', NULL, NULL),
(20, 12, 2, 'mình muốn đặt tour', '2026-02-20 14:09:52', 0, 0, '2026-02-20 14:09:51', NULL, NULL),
(21, 12, 2, 'Cảm ơn bạn đã liên hệ!', '2026-02-20 14:10:08', 1, 0, '2026-02-20 14:10:08', NULL, NULL),
(22, 12, 2, '😂', '2026-02-20 14:12:30', 0, 0, '2026-02-20 14:12:30', NULL, NULL),
(23, 12, 2, 'nhưng mà cho em hỏi', '2026-02-20 14:21:40', 0, 0, '2026-02-20 14:21:39', NULL, NULL),
(24, 12, 2, 'Vui lòng chờ trong giây lát...', '2026-02-20 14:25:06', 1, 0, '2026-02-20 14:25:06', NULL, NULL),
(25, 12, 2, 'dạa', '2026-02-20 14:25:13', 0, 0, '2026-02-20 14:25:12', NULL, NULL),
(26, 11, 2, 'hi ạ', '2026-02-20 14:25:36', 0, 0, '2026-02-20 14:25:36', NULL, NULL),
(27, 11, 2, 'Cảm ơn bạn đã liên hệ!', '2026-02-20 14:25:53', 1, 0, '2026-02-20 14:25:52', NULL, NULL),
(28, 13, 2, 'HII', '2026-04-05 17:03:53', 0, 0, '2026-04-05 17:03:53', NULL, NULL),
(29, 13, 2, 'Xin chào! Tôi có thể giúp gì cho bạn?', '2026-04-05 17:04:27', 1, 0, '2026-04-05 17:04:27', NULL, NULL),
(30, 13, 2, '❤️', '2026-04-05 17:06:20', 0, 0, '2026-04-05 17:06:20', NULL, NULL),
(31, 13, 2, 'Cảm ơn bạn đã liên hệ!', '2026-04-05 17:08:07', 1, 0, '2026-04-05 17:08:07', NULL, NULL),
(32, 13, 2, 'dạ khôngg có gì, hihihihi', '2026-04-05 17:08:30', 0, 0, '2026-04-05 17:08:30', NULL, NULL),
(33, 13, 2, 'Xin chào! Tôi có thể giúp gì cho bạn?', '2026-04-05 17:15:43', 1, 0, '2026-04-05 17:15:43', NULL, NULL),
(34, 13, 2, 'mình muốn đi du lịchh', '2026-04-05 17:19:32', 0, 0, '2026-04-05 17:19:32', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payments`
--

CREATE TABLE `payments` (
  `PaymentID` int(11) NOT NULL,
  `BookingID` int(11) NOT NULL,
  `PaymentMethod` varchar(20) NOT NULL,
  `Amount` decimal(10,2) NOT NULL,
  `PaymentDate` date NOT NULL,
  `Status` varchar(20) DEFAULT NULL,
  `TransactionID` varchar(50) DEFAULT NULL,
  `Notes` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `RoleID` int(11) NOT NULL,
  `RoleName` varchar(50) NOT NULL,
  `Description` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`RoleID`, `RoleName`, `Description`) VALUES
(1, 'Admin', 'Quản lý toàn bộ hệ thống'),
(2, 'Staff', 'Quản lý đặt tour và thanh toán'),
(3, 'TourGuide', 'Xem lịch trình và phân công');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tourdestinations`
--

CREATE TABLE `tourdestinations` (
  `TourID` int(11) NOT NULL,
  `DestinationID` int(11) NOT NULL,
  `VisitOrder` int(11) NOT NULL,
  `Duration` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tourguides`
--

CREATE TABLE `tourguides` (
  `GuideID` int(11) NOT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Phone` varchar(20) NOT NULL,
  `ExperienceYears` int(11) DEFAULT NULL,
  `FullName` varchar(255) NOT NULL,
  `Language` varchar(255) DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tourguides`
--

INSERT INTO `tourguides` (`GuideID`, `Email`, `Phone`, `ExperienceYears`, `FullName`, `Language`, `Status`) VALUES
(1, '12a5.vovantam@gmail.com', '0736452736', 5, 'Võ Văn Tâm', 'Tiếng Anh', 'AVAILABLE'),
(2, 'nghiaasi4n@gmail.com', '0736452736', 4, 'Trần Trọng Nghĩa', 'Tiếng Anh', 'AVAILABLE'),
(3, 'nghiapham4527@gmail.com', '0966543288', 4, 'Phạm Hồ Quốc Nghĩa', 'Tiếng Anh', 'AVAILABLE'),
(4, 'dangphamminhtriet@gmail.com', '0966543288', 4, 'Đặng Phạm Minh Triết', 'Tiếng Anh', 'AVAILABLE');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tourguidesassignments`
--

CREATE TABLE `tourguidesassignments` (
  `TourID` int(11) NOT NULL,
  `GuideID` int(11) NOT NULL,
  `AssignmentDate` date DEFAULT NULL,
  `Role` varchar(20) DEFAULT NULL,
  `BookingID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tours`
--

CREATE TABLE `tours` (
  `TourID` int(11) NOT NULL,
  `TourName` varchar(255) DEFAULT NULL,
  `Location` varchar(255) DEFAULT NULL,
  `Price` decimal(38,2) DEFAULT NULL,
  `DurationDays` int(11) NOT NULL,
  `StartDate` date NOT NULL,
  `ImageUrl` varchar(255) DEFAULT NULL,
  `DepartureLocation` varchar(255) DEFAULT NULL,
  `HotelName` varchar(255) DEFAULT NULL,
  `IsHot` tinyint(1) DEFAULT NULL,
  `Itinerary` text DEFAULT NULL,
  `Transportation` varchar(255) DEFAULT NULL,
  `GuideID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tours`
--

INSERT INTO `tours` (`TourID`, `TourName`, `Location`, `Price`, `DurationDays`, `StartDate`, `ImageUrl`, `DepartureLocation`, `HotelName`, `IsHot`, `Itinerary`, `Transportation`, `GuideID`) VALUES
(1, 'Tour Núi Cấm', 'An Giang, Việt Nam', 1500000.00, 2, '2026-03-08', 'https://i.pinimg.com/originals/af/9f/db/af9fdb5268b8eabb6478a63e7c91112c.jpg', 'TP. HCM', 'Victoria Núi Sam', 1, '🗓️ NGÀY 1: CHINH PHỤC ĐỈNH CAO & NGẮM HOÀNG HÔN\r\nSáng (08:00 – 11:00): Di chuyển đến Khu du lịch Lâm Viên Núi Cấm. Mua vé cáp treo (khứ hồi khoảng 180k - 200k) để ngắm toàn cảnh dãy Thất Sơn hùng vĩ.\r\n\r\nTrưa (11:30 – 13:00): Ăn trưa với đặc sản Bánh xèo rau rừng (có tới hơn 20 loại rau núi lạ miệng) tại khu vực Hồ Thủy Liêm.\r\n\r\nChiều (13:30 – 16:30): * Viếng Chùa Vạn Linh (ngắm tòa tháp 9 tầng soi bóng xuống hồ).\r\n\r\nTham quan Chùa Phật Lớn và check-in tượng Phật Di Lặc khổng lồ.\r\n\r\nThuê xe ôm địa phương lên Vồ Bồ Hong – đỉnh cao nhất của Núi Cấm (716m). Đứng đây bạn có thể nhìn thấy tận biển Hà Tiên và biên giới Campuchia.\r\n\r\nTối (17:00 trở đi): * Nhận phòng: Bạn nên đặt các Homestay trên núi (như Phú Sĩ Homestay hoặc các khu Camping như OTuksa Camp) để trải nghiệm cái lạnh \"Đà Lạt miền Tây\".\r\n\r\nĂn tối với món Gà đốt lá chúc thơm nức mũi hoặc lẩu mắm đồng quê. Buổi đêm trên núi rất tĩnh lặng, thích hợp để uống trà, trò chuyện.\r\n\r\n🗓️ NGÀY 2: SĂN MÂY, CHỢ NÚI & RỪNG TRÀM\r\nSáng sớm (05:00 – 07:00): Dậy sớm để săn mây. Nếu may mắn, bạn sẽ thấy cả vùng thung lũng chìm trong biển mây trắng xóa.\r\n\r\nSáng (07:30 – 09:30): Ghé Chợ Mây Núi Cấm. Đây là chợ của người dân địa phương họp sớm trên núi, bạn có thể mua rau rừng, sầu riêng núi Cấm, hoặc các loại củ sâm đất về làm quà.\r\n\r\nTrưa (10:00 – 12:00): Xuống núi. Di chuyển khoảng 10km sang Rừng Tràm Trà Sư.\r\n\r\nNgồi xuồng máy (tắc ráng) lướt trên thảm bèo xanh ngắt.\r\n\r\nLeo lên tháp canh để ngắm nhìn hàng ngàn con cò, chim bay về vờn trên ngọn tràm.\r\n\r\nChiều (13:30 – 15:00): Ăn trưa tại Rừng Tràm với món Chuột đồng chiên nước mắm hoặc Cá lóc nướng trui. Sau đó kết thúc hành trình, ghé các sạp ven đường mua đường thốt nốt hoặc quả mây gai về làm quà.\r\n\r\n🎒 Một vài món nên mua về làm quà:\r\nSản phẩm từ Thốt Nốt: Đường lá, nước màu thốt nốt, bánh bò thốt nốt.\r\n\r\nRau củ núi: Sâm đất (giúp giải nhiệt), măng núi, sầu riêng (nếu đúng mùa).\r\n\r\nMắm Châu Đốc: Nếu bạn tiện đường ghé qua chợ Châu Đốc lúc về.', 'Xe du lịch', NULL),
(2, 'Tour Khám Phá Miếu Bà Chúa Xứ Núi Sam', 'An Giang, Việt Nam', 4500000.00, 2, '2026-03-01', 'https://storage.googleapis.com/blogvxr-uploads/2022/04/rltIdS8c-1.jpg', 'TP. HCM', 'Victoria Núi Sam', 1, 'Ngày 1: Tham quan Miếu Bà Chúa Xứ và lễ hội tâm linh\r\n\r\nKhởi hành đến thành phố Châu Đốc – nơi nổi tiếng với các điểm hành hương linh thiêng. Du khách viếng Miếu Bà Chúa Xứ Núi Sam, ngôi miếu được xem là biểu tượng tâm linh của người dân miền Tây Nam Bộ. Tại đây, du khách thắp hương cầu bình an, tài lộc và có cơ hội hòa mình vào không khí lễ hội rộn ràng nếu đến vào mùa lễ hội (tháng 4 âm lịch).\r\n\r\nBuổi chiều, tiếp tục tham quan Chùa Tây An Cổ Tự và Lăng Thoại Ngọc Hầu, những công trình mang đậm dấu ấn văn hóa – lịch sử gắn liền với vùng đất An Giang. Nghỉ đêm tại Châu Đốc.\r\n\r\nNgày 2: Leo núi Sam – Ngắm toàn cảnh sông Hậu\r\n\r\nSau bữa sáng, đoàn bắt đầu hành trình leo Núi Sam, ngọn núi biểu tượng của Châu Đốc. Trên đường lên, du khách có thể dừng chân ngắm cảnh, chụp ảnh và tận hưởng không khí trong lành.\r\n\r\nTừ đỉnh núi, toàn cảnh thành phố Châu Đốc và dòng sông Hậu uốn lượn hiền hòa hiện ra trong tầm mắt. Đây là điểm lý tưởng để chiêm ngưỡng vẻ đẹp thiên nhiên, ghi lại những khoảnh khắc đáng nhớ trong chuyến đi. Buổi chiều, đoàn tự do nghỉ ngơi hoặc thưởng thức đặc sản địa phương.\r\n\r\nNgày 3: Thăm làng Chăm – Mua sắm đặc sản Châu Đốc\r\n\r\nBuổi sáng, đoàn khởi hành đến làng Chăm ven sông Hậu, nơi vẫn còn lưu giữ nhiều nét văn hóa đặc trưng của người Chăm Hồi giáo. Du khách có thể tham quan nhà sàn truyền thống, thánh đường Hồi giáo, tìm hiểu về phong tục, lối sống và nghề dệt thổ cẩm thủ công.\r\n\r\nTrên đường trở về, đoàn ghé chợ Châu Đốc, được mệnh danh là “vương quốc mắm miền Tây”, để mua sắm các đặc sản nổi tiếng như mắm cá linh, mắm thái, khô cá sặc, đường thốt nốt, bánh bò thốt nốt. Kết thúc chương trình, xe đưa đoàn trở lại điểm đón ban đầu, chia tay và hẹn gặp lại trong những hành trình tiếp theo.', 'Xe du lịch', NULL),
(3, 'Tour Rừng Tràm Trà Sư Sinh Thái', 'An Giang, Việt Nam', 5500000.00, 2, '2026-03-02', 'https://th.bing.com/th/id/OIP.51iw8xJyf5m8BWgWyGI3SwHaEg?o=7rm=3&rs=1&pid=ImgDetMain&o=7&rm=3', 'TP. HCM', 'Trà Sư Eco Resort', 1, 'Ngày 1: Đi thuyền khám phá rừng tràm – Ngắm chim di cư\r\n\r\nBuổi sáng, đoàn khởi hành đến rừng tràm Trà Sư, khu rừng ngập nước nổi tiếng của An Giang. Tại đây, du khách sẽ ngồi thuyền máy len lỏi qua những con rạch nhỏ phủ đầy bèo tây và sen súng, chiêm ngưỡng khung cảnh xanh mát yên bình của rừng tràm nguyên sinh.\r\n\r\nThuyền đưa du khách đến khu vực trung tâm – nơi sinh sống của hàng chục loài chim nước, cò, le le, vạc, điên điển... Mùa chim di cư là thời điểm lý tưởng để ngắm nhìn hàng nghìn cánh chim bay lượn trên nền trời trong xanh, tạo nên bức tranh thiên nhiên sống động hiếm có.\r\n\r\nBuổi chiều, du khách có thể lên tháp quan sát cao 23m để ngắm toàn cảnh rừng tràm từ trên cao. Buổi tối, nghỉ đêm tại khu du lịch sinh thái hoặc homestay ven rừng, tận hưởng không gian yên tĩnh giữa thiên nhiên.\r\n\r\nNgày 2: Dạo vườn quốc gia – Thưởng thức ẩm thực miền Tây\r\n\r\nBuổi sáng, đoàn di chuyển đến vườn quốc gia Trà Sư, nơi bảo tồn hệ sinh thái đa dạng của vùng đất ngập nước Tây Nam Bộ. Du khách có thể dạo bộ trên cầu tre xuyên rừng, hít thở bầu không khí trong lành và khám phá hệ thực vật, động vật đặc hữu.\r\n\r\nBuổi trưa, thưởng thức ẩm thực miền Tây dân dã với các món đặc sản như cá lóc nướng trui, lẩu mắm, gỏi bông điên điển, cơm nấu trong lá sen... tất cả được chế biến từ nguyên liệu địa phương tươi ngon.\r\n\r\nSau bữa trưa, đoàn khởi hành về lại điểm đón ban đầu, kết thúc hành trình khám phá rừng tràm – vườn quốc gia đầy trải nghiệm và ấn tượng.', 'Xe du lịch', NULL),
(4, 'Tour Hồ Tà Pạ - Tuyệt Tình Cốc', 'An Giang, Việt Nam', 5000000.00, 3, '2026-02-15', 'https://tse4.mm.bing.net/th/id/OIP.O9ygOZT0PrO1Ws3lxor4zwHaE7?rs=1&pid=ImgDetMain&o=7&rm=3', 'TP. HCM', 'Tà Pạ Lake Resort', 1, 'Ngày 1: Trekking quanh hồ – Ngắm cảnh núi non hùng vĩ\r\n\r\nBuổi sáng, đoàn khởi hành đến hồ Tà Pạ, điểm đến nổi tiếng thuộc huyện Tri Tôn, An Giang. Sau khi nhận phòng và nghỉ ngơi, du khách bắt đầu hành trình trekking quanh hồ Tà Pạ, nơi được mệnh danh là “tuyệt tình cốc của miền Tây”.\r\n\r\nCon đường mòn quanh hồ đưa du khách băng qua những triền cỏ xanh, vách đá và hồ nước trong veo phản chiếu bóng núi. Từ trên cao, toàn cảnh núi Cô Tô, núi Tà Pạ và những cánh đồng lúa trải dài hiện ra hùng vĩ, mang lại cảm giác thanh bình và thư thái.\r\n\r\nBuổi chiều, đoàn tự do khám phá, chụp ảnh hoàng hôn trên mặt hồ – một trong những khoảnh khắc đẹp nhất tại Tà Pạ. Nghỉ đêm tại homestay hoặc khu du lịch sinh thái gần hồ.\r\n\r\nNgày 2: Câu cá và picnic bên hồ\r\n\r\nSau bữa sáng, du khách cùng tham gia hoạt động câu cá thư giãn bên hồ, tận hưởng không khí mát lành và sự tĩnh lặng của thiên nhiên.\r\n\r\nBuổi trưa, đoàn tổ chức picnic ngoài trời, thưởng thức các món nướng, cá tươi vừa câu được, kết hợp vui chơi, chụp ảnh, nghỉ ngơi giữa khung cảnh non nước hữu tình.\r\n\r\nBuổi chiều, du khách có thể tản bộ quanh hồ hoặc ngồi ngắm hoàng hôn buông xuống mặt nước – một trải nghiệm nhẹ nhàng, gần gũi với thiên nhiên.\r\n\r\nNgày 3: Tham quan chùa Tà Pạ cổ kính\r\n\r\nBuổi sáng, đoàn làm thủ tục trả phòng, sau đó di chuyển đến chùa Tà Pạ, ngôi chùa cổ kính nằm trên triền núi Tà Pạ, nổi tiếng với kiến trúc Khmer độc đáo.\r\n\r\nTừ sân chùa, du khách có thể phóng tầm mắt ngắm toàn cảnh hồ Tà Pạ xanh ngọc bích và đồng ruộng Tri Tôn. Đây không chỉ là điểm tham quan tâm linh mà còn là nơi lý tưởng để chiêm nghiệm, tìm lại sự bình yên trong tâm hồn.\r\n\r\nSau bữa trưa, đoàn khởi hành trở về, kết thúc hành trình 3 ngày đầy trải nghiệm và thư giãn giữa thiên nhiên An Giang.', 'Xe du lịch', NULL),
(5, 'Tour Núi Cấm - Vạn Linh Thiền Viện', 'An Giang, Việt Nam', 6000000.00, 4, '2026-03-08', 'https://image.giacngo.vn/w770/Uploaded/2023/kyztyexlpvekilpve/2022_06_14/media-share-0-02-06-dd438dcb07f2fbbfb34df95009cc25b103956340f344e94e011846595e8a7968-7ea99658-2e67-480a-be1d-87d2005e57d5-e1586083062774-7386.jpeg', 'TP. HCM', 'Núi Cấm Resort', 1, 'Chào bạn! Với lịch trình 4 ngày tại Núi Cấm (An Giang) mà bạn đã chọn, đây chắc chắn sẽ là một chuyến hành trình tìm về sự an yên và khám phá thiên nhiên hùng vĩ của \"nóc nhà miền Tây\".\r\n\r\nDưới đây là lịch trình chi tiết được hệ thống lại dựa trên các điểm đến bạn mong muốn:\r\n\r\nLịch trình: Chinh Phục Núi Cấm – Huyền Thoại Thất Sơn (4 Ngày 3 Đêm)\r\nNgày 1 & 2: Chinh Phục \"Nóc Nhà Miền Tây\" & Chiêm Bái Chùa Vạn Linh\r\nBuổi sáng: Di chuyển đến chân Núi Cấm tại khu du lịch Lâm Viên. Trải nghiệm hệ thống Cáp treo Núi Cấm hiện đại nhất khu vực. Từ cabin, bạn có thể phóng tầm mắt ngắm nhìn thảm xanh của đại ngàn và các vách đá dựng đứng hùng vĩ.\r\n\r\nBuổi chiều: Tham quan Chùa Vạn Linh. Đây là một trong những ngôi chùa đẹp nhất núi với lối kiến trúc cổ kính, uy nghiêm. Điểm nhấn là tháp Quan Âm Các cao 9 tầng, nơi bạn có thể cảm nhận không khí thanh tịnh và mây trời giăng lối.\r\n\r\nNghỉ ngơi: Nghỉ đêm tại các nhà nghỉ hoặc homestay trên núi để cảm nhận cái se lạnh đặc trưng của \"Đà Lạt miền Tây\".\r\n\r\nNgày 3: Chiêm Bái Tượng Phật Di Lặc Lớn Nhất Châu Á\r\nBuổi sáng: Tham quan cụm tượng Phật Di Lặc tọa lạc trên đỉnh Núi Cấm. Đây là Tượng Phật Di Lặc lớn nhất Việt Nam và Châu Á, nổi bật với nụ cười từ bi hướng ra hồ Thủy Liêm xanh ngắt.\r\n\r\nBuổi chiều: Dạo quanh hồ Thủy Liêm, tận hưởng không gian thoáng đãng và chụp hình lưu niệm với khung cảnh \"sơn thủy hữu tình\" phản chiếu mây trời. Bạn cũng có thể ghé thăm chùa Phật Lớn nằm ngay cạnh đó.\r\n\r\nNgày 4: Khám Phá Hang Động & Trở Về\r\nBuổi sáng: Tham gia hành trình khám phá các hang động huyền bí trên núi như Hang Ông Thẻ hoặc các hang gắn liền với truyền thuyết của các bậc tu hành xưa. Đường đi qua các vách đá và tán rừng sẽ mang lại cảm giác phiêu lưu thú vị.\r\n\r\nBữa trưa: Đừng quên thưởng thức đặc sản Bánh xèo rau rừng Núi Cấm với hàng chục loại rau núi độc lạ trước khi xuống núi.\r\n\r\nBuổi chiều: Tự do mua sắm đặc sản như trà sâm núi, đường thốt nốt, mật ong rừng tại chợ nông sản trên đỉnh núi rồi khởi hành trở về.\r\n\r\n💡 Thông tin hữu ích cho bạn:\r\nCáp treo Núi Cấm (Ga đi): Nhà ga đi cáp treo, Núi Cấm, Tịnh Biên, An Giang\r\n\r\nChùa Vạn Linh: An Hảo, Tịnh Biên, An Giang\r\n\r\nTượng Phật Di Lặc: Ấp vào đầu xã, An Hảo, Tịnh Biên, An Giang\r\n\r\nHang động: Hang Ông Thẻ - Hang Công Đức\r\n\r\nLưu ý nhỏ: Buổi đêm và sáng sớm trên núi khá lạnh, bạn nên mang theo áo khoác mỏng và chuẩn bị giày thể thao có độ bám tốt để khám phá hang động an toàn hơn nhé! Chúc bạn có một chuyến đi tuyệt vời!', 'Xe du lịch', NULL),
(6, 'Tour Chợ Nổi Long Xuyên Sông Nước', 'An Giang, Việt Nam', 3500000.00, 2, '2026-03-08', 'https://ik.imagekit.io/tvlk/blog/2023/09/cho-noi-long-xuyen.jpg?tr=dpr-2,w-675', 'TP. HCM', 'Mekong Riverside Hotel', 0, 'Chào bạn! Với yêu cầu đi chợ nổi, làng nghề và khám phá sông nước tại An Giang, mình xin gợi ý lịch trình 2 ngày 1 đêm chi tiết, tập trung tại khu vực Thành phố Long Xuyên và các cù lao ven sông Hậu – nơi giữ được nét mộc mạc nhất của vùng đất này.\r\n\r\nNgày 1: Chợ Nổi Long Xuyên & Thiên Đường Ăn Vặt\r\nTrọng tâm: Trải nghiệm nhịp sống sông nước buổi sớm và ẩm thực đường phố sầm uất.\r\n\r\n05:30 - 06:30: Chợ nổi Long Xuyên.\r\n\r\nBạn nên xuất phát sớm từ bến phà Ô Môi. Chợ nổi ở đây không quá xô bồ như Cái Răng (Cần Thơ) mà rất chân phương.\r\n\r\nTrải nghiệm: Ăn sáng ngay trên ghe với các món hủ tiếu, bún cá Long Xuyên nóng hổi và uống cà phê kho.\r\n\r\n08:00 - 10:00: Mua trái cây tại chợ.\r\n\r\nGhé các ghe \"bẹo\" (treo gì bán đó) để mua trái cây đặc sản như nhãn xuồng, xoài, đu đủ... trực tiếp từ các nhà vườn với giá rất rẻ.\r\n\r\n11:00: Thưởng thức đặc sản Cơm tấm Long Xuyên.\r\n\r\nGhé các quán nổi tiếng như Cơm tấm Cây Điệp (Lý Tự Trọng) để thử đĩa cơm tấm với thịt nướng thái mảnh, trứng kho cắt nhỏ và nước mắm kẹo đặc trưng.\r\n\r\nChiều: Dạo phố và Ăn vặt đường phố.\r\n\r\nChợ Xẻo Trôm: Thiên đường của các loại bánh dân gian: bánh bò thốt nốt, bánh da lợn, bánh lọt.\r\n\r\nKhu vực bờ kè sông Hậu: Buổi chiều rất mát mẻ, tập trung nhiều xe đẩy bán đồ chiên, nem nướng và trà sữa.\r\n\r\nMón nên thử: Chè bưởi, chè thốt nốt Ngọc Điệp (khu Mỹ Bình) nổi tiếng thơm ngon.\r\n\r\nNgày 2: Làng Nghề Cù Lao & Sông Hậu Êm Đềm\r\nTrọng tâm: Thăm các làng nghề truyền thống và tận hưởng không khí trong lành của cù lao giữa sông.\r\n\r\nSáng: Khám phá Cù Lao Giêng hoặc Cù Lao Ông Hổ.\r\n\r\nCù Lao Ông Hổ (xã Mỹ Hòa Hưng): Đi phà băng qua sông Hậu. Đây là nơi có Làng nghề lưỡi câu Mỹ Hòa hơn 100 năm tuổi. Bạn có thể tận mắt xem người dân làm ra những chiếc lưỡi câu nhỏ xíu nhưng sắc bén.\r\n\r\nGhé thăm Khu lưu niệm Chủ tịch Tôn Đức Thắng với kiến trúc nhà sàn cổ đặc trưng của Nam Bộ.\r\n\r\n10:30: Thăm Làng dệt thổ cẩm Châu Phong (hoặc làng Chăm ven sông Hậu).\r\n\r\nNếu có thời gian di chuyển lên phía Châu Đốc/Tân Châu, bạn hãy ghé làng Chăm. Tại đây, bạn sẽ thấy những ngôi nhà sàn cao và các nghệ nhân dệt thổ cẩm thủ công rất tinh xảo.\r\n\r\nTrưa: Ăn trưa bên sông Hậu.\r\n\r\nThưởng thức cá tai tượng chiên xù, cá linh bông điên điển (nếu đi vào mùa nước nổi) hoặc các món từ cá tra, cá lăng đặc sản dòng sông Hậu.\r\n\r\nChiều: Check-in Làng hoa An Thạnh.\r\n\r\nNằm ngay ven sông Hậu (xã Hòa An), đây là vương quốc hoa kiểng của Long Xuyên. Bạn có thể dạo vườn hoa, tìm hiểu cách chăm sóc cây kiểng của người dân địa phương.\r\n\r\n16:00: Ra bến tàu, làm một chuyến dạo quanh các làng bè cá nổi trên sông Hậu để thấy quy trình nuôi cá đặc trưng trước khi kết thúc hành trình.\r\n\r\n💡 Kinh nghiệm nhỏ cho bạn:\r\nPhương tiện: Ở Long Xuyên bạn có thể thuê xe máy để dễ len lỏi vào các ngõ ngách ăn vặt. Khi đi chợ nổi, nên thương lượng giá tàu tại bến phà Ô Môi (khoảng 150k - 250k/tàu tùy số lượng người).\r\n\r\nQuà mang về: Ngoài trái cây, đừng quên mua mắm Châu Đốc, khô cá sặc hoặc đường thốt nốt nguyên chất nhé.', 'Thuyền', NULL),
(7, 'Tour Chùa Tây An Cổ Tự', 'An Giang, Việt Nam', 3000000.00, 3, '2026-03-08', 'https://thamhiemmekong.com/wp-content/uploads/2019/09/tay-an-co-tu-1.jpg', 'TP. HCM', 'Sam Mountain Resort', 1, 'Chào bạn! Một lịch trình 3 ngày 2 đêm tập trung vào cụm di tích Núi Sam (Châu Đốc) sẽ giúp bạn thấu hiểu sâu sắc hơn về dòng chảy lịch sử và sự giao thoa văn hóa độc đáo của vùng đất này.\r\n\r\nDưới đây là lịch trình chi tiết được hệ thống lại để bạn có trải nghiệm trọn vẹn nhất:\r\n\r\nHÀNH TRÌNH: CHÂU ĐỐC – GIAO THOA VĂN HÓA & DẤU ẤN MỞ CÕI (3 NGÀY 2 ĐÊM)\r\nNgày 1: Sắc Màu Kiến Trúc Ấn – Việt & Hương Vị Biên Thùy\r\nTrọng tâm: Chiêm ngưỡng sự độc đáo trong kiến trúc và ẩm thực địa phương.\r\n\r\nSáng: Khởi hành đi Châu Đốc.\r\n\r\nĐoàn di chuyển đến thành phố Châu Đốc. Bắt đầu hành trình khám phá những công trình có sự ảnh hưởng của kiến trúc Ấn Độ (điển hình là các tháp mái vòm, hoa văn tinh xảo kết hợp cùng mái ngói, rồng phượng Việt Nam).\r\n\r\nTrưa: Dùng bữa trưa với các món đặc sản: Cá basa kho tộ, canh chua bông súng.\r\n\r\nChiều: Khám phá \"Vương quốc mắm\" & Phố thị.\r\n\r\nChợ Châu Đốc: Tự do dạo chơi và thưởng thức ẩm thực đường phố.\r\n\r\nMón nên thử: Bún cá Châu Đốc (vị đậm đà từ ngải bún), chè thốt nốt béo ngậy, hoặc bánh bò thốt nốt nướng thơm lừng.\r\n\r\nMua sắm các loại mắm đặc sản và quà lưu niệm vùng biên giới.\r\n\r\nTối: Tận hưởng không khí yên bình của thành phố ngã ba sông về đêm.\r\n\r\nNgày 2: Chinh Phục Núi Sam – Toàn Cảnh Vùng Biên\r\nTrọng tâm: Trải nghiệm thiên nhiên và ngắm nhìn biên giới từ trên cao.\r\n\r\nSáng: Leo Núi Sam.\r\n\r\nDu khách có thể chọn đi bộ để rèn luyện sức khỏe hoặc đi xe trung chuyển lên đỉnh núi.\r\n\r\nTrải nghiệm: Phóng tầm mắt ngắm nhìn dải lụa Kênh Vĩnh Tế thẳng tắp và những cánh đồng lúa bạt ngàn trải dài sang tận biên giới Campuchia.\r\n\r\nTrưa: Thưởng thức bữa trưa với món Gà hấp lá chúc – món ăn mang hương vị đặc trưng của vùng Thất Sơn.\r\n\r\nChiều: Hoàng hôn trên đỉnh núi.\r\n\r\nNghỉ ngơi tại các vồ đá hoặc quán cà phê có view hướng sông.\r\n\r\nKhoảnh khắc vàng: Chờ đợi mặt trời dần lặn xuống sau những dãy núi phía xa, ánh sáng nhuộm vàng cả dòng sông Hậu và những xóm đạo ven sông. Đây là thời điểm tuyệt vời nhất để lưu lại những bức ảnh nghệ thuật.\r\n\r\nTối: Nghỉ đêm tại các khách sạn/resort ngay dưới chân núi để cảm nhận sự linh thiêng, tĩnh mịch.\r\n\r\nNgày 3: Điểm Hẹn Di Sản & Lịch Sử Khai Hoang\r\nTrọng tâm: Viếng chùa cổ và tưởng niệm công thần mở cõi.\r\n\r\nSáng: Viếng Chùa Tây An & Lăng Thoại Ngọc Hầu.\r\n\r\nChùa Tây An Cổ Tự: Chiêm ngưỡng các tháp tròn kiểu Ấn Độ kết hợp cùng kiến trúc chùa Việt, tìm hiểu về dòng thiền Lâm Tế tại đây.\r\n\r\nLăng Thoại Ngọc Hầu (Sơn Lăng): Thăm mộ phần và bia ký cổ. Nghe kể về cuộc đời danh tướng Thoại Ngọc Hầu – người đã dùng trí và lực để chỉ huy đào kênh Vĩnh Tế, tạo nên bước ngoặt cho kinh tế và quốc phòng miền Tây.\r\n\r\nTrưa: Dùng cơm trưa chia tay Châu Đốc.\r\n\r\nChiều: Đoàn khởi hành trở về. Trên đường đi có thể dừng chân mua đặc sản Đường thốt nốt hoặc Khô cá tra phồng làm quà.\r\n\r\n💡 Một vài lưu ý nhỏ cho chuyến đi:\r\nKiến trúc: Khi tham quan Chùa Tây An, bạn hãy chú ý đến các chi tiết trên mái vòm và các tượng voi – đó là những nét chấm phá Ấn Độ rất rõ nét.\r\n\r\nChuẩn bị: Leo núi Sam buổi sáng nên mang theo nước uống và chọn giày có độ bám tốt. Nếu ngắm hoàng hôn, đừng quên mang theo thuốc chống muỗi nhé.\r\n\r\nKênh Vĩnh Tế: Khi đứng trên núi Sam, hãy nhờ người dân chỉ hướng nhìn về phía kênh Vĩnh Tế để cảm nhận hết tầm vóc của công trình mà bạn đã tìm hiểu tại Lăng Thoại Ngọc Hầu.', 'Xe du lịch', NULL),
(8, 'Tour Khu Di Tích Óc Eo Lịch Sử', 'An Giang, Việt Nam', 4000000.00, 3, '2026-03-08', 'https://dailytravelvietnam.com/vi/images/2016/02/di-chi-oc-eo-an-giang-1-700x468.jpg', 'TP. HCM', 'Óc Eo Heritage Hotel', 0, 'Chào bạn, một hành trình đi ngược dòng thời gian về với nền văn minh Óc Eo (Phù Nam) tại An Giang là một lựa chọn cực kỳ sâu sắc và thú vị.\r\n\r\nDưới đây là lịch trình chi tiết 3 ngày 2 đêm tại khu vực Thoại Sơn - Tri Tôn - Tịnh Biên, nơi tập trung những dấu tích cổ xưa nhất:\r\n\r\nNgày 1: Ngược dòng Phù Nam tại Thoại Sơn\r\nTrọng tâm: Những nền móng gạch đá nghìn năm tuổi.\r\n\r\nSáng (08:00): Di chuyển đến Khu di tích Óc Eo (Thị trấn Óc Eo, Thoại Sơn). Đây từng là thương cảng sầm uất của vương quốc Phù Nam từ thế kỷ I đến thế kỷ VII.\r\n\r\n09:30: Tham quan Gò Cây Thị. Đây là di tích lộ thiên quan trọng nhất với các cấu trúc nền móng đền đài, mộ táng bằng gạch và đá vô cùng tinh xảo.\r\n\r\n11:00: Ghé thăm Linh Sơn Tự (Chùa Phật Bốn Tay). Nơi đây lưu giữ bức tượng Phật bốn tay cổ bằng đá (tượng thần Vishnu cải biến) và hai bia đá cổ có chữ Phạn (Sanskrit) – những bảo vật quốc gia minh chứng cho sự tồn tại của Phù Nam.\r\n\r\nTrưa: Thưởng thức cơm quê tại Thoại Sơn (cá lóc nướng trui, canh chua điên điển).\r\n\r\nChiều: Chinh phục Núi Sập và tham quan Hồ Ông Thoại. Dù mang tính chất thắng cảnh hơn, nhưng đây là nơi thờ Thoại Ngọc Hầu – người có công lớn trong việc khai phá vùng đất này sau này.\r\n\r\nNgày 2: Giải mã cổ vật tại Bảo tàng\r\nTrọng tâm: Chiêm ngưỡng trang sức, con dấu và biểu tượng tôn giáo.\r\n\r\nSáng (08:30): Tham quan Nhà trưng bày văn hóa Óc Eo (ngay tại Thoại Sơn). Đây là nơi tập trung các hiện vật được khai quật tại chỗ: gốm, đồ trang sức bằng vàng, hạt chuỗi, và các con dấu bằng đất nung.\r\n\r\n11:00: Di chuyển về Thành phố Long Xuyên. Ăn trưa với đặc sản lẩu mắm hoặc bún cá Long Xuyên.\r\n\r\nChiều (14:00): Tham quan Bảo tàng tỉnh An Giang.\r\n\r\nKhu vực khảo cổ học tại đây trưng bày những bộ sưu tập tượng đá Ganesha, Vishnu và đặc biệt là các Linga - Yoni bằng đá cực lớn, biểu tượng cho tín ngưỡng phồn thực của người Phù Nam cổ.\r\n\r\nTối: Dạo quanh hồ Nguyễn Du, tận hưởng không khí nhộn nhịp của Long Xuyên.\r\n\r\nNgày 3: Sắc màu Phum Sóc (Làng Khmer)\r\nTrọng tâm: Kiến trúc chùa tháp và văn hóa bản địa Tri Tôn/Tịnh Biên.\r\n\r\nSáng (08:00): Di chuyển hướng về huyện Tri Tôn. Đây là thủ phủ của người Khmer tại An Giang.\r\n\r\n09:30: Thăm Chùa Xà Tón (Xayton). Ngôi chùa hơn 200 năm tuổi, kiến trúc Khmer đặc trưng với mái đao nhọn và là nơi lưu giữ nhiều bộ kinh lá buông nhất Việt Nam.\r\n\r\n11:00: Check-in tại Cổng trời Tri Tôn (Cổng chùa Koh Kas) nằm giữa cánh đồng lúa xanh mướt, một biểu tượng rất \"nghệ\" của làng quê Khmer.\r\n\r\nTrưa: Thưởng thức ẩm thực Khmer: Gà đốt Ô Thum (thơm mùi lá chúc) hoặc Bún suông, Đu đủ đâm.\r\n\r\nChiều: Dạo quanh các phum sóc tại vùng Bảy Núi (Thất Sơn). Bạn có thể mua đường thốt nốt nguyên chất hoặc bánh bò thốt nốt nướng tại các lò của người dân địa phương làm quà.\r\n\r\n16:00: Kết thúc hành trình.\r\n\r\n💡 Một vài lưu ý cho chuyến đi:\r\nDi chuyển: Khoảng cách giữa Thoại Sơn, Long Xuyên và Tri Tôn khá xa (khoảng 40-50km mỗi chặng), bạn nên thuê xe máy hoặc ô tô riêng để chủ động.\r\n\r\nTrang phục: Khi vào các khu di tích tôn giáo (Linh Sơn Tự, Chùa Khmer), hãy mặc đồ kín đáo để thể hiện sự tôn trọng.\r\n\r\nTìm hiểu trước: Nên đọc qua một chút về \"Văn hóa Óc Eo\" để khi nhìn những nền gạch cũ ở Ngày 1, bạn sẽ thấy chúng \"biết nói\" và hấp dẫn hơn nhiều.', 'Xe du lịch', NULL),
(9, 'Tour Cù Lao Ông Chưởng Tôm Cá', 'An Giang, Việt Nam', 4500000.00, 2, '2026-03-08', 'https://media.mia.vn/uploads/blog-du-lich/cu-lao-ong-chuong-kham-pha-khu-du-lich-sinh-thai-cho-moi-an-giang-5-1660673506-1660790674.jpeg', 'TP. HCM', 'Cù Lao Riverside Lodge', 0, 'Ngày 1: Câu Cá và Hái Trái Cây Trong Vườn\r\nBuổi sáng, du khách khởi hành đến khu du lịch sinh thái, nơi có không gian xanh mát và những ao cá rộng lớn. Sau khi ổn định, hướng dẫn viên phổ biến lịch trình và phát cần câu để du khách tự tay trải nghiệm hoạt động câu cá. Đây là hoạt động thú vị, mang lại cảm giác thư giãn giữa không gian yên bình của miền quê.\r\nBuổi trưa, đoàn thưởng thức bữa ăn dân dã được chế biến từ chính những con cá vừa câu được, kết hợp với các món đặc sản miền sông nước. Sau bữa trưa, du khách có thời gian nghỉ ngơi ngắn tại khu nhà chòi trong vườn cây.\r\nBuổi chiều, chương trình tiếp tục với hoạt động hái trái cây tại vườn. Du khách được tự tay hái và thưởng thức các loại trái cây chín mọng như xoài, mận, chôm chôm, hoặc sầu riêng (tùy theo mùa). Đây là cơ hội để gần gũi hơn với thiên nhiên và cảm nhận nét bình dị của cuộc sống miệt vườn.\r\nBuổi tối, đoàn trở về khu nghỉ, cùng nhau thưởng thức bữa tối ấm cúng và tham gia các hoạt động tự do như đốt lửa trại, ca hát hoặc dạo quanh khu vực nghỉ dưỡng.\r\n\r\nNgày 2: Thăm Làng Mộc Thủ và Ẩm Thực Địa Phương\r\nBuổi sáng, du khách di chuyển đến làng nghề mộc Thủ, một trong những làng nghề truyền thống nổi tiếng với những sản phẩm thủ công tinh xảo. Tại đây, du khách được tìm hiểu quy trình làm mộc thủ công, từ khâu chọn gỗ, chạm khắc đến hoàn thiện sản phẩm. Du khách có thể giao lưu với các nghệ nhân, tận mắt chứng kiến kỹ thuật chạm khắc tinh tế và mua sắm các sản phẩm lưu niệm độc đáo.\r\nBuổi trưa, đoàn thưởng thức bữa ăn đặc sản địa phương với các món dân dã mang hương vị truyền thống như cá kho tộ, canh chua, bánh xèo hoặc bánh cống.\r\nBuổi chiều, sau khi nghỉ ngơi, du khách có thể tự do dạo quanh khu vực, chụp ảnh kỷ niệm hoặc mua quà đặc sản về làm quà.\r\nCuối buổi chiều, đoàn khởi hành trở về điểm xuất phát, kết thúc chuyến đi với nhiều trải nghiệm đáng nhớ và ấn tượng đẹp về cuộc sống làng quê yên bình.', 'Xe du lịch', NULL),
(10, 'Tour Chùa Lầu Kiến Trúc Độc Đáo', 'An Giang, Việt Nam', 3000000.00, 2, '2026-03-08', 'https://datviettour.com.vn/uploads/images/tin-tuc-SEO/mien-nam/An-Giang/danh-thang/chua-lau-an-giang.jpg', 'TP. HCM', 'Lầu Temple Inn', 0, 'Ngày 1: Check-in Mái Ngói Màu Sắc\r\nBuổi sáng, du khách khởi hành đến khu du lịch nổi tiếng với những mái ngói nhiều màu sắc rực rỡ. Đây là địa điểm lý tưởng để chụp ảnh và lưu giữ những khoảnh khắc đáng nhớ. Không gian nơi đây mang đậm nét kiến trúc truyền thống pha lẫn hiện đại, tạo nên vẻ đẹp vừa cổ kính vừa sinh động.\r\nBuổi trưa, đoàn thưởng thức bữa ăn đặc sản địa phương tại nhà hàng gần khu tham quan, sau đó nghỉ ngơi tại khách sạn.\r\nBuổi chiều, du khách có thể tự do dạo quanh khu vực, tận hưởng không khí trong lành, tham quan các cửa hàng lưu niệm hoặc trải nghiệm văn hóa địa phương qua các hoạt động thủ công mỹ nghệ.\r\nBuổi tối, cả đoàn cùng nhau dùng bữa tối, tham gia các hoạt động giao lưu hoặc thưởng thức các món ăn đường phố đặc trưng của vùng.\r\n\r\nNgày 2: Tham Quan Bảo Tháp Cổ và Cây Cổ Thụ\r\nBuổi sáng, du khách di chuyển đến khu di tích với bảo tháp cổ hàng trăm năm tuổi. Đây là công trình mang giá trị lịch sử và tâm linh sâu sắc, được xây dựng từ thời xưa với kiến trúc tinh xảo và không gian tĩnh lặng. Hướng dẫn viên sẽ giới thiệu chi tiết về lịch sử hình thành, ý nghĩa văn hóa, cũng như những câu chuyện gắn liền với bảo tháp.\r\nSau khi tham quan bảo tháp, đoàn tiếp tục đến thăm cây cổ thụ nổi tiếng, được xem là “chứng nhân lịch sử” của vùng. Với tán lá rộng lớn và thân cây to đồ sộ, nơi đây không chỉ là điểm tham quan mà còn là địa điểm lý tưởng để nghỉ chân, chụp hình lưu niệm và cảm nhận vẻ đẹp thiên nhiên bình yên.\r\nBuổi trưa, đoàn dùng bữa tại nhà hàng địa phương, sau đó nghỉ ngơi và chuẩn bị cho hành trình trở về.\r\nBuổi chiều, xe đưa đoàn khởi hành về điểm xuất phát, kết thúc chuyến tham quan với nhiều trải nghiệm thú vị và kỷ niệm đáng nhớ.', 'Xe du lịch', NULL),
(11, 'Tour Núi Sập - Hồ Ông Thoại', 'An Giang, Việt Nam', 4200000.00, 3, '2026-03-08', 'https://th.bing.com/th/id/OIP.0lyBoJ_5w05qM8mnb-6O1wHaE7?o=7rm=3&rs=1&pid=ImgDetMain&o=7&rm=3', 'TP. HCM', 'Sập Lake Resort', 0, 'Chào bạn! Một kế hoạch khám phá Núi Sập (Thoại Sơn, An Giang) thật nhẹ nhàng và thong thả. Núi Sập không chỉ có cảnh quan sơn thủy hữu tình mà còn gắn liền với lịch sử khai hoang của danh tướng Thoại Ngọc Hầu.\r\n\r\nDưới đây là lịch trình chi tiết 3 ngày để bạn tận hưởng trọn vẹn vẻ đẹp nơi này:\r\n\r\nNgày 1: Chinh phục đỉnh Núi Sập & Ngắm nhìn lòng hồ từ trên cao\r\nTrọng tâm: Hoạt động leo núi và thu trọn tầm mắt toàn cảnh Thoại Sơn.\r\n\r\nSáng (08:00): Bắt đầu hành trình leo Núi Sập (còn gọi là Thoại Sơn). Bạn có thể chọn đi bộ theo đường mòn để vận động hoặc đi xe máy theo đường nhựa lên đỉnh.\r\n\r\n09:30: Tham quan Chùa cổ trên núi và các am miếu nhỏ ẩn mình dưới tán cây rừng. Không khí trên cao rất trong lành và tĩnh mịch.\r\n\r\n10:30: Dừng chân tại các điểm dừng chân ven đường. Từ đây, bạn có thể ngắm toàn cảnh Hồ Ông Thoại xanh ngắt và những cánh đồng lúa bạt ngàn của huyện Thoại Sơn.\r\n\r\nTrưa: Thưởng thức bữa trưa với các món dân dã như gà thả vườn nướng hoặc canh chua tại các quán ăn dưới chân núi.\r\n\r\nChiều: Xuống núi, dạo quanh công viên ven hồ để cảm nhận nhịp sống chậm rãi của thị trấn.\r\n\r\nNgày 2: Hành trình di sản – Đền thờ Thoại Ngọc Hầu\r\nTrọng tâm: Tìm hiểu lịch sử và lòng biết ơn bậc tiền nhân.\r\n\r\nSáng (09:00): Viếng Đình thần Thoại Ngọc Hầu. Đây là nơi thờ vị danh tướng có công lớn trong việc chỉ huy đào kênh Thoại Hà, mở mang vùng đất này.\r\n\r\n10:00: Chiêm ngưỡng Bia đá Thoại Sơn – di sản quý giá ghi dấu công trạng đào kênh. Đây là bản gốc do chính Thoại Ngọc Hầu dựng lên vào năm 1822.\r\n\r\nTrưa: Dùng bữa với đặc sản địa phương: Lẩu mắm hoặc cá lóc đồng kho tộ.\r\n\r\nChiều: Tham quan Thiền viện Trúc Lâm An Giang (cách núi Sập không xa). Ngôi thiền viện có kiến trúc tuyệt đẹp hướng ra mặt hồ mênh mông, là nơi lý tưởng để tĩnh tâm và chụp ảnh kiến trúc.\r\n\r\nTối: Thưởng thức các món ăn vặt tại khu vực chợ đêm gần hồ Núi Sập.\r\n\r\nNgày 3: Picnic bên hồ – Thư giãn giữa thiên nhiên\r\nTrọng tâm: Tận hưởng không gian xanh và gắn kết bạn bè/gia đình.\r\n\r\nSáng (08:30): Di chuyển vào khu du lịch Hồ Ông Thoại. Đây là những lòng hồ nhân tạo nằm dưới chân núi với những khối đá nhô lên mặt nước như những hòn đảo nhỏ.\r\n\r\n09:00: Thuê một góc mát ven hồ hoặc các chòi lá. Bắt đầu buổi Picnic.\r\n\r\nChuẩn bị: Bạn nên mang theo đồ ăn nhẹ, trái cây (như thốt nốt, xoài) và đồ uống.\r\n\r\n10:30: Tham gia các hoạt động vui chơi: Đạp vịt (pedalo) trên hồ, đi xuồng máy len lỏi qua các khe đá hoặc đi cầu sắt bắc ngang lòng hồ để chụp ảnh.\r\n\r\nTrưa: Thưởng thức bữa trưa picnic ngoài trời giữa không gian gió lộng.\r\n\r\n15:00: Dọn dẹp vệ sinh khu vực picnic. Ghé mua một ít Đường thốt nốt hoặc Khô cá sặc đặc sản Thoại Sơn về làm quà trước khi kết thúc hành trình.\r\n\r\n💡 Lưu ý cho bạn:\r\nPicnic: Nếu bạn muốn picnic thoải mái, hãy đi sớm để chọn được vị trí dưới bóng cây cổ thụ ven hồ. Đừng quên mang theo tấm trải và túi đựng rác nhé!\r\n\r\nTrang phục: Ngày 1 leo núi nên mặc đồ thể thao, ngày 2 viếng đền thờ nên mặc trang phục lịch sự.\r\n\r\nThời điểm: Hồ Núi Sập đẹp nhất vào những ngày nắng ráo, nước hồ sẽ có màu xanh ngọc bích rất đẹp.', 'Xe du lịch', NULL),
(12, 'Tour Thốt Nốt Trái Tim Check-in', 'An Giang, Việt Nam', 2800000.00, 2, '2026-04-14', 'https://luhanhvietnam.com.vn/du-lich/vnt_upload/news/03_2024/cay_thot_not_trai_tim_an_giang_thamhiemmekong_2.jpg', 'TP. HCM', 'Thốt Nốt Village', 1, 'Chào bạn! Lịch trình của bạn tuy ngắn gọn nhưng lại đánh trúng \"linh hồn\" của vùng đất Tri Tôn (An Giang) – nơi được mệnh danh là xứ sở của những hàng thốt nốt huyền thoại.\r\n\r\nĐể bạn có những bức ảnh lung linh nhất và thưởng thức được hương vị tinh túy nhất, mình xin chi tiết hóa lịch trình như sau:\r\n\r\nNgày 1: Săn ảnh \"Trái tim vùng Bảy Núi\"\r\nTrọng tâm: Canh thời điểm vàng để có những bức ảnh để đời.\r\n\r\nSáng (07:00 - 09:00): Check-in Cây thốt nốt trái tim (xã An Tức).\r\n\r\nĐây là thời điểm ánh sáng dịu, nắng không quá gắt, giúp màu xanh của đồng lúa và hình dáng \"trái tim\" của cụm thốt nốt hiện lên rõ nét nhất.\r\n\r\nMẹo chụp ảnh: Hãy thử góc chụp từ xa để lấy được toàn cảnh sự kỳ vĩ của hàng cây giữa cánh đồng mênh mông. Nếu đi vào mùa lúa chín, khung cảnh sẽ vàng rực rỡ; nếu mùa nước nổi, hình ảnh phản chiếu dưới nước sẽ cực kỳ ảo diệu.\r\n\r\nTrưa: Ghé thăm Chùa Koh Kas (Cổng trời Tri Tôn) ngay gần đó. Chiếc cổng chùa đứng biệt lập giữa đồng lúa cũng là một \"background\" không thể bỏ qua.\r\n\r\nChiều: Di chuyển về phía Cánh đồng Tà Pạ. Từ trên cao nhìn xuống, bạn sẽ thấy những hàng thốt nốt vươn cao, soi bóng xuống những ô ruộng nhiều màu sắc như một bảng màu vẽ.\r\n\r\nTối: Nghỉ ngơi tại trung tâm thị trấn Tri Tôn, thưởng thức món Lạp xưởng bò (Tung lò mò) nướng thơm nức mũi.\r\n\r\nNgày 2: Hương vị ngọt ngào từ \"Nước trời cho\"\r\nTrọng tâm: Trải nghiệm thực tế quy trình làm đường và thưởng thức tại vườn.\r\n\r\nSáng (08:30): Khám phá vườn thốt nốt & Xem nghệ nhân leo cây.\r\n\r\nHãy tìm đến những khu vườn của người Khmer tại xã Châu Lăng hoặc ven núi Phnom Pi.\r\n\r\nTrải nghiệm: Tận mắt xem những người đàn ông Khmer thoăn thoắt leo lên ngọn cây cao vút để lấy nước thốt nốt từ vòi hoa. Bạn có thể xin thưởng thức ngay những giọt nước thốt nốt vừa mang xuống – ngọt thanh, mát lịm và thơm mùi sáp đặc trưng.\r\n\r\n10:30: Tham quan lò nấu đường thủ công.\r\n\r\nNgồi bên bếp lửa hồng, xem người dân khuấy những chảo mật thốt nốt vàng óng. Bạn sẽ được thử món Đường thốt nốt mới ra lò dẻo quẹo, béo ngậy.\r\n\r\nTrưa: Thưởng thức bữa trưa với món Gà đốt Ô Thum (lá chúc là linh hồn của món này) tại khu vực hồ Ô Thum gần đó.\r\n\r\nChiều: Thưởng thức đặc sản giải nhiệt.\r\n\r\nĐừng quên gọi một ly Thốt nốt lạnh với những lát cùi thốt nốt trắng ngần, giòn sần sật.\r\n\r\nMua quà mang về: Bánh bò thốt nốt nướng (vàng ươm, thơm phức) và những hũ đường thốt nốt nguyên chất.\r\n\r\n💡 Một vài lưu ý nhỏ từ \"peer\" du lịch:\r\nThời tiết: An Giang nắng rất gắt, bạn nhớ trang bị kem chống nắng và nón lá/nón rộng vành nhé (vừa che nắng vừa làm đạo cụ chụp ảnh rất hợp).\r\n\r\nSự tôn trọng: Khi vào vườn thốt nốt hoặc các lò đường của người dân, hãy xin phép trước. Người Khmer rất hiếu khách, đôi khi họ còn tặng bạn dùng thử vài quả thốt nốt miễn phí nữa đấy!\r\n\r\nCẩn thận: Cây thốt nốt trái tim nằm giữa đồng, đường đi có thể hơi bùn đất nếu gặp mưa, bạn nên đi giày có độ bám tốt.', 'Xe du lịch', NULL),
(13, 'Tour Chùa Hang Huyền Thoại Rắn', 'An Giang, Việt Nam', 3200000.00, 3, '2026-03-08', 'https://ik.imagekit.io/tvlk/blog/2023/05/chua-hang-8.jpg?tr=dpr-2,w-675', 'TP. HCM', 'Hang Cave Lodge', 0, 'Chào bạn! Một hành trình khám phá các hang động huyền bí và nghe những truyền thuyết li kỳ tại vùng Thất Sơn (An Giang) chắc chắn sẽ mang lại cho bạn cảm giác phiêu lưu cực kỳ phấn khích.\r\n\r\nĐịa điểm lý tưởng nhất cho lịch trình này chính là Núi Cấm kết hợp với khu vực Ba Chúc (Tri Tôn), nơi nổi tiếng với hệ thống hang động sâu thẳm và những câu chuyện \"mãng xà\" truyền đời.\r\n\r\nNgày 1: Thế Giới Ngầm & Tiếng Reo Của Suối\r\nTrọng tâm: Khám phá địa đạo đá tự nhiên và tận hưởng làn nước mát lành.\r\n\r\nSáng (08:30): Khám phá Hang Ông Thẻ (Núi Cấm).\r\n\r\nĐây là hệ thống hang động hẹp, luồn lách qua những khối đá khổng lồ. Cảm giác đi giữa những khe đá tối, chỉ có ánh đèn pin soi rọi sẽ khiến bạn thấy mình như một nhà thám hiểm thực thụ.\r\n\r\n10:30: Thăm Hang Bác Vật Lang.\r\n\r\nMột hang động nổi tiếng gắn liền với những câu chuyện về các bậc tu hành ẩn dật. Không gian bên trong rất mát mẻ và mang màu sắc tâm linh huyền bí.\r\n\r\nTrưa: Dùng bữa với Bánh xèo rau rừng ngay chân núi để nạp năng lượng.\r\n\r\nChiều (14:30): Giải nhiệt tại Suối Thanh Long.\r\n\r\nDòng suối này bắt nguồn từ đỉnh núi, len lỏi qua các khe đá tạo thành những tầng thác nhỏ. Bạn có thể tắm suối hoặc ngồi trên phiến đá, nghe tiếng nước chảy giữa rừng già để rũ bỏ mọi mệt nhọc.\r\n\r\nNgày 2: Truyền Thuyết Mãng Xà & Kỳ Bí Vùng Biên\r\nTrọng tâm: Những câu chuyện kỳ ảo gắn liền với địa danh thực tế.\r\n\r\nSáng (09:00): Viếng Chùa Phi Lai & Chùa Tam Bửu (Ba Chúc).\r\n\r\nSau khi chiêm bái, bạn hãy tìm gặp các vị bô lão hoặc người dân địa phương để nghe kể về truyền thuyết Rắn khổng lồ (Mãng xà) thường xuất hiện ở các vồ đá lớn vùng Bảy Núi.\r\n\r\nNhững câu chuyện về \"Rắn thần\" giữ núi hay những dấu chân khổng lồ trên đá sẽ khiến bạn không khỏi rùng mình thích thú.\r\n\r\nTrưa: Thưởng thức đặc sản Bò bảy món vùng núi Sam/Tri Tôn.\r\n\r\nChiều (15:00): Thăm Đồi Tức Dụp (Ngọn núi có 2 triệu đô la).\r\n\r\nKhám phá hệ thống hang động chằng chịt bên trong ngọn đồi này. Tương truyền, những hang động sâu thẳm nơi đây từng là nơi trú ẩn của những sinh vật khổng lồ trong các tích xưa của người Khmer.\r\n\r\nNgày 3: Trekking Chinh Phục \"Nóc Nhà Miền Tây\"\r\nTrọng tâm: Thử thách sức bền và thu trọn tầm mắt vùng Thất Sơn.\r\n\r\nSáng (07:00): Trekking đỉnh núi Cấm.\r\n\r\nThay vì đi cáp treo, bạn hãy chọn cung đường bộ trekking để cảm nhận hết vẻ đẹp của rừng núi.\r\n\r\nMục tiêu là chinh phục Vồ Bồ Hong – đỉnh cao nhất (710m). Đây là nơi gió thổi lồng lộng, bạn có thể nhìn thấy toàn cảnh cánh đồng biên giới Việt - Cam bạt ngàn.\r\n\r\nTrưa: Dùng cơm lam ống tre và gà đồi nướng ngay trên đỉnh núi.\r\n\r\nChiều (14:00): Xuống núi bằng đường mòn phía Hồ Thủy Liêm.\r\n\r\nĐoạn đường này cảnh sắc rất thơ mộng với những vườn dâu da đất và sầu riêng núi sai trĩu quả.\r\n\r\n16:30: Kết thúc hành trình, mua một ít mật ong rừng hoặc trà sâm núi về làm quà.\r\n\r\n💡 Bí kíp cho \"nhà thám hiểm\":\r\nTrang bị: Bắt buộc có đèn pin siêu sáng cho Ngày 1, giày trekking có độ bám cực tốt và thuốc chống vắt/muỗi rừng.\r\n\r\nNgười dẫn đường: Với các hang động sâu hoặc cung đường trekking hẻo lánh, bạn nên thuê một người dân địa phương dẫn đường để đảm bảo an toàn và được nghe \"kể chuyện sống động\" nhất.\r\n\r\nSức khỏe: Lịch trình này khá tốn sức, hãy đảm bảo bạn có đôi chân khỏe và đừng quên mang theo nước lọc pha muối khoáng nhé!', 'Xe du lịch', NULL),
(14, 'Tour Nhà Mồ Ba Chúc Lịch Sử', 'An Giang, Việt Nam', 3800000.00, 3, '2026-03-08', 'https://thamhiemmekong.com/wp-content/uploads/2020/09/chuatambuu.jpg', 'TP. HCM', 'Ba Chúc Memorial Hotel', 0, 'Chào bạn, đây là một hành trình đi sâu vào lịch sử đầy suy ngẫm và tâm linh tại vùng đất Ba Chúc (Tri Tôn, An Giang). Lịch trình này không chỉ là tham quan, mà còn là một chuyến đi để thấu hiểu những đau thương và sự kiên cường của người dân vùng biên giới.\r\n\r\nDưới đây là lịch trình chi tiết:\r\n\r\nNgày 1: Đối diện lịch sử - Khu di tích Thảm sát Ba Chúc\r\nTrọng tâm: Tìm hiểu về biến cố lịch sử đau thương và tưởng niệm những người đã khuất.\r\n\r\nSáng (09:00): Tham quan Nhà mồ Ba Chúc.\r\n\r\nĐây là nơi lưu giữ hài cốt của hàng ngàn nạn nhân trong vụ thảm sát năm 1978. Kiến trúc nhà mồ hình hoa sen trắng cách điệu mang ý nghĩa siêu thoát.\r\n\r\nTrải nghiệm: Bạn sẽ được tận mắt chứng kiến những bằng chứng lịch sử qua các tủ kính lưu giữ di cốt, được phân loại theo độ tuổi và giới tính. Không gian này vô cùng trang nghiêm, hãy giữ sự tĩnh lặng tuyệt đối.\r\n\r\nTrưa: Dùng bữa trưa đơn giản tại các quán ăn địa phương gần khu vực di tích. Thử món Bánh canh bột xắt hoặc Bún cá Tri Tôn.\r\n\r\nChiều (14:30): Thăm các điểm di tích phụ cận.\r\n\r\nGhé thăm các địa danh gắn liền với cuộc thảm sát như Cầu Sắt Vĩnh Thông (nơi ghi dấu những câu chuyện bi hùng).\r\n\r\nTrò chuyện với những nhân chứng sống hoặc người trông coi di tích để nghe kể về những ngày tháng kinh hoàng nhưng cũng đầy tình người trong cơn hoạn nạn.\r\n\r\nTối: Nghỉ ngơi tại khu vực Tri Tôn. Không khí buổi đêm ở đây rất tĩnh lặng, phù hợp để chiêm nghiệm.\r\n\r\nNgày 2: Học về lịch sử Khmer Đỏ & Biên giới Tây Nam\r\nTrọng tâm: Hệ thống lại kiến thức lịch sử và bối cảnh chính trị thời bấy giờ.\r\n\r\nSáng (08:30): Tìm hiểu tại Phòng trưng bày hiện vật.\r\n\r\nNằm ngay trong khuôn viên khu di tích Ba Chúc. Tại đây lưu giữ những hình ảnh, hiện vật và tư liệu về tội ác của quân Pol Pot (Khmer Đỏ).\r\n\r\nHọc lịch sử: Tìm hiểu về nguyên nhân cuộc chiến tranh biên giới Tây Nam và sự giúp đỡ của quân tình nguyện Việt Nam đối với nhân dân Campuchia.\r\n\r\nTrưa: Thưởng thức món Lạp xưởng bò (Tung lò mò) nướng – món ăn giao thoa văn hóa giữa người Kinh, Chăm và Khmer tại vùng biên.\r\n\r\nChiều (14:00): Chuyến đi thực tế dọc kênh Vĩnh Tế.\r\n\r\nDi chuyển dọc theo con kênh biên giới này để hình dung về địa thế quân sự và tầm quan trọng của việc bảo vệ biên cương.\r\n\r\nGhé thăm các cột mốc biên giới (nếu điều kiện cho phép) để cảm nhận rõ ranh giới giữa hai quốc gia.\r\n\r\nNgày 3: Sự an nhiên tại Chùa Phi Lai & Chùa Tam Bửu\r\nTrọng tâm: Tìm lại sự bình yên trong tâm hồn sau hai ngày tìm hiểu về lịch sử đau thương.\r\n\r\nSáng (08:00): Viếng Chùa Phi Lai.\r\n\r\nNằm sát bên khu di tích nhà mồ, chùa Phi Lai là nơi hàng ngàn người dân đã từng trú ẩn trong chiến tranh. Ngôi chùa có kiến trúc cổ kính với mái ngói rêu phong và khuôn viên tràn ngập cây xanh.\r\n\r\nTrải nghiệm: Hãy dành thời gian ngồi dưới những gốc bồ đề, lắng nghe tiếng chuông chùa để cảm nhận sự đối lập giữa quá khứ đau thương và hiện tại thanh bình.\r\n\r\n10:30: Thăm Chùa Tam Bửu.\r\n\r\nĐây là tổ đình của đạo Tứ Ân Hiếu Nghĩa, ngôi chùa mang đậm nét kiến trúc dân gian Nam Bộ và gắn liền với công cuộc khai khẩn vùng đất này.\r\n\r\nTrưa: Dùng bữa trưa với món chay tại chùa hoặc thưởng thức Đu đủ đâm và Nước thốt nốt tại chợ Ba Chúc.\r\n\r\nChiều: Mua sắm đặc sản: Đường thốt nốt, Khô bò, hoặc các loại thuốc nam từ núi rừng Thất Sơn trước khi kết thúc hành trình.\r\n\r\n💡 Một vài lưu ý cho hành trình này:\r\nTâm thế: Đây là một chuyến đi về nguồn mang tính chất lịch sử và tâm linh, bạn nên chuẩn bị tâm lý vững vàng và ăn mặc lịch sự, kín đáo khi vào nhà mồ và các ngôi chùa.\r\n\r\nChụp ảnh: Tại nhà mồ và khu trưng bày hài cốt, hãy hỏi ý kiến người quản lý trước khi quay phim, chụp ảnh để thể hiện sự tôn trọng với người đã khuất.\r\n\r\nHỏi chuyện: Đừng ngại đặt câu hỏi cho những người bản địa lớn tuổi, họ là những \"pho sử sống\" tuyệt vời nhất mà không sách vở nào thay thế được.', 'Xe du lịch', NULL),
(15, 'Tour Chợ Biên Giới Tịnh Biên', 'An Giang, Việt Nam', 3000000.00, 2, '2026-03-08', 'https://th.bing.com/th/id/R.ee8aaa6fb50bc92ad53e43a39e1fb31e?rik=2mh%2frFJ9stX4bA&pid=ImgRaw&r=0', 'TP. HCM', 'Border Market Inn', 0, 'Ngày 1: Mua sắm hàng Thái và đặc sản Châu Đốc\r\n\r\nBuổi sáng:\r\n\r\nXe và hướng dẫn viên đón quý khách tại điểm hẹn, khởi hành đến thành phố Châu Đốc – trung tâm giao thương sôi động của vùng biên giới Tây Nam.\r\n\r\nTrên đường đi, quý khách nghe thuyết minh về lịch sử vùng đất An Giang, nơi giao thoa văn hóa giữa Việt Nam – Campuchia – Thái Lan.\r\n\r\nĐến nơi, đoàn tham quan chợ biên giới Tịnh Biên hoặc chợ Châu Đốc, nơi nổi tiếng với hàng Thái Lan giá rẻ và các sản phẩm nhập khẩu phong phú như mỹ phẩm, quần áo, đồ gia dụng, bánh kẹo,…\r\n\r\nBuổi trưa:\r\n\r\nDùng bữa trưa với các món đặc sản miền Tây: lẩu mắm, cá basa kho tộ, cá linh chiên giòn, rau đồng quê.\r\n\r\nNhận phòng khách sạn nghỉ ngơi.\r\n\r\nBuổi chiều:\r\n\r\nTiếp tục mua sắm đặc sản địa phương như mắm Châu Đốc, khô cá sặc, đường thốt nốt, muối tôm, bánh bò thốt nốt,… tại các cửa hàng uy tín quanh khu vực núi Sam.\r\n\r\nTự do dạo phố, thưởng thức các món ăn vặt hấp dẫn: chè thốt nốt, bánh xèo, bún cá Châu Đốc, bún kèn.\r\n\r\nBuổi tối:\r\n\r\nDùng cơm tối, sau đó quý khách có thể tản bộ quanh khu vực chợ đêm Núi Sam hoặc thưởng thức cà phê ven sông Hậu.\r\n\r\nNghỉ đêm tại khách sạn.\r\n\r\nNgày 2: Dạo kênh Vĩnh Tế – Khám phá vùng sông nước biên giới\r\n\r\nBuổi sáng:\r\n\r\nĂn sáng tại khách sạn.\r\n\r\nXe đưa đoàn đến kênh Vĩnh Tế – công trình thủy lợi lịch sử dài hơn 80 km, được đào từ thời Nguyễn, nối liền Châu Đốc với Hà Tiên.\r\n\r\nQuý khách lên thuyền dạo kênh, chiêm ngưỡng cảnh sông nước yên bình, làng chài ven bờ và cuộc sống giản dị của người dân vùng biên giới.\r\n\r\nDừng chân tham quan một vài làng nghề truyền thống như làm mắm, đan rổ, hoặc làng người Chăm ven kênh để tìm hiểu văn hóa, phong tục đặc sắc.\r\n\r\nBuổi trưa:\r\n\r\nThưởng thức bữa trưa với các món dân dã miền Tây: cá lóc nướng trui, canh chua bông điên điển, gà hấp rượu, cơm trắng thơm lúa mới.\r\n\r\nBuổi chiều:\r\n\r\nTrả phòng khách sạn, khởi hành về lại điểm đón ban đầu.\r\n\r\nKết thúc chương trình, chia tay đoàn và hẹn gặp lại quý khách trong chuyến đi sau.', 'Xe du lịch', NULL),
(16, 'Tour Nhà Thờ Cù Lao Giêng Cổ', 'An Giang, Việt Nam', 4500000.00, 2, '2026-03-08', 'https://photo-baomoi.bmcdn.me/w700_r1/2023_11_14_465_47529343/3347fa2eef62063c5f73.jpg', 'TP. HCM', 'Cù Lao Giêng Retreat', 0, 'Chào bạn! Một hành trình khám phá nét đẹp tôn giáo và văn hóa tại Cù Lao Giêng (Chợ Mới, An Giang) là một lựa chọn cực kỳ tinh tế. Đây là nơi được mệnh danh là \"xứ sở của những nhà thờ cổ\" với không gian bình yên tách biệt hoàn toàn với sự ồn ào phố thị.\r\n\r\nDưới đây là lịch trình chi tiết cho chuyến đi của bạn:\r\n\r\nNgày 1: Hành trình Di sản – Thánh đường cổ nhất Nam Kỳ\r\nTrọng tâm: Chiêm ngưỡng kiến trúc phương Tây giữa lòng miền Tây sông nước.\r\n\r\nSáng (09:00): Tham quan Nhà thờ Cù Lao Giêng (Thánh đường Đầu Nước).\r\n\r\nĐây chính là ngôi thánh đường cổ nhất Nam Kỳ, được xây dựng từ năm 1877 theo kiến trúc Gothic Pháp.\r\n\r\nTrải nghiệm: Hãy dành thời gian ngắm nhìn tháp chuông cao vút, những ô cửa kính màu tinh xảo và hàng cột đá kiên cố đã đứng vững hơn một thế kỷ. Không gian bên trong rất uy nghiêm và lộng lẫy, mang lại cảm giác như đang đứng giữa lòng nước Pháp.\r\n\r\n11:00: Ghé thăm Tu viện Chúa Quan Phòng.\r\n\r\nNằm ngay gần nhà thờ, đây là một quần thể kiến trúc đẹp mắt với những dãy hành lang dài và vườn hoa tĩnh mịch. Bạn sẽ cảm nhận được sự tận tụy của các sơ qua các hoạt động từ thiện và chăm sóc trẻ em tại đây.\r\n\r\nTrưa: Thưởng thức cơm quê cù lao với các món cá linh, lẩu mắm hoặc cá lóc đồng kho tộ.\r\n\r\nChiều: Tham quan Thành Hoa Thôn (Phủ thờ dòng họ Lê) – một ngôi nhà cổ mang đậm kiến trúc phương Đông để thấy sự giao thoa văn hóa thú vị trên hòn cù lao này.\r\n\r\nTối: Nghỉ đêm tại các homestay ven sông để tận hưởng làn gió mát từ sông Tiền.\r\n\r\nNgày 2: Sắc màu Phố đạo – Dạo cù lao & Làng Công giáo\r\nTrọng tâm: Hòa mình vào đời sống thanh bình của người dân và các làng nghề.\r\n\r\nSáng (08:00): Dạo quanh các con đường làng Công giáo.\r\n\r\nBạn nên thuê xe đạp hoặc xe máy để thong dong trên những con đường nhỏ rợp bóng cây. Ở đây, hầu như trước nhà mỗi gia đình đều có tượng Chúa hoặc thánh giá, tạo nên một không gian \"phố đạo\" rất đặc thù.\r\n\r\n09:30: Tham quan Nhà thờ Phanxicô.\r\n\r\nMột ngôi nhà thờ khác với kiến trúc nhẹ nhàng hơn nhưng vẫn mang đậm dấu ấn lịch sử. Quần thể này bao gồm cả khu mộ của các vị linh mục đã có công khai phá vùng đất này.\r\n\r\n10:30: Khám phá làng nghề thủ công.\r\n\r\nGhé thăm các hộ dân làm lưới, đóng ghe xuồng hoặc đan lát. Người dân Cù Lao Giêng rất hiếu khách, bạn có thể xin thử tay nghề và trò chuyện để hiểu thêm về đời sống \"cha truyền con nối\".\r\n\r\nTrưa: Thưởng thức đặc sản Bánh canh ngọt hoặc các loại bánh dân gian được bày bán tại chợ Cù Lao Giêng.\r\n\r\nChiều: Ghé thăm Chùa Thành Hoa (ngôi chùa có kiến trúc độc đáo với các bức tượng Phật lớn) trước khi lên phà rời cù lao.\r\n\r\n16:00: Kết thúc hành trình, mua một ít trái cây đặc sản (xoài tượng, nhãn) ngay tại vườn của người dân về làm quà.\r\n\r\n💡 Bí kíp nhỏ dành cho bạn:\r\nTrang phục: Vì tham quan các điểm tôn giáo lâu đời, bạn nên mặc trang phục kín đáo, lịch sự.\r\n\r\nDi chuyển: Cù Lao Giêng gồm 3 xã (Tấn Mỹ, Mỹ Hiệp, Bình Phước), các điểm tham quan nằm khá gần nhau nên việc đạp xe dạo quanh là trải nghiệm tuyệt vời nhất.\r\n\r\nThời điểm: Nếu đi vào các dịp lễ Công giáo (như Noel hoặc lễ Bổn mạng), cù lao sẽ được trang hoàng cực kỳ lộng lẫy và nhộn nhịp.', 'Xe du lịch', NULL),
(17, 'Tour Đồi Tức Dụp Mây Trời', 'An Giang, Việt Nam', 4200000.00, 3, '2026-03-08', 'https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2023/4/28/1185956/Tuc-Dup-Tu-Ngon-Doi--04.jpg', 'TP. HCM', 'Tức Dụp Hill Camp', 0, 'Ngày 1: Leo đồi ngắm mây. Ngày 2: Cắm trại và ngắm bình minh. Ngày 3: Khám phá hang động.', 'Xe du lịch', NULL),
(18, 'Tour Chùa Xà Tón Kinh Lá', 'An Giang, Việt Nam', 3000000.00, 2, '2026-03-08', 'https://th.bing.com/th/id/OIP.idgRis6zFgkzf9cmggFK3wHaEK?o=7rm=3&rs=1&pid=ImgDetMain&o=7&rm=3', 'TP. HCM', 'Xvayton Temple Stay', 0, 'Ngày 1: Xem sách kinh lá cổ. Ngày 2: Kiến trúc Khmer. Ngày 3: Thăm ruộng lúa.', 'Xe du lịch', NULL),
(19, 'Tour Masjid Jamiul Azhar Hồi Giáo', 'An Giang, Việt Nam', 3000000.00, 2, '2026-03-08', 'https://media.mia.vn/uploads/blog-du-lich/kham-pha-thanh-duong-hoi-giao-jamiul-azhar-mosque-tuyet-dep-o-an-giang-9-1660032589.jpg', 'TP. HCM', 'Azhar Mosque Guesthouse', 0, 'Ngày 1: Tham quan thánh đường Hồi giáo lâu đời. Ngày 2: Gặp cộng đồng Chăm và ẩm thực Halal.', 'Xe du lịch', NULL),
(20, 'Tour Hồ Soài So Hoang Sơ', 'An Giang, Việt Nam', 4800000.00, 2, '2026-03-08', 'https://res.klook.com/image/upload/fl_lossy.progressive,q_85/c_fill,w_1000/v1663662549/blog/o1czmhbzedzyhpphkpnc.webp', 'TP. HCM', 'Soài So Lake Homestay', 0, 'Ngày 1: Trải nghiệm kayak trên hồ\r\n\r\nBuổi sáng, xe và hướng dẫn viên đón khách tại điểm hẹn, khởi hành đến khu du lịch sinh thái Châu Đốc. Sau khi nhận phòng và nghỉ ngơi ngắn, du khách bắt đầu hành trình chèo thuyền kayak trên hồ nước trong xanh, cảm nhận không gian yên tĩnh giữa thiên nhiên miền sông nước.\r\n\r\nHướng dẫn viên hướng dẫn kỹ thuật cơ bản cho người mới, sau đó du khách có thể tự do khám phá các ngóc ngách quanh hồ, ngắm nhìn rừng tràm, bèo tây trôi lững lờ và đàn cá bơi ngay dưới chân thuyền. Đây là hoạt động giúp du khách thư giãn, tận hưởng sự trong lành và tĩnh lặng của miền Tây Nam Bộ.\r\n\r\nBuổi chiều, đoàn dùng cơm với các món ăn dân dã đặc trưng như cá lóc nướng trui, lẩu chua bông điên điển, rau đồng quê. Buổi tối, du khách có thể tham gia đốt lửa trại, giao lưu văn nghệ hoặc ngắm sao giữa không gian yên bình của vùng sông nước.\r\n\r\nNgày 2: Ngắm chim và khám phá thiên nhiên hoang dã\r\n\r\nBuổi sáng, du khách dậy sớm, lên thuyền tham quan khu bảo tồn sinh thái hoặc sân chim tự nhiên – nơi sinh sống của hàng trăm loài chim nước như cò, vạc, le le, điên điển, diệc… Hình ảnh đàn chim bay lượn trên nền trời trong xanh tạo nên khung cảnh tuyệt đẹp và yên bình.\r\n\r\nHướng dẫn viên giới thiệu về hệ sinh thái đa dạng của vùng đất ngập nước An Giang, với nhiều loài thực vật và động vật quý hiếm. Du khách có thể chụp ảnh, nghe tiếng chim hót, tận hưởng không khí mát lành buổi sáng.\r\n\r\nBuổi trưa, đoàn trở về khu du lịch dùng cơm, nghỉ ngơi, sau đó khởi hành về lại điểm đón ban đầu. Kết thúc chương trình và chia tay đoàn trong không khí vui vẻ.', 'Xe du lịch', NULL);
INSERT INTO `tours` (`TourID`, `TourName`, `Location`, `Price`, `DurationDays`, `StartDate`, `ImageUrl`, `DepartureLocation`, `HotelName`, `IsHot`, `Itinerary`, `Transportation`, `GuideID`) VALUES
(21, 'Tour Chùa Koh Kas Cổng Trời Khmer', 'An Giang, Việt Nam', 3000000.00, 3, '2026-03-08', 'https://th.bing.com/th/id/OIP.Ek3smLy-vBd0XigNbRB_wgHaEo?o=7rm=3&rs=1&pid=ImgDetMain&o=7&rm=3', 'TP. HCM', 'Koh Kas Retreat', 0, 'Chào bạn! Một lịch trình 3 ngày tập trung vào sự tĩnh lặng và vẻ đẹp văn hóa tại Tri Tôn (An Giang) là một lựa chọn tuyệt vời để \"chữa lành\" và nạp lại năng lượng.\r\n\r\nDưới đây là lịch trình chi tiết được thiết kế theo phong cách chậm rãi, thong dong:\r\n\r\nNgày 1: Chạm ngõ \"Cổng trời\" & Hoàng hôn vùng biên\r\nTrọng tâm: Những khung hình bình yên và sự tĩnh lặng đầu chuyến đi.\r\n\r\nSáng (09:00): Check-in Cổng trời Tri Tôn (Cổng chùa Koh Kas).\r\n\r\nĐịa điểm này nằm biệt lập giữa cánh đồng lúa bao la. Chiếc cổng mang kiến trúc Khmer hoa văn tinh xảo, đứng sừng sững giữa thiên nhiên tạo nên vẻ đẹp vừa kỳ bí vừa yên bình.\r\n\r\nMẹo chụp ảnh: Hãy chụp từ phía xa để lấy trọn con đường nhỏ dẫn vào cổng giữa cánh đồng xanh mướt.\r\n\r\nTrưa: Thưởng thức bữa trưa với món Gà đốt Ô Thum (lá chúc thơm nồng) ven hồ nước trong xanh.\r\n\r\nChiều (15:30): Dạo chơi quanh hồ Tà Pạ.\r\n\r\nNgắm nhìn \"Tuyệt Tình Cốc\" với mặt nước xanh ngọc bích. Đây là nơi bạn có thể ngồi yên trên những vách đá, hít thở không khí núi rừng.\r\n\r\nTối: Nghỉ ngơi tại một homestay mang phong cách địa phương, thưởng thức nước thốt nốt tươi.\r\n\r\nNgày 2: Sắc màu Ruộng lúa Khmer & Đời sống Phum Sóc\r\nTrọng tâm: Hòa mình vào nhịp sống nông nghiệp đặc trưng của người dân bản địa.\r\n\r\nSáng (07:30): Khám phá ruộng lúa Tà Pạ.\r\n\r\nNhững ô ruộng của người Khmer ở đây không bằng phẳng mà lồi lõm theo địa hình, được bao quanh bởi những hàng thốt nốt vươn cao.\r\n\r\nTrải nghiệm: Đi bộ trên những bờ ruộng, xem người dân chăm sóc lúa hoặc điều khiển đôi bò đi làm đồng. Sự giản dị này sẽ mang lại cảm giác rất nhẹ lòng.\r\n\r\nTrưa: Tìm hiểu văn hóa ẩm thực qua món Bún Num Bò Chóc hoặc Đu đủ đâm trứ danh của các dì, các chị người Khmer.\r\n\r\nChiều (14:30): Tham quan vườn thốt nốt.\r\n\r\nGhé thăm các hộ dân làm đường thốt nốt thủ công. Bạn có thể xin thử leo cây (thử ở tầm thấp) hoặc đơn giản là ngồi nghe họ kể về sự gắn bó giữa cây thốt nốt và đời sống người Khmer.\r\n\r\nTối: Tự do dạo phố Tri Tôn, cảm nhận nhịp sống chậm rãi về đêm.\r\n\r\nNgày 3: Thiền định & Tĩnh tâm tại Chùa Núi\r\nTrọng tâm: Tìm về sự an lạc trong tâm hồn.\r\n\r\nSáng (08:00): Thiền định tại Chùa Tà Pạ (Chùa Chưn Núm).\r\n\r\nNgôi chùa tọa lạc trên đỉnh đồi cao, tách biệt hẳn với tiếng ồn.\r\n\r\nHoạt động: Hãy chọn một góc hiên chùa hoặc dưới gốc cây bồ đề để ngồi thiền hoặc đi bộ chậm (kinh hành). Tiếng chuông gió và mùi hương trầm nhẹ sẽ giúp tâm trí bạn cực kỳ thư thái.\r\n\r\n10:30: Đàm đạo hoặc tham quan kiến trúc chùa.\r\n\r\nChiêm ngưỡng những bức phù điêu kể về cuộc đời Đức Phật. Bạn cũng có thể quan sát các nhà sư trẻ (các chú tiểu) học kinh chữ Phạn.\r\n\r\nTrưa: Dùng bữa cơm chay thanh tịnh tại khu vực gần chùa.\r\n\r\nChiều: Ghé thăm Chùa Xà Tón – ngôi chùa cổ nhất vùng với kiến trúc mái đao rực rỡ trước khi rời Tri Tôn.\r\n\r\n16:00: Mua một ít bánh bò thốt nốt và đường thốt nốt nguyên chất về làm quà, kết thúc hành trình 3 ngày bình yên.\r\n\r\n💡 Một vài lưu ý cho hành trình của bạn:\r\nTrang phục: Vì có hoạt động thiền và thăm chùa, bạn nên mang theo ít nhất 1-2 bộ quần áo vải đũi hoặc đồ lam kín đáo, thoáng mát.\r\n\r\nThời điểm: Điểm \"Cổng trời\" đẹp nhất vào lúc nắng sớm (7h-9h).\r\n\r\nThái độ: Người dân và các sư sãi rất hiền hậu, bạn chỉ cần giữ nụ cười và sự tôn trọng không gian yên tĩnh của chùa.', 'Xe du lịch', NULL),
(22, 'Tour Làng Nổi Châu Đốc Sông Nước', 'An Giang, Việt Nam', 5000000.00, 2, '2026-03-08', 'https://th.bing.com/th/id/R.98f72892c40b52719a646937d871029e?rik=edwFuqIaFjNGaQ&pid=ImgRaw&r=0', 'TP. HCM', 'Châu Đốc Riverside Hotel', 1, 'Chào bạn! Một chuyến đi nhẹ nhàng 2 ngày 1 đêm tại Châu Đốc (An Giang) để cảm nhận hơi thở sông nước miền Tây là một lựa chọn tuyệt vời.\r\n\r\nDưới đây là lịch trình chi tiết giúp bạn tối ưu thời gian và trải nghiệm:\r\n\r\nNgày 1: Lênh đênh sông Hậu & Văn hóa làng bè\r\nTrọng tâm: Tìm hiểu đời sống mưu sinh trên sông của người dân địa phương và nét văn hóa Chăm.\r\n\r\nSáng (08:00): Xuất phát từ Bến tàu du lịch Châu Đốc. Thuê một chiếc thuyền nhỏ để bắt đầu hành trình.\r\n\r\n08:30: Thăm Làng bè nuôi cá trên sông Hậu.\r\n\r\nBạn sẽ được tận mắt thấy hàng nghìn con cá tra, cá basa quẫy nước tranh mồi dưới sàn nhà bè.\r\n\r\nNghe chủ bè kể về quy trình nuôi cá \"đặc sản\" xuất khẩu của vùng đất An Giang.\r\n\r\n10:00: Ghé thăm Làng Chăm Châu Phong.\r\n\r\nThuyền đưa bạn sang bờ bên kia để thăm các thánh đường Hồi giáo (Masjid) với kiến trúc mái vòm trắng độc đáo.\r\n\r\nXem các nghệ nhân người Chăm dệt thổ cẩm thủ công và thưởng thức bánh Ha nàm căn (bánh phồng đặc trưng).\r\n\r\nTrưa: Ăn trưa tại nhà hàng ven sông hoặc các quán ăn trung tâm với món Gỏi sầu đâu khô cá sặc.\r\n\r\nChiều: Về khách sạn nghỉ ngơi. Cuối chiều bạn có thể tản bộ ở công viên văn hóa Châu Đốc, ngắm tượng đài cá basa và hoàng hôn trên sông.\r\n\r\nNgày 2: Càn quét \"Vương quốc Mắm\" & Tiệc cá lóc nướng\r\nTrọng tâm: Mua sắm đặc sản và thưởng thức ẩm thực dân dã.\r\n\r\nSáng (07:30): Khám phá Chợ Châu Đốc.\r\n\r\nĂn sáng: Thử ngay một tô Bún cá Châu Đốc vỉa hè với màu vàng của nghệ và vị thơm của ngải bún.\r\n\r\nTham quan: Bước vào khu vực bán mắm và đồ khô. Đây là \"thiên đường\" để chụp ảnh và mua sắm. Bạn hãy thử mắm thái, mắm cá chốt hoặc mua đường thốt nốt nguyên chất về làm quà.\r\n\r\n10:30: Ghé qua các sạp hàng bán đồ Thái Lan, Campuchia bên trong chợ – nét đặc trưng của vùng biên thùy.\r\n\r\nTrưa (12:00): Thưởng thức Cá lóc nướng trui.\r\n\r\nBạn có thể chọn các quán ăn sân vườn hoặc khu vực quanh núi Sam.\r\n\r\nCách ăn đúng điệu: Cá lóc nướng trui cuốn bánh tráng, kèm rau sống, bún, và đặc biệt là chấm với mắm me chua ngọt. Vị cá thơm mùi rơm, thịt ngọt lịm sẽ là điểm nhấn khó quên cho chuyến đi.\r\n\r\nChiều: Trước khi ra về, bạn có thể ghé qua Chùa Tây An hoặc Miếu Bà Chúa Xứ gần đó để chiêm bái nếu còn thời gian.\r\n\r\n💡 Một vài mẹo nhỏ:\r\nGiá thuê thuyền: Nên thỏa thuận giá rõ ràng tại bến tàu (khoảng 200k - 350k tùy số lượng người và điểm dừng).\r\n\r\nMua mắm: Đừng ngại nếm thử (người bán rất hào phóng) để chọn đúng loại mắm vừa khẩu vị nhất.\r\n\r\nCá lóc nướng: Nếu muốn ăn cá lóc nướng rơm \"chuẩn\", hãy hỏi các quán sân vườn dọc đường về hướng núi Sam hoặc khu vực giáp biên giới.', 'Thuyền', NULL),
(23, 'Tour Chùa Long Sơn Tâm Linh', 'An Giang, Việt Nam', 2800000.00, 3, '2026-03-08', 'https://newlifetravel.vn/wp-content/uploads/2024/07/chua-long-son-nha-trang-newlifetravel.jpg', 'TP. HCM', 'Sam Mountain Resort', 0, 'Chào bạn! Một lịch trình 3 ngày tập trung quanh Núi Sam (Châu Đốc, An Giang) là một lựa chọn tuyệt vời để vừa tận hưởng không khí linh thiêng, vừa khám phá được vẻ đẹp hùng vĩ của vùng biên thùy.\r\n\r\nDưới đây là lịch trình chi tiết dành cho bạn:\r\n\r\nNgày 1: Hành trình tâm linh tại Chùa Long Sơn\r\nTrọng tâm: Chiêm bái ngôi chùa có kiến trúc đẹp và không gian thanh tịnh.\r\n\r\nSáng (09:00): Khởi hành đến Núi Sam. Điểm đến đầu tiên là Chùa Long Sơn.\r\n\r\nĐây là ngôi chùa nằm ở lưng chừng núi, nổi bật với kiến trúc tinh xảo và bức tượng Phật cao lớn.\r\n\r\nTrải nghiệm: Đi bộ thong thả trên các bậc thang đá, tận hưởng không gian yên tĩnh và ngắm nhìn những khóm hoa, cây cảnh được chăm sóc tỉ mỉ trong khuôn viên chùa.\r\n\r\nTrưa: Thưởng thức Bún cá Châu Đốc ngay dưới chân núi – món ăn nổi tiếng với vị ngọt từ cá lóc đồng và mùi thơm của ngải bún.\r\n\r\nChiều: Tự do dạo quanh khu vực chân núi Sam, tham quan các gian hàng bán đặc sản và đồ lưu niệm. Bạn có thể ghé một quán cà phê võng ven đường để cảm nhận nét bình dị của miền Tây.\r\n\r\nTối: Thưởng thức Lẩu mắm Châu Đốc – món ăn \"linh hồn\" của vùng đất này.\r\n\r\nNgày 2: Chinh phục đỉnh núi – Thu trọn tầm mắt vùng biên\r\nTrọng tâm: Hoạt động leo núi và ngắm nhìn toàn cảnh thiên nhiên.\r\n\r\nSáng (07:30): Bắt đầu hành trình leo Núi Sam.\r\n\r\nBạn có thể chọn đi bộ theo đường mòn để thử thách sức bền hoặc đi xe máy lữ hành lên đỉnh.\r\n\r\nTrên đường đi, bạn sẽ đi qua nhiều am, cốc nhỏ của các bậc tu hành.\r\n\r\n09:30: Chạm đỉnh Núi Sam.\r\n\r\nĐứng từ trên đỉnh cao nhất, bạn sẽ được chiêm ngưỡng toàn cảnh thành phố Châu Đốc, dải lụa xanh Kênh Vĩnh Tế và những cánh đồng lúa trải dài sang tận biên giới Campuchia.\r\n\r\nĐừng quên ghé thăm bệ đá nơi Bà Chúa Xứ từng ngự trước khi được thỉnh xuống miếu.\r\n\r\nTrưa: Dùng bữa với món Gà hấp lá chúc tại các quán ăn trên núi – vị thơm nồng của lá chúc sẽ khiến món gà trở nên vô cùng đặc biệt.\r\n\r\nChiều: Xuống núi thong thả, bạn có thể ghé vào các vách đá đẹp để chụp ảnh check-in với view đồng bằng phía dưới.\r\n\r\nTối: Nghỉ ngơi và dạo chợ đêm Châu Đốc.\r\n\r\nNgày 3: Dấu ấn lịch sử – Các đền thờ di sản\r\nTrọng tâm: Tìm hiểu về công lao mở cõi của các bậc tiền nhân.\r\n\r\nSáng (08:30): Viếng Miếu Bà Chúa Xứ Núi Sam.\r\n\r\nĐiểm hành hương quan trọng nhất. Hãy thắp một nén nhang cầu bình an cho gia đình và chiêm ngưỡng kiến trúc mái đình ba tầng lợp ngói xanh đặc sắc.\r\n\r\n10:00: Thăm Lăng Thoại Ngọc Hầu (Sơn Lăng).\r\n\r\nNằm ngay đối diện Miếu Bà. Đây là công trình kiến trúc cổ kính, uy nghiêm, nơi an nghỉ của danh tướng có công đào kênh Vĩnh Tế.\r\n\r\n11:00: Viếng Tây An Cổ Tự.\r\n\r\nNgôi chùa cổ với sự pha trộn kiến trúc Ấn Độ và Việt Nam vô cùng độc đáo.\r\n\r\nTrưa: Thưởng thức món Bánh bò thốt nốt và Nước thốt nốt lạnh để giải nhiệt.\r\n\r\nChiều: Ghé qua Chợ Châu Đốc (Vương quốc mắm) để mua sắm các loại mắm thái, khô cá tra phồng, hoặc đường thốt nốt về làm quà trước khi kết thúc hành trình.\r\n\r\n💡 Lưu ý nhỏ cho chuyến đi:\r\nTrang phục: Vì Ngày 1 và Ngày 3 đi nhiều chùa chiền linh thiêng, bạn nên mặc trang phục kín đáo (quần dài, áo có tay).\r\n\r\nDi chuyển: Leo núi vào Ngày 2 nên đi giày thể thao có độ bám tốt vì một số đoạn đường đá có thể hơi trơn.\r\n\r\nThời điểm: Buổi sáng sớm trên núi Sam thường có sương mờ rất đẹp, bạn nên tranh thủ xuất phát sớm để đón bình minh.', 'Xe du lịch', NULL),
(24, 'Tour Cánh Đồng Quạt Chong Chóng Tà Pạ', 'An Giang, Việt Nam', 2800000.00, 2, '2026-03-08', 'https://tse4.mm.bing.net/th/id/OIP.9i1k3T_OZ3EuBr_DxVxp4QHaE7?rs=1&pid=ImgDetMain&o=7&rm=3', 'TP. HCM', 'Tà Pạ Lake Resort', 0, 'Chào bạn! Lịch trình của bạn tập trung vào những \"tọa độ\" sống ảo đẹp nhất tại Tri Tôn (An Giang). Đây là vùng đất mà cứ giơ máy lên là có ảnh đẹp nhờ sự kết hợp giữa nét hùng vĩ của núi đá và sự mềm mại của những hàng thốt nốt.\r\n\r\nDưới đây là lịch trình chi tiết để bạn có những khung hình \"triệu view\":\r\n\r\nNgày 1: Cánh Đồng Quạt Gió & Sắc Màu Phum Sóc\r\nTrọng tâm: Những khung hình hiện đại đan xen nét truyền thống.\r\n\r\nSáng (07:30 - 09:30): Check-in Cánh đồng Điện gió (Quạt chong chóng).\r\n\r\nĐịa điểm: Khu vực xã Vĩnh Trung/An Hảo. Những trụ turbine cao vút giữa cánh đồng lúa bao la tạo nên một khung cảnh rất Tây.\r\n\r\nMẹo chụp ảnh: Nên đi sớm khi nắng chưa gắt để lấy được bầu trời xanh ngắt. Các góc chụp từ dưới lên hoặc chụp từ xa lấy toàn cảnh hàng quạt gió trải dài sẽ rất ấn tượng.\r\n\r\n10:00: Chụp ảnh hàng thốt nốt hình trái tim.\r\n\r\nCách khu vực quạt gió không xa. Đây là \"đặc sản\" không thể thiếu khi đến Tri Tôn. Hãy tranh thủ chụp trước khi nắng đứng bóng để tránh bị đổ bóng trên mặt.\r\n\r\nTrưa: Thưởng thức Gà đốt Ô Thum. Vừa ăn vừa ngắm nhìn hồ Ô Thum thơ mộng dưới chân núi Tô.\r\n\r\nChiều (15:00): Dạo chơi đồng lúa Tà Pạ.\r\n\r\nLúc này nắng vàng xiên khoai, rất hợp để chụp ảnh với những ô ruộng đa sắc màu và những người dân địa phương đang chăn bò hoặc làm ruộng.\r\n\r\nTối: Nghỉ ngơi tại Tri Tôn, dạo phố và ăn vặt với món Đu đủ đâm (Som tam kiểu miền Tây).\r\n\r\nNgày 2: \"Tuyệt Tình Cốc\" Tà Pạ & Tuyệt Tác Thiên Nhiên\r\nTrọng tâm: Khám phá vẻ đẹp thiên nhiên hoang sơ và kiến trúc Khmer.\r\n\r\nSáng (08:00): Check-in Hồ Tà Pạ (Tuyệt Tình Cốc).\r\n\r\nĐây là hồ nước nhân tạo trên núi với màu nước xanh ngọc bích quanh năm.\r\n\r\nMẹo chụp ảnh: Hãy chọn những vách đá có hình thù lạ mắt xung quanh hồ để làm tiền cảnh. Màu nước xanh của hồ kết hợp với màu đá xám và bầu trời sẽ tạo nên một bức ảnh vô cùng kỳ ảo.\r\n\r\n10:00: Tham quan Chùa Tà Pạ (Chùa Núi).\r\n\r\nNgôi chùa có kiến trúc Khmer tinh xảo, nằm ở vị trí cao nhất khu vực. Từ sân chùa, bạn có thể chụp ảnh toàn cảnh thị trấn Tri Tôn và những cánh đồng lúa bạt ngàn phía dưới.\r\n\r\nTrưa: Thưởng thức Bún Num Bò Chóc – món ăn tinh túy của người Khmer địa phương.\r\n\r\nChiều: Vườn thốt nốt & Hoàng hôn vùng biên.\r\n\r\nGhé một vườn thốt nốt bất kỳ dọc đường đi xã Châu Lăng. Chụp ảnh cảnh người dân leo cây lấy nước thốt nốt.\r\n\r\nKết thúc: Thưởng thức một ly nước thốt nốt mát lạnh ngay tại vườn trước khi khởi hành ra về.\r\n\r\n💡 Bí kíp để có ảnh \"nghìn like\":\r\nTrang phục: Ưu tiên các tông màu rực rỡ như Trắng, Vàng, Đỏ hoặc Cam để nổi bật giữa nền xanh của lúa và hồ nước.\r\n\r\nPhụ kiện: Một chiếc nón lá, một chiếc khăn rằn hoặc một chiếc ô trong suốt sẽ là những đạo cụ tuyệt vời cho bối cảnh miền Tây.\r\n\r\nThời điểm: Buổi sáng từ 07:00 - 09:00 và buổi chiều từ 15:30 - 17:30 là \"giờ vàng\" của ánh sáng tại An Giang.', 'Xe du lịch', NULL),
(25, 'Tour Núi Két - Đỉnh An Giang', 'An Giang, Việt Nam', 4200000.00, 3, '2026-03-08', 'https://tse2.mm.bing.net/th/id/OIP.nc9Ucz-lAm3VrVSNESn3pAHaEJ?rs=1&pid=ImgDetMain&o=7&rm=3', 'TP. HCM', 'Núi Két Homestay', 0, 'Chào bạn! Một chuyến khám phá Núi Két (Anh Vũ Sơn) tại Tịnh Biên, An Giang là lựa chọn rất thú vị cho những ai yêu thích cảm giác leo núi kết hợp với chiêm bái tâm linh. Núi Két tuy không cao bằng núi Cấm nhưng lại sở hữu những khối đá có hình thù kỳ thú và tầm nhìn cực đẹp.\r\n\r\nDưới đây là lịch trình chi tiết 3 ngày dành cho bạn:\r\n\r\nNgày 1: Chinh phục Anh Vũ Sơn – Ngắm toàn cảnh đồng biên giới\r\nTrọng tâm: Hoạt động leo núi và thưởng ngoạn thiên nhiên.\r\n\r\nSáng (08:00): Bắt đầu hành trình leo núi Két. Cung đường leo núi ở đây là các bậc thang đá đã được xây dựng khá hoàn chỉnh, len lỏi dưới tán cây rừng mát mẻ.\r\n\r\n09:30: Check-in Mỏ Ông Két. * Đây là biểu tượng của ngọn núi này – một khối đá khổng lồ nằm nhô ra có hình dáng giống hệt đầu một con chim Két (chim vẹt). Từ vị trí này, bạn có thể phóng tầm mắt ngắm nhìn toàn cảnh cánh đồng Tịnh Biên trải dài đến tận biên giới Campuchia.\r\n\r\n11:00: Khám phá Sân Tiên và Giếng Tiên. Những hốc đá tự nhiên tích tụ nước mưa trong vắt, gắn liền với những câu chuyện thần tiên huyền bí.\r\n\r\nTrưa: Thưởng thức bữa trưa dân dã tại các quán nhỏ ven đường lên núi (Gợi ý: Cá lóc nướng, gà đồi).\r\n\r\nChiều: Tự do dạo quanh các vách đá, tìm góc chụp hình toàn cảnh đồng bằng An Giang từ trên cao. Sau đó xuống núi nghỉ ngơi.\r\n\r\nNgày 2: Hành trình chiêm bái – Hệ thống chùa Núi Két\r\nTrọng tâm: Tìm về sự thanh tịnh và tìm hiểu các tích xưa.\r\n\r\nSáng (09:00): Viếng Chùa Thới Sơn.\r\n\r\nNgôi chùa nằm ngay chân núi, gắn liền với giáo phái Bửu Sơn Kỳ Hương. Không gian chùa rộng rãi, uy nghiêm và rất thanh tịnh.\r\n\r\n10:30: Thăm Điện Năm Thẻ và các am tu hành.\r\n\r\nDọc theo đường lên núi có rất nhiều điện thờ nhỏ như: Điện Chư Vị, Điện Phật Mẫu, Điện Ngọc Hoàng. Mỗi nơi đều có một câu chuyện kể về các bậc tiền nhân đã đến đây tu tập.\r\n\r\nTrưa: Dùng cơm chay tại chùa hoặc các quán chay phục vụ khách hành hương dưới chân núi.\r\n\r\nChiều: Ghé thăm Chùa Phật Thới – một ngôi chùa có kiến trúc đẹp và không gian thoáng đãng ngay khu vực lối lên núi.\r\n\r\nTối: Tận hưởng không khí yên bình của thị trấn vùng biên Tịnh Biên.\r\n\r\nNgày 3: Khám phá \"Láng Linh\" & Mua sắm vùng biên\r\nTrọng tâm: Kết nối với các điểm du lịch nổi tiếng lân cận.\r\n\r\nSáng (08:30): Khám phá Rừng tràm Trà Sư.\r\n\r\nCách núi Két chỉ khoảng 10-15 phút di chuyển. Đây là \"đặc sản\" của An Giang với những thảm bèo xanh ngắt và hệ sinh thái chim muông đa dạng. Đi xuồng ba lá len lỏi trong rừng tràm là trải nghiệm không thể bỏ qua.\r\n\r\nTrưa: Thưởng thức bữa trưa tại Rừng tràm: Chuột đồng chiên nước mắm, canh chua cá lóc bông điên điển.\r\n\r\nChiều (14:30): Mua sắm tại Chợ Tịnh Biên.\r\n\r\nĐây là chợ biên giới sầm uất nhất An Giang. Bạn có thể mua đặc sản làm quà: Các loại mắm Châu Đốc, đường thốt nốt, các loại côn trùng (nếu bạn đủ can đảm thử) và các món đồ gia dụng nhập khẩu từ Thái Lan, Campuchia giá rất rẻ.\r\n\r\n16:00: Kết thúc hành trình và khởi hành trở về.\r\n\r\n💡 Một vài lưu ý cho chuyến đi:\r\nTrang phục: Leo núi Két có khá nhiều bậc thang, bạn nên đi giày thể thao có độ bám tốt và mặc quần áo co giãn, thấm hút mồ hôi.\r\n\r\nChuẩn bị: Mang theo nước lọc và một ít đồ ăn nhẹ để bổ sung năng lượng dọc đường leo núi.\r\n\r\nThời điểm chụp ảnh: Để có ảnh \"Mỏ Ông Két\" đẹp nhất, bạn nên đến đây vào lúc sáng sớm (nắng dịu) hoặc lúc hoàng hôn.', 'Xe du lịch', NULL),
(26, 'Tour Làng Dệt Thổ Cẩm Chăm', 'An Giang, Việt Nam', 3000000.00, 2, '2026-03-08', 'https://kinhtevadubao.vn/stores/news_dataimages/kinhtevadubaovn/082019/13/15/lang-nghe-det-tho-cam-dam-tinh-tinh-hoa-cua-nguoi-viet-42-.4940.jpg', 'TP. HCM', 'Chăm Village Lodge', 0, 'Ngày 1: Khám phá quy trình dệt thổ cẩm truyền thống\r\n\r\nBuổi sáng, du khách di chuyển đến làng nghề dệt thổ cẩm Chăm – nơi gìn giữ những tinh hoa văn hóa của người Chăm qua từng đường sợi. Tại đây, bạn sẽ được:\r\n\r\nTìm hiểu quy trình dệt thủ công từ khâu xe sợi, nhuộm màu bằng nguyên liệu tự nhiên như lá chàm, củ nâu, vỏ cây, cho đến công đoạn dệt trên khung cửi truyền thống.\r\n\r\nQuan sát nghệ nhân Chăm miệt mài bên khung dệt, khéo léo đưa từng đường sợi tạo nên hoa văn rực rỡ tượng trưng cho văn hóa và tín ngưỡng.\r\n\r\nTrải nghiệm tự tay thử dệt một đoạn vải nhỏ và chụp ảnh lưu niệm cùng người dân địa phương.\r\n\r\nBuổi chiều, du khách có thể tham quan nhà trưng bày sản phẩm, tìm hiểu về lịch sử nghề dệt, ý nghĩa của các hoa văn đặc trưng, và cách người Chăm gìn giữ nghề qua nhiều thế hệ.\r\n\r\nNgày 2: Mua sắm sản phẩm thủ công và giao lưu văn hóa Chăm\r\n\r\nBuổi sáng, du khách tự do mua sắm sản phẩm thủ công mỹ nghệ, từ khăn, túi xách, váy áo, phụ kiện được dệt thủ công mang đậm dấu ấn văn hóa. Mỗi sản phẩm là một tác phẩm nghệ thuật chứa đựng tinh thần của người làm nghề.\r\n\r\nBuổi trưa, đoàn tham gia chương trình giao lưu văn hóa Chăm:\r\n\r\nThưởng thức điệu múa Apsara huyền bí, tượng trưng cho sự mềm mại và sức sống của người phụ nữ Chăm.\r\n\r\nNghe âm thanh nhạc cụ dân tộc như trống Ghi-năng, kèn Saranai.\r\n\r\nTìm hiểu phong tục, tín ngưỡng và ẩm thực truyền thống, cùng thưởng thức các món đặc sản như cà ri dê, cơm nị, bánh hổi, chè bí đỏ.\r\n\r\nBuổi chiều, du khách có thể dạo quanh làng, chụp ảnh cùng người dân trong trang phục truyền thống, lưu lại những khoảnh khắc đáng nhớ trước khi kết thúc hành trình.', 'Xe du lịch', NULL),
(31, 'Tour Khu du lịch Núi Cấm - Thiên Cấm Sơn', 'An Giang, Việt Nam', 1500000.00, 3, '2026-03-08', 'https://media.mia.vn/uploads/blog-du-lich/nui-cam-thien-cam-son-noi-nhung-tinh-hoa-ve-canh-sac-duoc-hoi-tu-7-1660411117.jpg', 'TP. HCM', 'Núi Cấm Resort', 1, 'Dựa trên những trải nghiệm tuyệt vời tại \"Nóc nhà miền Tây\" mà bạn đã chọn, mình xin hệ thống lại thành một bản lịch trình chi tiết 3 ngày 2 đêm tại Núi Cấm (Thiên Cấm Sơn). Lịch trình này được thiết kế để bạn vừa tận hưởng được sự linh thiêng, vừa cảm nhận được không khí trong lành như \"Đà Lạt của miền Tây\".\r\n\r\nLỊCH TRÌNH: CHINH PHỤC NÓC NHÀ MIỀN TÂY – NÚI CẤM (3 NGÀY 2 ĐÊM)\r\nNgày 1: Chạm ngõ Thiên Cấm Sơn – Sự tĩnh lặng của đại ngàn\r\nTrọng tâm: Chinh phục độ cao và chiêm bái các ngôi chùa cổ.\r\n\r\nSáng (08:30): Di chuyển đến chân Núi Cấm (Tịnh Biên).\r\n\r\nLựa chọn: Bạn có thể lên núi bằng Cáp treo (ngắm toàn cảnh rừng núi từ trên cao) hoặc đi xe lữ hành len lỏi qua những cung đường đèo dốc uốn lượn.\r\n\r\n10:30: Tham quan Chùa Vạn Linh.\r\n\r\nChiêm ngưỡng tòa tháp Quan Âm Các cao 9 tầng uy nghiêm. Đây là ngôi chùa có lối kiến trúc cổ điển, tọa lạc ở vị trí đắc địa nhìn ra hồ Thủy Liêm.\r\n\r\nTrưa: Dùng bữa trưa với món chay tại chùa hoặc các quán ăn khu vực hồ. Nghỉ ngơi tại homestay hoặc nhà nghỉ trên núi để cảm nhận cái se lạnh đặc trưng.\r\n\r\n15:00: Viếng Chùa Phật Lớn.\r\n\r\nTìm hiểu về lịch sử ngôi chùa có từ năm 1912 và chiêm bái tượng Phật Di Đà cổ.\r\n\r\nTối: Tận hưởng không khí Núi Cấm về đêm. Nhiệt độ trên núi sẽ giảm sâu, rất thích hợp để dạo bộ quanh hồ và ngắm sương mù giăng lối.\r\n\r\nNgày 2: Biểu tượng tâm linh & Tuyệt tác thiên nhiên\r\nTrọng tâm: Chiêm bái tượng Phật khổng lồ và thư giãn bên hồ.\r\n\r\nSáng (08:00): Chiêm bái Tượng Phật Di Lặc.\r\n\r\nĐây là bức tượng Phật Di Lặc lớn nhất trên đỉnh núi tại châu Á. Nụ cười từ bi của Đức Phật giữa không gian bao la mang lại cảm giác an yên tự tại. Bạn đừng quên lên khu vực bệ tượng để phóng tầm mắt nhìn xuống toàn cảnh vùng Thất Sơn và dải biên giới Việt - Cam.\r\n\r\nTrưa: Thưởng thức món Lẩu mắm hoặc Gà đồi nướng cực kỳ nổi tiếng của vùng núi này.\r\n\r\nChiều (14:30): Khám phá Hồ Thủy Liêm.\r\n\r\nDạo quanh bờ hồ xanh ngắt, cho cá ăn (cá ở đây rất dạn người) và chụp ảnh lưu niệm với khung cảnh \"sơn thủy hữu tình\". Nếu còn sức, bạn có thể đi bộ đến Vồ Bồ Hong – đỉnh cao nhất của Núi Cấm để đón gió.\r\n\r\nTối: Giao lưu cùng người dân địa phương, nghe những câu chuyện huyền bí về vùng đất Thất Sơn bên tách trà nóng.\r\n\r\nNgày 3: Hương vị rau rừng & Quà tặng từ đại ngàn\r\nTrọng tâm: Trải nghiệm ẩm thực và mua sắm đặc sản.\r\n\r\nSáng (08:00): Bữa sáng \"vị núi rừng\".\r\n\r\nPhải thử món Bánh xèo rau rừng: Bánh xèo đổ giòn tan ăn kèm với hơn 20 loại rau hái trực tiếp trên núi (lá lụa, sao nhái, cát lồi...).\r\n\r\nNhâm nhi một tách Cà phê núi trong màn sương sớm.\r\n\r\n10:00: Mua sắm tại chợ nông sản trên núi.\r\n\r\nMua đặc sản làm quà: Mật ong rừng, các loại thảo dược, trà sâm núi, hoặc trái cây theo mùa như dâu da đất, bơ, sầu riêng Núi Cấm (vị rất thơm và béo).\r\n\r\n11:30: Dùng bữa trưa nhẹ với Cơm lam ống tre.\r\n\r\n13:00: Thu dọn hành lý, di chuyển xuống núi. Kết thúc hành trình khám phá vùng đất linh thiêng.\r\n\r\n💡 Bí kíp nhỏ cho chuyến đi:\r\nTrang phục: Mang theo áo khoác mỏng vì buổi đêm và sáng sớm trên núi rất lạnh. Giày thể thao là lựa chọn tốt nhất để di chuyển.\r\n\r\nThời điểm: Nếu đi vào mùa mưa (tháng 9-11), thác băng trên núi sẽ chảy rất đẹp, nhưng đường đi có thể hơi trơn trượt.\r\n\r\nDi chuyển: Nếu bạn tay lái yếu, tuyệt đối không tự chạy xe máy lên đỉnh núi vì dốc rất đứng và nhiều khúc cua gắt; nên sử dụng dịch vụ xe lữ hành của khu du lịch.', 'Xe du lịch', NULL),
(32, 'Tour Khu du lịch Núi Sam - Châu Đốc 2 Ngày', 'An Giang, Việt Nam', 4500000.00, 2, '2026-03-08', 'https://th.bing.com/th/id/OIP.Umbni4DzNe11AoFqyMcRFgHaD4?o=7rm=3&rs=1&pid=ImgDetMain&o=7&rm=3', 'TP. HCM', 'Khách sạn Bến Đá Núi Sam', 1, 'Chào bạn! Một bản lịch trình hành hương và khám phá văn hóa tại Châu Đốc rất đầy đủ và mang đậm bản sắc vùng biên thùy. Để bản lịch trình này trở nên chuyên nghiệp, dễ theo dõi và hấp dẫn hơn cho đoàn khách, mình xin hệ thống lại theo phong cách \"cẩm nang du lịch\" dưới đây:\r\n\r\nHÀNH TRÌNH TÂM LINH & DI SẢN: CHÂU ĐỐC – NÚI SAM (2 NGÀY 1 ĐÊM)\r\nNgày 1: Linh Thiêng Miếu Bà – Dấu Ấn Khai Phá Nam Bộ\r\nTrọng tâm: Chiêm bái các di tích quốc gia và thưởng thức ẩm thực đặc trưng.\r\n\r\nSáng: Khởi hành & Hành hương\r\n\r\n07:00: Xe và HDV đón đoàn, bắt đầu hành trình về vùng đất Thất Sơn huyền bí.\r\n\r\n10:30: Đến Châu Đốc, đoàn viếng Miếu Bà Chúa Xứ Núi Sam. Đây là trung tâm hành hương lớn nhất Nam Bộ. Quý khách dâng hương, cầu bình an và chiêm ngưỡng bức tượng Bà bằng đá sa thạch có từ thế kỷ thứ VI.\r\n\r\nTrưa: Hương vị đồng quê\r\n\r\n12:00: Dùng bữa trưa với thực đơn \"rặt\" miền Tây: Lẩu mắm cốt cá linh, gỏi sầu đâu khô sặc, cá basa kho tộ.\r\n\r\n13:30: Làm thủ tục nhận phòng khách sạn, nghỉ ngơi.\r\n\r\nChiều: Quần thể di tích chân núi Sam\r\n\r\n15:00: Viếng Tây An Cổ Tự. Ngôi chùa nổi bật với lối kiến trúc giao thoa giữa nghệ thuật Ấn Độ và Việt Nam, nơi có hàng trăm bức tượng Phật bằng gỗ quý.\r\n\r\n16:00: Tham quan Sơn Lăng (Lăng Thoại Ngọc Hầu). Tìm hiểu về cuộc đời vị công thần có công đào kênh Vĩnh Tế, mở cõi phương Nam.\r\n\r\n17:00: Tự do thưởng thức quà vặt: Bánh bò thốt nốt nướng, nước thốt nốt lạnh.\r\n\r\nTối: Phố đêm biên thùy\r\n\r\n19:00: Sau bữa tối, đoàn tự do dạo Chợ đêm Núi Sam. Đây là dịp để cảm nhận nhịp sống về đêm của người dân địa phương và mua sắm các loại đồ lưu niệm thủ công.\r\n\r\nNgày 2: Đỉnh Núi Sam Huyền Bí – Vương Quốc Mắm\r\nTrọng tâm: Ngắm cảnh biên giới và mua sắm đặc sản.\r\n\r\nSáng: Chinh phục đỉnh cao & Cảnh sắc biên thùy\r\n\r\n07:30: Điểm tâm sáng (Gợi ý: Bún cá Châu Đốc).\r\n\r\n08:30: Di chuyển lên Đỉnh Núi Sam. Tại đây, quý khách tham quan:\r\n\r\nBệ đá nơi Bà Chúa Xứ ngự ngày xưa trước khi được thỉnh xuống miếu.\r\n\r\nMiếu Bà Nhỏ yên tĩnh và linh thiêng.\r\n\r\nPhóng tầm mắt: Ngắm nhìn toàn cảnh cánh đồng Vĩnh Tế trải dài đến tận biên giới Campuchia.\r\n\r\nTrưa: Mua sắm tại \"Vương quốc Mắm\"\r\n\r\n11:00: Xuống núi, dùng cơm trưa và làm thủ tục trả phòng.\r\n\r\n13:30: Ghé Chợ Châu Đốc. Quý khách tự do tham quan và mua sắm:\r\n\r\nCác loại mắm nổi tiếng: Mắm thái, mắm chốt, mắm cá linh.\r\n\r\nCác loại khô: Khô cá tra phồng, khô nhái (vũ nữ chân dài).\r\n\r\nThạch thốt nốt, đường thốt nốt nguyên chất.\r\n\r\nChiều: Kết thúc hành trình\r\n\r\n15:00: Xe khởi hành đưa đoàn về lại điểm đón ban đầu. Chia tay và hẹn gặp lại.\r\n\r\n💡 Gợi ý nhỏ để chuyến đi hoàn hảo hơn:\r\nĐặc sản mùa nước nổi: Nếu đi vào khoảng tháng 9 - tháng 11, bạn đừng quên dặn nhà hàng chuẩn bị món Bông điên điển xào tép hoặc Canh chua cá linh – đây là thời điểm ngon nhất trong năm.\r\n\r\nDi chuyển lên núi: Nếu đoàn có người lớn tuổi, ưu tiên sử dụng xe trung chuyển hoặc cáp treo núi Sam để giữ sức khỏe.\r\n\r\nTrang phục: Ngày 1 tham quan nhiều đền chùa linh thiêng, đoàn nên mặc trang phục kín đáo, lịch sự.', 'Xe du lịch', NULL),
(33, 'Tour Khám Phá Chùa Tà Pạ và Hồ Tuyệt Tình Cốc', 'An Giang, Việt Nam', 1500000.00, 2, '2026-03-08', 'https://mia.vn/media/uploads/blog-du-lich/kham-pha-ho-da-doi-ta-pa-tuyet-tinh-coc-giua-long-an-giang-4-1660655185.jpg', 'TP. HCM', 'TaPa Hotel', 1, 'LỊCH TRÌNH: TRI TÔN – SẮC MÀU PHUM SÓC (2 NGÀY 1 ĐÊM)\r\nNgày 1: Chùa Tà Pạ & Bức họa đồng quê\r\nTrọng tâm: Kiến trúc Khmer và tầm nhìn từ trên cao.\r\n\r\n08:00 – 10:30: Hành hương Chùa Tà Pạ (Chùa Núi).\r\n\r\nDi chuyển lên núi Tô bằng xe máy hoặc đi bộ để tận hưởng không khí trong lành.\r\n\r\nTrải nghiệm: Chiêm ngưỡng ngôi chùa được xây dựng theo lối kiến trúc \"hệ phái Nam tông\" với những bức phù điêu kể về cuộc đời Đức Phật và những mái đao nhọn vút lên bầu trời xanh.\r\n\r\n11:00 – 12:30: Ăn trưa đặc sản vùng cao.\r\n\r\nThưởng thức Gà đốt Ô Thum: Gà được đốt với lá chúc thơm nồng, da giòn rụm, ăn kèm với gỏi đu đủ đâm.\r\n\r\n14:30 – 16:30: Ngắm cánh đồng lúa Tri Tôn (Cánh đồng Tà Pạ).\r\n\r\nTừ sân chùa Tà Pạ, bạn hãy nhìn xuống toàn cảnh bên dưới. Đây là nơi có những hàng thốt nốt vươn cao giữa những ô ruộng nhiều màu sắc (xanh của lúa non, vàng của lúa chín).\r\n\r\nGóc ảnh đẹp: Xuống tận bờ ruộng để chụp ảnh với những \"cây thốt nốt trái tim\" nổi tiếng.\r\n\r\n17:00: Ngắm hoàng hôn trên cánh đồng.\r\n\r\nKhoảnh khắc mặt trời lặn sau những rặng thốt nốt là lúc Tri Tôn đẹp nhất, yên bình nhất.\r\n\r\nNgày 2: \"Tuyệt Tình Cốc\" & Hương vị Khmer\r\nTrọng tâm: Thiên nhiên kỳ ảo và giao lưu văn hóa.\r\n\r\n07:30 – 09:00: Ăn sáng đậm chất bản địa.\r\n\r\nTìm một quán nhỏ ven đường thưởng thức Bún Num Bò Chóc: Món bún tinh túy của người Khmer với vị nghệ tươi, mắm bò hóc và cá lóc đồng.\r\n\r\n09:30 – 11:30: Check-in Hồ Tà Pạ.\r\n\r\nĐây là hồ nước nhân tạo hình thành từ việc khai thác đá, nước hồ có màu xanh ngọc bích quanh năm.\r\n\r\nLưu ý: Nước hồ rất sâu và lạnh, bạn chỉ nên đi dạo trên bờ đá để chụp ảnh \"Tuyệt Tình Cốc\" và ngắm nhìn những vách đá dựng đứng hùng vĩ.\r\n\r\n12:00 – 13:30: Bữa trưa văn hóa.\r\n\r\nThử món Lạp xưởng bò (Tung lò mò) nướng thơm phức và canh xiêm lo.\r\n\r\n14:00 – 16:00: Khám phá đời sống Phum Sóc.\r\n\r\nGhé thăm các xóm làm Đường Thốt Nốt thủ công. Bạn có thể xem người dân leo cây lấy nước thốt nốt và trực tiếp khuấy những mẻ đường vàng óng, thơm lừng.\r\n\r\nTráng miệng: Thưởng thức bánh bò thốt nốt nướng hoặc uống một ly nước thốt nốt lạnh có thêm cùi thốt nốt sần sật.\r\n\r\n16:30: Mua sắm đặc sản (khô bò, đường thốt nốt, mắm) và kết thúc hành trình.\r\n\r\n💡 Bí kíp nhỏ cho bạn:\r\nThời điểm: Nếu đi vào khoảng tháng 9 - tháng 11 (mùa nước nổi), cánh đồng Tà Pạ sẽ có những đoạn nước đổ vào tạo nên những tấm gương phản chiếu rất đẹp.\r\n\r\nTrang phục: Các tông màu trắng, vàng hoặc đỏ sẽ rất nổi bật giữa nền xanh của núi rừng và hồ nước Tà Pạ.\r\n\r\nLưu ý môi trường: Hồ Tà Pạ là điểm đến thiên nhiên hoang sơ, bạn nhớ \"không để lại gì ngoài những dấu chân\" để bảo vệ màu nước xanh ngọc nhé!', 'Xe du lịch', NULL),
(34, 'Tour Di Tích Lịch Sử Lăng Thoại Ngọc Hầu và Kênh Vĩnh Tế', 'An Giang, Việt Nam', 1500000.00, 2, '2026-03-08', 'https://luhanhvietnam.com.vn/du-lich/vnt_upload/news/05_2025/lang_thoai_ngoc_hau_an_giang_dulichnuisam.jpg', 'TP. HCM', 'Châu Đốc Hotel', 1, 'Dựa trên nội dung bạn cung cấp về Lăng Thoại Ngọc Hầu, mình xin hệ thống lại thành một lịch trình chi tiết, kết hợp thêm các điểm tham quan phụ cận và yếu tố ẩm thực để chuyến đi của bạn tại Châu Đốc (An Giang) trở nên hoàn hảo hơn.\r\n\r\nLịch trình: Dấu Ấn Khai Phá Phương Nam (1 Ngày)\r\nSáng: Ngược dòng lịch sử tại Sơn Lăng\r\n07:30: Ăn sáng đặc sản Bún cá Châu Đốc (nấu với nghệ và ngải bún thơm nồng) để nạp năng lượng.\r\n\r\n08:30: Di chuyển đến cụm di tích dưới chân núi Sam. Điểm đến chính: Lăng Thoại Ngọc Hầu (Sơn Lăng).\r\n\r\nTham quan kiến trúc: Chiêm ngưỡng lòng lăng với các bức tường thành dày bằng gạch thẻ và hồ vôi, các hàng tượng lính hầu uy nghiêm.\r\n\r\nViếng mộ: Thắp nhang tại phần mộ của cụ Phan Ngọc Hiển (Thoại Ngọc Hầu) cùng hai vị phu nhân.\r\n\r\nTìm hiểu sử liệu: Đọc các bia ký cổ, đặc biệt là bản sao bia Vĩnh Tế Sơn Thông Kỷ để hiểu về kỳ tích đào kênh Vĩnh Tế – \"con đường tơ lụa\" thủy lợi của miền Tây xưa.\r\n\r\n10:30: Ghé thăm Miếu Bà Chúa Xứ Núi Sam (cách đó vài bước chân). Đây là trung tâm hành hương lớn nhất Nam Bộ, gắn liền với niềm tin tâm linh của người dân trong công cuộc khẩn hoang.\r\n\r\nTrưa: Thưởng thức phong vị vùng biên\r\n12:00: Dùng bữa trưa với các món ăn đặc trưng:\r\n\r\nGà hấp lá chúc: Loại lá gia vị đặc hữu của vùng Thất Sơn.\r\n\r\nLẩu mắm: Với đầy đủ các loại rau đồng nội (bông súng, điên điển, kèo nèo).\r\n\r\nChiều: Toàn cảnh Kênh Vĩnh Tế & Văn hóa đa dạng\r\n14:00: Di chuyển lên Đỉnh Núi Sam. Từ đây, bạn có thể phóng tầm mắt nhìn xuống toàn cảnh Kênh Vĩnh Tế chạy dài thẳng tắp về phía Hà Tiên, minh chứng cho tầm nhìn chiến lược của Thoại Ngọc Hầu.\r\n\r\n15:30: Tản bộ dọc bờ kênh hoặc ghé thăm Chùa Tây An – ngôi chùa có sự kết hợp kiến trúc độc đáo giữa Ấn Độ và Việt Nam, nằm ngay cạnh Sơn Lăng.\r\n\r\n16:30: Ghé qua Chợ Châu Đốc (Vương quốc mắm):\r\n\r\nTìm hiểu về sự giao thoa văn hóa Việt – Khmer – Chăm qua các loại khô, mắm và bánh bò thốt nốt.\r\n\r\nMua sắm đặc sản làm quà (mắm thái, mắm chốt, đường thốt nốt).\r\n\r\nTối: Bình yên phố thị Châu Đốc\r\n18:30: Dạo chơi phố đêm Châu Đốc, thưởng thức nước thốt nốt tươi mát lạnh. Kết thúc hành trình trong không gian thanh tịnh của vùng đất biên thùy.\r\n\r\n💡 Lưu ý cho hành trình này:\r\nTrang phục: Vì là điểm đến lịch sử và tâm linh uy nghiêm, bạn nên mặc trang phục lịch sự (quần dài, áo có tay).\r\n\r\nKể chuyện: Nếu đi theo nhóm, bạn có thể thuê hướng dẫn viên tại điểm để nghe kể chi tiết hơn về những giai thoại \"điều binh khiển tướng\" đầy ly kỳ của Danh tướng Thoại Ngọc Hầu.', 'Xe du lịch', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tourschedules`
--

CREATE TABLE `tourschedules` (
  `ScheduleID` int(11) NOT NULL,
  `TourID` int(11) NOT NULL,
  `DayNumber` int(11) NOT NULL,
  `TimeSlot` varchar(20) DEFAULT NULL,
  `Activity` varchar(100) NOT NULL,
  `Location` varchar(100) DEFAULT NULL,
  `StartTime` time DEFAULT NULL,
  `EndTime` time DEFAULT NULL,
  `Description` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `UserID` int(11) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `PasswordHash` varchar(255) NOT NULL,
  `RoleID` int(11) NOT NULL,
  `FullName` varchar(100) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `IsActive` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`UserID`, `Username`, `PasswordHash`, `RoleID`, `FullName`, `Email`, `IsActive`) VALUES
(1, 'admin', 'Duy@1007', 1, 'Việt Lữ Travel', 'vietlutravell@gmail.com', NULL),
(2, 'staff', 'Duy@1007', 2, 'Chăm Sóc Khách Hàng', 'cskh@gmail.com', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vanhoa`
--

CREATE TABLE `vanhoa` (
  `Id` bigint(20) NOT NULL,
  `TieuDe` varchar(255) DEFAULT NULL,
  `MoTa` text DEFAULT NULL,
  `VideoUrl` varchar(500) DEFAULT NULL,
  `HinhAnh` varchar(500) DEFAULT NULL,
  `DanhMuc` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `vanhoa`
--

INSERT INTO `vanhoa` (`Id`, `TieuDe`, `MoTa`, `VideoUrl`, `HinhAnh`, `DanhMuc`) VALUES
(1, 'Lễ hội Vía Bà Chúa Xứ Núi Sam', 'Nằm sừng sững dưới chân núi Sam huyền bí (TP. Châu Đốc), đây là trung tâm hành hương lớn nhất miền Tây. Pho tượng Bà bằng đá sa thạch quý hiếm có từ thế kỷ thứ 6, ẩn chứa biết bao huyền thoại về sự linh ứng che chở dân lành qua hàng thế kỷ.\r\n\r\nChiều sâu văn hóa: Lễ hội không chỉ là niềm tin tôn giáo mà còn là sự giao thoa giữa tín ngưỡng bản địa và văn hóa Óc Eo cổ xưa. Với người chưa biết: Điểm nhấn \"rõ nét nhất\" là nghi thức Tắm Bà diễn ra lúc nửa đêm ngày 23 rạng sáng 24/4 âm lịch.\r\n\r\nTrải nghiệm: Bạn sẽ thấy áo bào cũ của Bà được gỡ bỏ, thân tượng được lau sạch bằng nước thơm nấu từ 9 loại thảo mộc (hoa bưởi, quế, trầm...) trước khi thay bộ xiêm y lộng lẫy mới. Đến đây, bạn không chỉ xin lộc mà còn để chiêm ngưỡng kiến trúc \"nội công ngoại quốc\" rực rỡ với mái ngói xanh ngọc xếp tầng và cảm nhận niềm tin mãnh liệt của dòng người đổ về cầu mong gia đạo bình an dưới làn khói nhang huyền ảo.', 'https://www.youtube.com/embed/bQAE5eFkCJo', '/image/t1.jpg', 'lehoi'),
(2, 'Lễ hội Đua bò Bảy Núi', 'Đây là môn thể thao cảm giác mạnh \"độc nhất vô nhị\" của đồng bào Khmer vùng Tri Tôn, Tịnh Biên. Nó không phải cuộc đua trên đường nhựa bằng phẳng mà là sự thách thức bản lĩnh con người trước thiên nhiên.\r\n\r\nChiều sâu văn hóa: Đua bò gắn liền với lễ Sene Dolta (lễ cúng ông bà), thể hiện sự tri ân với vật nuôi đã giúp dân làng cày cấy. Với người chưa biết: Hai con bò kéo một chiếc bừa gỗ, người điều khiển đứng trên đó và thúc bò lao đi với tốc độ cực nhanh trên những thửa ruộng sình lầy sâm sấp nước.\r\n\r\nTrải nghiệm: Điểm hấp dẫn nhất là những pha \"tạt nước\" và bùn đất bắn tung tóe trong tiếng hò reo vang trời của hàng vạn khán giả. Bạn sẽ ngửi thấy mùi bùn non ngai ngái, nghe tiếng lạch cạch của bừa gỗ và cảm nhận được sự cộng hưởng tuyệt vời giữa con người và vật nuôi trong một không khí sôi động đến nghẹt thở.', 'https://www.youtube.com/embed/sUqJd4rlLf0', '/image/t2.jpg', 'lehoi'),
(3, 'Văn hóa Thất Sơn (Bảy Núi)', 'Thất Sơn không chỉ là địa danh địa lý, mà là một vùng đất huyền thoại gắn liền với những câu chuyện về các đạo sĩ, hổ mây và những bậc tu hành bí ẩn ẩn mình trong các hang động sâu.\r\n\r\nChiều sâu văn hóa: Bảy ngọn núi mọc lên giữa đồng bằng như những \"hòn non bộ\" khổng lồ, được coi là nơi hội tụ linh khí của đất trời. Với người chưa biết: Trải nghiệm rõ nhất là chinh phục núi Cấm (Thiên Cấm Sơn) – ngọn cao nhất – bằng cáp treo để ngắm nhìn toàn cảnh biên giới Campuchia từ trên cao.\r\n\r\nTrải nghiệm: Bạn sẽ lạc bước vào một không gian se lạnh như Đà Lạt, hít hà không khí trong lành của rừng nguyên sinh và nghe kể về truyền thuyết \"đất linh\" bảo vệ người dân miền biên viễn. Mỗi hang đá, mỗi dòng suối ở đây đều mang trong mình một câu chuyện tâm linh kỳ bí đang chờ bạn khám phá.', 'https://www.youtube.com/embed/wuXPRtxie3E', '/image/t3.jpg', 'dantoc'),
(4, 'Văn hóa Tín ngưỡng – Tôn giáo', 'An Giang là nơi khởi nguồn của đạo Phật Giáo Hòa Hảo và Tứ Ân Hiếu Nghĩa, tạo nên một bản sắc tôn giáo nội sinh cực kỳ đặc sắc và nhân văn.\r\n\r\nChiều sâu văn hóa: Đây là vùng đất của sự bao dung tuyệt đối, nơi đức tin được hiện thực hóa bằng những hành động thiện nguyện. Người chưa biết sẽ thấy ngạc nhiên khi chứng kiến chùa Khmer vàng rực, thánh đường Hồi giáo trắng muốt và chùa Hòa Hảo giản dị nằm cạnh nhau một cách hài hòa.\r\n\r\nTrải nghiệm: Bạn sẽ thấy người dân dù khác tôn giáo vẫn cùng nhau làm từ thiện, xây cầu, nấu cơm miễn phí cho người nghèo. Ghé thăm các cơ sở đạo tràng, bạn sẽ hiểu cách đức tin kết nối con người, tạo nên một đời sống tinh thần vô cùng phong phú, yên bình và đầy lòng trắc ẩn.', 'https://www.youtube.com/embed/wuXPRtxie3E', '/image/t4.jpg', 'dantoc'),
(5, 'Văn hóa Đa dân tộc', 'Sự giao thoa giữa 4 dân tộc Kinh, Hoa, Chăm và Khmer qua hàng trăm năm đã tạo nên một cộng đồng đa dạng nhưng cực kỳ hòa thuận và gắn bó.\r\n\r\nChiều sâu văn hóa: Đây là hình mẫu tiêu biểu cho sự cộng cư bền vững. Với người chưa biết: Bạn có thể nhận diện ngay qua trang phục và lối sống. Người Kinh mặc áo bà ba mộc mạc, người Chăm quấn khăn Mat-ra đội nước duyên dáng, người Khmer mặc xà rông sặc sỡ và người Hoa với những nếp nhà đặc trưng.\r\n\r\nTrải nghiệm: Sự giao thoa này rõ nhất ở phong vị ẩm thực (từ mắm kho đến bún nước lèo hay cà ri) và ngôn ngữ. Đến An Giang, bạn sẽ thấy một xã hội đa dạng nơi mỗi ngôi làng đều mang một bản sắc riêng không trộn lẫn nhưng lại cùng nhau xây dựng một quê hương chung giàu đẹp.', 'https://www.youtube.com/embed/jm_fmhtiSqU', '/image/t5.png', 'dantoc'),
(6, 'Văn hóa Nghệ thuật Dân gian', 'Nghệ thuật ở An Giang không nằm trên những sân khấu lớn sang trọng mà vang lên từ mái hiên nhà, bờ kênh hay dưới gốc thốt nốt rì rào trong gió.\r\n\r\nChiều sâu văn hóa: Đó là Đờn ca tài tử (Di sản UNESCO) mang đậm chất hào sảng của người dân Nam Bộ và điệu múa Lăm Vông uyển chuyển, mềm mại của người Khmer. Với người chưa biết: Những điệu hát này kể về lịch sử khẩn hoang, về tình yêu đôi lứa mộc mạc và tâm hồn khoáng đạt của người dân sông nước.\r\n\r\nTrải nghiệm: Hãy thử một lần ngồi nghe hát dưới ánh trăng rằm bên bờ sông Hậu, bạn sẽ cảm nhận được sự tinh tế trong tiếng đàn kìm réo rắt và sự chân thành trong lời ca, thấm đẫm tình đất, tình người của một vùng phù sa trù phú.', 'https://www.youtube.com/embed/FPOT4Kde--k', '/image/t6.jpg', 'nghethuat'),
(7, 'Nghề Truyền thống An Giang', 'An Giang sở hữu những làng nghề \"cha truyền con nối\" hàng trăm năm tuổi, gắn liền với biểu tượng cây thốt nốt vươn cao giữa trời xanh.\r\n\r\nChiều sâu văn hóa: Nghề truyền thống là minh chứng cho sự bền bỉ và khéo léo của người lao động. Với người chưa biết: Bạn nên chứng kiến cảnh người thợ leo lên ngọn cây cao vút chỉ với những chiếc thang tre đơn sơ để hứng từng giọt nước nhụy hoa thốt nốt mang về nấu đường thủ công.\r\n\r\nTrải nghiệm: Vị ngọt thanh mát, thơm nồng của đường thốt nốt Tri Tôn hay sự tinh xảo của làng nghề đúc lư đồng, nghề rèn Phú Mỹ sẽ khiến bạn nể phục. Từng thỏi đường vàng ươm hay món đồ đồng sáng bóng không chỉ là sản phẩm kinh doanh mà là tâm huyết gìn giữ hồn cốt quê hương qua bao thế hệ.', 'https://www.youtube.com/embed/0jcjIhLbs5I', '/image/t7.jpg', 'langnghe'),
(8, 'Nghề Nuôi Cá Bè trên Sông', 'Đây là một kiểu định cư và sinh kế độc đáo: nhà ở ngay trên lồng nuôi cá nổi bồng bềnh giữa dòng sông Hậu hiền hòa.\r\n\r\nChiều sâu văn hóa: Nó phản ánh sự thích nghi tuyệt vời của con người với môi trường sông nước Mekong. Với người chưa biết: Bạn sẽ thấy hàng trăm ngôi nhà san sát như một \"thành phố nổi\" kéo dài hàng cây số. Phía dưới sàn gỗ bạn đứng là hàng triệu con cá basa, cá tra đang bơi lội.\r\n\r\nTrải nghiệm: Trải nghiệm thú vị nhất là đi thuyền len lỏi giữa làng bè vào lúc chiều tà, tự tay rải mồi cho cá ăn và nghe những câu chuyện về nghề nuôi cá basa vang danh khắp thế giới. Ánh đèn từ các nhà bè phản chiếu xuống mặt sông tạo nên một khung cảnh lung linh, thơ mộng khó quên.', 'https://www.youtube.com/embed/QCt9ID-99VU', '/image/t8.jpg', 'sonnuoc'),
(9, 'Làng Chăm Châu Phong', 'Nằm yên bình bên kia sông Hậu, làng Chăm Châu Phong đưa bạn vào một không gian đậm chất Hồi giáo (Islam) với những quy tắc văn hóa nghiêm cẩn nhưng vô cùng mến khách.\r\n\r\nChiều sâu văn hóa: Đây là nơi lưu giữ những giá trị truyền thống từ kiến trúc đến ẩm thực của cộng đồng người Chăm. Người chưa biết sẽ ấn tượng với những thánh đường có mái vòm \"củ hành\" uy nghi và các ngôi nhà sàn gỗ cao ráo có kiến trúc cửa sổ đặc biệt.\r\n\r\nTrải nghiệm: Bạn sẽ gặp những cô gái Chăm khéo léo dệt thổ cẩm bên khung cửi và thưởng thức món \"Cơm Nị - Cà Púa\" (thịt bò nấu gia vị nồng nàn). Đây là điểm đến tuyệt vời để bạn sống chậm lại, quan sát nếp sống hiền lành và cảm nhận sự giao thoa văn hóa đặc sắc qua những tấm khăn thêu tay tinh xảo.', 'https://www.youtube.com/embed/l9B2CtnH6VQ', '/image/t9.jpg', 'dantoc'),
(10, 'Kiến trúc chùa Khmer – Xvayton', 'Chùa Xvayton (Tri Tôn) là ngôi chùa cổ nhất và là biểu tượng kiến trúc Phật giáo Nam tông Khmer rực rỡ bậc nhất tại An Giang.\r\n\r\nChiều sâu văn hóa: Chùa không chỉ là nơi thờ tự mà còn là một trung tâm giáo dục, nơi lưu giữ bộ \"Kinh lá buông\" quý hiếm được khắc trên lá thốt nốt. Với người chưa biết: Mái chùa có 3 tầng xếp chồng lên nhau, các góc mái uốn cong hình đuôi rồng Nagar rực rỡ vươn lên bầu trời.\r\n\r\nTrải nghiệm: Đến đây để chiêm ngưỡng những bức phù điêu chim thần Krud chạm trổ vàng rực và cảm nhận không gian tĩnh lặng, uy nghiêm. Từng chi tiết chạm khắc nhỏ nhất ở đây đều chứa đựng triết lý nhà Phật và tài hoa hội họa của người Khmer xưa, mang đến cảm giác thư thái và ngưỡng mộ.', 'https://www.youtube.com/embed/v3iewdRKW6Y', '/image/t10.jpg', 'dantoc'),
(11, 'Ẩm thực – Bún cá Châu Đốc', 'Đây là món ăn \"nhập môn\" mà bất kỳ ai chưa biết về An Giang phải thử ngay để cảm nhận vị ngon của phù sa bồi đắp.\r\n\r\nChiều sâu văn hóa: Món ăn là sự kết hợp tinh tế giữa nguyên liệu của người Kinh và gia vị ngải bún của người Khmer. Sự khác biệt nằm ở nước dùng vàng óng từ nghệ, mùi thơm của ngải bún và cá lóc đồng tươi rói được róc xương kỹ lưỡng.\r\n\r\nTrải nghiệm: Điểm nhấn rõ nhất là khi bát bún cá được dọn ra cùng đĩa bông điên điển vàng tươi (mùa nước nổi) và rau nhút giòn sần sật. Vị ngọt thanh của cá hòa quyện với vị đậm đà của mắm cá linh tạo nên một hương vị sông nước nồng nàn, gói trọn tinh hoa ẩm thực mà chỉ An Giang mới có đúng vị.', 'https://www.youtube.com/embed/O8cw5pcKtt8', '/image/t11.jpg', 'sonnuoc'),
(12, 'Nghề dệt thổ cẩm Tân Châu', 'Tân Châu từ lâu đã nổi tiếng với xứ sở của lụa Lãnh Mỹ A huyền thoại – loại vải đen huyền óng ả được nhuộm từ trái mặc nưa hoàn toàn tự nhiên.\r\n\r\nChiều sâu văn hóa: Đây là sản phẩm thủ công cao cấp đòi hỏi sự kiên nhẫn và kỹ thuật thượng thừa. Với người chưa biết: Để có một tấm lụa bóng đẹp như gương, người nghệ nhân phải nhuộm và phơi hàng chục lần dưới cái nắng cháy da cháy thịt của miền Tây.\r\n\r\nTrải nghiệm: Sờ tay vào tấm lụa Tân Châu, bạn sẽ cảm thấy sự mềm mịn, mát rượi đặc trưng. Từng tấm thổ cẩm với hoa văn tinh xảo không chỉ là trang phục mà còn là biểu tượng của sự sang trọng, tinh tế, là món quà quý giá mang đậm dấu ấn của những bàn tay tài hoa xứ biên thùy.', 'https://www.youtube.com/embed/-Fek_Ctl18w', '/image/t12.jpg', 'langnghe'),
(13, 'Chợ nổi Long Xuyên', 'Khác với sự xô bồ thương mại hóa của nhiều nơi khác, chợ nổi Long Xuyên vẫn giữ được vẻ nguyên sơ và cách mua bán \"chân lấm tay bùn\" của người dân miền Tây.\r\n\r\nChiều sâu văn hóa: Chợ nổi phản ánh tập quán giao thương đường thủy lâu đời. Người chưa biết hãy nhìn vào những chiếc \"cây bẹo\" – cây sào treo nông sản gì thì ghe đó bán thứ đó (như dưa hấu, khoai, khóm).\r\n\r\nTrải nghiệm: Hãy thuê một chiếc xuồng nhỏ lúc tờ mờ sáng khi sương còn trên mặt sông, ngồi ăn tô hủ tiếu nóng hổi dập dềnh theo con sóng và nghe tiếng rao hàng của các thương hồ. Bạn sẽ cảm nhận được trọn vẹn sự hiền lành, hiếu khách của những con người sống đời gạo chợ nước sông.', 'https://www.youtube.com/embed/RO7BbLnO92I', '/image/t13.jpg', 'sonnuoc'),
(14, 'Nghệ thuật Đờn ca tài tử', 'Được UNESCO vinh danh là Di sản văn hóa nhân loại, Đờn ca tài tử An Giang mang trong mình một sức sống mãnh liệt, vừa bác học vừa dân dã.\r\n\r\nChiều sâu văn hóa: Đây là âm nhạc dành cho các \"tài tử\" (người có tài năng nhưng không chuyên nghiệp). Với người chưa biết: Những người nông dân ngày làm ruộng, tối về tụ họp bên tách trà cùng đàn kìm, đàn tranh để kể chuyện đời.\r\n\r\nTrải nghiệm: Giai điệu trầm bổng, sâu lắng kể về tình yêu quê hương và lòng chung thủy. Khi nghe một bản dạ cổ hay một điệu nam ai giữa không gian mênh mông sông nước, bạn sẽ thấy mình hòa quyện vào tâm hồn đa cảm, hào hiệp của con người vùng đất phù sa trù phú này.', 'https://www.youtube.com/embed/uB6ye79H9C0', '/image/t14.jpg', 'nghethuat'),
(15, 'Lễ hội Đua ghe ngo', 'Ghe ngo là chiếc thuyền dài hơn 30 mét, chạm trổ rực rỡ hình rồng rắn, được coi là vật báu thiêng liêng của mỗi ngôi chùa Khmer.\r\n\r\nChiều sâu văn hóa: Đây là cuộc đua của sức mạnh và sự đồng lòng tuyệt đối của cả một phum sóc (ngôi làng). Với người chưa biết: Mỗi chiếc ghe có khoảng 50-60 tay chèo lực lưỡng, bơi theo nhịp dầm và tiếng nhạc ngũ âm sôi động thúc giục.\r\n\r\nTrải nghiệm: Cuộc đua diễn ra rực rỡ trên dòng sông vào dịp lễ Ok Om Bok, tạo nên một cảnh tượng cực kỳ hoành tráng với hàng vạn người hò reo vang dội. Bạn sẽ bị lôi cuốn bởi nhịp chèo mạnh mẽ và khát khao chiến thắng mãnh liệt, cầu mong thần nước ban cho vụ mùa bội thu và sức khỏe.', 'https://www.youtube.com/embed/crZF3hghbgQ', '/image/t15.jpg', 'lehoi'),
(16, 'Lễ hội Chol Chnam Thmay', 'Đây là Tết lớn nhất của người Khmer (diễn ra giữa tháng 4 dương lịch), đánh dấu thời khắc giao mùa từ mùa khô sang mùa mưa.\r\n\r\nChiều sâu văn hóa: Lễ hội mang ý nghĩa tạ ơn tổ tiên và cầu chúc cho vạn vật sinh sôi. Với người chưa biết: Không khí hội hè sẽ tràn ngập các ngôi chùa với nghi thức \"đắp núi cát\" để tích đức và \"tắm Phật\" để xua đuổi điều không may.\r\n\r\nTrải nghiệm: Ghé thăm các ngôi chùa Khmer vào dịp này, bạn sẽ được xem múa Lăm Vông dưới ánh đèn lung linh, tham gia các trò chơi dân gian và thưởng thức bánh gừng đặc sản. Đây là lúc bạn thấy rõ nhất sự gắn kết cộng đồng và niềm hy vọng tràn đầy của người dân vào một năm mới an lành.', 'https://www.youtube.com/embed/mBnyZhEc21I', '/image/t1.jpg', 'lehoi'),
(17, 'Lễ hội Ok Om Bok', 'Tổ chức vào rằm tháng 10 âm lịch, đây là dịp để người dân tạ ơn thần Mặt Trăng đã bảo vệ mùa màng và mang lại ấm no.\r\n\r\nChiều sâu văn hóa: Lễ hội thể hiện sự tôn trọng thiên nhiên sâu sắc của cư dân lúa nước. Người chưa biết nên tham gia vào đêm chính: Người dân dâng cúng cốm dẹp, trái cây và thực hiện nghi thức đút cốm dẹp cho trẻ nhỏ để cầu chúc sự khôn lớn.\r\n\r\nTrải nghiệm: Điểm đẹp nhất là lễ thả đèn nước trên sông, nơi hàng ngàn chiếc đèn mang theo lời cầu nguyện trôi lững lờ dưới ánh trăng rằm sáng rực. Không gian huyền ảo, ánh sáng lấp lánh trên mặt nước sông Hậu sẽ để lại một ấn tượng tâm linh sâu sắc và thơ mộng, kết thúc một hành trình khám phá văn hóa An Giang đầy cảm xúc.', 'https://www.youtube.com/embed/Vq-KN5ANW1I', '/image/t2.jpg', 'lehoi'),
(20, 'TEST', 'aaaaaaaaaaaaaaaaaaaaaaaa', 'https://www.youtube.com/embed/8sVtL0o-v7U', '/image/b2.jpg', 'langnghe');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`BookingID`),
  ADD KEY `FKn47inysublqsg9gsie1eredhw` (`CustomerID`),
  ADD KEY `FKfd5nt9xvi3i9n5fh1lsudyfat` (`TourID`),
  ADD KEY `FKjewbd5lhrsmka3tr9xetjwgq` (`GuideID`);

--
-- Chỉ mục cho bảng `contacts`
--
ALTER TABLE `contacts`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`CustomerID`);

--
-- Chỉ mục cho bảng `destinations`
--
ALTER TABLE `destinations`
  ADD PRIMARY KEY (`DestinationID`);

--
-- Chỉ mục cho bảng `foods`
--
ALTER TABLE `foods`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`MessageID`);

--
-- Chỉ mục cho bảng `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`PaymentID`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`RoleID`);

--
-- Chỉ mục cho bảng `tourdestinations`
--
ALTER TABLE `tourdestinations`
  ADD PRIMARY KEY (`TourID`,`DestinationID`);

--
-- Chỉ mục cho bảng `tourguides`
--
ALTER TABLE `tourguides`
  ADD PRIMARY KEY (`GuideID`);

--
-- Chỉ mục cho bảng `tourguidesassignments`
--
ALTER TABLE `tourguidesassignments`
  ADD PRIMARY KEY (`TourID`,`GuideID`);

--
-- Chỉ mục cho bảng `tours`
--
ALTER TABLE `tours`
  ADD PRIMARY KEY (`TourID`),
  ADD KEY `FKnjl8dl0qurcujpu5vprai88s2` (`GuideID`);

--
-- Chỉ mục cho bảng `tourschedules`
--
ALTER TABLE `tourschedules`
  ADD PRIMARY KEY (`ScheduleID`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`);

--
-- Chỉ mục cho bảng `vanhoa`
--
ALTER TABLE `vanhoa`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `bookings`
--
ALTER TABLE `bookings`
  MODIFY `BookingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT cho bảng `contacts`
--
ALTER TABLE `contacts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `customers`
--
ALTER TABLE `customers`
  MODIFY `CustomerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT cho bảng `destinations`
--
ALTER TABLE `destinations`
  MODIFY `DestinationID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `foods`
--
ALTER TABLE `foods`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT cho bảng `messages`
--
ALTER TABLE `messages`
  MODIFY `MessageID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT cho bảng `tourguides`
--
ALTER TABLE `tourguides`
  MODIFY `GuideID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `vanhoa`
--
ALTER TABLE `vanhoa`
  MODIFY `Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `FKfd5nt9xvi3i9n5fh1lsudyfat` FOREIGN KEY (`TourID`) REFERENCES `tours` (`TourID`),
  ADD CONSTRAINT `FKjewbd5lhrsmka3tr9xetjwgq` FOREIGN KEY (`GuideID`) REFERENCES `tourguides` (`GuideID`),
  ADD CONSTRAINT `FKn47inysublqsg9gsie1eredhw` FOREIGN KEY (`CustomerID`) REFERENCES `customers` (`CustomerID`);

--
-- Các ràng buộc cho bảng `tours`
--
ALTER TABLE `tours`
  ADD CONSTRAINT `FKnjl8dl0qurcujpu5vprai88s2` FOREIGN KEY (`GuideID`) REFERENCES `tourguides` (`GuideID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
