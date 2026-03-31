package com.example.kargo;

import com.example.kargo.model.Haber;
import com.example.kargo.model.Kullanici;
import com.example.kargo.repository.HaberRepository;
import com.example.kargo.repository.KullaniciRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KargoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KargoApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(HaberRepository haberRepo, KullaniciRepository kullaniciRepo) {
		return args -> {
			// Örnek Haberleri Ekle
			if (haberRepo.count() == 0) {
				Haber h1 = new Haber();
				h1.setBaslik("Java Spring Boot Öğreniyoruz!");
				h1.setIcerik("Profil sayfası entegrasyonu başarıyla devam ediyor.");
				h1.setBegeniSayisi(5);
				haberRepo.save(h1);
			}


			if (kullaniciRepo.count() != 0) {
				kullaniciRepo.deleteAll();
				Kullanici admin = new Kullanici();
				admin.setIsim("Yusuf");
				admin.setSoyisim("Özkaradeniz");
				admin.setEmail("yusufzkrdz@gmail.com");
				admin.setUnvan("Baş Editör");
				admin.setHakkimda("Haber sitesi projesini geliştiren ve yöneten yazılım geliştiricisi.");
				admin.setSifre("12345");

				kullaniciRepo.save(admin);
				System.out.println(">> SISTEM: Yusuf Özkaradeniz profili başarıyla oluşturuldu! <<");
			}
		};
	}
}