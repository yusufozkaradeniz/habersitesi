package com.example.kargo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Yorum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kullaniciAdi;
    private String icerik;

    // Hangi habere ait olduğunu belirtiyoruz
    private Long haberId;
}