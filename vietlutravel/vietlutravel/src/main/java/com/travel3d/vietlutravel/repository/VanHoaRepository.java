package com.travel3d.vietlutravel.repository;

import com.travel3d.vietlutravel.model.VanHoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VanHoaRepository extends JpaRepository<VanHoa, Long> {
}