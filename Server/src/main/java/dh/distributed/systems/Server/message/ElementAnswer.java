package dh.distributed.systems.Server.message;

import java.util.List;

public record ElementAnswer(Integer elementID, ListElementResponse element, List<ListElementResponse> elements, Boolean result) {}
