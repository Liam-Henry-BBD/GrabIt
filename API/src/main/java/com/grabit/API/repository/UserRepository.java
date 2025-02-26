package com.grabit.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grabit.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
