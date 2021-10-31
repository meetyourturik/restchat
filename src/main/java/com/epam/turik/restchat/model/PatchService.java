package com.epam.turik.restchat.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

@Slf4j
@Service
public class PatchService {
    private final ObjectMapper objectMapper;

    public PatchService(ObjectMapper objectMapper) {
        objectMapper.setTimeZone(TimeZone.getDefault());
        this.objectMapper = objectMapper;
    }

    public <T> T applyPatch(T object, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        // https://www.baeldung.com/spring-rest-json-patch
        JsonNode patched = patch.apply(objectMapper.convertValue(object, JsonNode.class));
        return (T) objectMapper.treeToValue(patched, object.getClass());
    }
}
