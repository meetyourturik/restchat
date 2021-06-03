package com.epam.turik.restchat.rest.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatchString {
    private String op;
    private String path;
    private String value;
}
