package com.anurag.request;


import lombok.Data;

@Data
public class CreateMassageRequest {

    private Long senderId;
    private Long projectId;

    private String content;

}
