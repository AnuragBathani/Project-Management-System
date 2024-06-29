package com.anurag.service;

import com.anurag.model.Chat;
import com.anurag.model.Project;
import com.anurag.model.User;
import com.anurag.repository.ProjectRepoository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepoository projectRepoository;

    @Autowired
    private UserService userService;

    @Autowired
    ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {

        Project createdpeoject=new Project();

        createdpeoject.setOwner(user);
        createdpeoject.setTags(project.getTags());
        createdpeoject.setName(project.getName());
        createdpeoject.setCategory(project.getCategory());
        createdpeoject.setDiscription(project.getDiscription());
        createdpeoject.getTeam().add(user);

        Project savedproject= projectRepoository.save(createdpeoject);

        Chat chat=new Chat();
        chat.setProject(savedproject);

        Chat projectchat=chatService.createchat(chat);

        savedproject.setChat(projectchat);



        return savedproject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String catogary, String tags) throws Exception {

        List<Project> projects=projectRepoository.findByTeamContainingOrOwner(user,user);

        if(catogary != null){
            projects=projects.stream().filter(project -> project.getCategory().equals(catogary))
                    .collect(Collectors.toList());
        }

        if(tags != null){
            projects=projects.stream().filter(project -> project.getTags().contains(tags))
                    .collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {

        Optional<Project> optionalProject=projectRepoository.findById(projectId);

        if(optionalProject.isEmpty()){
            throw new Exception("project not found");
        }

        return optionalProject.get();
    }

    @Override
    public void deleateProject(Long ProjectId, Long userid) throws Exception {

        getProjectById(ProjectId);

        projectRepoository.deleteById(ProjectId);

    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {

        Project project=getProjectById(id);

        project.setName(updatedProject.getName());
        project.setDiscription(updatedProject.getDiscription());
        project.setTags(updatedProject.getTags());


        return projectRepoository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project=getProjectById(projectId);

        User user =userService.findUserById(userId);

        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }

        projectRepoository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {

        Project project=getProjectById(projectId);

        User user =userService.findUserById(userId);

        if(project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }


        projectRepoository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {

        Project project=getProjectById(projectId);

        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {



        return projectRepoository.findByNameContainingAndTeamContains(keyword, user);

    }
}
