package com.example.kargo.repository;

import com.example.kargo.model.Haber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HaberRepository extends JpaRepository<Haber, Long> {
}