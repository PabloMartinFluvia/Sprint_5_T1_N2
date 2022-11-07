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

import com.martinfluviapablo.s5t1n2.controllers.operationAnnotations.DeleteOneFlorOperation;
import com.martinfluviapablo.s5t1n2.controllers.operationAnnotations.GetAllFlorOperation;
import com.martinfluviapablo.s5t1n2.controllers.operationAnnotations.GetOneFlorOperation;
import com.martinfluviapablo.s5t1n2.controllers.operationAnnotations.PostOneFlorOperation;
import com.martinfluviapablo.s5t1n2.model.services.FlorService;
import com.martinfluviapablo.s5t1n2.model.dto.FlorDto;
import com.martinfluviapablo.s5t1n2.model.dto.ValidId;
import com.martinfluviapablo.s5t1n2.model.dto.AddGroup;
import com.martinfluviapablo.s5t1n2.model.dto.ReplaceGroup;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@Tag(name = "Flor CRUD operations.", description = "Requests allowed in application when working with flor resource.")
@RestController
@RequestMapping(value = "/flors", produces = "application/json") // PLURAL resource path
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

    @GetMapping("/getAll")
    @GetAllFlorOperation
    public ResponseEntity<CollectionModel<EntityModel<FlorDto>>> getAll() {
        return ResponseEntity.ok(
                florAssembler.toGlobalCollectionModel(
                        florService.getAll()));
    }

    @GetMapping("/getOne/{id}")
    @GetOneFlorOperation
    public ResponseEntity<EntityModel<FlorDto>> getOne(@PathVariable @ValidId Integer id) {
        return ResponseEntity.ok(
                florAssembler.toModel(
                        florService.getOne(id)));
    }

    @DeleteMapping("/delete/{id}")
    @DeleteOneFlorOperation
    public ResponseEntity<Void> deleteOne(@PathVariable @ValidId Integer id){
        florService.deleteOne(id);
        return ResponseEntity.noContent().build(); // T body = null in HttpEntity
        //No content -> Body type in response: Void.class (better than Object or any other class
        //for documenting the API)
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
    @PostOneFlorOperation
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
    public ResponseEntity<EntityModel<FlorDto>> replaceOne(@RequestBody @Validated(ReplaceGroup.class) FlorDto dto){
        EntityModel<FlorDto> florModel= florAssembler.toModel(
                florService.replace(dto));
        return ResponseEntity.ok(florModel);
    }
}
