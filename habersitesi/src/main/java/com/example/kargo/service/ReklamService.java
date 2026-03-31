package com.example.kargo.service;

import com.example.kargo.model.Reklam;
import com.example.kargo.repository.ReklamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReklamService {

    @Autowired
    private ReklamRepository reklamRepository;

    // Reklamları Listele
    public List<Reklam> tumReklamlar() {
        return reklamRepository.findAll();
    }

    // Reklam Kaydet
    public Reklam reklamKaydet(Reklam reklam) {
        return reklamRepository.save(reklam);
    }

    // Reklam Sil
    public void reklamSil(Long id) {
        reklamRepository.deleteById(id);
    }

    // Reklam Güncelle
    public Reklam reklamGuncelle(Long id, Reklam yeniReklam) {
        return reklamRepository.findById(id)
                .map(reklam -> {
                    reklam.setReklamAdi(yeniReklam.getReklamAdi());
                    reklam.setReklamLinki(yeniReklam.getReklamLinki());
                    reklam.setGorselUrl(yeniReklam.getGorselUrl());
                    reklam.setFiyat(yeniReklam.getFiyat());
                    return reklamRepository.save(reklam);
                }).orElse(null);
    }
}