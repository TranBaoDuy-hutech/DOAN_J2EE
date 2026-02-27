package com.travel3d.vietlutravel.service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.travel3d.vietlutravel.model.Booking;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class ContractService {

    // ── Brand colors ──────────────────────────────────────────────
    private static final Color COLOR_PRIMARY  = new DeviceRgb(3,   105, 161);
    private static final Color COLOR_DARK     = new DeviceRgb(12,   35,  64);
    private static final Color COLOR_GOLD     = new DeviceRgb(200, 169, 110);
    private static final Color COLOR_LIGHT_BG = new DeviceRgb(240, 249, 255);
    private static final Color COLOR_ROW_ALT  = new DeviceRgb(248, 250, 252);
    private static final Color COLOR_BORDER   = new DeviceRgb(226, 232, 240);
    private static final Color COLOR_MUTED    = new DeviceRgb(100, 116, 139);
    private static final Color COLOR_WHITE    = new DeviceRgb(255, 255, 255);

    // ── DeviceRgb constants dùng cho inner class (static context) ─
    private static final DeviceRgb RGB_PRIMARY = new DeviceRgb(3,   105, 161);
    private static final DeviceRgb RGB_MUTED   = new DeviceRgb(100, 116, 139);
    private static final DeviceRgb RGB_GOLD    = new DeviceRgb(200, 169, 110);
    private static final DeviceRgb RGB_WHITE   = new DeviceRgb(255, 255, 255);


    private PdfFont loadFont(String filename) throws Exception {
        // Ưu tiên 1: font trong resources/fonts/
        InputStream is = getClass().getResourceAsStream("/fonts/" + filename);
        if (is != null) {
            byte[] bytes = is.readAllBytes();
            is.close();
            return PdfFontFactory.createFont(bytes, PdfEncodings.IDENTITY_H,
                    PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        }

        // Ưu tiên 2: dùng font Arial/Calibri có sẵn trên Windows (đều hỗ trợ tiếng Việt)
        boolean isBold = filename.toLowerCase().contains("bold");
        String[] windowsFonts = isBold
                ? new String[]{
                "C:/Windows/Fonts/arialbd.ttf",
                "C:/Windows/Fonts/calibrib.ttf",
                "C:/Windows/Fonts/verdanab.ttf"}
                : new String[]{
                "C:/Windows/Fonts/arial.ttf",
                "C:/Windows/Fonts/calibri.ttf",
                "C:/Windows/Fonts/verdana.ttf"};

        for (String path : windowsFonts) {
            if (new java.io.File(path).exists()) {
                return PdfFontFactory.createFont(path, PdfEncodings.IDENTITY_H,
                        PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
            }
        }

        // Không tìm thấy font nào phù hợp
        throw new RuntimeException(
                "Không tìm thấy font hỗ trợ tiếng Việt!\n" +
                        "Cách fix nhanh: Đặt file '" + filename + "' vào src/main/resources/fonts/\n" +
                        "Tải DejaVuSans miễn phí tại: https://dejavu-fonts.github.io/"
        );
    }

    // ─────────────────────────────────────────────────────────────
    public byte[] generateContract(Booking booking) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter   writer   = new PdfWriter(out);
            PdfDocument pdf      = new PdfDocument(writer);
            Document    document = new Document(pdf, PageSize.A4);
            document.setMargins(72, 56, 72, 56);

            PdfFont fontRegular = loadFont("DejaVuSans.ttf");
            PdfFont fontBold    = loadFont("DejaVuSans-Bold.ttf");

            DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            // FIX: dùng Locale("vi","VN") cho đúng định dạng số tiền Việt Nam
            NumberFormat numFmt = NumberFormat.getInstance(new Locale("vi", "VN"));

            // ── Dữ liệu ──────────────────────────────────────────
            String customerName  = safe(booking.getCustomer() != null ? booking.getCustomer().getUserName() : null);
            String customerEmail = safe(booking.getCustomer() != null ? booking.getCustomer().getEmail()    : null);
            String tourName      = safe(booking.getTour()     != null ? booking.getTour().getTourName()     : null);
            String tourLocation  = safe(booking.getTour()     != null ? booking.getTour().getLocation()     : null);
            String startDate     = (booking.getTour() != null && booking.getTour().getStartDate() != null)
                    ? booking.getTour().getStartDate().format(dateFmt) : "N/A";
            String bookingDate   = booking.getBookingDate() != null
                    ? booking.getBookingDate().format(dateFmt) : "N/A";
            String totalPrice    = numFmt.format(booking.getTotalPrice()) + " VNĐ";
            String numPeople     = booking.getNumberOfPeople() + " người";
            String bookingId     = "#" + booking.getBookingID();

            // ── Header/footer event ───────────────────────────────
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE,
                    new HeaderFooterHandler(fontRegular, fontBold, bookingId, bookingDate));

            // ── Nội dung trang ────────────────────────────────────
            addHeader(document, fontRegular, fontBold);
            addContractTitle(document, fontBold, bookingId, bookingDate);

            addSectionTitle(document, fontBold, "I. THÔNG TIN ĐẶT TOUR");
            addInfoTable(document, fontRegular, fontBold,
                    customerName, customerEmail, tourName, tourLocation,
                    startDate, numPeople, totalPrice, bookingDate);

            addSectionTitle(document, fontBold, "II. LỊCH TRÌNH TOUR");
            addItinerary(document, fontRegular, fontBold, booking);

            addSectionTitle(document, fontBold, "III. ĐIỀU KHOẢN HỢP ĐỒNG");
            addTerms(document, fontRegular, fontBold);

            addSignatureSection(document, fontRegular, fontBold, customerName);

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo hợp đồng PDF: " + e.getMessage(), e);
        }
    }

    private String safe(String val) {
        return (val != null && !val.isBlank()) ? val : "N/A";
    }

    // ── HEADER ────────────────────────────────────────────────────
    private void addHeader(Document doc, PdfFont fontRegular, PdfFont fontBold) {
        Table t = new Table(UnitValue.createPercentArray(new float[]{55, 45}))
                .setWidth(UnitValue.createPercentValue(100));

        Cell left = new Cell().setBorder(Border.NO_BORDER).setPaddingBottom(8);
        left.add(new Paragraph("VIỆT LỮ TRAVEL")
                .setFont(fontBold).setFontSize(24).setFontColor(COLOR_PRIMARY).setMarginBottom(3));
        left.add(new Paragraph("Hành trình đáng nhớ – Trải nghiệm trọn vẹn")
                .setFont(fontRegular).setFontSize(9).setFontColor(COLOR_MUTED).setItalic());

        Cell right = new Cell().setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT).setPaddingBottom(8);
        right.add(new Paragraph("Hotline: 096 123 4567")
                .setFont(fontRegular).setFontSize(9).setFontColor(COLOR_DARK));
        right.add(new Paragraph("Email: vietlutravell@gmail.com")
                .setFont(fontRegular).setFontSize(9).setFontColor(COLOR_DARK));
        right.add(new Paragraph("Website: vietlutravel.vn")
                .setFont(fontRegular).setFontSize(9).setFontColor(COLOR_PRIMARY));

        t.addCell(left);
        t.addCell(right);
        doc.add(t);

        doc.add(new LineSeparator(new com.itextpdf.kernel.pdf.canvas.draw.SolidLine(2f))
                .setStrokeColor(COLOR_GOLD).setMarginBottom(16));
    }

    // ── TIÊU ĐỀ HỢP ĐỒNG ─────────────────────────────────────────
    private void addContractTitle(Document doc, PdfFont fontBold, String bookingId, String bookingDate) {
        doc.add(new Paragraph("HỢP ĐỒNG DU LỊCH")
                .setFont(fontBold).setFontSize(20).setFontColor(COLOR_DARK)
                .setTextAlignment(TextAlignment.CENTER).setMarginBottom(4));
        doc.add(new Paragraph("Mã hợp đồng: " + bookingId + "   |   Ngày lập: " + bookingDate)
                .setFont(fontBold).setFontSize(9).setFontColor(COLOR_MUTED)
                .setTextAlignment(TextAlignment.CENTER).setMarginBottom(22));
    }

    // ── TIÊU ĐỀ SECTION ──────────────────────────────────────────
    private void addSectionTitle(Document doc, PdfFont fontBold, String title) {
        Table t = new Table(UnitValue.createPercentArray(new float[]{100}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginTop(14).setMarginBottom(8);
        Cell c = new Cell().setBackgroundColor(COLOR_DARK)
                .setPadding(7).setPaddingLeft(12).setBorder(Border.NO_BORDER);
        c.add(new Paragraph(title).setFont(fontBold).setFontSize(10).setFontColor(COLOR_WHITE));
        t.addCell(c);
        doc.add(t);
    }

    // ── BẢNG THÔNG TIN ────────────────────────────────────────────
    private void addInfoTable(Document doc, PdfFont fontRegular, PdfFont fontBold,
                              String customerName, String customerEmail,
                              String tourName, String tourLocation,
                              String startDate, String numPeople,
                              String totalPrice, String bookingDate) {

        Table table = new Table(UnitValue.createPercentArray(new float[]{38, 62}))
                .setWidth(UnitValue.createPercentValue(100)).setMarginBottom(6);

        String[][] rows = {
                {"Tên khách hàng",  customerName},
                {"Email",           customerEmail},
                {"Tên tour",        tourName},
                {"Địa điểm",        tourLocation},
                {"Ngày khởi hành",  startDate},
                {"Số lượng khách",  numPeople},
                {"Ngày đặt tour",   bookingDate},
                {"TỔNG TIỀN",       totalPrice},
        };

        for (int i = 0; i < rows.length; i++) {
            boolean isLast = (i == rows.length - 1);
            boolean isAlt  = (i % 2 == 1);
            Color   rowBg  = isLast ? COLOR_LIGHT_BG : (isAlt ? COLOR_ROW_ALT : COLOR_WHITE);

            Cell labelCell = new Cell()
                    .setBackgroundColor(rowBg)
                    .setPadding(8).setPaddingLeft(12)
                    .setBorderBottom(new SolidBorder(COLOR_BORDER, 0.5f))
                    .setBorderTop(Border.NO_BORDER)
                    .setBorderLeft(new SolidBorder(isLast ? COLOR_GOLD : COLOR_BORDER, isLast ? 3f : 0.5f))
                    .setBorderRight(Border.NO_BORDER);
            labelCell.add(new Paragraph(rows[i][0])
                    .setFont(isLast ? fontBold : fontRegular)
                    .setFontSize(9).setFontColor(isLast ? COLOR_DARK : COLOR_MUTED));

            Cell valueCell = new Cell()
                    .setBackgroundColor(rowBg)
                    .setPadding(8).setPaddingLeft(12)
                    .setBorderBottom(new SolidBorder(COLOR_BORDER, 0.5f))
                    .setBorderTop(Border.NO_BORDER)
                    .setBorderLeft(new SolidBorder(COLOR_BORDER, 0.5f))
                    .setBorderRight(new SolidBorder(COLOR_BORDER, 0.5f));
            valueCell.add(new Paragraph(rows[i][1])
                    .setFont(fontBold).setFontSize(isLast ? 11 : 9)
                    .setFontColor(isLast ? COLOR_PRIMARY : COLOR_DARK));

            table.addCell(labelCell);
            table.addCell(valueCell);
        }
        doc.add(table);
    }

    // ── LỊCH TRÌNH ───────────────────────────────────────────────
    private void addItinerary(Document doc, PdfFont fontRegular, PdfFont fontBold, Booking booking) {
        String itinerary = booking.getTour() != null ? booking.getTour().getItinerary() : null;

        if (itinerary == null || itinerary.trim().isEmpty()) {
            doc.add(new Paragraph("Lịch trình đang được cập nhật. Vui lòng liên hệ hotline để biết thêm chi tiết.")
                    .setFont(fontRegular).setFontSize(9).setFontColor(COLOR_MUTED)
                    .setItalic().setMarginLeft(12));
            return;
        }

        for (String raw : itinerary.split("\n")) {
            String line = raw.trim();
            if (line.isEmpty()) continue;

            // FIX: dùng Locale("vi","VN") cho toLowerCase() với tiếng Việt
            String normalized = line.toLowerCase(new Locale("vi", "VN"));

            // Detect tiêu đề ngày: "Ngày 1", "Ngày 1:", "Day 1", "1.", "1)"
            boolean isDayHeader =
                    normalized.matches("^(ngày|ngay|day)\\s*\\d+.*") ||
                            normalized.matches("^\\d+[.):]\\s.*");

            if (isDayHeader) {
                // Box tiêu đề ngày: nền xanh nhạt + border trái xanh đậm
                Table dayBox = new Table(UnitValue.createPercentArray(new float[]{100}))
                        .setWidth(UnitValue.createPercentValue(100))
                        .setMarginTop(10).setMarginBottom(4);
                Cell dc = new Cell()
                        .setBackgroundColor(COLOR_LIGHT_BG)
                        .setPadding(7).setPaddingLeft(14)
                        .setBorderLeft(new SolidBorder(COLOR_PRIMARY, 3.5f))
                        .setBorderTop(Border.NO_BORDER)
                        .setBorderRight(Border.NO_BORDER)
                        .setBorderBottom(Border.NO_BORDER);
                dc.add(new Paragraph(line)
                        .setFont(fontBold).setFontSize(9.5f).setFontColor(COLOR_PRIMARY));
                dayBox.addCell(dc);
                doc.add(dayBox);
            } else {
                // Nội dung dạng bullet
                doc.add(new Paragraph("\u2022  " + line)
                        .setFont(fontRegular).setFontSize(9).setFontColor(COLOR_DARK)
                        .setMultipliedLeading(1.5f)
                        .setMarginBottom(2).setMarginLeft(22).setMarginRight(8));
            }
        }
    }

    // ── ĐIỀU KHOẢN ───────────────────────────────────────────────
    private void addTerms(Document doc, PdfFont fontRegular, PdfFont fontBold) {
        String[][] terms = {
                {"1. Thanh toán",
                        "Khách hàng thanh toán theo hướng dẫn của công ty. Việc xác nhận đặt tour chỉ có hiệu lực sau khi nhận được biên lai thanh toán hợp lệ."},
                {"2. Hủy tour",
                        "Hủy trước 7 ngày: hoàn 100%. Hủy trong vòng 3–6 ngày: phạt 30%. Hủy trong vòng 1–2 ngày: phạt 70%. Hủy trong ngày: mất toàn bộ."},
                {"3. Thay đổi lịch trình",
                        "Công ty có quyền điều chỉnh lịch trình trong trường hợp bất khả kháng (thời tiết xấu, thiên tai, dịch bệnh...) và sẽ thông báo sớm nhất có thể."},
                {"4. Trách nhiệm khách hàng",
                        "Khách phải có mặt tại điểm tập kết trước giờ khởi hành ít nhất 15 phút. Công ty không chịu trách nhiệm nếu khách đến trễ."},
                {"5. Bảo hiểm",
                        "Giá tour đã bao gồm bảo hiểm du lịch cơ bản. Khách hàng nên mua thêm bảo hiểm cá nhân để được bảo vệ toàn diện hơn."},
        };

        for (String[] term : terms) {
            Table row = new Table(UnitValue.createPercentArray(new float[]{28, 72}))
                    .setWidth(UnitValue.createPercentValue(100)).setMarginBottom(5);

            Cell lc = new Cell()
                    .setBorderLeft(new SolidBorder(COLOR_GOLD, 3f))
                    .setBorderTop(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                    .setBorderBottom(Border.NO_BORDER)
                    .setPadding(6).setPaddingLeft(12);
            lc.add(new Paragraph(term[0]).setFont(fontBold).setFontSize(9).setFontColor(COLOR_DARK));

            Cell vc = new Cell().setBorder(Border.NO_BORDER).setPadding(6).setPaddingLeft(8);
            vc.add(new Paragraph(term[1])
                    .setFont(fontRegular).setFontSize(9).setFontColor(COLOR_DARK)
                    .setMultipliedLeading(1.5f));

            row.addCell(lc);
            row.addCell(vc);
            doc.add(row);
        }
    }

    // ── CHỮ KÝ ───────────────────────────────────────────────────
    private void addSignatureSection(Document doc, PdfFont fontRegular, PdfFont fontBold,
                                     String customerName) {
        doc.add(new Paragraph(" ").setMarginTop(16));
        doc.add(new LineSeparator(new com.itextpdf.kernel.pdf.canvas.draw.DashedLine(0.5f))
                .setStrokeColor(COLOR_BORDER).setMarginBottom(14));

        Table t = new Table(UnitValue.createPercentArray(new float[]{50, 50}))
                .setWidth(UnitValue.createPercentValue(100));

        Cell clientCell = new Cell().setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER).setPadding(8);
        clientCell.add(new Paragraph("KHÁCH HÀNG")
                .setFont(fontBold).setFontSize(10).setFontColor(COLOR_DARK)
                .setTextAlignment(TextAlignment.CENTER));
        clientCell.add(new Paragraph("(Ký và ghi rõ họ tên)")
                .setFont(fontRegular).setFontSize(8).setFontColor(COLOR_MUTED)
                .setItalic().setTextAlignment(TextAlignment.CENTER).setMarginBottom(48));
        clientCell.add(new Paragraph(customerName)
                .setFont(fontBold).setFontSize(9).setFontColor(COLOR_DARK)
                .setTextAlignment(TextAlignment.CENTER));

        Cell companyCell = new Cell().setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER).setPadding(8);
        companyCell.add(new Paragraph("ĐẠI DIỆN CÔNG TY")
                .setFont(fontBold).setFontSize(10).setFontColor(COLOR_DARK)
                .setTextAlignment(TextAlignment.CENTER));
        companyCell.add(new Paragraph("VIỆT LỮ TRAVEL")
                .setFont(fontRegular).setFontSize(8).setFontColor(COLOR_PRIMARY)
                .setTextAlignment(TextAlignment.CENTER).setMarginBottom(48));
        companyCell.add(new Paragraph("Trần Bảo Duy – Giám đốc")
                .setFont(fontBold).setFontSize(9).setFontColor(COLOR_DARK)
                .setTextAlignment(TextAlignment.CENTER));

        t.addCell(clientCell);
        t.addCell(companyCell);
        doc.add(t);

        doc.add(new Paragraph("Hợp đồng này được lập thành 02 bản có giá trị ngang nhau, mỗi bên giữ 01 bản.")
                .setFont(fontRegular).setFontSize(8).setFontColor(COLOR_MUTED)
                .setItalic().setTextAlignment(TextAlignment.CENTER).setMarginTop(10));
    }

    // ══════════════════════════════════════════════════════════════
    // PAGE HEADER / FOOTER
    // ══════════════════════════════════════════════════════════════
    private static class HeaderFooterHandler implements IEventHandler {

        private final PdfFont fontRegular;
        private final PdfFont fontBold;
        private final String  bookingId;
        private final String  bookingDate;

        HeaderFooterHandler(PdfFont fontRegular, PdfFont fontBold,
                            String bookingId, String bookingDate) {
            this.fontRegular = fontRegular;
            this.fontBold    = fontBold;
            this.bookingId   = bookingId;
            this.bookingDate = bookingDate;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument      pdfDoc  = docEvent.getDocument();
            PdfPage          page    = docEvent.getPage();
            int              pageNum = pdfDoc.getPageNumber(page);
            int              total   = pdfDoc.getNumberOfPages();
            Rectangle        rect    = page.getPageSize();
            PdfCanvas        canvas  = new PdfCanvas(page);
            float            mH      = 56f;

            // Top strip — từ trang 2 trở đi
            if (pageNum > 1) {
                canvas.saveState()
                        .setFillColor(RGB_PRIMARY)
                        .rectangle(0, rect.getTop() - 26, rect.getWidth(), 26)
                        .fill().restoreState();

                try (Canvas c = new Canvas(canvas, rect)) {
                    c.add(new Paragraph("VIỆT LỮ TRAVEL  |  Hợp đồng " + bookingId)
                            .setFont(fontBold).setFontSize(8)
                            .setFontColor(RGB_WHITE)
                            .setFixedPosition(mH, rect.getTop() - 19, rect.getWidth() - mH * 2)
                            .setTextAlignment(TextAlignment.LEFT));
                }
            }

            // Footer line vàng
            canvas.saveState()
                    .setStrokeColor(RGB_GOLD)
                    .setLineWidth(1f)
                    .moveTo(mH, 46).lineTo(rect.getWidth() - mH, 46)
                    .stroke().restoreState();

            try (Canvas c = new Canvas(canvas, rect)) {
                c.add(new Paragraph("VIỆT LỮ TRAVEL  \u2013  vietlutravel.vn")
                        .setFont(fontRegular).setFontSize(7.5f)
                        .setFontColor(RGB_MUTED)
                        .setFixedPosition(mH, 28, 220)
                        .setTextAlignment(TextAlignment.LEFT));

                c.add(new Paragraph("Trang " + pageNum + " / " + total)
                        .setFont(fontRegular).setFontSize(7.5f)
                        .setFontColor(RGB_MUTED)
                        .setFixedPosition(rect.getWidth() - mH - 80, 28, 80)
                        .setTextAlignment(TextAlignment.RIGHT));
            }
        }
    }
}