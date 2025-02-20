// contain interfaces that extend Spring Data JPA's
// interfaces provide CRUD operations for your entities

package com.grabit.API.repository;

import com.grabit.API.model.ProjectCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCollaboratorRepository extends JpaRepository<ProjectCollaborator, Long> {
    // the rest of the methods that are inherited from JpaRepository go here
}