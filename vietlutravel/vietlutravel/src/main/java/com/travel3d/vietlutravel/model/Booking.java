package com.travel3d.vietlutravel.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingID;

    // GIỮ NGUYÊN CỘT
    @Column(name = "customerID")
    private int customerID;

    @Column(name = "tourID")
    private int tourID;

    private LocalDate bookingDate;
    private int numberOfPeople;
    private double totalPrice;
    private String status;
    private String specialRequests;

    @ManyToOne
    @JoinColumn(name = "customerID", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "tourID", insertable = false, updatable = false)
    private Tours tour;

    // getter setter đầy đủ
    public int getBookingID() { return bookingID; }
    public void setBookingID(int bookingID) { this.bookingID = bookingID; }

    public int getCustomerID() { return customerID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    public int getTourID() { return tourID; }
    public void setTourID(int tourID) { this.tourID = tourID; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }

    public int getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(int numberOfPeople) { this.numberOfPeople = numberOfPeople; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }

    public Customer getCustomer() { return customer; }
    public Tours getTour() { return tour; }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTour(Tours tour) {
        this.tour = tour;
    }

}
