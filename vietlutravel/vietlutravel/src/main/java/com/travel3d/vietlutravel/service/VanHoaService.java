package com.travel3d.vietlutravel.service;

import com.travel3d.vietlutravel.model.VanHoa;
import com.travel3d.vietlutravel.repository.VanHoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VanHoaService {

    private final VanHoaRepository vanHoaRepository;

    public VanHoaService(VanHoaRepository vanHoaRepository) {
        this.vanHoaRepository = vanHoaRepository;
    }

    // ─── INIT DATA ───
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (vanHoaRepository.count() == 0) {
                vanHoaRepository.saveAll(List.of(
                        new VanHoa("Lễ hội Vía Bà Chúa Xứ Núi Sam",
                                "Nằm tại chân núi Sam (Châu Đốc), đây là trung tâm hành hương lớn nhất Nam Bộ với pho tượng đá sa thạch quý hiếm có từ thế kỷ thứ 6. Lễ hội chính diễn ra từ 22-27/4 âm lịch, thu hút hàng triệu người. Với người chưa biết: Điểm nhấn 'rõ nhất' là lễ Tắm Bà đêm 23 rạng sáng 24, khi áo bào cũ của Bà được gỡ bỏ và thay mới trong làn nước thơm hoa bưởi. Đến đây, bạn không chỉ xin lộc mà còn để chiêm ngưỡng kiến trúc 'nội công ngoại quốc' rực rỡ và cảm nhận niềm tin mãnh liệt của dòng người đổ về cầu mong gia đạo bình an.",
                                "https://www.youtube.com/embed/bQAE5eFkCJo", "/image/t1.jpg", "lehoi"),

                        new VanHoa("Lễ hội Đua bò Bảy Núi",
                                "Đây không phải cuộc đua trên đường bằng mà là cuộc đua 'nghẹt thở' trên những thửa ruộng sình lầy tại Tri Tôn và Tịnh Biên vào dịp lễ Sene Dolta (tháng 8-9 âm lịch). Người chưa biết cần hình dung: Mỗi đôi bò kéo một chiếc bừa gỗ, người điều khiển (tài xế) phải đứng vững trên đó và điều khiển bò lao đi với tốc độ cực cao. Bùn đất bắn tung tóe trong tiếng hò reo vang trời tạo nên một bầu không khí rực lửa. Đây là cách người Khmer tri ân đôi bò đã giúp họ cày cấy suốt mùa vụ.",
                                "https://www.youtube.com/embed/sUqJd4rlLf0", "/image/t2.jpg", "lehoi"),

                        new VanHoa("Văn hóa Thất Sơn (Bảy Núi)",
                                "Thất Sơn là cụm 7 ngọn núi nổi lên giữa đồng bằng mênh mông, gắn liền với các đạo sĩ, bậc tu hành và những câu chuyện kỳ bí về hổ mây, mãng xà xưa kia. Nếu bạn chưa biết: Núi Cấm là ngọn cao nhất, được mệnh danh là 'Đà Lạt của miền Tây' nhờ khí hậu mát mẻ. Trải nghiệm rõ nhất là đi cáp treo xuyên qua mây để ngắm hồ Thủy Liêm xanh ngắt và tượng Phật Di Lặc khổng lồ, cảm nhận sự giao thoa giữa thiên nhiên hùng vĩ và không gian tâm linh huyền bí.",
                                "https://www.youtube.com/embed/wuXPRtxie3E", "/image/t3.jpg", "dantoc"),

                        new VanHoa("Văn hóa Tín ngưỡng – Tôn giáo",
                                "An Giang là 'bảo tàng sống' tôn giáo, nơi bạn có thể thấy chùa Khmer vàng rực nằm cạnh thánh đường Hồi giáo trắng muốt hay chùa Hòa Hảo giản dị. Điều đặc biệt dành cho người mới: Đây là vùng đất của sự bao dung. Bạn sẽ thấy người dân khác tôn giáo nhưng vẫn cùng nhau đi làm từ thiện, xây cầu, nấu cơm miễn phí. Hãy ghé thăm các ngôi đình cổ để thấy cách người dân thờ phụng những bậc tiền nhân có công khai phá vùng đất này như Thoại Ngọc Hầu hay Nguyễn Hữu Cảnh.",
                                "https://www.youtube.com/embed/wuXPRtxie3E", "/image/t4.jpg", "dantoc"),

                        new VanHoa("Văn hóa Đa dân tộc",
                                "Bức tranh này được vẽ nên bởi 4 sắc tộc: Kinh, Hoa, Chăm, Khmer sống hòa thuận hàng trăm năm. Người chưa biết có thể nhận diện qua trang phục: Người Kinh mặc áo bà ba, người Khmer quấn xà rông rực rỡ, người Chăm đội khăn Mat-ra thêu hoa và người Hoa giữ nét truyền thống trong các hội quán. Sự đa dạng này rõ nhất ở các khu chợ, nơi bạn có thể nghe được nhiều ngôn ngữ khác nhau và thấy các sản phẩm thủ công đặc trưng của từng dân tộc bày bán xen kẽ.",
                                "https://www.youtube.com/embed/jm_fmhtiSqU", "/image/t5.png", "dantoc"),

                        new VanHoa("Văn hóa Nghệ thuật Dân gian",
                                "Không cần sân khấu sang trọng, nghệ thuật An Giang vang lên từ bờ kênh, gốc cây. Đó là Đờn ca tài tử với tiếng đàn kìm, đàn tranh réo rắt kể về nỗi lòng người đi mở đất. Với người mới: Hãy thử một lần ngồi nghe hát dưới mái hiên nhà vào buổi tối, bạn sẽ thấy sự mộc mạc nhưng cực kỳ tinh tế. Ngoài ra còn có điệu múa Lăm Vông của người Khmer – điệu múa tập thể mà bất cứ ai cũng có thể hòa mình vào theo nhịp trống ngũ âm rộn rã.",
                                "https://www.youtube.com/embed/FPOT4Kde--k", "/image/t6.jpg", "nghethuat"),

                        new VanHoa("Nghề Truyền thống An Giang",
                                "An Giang là xứ sở của cây thốt nốt. Nghề làm đường thốt nốt đòi hỏi người thợ phải leo lên ngọn cây cao vút, hứng từng giọt nước hoa thốt nốt đem về nấu thủ công. Người chưa biết nên nếm thử một miếng đường tán màu vàng nhạt: vị ngọt thanh, thơm nồng, không gắt như đường cát. Bên cạnh đó là nghề mắm Châu Đốc nổi tiếng với hàng trăm loại mắm 'ăn một lần nhớ cả đời' như mắm thái, mắm cá chốt, mắm cá linh.",
                                "https://www.youtube.com/embed/0jcjIhLbs5I", "/image/t7.jpg", "langnghe"),

                        new VanHoa("Nghề Nuôi Cá Bè trên Sông",
                                "Bạn hãy tưởng tượng những ngôi nhà gỗ nổi bồng bềnh trên sông, bên dưới sàn nhà là hàng vạn con cá basa bơi lội. Đây là cách người dân thích nghi tuyệt vời với mùa nước nổi. Người chưa biết nên thuê thuyền nhỏ đi dọc sông Hậu buổi chiều hoàng hôn để thấy 'thành phố nổi' lên đèn, tự tay rải mồi cho cá ăn và nghe những câu chuyện về thời hoàng kim khi cá basa An Giang xuất khẩu đi khắp thế giới, nuôi sống bao đời cư dân sông nước.",
                                "https://www.youtube.com/embed/QCt9ID-99VU", "/image/t8.jpg", "sonnuoc"),

                        new VanHoa("Làng Chăm Châu Phong",
                                "Chỉ cần qua một chuyến phà từ Châu Đốc, bạn sẽ lạc vào thế giới của người Chăm Islam. Người chưa biết sẽ ấn tượng với những thánh đường Hồi giáo có mái vòm 'củ hành' uy nghi và những ngôi nhà sàn gỗ cổ kính. Điểm nhấn là nghề dệt thổ cẩm thủ công: các cô gái Chăm ngồi bên khung cửi dệt nên những chiếc khăn, túi xách màu sắc tinh tế. Đừng quên thử món Cơm Nị - Cà Púa, món ăn đậm đà gia vị truyền thống chỉ có ở những dịp lễ trọng đại của làng.",
                                "https://www.youtube.com/embed/l9B2CtnH6VQ", "/image/t9.jpg", "dantoc"),

                        new VanHoa("Kiến trúc chùa Khmer – Xvayton",
                                "Tọa lạc tại Tri Tôn, chùa Xvayton là ngôi chùa Khmer cổ nhất và là nơi lưu giữ nhiều bộ kinh lá buông quý hiếm nhất. Với người chưa biết kiến trúc Phật giáo Nam tông: Hãy chú ý vào mái chùa có 3 tầng uốn lượn, trên đỉnh là các tháp nhọn cao vút và hình tượng rồng Nagar xòe đầu bảo vệ. Chùa không chỉ là nơi thờ tự mà còn là 'trung tâm văn hóa' nơi dạy chữ, dạy đạo đức và là nơi diễn ra các lễ hội quan trọng nhất của cộng đồng người Khmer.",
                                "https://www.youtube.com/embed/v3iewdRKW6Y", "/image/t10.jpg", "dantoc"),

                        new VanHoa("Ẩm thực – Bún cá Châu Đốc",
                                "Đây là món ăn 'quốc hồn quốc túy' của An Giang. Sự khác biệt cho người mới nằm ở: Sợi bún trắng mịn hòa cùng nước dùng vàng óng từ nghệ, mùi thơm dịu của ngải bún và vị ngọt thanh của cá lóc đồng. Đặc biệt, món này ăn kèm với bông điên điển mùa nước nổi và rau nhút giòn sần sật. Thêm một chút mắm me chua ngọt, bát bún cá sẽ gói trọn hương vị của đất, của nước và lòng hiếu khách của người dân vùng biên giới.",
                                "https://www.youtube.com/embed/O8cw5pcKtt8", "/image/t11.jpg", "sonnuoc"),

                        new VanHoa("Nghề dệt thổ cẩm Tân Châu",
                                "Tân Châu nổi tiếng với lụa Lãnh Mỹ A đen huyền óng ả. Người chưa biết sẽ ngạc nhiên khi biết màu đen này không phải từ hóa chất mà từ trái mặc nưa tự nhiên. Quy trình dệt và nhuộm cực kỳ kỳ công: lụa được nhuộm, phơi nắng, rồi lại nhuộm hàng chục lần để có độ bóng bền bỉ. Mặc lụa Tân Châu vào mùa hè thì mát, mùa đông thì ấm, sờ vào thấy mềm mịn như nhung – một biểu tượng của sự sang trọng và tinh tế của phụ nữ Nam Bộ xưa.",
                                "https://www.youtube.com/embed/-Fek_Ctl18w", "/image/t12.jpg", "langnghe"),

                        new VanHoa("Chợ nổi Long Xuyên",
                                "Không quá ồn ào như chợ nổi Cái Răng, chợ nổi Long Xuyên bình dị và chân chất hơn. Người chưa biết cần lưu ý cách mua bán: Mỗi ghe có một chiếc 'cây bẹo', trên đó treo món đồ họ bán (như khoai, bí, dưa hấu) để khách nhìn từ xa. Hãy dậy từ 5h sáng, ngồi xuồng len lỏi giữa hàng trăm ghe thuyền, gọi một bát hủ tiếu nóng ngay trên mặt sông dập dềnh để cảm nhận trọn vẹn nhịp sống 'trên bến dưới thuyền' đặc trưng của miền Tây.",
                                "https://www.youtube.com/embed/RO7BbLnO92I", "/image/t13.jpg", "sonnuoc"),

                        new VanHoa("Nghệ thuật Đờn ca tài tử",
                                "Đây là dòng nhạc dân gian đã được UNESCO vinh danh. Với người chưa biết: Đây là âm nhạc dành cho 'tài tử' (người có tài nhưng không chuyên). Những người nông dân ban ngày làm ruộng, tối về tụ họp bên tách trà, dùng tiếng đàn kìm, đàn cò để kể chuyện đời, chuyện tình. Giai điệu lúc trầm lúc bổng, sâu lắng, phản ánh tâm hồn phóng khoáng nhưng cũng rất đỗi đa sầu đa cảm của người con vùng đất phù sa này.",
                                "https://www.youtube.com/embed/uB6ye79H9C0", "/image/t14.jpg", "nghethuat"),

                        new VanHoa("Lễ hội Đua ghe ngo",
                                "Ghe ngo là chiếc thuyền dài hơn 30 mét, trang trí họa tiết rồng rắn rực rỡ, là niềm tự hào của người Khmer. Với người chưa biết: Đây là cuộc đua sức mạnh và sự đoàn kết. Mỗi chiếc ghe có khoảng 50 tay chèo lực lưỡng, bơi theo nhịp dầm và tiếng nhạc ngũ âm sôi động. Cuộc đua diễn ra trên sông vào dịp lễ Ok Om Bok, tạo nên một cảnh tượng cực kỳ hoành tráng với hàng vạn người đứng dọc hai bên bờ sông hò reo cổ vũ.",
                                "https://www.youtube.com/embed/crZF3hghbgQ", "/image/t15.jpg", "lehoi"),

                        new VanHoa("Lễ hội Chol Chnam Thmay",
                                "Đây là Tết mừng năm mới của người Khmer (diễn ra giữa tháng 4 dương lịch). Người chưa biết sẽ thấy không khí hội hè tràn ngập các ngôi chùa. Mọi người mặc quần áo mới, mang hoa quả lên chùa làm lễ tắm Phật và đắp núi cát để tích đức. Đây là dịp để thưởng thức các điệu múa truyền thống, chơi các trò chơi dân gian như đẩy gậy, kéo co và cảm nhận niềm hy vọng về một năm mới mưa thuận gió hòa, mùa màng bội thu của đồng bào Khmer.",
                                "https://www.youtube.com/embed/mBnyZhEc21I", "/image/t1.jpg", "lehoi"),

                        new VanHoa("Lễ hội Ok Om Bok",
                                "Lễ hội Ok Om Bok hay còn gọi là Lễ cúng Trăng, diễn ra vào rằm tháng 10 âm lịch. Người chưa biết nên tham gia vào đêm chính: Người Khmer dâng cúng cốm dẹp, trái cây để tạ ơn thần Mặt Trăng đã bảo vệ mùa màng. Điểm đẹp nhất là lễ thả đèn nước trên sông, ánh sáng lung linh trôi theo dòng nước mang theo những lời cầu nguyện về sự no đủ. Không gian huyền ảo giữa đêm trăng rằm sẽ để lại ấn tượng khó quên về vẻ đẹp tâm linh vùng sông nước.",
                                "https://www.youtube.com/embed/Vq-KN5ANW1I", "/image/t2.jpg", "lehoi")
                ));
                System.out.println("✅ Đã khởi tạo 17 bản ghi VanHoa với mô tả chi tiết.");
            }
        };
    }

    // ─── CRUD ───
    public List<VanHoa> getAll() { return vanHoaRepository.findAll(); }

    public Optional<VanHoa> getById(Long id) { return vanHoaRepository.findById(id); }

    public VanHoa create(VanHoa vanHoa) { return vanHoaRepository.save(vanHoa); }

    public VanHoa update(Long id, VanHoa updated) {
        VanHoa existing = vanHoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy VanHoa với id: " + id));
        existing.setTieuDe(updated.getTieuDe());
        existing.setMoTa(updated.getMoTa());
        existing.setVideoUrl(updated.getVideoUrl());
        existing.setHinhAnh(updated.getHinhAnh());
        existing.setDanhMuc(updated.getDanhMuc());
        return vanHoaRepository.save(existing);
    }

    public void delete(Long id) {
        if (!vanHoaRepository.existsById(id))
            throw new RuntimeException("Không tìm thấy VanHoa với id: " + id);
        vanHoaRepository.deleteById(id);
    }

    public long count() { return vanHoaRepository.count(); }
}