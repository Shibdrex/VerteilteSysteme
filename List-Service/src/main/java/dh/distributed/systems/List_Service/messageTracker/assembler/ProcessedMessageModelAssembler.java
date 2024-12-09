package dh.distributed.systems.List_Service.messageTracker.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import dh.distributed.systems.List_Service.messageTracker.controller.ProcessedMessageController;
import dh.distributed.systems.List_Service.messageTracker.model.ProcessedMessage;

@Component
public class ProcessedMessageModelAssembler implements RepresentationModelAssembler<ProcessedMessage, EntityModel<ProcessedMessage>> {
    
    @Override
    public @NonNull EntityModel<ProcessedMessage> toModel(@NonNull ProcessedMessage message) {

        return EntityModel.of(message,
        linkTo(methodOn(ProcessedMessageController.class).getOne(message.getId())).withSelfRel(),
        linkTo(methodOn(ProcessedMessageController.class).get()).withRel("messages"));
    }
}
