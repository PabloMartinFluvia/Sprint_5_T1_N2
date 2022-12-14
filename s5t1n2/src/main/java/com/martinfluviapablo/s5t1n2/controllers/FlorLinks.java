package com.martinfluviapablo.s5t1n2.controllers;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FlorLinks {
    private final Class<FlorController> CONTROLLER = FlorController.class;

    public Link linkToGetOneFlorSelfRelation(Integer id){
        return linkTo(methodOn(CONTROLLER).getOne(id)).withSelfRel();
    }

    public Link linkToGetAllFlorsSelfRelation(){
        return linkTo(methodOn(CONTROLLER).getAll()).withSelfRel();
    }

    public Link linkToGetAllFlorsCollectionRelation(){
        return linkTo(methodOn(CONTROLLER).getAll()).withRel(IanaLinkRelations.COLLECTION);
    }

    public LinkRelation getSelfRelation() {
        return IanaLinkRelations.SELF;
    }
}
