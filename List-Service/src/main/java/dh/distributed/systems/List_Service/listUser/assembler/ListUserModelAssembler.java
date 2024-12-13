package dh.distributed.systems.List_Service.listUser.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import dh.distributed.systems.List_Service.listUser.controller.ListUserController;
import dh.distributed.systems.List_Service.listUser.model.ListUser;

@Component
public class ListUserModelAssembler implements RepresentationModelAssembler<ListUser, EntityModel<ListUser>> {

    @Override
    public @NonNull EntityModel<ListUser> toModel(@NonNull ListUser user) {

        return EntityModel.of(user,
        linkTo(methodOn(ListUserController.class).getOne(user.getId())).withSelfRel(),
        linkTo(methodOn(ListUserController.class).get()).withRel("users"));
    }
}
