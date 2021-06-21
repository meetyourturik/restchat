package com.epam.turik.restchat.rest.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDTO {
    String text;
    String code;
}
