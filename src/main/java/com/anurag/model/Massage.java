package com.anurag.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity

public class Massage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


   private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    private Chat chat;

    @ManyToOne
    private User sender;



}
