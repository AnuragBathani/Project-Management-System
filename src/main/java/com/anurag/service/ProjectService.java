package com.anurag.service;

import com.anurag.model.Chat;
import com.anurag.model.Project;
import com.anurag.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {

    Project createProject(Project project,User user) throws Exception;

    List<Project> getProjectByTeam(User user, String catogary, String tags) throws Exception;

    Project getProjectById(Long projectId) throws  Exception;

    void deleateProject(Long ProjectId,Long userid) throws Exception;

    Project updateProject(Project updatedProject,Long id) throws Exception;

    void addUserToProject(Long projectId,Long userId) throws  Exception;


    void removeUserFromProject(Long projectId,Long userId) throws  Exception;

    Chat getChatByProjectId(Long projectId) throws Exception;


    List<Project> searchProjects(String keyword ,User user) throws Exception;



}
