package com.example.kargo.controller;

import com.example.kargo.model.Bildirim;
import com.example.kargo.repository.BildirimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bildirimler")
@CrossOrigin(origins = "*")
public class BildirimController {

    @Autowired
    private BildirimRepository bildirimRepository;

    // Tüm bildirimleri listeler
    @GetMapping
    public List<Bildirim> tumBildirimleriGetir() {
        return bildirimRepository.findAll();
    }

    // Bildirimi 'okundu' (true) olarak işaretler
    @PutMapping("/{id}/oku")
    public void okunduYap(@PathVariable Long id) {
        Bildirim bildirim = bildirimRepository.findById(id).orElse(null);
        if (bildirim != null) {
            bildirim.setOkundu(true);
            bildirimRepository.save(bildirim);
        }
    }

    // 🔥 GÜNCELLEME: Bildirimi veritabanından tamamen siler
    @DeleteMapping("/{id}")
    public void bildirimiSil(@PathVariable Long id) {
        if (bildirimRepository.existsById(id)) {
            bildirimRepository.deleteById(id);
        }
    }
}