package dh.distributed.systems.List_Service.listelement.controller;

import java.net.URI;
import java.util.List;
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

import dh.distributed.systems.List_Service.listelement.dto.ListElementResponse;
import dh.distributed.systems.List_Service.listelement.manager.ListElementManager;
import dh.distributed.systems.List_Service.listelement.model.ListElement;
import dh.distributed.systems.List_Service.listelement.transformer.ListElementTransformer;
import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/list-elements")
public class ListElementController {

    private final ListElementTransformer transformer;
    private final ListElementManager manager;

    @GetMapping()
    public List<ListElementResponse> get() {
        return this.transformer.getAllListElements();
    }

    @GetMapping("/user/{userID}")
    public List<ListElementResponse> getAllListElementsByUserId(
            @PathVariable(value = "userID") Integer userID) {
        return this.transformer.getAllElementsByUserID(userID);
    }

    @GetMapping("/lists/{listID}")
    public List<ListElementResponse> getAllListElementsByListId(
            @PathVariable(value = "listID") Integer listID) {
        return this.transformer.getAllElementsByListID(listID);
    }

    @GetMapping("/{id}")
    public ListElementResponse getOne(@PathVariable(value = "id") Integer id) {
        return this.transformer.getListElement(id);
    }

    @PostMapping("/user/{userID}/list/{listID}")
    public ResponseEntity<ListElementResponse> post(
            @PathVariable(value = "userID") Integer userID,
            @PathVariable(value = "listID") Integer listID,
            @RequestBody ListElement listElement) {
        if (this.manager.isValid(listElement)) {
            ListElementResponse response = this.transformer.getListElement(this.manager.createElement(userID, listID, listElement).getId());
            return ResponseEntity
                    .created(URI.create(response.getLinks().get("self")))
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody ListElement listElement, @PathVariable(value = "id") Integer id) {
        if (this.manager.isValid(listElement)) {
            ListElementResponse response = this.transformer.getListElement(this.manager.updateElement(listElement, id).getId());
            return ResponseEntity
                    .created(URI.create(response.getLinks().get("self")))
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ListElementResponse> delete(@PathVariable(value = "id") Integer id) {
        ListElementResponse response = this.transformer.getListElement(id);
        this.manager.deleteElement(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{userID}")
    public List<ListElementResponse> deleteAllListElementsOfUser(
            @PathVariable(value = "userID") Integer userID) {
        List<ListElementResponse> responses = this.transformer.getAllElementsByUserID(userID);
        this.manager.deleteAllByUser(userID);
        return responses;
    }

    @DeleteMapping("/list/{listID}")
    public List<ListElementResponse> deleteAllListElementsOfList(
            @PathVariable(value = "listID") Integer listID) {
        List<ListElementResponse> responses = this.transformer.getAllElementsByListID(listID);
        this.manager.deleteAllByUser(listID);
        return responses;
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAll() {
        this.manager.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
