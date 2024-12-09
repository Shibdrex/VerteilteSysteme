package dh.distributed.systems.List_Service.listelement.model;

import java.util.List;

import dh.distributed.systems.List_Service.listelement.dto.ListElementResponse;

/**
 * Record that represents a dto of a message to be send to server
 */
public record ElementAnswer(Integer elementID, ListElementResponse element, List<ListElementResponse> elements, Boolean result) {}
