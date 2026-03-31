package com.example.kargo.controller;

import com.example.kargo.model.Haber;
import com.example.kargo.model.Bildirim;
import com.example.kargo.service.HaberService;
import com.example.kargo.repository.BildirimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/haberler")
@CrossOrigin(origins = "http://localhost:3000")
public class HaberController {

    @Autowired
    private HaberService haberService;

    @Autowired
    private BildirimRepository bildirimRepository;

    @GetMapping
    public List<Haber> getAllHaberler() {
        return haberService.tumHaberleriGetir();
    }

    // ✅ HABER EKLEME VE OTOMATİK BİLDİRİM
    @PostMapping
    public Haber haberEkle(@RequestBody Haber haber) {
        // Haber modeline gorselUrl eklediğimiz için @RequestBody bunu otomatik yakalar.
        Haber yeniHaber = haberService.haberKaydet(haber);

        // 🔔 BİLDİRİM OLUŞTURMA
        Bildirim bildirim = new Bildirim();
        String haberBasligi = (yeniHaber.getBaslik() != null) ? yeniHaber.getBaslik() : "Yeni Haber";

        bildirim.setMesaj("Yeni bir haber eklendi: " + haberBasligi);
        bildirim.setTarih(LocalDateTime.now());
        bildirim.setOkundu(false);
        bildirimRepository.save(bildirim);

        return yeniHaber;
    }

    @PostMapping("/{id}/begen")
    public void haberBegen(@PathVariable Long id) {
        haberService.begeniArttir(id);
    }

    @PutMapping("/{id}")
    public Haber haberGuncelle(@PathVariable Long id, @RequestBody Haber yeniHaber) {
        return haberService.haberGuncelle(id, yeniHaber);
    }

    // 🔥 SİLME METODU
    @DeleteMapping("/{id}")
    public void haberSil(@PathVariable Long id) {
        haberService.haberSil(id);
    }

    // 📌 HABER KAYDETME METODU
    @PostMapping("/{id}/kaydet")
    public String haberKaydet(@PathVariable Long id) {
        return haberService.haberKaydetKullaniciya(1L, id);
    }
}