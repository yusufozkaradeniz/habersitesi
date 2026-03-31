package com.example.kargo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Kullanici {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isim;
    private String soyisim;
    private String email;
    private String unvan;
    private String sifre;

    @Column(length = 500)
    private String hakkimda;

    private String profilResmiUrl;

    // Şifre sıfırlama kodunu burada saklıyoruz
    private String sifreSifirlamaKodu;

    // : fetch = FetchType.EAGER eklendi
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "kullanici_kaydedilenler",
            joinColumns = @JoinColumn(name = "kullanici_id"),
            inverseJoinColumns = @JoinColumn(name = "haber_id"))
    private List<Haber> kaydedilenHaberler = new java.util.ArrayList<>();
}