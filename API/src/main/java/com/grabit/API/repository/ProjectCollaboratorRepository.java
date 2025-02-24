// contain interfaces that extend Spring Data JPA's
// interfaces provide CRUD operations for your entities

package com.grabit.API.repository;

import com.grabit.API.model.Project;
import com.grabit.API.model.ProjectCollaboratorModel;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCollaboratorRepository extends JpaRepository<ProjectCollaboratorModel, Integer> {
    // Additional query methods should be defined here
// --------------------------------------------------------

    // // find the collaborators by the ProjectID and isActive
    List<ProjectCollaboratorModel> findByprojectCollaboratorID(Long ProjectCollaboratorID);

    // //find all the active collaborators and order by joinedAt
    List<ProjectCollaboratorModel> findByIsActiveOrderByJoinedAtDesc(boolean isActive);

    // //find all the active collaborators
    List<ProjectCollaboratorModel> findByIsActive(boolean isActive);


    // Get the number of collaborators for a specific project
    long countByProject_ProjectID(int projectID);

    // Get the number of active collaborators for a specific project
    long countByProject_ProjectIDAndIsActive(int projectID, boolean isActive);

    // Get the number of collaborators for each project
    @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc "  + "JOIN pc.project p GROUP BY p.projectID")
    List<Object[]> countCollaboratorsByProject();

    // Get the number of active collaborators for each project
    @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc " + "JOIN pc.project p WHERE pc.isActive = true GROUP BY p.projectID")
    List<Object[]> countActiveCollaboratorsByProject();

    // Get all collaborators for a specific project
    List<ProjectCollaboratorModel> findByProject_ProjectID(int projectID);

    // Get all active collaborators for a specific project
    List<ProjectCollaboratorModel> findByProject_ProjectIDAndIsActive(int projectID, boolean isActive);

    // Get the number of collaborators joined after a specific date for a project
    long countByProject_ProjectIDAndJoinedAtAfter(int projectID, LocalDateTime date);

    // Get all projects with their collaborator count
    @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc " + "JOIN pc.project p GROUP BY p.projectID")
    List<Object[]> countCollaboratorsByAllProjects();

    //get the first collaborator to join a project
    @Query ("SELECT pc FROM ProjectCollaboratorModel pc WHERE pc.project.projectID = :projectID ORDER BY pc.joinedAt ASC")
    ProjectCollaboratorModel findFirstCollaboratorForProject(int projectID);


    // Get projects with no collaborators
    @Query("SELECT p FROM Project p WHERE NOT EXISTS (SELECT 1 FROM ProjectCollaboratorModel pc WHERE pc.project.projectID = p.projectID)")
    List<Project> findProjectsWithNoCollaborators();

    // Get total number of collaborators for a set of project IDs
    @Query("SELECT p.projectID, COUNT(pc) FROM ProjectCollaboratorModel pc " + "JOIN pc.project p WHERE p.projectID IN :projectIDs GROUP BY p.projectID")
    List<Object[]> countCollaboratorsByProjectIDs(@Param("projectIDs") List<Integer> projectIDs);
    // ok so, @Param is an annotation from the Spring Framework. It is used to bind a method parameter to a named parameter in a query
    
    // Get collaborators who joined before a specific date for a project
    List<ProjectCollaboratorModel> findByProject_ProjectIDAndJoinedAtBefore(int projectID, LocalDateTime date);
    
   
}
