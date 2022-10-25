/*
Observacions:

A) DeleteById method.
Segons l'especificació en CrudRepository (org.springframework.data.repository)
només hauria de generar una excepció si l'argument id és null. I si NO es trova no s'hauria de fer res.
Problema: la implementació per defecte és SimpleJpaRepository (org.springframework.data.jpa.repository.support),
i en aquesta implementació el deleteById llança una excepció EmptyResultDataAccessException si no existeix:
    Default deleteById:

    delete(findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
                String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), id), 1)))

Solució: sobreescriure el mètode i així, si no es trova el recurs, no es llanci excepció i es consideri
que la lògica s'ha executat amb èxit.
Solució 2: capturar i gestionar l'excepció com si tot estigués ok.
    No viable -> altres situacions poden llançar l'excepció, i la resposta ha de ser un error.
Solució 3: en lloc d'usar deleteById, usar un mètode personalitzat tipus deleteByX (x: field name).
    void deleteByPk_FlorID(Integer pk_FlorID)
    No viable -> Error Spring Data is not able to resolve, due field name (es queda només amb 'pk',
                a causa del format del nom de l'atribut). I a més SonarLint genera alerta a causa del format
                del nom de l'atribut. Però aquest NO es pot modificar, ja que és un requisit
                del projecte.
 */

package com.martinfluviapablo.s5t1n2.model.repository;

import com.martinfluviapablo.s5t1n2.model.domain.FlorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.Optional;

@Repository
public interface FlorRepository extends JpaRepository<FlorEntity,Integer> {

    @Transactional
    @Override
    default void deleteById(Integer id) {
        Assert.notNull(id, "The given id must not be null!");
        findById(id).ifPresent(this::delete); //new
    }

    Optional<FlorEntity> findByNomFlorAndPaisFlor(String nomFlor, String paisFlor);
}
