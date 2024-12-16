package dh.distributed.systems.List_Service.listelement.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import dh.distributed.systems.List_Service.listelement.controller.ListElementController;
import dh.distributed.systems.List_Service.listelement.model.ListElement;

@Component
public class ListElementModelAssembler implements RepresentationModelAssembler<ListElement, EntityModel<ListElement>> {

    @Override
    public @NonNull EntityModel<ListElement> toModel(@NonNull ListElement element) {

        return EntityModel.of(element,
        linkTo(methodOn(ListElementController.class).getOne(element.getId())).withSelfRel(),
        linkTo(methodOn(ListElementController.class).get()).withRel("elements"));
    }
}
