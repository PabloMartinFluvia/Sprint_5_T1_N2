package com.martinfluviapablo.s5t1n2.model.services;

import com.martinfluviapablo.s5t1n2.model.dto.FlorDto;
import com.martinfluviapablo.s5t1n2.model.repository.FlorRepository;

import com.martinfluviapablo.s5t1n2.exceptions.DuplicateDataException;
import com.martinfluviapablo.s5t1n2.exceptions.FlorNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.List;

@Service
public class FlorService {

    private final FlorRepository repository;

    private final FlorParser parser;

    @Autowired
    public FlorService(FlorRepository repository, FlorParser parser) {
        this.repository = repository;
        this.parser = parser;
    }

    public List<FlorDto> getAll(){
        return repository.findAll().stream()
                .map(parser::toDto).toList();
    }

    public FlorDto getOne(Integer id){
        Assert.notNull(id,"The given id must not be null!");
        //this method can work with id<=0

        return repository.findById(id)
               .map(parser::toDto)
               .orElseThrow(() -> new FlorNotFoundException("No existeix cap flor amb id: "+id));
    }

    public void deleteOne(Integer id){
        Assert.notNull(id,"The given id must not be null!");
        //this method can work with id<=0

        repository.deleteById(id);
    }

    public FlorDto addOne(FlorDto dto){
        doAssertsBdSchema(dto);
        //this method can work with empty String fields, and ignores dto's id value when is parsed to entity

        return parser.toDto(
                repository.save(
                        parser.toEntity(dto)));
    }

    private void doAssertsBdSchema(FlorDto dto){
        Assert.notNull(dto,"The given flor dto must not be null!");
        //BBDD schema for string columns has been defined with not null
        Assert.notNull(dto.getNomFlor(),"The given flor's name must not be null");
        Assert.notNull(dto.getPaisFlor(),"The given flor's country must not be null");
        Assert.isTrue(dto.getNomFlor().length()<=255,"The given flor's name length must not be > 255 characters");
        Assert.isTrue(dto.getPaisFlor().length()<=255,"The given flor's country length must not be > 255 characters");
        assertNoDataDuplication(dto);
    }

    /**
     * Asserts in BBD there's no other resource (with different id) with IDENTICAL name and country.
     * If fails DuplicateDataException is thrown.
     * Note: if exists but has the same ID, means that the flor provided is equals to the flor saved, so
     * save/override is idempotent.
     * @param dto FlorDto with the values to validate. If ID field is not null, exist the possibility to idempotent save.
     * @Trows DuplicateDataException
     */
    private void assertNoDataDuplication(FlorDto dto){
        Assert.notNull(dto,"The given flor dto must not be null!");

        repository.findByNomFlorAndPaisFlor(dto.getNomFlor(), dto.getPaisFlor())
                .ifPresent(entity -> {
                    if(!(entity.getPk_FlorID().equals(dto.getPk_FlorID()))){
                        throw new DuplicateDataException
                                ("Ja hi ha registrada una altra flor amb el mateix nom i en el mateix país. "+
                                        entity);
                    }
                });
    }

    public FlorDto replace(FlorDto dto){
        doAssertsBdSchema(dto);

        return parser.toDto(
                repository.findById(dto.getPk_FlorID())
                    .map(oldEntity -> repository.save( //if present -> replace
                            parser.updateEntity(oldEntity,dto)))
                    .orElseThrow(() -> //if not present -> not found exception
                            new FlorNotFoundException
                                ("Actualitzar dades de la flor cancel·lat. No existeix cap flor amb id: "
                                        +dto.getPk_FlorID())));
    }
}
