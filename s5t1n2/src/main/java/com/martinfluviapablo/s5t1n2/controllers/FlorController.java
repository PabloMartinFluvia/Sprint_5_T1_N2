/*
 * @Validated En l'àmbit de classe per a fer que s'avaluïn les restriccions imposades als paths variables
 * (i si fos el cas també en els request params).
 *      Validació segons estàndard (grup per defecte).
 *
 * @Validated També en l'àmbit d'arguments @RequestBody grups específics (en POST i PUT),
 * ja que cadascun requereix l'aplicació de la validació sobre unes restriccions específiques.
 * Aquestes validacions modifiquen el grup imposat en l'àmbit de la classe.
 */



package com.martinfluviapablo.s5t1n2.controllers;

import com.martinfluviapablo.s5t1n2.model.services.FlorService;
import com.martinfluviapablo.s5t1n2.model.dto.FlorDto;
import com.martinfluviapablo.s5t1n2.model.dto.ValidId;
import com.martinfluviapablo.s5t1n2.model.dto.AddGroup;
import com.martinfluviapablo.s5t1n2.model.dto.ReplaceGroup;

import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flors") // PLURAL resource path
@Validated // to validate constraints in request params and path variable
public class FlorController {
    //Assert method's pre-conditions implemented through validations

    private final FlorService florService;

    private final FlorModelAssembler florAssembler; //for mapping the dto to EntityModel

    @Autowired
    public FlorController(FlorService florService, FlorModelAssembler florAssembler) {
        this.florService = florService;
        this.florAssembler = florAssembler;
    }

    /**
     * Find and return all the flor's info in BBDD (if empty only link to collection self relation).
     * @return All flor's (dto) info + ok code + links to help RESTful API usage.
     */
    @GetMapping("/getAll")
    public ResponseEntity<CollectionModel<EntityModel<FlorDto>>> getAll() {
        return ResponseEntity.ok(
                florAssembler.toGlobalCollectionModel(
                        florService.getAll()));

    }

    /**
     * Find and returns the flor's info associated to the provided PK.
     * If it's not found or the PK it's not valid, the response is an error.
     *
     * @param id Must be int > 0 to make sense (it's a PK). Otherwise, returns an error.
     * @return Flor's (dto) info + ok code + links to help RESTful API usage. Or ERROR response.
     */
    @GetMapping("/getOne/{id}")
    public ResponseEntity<EntityModel<FlorDto>> geOne(@PathVariable @ValidId Integer id) {
        return ResponseEntity.ok(
                florAssembler.toModel(
                        florService.getOne(id)));
    }

    /**
     * Removes from BBDD the flor's data associated to the provided PK.
     * If it's not found, nothing happens.
     * @param id Must be int > 0 to make sense (it's a PK). Otherwise, returns an error.
     * @return HTTP status no content without body, or ERROR response.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable @ValidId Integer id){
        florService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Saves the provided resource if FlorDto is valid (see dto's constraints for Default and AddGroup) and
     * don't imply data duplication (2 resources with same values and different ID).
     * Otherwise, returns an error.
     * @param dto FlorDto provided by client
     * @return Flor's (dto) info + created code + self link in header + links to help RESTful API usage.
     * Or ERROR response.
     */
    @PostMapping("/add")
    public ResponseEntity<EntityModel<FlorDto>> addOne(@RequestBody @Validated(AddGroup.class) FlorDto dto){
        EntityModel<FlorDto> florModel= florAssembler.toModel(
                florService.addOne(dto));
        return ResponseEntity
                .created(florModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(florModel);
    }

    /**
     * Replaces the resource provided if the flor's ID is found and FlorDto is valid (see dto's constraints
     * for Default and ReplaceGroup) and  don't imply data duplication (2 resources with same values
     * and different ID).
     * @param dto FlorDto provided by client
     * @return Flor's (dto) info + ok code+ links to help RESTful API usage. Or ERROR response.
     */
    @PutMapping("/update")
    public ResponseEntity<EntityModel<FlorDto>> replace(@RequestBody @Validated(ReplaceGroup.class) FlorDto dto){
        EntityModel<FlorDto> florModel= florAssembler.toModel(
                florService.replace(dto));
        return ResponseEntity.ok(florModel);
    }
}
