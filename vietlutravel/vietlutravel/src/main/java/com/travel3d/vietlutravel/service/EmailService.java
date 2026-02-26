package com.travel3d.vietlutravel.service;

import com.travel3d.vietlutravel.model.Booking;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ContractService contractService;

    // ===============================
    // 1. EMAIL XÁC NHẬN ĐẶT TOUR
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

            String htmlContent = """
                    <div style="font-family: Arial; max-width:700px; margin:auto; border:1px solid #ddd; border-radius:10px; padding:20px">
                        
                        <h2 style="text-align:center; color:#2b7cff;">
                            XÁC NHẬN ĐẶT TOUR
                        </h2>

                        <p>Xin chào <strong>%s</strong>,</p>
                        <p>Bạn đã đặt tour thành công tại <b>Việt Lữ Travel</b>.</p>

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
                                <td style="padding:10px;border:1px solid #ddd;">Tổng chi phí</td>
                                <td style="padding:10px;border:1px solid #ddd; color:red; font-weight:bold;">
                                    %s VNĐ
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:10px;border:1px solid #ddd;">Ngày đặt</td>
                                <td style="padding:10px;border:1px solid #ddd;">%s</td>
                            </tr>
                        </table>

                        <p style="margin-top:20px">
                            Hợp đồng tour đã được đính kèm trong email này.
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
                    booking.getTour().getStartDate().format(dateFormat),
                    booking.getNumberOfPeople(),
                    totalPrice,
                    booking.getBookingDate().format(dateFormat)
            );

            helper.setTo(booking.getCustomer().getEmail());
            helper.setSubject("Xác nhận đặt tour - Việt Lữ Travel");
            helper.setText(htmlContent, true);
            helper.setFrom("vietlutravell@gmail.com");

            byte[] pdfFile = contractService.generateContract(booking);
            helper.addAttachment(
                    "HopDongTour_" + booking.getBookingID() + ".pdf",
                    new ByteArrayResource(pdfFile)
            );

            mailSender.send(message);
            System.out.println("Gửi email + PDF thành công!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // 2. EMAIL PHẢN HỒI LIÊN HỆ
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
                                    Mã có hiệu lực trong <b>10 phút</b>
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
}