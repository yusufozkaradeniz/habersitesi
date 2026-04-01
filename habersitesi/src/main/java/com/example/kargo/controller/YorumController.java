package com.example.kargo.controller;

import com.example.kargo.model.Yorum;
import com.example.kargo.repository.YorumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
// 🔥 HATA ALMAMAK İÇİN BU IMPORT ŞART:
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RestController
@RequestMapping("/api/yorumlar")
// 🛡️ YORUMLARIN İNTERNETTE SORUNSUZ ÇALIŞMASI İÇİN TÜM KAPILARI AÇIYORUZ:
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class YorumController {

    @Autowired
    private YorumRepository yorumRepository;

    // Yeni yorum eklemek için (POST)
    @PostMapping
    public Yorum yorumEkle(@RequestBody Yorum yorum) {
        return yorumRepository.save(yorum);
    }

    // Bir haberin altındaki tüm yorumları çekmek için (GET)
    @GetMapping("/haber/{haberId}")
    public List<Yorum> getYorumlarByHaber(@PathVariable Long haberId) {
        return yorumRepository.findByHaberId(haberId);
    }

    // Bir yorumu silmek için (DELETE)
    @DeleteMapping("/{id}")
    public void yorumSil(@PathVariable Long id) {
        yorumRepository.deleteById(id);
    }
}
