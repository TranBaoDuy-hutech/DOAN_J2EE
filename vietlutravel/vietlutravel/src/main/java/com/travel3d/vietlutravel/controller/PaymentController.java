package com.travel3d.vietlutravel.controller;

import com.travel3d.vietlutravel.config.VNPayConfig;
import com.travel3d.vietlutravel.model.Booking;
import com.travel3d.vietlutravel.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpSession;
import com.travel3d.vietlutravel.service.EmailService;

@Controller
public class PaymentController {

    @Value("${vnp_HashSecret}")
    private String vnpHashSecret;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/vnpay-payment-return")
    public String paymentReturn(HttpServletRequest request, Model model) {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = URLEncoder.encode(params.nextElement(), StandardCharsets.US_ASCII);
            String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        
        // Compute checksum
        String signValue = VNPayConfig.hmacSHA512(vnpHashSecret, hashAllFields(fields));
        
        String txnRef = request.getParameter("vnp_TxnRef");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");

        HttpSession session = request.getSession();
        Booking booking = (Booking) session.getAttribute("PENDING_BOOKING_" + txnRef);
        
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(vnp_ResponseCode)) {
                if (booking != null) {
                    booking.setStatus("Paid 70% Deposit");
                    bookingService.saveBooking(booking);
                    emailService.sendBookingConfirmation(booking);
                    session.removeAttribute("PENDING_BOOKING_" + txnRef);
                }
                model.addAttribute("message", "Thanh toán thành công. Đơn hàng của bạn đã cọc 70% tổng chi phí.");
                model.addAttribute("success", true);
            } else {
                if (booking != null) {
                    session.removeAttribute("PENDING_BOOKING_" + txnRef);
                }
                model.addAttribute("message", "Giao dịch thanh toán không thành công. Mã lỗi: " + vnp_ResponseCode);
                model.addAttribute("success", false);
            }
        } else {
            model.addAttribute("message", "Lỗi bảo mật: Dữ liệu bị thay đổi trong quá trình giao dịch!");
            model.addAttribute("success", false);
        }

        return "vnpay-result";
    }

    private String hashAllFields(Map<String, String> fields) {
        // Sort keys
        java.util.List<String> fieldNames = new java.util.ArrayList<>(fields.keySet());
        java.util.Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        java.util.Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }
}
