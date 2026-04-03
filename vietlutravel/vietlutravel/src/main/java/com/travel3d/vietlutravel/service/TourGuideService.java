package com.travel3d.vietlutravel.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel3d.vietlutravel.model.Booking;
import com.travel3d.vietlutravel.model.TourGuide;
import com.travel3d.vietlutravel.repository.TourGuideRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class TourGuideService {

    @Autowired
    private TourGuideRepository tourGuideRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<TourGuide> getAllGuides() {
        return tourGuideRepository.findAll();
    }

    public TourGuide getGuideById(Integer id) {
        return tourGuideRepository.findById(id).orElse(null);
    }

    public void saveGuide(TourGuide guide) {
        tourGuideRepository.save(guide);
    }

    public void deleteGuide(Integer id) {
        tourGuideRepository.deleteById(id);
    }

    public boolean isGuideAvailable(Integer guideId, LocalDate startDate, LocalDate endDate, List<Integer> excludeBookingIds) {
        if (guideId == null || startDate == null || endDate == null) return true;

        List<Booking> allBookings = entityManager.createQuery(
                "FROM Booking b WHERE b.tourGuide.guideId = :guideId AND b.status != 'Cancelled'", Booking.class)
                .setParameter("guideId", guideId)
                .getResultList();

        for (Booking b : allBookings) {
            if (excludeBookingIds != null && excludeBookingIds.contains(b.getBookingID())) {
                continue;
            }
            if (b.getTravelDate() != null && b.getTour() != null && b.getTour().getDurationDays() != null) {
                LocalDate existingStart = b.getTravelDate();
                LocalDate existingEnd = existingStart.plusDays(b.getTour().getDurationDays() - 1);

                if (!startDate.isAfter(existingEnd) && !endDate.isBefore(existingStart)) {
                    return false; // Trùng lịch
                }
            }
        }
        return true;
    }
}
