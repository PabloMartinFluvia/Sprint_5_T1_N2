package com.martinfluviapablo.s5t1n2.controllers;

import com.martinfluviapablo.s5t1n2.model.dto.FlorDto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.util.Assert;
import java.net.URI;

@Component
public class FlorModelAssembler implements RepresentationModelAssembler<FlorDto, EntityModel<FlorDto>> {

    private FlorLinks linksGenerator;

    @Autowired
    public FlorModelAssembler (FlorLinks linksGenerator){
        this.linksGenerator = linksGenerator;
    }

    @Override
    public EntityModel<FlorDto> toModel(FlorDto flor) {
        Assert.notNull(flor,"The given FlorDto must not be null!");
        Assert.isTrue(flor.getPk_FlorID() !=null && flor.getPk_FlorID()>0,"Flor's ID mut be > 0!");

        return EntityModel.of(flor,
                linksGenerator.linkToGetOneFlorSelfRelation(flor.getPk_FlorID()),
                linksGenerator.linkToGetAllFlorsCollectionRelation());
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
                .add(linksGenerator.linkToGetAllFlorsSelfRelation());
    }

    public URI getSelfUri (EntityModel<FlorDto> florModel){
        Assert.isTrue(florModel.hasLink(linksGenerator.getSelfRelation()),
                "Any FlorModel must have link with SELF relation.");
        return florModel.getRequiredLink(linksGenerator.getSelfRelation()).toUri();
    }


    /*
        inner classes, only to define class for schema when @Schema(implementation= )
        due generic problems
     */

    @Schema(name = "EntityModel<FlorDto>")
    @Relation(collectionRelation = "florsList") //to change default "florModelList" in embebed array (documentation)
    public class FlorModel extends EntityModel<FlorDto>{}

    @Schema(name = "CollectionModel<EntityModel<FlorDto>>")
    public class FlorsCollection extends CollectionModel<FlorModel>{}
}
