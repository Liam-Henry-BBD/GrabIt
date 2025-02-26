package com.grabit.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grabit.api.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
