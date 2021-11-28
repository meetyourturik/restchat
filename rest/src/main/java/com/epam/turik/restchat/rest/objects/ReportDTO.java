package com.epam.turik.restchat.rest.objects;

import lombok.Data;

@Data
public class ReportDTO {
    private Long reporter_id;
    private Long reported_id;
    private String reason;
}
