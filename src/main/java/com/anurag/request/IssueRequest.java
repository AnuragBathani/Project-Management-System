package com.anurag.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class IssueRequest {

    private String title;
    private String discription;
    private  String status;
    private Long projectID;
    private String priority;
    private LocalDate dueDate;
}
