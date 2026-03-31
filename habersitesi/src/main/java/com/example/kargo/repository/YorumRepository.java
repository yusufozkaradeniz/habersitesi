package com.example.kargo.repository;

import com.example.kargo.model.Yorum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface YorumRepository extends JpaRepository<Yorum, Long> {
    // Belirli bir habere ait yorumları bulmak için
    List<Yorum> findByHaberId(Long haberId);
}