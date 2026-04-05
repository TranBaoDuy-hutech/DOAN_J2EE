package com.travel3d.vietlutravel.service;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.travel3d.vietlutravel.model.Booking;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ContractService contractService;

    // ===============================
    // 1. EMAIL XÁC NHẬN ĐẶT TOUR (KHÔNG GỬI CONTRACT)
    // ===============================
    public void sendBookingConfirmation(Booking booking) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            DateTimeFormatter dateFormat =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");

            NumberFormat currencyFormat =
                    NumberFormat.getInstance(new Locale("vi", "VN"));

            String totalPrice =
                    currencyFormat.format(booking.getTotalPrice());
            String depositPrice =
                    currencyFormat.format(booking.getTotalPrice() * 0.7);

            String htmlContent = """
                    <div style="font-family: Arial; max-width:700px; margin:auto; border:1px solid #ddd; border-radius:10px; padding:20px">

                        <h2 style="text-align:center; color:#2b7cff;">
                            XÁC NHẬN ĐẶT TOUR
                        </h2>

                        <p>Xin chào <strong>%s</strong>,</p>
                        <p>Bạn đã đặt tour thành công tại <b>Việt Lữ Travel</b>.</p>

                        <h3>Thông tin đặt tour</h3>

                        <table style="width:100%%; border-collapse: collapse;">
                            <tr style="background:#f2f2f2;">
                                <th style="padding:10px;border:1px solid #ddd;">Thông tin</th>
                                <th style="padding:10px;border:1px solid #ddd;">Chi tiết</th>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Mã đặt tour</td>
                                <td style="padding:10px;border:1px solid #ddd;">#%d</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Tên tour</td>
                                <td style="padding:10px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Ngày khởi hành</td>
                                <td style="padding:10px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Số lượng khách</td>
                                <td style="padding:10px;border:1px solid #ddd;">%d người</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Tổng chi phí</td>
                                <td style="padding:10px;border:1px solid #ddd; color:red; font-weight:bold;">
                                    %s VNĐ
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd; color:#008000; font-weight:bold;">Đã thanh toán (Cọc 70%%) qua VNPAY</td>
                                <td style="padding:10px;border:1px solid #ddd; color:#008000; font-weight:bold;">
                                    %s VNĐ
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Ngày đặt</td>
                                <td style="padding:10px;border:1px solid #ddd;">%s</td>
                            </tr>
                        </table>
                        
                        <p style="margin-top:15px; color:#008000; font-weight:bold;">
                            Giao dịch thanh toán cọc 70%% đã được xác nhận thành công.
                        </p>
                        
                        <p style="margin-top:20px; color:#666;">
                            <b>Lưu ý:</b> Số tiền còn lại (30%%) sẽ được thanh toán trực tiếp cho Hướng dẫn viên khi bắt đầu tour. Hợp đồng tour sẽ được gửi qua email sau khi admin duyệt booking.
                        </p>

                        <hr>

                        <p>
                            Hotline: <b>096 123 4567</b><br>
                            Email: vietlutravell@gmail.com
                        </p>

                        <p style="text-align:center; color:gray;">
                            © Việt Lữ Travel
                        </p>
                    </div>
                    """.formatted(
                    booking.getCustomer().getUserName(),
                    booking.getBookingID(),
                    booking.getTour().getTourName(),
                    booking.getTravelDate().format(dateFormat),
                    booking.getNumberOfPeople(),
                    totalPrice,
                    depositPrice,
                    booking.getBookingDate().format(dateFormat)
            );

            helper.setTo(booking.getCustomer().getEmail());
            helper.setSubject("Xác nhận đặt tour - Việt Lữ Travel");
            helper.setText(htmlContent, true);
            helper.setFrom("vietlutravell@gmail.com");

            // REMOVED: Không gửi contract PDF ngay lúc đặt tour
            // byte[] pdfFile = contractService.generateContract(booking);
            // helper.addAttachment("HopDongTour_" + booking.getBookingID() + ".pdf", new ByteArrayResource(pdfFile));

            mailSender.send(message);
            System.out.println("Gửi email xác nhận đặt tour thành công!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // 1.5. EMAIL GỬI HỢP ĐỒNG TOUR (SAU KHI CONFIRMED)
    // ===============================
    public void sendContract(Booking booking) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            DateTimeFormatter dateFormat =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");

            NumberFormat currencyFormat =
                    NumberFormat.getInstance(new Locale("vi", "VN"));

            String totalPrice =
                    currencyFormat.format(booking.getTotalPrice());
            String depositPrice =
                    currencyFormat.format(booking.getTotalPrice() * 0.7);

            String guideName = booking.getTourGuide() != null
                    ? booking.getTourGuide().getFullName()
                    : "Chưa phân công";

            String htmlContent = """
                    <div style="font-family: Arial; max-width:700px; margin:auto; border:1px solid #ddd; border-radius:10px; padding:20px">

                        <h2 style="text-align:center; color:#28a745;">
                            🎉 HỢP ĐỒNG TOUR ĐÃ ĐƯỢC DUYỆT
                        </h2>

                        <p>Xin chào <strong>%s</strong>,</p>
                        <p>Booking của bạn đã được <b>admin duyệt</b> và <b>hợp đồng tour</b> đã sẵn sàng!</p>

                        <h3>Thông tin hợp đồng tour</h3>

                        <table style="width:100%%; border-collapse: collapse;">
                            <tr style="background:#f2f2f2;">
                                <th style="padding:10px;border:1px solid #ddd;">Thông tin</th>
                                <th style="padding:10px;border:1px solid #ddd;">Chi tiết</th>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Mã hợp đồng</td>
                                <td style="padding:10px;border:1px solid #ddd;">#%d</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Tên tour</td>
                                <td style="padding:10px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Ngày khởi hành</td>
                                <td style="padding:10px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Số lượng khách</td>
                                <td style="padding:10px;border:1px solid #ddd;">%d người</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Hướng dẫn viên</td>
                                <td style="padding:10px;border:1px solid #ddd; font-weight:bold;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Tổng chi phí</td>
                                <td style="padding:10px;border:1px solid #ddd; color:red; font-weight:bold;">
                                    %s VNĐ
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd; color:#008000; font-weight:bold;">Đã thanh toán (Cọc 70%%)</td>
                                <td style="padding:10px;border:1px solid #ddd; color:#008000; font-weight:bold;">
                                    %s VNĐ
                                </td>
                            </tr>
                        </table>

                        <p style="margin-top:20px">
                            📎 <b>Hợp đồng tour chi tiết</b> đã được đính kèm trong email này.
                            Vui lòng kiểm tra và chuẩn bị cho chuyến đi! Số tiền còn lại (30%%) sẽ được thanh toán trực tiếp cho Hướng dẫn viên khi bắt đầu tour.
                        </p>

                        <hr>

                        <p>
                            Hotline: <b>096 123 4567</b><br>
                            Email: vietlutravell@gmail.com
                        </p>

                        <p style="text-align:center; color:gray;">
                            © Việt Lữ Travel
                        </p>
                    </div>
                    """.formatted(
                    booking.getCustomer().getUserName(),
                    booking.getBookingID(),
                    booking.getTour().getTourName(),
                    booking.getTravelDate().format(dateFormat),
                    booking.getNumberOfPeople(),
                    guideName,
                    totalPrice,
                    depositPrice
            );

            helper.setTo(booking.getCustomer().getEmail());
            helper.setSubject("Hợp đồng tour đã được duyệt - Việt Lữ Travel");
            helper.setText(htmlContent, true);
            helper.setFrom("vietlutravell@gmail.com");

            // Gửi contract PDF đính kèm
            byte[] pdfFile = contractService.generateContract(booking);
            helper.addAttachment(
                    "HopDongTour_" + booking.getBookingID() + ".pdf",
                    new ByteArrayResource(pdfFile)
            );

            mailSender.send(message);
            System.out.println("Gửi hợp đồng tour thành công cho booking #" + booking.getBookingID());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // ===============================
    public void sendReplyEmail(String toEmail, String subject, String content) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = """
                    <div style="font-family: Arial; max-width:600px; margin:auto; border:1px solid #ddd; border-radius:10px; padding:20px">
                        
                        <h2 style="color:#2b7cff;">Phản hồi từ Việt Lữ Travel</h2>

                        <p>Xin chào,</p>

                        <p>%s</p>

                        <br>

                        <p>Nếu cần hỗ trợ thêm vui lòng liên hệ:</p>

                        <p>
                        Hotline: <b>096 123 4567</b><br>
                        Email: vietlutravell@gmail.com
                        </p>

                        <hr>

                        <p style="color:gray; font-size:13px;">
                        © Việt Lữ Travel
                        </p>
                    </div>
                    """.formatted(content);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setFrom("vietlutravell@gmail.com");

            mailSender.send(message);
            System.out.println("Gửi phản hồi liên hệ thành công!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // 3. EMAIL GỬI MÃ OTP
    // ===============================
    public void sendOtpEmail(String toEmail, String otp) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = """
                    <div style="font-family:Arial; max-width:560px; margin:auto; border:1px solid #e2e8f0; border-radius:12px; overflow:hidden;">
                        
                        <div style="background:#0c2340; padding:24px; text-align:center;">
                            <h2 style="color:#e8d5a3; margin:0; font-size:22px;">
                                🔐 Việt Lữ Travel
                            </h2>
                        </div>

                        <div style="padding:32px 28px;">
                            <h3 style="color:#0c2340; margin-top:0;">Khôi phục mật khẩu</h3>

                            <p style="color:#475569;">
                                Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản <b>%s</b>.
                                Sử dụng mã OTP bên dưới để tiếp tục:
                            </p>

                            <div style="background:#f1f5f9; border-radius:10px; padding:24px; text-align:center; margin:24px 0;">
                                <div style="font-size:38px; font-weight:bold; letter-spacing:12px; color:#0c2340;">
                                    %s
                                </div>
                                <p style="color:#64748b; font-size:13px; margin:8px 0 0;">
                                    Mã có hiệu lực trong <b>3 phút</b>
                                </p>
                            </div>

                            <p style="color:#64748b; font-size:13px;">
                                Nếu bạn không yêu cầu đặt lại mật khẩu, hãy bỏ qua email này.
                                Tài khoản của bạn vẫn an toàn.
                            </p>
                        </div>

                        <div style="background:#f8fafc; padding:16px; text-align:center; border-top:1px solid #e2e8f0;">
                            <p style="color:#94a3b8; font-size:12px; margin:0;">
                                Hotline: <b>096 123 4567</b> &nbsp;|&nbsp; vietlutravell@gmail.com
                            </p>
                            <p style="color:#cbd5e1; font-size:11px; margin:6px 0 0;">
                                © Việt Lữ Travel
                            </p>
                        </div>
                    </div>
                    """.formatted(toEmail, otp);

            helper.setTo(toEmail);
            helper.setSubject("Mã OTP đặt lại mật khẩu - Việt Lữ Travel");
            helper.setText(htmlContent, true);
            helper.setFrom("vietlutravell@gmail.com");

            mailSender.send(message);
            System.out.println("Gửi OTP tới " + toEmail + " thành công!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // 4. EMAIL XÁC NHẬN ĐỔI MẬT KHẨU
    // ===============================
    public void sendPasswordChangedEmail(String toEmail) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            // Lấy thời điểm thay đổi theo giờ VN
            java.time.ZonedDateTime now = java.time.ZonedDateTime.now(
                    java.time.ZoneId.of("Asia/Ho_Chi_Minh"));
            String changedAt = now.format(
                    java.time.format.DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy"));

            String htmlContent = """
                    <div style="font-family:Arial; max-width:560px; margin:auto; border:1px solid #e2e8f0; border-radius:12px; overflow:hidden;">

                        <div style="background:#0c2340; padding:24px; text-align:center;">
                            <h2 style="color:#e8d5a3; margin:0; font-size:22px;">
                                🛡️ Việt Lữ Travel
                            </h2>
                        </div>

                        <div style="padding:32px 28px;">
                            <h3 style="color:#059669; margin-top:0;">✅ Mật khẩu đã được thay đổi thành công</h3>

                            <p style="color:#475569;">
                                Mật khẩu tài khoản <b>%s</b> vừa được đặt lại thành công vào lúc:
                            </p>

                            <div style="background:#f0fdf4; border:1px solid #bbf7d0; border-radius:10px; padding:16px; text-align:center; margin:20px 0;">
                                <span style="font-size:18px; font-weight:bold; color:#065f46;">
                                    🕐 %s
                                </span>
                            </div>

                            <p style="color:#475569;">
                                Bạn có thể đăng nhập lại bằng mật khẩu mới ngay bây giờ.
                            </p>

                            <div style="background:#fef9ec; border:1px solid #fde68a; border-radius:10px; padding:16px; margin:20px 0;">
                                <p style="color:#92400e; font-size:13px; margin:0;">
                                    ⚠️ <b>Không phải bạn thực hiện?</b> Nếu bạn không yêu cầu thay đổi mật khẩu,
                                    hãy liên hệ ngay với chúng tôi để được hỗ trợ bảo mật tài khoản.
                                </p>
                            </div>
                        </div>

                        <div style="background:#f8fafc; padding:16px; text-align:center; border-top:1px solid #e2e8f0;">
                            <p style="color:#94a3b8; font-size:12px; margin:0;">
                                Hotline: <b>096 123 4567</b> &nbsp;|&nbsp; vietlutravell@gmail.com
                            </p>
                            <p style="color:#cbd5e1; font-size:11px; margin:6px 0 0;">
                                © Việt Lữ Travel
                            </p>
                        </div>
                    </div>
                    """.formatted(toEmail, changedAt);

            helper.setTo(toEmail);
            helper.setSubject("Mật khẩu đã được thay đổi - Việt Lữ Travel");
            helper.setText(htmlContent, true);
            helper.setFrom("vietlutravell@gmail.com");

            mailSender.send(message);
            System.out.println("Gửi xác nhận đổi mật khẩu tới " + toEmail + " thành công!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // 5. EMAIL GỬI THÔNG TIN TOUR CHO HDV
    // ===============================
    public void sendTourInfoToHDV(Booking booking) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            DateTimeFormatter dateFormat =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");

            NumberFormat currencyFormat =
                    NumberFormat.getInstance(new Locale("vi", "VN"));

            String totalPrice =
                    currencyFormat.format(booking.getTotalPrice());

            String customerName = booking.getCustomer() != null
                    ? booking.getCustomer().getUserName()
                    : "N/A";

            String customerEmail = booking.getCustomer() != null
                    ? booking.getCustomer().getEmail()
                    : "N/A";

            String customerPhone = booking.getCustomer() != null
                    ? booking.getCustomer().getPhone()
                    : "N/A";

            String htmlContent = """
                    <div style="font-family: Arial; max-width:700px; margin:auto; border:1px solid #ddd; border-radius:10px; padding:20px">

                        <h2 style="text-align:center; color:#17a2b8;">
                            📋 THÔNG TIN TOUR PHÂN CÔNG
                        </h2>

                        <p>Xin chào <strong>%s</strong>,</p>
                        <p>Bạn đã được phân công dẫn dắt tour sau:</p>

                        <h3>Chi tiết tour</h3>

                        <table style="width:100%%; border-collapse: collapse;">
                            <tr style="background:#f2f2f2;">
                                <th style="padding:10px;border:1px solid #ddd;">Thông tin</th>
                                <th style="padding:10px;border:1px solid #ddd;">Chi tiết</th>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Mã Booking</td>
                                <td style="padding:10px;border:1px solid #ddd;">#%d</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Tên tour</td>
                                <td style="padding:10px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Ngày khởi hành</td>
                                <td style="padding:10px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Số lượng khách</td>
                                <td style="padding:10px;border:1px solid #ddd;">%d người</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Tên khách hàng</td>
                                <td style="padding:10px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Email khách hàng</td>
                                <td style="padding:10px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">SĐT khách hàng</td>
                                <td style="padding:10px;border:1px solid #ddd;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Tổng chi phí</td>
                                <td style="padding:10px;border:1px solid #ddd; color:red; font-weight:bold;">
                                    %s VNĐ
                                </td>
                            </tr>
                        </table>

                        <p style="margin-top:20px">
                            Vui lòng chuẩn bị kỹ lưỡng cho chuyến đi và liên hệ với khách hàng nếu cần thêm thông tin.
                        </p>

                        <hr>

                        <p>
                            Hotline: <b>096 123 4567</b><br>
                            Email: vietlutravell@gmail.com
                        </p>

                        <p style="text-align:center; color:gray;">
                            © Việt Lữ Travel
                        </p>
                    </div>
                    """.formatted(
                    booking.getTourGuide().getFullName(),
                    booking.getBookingID(),
                    booking.getTour().getTourName(),
                    booking.getTravelDate().format(dateFormat),
                    booking.getNumberOfPeople(),
                    customerName,
                    customerEmail,
                    customerPhone,
                    totalPrice
            );

            helper.setTo(booking.getTourGuide().getEmail());
            helper.setSubject("Thông tin tour phân công - Việt Lữ Travel");
            helper.setText(htmlContent, true);
            helper.setFrom("vietlutravell@gmail.com");

            mailSender.send(message);
            System.out.println("Gửi thông tin tour cho HDV " + booking.getTourGuide().getFullName() + " thành công cho booking #" + booking.getBookingID());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}