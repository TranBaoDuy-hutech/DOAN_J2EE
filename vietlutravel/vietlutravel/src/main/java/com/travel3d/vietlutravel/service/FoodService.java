package com.travel3d.vietlutravel.service;

import com.travel3d.vietlutravel.model.Food;
import com.travel3d.vietlutravel.repository.FoodRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public Food getFoodById(Long id) {
        Optional<Food> food = foodRepository.findById(id);
        return food.orElse(null);
    }

    @PostConstruct
    @Transactional
    public void initData() {
        if (foodRepository.count() > 0) {
        }

        if (foodRepository.count() == 0) {
            List<Food> foods = new ArrayList<>();

            foods.add(createFood("Gà Đốt Ô Thum",
                    "Món gà đốt trứ danh của vùng đất Ô Thum, Tri Tôn. Gà vườn được tẩm ướp gia vị đặc trưng gồm lá chúc, sả, ớt, tỏi rồi đốt trong nồi đất đến khi da vàng giòn, thịt ngọt đậm đà. Ăn kèm gỏi hoa chuối và muối ớt chanh.",
                    "/models/ga.glb", "/models/posters/ga.jpg", "Gà Đốt Ô Thum đặc sản An Giang",
                    "250.000đ - 300.000đ/con", "Gà ta, Lá chúc, Sả, Ớt, Tỏi",
                    "Gà đốt Ô Thum có nguồn gốc từ người Khmer vùng Bảy Núi. Bí quyết nằm ở lá chúc (chanh sần) tạo nên hương thơm độc bản không nơi nào có.",
                    4.9, 320));

            foods.add(createFood("Bánh Bò Thốt Nốt",
                    "Bánh bò thốt nốt vàng ươm, thơm lừng vị đường thốt nốt nguyên chất. Bánh có độ xốp mềm, vị ngọt thanh tao, béo nhẹ của nước cốt dừa. Là thức quà quê dân dã nhưng khó quên.",
                    "/models/thotnot.glb", "/models/posters/thotnot.jpg", "Bánh Bò Thốt Nốt đặc sản An Giang",
                    "5.000đ - 10.000đ/cái", "Bột gạo, Đường thốt nốt, Nước cốt dừa",
                    "Món bánh dân dã gắn liền với cây thốt nốt - linh hồn của vùng đất An Giang.", 4.8, 512));

            foods.add(createFood("Bún Quậy An Giang",
                    "Một biến tấu thú vị của món bún nước lèo. Nước dùng thanh ngọt từ hải sản, chả cá tự làm dai ngon. Thực khách tự pha nước chấm 'quậy' theo khẩu vị riêng.",
                    "/models/bunquay.glb", "/models/posters/bunquay.jpg", "Bún Quậy An Giang",
                    "35.000đ - 55.000đ", "Bún tươi, Chả cá thác lác, Mực, Tôm",
                    "Bún quậy đòi hỏi thực khách phải 'quậy' nước chấm, tạo nên trải nghiệm ăn uống thú vị và tương tác.",
                    4.5, 180));

            foods.add(createFood("Trái Thốt Nốt",
                    "Trái thốt nốt tươi với cơm mềm, dẻo, ngọt mát như thạch, giải nhiệt cực tốt. Nước thốt nốt ngọt thanh tự nhiên, thơm dịu.",
                    "/models/traithotnot.glb", "/models/posters/traithotnot.jpg", "Trái Thốt Nốt An Giang",
                    "20.000đ/chục", "Trái thốt nốt tươi",
                    "Cây thốt nốt là biểu tượng của vùng Bảy Núi, mọi bộ phận của cây đều có thể dùng được.", 4.7, 80));

            foods.add(createFood("Bò Leo Núi",
                    "Món ăn độc đáo vùng Tân Châu. Bò được tẩm ướp kỹ, nướng trên vỉ nướng chuyên dụng có hình dạng đặc biệt giống ngọn núi. Thịt bò mềm, ngọt ăn kèm rau rừng và trứng gà.",
                    "/models/boleonui.glb", "/models/posters/boleonui.jpg", "Bò Leo Núi An Giang",
                    "150.000đ - 200.000đ/phần", "Thịt bò tơ, Trứng gà, Bơ, Rau rừng",
                    "Tên gọi 'Bò leo núi' xuất phát từ chiếc vỉ nướng nhô cao như ngọn núi, giữ nhiệt tốt giúp thịt chín đều mềm ngọt.",
                    4.8, 215));

            foods.add(createFood("Bún Cá Châu Đốc",
                    "Đặc sản nổi tiếng miền Tây. Nước lèo vàng nghệ đậm đà, cá lóc đồng tươi ngon, ăn kèm bông điên điển và rau sống.",
                    "/models/bunca.glb", "/models/posters/bunca.jpg", "Bún Cá Châu Đốc - An Giang",
                    "25.000đ - 40.000đ", "Cá lóc đồng, Nghệ tươi, Bông điên điển",
                    "Món ăn thể hiện sự trù phú của miền Tây mùa nước nổi với cá đồng và bông điên điển.", 4.6, 900));

            foods.add(createFood("Cơm Tấm Long Xuyên",
                    "Hạt tấm nhuyễn đặc trưng, sườn rim đậm vị, bì thơm, trứng kho thấm đều. Kèm chén nước mắm kẹo và đồ chua làm nên hương vị khó lẫn.",
                    "/models/comtam.glb", "/models/posters/comtam.jpg", "Cơm Tấm Long Xuyên - An Giang",
                    "30.000đ - 60.000đ", "Gạo tấm nhuyễn, Sườn heo, Trứng kho, Bì",
                    "Khác với Sài Gòn, cơm tấm Long Xuyên dùng gạo tấm rất nhuyễn và thịt sườn được rim mềm cắt nhỏ.",
                    4.9, 1200));

            foods.add(createFood("Lẩu Mắm Châu Đốc",
                    "Lẩu mắm đậm đà hương vị mắm cá linh, cá sặc. Ăn kèm với hàng chục loại rau đồng nội, cá hú, thịt ba chỉ, tôm, mực.",
                    "/models/laumam.glb", "/models/posters/laumam.jpg", "Lẩu Mắm Châu Đốc - An Giang",
                    "200.000đ - 400.000đ", "Mắm cá linh, Cá hú, Thịt ba chỉ, Rau đồng",
                    "Châu Đốc là vương quốc mắm, và lẩu mắm là bản giao hưởng của các loại mắm ngon nhất nơi đây.", 4.7,
                    560));

            foods.add(createFood("Bò Bảy Món Núi Sam",
                    "Bảy món bò trứ danh gồm bò lụi, bò nướng lá lốt, cháo bò... Nguyên liệu bò tơ tươi ngon vùng Bảy Núi.",
                    "/models/bo7.glb", "/models/posters/bo7.jpg", "Bò bảy món Núi Sam - An Giang",
                    "100.000đ - 150.000đ/món", "Thịt bò tơ, Lá lốt, Mỡ chài",
                    "Đặc sản trứ danh phục vụ khách hành hương vía Bà Chúa Xứ Núi Sam.", 4.6, 340));

            foods.add(createFood("Bánh Xèo",
                    "Bánh xèo miền Tây vỏ mỏng giòn rụm, nhân tôm thịt, giá sống. Ăn kèm hàng tá loại rau rừng tươi xanh.",
                    "/models/banhxeo.glb", "/models/posters/banhxeo.jpg", "Bánh Xèo - An Giang",
                    "20.000đ - 40.000đ/cái", "Bột gạo, Nghệ, Tôm, Thịt ba rọi, Giá",
                    "Bánh xèo mỗi miền mỗi khác, bánh xèo An Giang nổi tiếng với rau rừng ăn kèm phong phú nhất.", 4.8,
                    780));

            foods.add(createFood("Đường Thốt Nốt",
                    "Đặc sản trứ danh vùng Bảy Núi. Đường nấu thủ công từ mật hoa thốt nốt, vị ngọt thanh, dùng nấu chè hay kho cá đều tuyệt hảo.",
                    "/models/duong.glb", "/models/posters/duong.jpg", "Đường Thốt Nốt - An Giang",
                    "35.000đ - 50.000đ/kg", "Mật hoa thốt nốt 100%",
                    "Nghề nấu đường thốt nốt là di sản văn hóa phi vật thể quốc gia, gắn liền với đời sống người Khmer.",
                    5.0, 410));

            foods.add(createFood("Trái Chúc",
                    "Loại chanh rừng vỏ xù xì nhưng cực thơm, đặc sản vùng Bảy Núi. Lá và trái chúc là gia vị không thể thiếu trong món gà đốt hay cháo bò.",
                    "/models/traichuc.glb", "/models/posters/traichuc.jpg", "Trái Chúc - An Giang",
                    "50.000đ - 80.000đ/kg", "Trái chúc tươi",
                    "Cây chúc mọc hoang dã ở vùng Bảy Núi, được xem là loại gia vị 'linh hồn' của ẩm thực nơi đây.",
                    4.9, 150));

            // Thêm các món còn lại với dữ liệu giả lập chèn vào
            foods.add(createFood("Xôi Phồng Chợ Mới", "Xôi chiên phồng giòn rụm, bên trong dẻo thơm, ăn kèm gà quay.",
                    "/models/xoiphongchomoi.glb", "/models/posters/xoiphongchomoi.jpg", "Xôi Phồng Chợ Mới - An Giang",
                    "60.000đ - 120.000đ", "Nếp dẻo, Đậu xanh, Gà quay",
                    "Kỹ thuật chiên xôi phồng điêu luyện tạo nên quả cầu vàng ươm đẹp mắt.", 4.7, 220));
            foods.add(createFood("Bánh Chăm", "Loại bánh truyền thống của người Chăm An Giang.", "/models/banhcham.glb",
                    "/models/posters/banhcham.jpg", "Bánh Chăm - An Giang", "5.000đ - 10.000đ", "Bột gạo, Đường, Trứng",
                    "Món bánh không thể thiếu trong các dịp lễ hội của đồng bào Chăm.", 4.5, 90));
            foods.add(createFood("Bánh Canh Vĩnh Trung", "Sợi bánh canh dai đặc biệt, nước dùng ngọt thanh.",
                    "/models/banhcanh.glb", "/models/posters/banhcanh.jpg", "Bánh Canh Vĩnh Trung - An Giang",
                    "30.000đ - 40.000đ", "Bột gạo Neang Nhen, Cá lóc, Giò heo",
                    "Sợi bánh canh dẹp, dai, mềm được làm từ loại gạo lúa mùa đặc sản vùng Bảy Núi.", 4.6, 310));
            foods.add(createFood("Khô Rắn An Phú", "Đặc sản mùa nước nổi, vị ngọt, dai ngon.",
                    "/models/khorananphu.glb", "/models/posters/khorananphu.jpg", "Khô rắn An Phú - An Giang",
                    "300.000đ - 500.000đ/kg", "Rắn bông súng, Rắn nước",
                    "Mùa nước nổi là mùa của các loại rắn, người dân chế biến thành khô để dành ăn quanh năm.", 4.8,
                    140));
            foods.add(createFood("Bánh Mằn Dè", "Bánh dân gian độc lạ vùng biên giới.", "/models/banhmande.glb",
                    "/models/posters/banhmande.jpg", "Bánh Mằn Dè - An Giang", "5.000đ", "Bột nếp, Đậu xanh, Dừa",
                    "Loại bánh ít người biết đến nhưng hương vị rất mộc mạc, đậm chất quê.", 4.4, 45));
            foods.add(createFood("Bánh Hạnh Nhân", "Bánh giòn tan, thơm béo vị hạnh nhân.", "/models/banhhanhnhan.glb",
                    "/models/posters/banhhanhnhan.jpg", "Bánh Hạnh Nhân - An Giang", "50.000đ/hộp",
                    "Bột mì, Hạnh nhân, Đường, Bơ", "Món bánh tây được Việt hóa, trở thành đặc sản biếu tặng phổ biến.",
                    4.6, 280));
            foods.add(createFood("Gỏi Cá Trích", "Cá trích tươi tái chanh, trộn gỏi chua ngọt.",
                    "/models/goicatrich.glb", "/models/posters/goicatrich.jpg", "Gỏi Cá Trích - An Giang",
                    "100.000đ/dĩa", "Cá trích tươi, Dừa nạo, Đậu phộng, Hành tây",
                    "Món ăn tươi sống đòi hỏi cá phải thật tươi, xử lý kỹ để không bị tanh.", 4.7, 190));
            foods.add(createFood("Cơm Ghẹ", "Cơm chiên với thịt ghẹ tươi ngọt.", "/models/comghe.glb",
                    "/models/posters/comghe.jpg", "Cơm Ghẹ - An Giang", "60.000đ - 80.000đ",
                    "Gạo thơm, Thịt ghẹ, Rau củ", "Hương vị biển khơi hòa quyện trong từng hạt cơm chiên săn chắc.",
                    4.6, 250));
            foods.add(createFood("Nước Mắm Phú Quốc", "Nước mắm nhỉ cá cơm thượng hạng.", "/models/nuocmam.glb",
                    "/models/posters/nuocmam.jpg", "Nước Mắm Phú Quốc - An Giang", "150.000đ/lít", "Cá cơm, Muối biển",
                    "Quốc hồn quốc túy, gia vị không thể thiếu trong bữa cơm người Việt.", 5.0, 1500));
            foods.add(createFood("Tiêu Phú Quốc", "Tiêu sọ cay nồng đặc trưng.", "/models/tieu.glb",
                    "/models/posters/tieu.jpg", "Tiêu Phú Quốc - An Giang", "300.000đ/kg", "Hạt tiêu chín",
                    "Tiêu Phú Quốc nổi tiếng vỏ mẩy, hạt chắc, cay nồng và thơm đậm đà.", 4.9, 860));
            foods.add(createFood("Bánh Tét Mật Cật", "Bánh tét gói bằng lá mật cật độc đáo.", "/models/banhtet.glb",
                    "/models/posters/banhtet.jpg", "Bánh Tét Mật Cật - An Giang", "150.000đ/đòn",
                    "Nếp, Đậu xanh, Thịt mỡ, Lá mật cật",
                    "Lá mật cật giúp bánh có màu xanh ngọc bích đẹp mắt và hương thơm đặc trưng.", 4.8, 420));
            foods.add(createFood("Bún Kèn Hà Tiên", "Bún nước lèo nấu từ cá và nước cốt dừa.", "/models/bunken.glb",
                    "/models/posters/bunken.jpg", "Bún Kèn Hà Tiên - An Giang", "30.000đ",
                    "Cá nhồng, Nước cốt dừa, Bột cà ri, Bún",
                    "Vị béo ngậy của nước cốt dừa hòa quyện vị đậm đà của cá biển tạo nên món bún kèn lạ miệng.", 4.7,
                    180));
            foods.add(createFood("Hải Sản Hà Tiên", "Tôm cua ghẹ tươi sống vùng biển Kiên Giang.", "/models/haisan.glb",
                    "/models/posters/haisan.jpg", "Hải Sản Hà Tiên, Phú Quốc - An Giang", "Thời giá",
                    "Tôm, Cua, Ghẹ, Mực",
                    "Hải sản tươi sống vừa đánh bắt, chế biến đơn giản giữ trọn vị ngọt tự nhiên.", 4.8, 670));
            foods.add(createFood("Bánh Ống Lá Dứa", "Bánh ống thơm lừng mùi lá dứa và dừa nạo.", "/models/banhduc.glb",
                    "/models/posters/banhduc.jpg", "Bánh ống lá dứa - An Giang", "5.000đ/cái",
                    "Bột gạo, Lá dứa, Dừa nạo, Muối mè",
                    "Tiếng còi tu tu của xe bán bánh ống là ký ức tuổi thơ của biết bao người miền Tây.", 4.6, 530));
            foods.add(createFood("Thịt Chuột Đồng", "Đặc sản mùa lúa, chuột đồng quay lu béo ngậy.",
                    "/models/chuot.glb", "/models/posters/chuot.jpg", "Thịt chuột đồng quay lu - An Giang",
                    "120.000đ/dĩa", "Chuột đồng, Sả ớt, Gia vị",
                    "Món ăn thử thách lòng can đảm nhưng hương vị thơm ngon hơn cả thịt gà.", 4.5, 210));
            foods.add(createFood("Tung Lò Mò", "Lạp xưởng bò đặc biệt của người Chăm.", "/models/lapxuong.glb",
                    "/models/posters/lapxuong.jpg", "Tung lò mò (Lạp xưởng bò Chăm) - An Giang", "280.000đ/kg",
                    "Thịt bò, Mỡ bò, Cơm nguội, Ruột bò",
                    "Món ăn chứa đựng tinh hoa ẩm thực của văn hóa Chăm Islam ở An Giang.", 4.8, 300));

            foodRepository.saveAll(foods);
        }
    }

    private Food createFood(String name, String description, String modelPath, String posterPath, String altText,
                            String price, String ingredients, String story, Double rating, Integer reviewsCount) {
        Food food = new Food();
        food.setName(name);
        food.setDescription(description);
        food.setModelPath(modelPath);
        food.setPosterPath(posterPath);
        food.setAltText(altText);
        food.setPrice(price);
        food.setIngredients(ingredients);
        food.setStory(story);
        food.setRating(rating);
        food.setReviewsCount(reviewsCount);
        return food;
    }
}