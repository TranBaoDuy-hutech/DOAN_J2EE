package com.travel3d.vietlutravel.controller;

import com.travel3d.vietlutravel.model.Tours;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
public class ToursController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/tours")
    public String toursPage(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "date", required = false) String date,
            Model model) {

        StringBuilder jpql = new StringBuilder("SELECT t FROM Tours t WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            jpql.append(" AND (")
                    .append("LOWER(t.tourName) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
                    .append("OR LOWER(t.location) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
                    .append("OR LOWER(t.departureLocation) LIKE LOWER(CONCAT('%', :keyword, '%'))")
                    .append(")");
        }

        if (date != null && !date.isEmpty()) {
            jpql.append(" AND FUNCTION('DATE', t.startDate) = :date");
        }

        var query = entityManager.createQuery(jpql.toString(), Tours.class);

        if (keyword != null && !keyword.trim().isEmpty()) {
            query.setParameter("keyword", keyword.trim());
        }

        if (date != null && !date.isEmpty()) {
            query.setParameter("date", Date.valueOf(date));
        }

        List<Tours> tours = query.getResultList();

        model.addAttribute("tours", tours);
        model.addAttribute("keyword", keyword);
        model.addAttribute("date", date);
        model.addAttribute("currentPage", "tours");

        return "tours";
    }
}