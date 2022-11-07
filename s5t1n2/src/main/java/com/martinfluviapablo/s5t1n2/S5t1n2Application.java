
/*
NOTES:
0) Application.properties configurat per a:
	Llançar excepcions si no existeix cap mètode mapejat per a gestionar una petició.
	No permetre que jackson trunqui un número decimal quan s'espera un enter.
	Crear la base de dades "flors" si no existeix en la ruta proporcionada.
1) Disseny per contracte. Asserts per a validar pre-condicions de mètodes.
2) Path general dels recursos canviat a plural: /flors enlloc de /flor -> veure FlorController
3) No està permés tenir en la BBDD 2 registres amb idèntic nom i país
	-> veure FlorService mètode assertNoDataDuplication
4) Injeccions @Autowired anotant constructor. Bona pràctica per a fer tests.
5) Classe Apunts no influeix en la API. La deixo perquè l'he usat per a fer comentaris i validacions
dels dos moments en què m'he bloquejat, i així em serveix d'apunts.
	Última versió del hibernate-validator
	No funcionament de les propietats del mapper de json degut a @EnableMvC (opció chapuza per a solucionar
	problema de propietats no correctament indicades per a llançar NoHandlerFoundException)


A) Implementació per defecte del DAO deleteById pot generar problemes -> veure interface FlorRepository
B) Per a saber si el string associat al nom d'un país pertany a la UE
	no es té en compte les majúscules ni accents. Ex: iTaLiA = Itàlia -> veure InfoUE en domain.
C) La lògica dels mètodes de FlorService els he implementat sense declarar variables locals, ja que
	no serien necessaries/correctes (no hi hauria canvis de valors/estat), i al mateix temps que siguin
	lo més llegibles possible (fàcils d'entendre) -> veure FlorService
D) Missatges d'error si falla una constraint -> ValidationMessages.properties
E) Validacions del dto en POST i PUT:
	tipusPais -> null (sempre)
	nom i pais -> not blank	i número màxim de caràcters (sempre) -> compounded constraint @ValidString
	id -> null (només en POST) -> validació del grup AddGroup
		-> not null i positiu (només en PUT) -> compounded constraint @ValidID i validació del grup ReplaceGroup
		NOTA: els les 2 interfaces d'agrupació hereden del Default group
			-> Realitzar una validació d'aquell grup implica validar les restriccions:
					específiques d'aquell grup + les que no estan associades a cap grup
	-> veure paquet dto
F) Lògica per afegir links en la resposta -> veure FlorModelAssembler en paquet controller
G) Controlador té anotació @Validated a nivell de classe i també en els arguments @RequestBody
	-> veure FlorController
H) @RestControllerAdvice per a gestionar totes les possibles excepcions d'una manera personalitzada
 */

/*
TODO: Verificacions:
E) Transformació a JSON <-> dto funciona si no hi ha "no args constructor"?
 	(exemple: només un constructor amb tots els atributs) OJO amb el toDto del parser quan faci la prova
F) Una anotació @Validated amb àmbit de classe i una amb àmbit d'argument de mètode +
 les dues associades a grups diferents i excloents -> quina té preferència al invocar el mètode?
 S'aplica:
 	els dos grups -> ??
 	només el del mètode -> ??
 	només el de la classe -> NO
G) Missatge d'error llegible quan es gestionen les excepcions:
	MethodArgumentTypeMismatchException
	MethodArgumentNotValidException
	HttpMessageNotReadableException
	utils: https://www.baeldung.com/global-error-handler-in-a-spring-rest-api

TODO: Preguntar:
1) Els links a adjuntar al retornar un recurs només són els que tenen un mètode GET?
En tots els exemples que he vist és així, però enlloc he vist una explicació...

TODO: Aprofundir en validacions (especifiació JSR 303 i implementació Hibernate Validator)
https://beanvalidation.org/1.0/spec/
https://programmerclick.com/article/42141828963/
 */

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
