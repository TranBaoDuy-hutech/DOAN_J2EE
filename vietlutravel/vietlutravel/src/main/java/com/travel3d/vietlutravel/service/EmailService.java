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

    // thêm service tạo hợp đồng PDF
    @Autowired
    private ContractService contractService;

    public void sendBookingConfirmation(Booking booking) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            // ===== Format ngày =====
            DateTimeFormatter dateFormat =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // ===== Format tiền =====
            NumberFormat currencyFormat =
                    NumberFormat.getInstance(new Locale("vi", "VN"));

            String totalPrice =
                    currencyFormat.format(booking.getTotalPrice());

            // ===== Nội dung email =====
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

            // ===== TẠO FILE PDF =====
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
}