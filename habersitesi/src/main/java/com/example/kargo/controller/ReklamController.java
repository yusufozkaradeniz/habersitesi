package com.example.kargo.controller;

import com.example.kargo.model.Reklam;
import com.example.kargo.service.ReklamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
// 🔥 HATA ALMAMAK İÇİN BU IMPORT ŞART:
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RestController
@RequestMapping("/api/reklamlar")
// 🛡️ REKLAMLARIN YÖNETİLMESİ İÇİN TÜM KAPILARI AÇIYORUZ:
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ReklamController {

    @Autowired
    private ReklamService reklamService;

    // 8. Madde: Reklamları listeler (GET)
    @GetMapping
    public List<Reklam> hepsiniGetir() {
        return reklamService.tumReklamlar();
    }

    // 9. Madde: Reklam ekler veya günceller (POST)
    @PostMapping
    public Reklam yeniEkle(@RequestBody Reklam reklam) {
        return reklamService.reklamKaydet(reklam);
    }

    // 10. Madde: Reklam siler (DELETE)
    @DeleteMapping("/{id}")
    public void sil(@PathVariable Long id) {
        reklamService.reklamSil(id);
    }

    // 11. Madde: Reklam Güncelle (PUT)
    @PutMapping("/{id}")
    public Reklam guncelle(@PathVariable Long id, @RequestBody Reklam reklam) {
        return reklamService.reklamGuncelle(id, reklam);
    }
}
