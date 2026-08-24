package spring.ai.tutorial.dto;

import java.util.List;
import java.util.Map;

public record QueryRoute(List<Action> actions) {

    public record Action(String type, Map<String, Object> parameters) {}
}
