package com.martinfluviapablo.s5t1n2.controllers;

import com.martinfluviapablo.s5t1n2.model.services.FlorCrudService;
import com.martinfluviapablo.s5t1n2.model.dto.*;
import com.martinfluviapablo.s5t1n2.controllers.operationAnnotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Flor CRUD endpoints.", description = "Available CRUD operations for Flor resource.")
@RestController
@RequestMapping(value = "/flors", produces = "application/json")
@Validated
public class FlorController {

    private final FlorCrudService florService;

    private final FlorModelAssembler florAssembler;

    @Autowired
    public FlorController(FlorCrudService florService, FlorModelAssembler florAssembler) {
        this.florService = florService;
        this.florAssembler = florAssembler;
    }

    @GetMapping("/getOne/{id}")
    @GetOneFlorOperation //for documentation
    public ResponseEntity<EntityModel<FlorDto>> getOne(@PathVariable @ValidId Integer id) {
        EntityModel<FlorDto> florModel = florAssembler.toModel(florService.getOne(id));
        return ResponseEntity.ok(florModel);
    }

    @GetMapping("/getAll")
    @GetAllFlorOperation //for documentation
    public ResponseEntity<CollectionModel<EntityModel<FlorDto>>> getAll() {
        CollectionModel<EntityModel<FlorDto>> florCollection =
                    florAssembler.toGlobalCollectionModel(florService.getAll());
        return ResponseEntity.ok(florCollection);
    }

    @DeleteMapping("/delete/{id}")
    @DeleteOneFlorOperation //for documentation
    public ResponseEntity<Void> deleteOne(@PathVariable @ValidId Integer id){
        florService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add")
    @PostOneFlorOperation //for documentation
    public ResponseEntity<EntityModel<FlorDto>> addOne(
            @RequestBody @Validated(AddGroup.class) FlorDto dto){
        EntityModel<FlorDto> florModel= florAssembler.toModel(florService.addOne(dto));
        return ResponseEntity
                .created(florAssembler.getSelfUri(florModel))
                .body(florModel);
    }

    @PutMapping("/update")
    @PutOneFlorOperation //for documentation
    public ResponseEntity<EntityModel<FlorDto>> replaceOne(
            @RequestBody @Validated(ReplaceGroup.class) FlorDto dto){
        EntityModel<FlorDto> florModel= florAssembler.toModel(florService.replaceOne(dto));
        return ResponseEntity.ok(florModel);
    }
}
