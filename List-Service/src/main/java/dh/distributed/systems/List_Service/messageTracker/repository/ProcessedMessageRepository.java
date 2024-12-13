package dh.distributed.systems.List_Service.messageTracker.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dh.distributed.systems.List_Service.messageTracker.model.ProcessedMessage;

public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, Integer> {
    Optional<ProcessedMessage> findByMessageId(UUID messageId);
}
