package com.example.kargo.model;

import jakarta.persistence.*;

@Entity
public class Haber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baslik;

    @Column(length = 5000) // İçerik çok uzun olursa hata vermemesi için
    private String icerik;

    private int begeniSayisi = 0;

     // görsel url tutucak alan
    private String gorselUrl;

    // Getter ve Setter'lar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }

    public String getIcerik() { return icerik; }
    public void setIcerik(String icerik) { this.icerik = icerik; }

    public int getBegeniSayisi() { return begeniSayisi; }
    public void setBegeniSayisi(int begeniSayisi) { this.begeniSayisi = begeniSayisi; }

    // görsel url için getter ve setter

    public String getGorselUrl() { return gorselUrl; }
    public void setGorselUrl(String gorselUrl) { this.gorselUrl = gorselUrl; }
}