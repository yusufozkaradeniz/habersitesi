package com.example.kargo.service;

import com.example.kargo.model.Haber;
import com.example.kargo.model.Kullanici;
import com.example.kargo.repository.HaberRepository;
import com.example.kargo.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HaberService {

    @Autowired
    private HaberRepository haberRepository;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    // Tüm haberleri listelemek için
    public List<Haber> tumHaberleriGetir() {
        return haberRepository.findAll();
    }

    //  Beğeni Sayısını 1 Artıran Fonksiyon
    public void begeniArttir(Long id) {
        Haber haber = haberRepository.findById(id).orElse(null);
        if (haber != null) {
            haber.setBegeniSayisi(haber.getBegeniSayisi() + 1);
            haberRepository.save(haber);
        }
    }

    // Haber ve Alt Yazıyı (İçeriği) Güncelleme
    public Haber haberGuncelle(Long id, Haber yeniHaber) {
        Haber eskiHaber = haberRepository.findById(id).orElse(null);
        if (eskiHaber != null) {
            eskiHaber.setBaslik(yeniHaber.getBaslik()); // Başlığı günceller
            eskiHaber.setIcerik(yeniHaber.getIcerik()); // ALT YAZIYI (içeriği) günceller
            return haberRepository.save(eskiHaber);
        }
        return null;
    }

    public Haber haberKaydet(Haber haber) {
        return haberRepository.save(haber); // Burayı ekledik, haberi veritabanına yazar.
    }

    //  SİLME METODU ( EN ALTA EKLENDİ)
    public void haberSil(Long id) {
        haberRepository.deleteById(id);
    }

    //  HABERİ KULLANICIYA KAYDETME METODU (BOZMADAN EKLENDİ)
    public String haberKaydetKullaniciya(Long kullaniciId, Long haberId) {
        Kullanici kullanici = kullaniciRepository.findById(kullaniciId).orElse(null);
        Haber haber = haberRepository.findById(haberId).orElse(null);

        if (kullanici != null && haber != null) {
            kullanici.getKaydedilenHaberler().add(haber);
            kullaniciRepository.save(kullanici);
            return "Haber başarıyla kaydedildi!";
        }
        return "Hata: Kullanıcı veya Haber bulunamadı!";
    }
}