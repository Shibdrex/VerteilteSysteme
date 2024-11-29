package dh.distributed.systems.List_Service.listelement.controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dh.distributed.systems.List_Service.listelement.assembler.ListElementModelAssembler;
import dh.distributed.systems.List_Service.listelement.manager.ListElementManager;
import dh.distributed.systems.List_Service.listelement.model.ListElement;
import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/list-elements")
public class ListElementController {

    private final ListElementManager manager;

    private final ListElementModelAssembler assembler;


    @GetMapping()
    public CollectionModel<EntityModel<ListElement>> get() {
        List<EntityModel<ListElement>> listElements = this.manager.getAllElements().stream()
                .map(this.assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(listElements, linkTo(methodOn(ListElementController.class).get()).withSelfRel());
    }

    @GetMapping("/users/{userID}/elements")
    public CollectionModel<EntityModel<ListElement>> getAllListElementsByUserId(
            @PathVariable(value = "userID") Integer userID) {
        List<EntityModel<ListElement>> listElements = this.manager.getAllElementsByUserID(userID).stream()
                .map(this.assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(listElements, linkTo(methodOn(ListElementController.class).get()).withSelfRel());
    }

    @GetMapping("/lists/{listID}/elements")
    public CollectionModel<EntityModel<ListElement>> getAllListElementsByListId(
            @PathVariable(value = "listID") Integer listID) {
        List<EntityModel<ListElement>> listElements = this.manager.getAllElementsByListID(listID).stream()
                .map(this.assembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(listElements, linkTo(methodOn(ListElementController.class).get()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ListElement> getOne(@PathVariable(value = "id") Integer id) {
        return this.assembler.toModel(this.manager.getElement(id));
    }

    @PostMapping("/users/{userID}/lists/{listID}")
    public ResponseEntity<EntityModel<ListElement>> post(
            @PathVariable(value = "userID") Integer userID,
            @PathVariable(value = "listID") Integer listID,
            @RequestBody ListElement listElement) {
        if (this.manager.isValid(listElement)) {
            EntityModel<ListElement> model = this.assembler.toModel(this.manager.createElement(userID, listID, listElement));
            return ResponseEntity
                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(model);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody ListElement listElement, @PathVariable Integer id) {
        if (this.manager.isValid(listElement)) {
            EntityModel<ListElement> model = this.assembler.toModel(this.manager.updateElement(listElement, id));
            return ResponseEntity
                    .created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(model);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ListElement> delete(@PathVariable Integer id) {
        if (id >= 0) {
            ListElement deletedElem = this.manager.getElement(id);
            this.manager.deleteElement(id);
            return ResponseEntity.ok(deletedElem);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping("/users/{userID}")
    public CollectionModel<EntityModel<ListElement>> deleteAllListElementsOfUser(@PathVariable(value = "userID") Integer userID) {
        if (userID >= 0) {
            List<EntityModel<ListElement>> listElements = this.manager.getAllElementsByUserID(userID).stream()
                    .map(this.assembler::toModel)
                    .collect(Collectors.toList());
            this.manager.deleteAllByUser(userID);
            return CollectionModel.of(listElements, linkTo(methodOn(ListElementController.class).get()).withSelfRel());
        }
        return CollectionModel.empty();
    }

    @DeleteMapping("/lists/{listID}")
    public CollectionModel<EntityModel<ListElement>> deleteAllListElementsOfList(@PathVariable(value = "listID") Integer listID) {
        if (listID >= 0) {
            List<EntityModel<ListElement>> listElements = this.manager.getAllElementsByListID(listID).stream()
                    .map(this.assembler::toModel)
                    .collect(Collectors.toList());
            this.manager.deleteAllByUser(listID);
            return CollectionModel.of(listElements, linkTo(methodOn(ListElementController.class).get()).withSelfRel());
        }
        return CollectionModel.empty();
    }
}
