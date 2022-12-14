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
public class LocalFlorCrudService implements FlorCrudService{

    private final FlorRepository repository;

    private final FlorParser parser;

    @Autowired
    public LocalFlorCrudService(FlorRepository repository, FlorParser parser) {
        this.repository = repository;
        this.parser = parser;
    }

    @Override
    public List<FlorDto> getAll(){
        return repository.findAll().stream()
                .map(parser::toDto).toList();
    }

    @Override
    public FlorDto getOne(Integer id){
        Assert.notNull(id,"The given id must not be null!");

        return repository.findById(id)
               .map(parser::toDto)
               .orElseThrow(() -> new FlorNotFoundException(id));
    }

    @Override
    public void deleteOne(Integer id){
        Assert.notNull(id,"The given id must not be null!");

        repository.deleteById(id);
    }

    @Override
    public FlorDto addOne(FlorDto dto){
        doAssertsBdSchema(dto);
        assertNoDataDuplication(dto);
        return parser.toDto(
                repository.save(
                        parser.toEntity(dto)));
    }

    //to assert data fits BD requirements (constraints validated on controller)
    private void doAssertsBdSchema(FlorDto dto){
        Assert.notNull(dto,"The given flor dto must not be null!");
        //BBDD schema for string columns has been defined with not null
        Assert.notNull(dto.getNomFlor(),"The given flor's name must not be null");
        Assert.notNull(dto.getPaisFlor(),"The given flor's country must not be null");
        Assert.isTrue(dto.getNomFlor().length()<=255,"The given flor's name length must not be > 255 characters");
        Assert.isTrue(dto.getPaisFlor().length()<=255,"The given flor's country length must not be > 255 characters");
    }


    private void assertNoDataDuplication(FlorDto dto){
        Assert.notNull(dto,"The given flor dto must not be null!");

        repository.findByNomFlorAndPaisFlor(dto.getNomFlor(), dto.getPaisFlor())
                .ifPresent(entity -> {
                    //no exception if dto's id = entity found id (just replace without changes)
                    if(!(entity.getPk_FlorID().equals(dto.getPk_FlorID()))){
                        throw new DuplicateDataException(entity.getPk_FlorID());
                    }
                });
    }

    @Override
    public FlorDto replaceOne(FlorDto dto){
        doAssertsBdSchema(dto);
        assertNoDataDuplication(dto);
        return parser.toDto(
                repository.findById(dto.getPk_FlorID())
                    .map(oldEntity -> repository.save( //if present -> replace
                            parser.updateEntity(oldEntity,dto)))
                    .orElseThrow(() -> //if not present -> not found exception
                            new FlorNotFoundException(dto.getPk_FlorID())));
    }
}
