package com.example.kargo.repository;

import com.example.kargo.model.Reklam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReklamRepository extends JpaRepository<Reklam, Long> {
}