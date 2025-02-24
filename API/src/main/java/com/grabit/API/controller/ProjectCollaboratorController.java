// contain classes that handle HTTP requests and responses

package com.grabit.API.controller;
import com.grabit.API.model.ProjectCollaboratorModel;
import com.grabit.API.service.ProjectCollaboratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/project-collaborators")
public class ProjectCollaboratorController {
    // i am getting endpoint to the project collaborator
    //1. get all project collaborators
    //2. get project collaborator by id
    //3. add project collaborator
    //4. update project collaborator
    //5. delete project collaborator
 
    @Autowired
    private ProjectCollaboratorService projectCollaboratorService;

    @PostMapping
    public ProjectCollaboratorModel addProjectCollaborator(@RequestBody ProjectCollaboratorModel projectCollaborator){
        return projectCollaboratorService.addProjectCollaborator(projectCollaborator);
    }

    @GetMapping("/{id}")
    public ProjectCollaboratorModel getProjectCollaboratorByID(@PathVariable Long id){
        return projectCollaboratorService.getProjectCollaboratorByID(id);
    }

    @PutMapping("/{id}")
    public ProjectCollaboratorModel updateProjectCollaborator(@PathVariable Long id, @RequestBody ProjectCollaboratorModel projectCollaborator){
        return projectCollaboratorService.updateProjectCollaborator(id, projectCollaborator);
    }

    @DeleteMapping("/{id}")
    public void deactivateProjectCollaborator(@PathVariable Long id){
        projectCollaboratorService.deactivateProjectCollaborator(id);
    }
    
}
