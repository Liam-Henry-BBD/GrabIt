// contain interfaces that extend Spring Data JPA's
// interfaces provide CRUD operations for your entities

package com.grabit.API.repository;

import com.grabit.API.model.ProjectCollaborator;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCollaboratorRepository extends JpaRepository<ProjectCollaborator, Integer> {
    // Additional query methods should be defined here
// --------------------------------------------------------

    //find collaborators by the usersID
    //List<ProjectCollaborator> findByUserID(Long userID);

    //find all the collaborators by the RoleID
    // //List<ProjectCollaborator> findByRoleID(Long roleID);

    // // find the collaborators by the ProjectID and isActive
    List<ProjectCollaborator> findByprojectCollaboratorID(Long ProjectCollaboratorID);

    // // find the collaborators by the ProjectID and isActive and order by joinedAt
    // List<ProjectCollaborator> findByProjectIDAndIsActiveOrderByJoinedAtDesc(Long ProjectID, boolean isActive);

    // // find the collaborators by the ProjectID and isActive and order by joinedAt
    // List<ProjectCollaborator> findByProjectIDAndIsActiveOrderByJoinedAtAsc(Long ProjectID, boolean isActive);

    // //find all the active collaborators
    // List<ProjectCollaborator> findByIsActive(boolean isActive);

    // //find all the active collaborators and order by joinedAt
    // List<ProjectCollaborator> findByIsActiveOrderByJoinedAtDesc(boolean isActive);
    
    // //find all collaborators by the RoleID and isActive
    // //List<ProjectCollaborator> findByRoleIDAndIsActive(Long roleID, boolean isActive);




}