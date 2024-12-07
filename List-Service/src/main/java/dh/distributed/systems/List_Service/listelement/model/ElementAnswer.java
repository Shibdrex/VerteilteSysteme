package dh.distributed.systems.List_Service.listelement.model;

import java.util.List;

import dh.distributed.systems.List_Service.listelement.dto.ListElementResponse;

public record ElementAnswer(Integer elementID, ListElementResponse element, List<ListElementResponse> elements, Boolean result) {}
