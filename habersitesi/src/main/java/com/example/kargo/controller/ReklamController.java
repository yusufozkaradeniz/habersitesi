package com.example.kargo.controller;

import com.example.kargo.model.Reklam;
import com.example.kargo.service.ReklamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reklamlar")
@CrossOrigin(origins = "*") // React ile Java'nın el sıkışmasını sağlar
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