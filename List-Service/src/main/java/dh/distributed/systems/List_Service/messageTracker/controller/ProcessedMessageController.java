package dh.distributed.systems.List_Service.messageTracker.controller;

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

import dh.distributed.systems.List_Service.messageTracker.dto.ProcessedMessageResponse;
import dh.distributed.systems.List_Service.messageTracker.manager.ProcessedMessageManager;
import dh.distributed.systems.List_Service.messageTracker.model.ProcessedMessage;
import dh.distributed.systems.List_Service.messageTracker.transformer.ProcessedMessageTransformer;
import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/processed-messages")
public class ProcessedMessageController {
    
    private final ProcessedMessageTransformer transformer;
    private final ProcessedMessageManager manager;

    @GetMapping()
    public List<ProcessedMessageResponse> get() {
        return this.transformer.getAllProcessedMessages();
    }

    @GetMapping("/{id}")
    public ProcessedMessageResponse getOne(@PathVariable Integer id) {
        return this.transformer.getProcessedMessage(id);
    }

    @PostMapping()
    public ResponseEntity<ProcessedMessageResponse> post(@RequestBody ProcessedMessage message) {
        if (this.manager.isValid(message)) {
            ProcessedMessageResponse response = this.transformer.getProcessedMessage(this.manager.creatProcessedMessage(message).getId());
            return ResponseEntity
                    .created(URI.create(response.getLinks().get("self")))
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody ProcessedMessage message, @PathVariable Integer id) {
        if (this.manager.isValid(message)) {
            ProcessedMessageResponse response = this.transformer.getProcessedMessage(this.manager.updateProcessedMessage(message, id).getId());
            return ResponseEntity
                    .created(URI.create(response.getLinks().get("self")))
                    .body(response);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProcessedMessageResponse> delete(@PathVariable Integer id) {
        ProcessedMessageResponse response = this.transformer.getProcessedMessage(id);
        this.manager.deleteProcessedMessage(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAll() {
        this.manager.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
