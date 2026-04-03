package com.travel3d.vietlutravel.repository;

import com.travel3d.vietlutravel.model.TourGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourGuideRepository extends JpaRepository<TourGuide, Integer> {
}
