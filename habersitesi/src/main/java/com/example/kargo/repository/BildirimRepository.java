package com.example.kargo.repository;

import com.example.kargo.model.Bildirim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BildirimRepository extends JpaRepository<Bildirim, Long> {

    // Okunmamış bildirimleri yeniden eskiye doğru sıralayıp getiren özel sorgumuz:
    List<Bildirim> findByOkunduFalseOrderByTarihDesc();
}