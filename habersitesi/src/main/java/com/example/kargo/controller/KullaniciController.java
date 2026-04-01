package com.example.kargo.controller;

import com.example.kargo.model.Haber;
import com.example.kargo.model.Kullanici;
import com.example.kargo.repository.HaberRepository;
import com.example.kargo.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/kullanici")
// 🔥 BURASI GÜNCELLENDİ: Chrome'un 'www' inadını kırmak için her iki adresi de açıkça belirttik.
@CrossOrigin(
    origins = {"https://yusufozkaradeniz.xyz", "https://www.yusufozkaradeniz.xyz"}, 
    allowedHeaders = "*", 
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
    allowCredentials = "true"
)
public class KullaniciController {

    @Autowired
    private KullaniciRepository kullaniciRepo;

    @Autowired
    private HaberRepository haberRepository;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping
    public List<Kullanici> getProfil() {
        return kullaniciRepo.findAll();
    }

    @PostMapping("/kayit")
    public ResponseEntity<String> kayitOl(@RequestBody Kullanici yeniKullanici) {
        if (kullaniciRepo.findByEmail(yeniKullanici.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body("❌ Bu e-posta adresi zaten kullanımda!");
        }
        kullaniciRepo.save(yeniKullanici);
        return ResponseEntity.ok("✅ Kayıt başarıyla tamamlandı.");
    }

    @PostMapping("/giris")
    public ResponseEntity<?> girisYap(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String sifre = loginRequest.get("sifre");
        Optional<Kullanici> kullaniciOpt = kullaniciRepo.findByEmail(email);
        if (kullaniciOpt.isPresent()) {
            Kullanici kullanici = kullaniciOpt.get();
            if (sifre != null && sifre.equals(kullanici.getSifre())) {
                return ResponseEntity.ok(kullanici);
            } else {
                return ResponseEntity.status(401).body("❌ Hatalı şifre!");
            }
        }
        return ResponseEntity.status(404).body("❌ Kullanıcı bulunamadı.");
    }

    @PostMapping("/sifre-sifirla")
    public ResponseEntity<String> sifreSifirla(@RequestBody Map<String, String> request) {
        String aliciEmail = request.get("email");
        Optional<Kullanici> kullaniciOpt = kullaniciRepo.findByEmail(aliciEmail);

        if (kullaniciOpt.isPresent()) {
            Kullanici kullanici = kullaniciOpt.get();
            String rastgeleKod = String.valueOf((int)(Math.random() * 900000) + 100000);

            kullanici.setSifreSifirlamaKodu(rastgeleKod);
            kullaniciRepo.save(kullanici);

            try {
                SimpleMailMessage mesaj = new SimpleMailMessage();
                mesaj.setTo(aliciEmail);
                mesaj.setSubject("Haber Sitesi - Şifre Sıfırlama Kodu");
                mesaj.setText("Merhaba " + kullanici.getIsim() + ",\n\nKodunuz: " + rastgeleKod);
                mailSender.send(mesaj);
                return ResponseEntity.ok("✅ Kod gönderildi.");
            } catch (Exception e) {
                return ResponseEntity.ok("✅ Kod üretildi (Konsoldan kontrol et).");
            }
        }
        return ResponseEntity.status(404).body("❌ Kullanıcı bulunamadı.");
    }

    @PostMapping("/kod-dogrula")
    public ResponseEntity<String> kodDogrula(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String girilenKod = request.get("kod");
        Optional<Kullanici> kullaniciOpt = kullaniciRepo.findByEmail(email);

        if (kullaniciOpt.isPresent() && girilenKod != null && girilenKod.equals(kullaniciOpt.get().getSifreSifirlamaKodu())) {
            return ResponseEntity.ok("✅ Kod doğru.");
        }
        return ResponseEntity.status(400).body("❌ Kod hatalı.");
    }

    @PostMapping("/sifre-guncelle")
    public ResponseEntity<String> sifreGuncelle(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String yeniSifre = request.get("sifre");
        String kod = request.get("kod");

        Optional<Kullanici> kullaniciOpt = kullaniciRepo.findByEmail(email);

        if (kullaniciOpt.isPresent()) {
            Kullanici kullanici = kullaniciOpt.get();
            if (kod != null && kod.equals(kullanici.getSifreSifirlamaKodu())) {
                kullanici.setSifre(yeniSifre);
                kullanici.setSifreSifirlamaKodu(null);
                kullaniciRepo.save(kullanici);
                return ResponseEntity.ok("✅ Şifre güncellendi.");
            }
        }
        return ResponseEntity.status(400).body("❌ İşlem başarısız.");
    }

    @PutMapping("/{id}")
    public Kullanici guncelle(@PathVariable Long id, @RequestBody Kullanici yeniBilgiler) {
        return kullaniciRepo.findById(id).map(kullanici -> {
            kullanici.setIsim(yeniBilgiler.getIsim());
            kullanici.setSoyisim(yeniBilgiler.getSoyisim());
            kullanici.setUnvan(yeniBilgiler.getUnvan());
            kullanici.setHakkimda(yeniBilgiler.getHakkimda());
            kullanici.setEmail(yeniBilgiler.getEmail());
            return kullaniciRepo.save(kullanici);
        }).orElseGet(() -> {
            yeniBilgiler.setId(id);
            return kullaniciRepo.save(yeniBilgiler);
        });
    }

    @PostMapping("/{kullaniciId}/kaydet/{haberId}")
    public String haberKaydet(@PathVariable Long kullaniciId, @PathVariable Long haberId) {
        Optional<Kullanici> kullaniciOpt = kullaniciRepo.findById(kullaniciId);
        Optional<Haber> haberOpt = haberRepository.findById(haberId);

        if (kullaniciOpt.isPresent() && haberOpt.isPresent()) {
            Kullanici kullanici = kullaniciOpt.get();
            Haber haber = haberOpt.get();
            if(!kullanici.getKaydedilenHaberler().contains(haber)) {
                kullanici.getKaydedilenHaberler().add(haber);
                kullaniciRepo.save(kullanici);
                return "Haber kaydedildi!";
            }
            return "Zaten kayıtlı.";
        }
        return "Hata!";
    }

    @GetMapping("/{id}/kaydedilenler")
    public List<Haber> getKaydedilenler(@PathVariable Long id) {
        return kullaniciRepo.findById(id)
                .map(Kullanici::getKaydedilenHaberler)
                .orElse(null);
    }

    @DeleteMapping("/{kullaniciId}/kaydedilenler/{haberId}")
    public ResponseEntity<String> kaydedilenHaberiSil(@PathVariable Long kullaniciId, @PathVariable Long haberId) {
        Optional<Kullanici> kullaniciOpt = kullaniciRepo.findById(kullaniciId);
        Optional<Haber> haberOpt = haberRepository.findById(haberId);

        if (kullaniciOpt.isPresent() && haberOpt.isPresent()) {
            Kullanici kullanici = kullaniciOpt.get();
            Haber haber = haberOpt.get();

            if (kullanici.getKaydedilenHaberler().contains(haber)) {
                kullanici.getKaydedilenHaberler().remove(haber);
                kullaniciRepo.save(kullanici);
                return ResponseEntity.ok("Haber listeden kaldırıldı.");
            }
        }
        return ResponseEntity.status(404).body("Hata oluştu.");
    }
}
