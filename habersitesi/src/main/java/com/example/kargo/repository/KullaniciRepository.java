package com.example.kargo.repository;

import com.example.kargo.model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {

    // Veritabanında email adresine göre kullanıcıyı bulmamızı sağlar
    Optional<Kullanici> findByEmail(String email);

}