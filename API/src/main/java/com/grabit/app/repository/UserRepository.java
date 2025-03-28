package com.grabit.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grabit.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM Users WHERE GitHubID = :githubID", nativeQuery = true)
    User findByGitHubID(String githubID);

    @Query(value = "SELECT * FROM Users WHERE UserID = :userID", nativeQuery = true)
    User findByUserID(String userID);

    List<User> findByGitHubIDContainingIgnoreCase(String gitHubID);

}
