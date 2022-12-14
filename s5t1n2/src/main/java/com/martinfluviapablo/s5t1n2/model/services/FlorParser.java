/*
Conversió FlorDto <-> FlorEntity

Es suggereix que la conversió es dugui a terme durant el servei (controladors només dto i repositoris només
entitats).
Qui es fa responsable (implementa la lògica)?
    Opció dto: descartat. Un dto (capa controladors) no hauria d'estar acoplat a una entitat (persistència).
    Opció entity: descartat. Ídem raonament a la inversa.
    Opció crear classe Flor en la capa de negoci que:
        faci de model amb el qual treballa el servei
        constructors a partir d'un dto o d'una entitat
        mètodes toDto i toEntity.
        -> Innecessari: poca responsabilitat i augment de la complexitat (servei tal com rep el dto crea Flor
        per a després passar-lo a entitat: new Flor(dto).toEntity o flor.fromDto(dto).toEntity (i viceversa))
        -> Un altre disseny seria que:
            el controlador també treballés amb aquesta classe i les conversions dto <-> Flor es fessin allà
            el servei només treballa amb Flor
            hi hagués un adaptador, entre el servei i la persistència, en el que es fessin les conversions
                Flor <-> entity
            -> només seria interessant si Flor pogués assumir alguna part de la lògica del negoci.
     Opció mètodes toDto i toEntity directament en FlorService: no em convenç,
        implicaria haver d'acoblar-se al bean UeService només per a ser usat en un sol mètode
        implicaria que la classe fos susceptible a canvis a causa de 2 motius diferents:
            variació dels requisits dels endpoints de la api
            variació de l'estructura de la data del model
      Opció crear una classe específica per a la conversió:
        soluciona problema de la responsabilitat i permet extensibilitat.
 */

package com.martinfluviapablo.s5t1n2.model.services;

import com.martinfluviapablo.s5t1n2.model.domain.FlorEntity;
import com.martinfluviapablo.s5t1n2.model.dto.FlorDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class FlorParser {

    private final UeService ueService;

    @Autowired
    public FlorParser(UeService ueService) {
        this.ueService = ueService;
    }

    public FlorDto toDto(FlorEntity entity){
        Assert.notNull(entity,"The given flor entity must not be null!");
        FlorDto dto = new FlorDto();
        dto.setPk_FlorID(entity.getPk_FlorID());
        dto.setNomFlor(entity.getNomFlor());
        dto.setPaisFlor(entity.getPaisFlor());
        dto.setTipusFlor(ueService.getMembershipInfo(entity.getPaisFlor()));
        return dto;
    }

    public FlorEntity toEntity(FlorDto dto){
        Assert.notNull(dto,"The given flor entity must not be null!");
        FlorEntity entity = new FlorEntity();
        updateEntity(entity,dto);
        return entity;
    }

    public FlorEntity updateEntity(FlorEntity entity, FlorDto dto){
        Assert.notNull(entity,"The given flor entity entity must not be null!");
        Assert.notNull(dto,"The given flor dto entity must not be null!");
        entity.setNomFlor(dto.getNomFlor());
        entity.setPaisFlor(dto.getPaisFlor());
        return entity;
    }
}
