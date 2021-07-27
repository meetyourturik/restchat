package com.epam.turik.restchat.rest.objects;

import com.epam.turik.restchat.rest.exceptions.Violation;
import lombok.Data;

import java.util.List;

@Data
public class ProblemDTO {
    String          type;
    String          title;
    String          detail;
    List<Violation> violations;
}
