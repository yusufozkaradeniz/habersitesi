package com.example.kargo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bildirimler") // Veritabanında tablo adı 'bildirimler' olsun
public class Bildirim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mesaj;

    private LocalDateTime tarih;

    private boolean okundu = false; // Yeni bildirimler varsayılan olarak okunmamış (false) gelir

    // --- Constructor (Yapıcı Metotlar) ---
    public Bildirim() {}

    public Bildirim(String mesaj, LocalDateTime tarih) {
        this.mesaj = mesaj;
        this.tarih = tarih;
        this.okundu = false;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMesaj() { return mesaj; }
    public void setMesaj(String mesaj) { this.mesaj = mesaj; }

    public LocalDateTime getTarih() { return tarih; }
    public void setTarih(LocalDateTime tarih) { this.tarih = tarih; }

    public boolean isOkundu() { return okundu; }
    public void setOkundu(boolean okundu) { this.okundu = okundu; }
}