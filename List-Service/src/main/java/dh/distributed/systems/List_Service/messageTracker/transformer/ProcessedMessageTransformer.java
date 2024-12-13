package dh.distributed.systems.List_Service.messageTracker.transformer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import dh.distributed.systems.List_Service.messageTracker.assembler.ProcessedMessageModelAssembler;
import dh.distributed.systems.List_Service.messageTracker.dto.ProcessedMessageResponse;
import dh.distributed.systems.List_Service.messageTracker.manager.ProcessedMessageManager;
import dh.distributed.systems.List_Service.messageTracker.model.ProcessedMessage;
import lombok.AllArgsConstructor;

/**
 * Handles converting {@link ProcessedMessage} objects to
 * {@link ProcessedMessageResponse} objects, which contain HATEOAS-links.
 */
@AllArgsConstructor
@Service
public class ProcessedMessageTransformer {

    private final ProcessedMessageManager manager;
    private final ProcessedMessageModelAssembler assembler;

    private ProcessedMessageResponse transformToResponse(ProcessedMessage message) {
        EntityModel<ProcessedMessage> model = this.assembler.toModel(message);
        return new ProcessedMessageResponse(message, model);
    }

    public ProcessedMessageResponse getProcessedMessage(Integer id) {
        ProcessedMessage message = this.manager.getProcessedMessage(id);
        EntityModel<ProcessedMessage> model = this.assembler.toModel(message);
        return new ProcessedMessageResponse(message, model);
    }

    public List<ProcessedMessageResponse> getAllProcessedMessages() {
        return this.manager.getAllProcessedMessages().stream()
                .map(this::transformToResponse)
                .collect(Collectors.toList());
    }
}
