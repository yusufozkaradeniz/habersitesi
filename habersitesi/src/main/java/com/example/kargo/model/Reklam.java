package com.example.kargo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reklam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reklamAdi;
    private String reklamLinki;
    private String gorselUrl;
    private Double fiyat;

    // Getter ve Setter Metotları
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getReklamAdi() { return reklamAdi; }
    public void setReklamAdi(String reklamAdi) { this.reklamAdi = reklamAdi; }
    public String getReklamLinki() { return reklamLinki; }
    public void setReklamLinki(String reklamLinki) { this.reklamLinki = reklamLinki; }
    public String getGorselUrl() { return gorselUrl; }
    public void setGorselUrl(String gorselUrl) { this.gorselUrl = gorselUrl; }
    public Double getFiyat() { return fiyat; }
    public void setFiyat(Double fiyat) { this.fiyat = fiyat; }
}