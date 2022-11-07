/*
    Responsibility:
    add links in response body when flor resource is requested.
    Logic implemented here (instead directly on the controller), to reduce coupling
    and follow/respect framework's design.

    This class uses WebMvcLinkBuilder and IanaLinkRelations, but there's no need of having attributes
    of theses clases, because only static methods and fields are used.
*/

package com.martinfluviapablo.s5t1n2.controllers;

import com.martinfluviapablo.s5t1n2.model.dto.FlorDto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.IanaLinkRelations; //standard names for different "types" of links
import org.springframework.util.Assert;

@Component
public class FlorModelAssembler implements RepresentationModelAssembler<FlorDto, EntityModel<FlorDto>> {

    @Override
    public EntityModel<FlorDto> toModel(FlorDto flor) {
        Assert.notNull(flor,"The given FlorDto must not be null!");
        Assert.isTrue(flor.getPk_FlorID() !=null && flor.getPk_FlorID()>0,"Flor's ID mut be > 0!");

        // FlorDto model + links to /getOne/{this flor id} and /getAll
        return EntityModel.of(flor,
                linkTo(methodOn(FlorController.class).getOne(flor.getPk_FlorID())).withSelfRel(),
                linkTo(methodOn(FlorController.class).getAll()).withRel(IanaLinkRelations.COLLECTION));
    }

    /*
    Default method toCollectionModel from interface don't add self relation link for all the collection
    in a global way (only in each member).
    Override it's not worthy, only want to extend it, but call super it's not possible (interface).
    So new method to extend functionality.
     */
    public CollectionModel<EntityModel<FlorDto>> toGlobalCollectionModel (Iterable<? extends FlorDto> flors){
        Assert.notNull(flors,"The given Iterable of FlorDto must not be null!"); //can be empty
        return toCollectionModel(flors)
                .add(linkTo(methodOn(FlorController.class).getAll()).withSelfRel());
    }

    /*
        inner class, only to define class for schema when @Schema(implementation= )
        due CollectionModel<EntityModel<FlorDto>>.class it's not valid
     */

    @Schema(name = "CollectionModel<EntityModel<FlorDto>>")
    public class FlorCollection extends CollectionModel<EntityModel<FlorDto>>{}

    @Schema(name = "EntityModel<FlorDto>")
    public class FlorModel extends EntityModel<FlorDto>{}
}
