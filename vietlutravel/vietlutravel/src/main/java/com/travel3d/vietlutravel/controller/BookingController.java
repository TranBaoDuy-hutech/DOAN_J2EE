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
        if (customer == null) {
            return "redirect:/login";
        }

        Tours tour = entityManager.find(Tours.class, id);

        model.addAttribute("tour", tour);
        model.addAttribute("customer", customer);

        return "booking-form";
    }

    /**
     * XEM TRƯỚC HỢP ĐỒNG (CHƯA LƯU DATABASE)
     */
    @GetMapping("/preview-contract")
    public ResponseEntity<byte[]> previewContract(@RequestParam int tourID,
                                                  @RequestParam int numberOfPeople,
                                                  HttpSession session) {

        Customer customer = (Customer) session.getAttribute("user");
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Tours tour = entityManager.find(Tours.class, tourID);
        if (tour == null) {
            return ResponseEntity.notFound().build();
        }

        Booking booking = new Booking();
        booking.setBookingID(0);
        booking.setTour(tour);
        booking.setCustomer(customer);
        booking.setNumberOfPeople(numberOfPeople);
        booking.setBookingDate(LocalDate.now());
        booking.setTotalPrice(tour.getPrice().doubleValue() * numberOfPeople);

        byte[] pdf = contractService.generateContract(booking);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.inline().filename("preview-hop-dong-tour.pdf").build()
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }

    /**
     * XỬ LÝ ĐẶT TOUR (LÚC NÀY MỚI LƯU DB)
     */
    @PostMapping("/booking-tour")
    public String bookTour(@RequestParam("tourID") int tourID,
                           @RequestParam("numberOfPeople") int numberOfPeople,
                           HttpSession session) {

        Customer customer = (Customer) session.getAttribute("user");

        if (customer == null) {
            return "redirect:/login";
        }

        Booking booking = bookingService.bookTour(
                tourID,
                customer.getCustomerID(),
                numberOfPeople
        );

        // Sau khi đặt xong → mở hợp đồng thật
        return "redirect:/contract/" + booking.getBookingID();
    }

    /**
     * HỢP ĐỒNG CHÍNH THỨC (SAU KHI ĐẶT TOUR)
     */
    @GetMapping("/contract/{id}")
    public ResponseEntity<byte[]> viewContract(@PathVariable int id) {

        Booking booking = bookingService.getBookingById(id);

        if (booking == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] pdf = contractService.generateContract(booking);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.inline().filename("hop-dong-tour.pdf").build()
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}