package com.epam.turik.restchat.rest.objects;

import lombok.Data;

@Data
public class OperationDTO {
    public enum OperationType {
        REPLACE, REMOVE
    }

    private OperationType operationType;
    private String path;
    private String value;
}