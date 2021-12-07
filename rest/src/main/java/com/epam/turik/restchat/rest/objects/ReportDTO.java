package com.epam.turik.restchat.rest.objects;

import lombok.Data;

@Data
public class ReportDTO {
    private Long reporterId;
    private Long reportedId;
    private String reason;
}
