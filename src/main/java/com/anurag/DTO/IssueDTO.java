package com.anurag.DTO;

import com.anurag.model.Project;
import com.anurag.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {

    private Long id;
    private String title;
    private String discription;
    private  String status;
    private Long projectID;
    private String priority;
    private LocalDate dueDate;

    private List<String> tags=new ArrayList<>();
    private Project project;
    private User assignee;

}
