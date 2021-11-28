package com.epam.turik.restchat.rest.exceptions;

import lombok.Data;

@Data
public class Violation {
    private String code;
    private String field;
    private String detail;
}
