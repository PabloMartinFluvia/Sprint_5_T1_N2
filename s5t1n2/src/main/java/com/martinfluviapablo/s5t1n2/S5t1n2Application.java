package com.martinfluviapablo.s5t1n2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class S5t1n2Application {

	public static void main(String[] args) {
		SpringApplication.run(S5t1n2Application.class, args);
	}

	/*
	//Per si es vol provar l'API amb algunes flors guardades per defecte
	@Bean
	public CommandLineRunner initialPopulation(FlorRepository repository){
		return args -> {
			FlorEntity flor = new FlorEntity();
			flor.setNomFlor("nom1");
			flor.setPaisFlor("pais1");
			repository.findByNomFlorAndPaisFlor(flor.getNomFlor(), flor.getPaisFlor()).orElse(repository.save(flor));
			flor = new FlorEntity();
			flor.setNomFlor("nom2");
			flor.setPaisFlor("itaLia");
			repository.findByNomFlorAndPaisFlor(flor.getNomFlor(), flor.getPaisFlor()).orElse(repository.save(flor));
			flor = new FlorEntity();
			flor.setNomFlor("nom3");
			flor.setPaisFlor("Espanya");
			repository.findByNomFlorAndPaisFlor(flor.getNomFlor(), flor.getPaisFlor()).orElse(repository.save(flor));
		};
	}
	*/
}
