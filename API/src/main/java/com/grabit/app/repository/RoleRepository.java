package com.grabit.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grabit.app.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
