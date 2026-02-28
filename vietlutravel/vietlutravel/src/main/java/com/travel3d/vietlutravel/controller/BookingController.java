package com.travel3d.vietlutravel.controller;

import com.travel3d.vietlutravel.model.Booking;
import com.travel3d.vietlutravel.model.Customer;
import com.travel3d.vietlutravel.model.Tours;
import com.travel3d.vietlutravel.service.BookingService;
import com.travel3d.vietlutravel.service.ContractService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class BookingController {

    private static final int MIN_PEOPLE = 1;
    private static final int MAX_PEOPLE = 50;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ContractService contractService;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * HIỂN THỊ FORM ĐẶT TOUR
     */
    @GetMapping("/booking-tour/{id}")
    public String showBookingForm(@PathVariable int id,
                                  HttpSession session,
                                  Model model) {

        Customer customer = (Customer) session.getAttribute("user");
        if (customer == null) return "redirect:/login";

        Tours tour = entityManager.find(Tours.class, id);
        if (tour == null) return "redirect:/";

        model.addAttribute("tour", tour);
        model.addAttribute("customer", customer);
        model.addAttribute("minPeople", MIN_PEOPLE);
        model.addAttribute("maxPeople", MAX_PEOPLE);
        model.addAttribute("today", LocalDate.now());

        return "booking-form";
    }

    /**
     * XEM TRƯỚC HỢP ĐỒNG (CHƯA LƯU DATABASE)
     */
    @GetMapping("/preview-contract")
    public ResponseEntity<byte[]> previewContract(
            @RequestParam int tourID,
            @RequestParam int numberOfPeople,
            @RequestParam(required = false) String travelDate,
            HttpSession session) {

        Customer customer = (Customer) session.getAttribute("user");
        if (customer == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if (numberOfPeople < MIN_PEOPLE || numberOfPeople > MAX_PEOPLE)
            return ResponseEntity.badRequest().build();

        Tours tour = entityManager.find(Tours.class, tourID);
        if (tour == null) return ResponseEntity.notFound().build();

        Booking booking = new Booking();
        booking.setBookingID(0);
        booking.setTour(tour);
        booking.setCustomer(customer);
        booking.setNumberOfPeople(numberOfPeople);
        booking.setBookingDate(LocalDate.now());
        booking.setTravelDate(travelDate != null && !travelDate.isBlank()
                ? LocalDate.parse(travelDate) : null);
        booking.setTotalPrice(tour.getPrice().doubleValue() * numberOfPeople);

        byte[] pdf = contractService.generateContract(booking);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.inline().filename("preview-hop-dong-tour.pdf").build());

        return ResponseEntity.ok().headers(headers).body(pdf);
    }

    /**
     * XỬ LÝ ĐẶT TOUR (LƯU DATABASE)
     */
    @PostMapping("/booking-tour")
    public String bookTour(
            @RequestParam("tourID") int tourID,
            @RequestParam("numberOfPeople") int numberOfPeople,
            @RequestParam(value = "travelDate", required = false) String travelDateStr,
            @RequestParam(value = "note", required = false) String note,
            HttpSession session,
            Model model) {

        Customer customer = (Customer) session.getAttribute("user");
        if (customer == null) return "redirect:/login";

        Tours tour = entityManager.find(Tours.class, tourID);

        // Validate số người
        if (numberOfPeople < MIN_PEOPLE || numberOfPeople > MAX_PEOPLE) {
            model.addAttribute("tour", tour);
            model.addAttribute("customer", customer);
            model.addAttribute("minPeople", MIN_PEOPLE);
            model.addAttribute("maxPeople", MAX_PEOPLE);
            model.addAttribute("today", LocalDate.now().toString());
            model.addAttribute("errorMessage",
                    "Số người phải từ " + MIN_PEOPLE + " đến " + MAX_PEOPLE + " người.");
            return "booking-form";
        }

        // Validate ngày khởi hành
        if (travelDateStr == null || travelDateStr.isBlank()) {
            model.addAttribute("tour", tour);
            model.addAttribute("customer", customer);
            model.addAttribute("minPeople", MIN_PEOPLE);
            model.addAttribute("maxPeople", MAX_PEOPLE);
            model.addAttribute("today", LocalDate.now().toString());
            model.addAttribute("errorMessage", "Vui lòng chọn ngày khởi hành.");
            return "booking-form";
        }

        LocalDate travelDate;
        try {
            travelDate = LocalDate.parse(travelDateStr);
            if (travelDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Ngày khởi hành không được là ngày trong quá khứ.");
            }
        } catch (Exception e) {
            model.addAttribute("tour", tour);
            model.addAttribute("customer", customer);
            model.addAttribute("minPeople", MIN_PEOPLE);
            model.addAttribute("maxPeople", MAX_PEOPLE);
            model.addAttribute("today", LocalDate.now().toString());
            model.addAttribute("errorMessage", e.getMessage());
            return "booking-form";
        }

        bookingService.bookTour(
                tourID,
                customer.getCustomerID(),
                numberOfPeople,
                travelDate,
                note
        );

        return "redirect:/";
    }

    /**
     * XEM HỢP ĐỒNG (NẾU USER MUỐN XEM LẠI)
     */
    @GetMapping("/contract/{id}")
    public ResponseEntity<byte[]> viewContract(@PathVariable int id) {

        Booking booking = bookingService.getBookingById(id);
        if (booking == null) return ResponseEntity.notFound().build();

        byte[] pdf = contractService.generateContract(booking);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.inline().filename("hop-dong-tour.pdf").build());

        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}