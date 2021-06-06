package com.epam.turik.restchat.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.stereotype.Service;

@Service
public class PatchService {
    private final ObjectMapper objectMapper;

    public PatchService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T applyPatch(T object, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        // https://www.baeldung.com/spring-rest-json-patch
        JsonNode patched = patch.apply(objectMapper.convertValue(object, JsonNode.class));
        return (T) objectMapper.treeToValue(patched, object.getClass());
    }
}
